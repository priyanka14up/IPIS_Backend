/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2022
 * Purpose: Common Utility
 */
package com.innobitsysytems.ipis.utility;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

// import com.fasterxml.jackson.databind.ObjectMapper;
import com.innobitsysytems.ipis.dto.AuthenticationResponse;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.device.HashMapCoachDetails;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.reports.Announcement;
import com.innobitsysytems.ipis.model.reports.CgdbTransmission;
import com.innobitsysytems.ipis.model.reports.LinkCheck;
import com.innobitsysytems.ipis.model.reports.Login;
import com.innobitsysytems.ipis.model.reports.TrainTransmission;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.UserRepository;
import com.innobitsysytems.ipis.repository.reports.AnnouncementReportsRepository;
import com.innobitsysytems.ipis.repository.reports.CgdbTransmissionReportsRepository;
import com.innobitsysytems.ipis.repository.reports.LinkCheckReportsRepository;
import com.innobitsysytems.ipis.repository.reports.LoginReportsRepository;
import com.innobitsysytems.ipis.repository.reports.TrainTransmissionReportsRepository;

@Component
public class CommonUtil {

	@Autowired
	private LoginReportsRepository loginReportsRepository;
	
	@Autowired
	private CustomUtil customUtil;
	
	@Autowired
	private CgdbTransmissionReportsRepository cgdbTransmissionReportsRepository;
	
	@Autowired
	private TrainTransmissionReportsRepository trainTransmissionReportsRepository;
	
	@Autowired
	private AnnouncementReportsRepository announcementReportsRepository;
	
	@Autowired
	private LinkCheckReportsRepository linkCheckReportsRepository;

	@Autowired
	private UserRepository userRepo;
	
	 @Autowired
	 private DeviceRepository deviceRepository;
	
	 LocalDateTime date = LocalDateTime.now(); 
	 
	 Date dateTime = new Date();
	 
	 CgdbTransmission cgdbTransmission = new CgdbTransmission();
	 
	 TrainTransmission trainTransmission = new TrainTransmission();
	 
	 Announcement announcement = new Announcement();
	 
	 public void LoginReports(User user, AuthenticationResponse authenticationResponse) {
		
		LocalDateTime exp = LocalDateTime.ofInstant(authenticationResponse.getExpiresAt(), ZoneOffset.UTC);
		Login login = new Login();

		login.setFirstName(user.getFirstname());
		login.setLastName(user.getLastname());
		login.setRole(user.getUserRole().getRoleText());
		login.setLoginDateTime(date);
		login.setLogoutDateTime(exp);
		login.setUser(user);
		loginReportsRepository.save(login);

	}
	
	public void LogoutReport(String token) {
		
		String[] chunks = token.split("\\.");
		Base64.Decoder decoder = Base64.getUrlDecoder();
		String payload = new String(decoder.decode(chunks[1]));
		JSONObject jsonPayload;
			
		try {
			
			jsonPayload = new JSONObject(payload);
			String exp = jsonPayload.get("exp").toString();
			String sub = jsonPayload.get("sub").toString();
			Optional<User> user = userRepo.findByEmail(sub);
			List<Login> loginUserData = loginReportsRepository.findByUser(user.get());
			Login loginData = loginUserData.get(loginUserData.size()-1);
//			long time1 = Long.parseLong(exp);
				
//			String myDate= new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(new Date(time1 * 1000));
				
//			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
		        
//		    LocalDateTime localDateTime = LocalDateTime.parse(myDate, formatter);
//		    LocalDateTime now = LocalDateTime.now(); 
		        
//		    if(loginData.getLogoutDateTime().compareTo(localDateTime) == 0) {
//		    	 
//		    	 if(loginData.getLoginDateTime().compareTo(now) < 0) {
//		    		 
//		    		 if(now.compareTo(loginData.getLogoutDateTime()) < 0) {
		    			 
		    			 loginData.setLogoutDateTime(date);
		        		 loginReportsRepository.save(loginData);
		        		
//		        	}
//		         }
//		      }
				
			}catch (JSONException e) {
				
				e.printStackTrace();
			}
	
	}
	
	public void updateActivities(String activity, String createdBy) throws HandledException {
		
		User user = userRepo.findById(Long.parseLong(createdBy))
				.orElseThrow(() -> new HandledException("NOT_FOUND", "User id not found in Database : " + createdBy));
		
		List<Login> loginUserData = loginReportsRepository.findByUser(user);
		int size = loginUserData.size();
		Login login = loginUserData.get(size-1);
		List<String> userActivity = new ArrayList<String>();
		
		if(login.getActivities().size() > 0) {
			
			userActivity.addAll(login.getActivities());
			userActivity.add(activity);
			
		}else {
			
			userActivity.add(activity);
		}
		
		login.setActivities(userActivity);
		login.setFirstName(login.getFirstName());
		login.setLastName(login.getLastName());
		login.setLoginDateTime(login.getLoginDateTime());
		login.setLogoutDateTime(login.getLogoutDateTime());
		login.setRole(login.getRole());
		login.setUser(login.getUser());
		login.setId(login.getId());
		
	}
	
	public void onlineTrainCgdb(List<OnlineTrain> onlineTrainCgdb) throws HandledException {
		
		Optional<User> user = userRepo.findById(customUtil.getIdFromToken());
		List <Device> deviceInfo = deviceRepository.findAllBydeviceType(DeviceType.cgdb);
		List<CoachesDetail> coachDetails = new ArrayList<CoachesDetail>();
		 HashMapCoachDetails hashmap=new HashMapCoachDetails();
		 HashMap<String, String> map = new HashMap<String, String>();
		//List<Object> list=new ArrayList<>();
		 
		 
		 
		for (int i = 0; i < deviceInfo.size(); i++) {
			List<CoachesDetail> coachesList=deviceInfo.get(i).getCoaches();
			 String s=hashmap.convertToDatabaseColumn(coachDetails);  
			
			 try
			 {
			 	JSONArray jsonarray=new JSONArray(s);
			 	for(int j=0;j<jsonarray.length();j++)
			 	{
					
			 		JSONObject e = jsonarray.getJSONObject(j);
			 		//ObjectMapper objectMapper = new ObjectMapper();
			 		//CoachesDetail coach=objectMapper.readValue(s,CoachesDetail.class);
					
                     map.put("coachNo",  String.valueOf("coachNo"));
                     map.put("coachName", "coachName :" + e.getString("coachName"));
                     map.put("ipAddress", "ipAddress : " +  e.getString("ipAddress"));
                     map.put("status", "status : " +  e.getString("status"));
			 	}	
			 }catch(Exception e)
			 {
			 	System.out.println(e);
			 }
		}	
		
		String trainName = "";
		String platformNo = "";
		String pdcPort = "";
			
		ArrayList<String> trainNameArray = new ArrayList<String>(); 
		ArrayList<String> platformArray = new ArrayList<String>();
		ArrayList<String> pdcArray = new ArrayList<String>();
			
		for(int i = 0; i < onlineTrainCgdb.size(); i++) {
				
				trainNameArray.add(onlineTrainCgdb.get(i).getTrainName());
				platformArray.add(onlineTrainCgdb.get(i).getPlatformNo());

		}
		
		for( int i = 0; i < platformArray.size(); i++) {
			
			String[] platformCgdb = { platformArray.get(i) };
			try {
				
				Device device = deviceRepository.getIdByDeviceType(DeviceType.pdc, platformCgdb);
				pdcArray.add(device.getPortNumber());
				
			}catch(Exception e) {
				
				throw new HandledException("NOT_FOUND","Pdc for platform number " + platformArray.get(i)+" doesn't exist in Database");
			}
			
			
		}
			
		for(int i=0; i<trainNameArray.size(); i++) {
				
				if(i!=trainNameArray.size()-1) {
					
					trainName+= trainNameArray.get(i)+", ";
					platformNo+=platformArray.get(i)+", ";
					pdcPort+= pdcArray.get(i)+", ";
				
				}else {
				
					trainName+= trainNameArray.get(i);
					pdcPort+= pdcArray.get(i);
					platformNo+=platformArray.get(i);
				
				}
				
			}

		cgdbTransmission.setTrainName(trainName);
		cgdbTransmission.setPlatformNo(platformNo);
		cgdbTransmission.setLocalDateTime(date);
		cgdbTransmission.setUser(user.get());
		cgdbTransmission.setPdcPort(pdcPort);
		cgdbTransmission.setData(coachDetails);
		cgdbTransmissionReportsRepository.save(cgdbTransmission);
		
		}
	
	public void onlineTrainTadb(List<OnlineTrain> onlineTrain) {
		
		Optional<User> user = userRepo.findById(customUtil.getIdFromToken());
		
		String trainName = "";
		String actualArrival = "";
		String actualDeparture = "";
		String scheuduledArrival = "";
		String scheuduledDeparture = "";
		String late = "";
		String description = "";
		
		ArrayList<String> trainNameArray = new ArrayList<String>(); 
		ArrayList<String> actualArrivalArray = new ArrayList<String>();
		ArrayList<String> actualDepartureArray = new ArrayList<String>();
		ArrayList<String> scheuduledArrivalArray = new ArrayList<String>();
		ArrayList<String> scheuduledDepartureArray = new ArrayList<String>();
		ArrayList<String> lateArray = new ArrayList<String>();
		ArrayList<String> descriptionArray = new ArrayList<String>();
		ArrayList<String> trainNumberArray = new ArrayList<String>();
		ArrayList<String> trainStatusArray = new ArrayList<String>();
		
		for(int i = 0; i < onlineTrain.size(); i++) {
			
			trainNameArray.add(onlineTrain.get(i).getTrainName());
			scheuduledArrivalArray.add(onlineTrain.get(i).getSTA().toString());
			scheuduledDepartureArray.add(onlineTrain.get(i).getSTD().toString());
			lateArray.add(onlineTrain.get(i).getLate());
			actualArrivalArray.add(onlineTrain.get(i).getETA().toString());
			actualDepartureArray.add(onlineTrain.get(i).getETD().toString());
			trainStatusArray.add(onlineTrain.get(i).getTrainStatus()); 
			trainNumberArray.add(onlineTrain.get(i).getTrainNumber().toString());
			
			descriptionArray.add(onlineTrain.get(i).getTrainNumber().toString());
			descriptionArray.add(onlineTrain.get(i).getTrainName());
			descriptionArray.add(onlineTrain.get(i).getETA().toString());
			descriptionArray.add(onlineTrain.get(i).getETD().toString());
			descriptionArray.add(onlineTrain.get(i).getPlatformNo());
			
		}

		for(int i = 0; i < trainNameArray.size(); i++) {
			
			if(i != trainNameArray.size()-1) {
				
				description+=descriptionArray.get(i)+" - ";
				trainName+= trainNameArray.get(i)+", ";
				actualArrival+= actualArrivalArray.get(i)+", ";
				actualDeparture+= actualDepartureArray.get(i)+", ";
				scheuduledDeparture+= scheuduledDepartureArray.get(i)+", ";
				scheuduledArrival+= scheuduledArrivalArray.get(i)+", ";
	
			}else {
				
				description+=descriptionArray.get(i)+" ";
				trainName+= trainNameArray.get(i);
				actualArrival+= actualArrivalArray.get(i);
				actualDeparture+= actualDepartureArray.get(i);
				scheuduledDeparture+= scheuduledDepartureArray.get(i);
				scheuduledArrival+= scheuduledArrivalArray.get(i);
			
			}
			
		}
		
		trainTransmission.setActualArrival(actualArrival);
		trainTransmission.setActualDeparture(actualDeparture);
		trainTransmission.setLocalDateTime(date);
		trainTransmission.setTrainName(trainName);
		trainTransmission.setLate(late);
		trainTransmission.setScheuduledArrival(scheuduledArrival);
		trainTransmission.setScheuduledDeparture(scheuduledDeparture);
		trainTransmission.setUser(user.get());
		trainTransmission.setDescription(description);
		trainTransmissionReportsRepository.save(trainTransmission);
	}
	
	public void Announcement(PublicAnnouncement pAData) {
		
		Optional<User> user = userRepo.findById(customUtil.getIdFromToken());
		
		announcement.setAnnouncementTime(date);
		announcement.setAnnouncementType(pAData.getMessageType());
		announcement.setUser(user.get());
		announcementReportsRepository.save(announcement);
	}
	
	public void trainAnnouncement(List<OnlineTrain> onlineTrain) {
		
		Optional<User> user = userRepo.findById(customUtil.getIdFromToken());
		
		String msg = "";
		for(int i = 0; i < onlineTrain.size();i++) {
			
			OnlineTrain trainData = onlineTrain.get(i);
			msg += trainData.getTrainNumber().toString()+""+trainData.getTrainName()+ ", ";
			
		}
		announcement.setAnnouncementTime(date);
		announcement.setAnnouncementType("Train Announcement");
		announcement.setUser(user.get());
		announcement.setAnnouncementMessage(msg);
		announcementReportsRepository.save(announcement);
	}
	
	
	public void linkCheck(ArrayList device) {
		
		Optional<User> user = userRepo.findById(customUtil.getIdFromToken());
			
		for(int i = 0; i < device.size(); i++) {
			
			LinkCheck linkCheck = new LinkCheck();
			Object data = device.get(i);
			Map myMap =  (Map)data;
			linkCheck.setDeviceType(String.valueOf(myMap.get("deviceType")));
			linkCheck.setIpAddress(String.valueOf(myMap.get("ipAddress")));
			linkCheck.setLocalDateTime(date);
			linkCheck.setLinkTime(String.valueOf(myMap.get("linkTime")));
			linkCheck.setPort(String.valueOf(myMap.get("portNumber")));
			linkCheck.setResponseTime(String.valueOf(myMap.get("responseTime")));
			linkCheck.setStatus(String.valueOf(myMap.get("status")));
			linkCheck.setUser(user.get());
			linkCheckReportsRepository.save(linkCheck);
			
		}

	}	
	
}
	

