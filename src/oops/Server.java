package oops;

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import tcdIO.Terminal;

public class Server extends Node
{
	static final int	DEFAULT_PORT		= 50001;
	static final int	PACKETS_IN_CYCLE	= 2;
	static final int UNKNOWN_POSITION = -1;

	Terminal	terminal = null;
	int[][]		clients;

	/*
	 * 
	 */
	Server(Terminal terminal, int port, int[][] clients)
	{
		try
		{
			
			this.terminal = terminal;
			this.clients = clients;
			socket = new DatagramSocket(port);
			listener.go();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}
	
	Server(int port, int[][] clients)
	{
		try
		{
			boolean socketAvailable = false;
			while (!socketAvailable)
			{
				try
				{
					DatagramSocket attempt = new DatagramSocket(port);
					attempt.close();
					socketAvailable = true;
				}
				catch (java.net.BindException ee)
				{
					port++;
					if (port - DEFAULT_PORT > 100)
					{
						System.out.println(
								"Too many servers. Limit is " + 100);
						System.exit(1);
					}
				}
			}
			this.clients = clients;
			socket = new DatagramSocket(port);
			listener.go();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * Assume that incoming packets contain a String and print the string.
	 */
	@Override
	public void onReceipt(DatagramPacket packet)
	{
		try
		{
			StringContent content = new StringContent(packet);
			byte[] data = packet.getData();
			byte[] header = new byte[PacketContent.HEADERLENGTH];
			System.arraycopy(data, 0, header, 0, header.length);

			byte[] port = new byte[5];
			System.arraycopy(header, 0, port, 0, port.length);

			int portInt = Integer.parseInt(new String(port));
			int clientPos = UNKNOWN_POSITION;
			for (int i = 0; i < clients.length; i++)
			{
				if (clients[i][0] == portInt)
				{
					clientPos = i;
				}
			}
			if (clientPos == UNKNOWN_POSITION)
			{
				int[][] temp = clients;
				clients = new int[clients.length + 1][2];
				System.arraycopy(temp, 0, clients, 0, temp.length);
				clients[clients.length - 1][0] = portInt;
				clientPos = clients.length - 1;
				clients[clientPos][1] = 0;
			}
			
			if (terminal != null)
			{
				terminal.println(
						"Received from " + portInt + ": " + content.toString());
			}

			byte[] packetNo = new byte[1];
			System.arraycopy(header, PacketContent.HEADERLENGTH - 1, packetNo,
					0, packetNo.length);
			int packetNoInt = Integer.parseInt(new String(packetNo));
			String responseString;

			if (packetNoInt != clients[clientPos][1])
			{
				responseString = "NCK";
				responseString += ((clients[clientPos][1]) % PACKETS_IN_CYCLE);
				if (terminal != null)
				{
					terminal.println("Received packet " + packetNoInt
							+ ". Expected " + clients[clientPos][1]);
				}
			}
			else
			{
				responseString = "ACK";
				clients[clientPos][1] = (clients[clientPos][1] + 1)
						% PACKETS_IN_CYCLE;
				responseString += clients[clientPos][1];
			}

			DatagramPacket response;
			response = (new StringContent(responseString))
					.toDatagramPacket(port);
			response.setSocketAddress(packet.getSocketAddress());
			if (terminal != null)
			{
				terminal.println("Sending to " + portInt + ": " + responseString);
			}

			socket.send(response);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	public synchronized void start() throws Exception
	{
		if (terminal != null)
		{
			terminal.println("Waiting for contact");
		}
		this.wait();
	}

	/*
	 * 
	 */
	public static void main(String[] args)
	{
		try
		{
			int[][] clients = new int[0][2];
			Terminal terminal = new Terminal("Server");
			(new Server(terminal, DEFAULT_PORT, clients)).start();
			terminal.println("Program completed");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}
}
