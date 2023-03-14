/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2022
 * Purpose: Report Bean
 */
package com.innobitsysytems.ipis.services.reports;

import java.util.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.innobitsysytems.ipis.dto.AnnouncementDto;
import com.innobitsysytems.ipis.dto.CgdbTransmissionDto;
import com.innobitsysytems.ipis.dto.LinkCheckDto;
import com.innobitsysytems.ipis.dto.LoginDto;
import com.innobitsysytems.ipis.dto.TrainTransmissionDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.*;
import com.innobitsysytems.ipis.repository.UserRepository;
import com.innobitsysytems.ipis.repository.reports.*;

@Service
public class ReportBean implements ReportService {

	@Autowired
	private  UserRepository userRepository;
	@Autowired
	private CgdbTransmissionReportsRepository cgdbTransmissionReportsRepository;
	@Autowired
	private TrainTransmissionReportsRepository trainTransmissionReportsRepository;
	@Autowired
	private LinkCheckReportsRepository linkCheckReportsRepository;
	@Autowired
	private LoginReportsRepository loginReportsRepository;
	@Autowired
	private AnnouncementReportsRepository announcementReportsRepository;
	
	@Override
	public List getAllUsers() {
		
		List<User> userData = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstname"));
		
		List userList = new ArrayList<>();
		
		for (int i=0; i<userData.size(); i++) {
			
			userList.add(customResponseUser(userData.get(i)));
			
		}
		
		return userList;
	}

	@Override
	public List<LinkCheckDto> getAllLinkCheckReports(Date DateStart, Date DateEnd, Long userid) throws HandledException {
		
		 List<LinkCheckDto> linkCheck = linkCheckReportsRepository.findByCreatedAtFilter(DateStart, DateEnd, userid);
		 
		 if(linkCheck.size() > 0) {
			 
			 return linkCheck;
			 
		 }else {
			 
				throw new HandledException("CHECK_PARAMETERS","No Report is present for this User Between "+DateStart +" and "+DateEnd);

		 }

	}

	@Override
	public List<LoginDto> getAllLoginReports(Date DateStart, Date DateEnd, Long userid) throws HandledException {
		
		User user = userRepository.findById(userid)
				 .orElseThrow(() -> new HandledException("NOT_FOUND", "User not found in Database with id : " + userid));
		
        List<LoginDto> login = loginReportsRepository.findByUserAndCreatedAtBetween(user, DateStart, DateEnd);
        
        if(login.size() > 0) {
			 
			 return login;
			 
		 }else {
			 
				throw new HandledException("CHECK_PARAMETERS","No Report is present for this User Between "+DateStart +" and "+DateEnd);

		 }
		
	}

	@Override
	public List<AnnouncementDto> getAllAnnouncementReports(Date DateStart, Date DateEnd, Long userid) throws HandledException {
		
		 List<AnnouncementDto> announcement = announcementReportsRepository.findByCreatedAtFilter(DateStart, DateEnd, userid);
		 
		 if(announcement.size() > 0) {
			 
			 return announcement;
			 
		 }else {
			 
				throw new HandledException("CHECK_PARAMETERS","No Report is present for this User Between "+DateStart +" and "+DateEnd);

		 }
		
	}

	@Override
	public List<TrainTransmissionDto> getAllTrainTransmissionReports(Date DateStart, Date DateEnd, Long userid) throws HandledException {
		
		List<TrainTransmissionDto> trainTransmission = trainTransmissionReportsRepository.findByCreatedAtFilter(DateStart, DateEnd, userid);
		
		if(trainTransmission.size() > 0) {
			 
			 return trainTransmission;
			 
		 }else {
			 
				throw new HandledException("CHECK_PARAMETERS","No Report is present for this User Between "+DateStart +" and "+DateEnd);

		 }
		
	}

	@Override
	public List<CgdbTransmissionDto> getAllCgdbTransmissionReports(Date DateStart, Date DateEnd, Long userid) throws HandledException {
		
		List<CgdbTransmissionDto> CgdbTransmission = cgdbTransmissionReportsRepository.findByCreatedAtFilter(DateStart, DateEnd, userid);
		
		if(CgdbTransmission.size() > 0) {
			 
			 return CgdbTransmission;
			 
		 }else {
			 
				throw new HandledException("CHECK_PARAMETERS","No Report is present for this User Between "+DateStart +" and "+DateEnd);

		 }
	}

	
	private HashMap<String, Object> customResponseUser( User userData) {
		
		HashMap<String, Object> userMap =  new HashMap<>();
		
		userMap.put("id", userData.getId());
		userMap.put("firstname", userData.getFirstname());
		userMap.put("lastname", userData.getLastname());

		return userMap;
		
	}
	
}
