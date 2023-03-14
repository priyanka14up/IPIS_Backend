/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: PdcUtil
 */
package com.innobitsysytems.ipis.utility;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.ChildrenDetails;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.services.CommunicationService;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.threadmgnt.CommThread;

@Component
public class PdcUtil implements UtilityInterface {

    @Autowired
    private DataUtil dataUtil;

    @Autowired
    private TcpPacketUtil tcpPacket;

    @Autowired
    private CommunicationService commService;

    @Autowired
    private DeviceRepository deviceRepository;
    
    @Autowired
   	private CommThread commThread;
    
    @Autowired
 	private NotificationService notificationService;

    @Override
    public void sendCommand( Device device, String command) {

    	 String ipAddress = device.getIpAddress();
    	
    	 if (command.equals(Command.Start.toString())) {
			 
			try {
				 
				 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pdc.toString(), Command.Start.toString(), ipAddress);

	             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

	          } catch (HandledException e) {
	        	  
	        	  notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
		    	  System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

	             }

    	 }else if (command.equals(Command.Stop.toString())) {
	    	  
	    	  try {
	        	  
	        	  byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pdc.toString(), Command.Stop.toString(), ipAddress);

	              commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

	           } catch (HandledException e) {
	        	   
	        	   notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
			       System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

	              }

    	 }else if (command.equals(Command.SoftReset.toString())) {
	    	  
	    	 try {
	        	  
	        	  byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pdc.toString(), Command.SoftReset.toString(), ipAddress);

	              commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

	          } catch (HandledException e) {
	        	  
	        	  notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
			      System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

	            }

    	 }else if (command.equals(Command.DeleteAllData.toString())) {
	    	
	    	try {
	        	 
	        	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pdc.toString(), Command.DeleteAllData.toString(), ipAddress);

	             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

	            } catch (HandledException e) {
	            	
	            	notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
				    System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

	                }

	}else if (command.equals(Command.GetConfiguration.toString())) {
	    	
	    	try {
	        	 
	        	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pdc.toString(), Command.GetConfiguration.toString(), ipAddress);

	             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

	           } catch (HandledException e) {
	        	   
	        	   notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
				   System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

	                }

	}else if (command.equals(Command.OptionalLinkCheck.toString())) {
		  
		  String[] platformArray = device.getPlatformNo();
		  
		  try {
	    	   
	    	   byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pdc.toString(), Command.OptionalLinkCheck.toString(), ipAddress);
	    	   byte[] response = commService.sendReceiveCommand(packetData, ipAddress, Protocol.TCP.toString());
	    	   commThread.response(response, platformArray);

	        } catch (HandledException e) {
	        	
	        	notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
			    System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

	          }

	}
}

    //To set intensity
   @Override
    public void sendConfiguration(List<Device> device) {
    	
        for (int i = 0; i < device.size(); i++) {
        	
        	Device data = device.get(i);
        	String ipAddress = device.get(i).getIpAddress();
        	commThread.pool.execute(new Runnable(){public void run(){sendPackage(ipAddress, data);}});
           
        }

    }
    
    public void sendPackage(String ipAddress, Device data) {
    	
    	 try {

             byte[] dataPacket = this.getPdcConfigData(data);
             byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.pdc.toString(), Command.SetConfiguration.toString(), ipAddress, dataPacket);
             commService.sendData(packetData, ipAddress, Protocol.TCP.toString());

         } catch (HandledException | IOException e) {

        	 notificationService.sendNotification(" Time out for PDC with Ip Address "+ipAddress);
			 System.out.println(e.getMessage()+" for PDC with Ip Address "+ipAddress);

         }

    }
    
    public byte[] getPdcConfigData(Device device) throws IOException {

    
    	
    	List<Device> allPdc=deviceRepository.findAllBydeviceType(DeviceType.pdc);
    	
    	for(int i=0;i<=allPdc.size();i++)
    	{
    		List pdcChildren=allPdc.get(i).getChildren();
    		
    		if (pdcChildren != null) {

    			for (int j = 0; j < pdcChildren.size();j++) {

    				Object obj = pdcChildren.get(i);
    				Map myMap = (Map) obj;
    				
    			System.out.println("myMapz"+myMap);
    				
    				
//    				String stringToConvert = String.valueOf(myMap.get("id"));
//    				Long convertedLong = Long.parseLong(stringToConvert);
//    				Device delpdc = deviceRepository.findAllById(convertedLong);
//    				deviceRepository.delete(delpdc);
    			}

    		}
    		
    	}
    	//Device pdc = deviceRepository.getIdByDeviceType(DeviceType.pdc, platformNumber);
		//List pdcChildren = pdc.getChildren();

		

    	
        //int noOfDevices = DataUtil.hexToInt(dataUtil.decimalToHex(String.valueOf(device.getChildren().size())));
        //System.out.println("number of devices---------->"+noOfDevices+"for pdc---->"+device.getDeviceName()+"of platform number"+device.getPlatformNo());
        
        int noOfConnectDevice = 0x00;
        byte[] data = new byte[0];
        int status = 0x00;  // for set configuration

        //data = new byte[]{(byte) noOfDevices, (byte) noOfConnectDevice};
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        output.write(data);
   
        List< ChildrenDetails > child = device.getChildren();
      
//    	for (int i = 0; i < child.size(); i++) {
//        	
//        	Object obj = child.get(i);
//    		Map myMap =  (Map)obj; 
//    		String devicetype = String.valueOf(myMap.get("deviceType"));
//    		Integer idInt = (Integer) myMap.get("id");
//    		Long idLong = Long.valueOf(idInt);
//    	
//            if (devicetype != null) {
//            	
//                switch (devicetype) {
//                
//                    case "pfdb": {
//                    	
//                    	Device pfdbData = deviceRepository.findAllById(idLong);
//                        String ip = pfdbData.getIpAddress();
//                        String[] ipArray = ip.split("[, . ']+");
//                        int deviceId = DataUtil.hexToInt(dataUtil.decimalToHex(ipArray[3]));
//                        int deviceType = 0x03;
//                        byte[] pfdbPacket = new byte[]{(byte) deviceId, (byte) deviceType};
//                        output.write(pfdbPacket);
//                        break;
//                        
//                    }
////                    case "cgdb": {
////                    	
////                        Device cgdbData = deviceRepository.findAllById(idLong);
////                        List<CoachesDetail> coachData = cgdbData.getCoaches();
////                     
////                        for (int j = 0; j < coachData.size(); j++) {
////
////                        	Object coach = coachData.get(j);
////                    		Map mapCoach =  (Map)coach; 
////                    		String ipAddress = String.valueOf(mapCoach.get("ipAddress"));
////                    		String[] ipArray = ipAddress.split("[, . ']+");
////                    		int deviceId = DataUtil.hexToInt(dataUtil.decimalToHex(ipArray[3]));
////                    		int deviceType = 0x02;
////                            byte[] cgdbPacket = new byte[]{(byte) deviceId, (byte) deviceType};
////                            output.write(cgdbPacket);
////                            
////                        }
////                        
////                        break;
////                        
//                   // }
//                    case "agdb": {
//                    	
//                        Device agdbData = deviceRepository.findAllById(idLong);
//                        String ip = agdbData.getIpAddress();
//                        String[] ipArray = ip.split("[, . ']+");
//                        int deviceId = DataUtil.hexToInt(dataUtil.decimalToHex(ipArray[3]));
//                        int deviceType = 0x04;
//                        byte[] agdbPacket = new byte[]{(byte) deviceId, (byte) deviceType};
//                        output.write(agdbPacket);
//                        break;
//                    }
//                    
//                    default:
//                    	
//                        break;
//                }
//            }
//
//        }

        byte[] dataStatus = new byte[]{(byte) status};
        output.write(dataStatus);
        byte[] finalPacket = output.toByteArray();
        
        return finalPacket;
    }

    @Override
    public void sendConfiguration(List<Device> device, int data) {

    }

    @Override
	public void sendMessage(Device device, String data, String speed, String effect, String letterSize) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect, String letterSize) {

	}

	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize, String effectCode) {
		// TODO Auto-generated method stub
		
	}



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
