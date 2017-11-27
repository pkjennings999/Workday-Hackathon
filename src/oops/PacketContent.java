package oops;

import java.net.DatagramPacket;

public interface PacketContent
{

	public static byte HEADERLENGTH = 10;

	@Override
	public String toString();

	public DatagramPacket toDatagramPacket();
}
