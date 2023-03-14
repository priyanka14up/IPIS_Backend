/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Tv Util
 */
package com.innobitsysytems.ipis.utility;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.services.CommunicationService;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.threadmgnt.CommThread;

@Component
public class TvUtil implements UtilityInterface{
	
	@Autowired
    private DataUtil dataUtil;
    
    @Autowired
    private TcpPacketUtil tcpPacket;
    
    @Autowired
    private CommunicationService commService;
    
    @Autowired
	private CommThread commThread;
    
    @Autowired
	private NotificationService notificationService;
    
    @Override
  	public void sendCommand( Device device, String command) {
    	
    	String ipAddress = device.getIpAddress();
  		 
    	if (command.equals(Command.Start.toString())) {
  			 
  			 try {
  				 
  				 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.tvDisplay.toString(), Command.Start.toString(), ipAddress);

  	             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

  	          } catch (HandledException e) {
  	        	  
  	        	notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
	 			System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

  	          }

  	      }else if (command.equals(Command.Stop.toString())) {
  	    	  
  	    	  try {
  	        	  
  	        	  byte[] packetData = tcpPacket.getCommandPacket(DeviceType.tvDisplay.toString(), Command.Stop.toString(), ipAddress);

  	              commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

  	           } catch (HandledException e) {
  	        	   
  	        	 notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
 	 			 System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

  	              }

  	      }else if (command.equals(Command.SoftReset.toString())) {
  	    	  
  	    	  try {
  	        	  
  	        	  byte[] packetData = tcpPacket.getCommandPacket(DeviceType.tvDisplay.toString(), Command.SoftReset.toString(), ipAddress);

  	              commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

  	          } catch (HandledException e) {
  	        	  
  	        	notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
	 			System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

  	            }

  	    }else if (command.equals(Command.DeleteAllData.toString())) {
  	    	
  	    	try {
  	        	 
  	        	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.tvDisplay.toString(), Command.DeleteAllData.toString(), ipAddress);

  	             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

  	            } catch (HandledException e) {
  	            	
  	            	notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
  		 			System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

  	                }

  	    }else if (command.equals(Command.GetConfiguration.toString())) {
  	    	
  	    	try {
  	        	 
  	        	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.tvDisplay.toString(), Command.GetConfiguration.toString(), ipAddress);

  	             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

  	           } catch (HandledException e) {
  	        	   
  	        	 notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
 	 			 System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

  	                }

  	  }else if (command.equals(Command.OptionalLinkCheck.toString())) {
  		  
  		  try {
  	    	   
  	    	   byte[] packetData = tcpPacket.getCommandPacket(DeviceType.tvDisplay.toString(), Command.OptionalLinkCheck.toString(), ipAddress);

  	           commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

  	        } catch (HandledException e) {
  	        	
  	        	notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
	 			System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

  	          }

  	    }
  }

    // To set intensity
	@Override
    public void sendConfiguration(List<Device> device, int data) {
        
    	for (int i = 0; i < device.size(); i++) {

            String ipAddress = device.get(i).getIpAddress();
            commThread.pool.execute(new Runnable(){public void run(){sendPackage(ipAddress, data);}});

    	}
    }
    
    public void sendPackage(String ipAddress, int data) {
    	
    	String color = "#00000";
        int timeOut = 300;

        try {

        	byte[] configData = this.getConfigData(data, timeOut, color, color, color, color, color, color, color, color, color);
        	byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.tvDisplay.toString(), Command.SetConfiguration.toString(), ipAddress, configData);
        	commService.sendData(packetData, ipAddress, Protocol.TCP.toString());

        } catch (Exception e) {

        	notificationService.sendNotification(" Time out for TV Display with Ip Address "+ipAddress);
 			System.out.println(e.getMessage()+" for TV Display with Ip Address "+ipAddress);

        }
    }
    
  private byte[] getConfigData(int data, int time, String hColor, String vColor, String bColor, String mColor, String tnoColor, String tnaColor, String tntColor, String tnadColor,String tnpfColor ) {
    	
    	int[] hozColor = dataUtil.colorConvert(hColor);
        int[] vertColor = dataUtil.colorConvert(vColor);
        int[] backColor = dataUtil.colorConvert(bColor); 
        int[] msgColor = dataUtil.colorConvert(mColor); 
        int statusCode = 0x01;
        int[] traiNoColor = dataUtil.colorConvert(tnoColor); 
        int[] trainNameColor = dataUtil.colorConvert(tnaColor); 
        int[] trainTimeColor = dataUtil.colorConvert(tntColor); 
        int[] trainAdColor = dataUtil.colorConvert(tnadColor);
        int[] trainPfColor = dataUtil.colorConvert(tnpfColor);

        byte[] res = new byte[]{(byte) data, (byte) time, (byte)hozColor[0],(byte)hozColor[1],(byte)hozColor[2],(byte)vertColor[0],(byte)vertColor[1],(byte)vertColor[2],
            
        		(byte)backColor[0],(byte)backColor[1],(byte)backColor[2],(byte)msgColor[0],(byte)msgColor[1],(byte)msgColor[2],(byte)statusCode,(byte)traiNoColor[0],(byte)traiNoColor[1],(byte)traiNoColor[2],
        		(byte)trainNameColor[0],(byte)trainNameColor[1],(byte)trainNameColor[2],(byte)trainTimeColor[0],(byte)trainTimeColor[1],(byte)trainTimeColor[2],(byte)trainAdColor[0],(byte)trainAdColor[1],(byte)trainAdColor[2],
        		(byte)trainPfColor[0],(byte)trainPfColor[1],(byte)trainPfColor[2]
        
        };

        return res;

    }

//  	@Override
//	public void sendDefaultData(List <Device> device, String data, String speed, String effect, String letterSize){
//
//      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//
//  }

  	@Override
	public void sendConfiguration(List<Device> device) {

      throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.

  }

	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
			String effectCode) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void sendDefaultData(List<Device> device, String[] data, String speed, String effect, String letterSize) {
//
//		// TODO Auto-generated method stub
//
//
//	}

	@Override
	public void sendMessage(Device device, String data, String speed, String effect, String letterSize) {

	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize) {
		// TODO Auto-generated method stub
		
	}

//	@Override
//	public void sendDefaultData(Device device, String[] data, String speed, String effect, String letterSize) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize, int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
			String effectCode, int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendConfiguration(List<Device> device, int data, int intDisplayTimeMldb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendPackage(String ipAddress, int intensity, int intDisplayTimePfdb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendConfigurationManualSingle(String ipAddress, int intensity, int intDisplayTimeMldb) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String ipAddress, String data, String speed, String effect, String letterSize, int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize,
			int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendDefaultData(List<Device> device, String[] data, String speed, String effect, String letterSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendDefaultData(Device device, String[] data, String speed, String effect, String letterSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlineTrain, String speed, String effect,
			String letterSize, int gap, int timeDelay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
			String effectCode, int gap, int timeDelay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String ipAddress, Message messageDetails, String speed, String effect, String letterSize,
			int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendMessage(String ipAddress, Message messageDetails, String speed, String effect, String letterSize,
			int gap, int timeDelay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize,
			int gap, int timeDelay) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendCommand(String ip, String command) {
		// TODO Auto-generated method stub
		
	}
	 
}
