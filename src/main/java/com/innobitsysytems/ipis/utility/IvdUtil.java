/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: IvdUtil
 */
package com.innobitsysytems.ipis.utility;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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
public class IvdUtil implements UtilityInterface {

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

	private static Unsafe unsafe;
	
	@Autowired
	public EnableDisableBoardRepository enableDisableBoardRepository;
	

	static {
		try {
			Field field = Unsafe.class.getDeclaredField("theUnsafe");
			field.setAccessible(true);
			unsafe = (Unsafe) field.get(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static long addressOf(String trainNo) throws Exception {
		String[] array = new String[] { trainNo };
		long baseOffset = unsafe.arrayBaseOffset(String[].class);

		return (baseOffset);
	}

	@Override
	public void sendCommand(Device device, String command) {

		String ipAddress = device.getIpAddress();

		if (command.equals(Command.Start.toString())) {

			try {

				byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(), Command.Start.toString(),
						ipAddress);

				commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

			} catch (HandledException e) {

				notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
				System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

			}

		} else if (command.equals(Command.Stop.toString())) {

			try {

				byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(), Command.Stop.toString(),
						ipAddress);

				commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

			} catch (HandledException e) {

				notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
				System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

			}

		} else if (command.equals(Command.SoftReset.toString())) {

			try {

				byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(), Command.SoftReset.toString(),
						ipAddress);

				commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

			} catch (HandledException e) {

				notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
				System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

			}

		} else if (command.equals(Command.DeleteAllData.toString())) {

			try {

				byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(),
						Command.DeleteAllData.toString(), ipAddress);

				commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

			} catch (HandledException e) {

				notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
				System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

			}

		} else if (command.equals(Command.GetConfiguration.toString())) {

			try {

				byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(),
						Command.GetConfiguration.toString(), ipAddress);

				commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

			} catch (HandledException e) {

				notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
				System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

			}

		} else if (command.equals(Command.OptionalLinkCheck.toString())) {

			try {

				byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(),
						Command.OptionalLinkCheck.toString(), ipAddress);

				commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

			} catch (HandledException e) {

				notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
				System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

			}

		}
	}

	// To set intensity(ivd)
	@Override
	public void sendConfiguration(List<Device> device, int data) {

		for (int i = 0; i < device.size(); i++) {

			String ipAddress = device.get(i).getIpAddress();
			commThread.pool.execute(new Runnable() {
				public void run() {
					sendPackage(ipAddress, data);
				}
			});

		}
		// commThread.pool.shutdown();
	}

	public void sendPackage(String ipAddress, int data) {

		String colour = "#00000";

		int timeOut = 300;

		try {

			byte[] configData = this.getConfigData(data, timeOut, colour, colour, colour, colour, colour, colour,
					colour, colour, colour);
			byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.ivd.toString(),
					Command.SetConfiguration.toString(), ipAddress, configData);
			commService.sendData(packetData, ipAddress, Protocol.TCP.toString());

		} catch (Exception e) {

			notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
			System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

		}

	}

	private byte[] getConfigData(int data, int time, String hColor, String vColor, String bColor, String mColor,
			String tnoColor, String tnaColor, String tntColor, String tnadColor, String tnpfColor) {

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

		byte[] res = new byte[] { (byte) data, (byte) time, (byte) hozColor[0], (byte) hozColor[1], (byte) hozColor[2],
				(byte) vertColor[0], (byte) vertColor[1], (byte) vertColor[2],

				(byte) backColor[0], (byte) backColor[1], (byte) backColor[2], (byte) msgColor[0], (byte) msgColor[1],
				(byte) msgColor[2], (byte) statusCode, (byte) traiNoColor[0], (byte) traiNoColor[1],
				(byte) traiNoColor[2], (byte) trainNameColor[0], (byte) trainNameColor[1], (byte) trainNameColor[2],
				(byte) trainTimeColor[0], (byte) trainTimeColor[1], (byte) trainTimeColor[2], (byte) trainAdColor[0],
				(byte) trainAdColor[1], (byte) trainAdColor[2], (byte) trainPfColor[0], (byte) trainPfColor[1],
				(byte) trainPfColor[2]

		};

		return res;

	}

//    @Override
//    public void sendDefaultData(List <Device> device, String[] data, String speed, String effect, String letterSize) {
//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//    }

	@Override
	public void sendConfiguration(List<Device> device) {
		throw new UnsupportedOperationException("Not supported yet."); // To change body of generated methods, choose
																		// Tools | Templates.
	}

//	@Override
//	public void sendDefaultData(List<Device> device, String data, String speed, String effect, String letterSize) {
//		// TODO Auto-generated method stub
//		
//	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize, int gap,int timeDelay) {
		// TODO Auto-generated method stub

		for (int i = 0; i < device.size(); i++) {

			tadbDataPacket(device.get(i).getIpAddress(), onlinetrain, speed, effect, letterSize, gap,timeDelay);

		}

	}

	private void tadbDataPacket(String ipAddress, List<OnlineTrain> onlineTrain, String speed, String effect,
			String letterSize, int gap,int timeDelay) {

		short lColumn = 1, rColumn = 1, tRow = 1, bRow = 1;

		List<Byte> byteArrayList = new ArrayList<Byte>();

		for (int i = 0; i < onlineTrain.size(); i++) {

			String trainNo = String.valueOf(onlineTrain.get(i).getTrainNumber());

			String trainName = onlineTrain.get(i).getTrainName();

			String ad = onlineTrain.get(i).getArrDep();
			String trainStatus = onlineTrain.get(i).getTrainStatus();

			String platform = String.valueOf(onlineTrain.get(i).getPlatformNo());

			String time = onlineTrain.get(i).getETA();

			try {
				byteArrayList.addAll(this.getTadbPacket(lColumn, rColumn, tRow, bRow, trainNo, time, ad, platform,
						trainName, speed, effect, letterSize, gap, trainStatus,timeDelay));

			} catch (Exception e) {
				System.out.println(e.getMessage() + "in byte array list");
			}
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);

		for (byte element : byteArrayList) {

			try {

				out.writeByte(element);

			} catch (IOException e) {

				System.out.println(e.getMessage() + "in catch of byte element");
			}
		}

		byte[] bytes = baos.toByteArray();

		try {

			 List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
	            if(enableDiable.get(0).getIvdDisplay().equals("enable"))
	            {
	           	  

	    			byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.ivd.toString(),Command.DataTransfer.toString(), ipAddress, bytes);

	    			commService.sendData(packetData, ipAddress, Protocol.TCP.toString());
	    			
	            }
	            else
	            {
	           	 throw new HandledException("Ivd devices are disabled","Disabled");
	           	 
	            }

		}

		catch (HandledException e) {

			notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
			System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);
		}

	}

	private List<Byte> getTadbPacket(short leftColumn, short rightColumn, short tRow, short bRow, String trainNo, String time,
			String ad, String platform, String trainName, String speedValue, String effect, String letterSizeValue,
			int gap, String trainStatus,int timeDelay) throws Exception {

		
		  List<Byte> res=new ArrayList<Byte>();
		//window Parameters
		
		byte[] leftColumnBytes = new byte[2];
		byte[] rightColumnBytes = new byte[2];
		byte[] topRowBytes = new byte[2];
		byte[] bottomRowBytes = new byte[2];

		leftColumnBytes=dataUtil.getDataOnTwoBytesParam(leftColumn);
		
		for(int i=0;i<leftColumnBytes.length;i++)
		{
			res.add(leftColumnBytes[i]);	
		}
		
		rightColumnBytes=dataUtil.getDataOnTwoBytesParam(rightColumn);
		
		for(int i=0;i<leftColumnBytes.length;i++)
		{
			res.add(rightColumnBytes[i]);
			
		}
		
		topRowBytes=dataUtil.getDataOnTwoBytesParam(tRow);

		for(int i=0;i<leftColumnBytes.length;i++)
		{
			res.add(topRowBytes[i]);
			
		}

		bottomRowBytes=dataUtil.getDataOnTwoBytesParam(bRow);

		for(int i=0;i<leftColumnBytes.length;i++)
		{
			res.add(bottomRowBytes[i]);
			
		}

		// Speed

		
		int speed = dataUtil.getSpeed(speedValue);

		byte speed8 = (byte) (((Constants.REVERSE_VIDEO & 0X01) << 7) | (speed & 0x07));

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

		byte[] trainNoArray = trainNo.getBytes(charset);

		byte[] trainNameArray = trainName.getBytes(charset);

		byte[] timeArray = time.getBytes(charset);

		byte[] adArray = ad.getBytes(charset);

		byte[] platformArray = platform.getBytes(charset);
		
		byte[] trainStatusArray=trainStatus.getBytes(charset);
		
		short add = (short) addressOf(trainNo);

		byte[] startAddressBytes = new byte[2];
		
		startAddressBytes=dataUtil.getDataOnTwoBytesParam(add);
		

		for(int i=0;i<startAddressBytes.length;i++)
		{
			res.add(startAddressBytes[i]);
			
		}
		
		for (int i = 2; i < trainNoArray.length; i++) {
			res.add((byte) trainNoArray[i]);
		}

		byte[] gapCodeBytes = new byte[2];

		gapCodeBytes[0] = (byte) ((Constants.GAP_CODE >> 8) & 0xff);
		gapCodeBytes[1] = (byte) (Constants.GAP_CODE & 0Xff);

		

		byte[] columnsTobeLeft = new byte[2];
		
		columnsTobeLeft=dataUtil.getDataOnTwoBytesParam(Constants.COLUMN_lEFT);

		//further use
		//int decode = (((gCode[1] & 0XFF) << 8) | gCode[0]);

		List<Byte> byteList = new ArrayList<Byte>();

		for (int i = 0; i < res.size(); i++) {

			byteList.add(res.get(i));

		}

		for (int i = 0; i < gapCodeBytes.length; i++) {

			byteList.add(gapCodeBytes[i]);

		}
		

		for (int i = 0; i < columnsTobeLeft.length; i++) {

			byteList.add(columnsTobeLeft[i]);
		}


		for (int i = 2; i < trainNameArray.length; i++) {

			byteList.add(trainNameArray[i]);

		}

		for (int i = 0; i < gapCodeBytes.length; i++) {

			byteList.add(gapCodeBytes[i]);

		}
		
		for (int i = 0; i < columnsTobeLeft.length; i++) {

			byteList.add(columnsTobeLeft[i]);
		}
		


		for (int i = 2; i < timeArray.length; i++) {

			byteList.add(timeArray[i]);

		}
		
		for (int i = 0; i < gapCodeBytes.length; i++) {

			byteList.add(gapCodeBytes[i]);

		}
		
		for (int i = 0; i < columnsTobeLeft.length; i++) {

			byteList.add(columnsTobeLeft[i]);
		}
		
		
		
		for (int i = 2; i < adArray.length; i++) {

			byteList.add(adArray[i]);

		}
		
		for (int i = 0; i < gapCodeBytes.length; i++) {

			byteList.add(gapCodeBytes[i]);

		}
		
		for (int i = 0; i < columnsTobeLeft.length; i++) {

			byteList.add(columnsTobeLeft[i]);
		}
		
		
		
		
		
		//status code
//        int statusCode=dataUtil.getCommandPacketHeaderForColorStatus(trainStatus,ad);
//        byteList.add((byte)statusCode);
        
        for (int i = 2; i < trainStatusArray.length; i++) {

			byteList.add(trainStatusArray[i]);

		}
        
        
        for (int i = 0; i < gapCodeBytes.length; i++) {

			byteList.add(gapCodeBytes[i]);

		}
		
		for (int i = 0; i < columnsTobeLeft.length; i++) {

			byteList.add(columnsTobeLeft[i]);
		}
		
		
		
		
		
		 TrainName getTrainName=trainNameRepo.findByTrainNo(Long.parseLong(trainNo));
         String hindiTrainName=getTrainName.getHindiTrainName();
         String reginolalTrainName=getTrainName.getRegionalTrainName();
         System.out.println("hindiTrainName-------------"+hindiTrainName);
         System.out.println("regionalTrainName-------------"+reginolalTrainName);
         
         
         byte[] hindiTrainNameByteArray = hindiTrainName.getBytes(charset);
         
         for (int i = 2; i < hindiTrainNameByteArray.length; i++) {
        	 
          	 byteList.add(hindiTrainNameByteArray[i]);
          	
          }
      
         for (int i = 0; i < gapCodeBytes.length; i++) {

 			byteList.add(gapCodeBytes[i]);

 		}
 		
 		for (int i = 0; i < columnsTobeLeft.length; i++) {

 			byteList.add(columnsTobeLeft[i]);
 		}
 		
         byte[] regionalTrainNameByteArray = reginolalTrainName.getBytes(charset);
         
         for (int i = 2; i < regionalTrainNameByteArray.length; i++) {
         	 
           	 byteList.add(regionalTrainNameByteArray[i]);
           	
           }
         
         for (int i = 0; i < gapCodeBytes.length; i++) {

  			byteList.add(gapCodeBytes[i]);

  		}
  		
  		for (int i = 0; i < columnsTobeLeft.length; i++) {

  			byteList.add(columnsTobeLeft[i]);
  		}
		
		  for (int i = 2; i < platformArray.length; i++) {

			byteList.add(platformArray[i]);

		}
		
		for (int i = 0; i < gapCodeBytes.length; i++) {

			byteList.add(gapCodeBytes[i]);

		}
		
		for (int i = 0; i < columnsTobeLeft.length; i++) {

			byteList.add(columnsTobeLeft[i]);
		}
		int terminationByte1 = 0xFF;

		int terminationByte2 = 0xFF;

		byteList.add((byte) terminationByte1);
		byteList.add((byte) terminationByte2);
				
        return byteList;

	}

	@Override
	public void sendMessage(Device device, String data, String speed, String effect, String letterSize) {

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
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize, int gap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sendCommand(String ipAddress, String command) {
		// TODO Auto-generated method stub
		try {

			byte[] packetData = tcpPacket.getCommandPacket(DeviceType.ivd.toString(),
					Command.GetConfiguration.toString(), ipAddress);

			commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

		} catch (HandledException e) {

			notificationService.sendNotification(" Time out for IVD with Ip Address " + ipAddress);
			System.out.println(e.getMessage() + " for IVD with Ip Address " + ipAddress);

		}
	}

}
