package oops;

import java.net.DatagramPacket;

public class StringContent implements PacketContent {
	String string;
	
	public StringContent(DatagramPacket packet) {
		byte[] payload;
		byte[] buffer;
		
		buffer= packet.getData();
		System.out.println(HEADERLENGTH);
		System.out.println(buffer.toString());
		payload= new byte[packet.getLength()-HEADERLENGTH];
		System.arraycopy(buffer, HEADERLENGTH, payload, 0, packet.getLength()-HEADERLENGTH);
		string = new String(payload);
	}
	
	public StringContent(String string) {
		this.string = string;
	}
	
	@Override
	public String toString() {
		return string;
	}

	@Override
	public DatagramPacket toDatagramPacket() {
		DatagramPacket packet= null;
		byte[] buffer= null;
		byte[] payload= null;
		byte[] header= null;

		try {
			payload= string.getBytes();
			header= new byte[HEADERLENGTH];
			buffer= new byte[header.length+payload.length];
			packet= new DatagramPacket(buffer, buffer.length);
		}
		catch(Exception e) {e.printStackTrace();}
		return packet;
	}
	
	public DatagramPacket toDatagramPacket(byte[] header) {
		DatagramPacket packet= null;
		byte[] buffer= null;
		byte[] payload= null;
		
		try {
			payload= string.getBytes();
			buffer= new byte[HEADERLENGTH+payload.length];
			System.arraycopy(header, 0, buffer, 0, header.length);
			System.arraycopy(payload, 0, buffer, HEADERLENGTH, payload.length);
			packet= new DatagramPacket(buffer, buffer.length);
		}
		catch(Exception e) {e.printStackTrace();}
		return packet;
	}
}
