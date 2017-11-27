/**
 * 
 */
package oops;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetSocketAddress;
import tcdIO.*;

/**
 *
 * Client class
 * 
 * An instance accepts user input
 *
 */
public class Client extends Node
{
	static final int	DEFAULT_SRC_PORT	= 50000;
	static final int	DEFAULT_DST_PORT	= 50001;
	static final int	SOCKET_LIMIT		= 100;
	static final String	DEFAULT_DST_NODE	= "localhost";
	static final int	PACKETS_IN_CYCLE	= 2;
	static final int	TIMEOUT				= 50;

	int	frameNumber	= 1;
	int	lastAckRecieved	= 0;

	Terminal			terminal = null;
	InetSocketAddress	dstAddress;
	DatagramPacket		lastSent;
	boolean				NACK	= false;

	/**
	 * Constructor
	 * 
	 * Attempts to create socket at given port and create an InetSocketAddress
	 * for the destinations
	 */
	Client(Terminal terminal, String dstHost, int dstPort, int srcPort)
	{
		try
		{
			boolean socketAvailable = false;
			while (!socketAvailable)
			{
				try
				{
					DatagramSocket attempt = new DatagramSocket(srcPort);
					attempt.close();
					socketAvailable = true;
				}
				catch (java.net.BindException ee)
				{
					srcPort--;
					if (DEFAULT_SRC_PORT - srcPort > SOCKET_LIMIT)
					{
						System.out.println(
								"Too many clients. Limit is " + SOCKET_LIMIT);
						System.exit(1);
					}
				}
			}
			this.terminal = terminal;
			terminal.println("Port: " + srcPort);
			dstAddress = new InetSocketAddress(dstHost, dstPort);
			socket = new DatagramSocket(srcPort);
			listener.go();
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}
	
	Client(String dstHost, int dstPort, int srcPort)
	{
		try
		{
			boolean socketAvailable = false;
			while (!socketAvailable)
			{
				try
				{
					DatagramSocket attempt = new DatagramSocket(srcPort);
					attempt.close();
					socketAvailable = true;
				}
				catch (java.net.BindException ee)
				{
					srcPort--;
					if (DEFAULT_SRC_PORT - srcPort > SOCKET_LIMIT)
					{
						System.out.println(
								"Too many clients. Limit is " + SOCKET_LIMIT);
						System.exit(1);
					}
				}
			}
			dstAddress = new InetSocketAddress(dstHost, dstPort);
			socket = new DatagramSocket(srcPort);
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
	public synchronized void onReceipt(DatagramPacket packet)
	{
		StringContent content = new StringContent(packet);
		if (content.toString().contains("NCK"))
		{
			NACK = true;
			this.notify();
			if (terminal != null)
			{
				terminal.println("Received: " + content.toString());
			}
		}
		else if(content.toString().contains("ACK"))
		{
			NACK = false;
			this.notify();
			if (terminal != null)
			{
				terminal.println("Received: " + content.toString());
			}
		}
	}

	/**
	 * Sender Method
	 * 
	 */
	public synchronized void start() throws Exception
	{
		while (true)
		{
			if (NACK)
			{
				socket.send(lastSent);
				if (terminal != null)
				{
					terminal.println("Resending...");
				}
				NACK = false;
			}
			else
			{
				DatagramPacket packet = null;

				byte[] payload = null;
				byte[] header = null;
				byte[] buffer = null;

				payload = (terminal.readString("String to send: ")).getBytes();

				header = new byte[PacketContent.HEADERLENGTH];
				byte[] socketBytes = Integer
						.toString(this.socket.getLocalPort()).getBytes();
				System.arraycopy(socketBytes, 0, header, 0, socketBytes.length);

				byte[] number = Integer.toString(frameNumber++).getBytes();
				System.arraycopy(number, 0, header,
						PacketContent.HEADERLENGTH - 1, number.length);

				buffer = new byte[header.length + payload.length];
				System.arraycopy(header, 0, buffer, 0, header.length);
				System.arraycopy(payload, 0, buffer, header.length,
						payload.length);
				
				if (terminal != null)
				{
					terminal.println("Sending packet...");
				}
				packet = new DatagramPacket(buffer, buffer.length, dstAddress);
				socket.send(packet);
				lastSent = packet;
				if (terminal != null)
				{
					terminal.println("Packet sent");
				}
				frameNumber = frameNumber % PACKETS_IN_CYCLE;
			}
			long tBefore = System.currentTimeMillis();
			this.wait(TIMEOUT);
			if ((System.currentTimeMillis() - tBefore) >= TIMEOUT)
			{
				NACK = true;
			}
		}
	}
	
	public synchronized void send(String s) throws Exception
	{
		while (!NACK)
		{
			if (NACK)
			{
				socket.send(lastSent);
				if (terminal != null)
				{
					terminal.println("Resending...");
				}
				NACK = false;
			}
			else
			{
				DatagramPacket packet = null;

				byte[] payload = null;
				byte[] header = null;
				byte[] buffer = null;

				payload = s.getBytes();

				header = new byte[PacketContent.HEADERLENGTH];
				byte[] socketBytes = Integer
						.toString(this.socket.getLocalPort()).getBytes();
				System.arraycopy(socketBytes, 0, header, 0, socketBytes.length);

				byte[] number = Integer.toString(frameNumber++).getBytes();
				System.arraycopy(number, 0, header,
						PacketContent.HEADERLENGTH - 1, number.length);

				buffer = new byte[header.length + payload.length];
				System.arraycopy(header, 0, buffer, 0, header.length);
				System.arraycopy(payload, 0, buffer, header.length,
						payload.length);
				
				if (terminal != null)
				{
					terminal.println("Sending packet...");
				}
				packet = new DatagramPacket(buffer, buffer.length, dstAddress);
				socket.send(packet);
				lastSent = packet;
				if (terminal != null)
				{
					terminal.println("Packet sent");
				}
				frameNumber = frameNumber % PACKETS_IN_CYCLE;
			}
			long tBefore = System.currentTimeMillis();
			this.wait(TIMEOUT);
			if ((System.currentTimeMillis() - tBefore) >= TIMEOUT)
			{
				NACK = true;
			}
		}
	}

	/**
	 * Test method
	 * 
	 * Sends a packet to a given address
	 */
	public static void main(String[] args)
	{
		try
		{
			Terminal terminal = new Terminal("Client");
			(new Client(terminal, DEFAULT_DST_NODE, DEFAULT_DST_PORT,
					DEFAULT_SRC_PORT)).start();
			terminal.println("Program completed");
		}
		catch (java.lang.Exception e)
		{
			e.printStackTrace();
		}
	}
}
