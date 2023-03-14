/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: ConnectionUtil
 */
package com.innobitsysytems.ipis.utility;

import java.io.DataOutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class ConnectionUtil {

public static void sendUdpDatagram(byte[] data, int length, InetAddress ipAddress, int portNumber) throws Exception{
	
	
	try {
		
			DatagramSocket ds = new DatagramSocket();
			DatagramPacket dp = new DatagramPacket(data,length,ipAddress,portNumber);
			ds.send(dp);
			ds.close();
	
	}catch( Exception e) {
		
			throw handleExceprtion(e);
		
	}
	
	
	
	
}


public static void broadcastUdpDatagram(byte[] data, int length, InetAddress ipAddress, int portNumber) throws Exception{
	
	
	try {
		
			DatagramSocket ds = new DatagramSocket(portNumber);
			DatagramPacket dp = new DatagramPacket(data,length,ipAddress,portNumber);
			ds.setBroadcast(true);
			ds.connect(ipAddress,portNumber);
			ds.send(dp);
			ds.close();
	
	}catch( Exception e) {
		
			throw handleExceprtion(e);
		
	}
	
	
	
	
}

public static String sendReceiveUdpDatagram(byte[] data, int length, InetAddress ipAddress, int portNumber) throws Exception{
	
	final String tempData; 
	
	try {
		
	        //Sending data
		
			DatagramSocket ds = new DatagramSocket();
			DatagramPacket dp = new DatagramPacket(data,length,ipAddress,portNumber);
			ds.send(dp);
			
			//Receiving data
			
			byte[] dataBuffer = new byte[1024];
			DatagramPacket recivePacket = new DatagramPacket(dataBuffer,dataBuffer.length);
			ds.receive(recivePacket);
			tempData = DataUtil.byteToString(recivePacket.getData());
			ds.close();
			
	
	}catch( Exception e) {
		
			throw handleExceprtion(e);
		
	}
	
			return tempData;
	 
	
}




public static void sendTcpMsg ( byte[] data, InetAddress ipAddress, int portNumber ) throws Exception {
	
	
	try {
	
			Socket s = new Socket( ipAddress, portNumber);
			DataOutputStream dout = new DataOutputStream(s.getOutputStream()); 
			dout.write(data);
			dout.flush();
			dout.close();
			s.close();
	
	
	}catch( Exception e ) {
		
		
			System.out.println(e);
		
		
	}
	
}

public static Exception handleExceprtion(Exception e) {
	
	return new Exception();
		
}



}
