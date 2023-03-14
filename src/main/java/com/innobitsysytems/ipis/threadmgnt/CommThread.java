/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: CommThread
 */
package com.innobitsysytems.ipis.threadmgnt;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.LinkStatus;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.BoardSetting;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.setup.BoardSettingRepository;
import com.innobitsysytems.ipis.repository.LinkStatusRepository;
import com.innobitsysytems.ipis.utility.*;

@Component
public class CommThread extends Thread {
	
	 @Autowired
	 private CgdbUtil cgdbUtil;
	 
	 @Autowired
	 private IvdUtil ivdUtil;
	 
	 @Autowired
	 private OvdUtil ovdUtil;
	 
	 @Autowired
	 private MldbUtil mldbUtil;
	 
	 @Autowired
	 private AgdUtil agdUtil;
	 
	 @Autowired
	 private PfdUtil pfdUtil;
	 
	 @Autowired
	 private TvUtil tvUtil;
	 
	 @Autowired
	 private PdcUtil pdcUtil;
	 
	 @Autowired
	 private DeviceRepository deviceRepository;
	 
	 @Autowired
	 private BoardSettingRepository boardSettingRepo;

	 @Autowired
	 private LinkStatusRepository linkStatusRepo;
	 
	 @Autowired 
	 CommonUtil commonUtil;
	 
	 Date date = new Date();
	
	String url, data, device, id, speed, effect, letterSize,mode,deviceId;
	Integer dayIntensity,nightIntensity,intensity;
	String dayIntensityTime,nightIntensityTime,boardType="all";
	String intensityMode="",pf="";
	DeviceType deviceType;
	
	int gap,timeDelay;
	String ip ="192.168.";
	String[] platform = new String[2];
	String[] dataArray = new String[4];
	List<OnlineTrain> onlineTrain = new ArrayList<OnlineTrain>();
	Device deviceData = new  Device();
	Message messageDetails = new  Message();
	
	public ExecutorService pool  = Executors.newCachedThreadPool();
	
	CommThread(){}
	
	// To set configuration for pdc
	public CommThread(String url) {
		
		this.url = url;
	}
	
	// To set Default Message
	public CommThread(String data, String url, String device, String platformNo, String speed, String effect, String letterSize,int gap,int timeDelay) {
		System.out.println("in comm thread");
		this.data = data;
		this.url = url;
		this.device = device;
		platform[0] = platformNo;
		this.speed = speed;
		this.effect = effect;
		this.letterSize = letterSize;
		this.gap=gap;
		this.timeDelay=timeDelay;
	}
	
	// To set Default Message for CGDB
    public CommThread(String[] data, String url, String boardType, String platformNo, String speed, String effect, String letterSize,int gap,int timeDelay) {
		System.out.println("in comm thread of CGDB constructor");
	    dataArray = data;
		this.url = url;
		device = boardType;
		platform[0] = platformNo;
		this.speed = speed;
		this.effect = effect;
		this.letterSize = letterSize;
	      this.gap=gap;
	      this.timeDelay=timeDelay;
	
	}
  
    // To set Message
    public CommThread(Message messageDetails, String url, String device, String platformNo, String deviceId, String speed, String effect, String letterSize,int gap,int timeDelay) {
	
		this.url = url;
		//this.data = data;
		this.messageDetails=messageDetails;
		this.device = device;
		platform[0] = platformNo;
		id = deviceId;
		this.speed = speed;
		this.effect = effect;
		this.letterSize = letterSize;
		ip = ip.concat(platformNo+"."+deviceId);
	      this.gap=gap;
	      this.timeDelay=timeDelay;
	     
	
	}
	
    // To set intensity(auto)
	public CommThread(Integer data,String url,String intensityMode) {
		
		
		this.data = String.valueOf(data);
		this.url = url;
		this.intensityMode=intensityMode;
		
	
	}
	
	// To Set Intensity(Manual)
	public CommThread(Integer intensity, String url, String mode, String intensityMode) {
		
		//this.data = String.valueOf(data);
		this.intensity=intensity;
		this.url = url;
		this.mode = mode;
		this.intensityMode=intensityMode;
		//platform[0] = platformNo;
	
	}
	
	// To Set Intensity(Manual)single
		public CommThread( Integer intensity ,String url,String device,String deviceId, String platformNo,String mode,String intensityMode) {
			
		    this.intensity = intensity;
			this.url = url;
			this.device = device;
			this.deviceId=deviceId;
			this.mode=mode;
			this.intensityMode=intensityMode;
			platform[0] = platformNo;
		
		}
		
	
	//For link Check
	public CommThread(String url, Device device) {
		
		this.url = url;
		this.deviceData = device;

	}
	
	//To display CGDB Data
	public CommThread(String url, List<OnlineTrain> onlineTrain, String speed, String letterSize, String effectCode,int gap,int timeDelay) {
		
		this.url = url;
		this.speed = speed;
		this.letterSize = letterSize;
		this.effect = effectCode;
		this.onlineTrain = onlineTrain;
	      this.gap=gap;
	      this.timeDelay=timeDelay;
		
	}
	
	// For Tadb
	public CommThread(List<OnlineTrain> onlineTrain, String url) {
		
	    this.onlineTrain = onlineTrain;
		this.url = url;
		
	}
	
	// For softreset
		public CommThread(String boardType, String url) {
			
		    this.boardType = boardType;
			this.url = url;
			
		}
		
		// For getConfig
				public CommThread(String boardType,String pf,String deviceId,String url) {
					
				    this.boardType = boardType;
				    this.pf=pf;
					this.url = url;
					this.deviceId=deviceId;
					
				}

		
		
	public void run() 
	{
		System.out.println("run");
		BoardSetting boardSettingMldb = boardSettingRepo.findByBoardType(DeviceType.mldb.toString());		
		String displayTimeMldb = boardSettingMldb.getDisplayTime();
		int intDisplayTimeMldb = Integer.parseInt(displayTimeMldb);
		
		BoardSetting boardSettingPfdb = boardSettingRepo.findByBoardType(DeviceType.pfdb.toString());		
		String displayTimePfdb = boardSettingPfdb.getDisplayTime();
		int intDisplayTimePfdb = Integer.parseInt(displayTimePfdb);
		
		BoardSetting boardSettingAgdb = boardSettingRepo.findByBoardType(DeviceType.agdb.toString());		
		String displayTimeAgdb = boardSettingAgdb.getDisplayTime();
		int intDisplayTimeAgdb = Integer.parseInt(displayTimeAgdb);
		
		BoardSetting boardSettingCgdb = boardSettingRepo.findByBoardType(DeviceType.cgdb.toString());		
		String displayTimeCgdb = boardSettingCgdb.getDisplayTime();
		int intDisplayTimeCgdb = Integer.parseInt(displayTimeCgdb);
		
		BoardSetting boardSettingIvd = boardSettingRepo.findByBoardType(DeviceType.ivd.toString());		
		String displayTimeIvd = boardSettingIvd.getDisplayTime();
		int intDisplayTimeIvd = Integer.parseInt(displayTimeIvd);
		
		BoardSetting boardSettingOvd = boardSettingRepo.findByBoardType(DeviceType.ovd.toString());		
		String displayTimeOvd = boardSettingOvd.getDisplayTime();
		int intDisplayTimeOvd = Integer.parseInt(displayTimeOvd);
		

		List <Device> cgdbData = deviceRepository.findAllBydeviceType(DeviceType.cgdb);
		List <Device> ivdData = deviceRepository.findAllBydeviceType(DeviceType.ivd);
		List <Device> ovdData = deviceRepository.findAllBydeviceType(DeviceType.ovd);
		List <Device> mldbData = deviceRepository.findAllBydeviceType(DeviceType.mldb);
		List <Device> agdbData = deviceRepository.findAllBydeviceType(DeviceType.agdb);
		List <Device> pfdbData = deviceRepository.findAllBydeviceType(DeviceType.pfdb);
		
		 if(url.equals(RequestTypes.SET_INTENSITY.toString())&&(intensityMode.equals("Auto")))
		{
			System.out.println("in run auto case");
			int intensity = Integer.parseInt(data);
						
			cgdbUtil.sendConfiguration(cgdbData,intensity,intDisplayTimeCgdb); 
			//ivdUtil.sendConfiguration( ivdData, intensity,intDisplayTimeIvd);
			mldbUtil.sendConfiguration(mldbData, intensity,intDisplayTimeMldb);
			agdUtil.sendConfiguration(agdbData, intensity,intDisplayTimeAgdb);
			pfdUtil.sendConfiguration(pfdbData, intensity,intDisplayTimePfdb);
		}
		 else if(url.equals(RequestTypes.SET_INTENSITY.toString())&&(intensityMode.equals("Manual"))) {

				
				if(mode.equals("Single"))
				{
					
					switch(device){  
				     
				    case "cgdb": 
				    	
				    	List <Device> dataCgdb = deviceRepository.getAllBydeviceType(DeviceType.cgdb, platform);
				    	
				    	 ip=ip.concat(".").concat(platform[0]).concat(".").concat(deviceId);
				    	cgdbUtil.sendConfigurationManualSingle(ip, intensity,intDisplayTimeCgdb);
				    	break;  
			    	
				    case "ivd": 
			    
			    	//List <Device> dataIvd = deviceRepository.getAllBydeviceType(DeviceType.ivd, platform);
				    //	ivdUtil.sendConfigurationManualSingle( dataIvd, intData);
			    	break;  
//				    	
//				    case "ovd": 
//				    	
//				    	List <Device> dataOvd = deviceRepository.getAllBydeviceType(DeviceType.ovd, platform);
//				    	ovdUtil.sendConfiguration(dataOvd, intData);
//				    	break;  
				    	
				    case "mldb": 
				    	List <Device> dataMldb = deviceRepository.getAllBydeviceType(DeviceType.mldb, platform);
				    	 ip=ip.concat(".").concat(platform[0]).concat(".").concat(deviceId);
				    	mldbUtil.sendConfigurationManualSingle(ip, intensity,intDisplayTimeMldb);
				    	
				    	break;
				    	
				    case "agdb": 
				    
				    	List <Device> dataAgdb = deviceRepository.getAllBydeviceType(DeviceType.agdb, platform);
				    	 ip=ip.concat(".").concat(platform[0]).concat(".").concat(deviceId);
				    	agdUtil.sendConfigurationManualSingle(ip, intensity,intDisplayTimeAgdb);
				    	break;
				    	
				    case "pfdb": 
				    	
				    	List <Device> dataPfdb = deviceRepository.getAllBydeviceType(DeviceType.pfdb, platform);
				    	 ip=ip.concat(".").concat(platform[0]).concat(".").concat(deviceId);
				    	pfdUtil.sendConfigurationManualSingle(ip, intensity,intDisplayTimePfdb);
				    	break;
				    
				    default:	
				    }  //end of switch
				}
				else
				{
				
					
					
					cgdbUtil.sendConfiguration( cgdbData,intensity,intDisplayTimeCgdb);
					//ivdUtil.sendConfiguration( ivdData, intensity,intDisplayTimeIvd);
					//ovdUtil.sendConfiguration(ovdData, intensity);
					mldbUtil.sendConfiguration(mldbData, intensity, intDisplayTimeMldb);
					agdUtil.sendConfiguration(agdbData, intensity,intDisplayTimeAgdb);
					pfdUtil.sendConfiguration(pfdbData, intensity,intDisplayTimePfdb);
					
				}
					
				
			}
		else if( url == RequestTypes.SET_CONFIGURATION.toString() ) {

			 List <Device> pdcData = deviceRepository.findAllBydeviceType(DeviceType.pdc);
			 pdcUtil.sendConfiguration(pdcData);
			
		}else if( url == RequestTypes.DEFAULT_MSG.toString() ) {
			System.out.println("in default msg run");
			
			switch((device) ){  		
		    case "cgdb": 
		    	System.out.println("in cgdb case");
		    	
		    	
		    	List<Device> cgdbDevices=deviceRepository.findAllBydeviceType(DeviceType.cgdb);
		    	
		    	System.out.println("cgdbDevices----"+cgdbDevices);
		    	
		    	for(int i=0;i<cgdbDevices.size();i++)
		    	{
		    		System.out.println("cgdb size"+cgdbDevices.size());
		    		System.out.println("cgdb platform no"+cgdbDevices.get(i).getPlatformNo()[0]);
		    		if(cgdbDevices.get(i).getPlatformNo()[0].equals(platform[0]))
		    		{
		    			System.out.println(cgdbDevices.get(i).getCoaches());
		    			cgdbDevices.get(i).getCoaches();
		    			cgdbUtil.sendDefaultData(cgdbDevices.get(i), dataArray, speed, effect, letterSize,gap,timeDelay);
		    		}
		    		
		    	}
		    	
		    	break;  
		    	
		    case "mldb": 
		    	List<Device> res2=new ArrayList<Device>();
		    	System.out.println("in mldb case"); 
		    	
		    	List<Device> mldbDevices=deviceRepository.findAllBydeviceType(DeviceType.mldb);
		    	
		    	for(int i=0;i<mldbDevices.size();i++)
		    	{
	
		    		String ip=mldbDevices.get(i).getIpAddress();
		    		
		    		String[] ipAddress = ip.split("[, . ']+");
		    		
		    		if(platform[0].equals(ipAddress[2]))
		    		{
		    			Device dev1=deviceRepository.findByIpAddress(ip);
		    			
		    			System.out.println("checked devices==="+dev1);
		    			
		    			res2.add(dev1);
		    		}
		    		
		    	}
		    	
		    	for(int i=0;i<res2.size();i++)
		    	{
		    		System.out.println(res2.get(i).getIpAddress());
		    	}
		    	
		    	mldbUtil.sendDefaultData(res2, data, speed, effect, letterSize,gap,timeDelay);
		    	
		    	break;
		    	
		    case "agdb": 
		    	List<Device> res=new ArrayList<Device>();
		    	
		    	
		    	List<Device> agdbDevices=deviceRepository.findAllBydeviceType(DeviceType.agdb);
		    	
		    	for(int i=0;i<agdbDevices.size();i++)
		    	{
		    		String ip=agdbDevices.get(i).getIpAddress();
		    		String[] ipAddress = ip.split("[, . ']+");
		    		if(platform[0].equals(ipAddress[2]))
		    		{
		    			Device dev1=deviceRepository.findByIpAddress(ip);
		    			
		    			res.add(dev1);
		    		}
		    		
		    	}
		    	
		    	agdUtil.sendDefaultData(res, data, speed, effect, letterSize,gap,timeDelay);
		    	
		    	break;
		    	
		    case "pfdb": 
		    	
		    	List<Device> res1=new ArrayList<Device>();
		    	
		    	
		    	List<Device> pfdbDevices=deviceRepository.findAllBydeviceType(DeviceType.pfdb);
		    	
		    	for(int i=0;i<pfdbDevices.size();i++)
		    	{
		    		String ip=pfdbDevices.get(i).getIpAddress();
		    		
		    		String[] ipAddress = ip.split("[, . ']+");
		    		
		    		if(platform[0].equals(ipAddress[2]))
		    		{
		    			Device dev1=deviceRepository.findByIpAddress(ip);
		    			
		    			res1.add(dev1);
		    		}
		    		
		    	}
		    	
		    	pfdUtil.sendDefaultData(res1, data, speed, effect, letterSize,gap,timeDelay);
		    	
		    	break;
		    	
		    case "ovd": 
		    	
		    	List<Device> resOvd=new ArrayList<Device>();
		    	
		    	
		    	List<Device> ovdDevices=deviceRepository.findAllBydeviceType(DeviceType.ovd);
		    	
		    
		    	for(int i=0;i<ovdDevices.size();i++)
		    	{
		    		String ip=ovdDevices.get(i).getIpAddress();
		    		
		    		String[] ipAddress = ip.split("[, . ']+");
		    		
		    		if(platform[0].equals(ipAddress[2]))
		    		{
		    			Device dev1=deviceRepository.findByIpAddress(ip);
		    			
		    			resOvd.add(dev1);
		    		}
		    		
		    	}
		    	
		    	ovdUtil.sendDefaultData(resOvd, data, speed, effect, letterSize,gap);
		    	
		    	break;
		    	
		    default:
		    	
		    	break;
				
		    }  
		}
		else if( url == RequestTypes.MessageDataPacket.toString() ) {
			
			Device deviceData = null;
			
			try {
				
				deviceData = deviceRepository.findByIpAddress(ip);
				
			}catch(Exception e) {
				
				System.out.println(e);
			}
			
			
			switch((device) ){  
		     
			case "mldb": 
				
				
		    	mldbUtil.sendMessage(ip, messageDetails, speed, effect, letterSize,gap,timeDelay);
		    	
		    	break;
		    	
		    case "agdb": 
		    	
		    	agdUtil.sendMessage(ip, messageDetails, speed, effect, letterSize,gap,timeDelay);
		    	break;
		    	
		    case "pfdb": 
		    	
		    	pfdUtil.sendMessage(ip, messageDetails, speed, effect, letterSize,gap,timeDelay);
		    	
		    	break;
		    	
		    default:
		    	
		    	break;
				
		    }  
		
			
		}else if( url == RequestTypes.TADB.toString() ) {
				
			List<Device> mldbDevice = deviceRepository.findAllBydeviceType(DeviceType.mldb);
			List<Device> agdbDevice = deviceRepository.findAllBydeviceType(DeviceType.agdb);
			List<Device> pfdbDevice = deviceRepository.findAllBydeviceType(DeviceType.pfdb);
			List<Device> ivdDevice = deviceRepository.findAllBydeviceType(DeviceType.ivd);
			List<Device> ovdDevice = deviceRepository.findAllBydeviceType(DeviceType.ovd);
			
 					
			String speedMldb = boardSettingMldb.getSpeed();
			String effectMldb = boardSettingMldb.getEffect();
			String letterSizeMldb = boardSettingMldb.getLetterSize();
			int gap = boardSettingMldb.getCharacterGap();
			int timeDelayMldb=boardSettingMldb.getPageChangeTime();
			pool.execute(new Runnable(){public void run(){mldbUtil.displayTadb(mldbDevice, onlineTrain, speedMldb, effectMldb, letterSizeMldb,gap,timeDelayMldb);}});

			
			String speedAgdb = boardSettingAgdb.getSpeed();
			String effectAgdb = boardSettingAgdb.getEffect();
			String letterSizeAgdb = boardSettingAgdb.getLetterSize();
			int gapAgdb=boardSettingAgdb.getCharacterGap();
			int timeDelayAgdb=boardSettingAgdb.getPageChangeTime();
			pool.execute(new Runnable(){public void run(){agdUtil.displayTadb(agdbDevice, onlineTrain, speedAgdb, effectAgdb, letterSizeAgdb,gapAgdb,timeDelayAgdb);}});
			
			
			String speedPfdb = boardSettingPfdb.getSpeed();
			String effectPfdb = boardSettingPfdb.getEffect();
			String letterSizePfdb = boardSettingPfdb.getLetterSize();
			int gapPfdb=boardSettingPfdb.getCharacterGap();
			int timeDelayPfdb=boardSettingPfdb.getPageChangeTime();
			pool.execute(new Runnable(){public void run(){pfdUtil.displayTadb(pfdbDevice, onlineTrain, speedPfdb, effectPfdb, letterSizePfdb,gapPfdb,timeDelayPfdb);}});
			
			
			String speedIvd = boardSettingIvd.getSpeed();
			String effectIvd = boardSettingIvd.getEffect();
			String letterSizeIvd = boardSettingIvd.getLetterSize();
			int gapIvd=boardSettingIvd.getCharacterGap();		
			int timeDelayIvd=boardSettingIvd.getPageChangeTime();
			pool.execute(new Runnable(){public void run(){ivdUtil.displayTadb(ivdDevice, onlineTrain, speedIvd, effectIvd, letterSizeIvd,gapIvd,timeDelayIvd);}});
			
			
			String speedOvd = boardSettingOvd.getSpeed();
			String effectOvd = boardSettingOvd.getEffect();
			String letterSizeOvd = boardSettingOvd.getLetterSize();
			int gapOvd=boardSettingOvd.getCharacterGap();		
			int timeDelayOvd=boardSettingOvd.getPageChangeTime();
			pool.execute(new Runnable(){public void run(){ovdUtil.displayTadb(ovdDevice, onlineTrain, speedOvd, effectOvd, letterSizeOvd,gapOvd,timeDelayOvd);}});
			
		}else if( url == RequestTypes.CGS.toString() ) {
			
			List<Device> cgdbDevice = deviceRepository.findAllBydeviceType(DeviceType.cgdb);
			
			for( int i = 0; i < onlineTrain.size(); i++) {
				
				for(int j = 0; j < cgdbDevice.size(); j++) {
					
					Device cgdbValue = cgdbDevice.get(j);
					
    				if ( cgdbDevice.get(j).getPlatformNo()[0].equals(onlineTrain.get(i).getPlatformNo())) {
    					
    					cgdbUtil.sendCommand(cgdbValue, Command.DeleteAllData.toString());
    					
    				}
					
				}
				
			}
			
		}else if( url == RequestTypes.CGDB.toString() ) 
		{
			
			List<Device> deviceInfo = deviceRepository.findAllBydeviceType(DeviceType.cgdb);
			
			List<CoachesDetail> coachDetails = new ArrayList<CoachesDetail>();
			
			for (int i = 0; i < deviceInfo.size(); i++) {
			
				String devicePlatformNo = deviceInfo.get(i).getPlatformNo()[0];
				
				//coachDetails.addAll(deviceInfo.get(i).getCoaches());
				
				for(int j = 0; j < onlineTrain.size(); j++){
					
					String trainNumber = String.valueOf(onlineTrain.get(j).getTrainNumber());
					
					String[] coaches = onlineTrain.get(j).getCoaches();
				
					String onlineTrainPlatformNo = String.valueOf(onlineTrain.get(j).getPlatformNo());
					
					if(devicePlatformNo.equals(onlineTrainPlatformNo)){
			
						cgdbUtil.sendCgdbData(deviceInfo.get(i) ,trainNumber, coaches, speed, letterSize, effect,gap,timeDelay);
						
					}
					
				}
				
			}
			
		}else if( url == RequestTypes.OptionalLinkCheck.toString() ) {
			
			pool.execute(new Runnable(){public void run(){
				
				DeviceType deviceType = deviceData.getDeviceType();
				
				switch (deviceType.toString()){
				
				case "cgdb" :
					
					cgdbUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;				
					
				case "agdb":
			       
					agdUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;
						 
				case "pfdb":
					
					pfdUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;
					
				case "mldb":
					
					mldbUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;
					
				case "ivd":
					
					ivdUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;
					
				case "ovd":
					
					ovdUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;
					
				case "pdc":
					
					pdcUtil.sendCommand(deviceData, RequestTypes.OptionalLinkCheck.toString());
					break;	
					
				default:
					
					break;
						
				}
				
			}});
			
		}
		 
		else if( url == Command.SoftReset.toString() )
		{
			List <Device> dataCgdb = deviceRepository.findAllBydeviceType(DeviceType.cgdb);
			List <Device> dataIvd = deviceRepository.findAllBydeviceType(DeviceType.ivd);
			List <Device> dataOvd = deviceRepository.findAllBydeviceType(DeviceType.ovd);
			List <Device> dataMldb = deviceRepository.findAllBydeviceType(DeviceType.mldb);
			List <Device> dataAgdb = deviceRepository.findAllBydeviceType(DeviceType.agdb);
			List <Device> dataPfdb = deviceRepository.findAllBydeviceType(DeviceType.pfdb);
			List <Device> dataPdc = deviceRepository.findAllBydeviceType(DeviceType.pfdb);
				switch(boardType){  
			     
			    case "cgdb": 
			    	
			    	for(int i=0;i<dataCgdb.size();i++)
			    	{
			    		cgdbUtil.sendCommand(dataCgdb.get(i),Command.SoftReset.toString());
			    	}
			    	
			    	break;  
		    	
			    case "ivd": 
			    	
			    	
			    	for(int i=0;i<dataIvd.size();i++)
			    	{
			    		ivdUtil.sendCommand(dataIvd.get(i),Command.SoftReset.toString());
			    	}
		    	
		    	break;  		    	
			    case "ovd": 
			    	
			    	
			    	for(int i=0;i<dataOvd.size();i++)
			    	{
			    		ovdUtil.sendCommand(dataOvd.get(i),Command.SoftReset.toString());
			    	}
			    	
			    	break;  
			    	
			    case "mldb": 
			    	
			    	
			    	for(int i=0;i<dataMldb.size();i++)
			    	{
			    		mldbUtil.sendCommand(dataMldb.get(i),Command.SoftReset.toString());
			    	}    	
			    	
			    	break;
			    	
			    case "agdb": 
			    
			    	
			    	
			    	for(int i=0;i<dataAgdb.size();i++)
			    	{
			    		agdUtil.sendCommand(dataAgdb.get(i),Command.SoftReset.toString());
			    	}    	
			    
			    	
			    	break;
			    	
			    case "pfdb": 
			    	
			    	
			    	
			    	for(int i=0;i<dataPfdb.size();i++)
			    	{
			    		pfdUtil.sendCommand(dataPfdb.get(i),Command.SoftReset.toString());
			    	}    
			    	break;
			    
			    case "pdc": 
			    	
			    	for(int i=0;i<dataCgdb.size();i++)
			    	{
			    		pdcUtil.sendCommand(dataCgdb.get(i),Command.SoftReset.toString());
			    	}
			    default:	
			    	for(int i=0;i<dataCgdb.size();i++)
			    	{
			    		cgdbUtil.sendCommand(dataCgdb.get(i),Command.SoftReset.toString());
			    	}
			    	for(int i=0;i<dataIvd.size();i++)
			    	{
			    		ivdUtil.sendCommand(dataIvd.get(i),Command.SoftReset.toString());
			    	}
			    	for(int i=0;i<dataOvd.size();i++)
			    	{
			    		ovdUtil.sendCommand(dataOvd.get(i),Command.SoftReset.toString());
			    	}
			    	
			    	for(int i=0;i<dataMldb.size();i++)
			    	{
			    		mldbUtil.sendCommand(dataMldb.get(i),Command.SoftReset.toString());
			    	}  
			    	

			    	for(int i=0;i<dataAgdb.size();i++)
			    	{
			    		agdUtil.sendCommand(dataAgdb.get(i),Command.SoftReset.toString());
			    	}    

			    	for(int i=0;i<dataPfdb.size();i++)
			    	{
			    		pfdUtil.sendCommand(dataPfdb.get(i),Command.SoftReset.toString());
			    	}    
			    	
			    	for(int i=0;i<dataCgdb.size();i++)
			    	{
			    		pdcUtil.sendCommand(dataCgdb.get(i),Command.SoftReset.toString());
			    	}
			    	break;
			    	
			    }  //end of switch	
		}
		else if( url == Command.GetConfiguration.toString() )
		{
			String ip="192.168";
    		ip=ip.concat(pf).concat(deviceId);
				switch(boardType){  
			     
			    case "cgdb": 
			    	
			    	//List <Device> dataCgdb = deviceRepository.findAllBydeviceType(DeviceType.cgdb);
			    		
			    	cgdbUtil.sendCommand(ip,Command.GetConfiguration.toString());
			    	break;  
		    	
			    case "ivd": 
			    	List <Device> dataIvd = deviceRepository.findAllBydeviceType(DeviceType.ivd);
			    	
			    	
			    		ivdUtil.sendCommand(ip,Command.GetConfiguration.toString());
			    		//ivdUtil.sendCommand(dataIvd.get(i),Command.GetConfiguration.toString());
			    	
		    	
		    	break;  		    	
			    case "ovd": 
//			    	List <Device> dataOvd = deviceRepository.findAllBydeviceType(DeviceType.ovd);
//			    	
//			    	for(int i=0;i<dataOvd.size();i++)
//			    	{
//			    		ovdUtil.sendCommand(dataOvd.get(i),Command.GetConfiguration.toString());
//			    	}
			    	
			    	ovdUtil.sendCommand(ip,Command.GetConfiguration.toString());
			    	break;  
			    	
			    case "mldb": 
//			    	List <Device> dataMldb = deviceRepository.findAllBydeviceType(DeviceType.mldb);
//			    	
//			    	for(int i=0;i<dataMldb.size();i++)
//			    	{
//			    		mldbUtil.sendCommand(dataMldb.get(i),Command.GetConfiguration.toString());
//			    	}    	
			    	mldbUtil.sendCommand(ip,Command.GetConfiguration.toString());
			    	break;
			    	
			    case "agdb": 
			    
//			    	List <Device> dataAgdb = deviceRepository.findAllBydeviceType(DeviceType.agdb);
//			    	
//			    	for(int i=0;i<dataAgdb.size();i++)
//			    	{
//			    		agdUtil.sendCommand(dataAgdb.get(i),Command.GetConfiguration.toString());
//			    	}    	
			    	agdUtil.sendCommand(ip,Command.GetConfiguration.toString());
			    	
			    	break;
			    	
			    case "pfdb": 
			    	
//			    	List <Device> dataPfdb = deviceRepository.findAllBydeviceType(DeviceType.pfdb);
//			    	
//			    	for(int i=0;i<dataPfdb.size();i++)
//			    	{
//			    		pfdUtil.sendCommand(dataPfdb.get(i),Command.GetConfiguration.toString());
//			    	}    
			    	
			    	pfdUtil.sendCommand(ip,Command.GetConfiguration.toString());
			    	break;
			    
			    default:	
			    
			    	
			    }  //end of switch	
		}	
		 
		 
		}
		
	public void response(byte[] resArray, String[] platformArray) {
		
		int len = resArray.length;
		byte[] pdcResArray = Arrays.copyOfRange( resArray, 15, len-4);

		for (int i = 0; i < pdcResArray.length; ) {
			
			int id = DataUtil.hexToInt(String.valueOf(pdcResArray[i]));
			
			int dtype = DataUtil.getDeviceHeader(String.valueOf(pdcResArray[i++]));
			
			String rStatus = DataUtil.getResponseStatus(pdcResArray[i++]);
			
			i++;
			ip = ip.concat(platformArray[0]+"."+String.valueOf(id));
			
			Device ipData = deviceRepository.findByIpAddress(ip);
			
			LinkStatus linkId = ipData.getLinkStatus();
			
			Optional<LinkStatus> linkStatus = linkStatusRepo.findById(linkId.getId());
			
			linkStatus.get().setResponse(rStatus);
			
			linkStatus.get().setLinkTime(date);
			linkStatus.get().setResponseTime(date);
			linkStatusRepo.save(linkStatus.get());	
		}
		
	}
			
}




