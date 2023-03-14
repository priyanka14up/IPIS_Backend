/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: PfdUtil
 */
package com.innobitsysytems.ipis.utility;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.invoke.VarHandle;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.model.setup.TrainName;
import com.innobitsysytems.ipis.repository.setup.EnableDisableBoardRepository;
import com.innobitsysytems.ipis.repository.setup.TrainNameRepository;
import com.innobitsysytems.ipis.services.CommunicationService;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.threadmgnt.CommThread;


import sun.misc.Unsafe;
@Component
public class PfdUtil implements UtilityInterface{
	
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
	
	@Autowired
	private TrainNameRepository trainNameRepo;
	
	@Value("${ipis-server-port}")
	private String port;
	
	private static Unsafe unsafe;
	
	@Autowired
	EnableDisableBoardRepository enableDisableBoardRepository;
	
	static
	{
	    try
	    {
	        Field field = Unsafe.class.getDeclaredField("theUnsafe");
	        field.setAccessible(true);
	        unsafe = (Unsafe)field.get(null);
	    }
	    catch (Exception e)
	    {
	        e.printStackTrace();
	    }
	}

	public static long addressOf(String trainNo)
			throws Exception
			{
		String[] array = new String[] {trainNo};
		 long baseOffset = unsafe.arrayBaseOffset(String[].class);			   

		    return(baseOffset);
		}
	
	
	
	@Override
	public void sendCommand( Device device, String command) {
		
		 String ipAddress = device.getIpAddress();
		 
		 if (command.equals(Command.Start.toString())) {
				 
				try {
					 
					 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.Start.toString(), ipAddress);

		             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

		          } catch (HandledException e) {
		        	  
		        	  notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
		 			  System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

		             }

		}else if (command.equals(Command.Stop.toString())) {
		    	  
		    	  try {
		        	  
		        	  byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.Stop.toString(), ipAddress);

		              commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

		           } catch (HandledException e) {
		        	   
		        	   notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
		        	   System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

		              }

		}else if (command.equals(Command.SoftReset.toString())) {
		    	  
		    	  try {
		        	  
		        	  byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.SoftReset.toString(), ipAddress);

		              commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

		          } catch (HandledException e) {
		        	  
		        	  notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
		 			  System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

		            }

		}else if (command.equals(Command.DeleteAllData.toString())) {
		    	
		    	try {
		        	 
		        	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.DeleteAllData.toString(), ipAddress);

		             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

	          } catch (HandledException e) {
	        	  
	        	  notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
	 			  System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

		    	}

		}else if (command.equals(Command.GetConfiguration.toString())) {
		    	
		    	try {
		        	 
		        	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.GetConfiguration.toString(), ipAddress);

		             commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

		           } catch (HandledException e) {
		        	   
		        	   notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
		        	   System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

		            }

		}else if (command.equals(Command.OptionalLinkCheck.toString())) {
			  
			  try {
		    	   
		    	   byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.OptionalLinkCheck.toString(), ipAddress);

		           commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

		        } catch (HandledException e) {
		        	
		        	 notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
		 			 System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

		        }

		}
	 
	 
	}

	//To set intensity
	
//public void sendPackage(String ipAddress, int intensity,int intDisplayTimePfdb) {
//    	
//    	
//        try {
//
//        	byte[] configData = this.getConfigData(data, timeOut, color , color, color, color, color, color, color, color, color);
//        	//byte[] configData = this.getConfigData(intensity, intDisplayTimePfdb);
//			
//        	byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.pfdb.toString(), Command.SetConfiguration.toString(), ipAddress, configData);
//        	commService.sendData(packetData, ipAddress, Protocol.TCP.toString());
//
//        } catch (Exception e) {
//        	
//        	 notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
//			 System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);
//          
//        }
//    }
    
	private byte[] getConfigData(int data, int time) {

		int intensity=dataUtil.getIntensityHeader(data);
		
		
		byte[] res = new byte[] { (byte) intensity, (byte) time};

		return res;

	}
	
 //To set Default Message
 
    @Override
    public void sendDefaultData(List <Device> device, String data, String speed, String effect, String letterSize,int gap,int timeDelay) {
    	
    	 for (int i = 0; i < device.size(); i++) {

             String ipAddress = device.get(i).getIpAddress();
             System.out.println(ipAddress);
             commThread.pool.execute(new Runnable(){public void run(){defaultDataPacket(ipAddress, data, speed, effect, letterSize,gap,timeDelay);}});
            
         }
    }
    
    private void defaultDataPacket(String ipAddress, String data, String speed, String effect, String letterSize,int gap,int timeDelay) {

    			
    			short lColumn = 1, rColumn = 1, tRow = 1, bRow = 1;

        try {

           	byte[] configData = this.getDefaultData( lColumn,  rColumn,  tRow,  bRow, data, speed, effect, letterSize,gap,timeDelay);
        	System.out.println("byteArrayList-----");
			System.out.println("lColumn,rColumn,tRow,bRow,speed(rev video+not used+speed),effectcode(effect code+not used),lettersize(not used+letter size+gap),timedelay,startAddress,pfdbMsg,termination byte1,termination byte2");

//			for (int j = 0; j < configData.length;j++) {
//				System.out.print(configData[j]+ " ");
//			}
			   	
            List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
            if(enableDiable.get(0).getPfdb().equals("enable"))
            {
           	  
            	byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.pfdb.toString(), Command.DefaultDataPacket.toString(), ipAddress, configData);
               	commService.sendData(packetData, ipAddress, Protocol.TCP.toString(),Integer.parseInt(port));
             	
            }
            else
            {
           	 throw new HandledException("Pfdb devices are disabled","Disabled");
           	 
            }
           	
           	

           } catch (Exception e) {
           	
        	   notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
        	   System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);
              
           }
       	
       }
       
       private byte[] getDefaultData(short lColumn,short rColumn,short tRow,short bRow, String data, String speedValue, String effect, String letterSizeValue,int gap,int timeDelay) throws Exception {
       	
    	   List<Byte> res = new ArrayList<Byte>();

   		byte[] lc = new byte[2];
   		lc[0] = (byte) (lColumn >> 8);
   		lc[1] = (byte) lColumn;
   		res.add(lc[0]);
   		res.add(lc[1]);

   		byte[] rc = new byte[2];
   		rc[0] = (byte) (rColumn >> 8);
   		rc[1] = (byte) rColumn;
   		res.add(rc[0]);
   		res.add(rc[1]);

   		byte[] tr = new byte[2];
   		tr[0] = (byte) (tRow >> 8);
   		tr[1] = (byte) tRow;
   		res.add(tr[0]);
   		res.add(tr[1]);

   		byte[] br = new byte[2];
   		br[0] = (byte) (bRow >> 8);
   		br[1] = (byte) bRow;
   		res.add(br[0]);
   		res.add(br[1]);

   		// Speed

   		int rev = 0;
   		int speed = dataUtil.getSpeed(speedValue);

   		byte speed8 = (byte) (((rev & 0X01) << 7) | (speed & 0x07));

   		res.add(speed8);

   		// effectcode

   		int effectCode = dataUtil.getEffectCode(effect);
   		byte effect8 = (byte) (effectCode & 0x0f);

   		res.add(effect8);

   		// letter Size

   		int letterSize = dataUtil.getFont(letterSizeValue);
   		int gapFinal = dataUtil.getGap(gap);

   		byte letter8 = (byte) (((letterSize & 0x07) << 3) | (gapFinal & 0x07));

   		res.add(letter8);

   		

   		res.add((byte) timeDelay);

   		Charset charset = StandardCharsets.UTF_16;

   		byte[] msgByteArrray = data.getBytes(charset);

   		short add = (short) addressOf(data);
   		// System.out.println("start addresss in decimal------------->"+add);

   		byte[] sadd = new byte[2];
   		sadd[0] = (byte) (add >> 8);
   		sadd[1] = (byte) add;

   		res.add(sadd[0]);
   		res.add(sadd[1]);

   		for (int i = 2; i < msgByteArrray.length; i++) {
   			res.add(msgByteArrray[i]);
   		}

   		List<Byte> byteList = new ArrayList<Byte>();

   		for (int i = 0; i < res.size(); i++) {

   			byteList.add(res.get(i));

   		}

   		int terminationByte1 = 0xFF;

   		int terminationByte2 = 0xFF;

   		byteList.add((byte) terminationByte1);
   		byteList.add((byte) terminationByte2);

   		ByteArrayOutputStream baos = new ByteArrayOutputStream();
   		DataOutputStream out = new DataOutputStream(baos);

   		for (byte element : byteList) {

   			out.writeByte(element);

   		}

   		byte[] bytes = baos.toByteArray();

   		return bytes;

         }
       
      // To send Message
      @Override
      public void sendMessage(String ipAddress, Message messageDetails, String speed, String effect, String letterSize,int gap,int timeDelay) {
    	   
           commThread.pool.execute(new Runnable(){public void run(){msgDataPacket(ipAddress, messageDetails, speed, effect, letterSize,gap,timeDelay);}});
           
       }
      
      private void msgDataPacket(String ipAddress, Message messageDetails, String speed, String effect, String letterSize,int gap,int timeDelay) {
          
	
			short lColumn = 1, rColumn = 1, tRow = 1, bRow = 1;
         	
          try {

             	byte[] configData = this.getMsgPacket( lColumn,  rColumn,  tRow,  bRow, messageDetails, speed, effect, letterSize,gap,timeDelay);
             	
             	System.out.println("lColumn,rColumn,tRow,bRow,speed(rev video+not used+speed),effectcode(effect code+not used),lettersize(not used+letter size+gap),timedelay,startAddress,trainNo,gapcode,trainName,gapcode,time,gapcode,ad,gapcode, platform,gapcode,termination byte1,termination byte2");
            	
                for(int j=0;j<configData.length;j++)
            	{
            		System.out.print(configData[j]+",");
            	}
            	System.out.println();
            	System.out.println();
            	System.out.println();
             
             	 List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
                 if(enableDiable.get(0).getPfdb().equals("enable"))
                 {
                	  
                	 byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.pfdb.toString(), Command.MessageDataPacket.toString(), ipAddress, configData);
                  	commService.sendData(packetData, ipAddress, Protocol.TCP.toString());
                  	
                 }
                 else
                 {
                	 throw new HandledException("Pfdb devices are disabled","Disabled");
                	 
                 }

             } catch (Exception e) {
             	
            	 notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
	 			 System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);
                
             }
         	
         }
         
         private byte[] getMsgPacket(short lColumn,short rColumn,short tRow,short bRow, Message messageDetails, String speedValue, String effect, String letterSizeValue,int gap,int timeDelay) throws Exception {
         	
        	 List<Byte> res=new ArrayList<Byte>();

    	      
        	  
        	  byte[] lc=new byte[2];
        	  lc[0]= (byte)(lColumn>>8);
        	  lc[1]=(byte)lColumn;
        	 res.add(lc[0]);
        	 res.add(lc[1]);
           
           	
           	  
           	 
           	  byte[] rc=new byte[2];
           	rc[0]= (byte)(rColumn>>8);
           	rc[1]=(byte)rColumn;
           	 res.add(rc[0]);
           	 res.add(rc[1]);
           	

          
           	 byte[] tr=new byte[2];
           	tr[0]= (byte)(tRow>>8);
           	tr[1]=(byte)tRow;
            	 res.add(tr[0]);
            	 res.add(tr[1]);
            	

            	
           	
            	 byte[] br=new byte[2];
            	br[0]= (byte)(bRow>>8);
            	br[1]=(byte)bRow;
             	 res.add(br[0]);
             	 res.add(br[1]);
           
            	 
            	//Speed 

           	int rev=0;
           	int speed =dataUtil.getSpeed(speedValue);
           	
        
           	
           	byte speed8 =(byte)(((rev &0X01 )<<7) | (speed & 0x07)) ;
           	
           	res.add(speed8);

            //effectcode
           
            int effectCode = dataUtil.getEffectCode(effect);
            byte effect8 =(byte) (effectCode & 0x0f) ;
         	
         	
         	res.add(effect8);  

         	
           //letter Size
           
          int letterSize = dataUtil.getFont(letterSizeValue);
          int gapFinal = dataUtil.getGap(gap);
         
          byte letter8 =(byte)(((letterSize & 0x07)<<3)|(gapFinal & 0x07)) ;
       	
       	
       	res.add(letter8);  

       	System.out.println("timeDelay"+timeDelay);
    	     res.add((byte)timeDelay);   
    	     
    	     
    	     String engMessage=messageDetails.getMessageEnglish();
    	     
    	  	   Charset charset = StandardCharsets.UTF_16;
    	  	   
    	  	  
    	  	   byte[] engMsgByteArrray = engMessage.getBytes(charset);
    	  	   
    	  	 short add=(short)addressOf(engMessage);
    	      
    	      	 
    	      	 byte[] sadd=new byte[2];
    	      	 sadd[0]= (byte)(add>>8);
    	      	 sadd[1]=(byte)add;
    	      	 
    	          	 res.add(sadd[0]);
    	          	 res.add(sadd[1]);
    	             
    	             	
    	    	 
    	    	 for(int i=2;i<engMsgByteArrray.length;i++)
    	     	   {
    	     		   res.add(engMsgByteArrray[i]);
    	     	   }

    	  	 
    	  	 List<Byte> byteList = new ArrayList<Byte>();
    	     
    	     for(int i = 0; i < res.size(); i++) {
    	     	
    	     	 byteList.add(res.get(i));
    	     	
    	     }
    	     
    	     
    		   
    	     int gapCode=0Xe700;
         	
      	 	byte[] gCode=new byte[2];
      	 	
      	 	gCode[0]= (byte)((gapCode >>8) & 0xff);
      	 	gCode[1]=(byte)(gapCode & 0Xff);
       
       	 	int decode=(((gCode[1] & 0XFF)<<8)| gCode[0]);
       	 
       	 	short columnLeft=3;
    	 	
    	 	byte[] columnsTobeLeft=new byte[2];
    	 	columnsTobeLeft[0]= (byte)(columnLeft>>8);
    	 	columnsTobeLeft[1]=(byte)columnLeft;
    	     
    	 	
    	 	 byteList.add(gCode[0]);
             byteList.add(gCode[1]);
             
             byteList.add(columnsTobeLeft[0]);
             byteList.add(columnsTobeLeft[1]);
    	     
             String hindiMessage=messageDetails.getMessageHindi();
             
             byte[] hindiMsgByteArrray = hindiMessage.getBytes(charset);
    	     
             for(int i=2;i<hindiMsgByteArrray.length;i++)
	     	   {
            	 byteList.add(hindiMsgByteArrray[i]);
	     	   }
             

    	 	 byteList.add(gCode[0]);
             byteList.add(gCode[1]);
             
             byteList.add(columnsTobeLeft[0]);
             byteList.add(columnsTobeLeft[1]);
             
             String reginoalMessage=messageDetails.getMessageRegional();
             
             byte[] reginoalMsgByteArrray = reginoalMessage.getBytes(charset);
             
             for(int i=2;i<reginoalMsgByteArrray.length;i++)
	     	   {
          	 byteList.add(reginoalMsgByteArrray[i]);
	     	   }
             
    	  	  int terminationByte1 = 0xFF;
    	  	   
    	  	   int terminationByte2 = 0xFF;
    	  	   
    	        
    	  	 byteList.add((byte) terminationByte1);
    	  	byteList.add((byte) terminationByte2);
    	      
    	     
    	       
    	       ByteArrayOutputStream baos = new ByteArrayOutputStream();
    	       DataOutputStream out = new DataOutputStream(baos);

    	       for (byte element : byteList) {
    	       	
    	           out.writeByte(element);
    	           
    	       }
    	       
    	       byte[] bytes = baos.toByteArray();
    	      
    	       return bytes;
             
           }

         // To display TADB
         @Override
     	public void displayTadb(List<Device> device, List<OnlineTrain> onlineTrain, String speed, String effect, String letterSize,int gap,int timeDelay) {
        	
     		for (int i = 0; i < device.size(); i++) {
     			
     			for (int j = 0; j < onlineTrain.size(); j++) {
     				
     				if ( device.get(i).getPlatformNo()[0].equals(onlineTrain.get(j).getPlatformNo())){
     					
     					tadbDataPacket( device.get(i).getIpAddress(), onlineTrain.get(j),  speed,  effect,  letterSize,gap,timeDelay);
     					
     					
     				}
     				
     			}
     		}
         	
     	}
        
         private void tadbDataPacket(String ipAddress, OnlineTrain onlineTrain, String speed, String effect, String letterSize,int gap,int timeDelay) {
             
    		
    		short lColumn = 1, rColumn = 1, tRow = 1, bRow = 1;
         	String trainNo = String.valueOf(onlineTrain.getTrainNumber());
         	String trainName = onlineTrain.getTrainName();
         	String ad = onlineTrain.getArrDep();
         	String platform = String.valueOf(onlineTrain.getPlatformNo());
         	String time = onlineTrain.getETA();
         	String trainStatus=onlineTrain.getTrainStatus();
         	
          try {
         	 
         	  byte[] configData = this.getTadbPacket( lColumn,  rColumn,  tRow,  bRow, trainNo, time, ad, platform, trainName,trainStatus,speed, effect, letterSize,gap,timeDelay);
         	  
              List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
              if(enableDiable.get(0).getPfdb().equals("enable"))
              {
             	  
            	  byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.pfdb.toString(), Command.DataTransfer.toString(), ipAddress, configData);              
                   
              commService.sendData(packetData, ipAddress, Protocol.TCP.toString());
              }
              else
              {
             	 throw new HandledException("Pfdb devices are disabled","Disabled");
             	 
              }
             }
          catch (Exception e) {
             	
            	 notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
	 			 System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);
                
             }
         	
         }
         
     
        	 private byte[] getTadbPacket(short lColumn,short rColumn,short tRow,short bRow, String trainNo, String time, String ad, String platform, String trainName,String trainStatus, String speedValue, String effect, String letterSizeValue,int gap,int timeDelay) throws Exception {
              	
        	        
        		 List<Byte> res=new ArrayList<Byte>();

              	  byte[] lc=new byte[2];
              	lc[0]= (byte)(lColumn>>8);
              	lc[1]=(byte)lColumn;
              	 res.add(lc[0]);
              	 res.add(lc[1]);
                 
               
                 	 
                 	  byte[] rc=new byte[2];
                 	rc[0]= (byte)(rColumn>>8);
                 	rc[1]=(byte)rColumn;
                 	 res.add(rc[0]);
                 	 res.add(rc[1]);
                 	
         
                 	 
                 	 byte[] tr=new byte[2];
                 	tr[0]= (byte)(tRow>>8);
                 	tr[1]=(byte)tRow;
                  	 res.add(tr[0]);
                  	 res.add(tr[1]);
                  	
          
                  	
                 	
                  	 byte[] br=new byte[2];
                  	br[0]= (byte)(bRow>>8);
                  	br[1]=(byte)bRow;
                   	 res.add(br[0]);
                   	 res.add(br[1]);
                   	
           	
                 	
                  	//Speed 

                 	int rev=0;
                 	int speed =dataUtil.getSpeed(speedValue);
                
                 	
                 	byte speed8 =(byte)(((rev &0X1 )<<7) | (speed & 0x07)) ;
                 	
                 
                 	res.add(speed8);

                  //effectcode
                 
                  int effectCode = dataUtil.getEffectCode(effect);
                  byte effect8 =(byte) (effectCode & 0x0f) ;
               	
               
               	res.add(effect8);  

               	
                 //letter Size
                 
                int letterSize = dataUtil.getFont(letterSizeValue);
                int gapFinal = dataUtil.getGap(gap);
               
                byte letter8 =(byte)(((letterSize & 0x07)<<3)|(gapFinal & 0x07)) ;
             	
             	
             	res.add(letter8);  

             	//int timeDelay = 0x14;
     	   
     	     res.add((byte)timeDelay)  ;   
          	
          	   Charset charset = StandardCharsets.UTF_16;
          	
          	   byte[] trainNoArray = trainNo.getBytes(charset);
          	   	  
          	  byte[] trainNameArray = trainName.getBytes(charset);
          	     
          	   byte[] timeArray = time.getBytes(charset);
          	  
          	   byte[] adArray = ad.getBytes(charset);
          	     
          	   byte[] platformArray = platform.getBytes(charset);
          	   
          	  
          	   
         	 short add=(short)addressOf(trainNo);
       	
       	 
       	 byte[] sadd=new byte[2];
       	 sadd[0]= (byte)(add>>8);
       	 sadd[1]=(byte)add;
       	 
           	 res.add(sadd[0]);
           	 res.add(sadd[1]);
              
         	 
         	 for(int i=2;i<trainNoArray.length;i++)
          	   {
          		   res.add((byte)trainNoArray[i]);
          	   }
             	 
         	 
        
         	  int gapCode=0Xe700;
        	
     	 	byte[] gCode=new byte[2];
     	 	
     	 	gCode[0]= (byte)((gapCode >>8) & 0xff);
     	 	gCode[1]=(byte)(gapCode & 0Xff);
      
      	 	int decode=(((gCode[1] & 0XFF)<<8)| gCode[0]);
      	 
      	 	short columnLeft=3;
   	 	
   	 	byte[] columnsTobeLeft=new byte[2];
   	 	columnsTobeLeft[0]= (byte)(columnLeft>>8);
   	 	columnsTobeLeft[1]=(byte)columnLeft;
             
                 List<Byte> byteList = new ArrayList<Byte>();
                 
                 for(int i = 0; i < res.size(); i++) {
                 	
                 	 byteList.add(res.get(i));
                 	
                 }
                 
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
               
                 
                 for (int i = 2; i < trainNameArray.length; i++) {
                 	 
                   	 byteList.add(trainNameArray[i]);
                   	
                   }
                
                 
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 for (int i = 2; i < timeArray.length; i++) {
                 	 
                 	 byteList.add(timeArray[i]);
                 	
                 }
                
                 
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 for (int i = 2; i < adArray.length; i++) {
                 	
                 	 byteList.add(adArray[i]);
                 	
                 }
                   
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 for (int i = 2; i < platformArray.length; i++) {
                   	 
                 	 byteList.add(platformArray[i]);
                 	
                 }
             
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 
                 
                 //status code
                 int statusCode=dataUtil.getCommandPacketHeaderForColorStatus(trainStatus,ad);
                 byteList.add((byte)statusCode);
                 
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 //TrainName trainName1=new TrainName();
                 TrainName getTrainName=trainNameRepo.findByTrainNo(Long.parseLong(trainNo));
                 String hindiTrainName=getTrainName.getHindiTrainName();
                 String reginolalTrainName=getTrainName.getRegionalTrainName();
                 System.out.println("hindiTrainName-------------"+hindiTrainName);
                 System.out.println("regionalTrainName-------------"+reginolalTrainName);
                 
                 
                 byte[] hindiTrainNameByteArray = hindiTrainName.getBytes(charset);
                 
                 for (int i = 2; i < hindiTrainNameByteArray.length; i++) {
                	 
                  	 byteList.add(hindiTrainNameByteArray[i]);
                  	
                  }
              
                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 byte[] regionalTrainNameByteArray = reginolalTrainName.getBytes(charset);
                 
                 for (int i = 2; i < regionalTrainNameByteArray.length; i++) {
                 	 
                   	 byteList.add(regionalTrainNameByteArray[i]);
                   	
                   }
                 

                 byteList.add(gCode[0]);
                 byteList.add(gCode[1]);
                 
                 byteList.add(columnsTobeLeft[0]);
                 byteList.add(columnsTobeLeft[1]);
                 
                 int terminationByte1 = 0xFF;
           	   
           	   int terminationByte2 = 0xFF;
           	   
                 
                byteList.add((byte) terminationByte1);
                byteList.add((byte) terminationByte2);
               
                System.out.println("data packet size"+byteList.size());
            
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(baos);

                for (byte element : byteList) {
                	
                    out.writeByte(element);
                    
                }
                
                byte[] bytes = baos.toByteArray();
               
                return bytes;
                
             }
         

         
     


      @Override
      public void sendConfiguration(List<Device> device) {
          throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
      }
	
	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
			String effectCode) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
			String effectCode, int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendConfiguration(List<Device> device, int data, int intDisplayTimePfdb) {
		// TODO Auto-generated method stub
		for (int i = 0; i < device.size(); i++) {

            String ipAddress = device.get(i).getIpAddress();
            commThread.pool.execute(new Runnable(){public void run(){sendPackage(ipAddress, data,intDisplayTimePfdb);}});

        }
	}



	@Override
	public void sendConfiguration(List<Device> device, int data) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendPackage(String ipAddress, int intensity, int intDisplayTimePfdb) {
		// TODO Auto-generated method stub
		try {
			
		
			byte[] configData = this.getConfigData(intensity, intDisplayTimePfdb);
			
			byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.pfdb.toString(),
					Command.SetConfiguration.toString(), ipAddress, configData);

			commService.sendData(packetData, ipAddress, Protocol.TCP.toString());

		} catch (Exception e) {

			notificationService.sendNotification(" Time out for PFDB with Ip Address " + ipAddress);

			System.out.println(e.getMessage() + " for PFDB with Ip Address " + ipAddress);

		}
	}


	// To set manual intensity (in interface set configuration)
	@Override
	public void sendConfigurationManualSingle(String ipAddress, int intensity, int intDisplayTimeMldb) {
		// TODO Auto-generated method stub
		commThread.pool.execute(new Runnable() {
			public void run() {
				sendPackage(ipAddress, intensity,intDisplayTimeMldb);
			}
		});
		
	}



	@Override
	public void sendMessage(Device device, String data, String speed, String effect, String letterSize) {
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
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize, int gap) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize,
			String effectCode, int gap, int timeDelay) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendMessage(String ipAddress, String data, String speed, String effect, String letterSize, int gap) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendMessage(String ipAddress, Message messageDetails, String speed, String effect, String letterSize,
			int gap) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize,
			int gap) {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void sendCommand(String ipAddress, String command) {
		// TODO Auto-generated method stub
		try {
       	 
       	 byte[] packetData = tcpPacket.getCommandPacket(DeviceType.pfdb.toString(), Command.GetConfiguration.toString(), ipAddress);

            commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

          } catch (HandledException e) {
       	   
       	   notificationService.sendNotification(" Time out for PFDB with Ip Address "+ipAddress);
       	   System.out.println(e.getMessage()+" for PFDB with Ip Address "+ipAddress);

           }

	}

 
}
