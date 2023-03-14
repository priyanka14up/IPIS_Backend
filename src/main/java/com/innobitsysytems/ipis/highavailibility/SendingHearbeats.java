package com.innobitsysytems.ipis.highavailibility;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.TimerTask;
//import io.jsonwebtoken.io.IOException;

public class SendingHearbeats extends TimerTask 
{
	public void sendHeartBeat(String msg, String destinationIp, int sendingPort) throws Exception
	{
		DatagramSocket ds = new DatagramSocket();
		
		byte[] data = msg.getBytes();
		
		InetAddress i = InetAddress.getByName(destinationIp);

		DatagramPacket DpSend = new DatagramPacket(data, data.length, i, sendingPort);

		ds.send(DpSend);
		
		System.out.println(msg + "  send to other machine");

	}

	@Override
	public void run() 
	{
		try
		{

			Ha_Initialization ts = new Ha_Initialization();
			
			sendHeartBeat(ts.SendingMsg, ts.destination_ip, ts.sender_port);

		} 
		catch (Exception e)
		{
			System.out.println(e);
		}

	}

}
