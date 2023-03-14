/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Communication Service
 */
package com.innobitsysytems.ipis.services;

import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.library.TcpClient;
import com.innobitsysytems.ipis.library.UdpClient;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.utility.TcpPacketUtil;

import java.net.*;
import java.io.*;
import org.springframework.beans.factory.annotation.Autowired;

@Service
public class CommunicationService {

    @Autowired
    private TcpPacketUtil tcpPacket;
    
    @Autowired
    private TcpClient tcpClient;
    
    @Autowired
    private UdpClient udpClient;
    
    final private int port = 8887;

    public void sendData( byte[] data, String ipAddress, String protocol) throws HandledException {

    	System.out.println("data in bytes aaray");
        for(int i=0;i<data.length;i++)
        {
        	System.out.print(data[i]+" ");
        	
        }
        System.out.println();

        try {

            InetAddress ia = InetAddress.getByName("127.0.0.1");

            if (protocol.equals("TCP")) {
            	
            	tcpClient.sendTcpMsg(data, ia, 25000,null);
                

            } else if (protocol.equals("UDP")) {

                 //UdpClient.sendUdpDatagram(DataUtil.stringToByte(data), data.length(), ia, port);
            }

        } catch (Exception e) {

            throw new HandledException("COMM_EXCEPTION", "Exception " + e);

        }

    }

    public void sendData( byte[] data, String ipAddress, String protocol,int port) throws HandledException {

    	System.out.println("data in bytes aaray");
        for(int i=0;i<data.length;i++)
        {
        	System.out.print(data[i]+" ");
        	
        }
        System.out.println();

        try {

            InetAddress ia = InetAddress.getByName("192.168.3.7");

            if (protocol.equals("TCP")) {
            	
            	tcpClient.sendTcpMsg(data, ia,25000,null);
                

            } else if (protocol.equals("UDP")) {

                  udpClient.sendUdpDatagram(data, data.length,ia,26000 );
            }

        } catch (Exception e) {

            throw new HandledException("COMM_EXCEPTION", "Exception " + e);

        }

    }
    
    
    
    public void sendCommand(byte[] data, String ipAddress, String protocol) throws HandledException {

        try {
            InetAddress ia = InetAddress.getByName("192.168.3.7");

            if (protocol.equals("TCP")) {

            	tcpClient.sendTcpMsg(data, ia, 25000,null);

            } else if (protocol.equals("UDP")) {

                udpClient.sendUdpDatagram(data, data.length, ia,26000);

            }

        } catch (Exception e) {

            throw new HandledException("COMM_EXCEPTION", "Exception " + e);

        }

    }
    
    public byte[] sendReceiveCommand(byte[] data, String ipAddress, String protocol) throws HandledException {

    	byte[] byteArray = new byte[0];
    	
        try {

            InetAddress ia = InetAddress.getByName(ipAddress);

            if (protocol.equals("TCP")) {

            	byteArray = TcpClient.sendReceiveTcpMsg(data, ia, port);

            } else if (protocol.equals("UDP")) {

               // UdpClient.sendUdpDatagram(DataUtil.stringToByte(data), data.length(), ia, port);

            }

        } catch (Exception e) {

            throw new HandledException("COMM_EXCEPTION", "Exception " + e);

        }
        
        return byteArray;

    }

    public boolean pingDeviceByIp(String ipAddress, int timeout) throws HandledException {

        boolean reachable = false;

        try {

            InetAddress inetAddress = InetAddress.getByName(ipAddress);

            if (inetAddress.isReachable(timeout)) {

                reachable = true;

            } else {

                reachable = false;

            }

        } catch (IOException e) {

            throw new HandledException("IOEXCEPTION", "Exception " + e);

        }

        return reachable;

    }

}
