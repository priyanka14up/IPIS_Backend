
package com.innobitsysytems.ipis.utility;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.repository.setup.CoachDataRepository;
import com.innobitsysytems.ipis.repository.setup.EnableDisableBoardRepository;
import com.innobitsysytems.ipis.services.CommunicationService;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.threadmgnt.CommThread;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class CgdbUtil implements UtilityInterface {

	@Autowired
	private CommunicationService commService;

	@Autowired
	private DataUtil dataUtil;

	@Autowired
	private TcpPacketUtil tcpPacket;

	@Autowired
	private CommThread commThread;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	public CoachDataRepository coachDataRepository;

	@Value("${ipis-server-port}")
	private String port;

	@Autowired
	public EnableDisableBoardRepository enableDisableBoardRepository;
	
	@Override
	public void sendCommand( Device device, String command) {

		if (command.equals(Command.Stop.toString())) {

			List<CoachesDetail> coaches = device.getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(j);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));

				try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.Stop.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);

				}

			}

		} 
		else if (command.equals(Command.Start.toString())) {

			List<CoachesDetail> coaches = device.getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(j);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));

				commThread.pool.execute(new Runnable(){public void run(){try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.Start.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);

				} }});

			}

		}

		else if (command.equals(Command.SoftReset.toString())) {

			List<CoachesDetail> coaches = device.getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(j);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));

				try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.SoftReset.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


				}

			}

		} else if (command.equals(Command.DeleteAllData.toString())) {

			List<CoachesDetail> coaches = device.getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(j);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));

				commThread.pool.execute(new Runnable(){public void run(){try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.DeleteAllData.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


				} }});

			}

		} else if (command.equals(Command.GetConfiguration.toString())) {

			List<CoachesDetail> coaches = device.getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(j);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));

				try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.GetConfiguration.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


				}

			}

		} else if (command.equals(Command.OptionalLinkCheck.toString())) {

			List<CoachesDetail> coaches = device.getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(j);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));

				try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.OptionalLinkCheck.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


				}

			}

		}

	}

	public byte[] getTcpPacket(String data, int date) {

		byte[] res = new byte[]{(byte) DataUtil.hexToInt(data), (byte) date};

		return res;

	}

	public byte[] getData(String data, int date) {

		byte[] res = new byte[]{(byte) DataUtil.hexToInt(data), (byte) date};

		return res;

	}

	// To set intensity(CGDB)
	@Override
	public void sendConfiguration(List<Device> device, int data) {

		for (int i = 0; i < device.size(); i++) {

			List< CoachesDetail > coaches = device.get(i).getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(i);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));
				commThread.pool.execute(new Runnable(){public void run(){sendPackage(ipAddress, data);}});

			}

		}
	}




	public void sendPackage(String ipAddress, int data) {

		int defaultTime = 200;
		try {

			byte[] intesityData =  this.getIntensityConfigData(data, defaultTime);

			byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.cgdb.toString(), Command.SetConfiguration.toString(), ipAddress, intesityData);

			commService.sendData(packetData, ipAddress, Protocol.TCP.toString());

		} catch (HandledException e) {

			notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
			System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


		}

	}




	public byte[] getIntensityConfigData(int data, int time) {

		int  tempData = DataUtil.getIntensityHeader(data);

		String timeData = dataUtil.decimalToHex(time);  

		byte[] res = new byte[]{(byte)data, (byte)time };  //// look

		return res;

	}


	// To send Default message

	public void sendDefaultData(Device device, String[] data, String speed, String effect, String letterSize,int gap,int timeDelay) {


		List< CoachesDetail > coaches = device.getCoaches();

		for (int j = 0; j < coaches.size(); j++) {

			System.out.println(coaches.get(j));
			System.out.println("coaches deatil size"+coaches.size());

			Object obj = coaches.get(j);
			Map myMap =  (Map)obj; 
			String ipAddress = String.valueOf(myMap.get("ipAddress"));
			commThread.pool.execute(new Runnable(){public void run(){defaultDataPacket(ipAddress, data, speed, effect, letterSize,gap,timeDelay);}});

		}


	}

	private void defaultDataPacket(String ipAddress, String[] data, String speed, String effect, String letterSize,int gap,int timeDelay) {	

		short lColumn = 1, rColumn = 1, tRow = 1, bRow = 1;

		System.out.println("defaultDataPacket");

		try {

			//    		System.out.println("stop command packet");
			//    		byte[] stopCommand=tcpPacket.getCommandPacket((DeviceType.cgdb.toString()), Command.Stop.toString(), ipAddress);

			byte[] configData = this.getDefaultData( lColumn,  rColumn,  tRow,  bRow, data, speed, effect, letterSize,gap,timeDelay);
			System.out.println("byteArrayList-----");
			System.out.println("lColumn,rColumn,tRow,bRow,speed(rev video+not used+speed),effectcode(effect code+not used),lettersize(not used+letter size+gap),timedelay,startAddress,trainNo,gapcode,trainName,gapcode,time,gapcode,ad,gapcode, platform,gapcode,termination byte1,termination byte2");

			for(int i=0 ;i<configData.length;i++) {
				System.out.print(configData[i]+",");
			}
			System.out.println();
			System.out.println();
			System.out.println();

			System.out.println("311");
			
			 List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
	            if(enableDiable.get(0).getCgdb().equals("enable"))
	            {
	           	  
	            	byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.cgdb.toString(), Command.DefaultDataPacket.toString(), ipAddress, configData);


	    			//   			 System.out.println("start command packet");
	    			//   	       	 byte[] startCommand=tcpPacket.getCommandPacket((DeviceType.cgdb.toString()), Command.Start.toString(), ipAddress);
	    			commService.sendData(packetData, ipAddress, Protocol.TCP.toString(),Integer.parseInt(port));
	            }
	            else
	            {
	           	 throw new HandledException("Cgdb devices are disabled","Disabled");
	           	 
	            }

		} catch (Exception e) {

			notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
			System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


		}

	}

	private byte[] getDefaultData(short lColumn,short rColumn,short tRow,short bRow, String[] data, String speedValue, String effect, String letterSizeValue,int gap,int timeDelay) throws IOException {
		System.err.println(" inside default data");
		System.out.println("in get default data");

		List<Byte> res=new ArrayList<Byte>(); 	 
		res.add((byte)lColumn);
		res.add((byte)rColumn);
		res.add((byte)tRow);
		res.add((byte)bRow);

		//Speed 

		int rev=0;
		int speed =dataUtil.getSpeed(speedValue);

		System.out.println("speed from front"+speedValue);
		System.out.println("effectcode from front"+effect);
		System.out.println("lettersize from front"+letterSizeValue);
		System.out.println("gap from front"+gap);
		byte speed8 =(byte)(((rev &0X1 )<<7) | (speed & 0x07)) ;


		System.out.println("final speed----------------->"+speed8);
		res.add(speed8);

		//effectcode

		int effectCode = dataUtil.getEffectCode(effect);
		byte effect8 =(byte) (effectCode & 0x0f) ;


		System.out.println("final effect----------------->"+effect8);
		res.add(effect8);  


		//letter Size

		int letterSize = dataUtil.getFont(letterSizeValue);
		int gapFinal = dataUtil.getGap(gap);

		byte letter8 =(byte)(((letterSize & 0x07)<<3)|(gapFinal & 0x07)) ;


		System.out.println("final letter----------------->"+letter8);
		res.add(letter8);  


		res.add((byte)timeDelay)  ;   

		List<Byte> byteList = new ArrayList<Byte>();

		for(int i = 0; i < res.size(); i++) {

			byteList.add(res.get(i));

		}

		int gapCode=0Xe700;
		System.out.println("binary of 0xe700------>"+Integer.toBinaryString(gapCode));
		byte[] gCode=new byte[2];

		short a=(short)(gapCode & 0Xff);
		short b=(short)((gapCode >>8) & 0xff);

		gCode[0]= (byte)((gapCode >>8) & 0xff);
		gCode[1]= (byte)(gapCode & 0Xff);

		Charset charset = StandardCharsets.UTF_16;

		
		
		
		for(int i=0;i<data.length/2;i++)
		{
			System.out.println("data[i]"+data[i]);
			byte[] dataBytesArrray = data[i].getBytes(charset);
			for(int j=2;j<dataBytesArrray.length;j++)
			{
				byteList.add(dataBytesArrray[j]);
			}
			if(i==0)
			{
				byteList.add(gCode[0]);
				byteList.add(gCode[1]);
			}
			
//			boolean isInserted = true;
//			if(isInserted) {
//				byteList.add(gCode[0]);
//				byteList.add(gCode[1]);
//				isInserted = false;
//			}

		}	

		int endData = 0xEC;

		byteList.add((byte)endData);


		System.out.println("data packet size"+byteList.size());

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream out = new DataOutputStream(baos);

		for (byte element : byteList) {

			out.writeByte(element);

		}

		byte[] bytes = baos.toByteArray();

		return bytes;


	}

	//To send CGDB data
	@Override
	public void sendCgdbData(Device device, String trainNo, String[] coachDetails, String speed, String letterSize, String effectCode,int gap,int timeDelay) {



		List< CoachesDetail > coaches = device.getCoaches();
		int size=coaches.size();
		System.out.println();
		System.out.println("Created CGDB size"+size);
		System.out.println();

		for (int j = 0; j < coaches.size(); j++) {

			Object obj = coaches.get(j);
			Map myMap =  (Map)obj; 
			String ipAddress = String.valueOf(myMap.get("ipAddress"));
			String coachValue = coachDetails[j];
			System.out.println("ipaddress"+ipAddress);
			System.out.println("online train coach="+coachValue);
			System.out.println();
			System.out.println("Created CGDB details"+coaches.get(j));
			System.out.println();

			//String coachValue = coachDetails[1];  // to see  		 

			commThread.pool.execute(new Runnable(){public void run(){dataCgdb(ipAddress, trainNo, coachValue, speed, letterSize, effectCode,gap,timeDelay);}});
		}
	}	

	private void dataCgdb(String ipAddress, String trainNo, String coachDetails, String speed, String letterSize, String effectCode,int gap,int timeDelay){


		System.out.println("ipaddress on which we are sending data------------------"+ipAddress);


		short lColumn = 1, rColumn = 1, tRow = 1, bRow = 1;

		try {
			System.out.println("stop command packet");
			byte[] stopCommand=tcpPacket.getCommandPacket((DeviceType.cgdb.toString()), Command.Stop.toString(), ipAddress);


			commService.sendCommand(stopCommand, ipAddress, Protocol.UDP.toString());

			Thread.sleep(100);


			byte[] cgdbData = this.getCgdbData( lColumn,  rColumn,  tRow,  bRow, trainNo, coachDetails, speed, letterSize, effectCode,gap,timeDelay);  
			System.out.println("byteArrayList-----");
			System.out.println("lColumn,rColumn,tRow,bRow,speed(rev video+not used+speed),effectcode(effect code+not used),lettersize(not used+letter size+gap),timedelay,startAddress,trainNo,gapcode,trainName,gapcode,time,gapcode,ad,gapcode, platform,gapcode,termination byte1,termination byte2");;

//			for(int i=0 ;i<cgdbData.length;i++) {
//				System.out.print(cgdbData[i]+",");
//			}
//			System.out.println();
//			System.out.println();
//			System.out.println();

		
			
			 List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
	            if(enableDiable.get(0).getCgdb().equals("enable"))
	            {
	           	  
	            	byte[] cgdbPacket= tcpPacket.getConfigDataPacket((DeviceType.cgdb.toString()), Command.DataTransfer.toString(), ipAddress, cgdbData);
	    			commService.sendData(cgdbPacket, ipAddress, Protocol.TCP.toString());
	            }
	            else
	            {
	           	 throw new HandledException("Cgdb devices are disabled","Disabled");
	           	 
	            }

			Thread.sleep(100);

			System.out.println("start command packet");

			byte[] startCommand=tcpPacket.getCommandPacket((DeviceType.cgdb.toString()), Command.Start.toString(), ipAddress);
			commService.sendCommand(startCommand, ipAddress, Protocol.TCP.toString());       
		} catch (Exception e) {

			notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
			System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);

		}

	}

	private byte[] getCgdbData(short lColumn,short rColumn,short tRow,short bRow, String trainNo, String coachDetails, String speedValue, String letterSizeValue, String effectCodeValue,int gap,int timeDelay) throws IOException {



		List<Byte> res=new ArrayList<Byte>(); 	 
		res.add((byte)lColumn);
		res.add((byte)rColumn);
		res.add((byte)tRow);
		res.add((byte)bRow);

		//Speed 

		int rev=0;
		int speed =dataUtil.getSpeed(speedValue);

		System.out.println("speed from front"+speedValue);
		System.out.println("effectcode from front"+effectCodeValue);
		System.out.println("lettersize from front"+letterSizeValue);
		System.out.println("gap from front"+gap);
		byte speed8 =(byte)(((rev &0X1 )<<7) | (speed & 0x07)) ;


		System.out.println("final speed----------------->"+speed8);
		res.add(speed8);

		//effectcode

		int effectCode = dataUtil.getEffectCode(effectCodeValue);
		byte effect8 =(byte) (effectCode & 0x0f) ;


		System.out.println("final effect----------------->"+effect8);
		res.add(effect8);  


		//letter Size

		int letterSize = dataUtil.getFont(letterSizeValue);
		int gapFinal = dataUtil.getGap(gap);

		byte letter8 =(byte)(((letterSize & 0x07)<<3)|(gapFinal & 0x07)) ;


		System.out.println("final letter----------------->"+letter8);
		res.add(letter8);  

		//int timeDelay = 0x14;

		res.add((byte)timeDelay)  ;   


		Charset charset = StandardCharsets.UTF_16;
		byte[] trainNoArrray = trainNo.getBytes(charset);


		for(int i=2;i<trainNoArrray.length;i++)
		{
			res.add((byte)trainNoArrray[i]);
		}

		List<Byte> byteList = new ArrayList<Byte>();

		for(int i = 0; i < res.size(); i++) {

			byteList.add(res.get(i));

		}


		System.out.println("bytes of single coach-------------------"+coachDetails);

		byte[] coachArray = coachDetails.getBytes(charset);
		for(int i=2;i<coachArray.length;i++) {

			byteList.add(coachArray[i]);
		}
		System.out.println();

		// int fieldSeparator = 0xE700;

		int gapCode=0Xe700;

		byte[] gCode=new byte[2];

		gCode[0]= (byte)((gapCode >>8) & 0xff);
		gCode[1]=(byte)(gapCode & 0Xff);

		short columnLeft=3;

		byte[] columnsTobeLeft=new byte[2];
		columnsTobeLeft[0]= (byte)(columnLeft>>8);
		columnsTobeLeft[1]=(byte)columnLeft;

		byteList.add(gCode[0]);
		byteList.add(gCode[1]);

		//	  byteList.add(columnsTobeLeft[0]);
		//	  byteList.add(columnsTobeLeft[1]);
		//  	 	

		//byteList.add((byte)fieldSeparator);

		List<CoachData> coachData=coachDataRepository.findAll();
		//CoachData coachData=coachDataRepository.findByEngCoachName(coachDetails);
		String hindiCoachName="";
		for(int i=0;i<coachData.size();i++)
		{
			if(coachData.get(i).getEngCoachName().equals(coachDetails))
			{
				hindiCoachName=coachData.get(i).getHindiCoachName();
			}
		}

		byte[] hindiCoachArray = hindiCoachName.getBytes(charset);

		for(int i=2;i<hindiCoachArray.length;i++) {
			System.out.print(hindiCoachArray[i]+",");
			byteList.add(hindiCoachArray[i]);
		}
		System.out.println();


		int endData = 0xEC;

		byteList.add((byte)endData);

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

	//    @Override
	//    public void sendDefaultData(List <Device> device, String data, String speed, String effect, String letterSize) {
	//        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
	//    }

	@Override
	public void sendMessage(Device device, String data, String speed, String effect, String letterSize) {
		// TODO Auto-generated method stub

	}

	@Override
	public void displayTadb(List<Device> device, List<OnlineTrain> onlinetrain, String speed, String effect,
			String letterSize) {
		// TODO Auto-generated method stub

	}

	//	@Override
	//	public void sendDefaultData(List<Device> device, String[] data, String speed, String effect, String letterSize) {
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
			String effectCode) {
		// TODO Auto-generated method stub

	}

	@Override
	public void sendConfiguration(List<Device> device, int intensity, int intDisplayTimeCgdb) {
		// TODO Auto-generated method stub

		for (int i = 0; i < device.size(); i++) {

			List< CoachesDetail > coaches = device.get(i).getCoaches();

			for (int j = 0; j < coaches.size(); j++) {

				Object obj = coaches.get(i);
				Map myMap =  (Map)obj; 
				String ipAddress = String.valueOf(myMap.get("ipAddress"));
				System.out.println("ipaddress"+ipAddress);
				commThread.pool.execute(new Runnable(){public void run(){sendPackage(ipAddress, intensity,intDisplayTimeCgdb);}});

			}

		}

	}

	@Override
	public void sendPackage(String ipAddress, int intensity, int intDisplayTimeCgdb) {
		// TODO Auto-generated method stub
		//String color = "#00000";
		//int time = 300;

		try {
			System.out.println("send package");
			//byte[] configData = this.getConfigData(data, time, color, color, color, color, color, color, color, color,color);

			byte[] configData = this.getConfigData(intensity, intDisplayTimeCgdb);

			byte[] packetData = tcpPacket.getConfigDataPacket(DeviceType.cgdb.toString(),
					Command.SetConfiguration.toString(), ipAddress, configData);

			commService.sendData(packetData, ipAddress, Protocol.TCP.toString());

		} catch (Exception e) {

			notificationService.sendNotification(" Time out for CGDB with Ip Address " + ipAddress);

			System.out.println(e.getMessage() + " for CGDB with Ip Address " + ipAddress);

		}
	}

	private byte[] getConfigData(int data, int time) {

		int intensity=dataUtil.getIntensityHeader(data);


		byte[] res = new byte[] { (byte) intensity, (byte) time};

		return res;

	}
	@Override
	public void sendConfigurationManualSingle(String ipAddress, int intensity, int intDisplayTimeCgdb) {
		// TODO Auto-generated method stub
		commThread.pool.execute(new Runnable() {
			public void run() {
				sendPackage(ipAddress, intensity,intDisplayTimeCgdb);
			}
		});
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
			String effectCode, int gap) {
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
	public void sendCommand(String ipAddress, String command) {
		// TODO Auto-generated method stub
		 if (command.equals(Command.GetConfiguration.toString())) {

		
				
				try {

					byte[] packetData = tcpPacket.getCommandPacket(DeviceType.cgdb.toString(), Command.GetConfiguration.toString(), ipAddress);

					commService.sendCommand(packetData, ipAddress, Protocol.TCP.toString());

				} catch (HandledException e) {

					notificationService.sendNotification(" Time out for CGDB with Ip Address "+ipAddress);
					System.out.println(e.getMessage()+" for CGDB with Ip Address "+ipAddress);


				}

			
		}
	}

	//	@Override
	//	public void sendDefaultData(Device device, String[] data, String speed, String effect, String letterSize) {
	//		// TODO Auto-generated method stub
	//		
	//	}



}