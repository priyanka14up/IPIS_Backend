/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Intensity Bean
 */
package com.innobitsysytems.ipis.services.setup;

import com.innobitsysytems.ipis.dto.SoftResetDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.GetIntensity;
import com.innobitsysytems.ipis.model.setup.Intensity;
import com.innobitsysytems.ipis.model.setup.WebConfiguration;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.GetIntensityRepo;
import com.innobitsysytems.ipis.repository.setup.IntensityRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.innobitsysytems.ipis.utility.Command;
import com.innobitsysytems.ipis.utility.CommonUtil;
import com.innobitsysytems.ipis.utility.CustomUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class IntensityBean implements IntensityService {

	private static final Logger log = LoggerFactory.getLogger(IntensityBean.class);

	@Autowired
	private IntensityRepository intensityRepo;

	@Autowired
	private RequestWrapper requestWrapper;

	@Autowired
	public CustomUtil customUtil;

	@Autowired
	public CommonUtil commonUtil;
	
	@Autowired
	public DeviceRepository deviceRepository;

	@Autowired
	public 	GetIntensityRepo getIntensityRepo;
	
	@Override
	public HashMap<String, Object> getAutoIntensity() throws HandledException {

		Intensity intensityData = intensityRepo.findByIntensityMode("Auto");

		if (intensityData != null) {

			
			return customAutoIntensity(intensityData);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Database is empty");
		}
	}

	@Override
	public HashMap<String, Object> getManualIntensity() throws HandledException {

		Intensity intensityData = intensityRepo.findByIntensityMode("Manual");

		if (intensityData != null) {

			return customManualIntensity(intensityData);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Database is empty");
		}
	}

//	old code
//	@Override
//	public HashMap<String, Object> create(Intensity intensity) throws HandledException {
//
//		HashMap<String, Object> intensityMap = new HashMap<>();
//		Intensity intensityData = intensityRepo.findByIntensityMode("Auto");
//
//		if (intensity.getIntensityMode().equals("Auto")) {
//
//			if (intensityData == null) {
//
//				intensity.setCreatedBy((int) customUtil.getIdFromToken());
//				commonUtil.updateActivities("Display Intensity Setting By Auto Mode ",
//						String.valueOf(customUtil.getIdFromToken()));
//				intensityRepo.save(intensity);
//				intensityMap = customAutoIntensity(intensity);
//				
//				
//				
//				requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY);
//
//			} else {
//
//				intensityData.setCreatedBy(intensityData.getCreatedBy());
//				intensityData.setUpdatedBy((int) customUtil.getIdFromToken());
//				intensityData.setIntensityMode(intensity.getIntensityMode());
//				intensityData.setDayStartTime(intensity.getDayStartTime());
//				intensityData.setNightStartTime(intensity.getNightStartTime());
//				intensityData.setDayIntensity(intensity.getDayIntensity());
//				intensityData.setNightIntensity(intensity.getNightIntensity());
//				commonUtil.updateActivities("Update Display Intensity Setting By Auto Mode ",
//						String.valueOf(customUtil.getIdFromToken()));
//				intensityRepo.save(intensityData);
//				intensityMap = customAutoIntensity(intensityData);
//				requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY);
//
//			}
//
//		} else if (intensity.getIntensityMode().equals("Manual")) {
//			Intensity intensityDataManual = intensityRepo.findByIntensityMode("Manual");
//
//			if (intensityDataManual == null) {
//
//				intensity.setCreatedBy((int) customUtil.getIdFromToken());
//				commonUtil.updateActivities("Display Intensity Setting By Auto Mode ",
//						String.valueOf(customUtil.getIdFromToken()));
//				intensityRepo.save(intensity);
//				intensityMap = customManualIntensity(intensity);
//				String dataIntensity = intensity.getIntensityValue().toString();
//				requestWrapper.postRequest(dataIntensity, RequestTypes.SET_INTENSITY, intensity.getDevice(),
//						intensity.getPlatform());
//
//			} else {
//
//				intensity.setCreatedBy((int) customUtil.getIdFromToken());
//				intensityDataManual.setUpdatedBy((int) customUtil.getIdFromToken());
//				intensityDataManual.setIntensityMode(intensity.getIntensityMode());
//				intensityDataManual.setIntensityValue(intensity.getIntensityValue());
//				intensityDataManual.setDevice(intensity.getDevice());
//				intensityDataManual.setPlatform(intensity.getPlatform());
//				commonUtil.updateActivities(" Display Intensity Setting By Manual Mode ",
//						String.valueOf(customUtil.getIdFromToken()));
//				intensityRepo.save(intensityData);
//				intensityMap = customManualIntensity(intensityDataManual);
//				requestWrapper.postRequest(intensity.getIntensityValue().toString(), RequestTypes.SET_INTENSITY,
//						intensity.getDevice(), intensity.getPlatform());
//
//				if (intensity.getMode().equals("All")) {
//
//					requestWrapper.postRequest(intensity.getIntensityValue(), RequestTypes.SET_INTENSITY);
//
//				} else {
//
//					String dataIntensity = intensity.getIntensityValue().toString();
//					requestWrapper.postRequest(dataIntensity, RequestTypes.SET_INTENSITY, intensity.getDevice(),
//							intensity.getPlatform());
//
//				}
//			}
//
//		}
//
//		return intensityMap;
//	}
//	
	@Override
	public HashMap<String, Object> create(Intensity intensity) throws HandledException {

		HashMap<String, Object> intensityMap = new HashMap<>();
		Intensity intensityData = intensityRepo.findByIntensityMode("Auto");

		if (intensity.getIntensityMode().equals("Auto")) {

			if (intensityData == null) {

				intensity.setCreatedBy((int) customUtil.getIdFromToken());
				commonUtil.updateActivities("Display Intensity Setting By Auto Mode ",
						String.valueOf(customUtil.getIdFromToken()));
				Calendar date = Calendar.getInstance();
				Date currentTime = date.getTime();
				System.out.println("current time"+currentTime);
				intensityRepo.save(intensity);
				intensityMap = customAutoIntensity(intensity);
				
				requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());

			} else {

				intensityData.setCreatedBy(intensityData.getCreatedBy());
				intensityData.setUpdatedBy((int) customUtil.getIdFromToken());
				intensityData.setIntensityMode(intensity.getIntensityMode());
				intensityData.setDayStartTime(intensity.getDayStartTime());
				intensityData.setNightStartTime(intensity.getNightStartTime());
				intensityData.setDayIntensity(intensity.getDayIntensity());
				intensityData.setNightIntensity(intensity.getNightIntensity());
				commonUtil.updateActivities("Update Display Intensity Setting By Auto Mode ",
						String.valueOf(customUtil.getIdFromToken()));
				intensityRepo.save(intensityData);
				intensityMap = customAutoIntensity(intensityData);
				Calendar date = Calendar.getInstance();
				Date currentTime = date.getTime();
				System.out.println("current time"+currentTime);
				requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());

			}

		} else if (intensity.getIntensityMode().equals("Manual")) {
			Intensity intensityDataManual = intensityRepo.findByIntensityMode("Manual");

			if (intensityDataManual == null) {

				intensity.setCreatedBy((int) customUtil.getIdFromToken());
				commonUtil.updateActivities("Display Intensity Setting By Auto Mode ",
						String.valueOf(customUtil.getIdFromToken()));
				intensityRepo.save(intensity);
				intensityMap = customManualIntensity(intensity);
				String dataIntensity = intensity.getIntensityValue().toString();
				//requestWrapper.postRequest(dataIntensity, RequestTypes.SET_INTENSITY, intensity.getDevice(),intensity.getPlatform());

			} else {

				intensity.setCreatedBy((int) customUtil.getIdFromToken());
				intensityDataManual.setUpdatedBy((int) customUtil.getIdFromToken());
				intensityDataManual.setIntensityMode(intensity.getIntensityMode());
				intensityDataManual.setIntensityValue(intensity.getIntensityValue());
				intensityDataManual.setDevice(intensity.getDevice());
				intensityDataManual.setPlatform(intensity.getPlatform());
				commonUtil.updateActivities(" Display Intensity Setting By Manual Mode ",
						String.valueOf(customUtil.getIdFromToken()));
				intensityRepo.save(intensityData);
				intensityMap = customManualIntensity(intensityDataManual);
				//requestWrapper.postRequest(intensity.getIntensityValue().toString(), RequestTypes.SET_INTENSITY,intensity.getDevice(), intensity.getPlatform());

				if (intensity.getMode().equals("All")) {

					requestWrapper.postRequest(intensity.getIntensityValue(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());

				} else {

					String dataIntensity = intensity.getIntensityValue().toString();
					//requestWrapper.postRequest(dataIntensity, RequestTypes.SET_INTENSITY, intensity.getDevice(),
							//intensity.getPlatform());

				}
			}

		}

		return intensityMap;
	}


	
	// Custom Response Using HashMap

	private HashMap<String, Object> customAutoIntensity(Intensity intensity) {

		HashMap<String, Object> intensityMap = new HashMap<>();

		intensityMap.put("id", intensity.getId());
		intensityMap.put("intensityMode", intensity.getIntensityMode());
		intensityMap.put("dayStartTime", intensity.getDayStartTime());
		intensityMap.put("nightStartTime", intensity.getNightStartTime());
		intensityMap.put("dayIntensity", intensity.getDayIntensity());
		intensityMap.put("nightIntensity", intensity.getNightIntensity());

		return intensityMap;

	}

	private HashMap<String, Object> customManualIntensity(Intensity intensity) {

		HashMap<String, Object> intensityMap = new HashMap<>();

		intensityMap.put("id", intensity.getId());
		intensityMap.put("intensityMode", intensity.getIntensityMode());
		intensityMap.put("Device", intensity.getDevice());
		intensityMap.put("intensityValue", intensity.getIntensityValue());
		intensityMap.put("platform", intensity.getPlatform());

		return intensityMap;

	}

	@Override
	public List<Intensity> getAllIntensity() throws Exception {
		HashMap<String, Object> intensityMap = new HashMap<>();
		List<Intensity> intensityList = intensityRepo.findAll();

		return intensityList;
	}

//	@Override
//	public Intensity saveIntensity(Intensity intensity) throws Exception {
//		// TODO Auto-generated method stub
//		List<Intensity> intensityList = intensityRepo.findAll();
//		if (intensityList.size() == 0) {
//			intensity.setUpdatedBy((int) customUtil.getIdFromToken());
//			commonUtil.updateActivities("Post Web Configuration in SetUp ",
//					String.valueOf(customUtil.getIdFromToken()));
//			intensityRepo.save(intensity);
//			
//			Calendar date = Calendar.getInstance();
//			Date currentTime = date.getTime();
//			System.out.println("current time"+currentTime);
//			
//			
//			return intensity;
//
//		} else {
//			long id = intensityList.get(0).getId();
//			System.out.println(id);
//			Optional<Intensity> intens = intensityRepo.findById(id);
//			System.out.println(intens);
//			Intensity IntensityData = intens.get();
//
//			IntensityData.setDayIntensity(intensity.getDayIntensity());
//			IntensityData.setDayStartTime(intensity.getDayStartTime());
//			IntensityData.setDevice(intensity.getDevice());
//			IntensityData.setIntensityMode(intensity.getIntensityMode());
//			IntensityData.setIntensityValue(intensity.getIntensityValue());
//			IntensityData.setMode(intensity.getMode());
//			IntensityData.setNightIntensity(intensity.getNightIntensity());
//			IntensityData.setNightStartTime(intensity.getNightStartTime());
//			IntensityData.setPlatform(intensity.getPlatform());
//			IntensityData.setDeviceId(intensity.getDeviceId());
//			IntensityData.setUpdatedBy((int) customUtil.getIdFromToken());
//			intensityRepo.save(IntensityData);
//			
//			
//			
//					
//			return IntensityData;
//		}
//	}

	
	
	
	private String[] ipValues(String ip) {
		
		String[] ipAddress = ip.split("[, . ']+");
		return ipAddress;
		
	}

	@Override
	public HashSet<String> getDevicePlatform(DeviceType deviceType) throws HandledException{
		
		List<Device> deviceData = deviceRepository.findAllBydeviceType(deviceType);
		HashSet<String> platform = new HashSet<String>();
		
		if(deviceData.size() != 0) {
			
			for (int i = 0; i < deviceData.size(); i++) {
				
				String[] platArray = deviceData.get(i).getPlatformNo();
				platform.add(platArray[0]);
				
			}	
			
		}else {

			throw new HandledException("CHECK_PARAMETERS","Display Board doesn't Exist.");
		}
		
		return platform;
	}
	
	
	@Override
	public List<String> getDeviceId( DeviceType deviceType ,String platformNo) throws HandledException {
		
		List <Device> devices=deviceRepository.findAllBydeviceType(deviceType);
		List<String> list = new ArrayList<String>();
		String thirdOctate="";
		
		
		
		
		for(int i=0;i<devices.size();i++)
		{
			if(deviceType.toString().equals("cgdb"))
			{
				List< CoachesDetail > coaches = devices.get(i).getCoaches();
				int size=coaches.size();
				System.out.println();
				System.out.println("Created CGDB size"+size);
				System.out.println();

				for (int j = 0; j < coaches.size(); j++) {

					Object obj = coaches.get(j);
					Map myMap =  (Map)obj; 
					String ipAddress = String.valueOf(myMap.get("ipAddress"));
					
					System.out.println("ipaddress"+ipAddress);
					
					System.out.println();
					System.out.println("Created CGDB details"+coaches.get(j));
					System.out.println();

					String[] platArray=devices.get(i).getPlatformNo();
					
					if(platformNo.equals(platArray[0]))
					{
						 thirdOctate=ipValues(ipAddress)[3];
						 list.add(thirdOctate);
						
					}
					//String coachValue = coachDetails[1];  // to see  		 

					
				}
			}
			
			else
			{
				String ip=devices.get(i).getIpAddress();
				String[] platArray=devices.get(i).getPlatformNo();
				
				if(platformNo.equals(platArray[0]))
				{
					 thirdOctate=ipValues(ip)[3];
					 list.add(thirdOctate);
					
				}
				
			}
			
		
		}
		return list;
	}
	

@Override
public HashMap<String, Object> saveIntensity(Intensity intensity) throws Exception {
	
	HashMap<String, Object> intensityMap = new HashMap<>();
	Intensity intensityData = intensityRepo.findByIntensityMode("Auto");

	if (intensity.getIntensityMode().equals("Auto")) {

		if (intensityData == null) 
		{

			intensity.setCreatedBy((int) customUtil.getIdFromToken());
			commonUtil.updateActivities("Display Intensity Setting By Auto Mode ",
					String.valueOf(customUtil.getIdFromToken()));
			
			intensityRepo.save(intensity);
			intensityMap = customAutoIntensity(intensity);
			
			Calendar date = Calendar.getInstance();
			Date currentTime = date.getTime();
			System.out.println("current time"+currentTime);
			
			SimpleDateFormat formatMinutes = new SimpleDateFormat("mm");
					String getMinutes = formatMinutes.format(new Date());
					
					SimpleDateFormat formatHours = new SimpleDateFormat("HH");
					String getHours = formatHours.format(new Date());
					
					SimpleDateFormat formatSecs = new SimpleDateFormat("ss");
					String getSecs = formatSecs.format(new Date());
					
					System.out.println("hh:mm="+getHours+":"+getMinutes);
					System.out.println("intensity.getDayStartTime()=="+intensity.getDayStartTime());
					String getDayStartTime=intensity.getDayStartTime();
					String getNightStartTime=intensity.getNightStartTime();
					
					  LocalTime systemTime, dayStartTime,nightStartTime; 
					  
					  int sysHr=Integer.parseInt(getHours);
					  int sysMm=Integer.parseInt(getMinutes);
					  systemTime=LocalTime.of(sysHr,sysMm);
					  
					  String[] dayTime = getDayStartTime.split(":");
					  int dayHr=Integer.parseInt(dayTime[0]);
					  int dayMm=Integer.parseInt(dayTime[1]);
					  dayStartTime=LocalTime.of(dayHr,dayMm);
					  
					  String[] nightTime = getNightStartTime.split(":");
					  int nightHr=Integer.parseInt(nightTime[0]);
					  int nightMm=Integer.parseInt(nightTime[1]);
					  nightStartTime=LocalTime.of(nightHr,nightMm);
					  
					  int value1 = systemTime.compareTo(dayStartTime);
					  System.out.println("value1="+value1);
					  int value2 = systemTime.compareTo(nightStartTime);
					  System.out.println("value2="+value2);
					 
					  
					  if((value1>=0)&&(value2<0))
					  {
						  System.out.println("time comarison day start case");
						  requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());
						  
					  }
					  else if((value2>=0)&&(value1<0))
					  {
						  requestWrapper.postRequest(intensity.getNightIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());
						 
					  }
					  else
					  {
						  throw new HandledException("CHECK_PARAMETERS", "Day time and night time clashes");
					  }
					  
			//requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());

		} else {

			intensityData.setCreatedBy(intensityData.getCreatedBy());
			intensityData.setUpdatedBy((int) customUtil.getIdFromToken());
			intensityData.setIntensityMode(intensity.getIntensityMode());
			intensityData.setDayStartTime(intensity.getDayStartTime());
			intensityData.setNightStartTime(intensity.getNightStartTime());
			intensityData.setDayIntensity(intensity.getDayIntensity());
			intensityData.setNightIntensity(intensity.getNightIntensity());
			commonUtil.updateActivities("Update Display Intensity Setting By Auto Mode ",
					String.valueOf(customUtil.getIdFromToken()));
			intensityRepo.save(intensityData);
			intensityMap = customAutoIntensity(intensityData);
			
			Calendar date = Calendar.getInstance();
			Date currentTime = date.getTime();
			System.out.println("current time"+currentTime);
			
			SimpleDateFormat formatMinutes = new SimpleDateFormat("mm");
					String getMinutes = formatMinutes.format(new Date());
					

					SimpleDateFormat formatHours = new SimpleDateFormat("HH");
					String getHours = formatHours.format(new Date());
					
					SimpleDateFormat formatSecs = new SimpleDateFormat("ss");
					String getSecs = formatSecs.format(new Date());
					
					System.out.println("hh:mm="+getHours+":"+getMinutes);
					System.out.println("intensity.getDayStartTime()=="+intensity.getDayStartTime());
					String getDayStartTime=intensity.getDayStartTime();
					String getNightStartTime=intensity.getNightStartTime();
					
					  LocalTime systemTime, dayStartTime,nightStartTime; 
					  
					  int sysHr=Integer.parseInt(getHours);
					  int sysMm=Integer.parseInt(getMinutes);
					  systemTime=LocalTime.of(sysHr,sysMm);
					  
					  String[] dayTime = getDayStartTime.split(":");
					  int dayHr=Integer.parseInt(dayTime[0]);
					  int dayMm=Integer.parseInt(dayTime[1]);
					  dayStartTime=LocalTime.of(dayHr,dayMm);
					  
					  String[] nightTime = getNightStartTime.split(":");
					  int nightHr=Integer.parseInt(nightTime[0]);
					  int nightMm=Integer.parseInt(nightTime[1]);
					  nightStartTime=LocalTime.of(nightHr,nightMm);
					  
					  int value1 = systemTime.compareTo(dayStartTime);
					  System.out.println("value1="+value1);
					  int value2 = systemTime.compareTo(nightStartTime);
					  System.out.println("value2="+value2);
					 
					  
					  if((value1>=0)&&(value2<0))
					  {
						  System.out.println("time comarison day start case");
						  requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());
						  
					  }
					  else if((value2>=0)&&(value1<0))
					  {
						  requestWrapper.postRequest(intensity.getNightIntensity(), RequestTypes.SET_INTENSITY,intensity.getIntensityMode());
						 
					  }
					  else
					  {
						  throw new HandledException("CHECK_PARAMETERS", "Day time and night time clashes");
					  }
		}

	} else if (intensity.getIntensityMode().equals("Manual")) {
		Intensity intensityDataManual = intensityRepo.findByIntensityMode("Manual");

		if (intensityDataManual == null) {

			intensity.setCreatedBy((int) customUtil.getIdFromToken());
			commonUtil.updateActivities("Display Intensity Setting By Auto Mode ",
					String.valueOf(customUtil.getIdFromToken()));
			intensityRepo.save(intensity);
			intensityMap = customManualIntensity(intensity);
			String dataIntensity = intensity.getIntensityValue().toString();
			//requestWrapper.postRequest(dataIntensity, RequestTypes.SET_INTENSITY, intensity.getDevice(),intensity.getPlatform());

		} else {

			
			intensity.setCreatedBy((int) customUtil.getIdFromToken());
			intensityDataManual.setUpdatedBy((int) customUtil.getIdFromToken());
			intensityDataManual.setIntensityMode(intensity.getIntensityMode());
			intensityDataManual.setMode(intensity.getMode());
			
			
			intensityDataManual.setIntensityValue(intensity.getIntensityValue());
			intensityDataManual.setDevice(intensity.getDevice());
			intensityDataManual.setPlatform(intensity.getPlatform());
			commonUtil.updateActivities(" Display Intensity Setting By Manual Mode ",
					String.valueOf(customUtil.getIdFromToken()));
			intensityRepo.save(intensityDataManual);
			intensityMap = customManualIntensity(intensityDataManual);

			if (intensity.getMode().equals("All")) {

				requestWrapper.postRequest(intensity.getIntensityValue(), RequestTypes.SET_INTENSITY,intensity.getMode(),intensity.getIntensityMode());

			} else {

		
				requestWrapper.postRequest(intensity.getIntensityValue(),RequestTypes.SET_INTENSITY, intensity.getDevice(),intensity.getDeviceId(),intensity.getPlatform(),intensity.getMode(),intensity.getIntensityMode());

			}
		}
	
	}

	return intensityMap;

}

//@Override
//public void getConfig(DeviceType deviceType) throws HandledException {
//
//	
//	if (deviceType!=null) {
//
//		requestWrapper.getConfigRequest(deviceType,Command.GetConfiguration);
//
//	} else {
//
//		throw new HandledException("NOT_FOUND", "Please Select board to Reset it");
//
//	}
//}


@Override
public  HashMap<String, Object> getConfig(Intensity intensity) throws HandledException {

	int intense =0;
	HashMap<String, Object> st = new HashMap<String, Object>();
	if(intensity!=null)
	{
		requestWrapper.getConfigRequest(intensity.getDevice(),intensity.getPlatform(),intensity.getDeviceId(),Command.GetConfiguration);
		GetIntensity getIntensity=new GetIntensity();
		List<GetIntensity> list=getIntensityRepo.findAll();
		
		for(int i=0;i<list.size();i++)
		{
			if(list.get(i).getDeviceId().equals(intensity.getDeviceId())&&list.get(i).getPlatform().equals(intensity.getPlatform()))
			{
			 intense=list.get(i).getIntensityValue();
			}	
		}
		 st.put("intensityValue", intense);

	}
	else
	{
		throw new HandledException("NOT_FOUND", "Please Select reuired fields");
	}
	return st;
	
	
	
//	if (deviceType!=null) {
//
//		requestWrapper.getConfigRequest(deviceType,Command.GetConfiguration);
//
//	} else {
//
//		throw new HandledException("NOT_FOUND", "Please Select board to Reset it");
//
//	}
}

@Override
public void getConfig(DeviceType deviceType) throws HandledException {
	// TODO Auto-generated method stub
	
}


}
