package com.innobitsysytems.ipis.services.reports;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.AnnouncementDto;
import com.innobitsysytems.ipis.dto.CgdbTransmissionDto;
import com.innobitsysytems.ipis.dto.LinkCheckDto;
import com.innobitsysytems.ipis.dto.LoginDto;
import com.innobitsysytems.ipis.dto.TrainTransmissionDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.repository.UserRepository;
import com.innobitsysytems.ipis.repository.reports.AnnouncementReportsRepository;
import com.innobitsysytems.ipis.repository.reports.CgdbTransmissionReportsRepository;
import com.innobitsysytems.ipis.repository.reports.LinkCheckReportsRepository;
import com.innobitsysytems.ipis.repository.reports.LoginReportsRepository;
import com.innobitsysytems.ipis.repository.reports.TrainTransmissionReportsRepository;


	@ExtendWith(SpringExtension.class)
	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestReportBean {
		
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
		@Rule
		public ExpectedException exceptionRule = ExpectedException.none(); 
	
		@Test
		public void testGetAllUsers() {
			
			List<User> userData = userRepository.findAll();
			   
			List userList = new ArrayList<>();
			for (int i=0; i<userData.size(); i++) {
				userList.add(testcustomResponseUser(userData.get(i)));
			}
			Assert.assertNotNull(userList);
			
		}
	
		@Test
		public void testGetAllLinkCheckReports() throws HandledException, ParseException {
			
			String startDateVar="14/03/2022"; 
			String endDateVar="15/03/2022"; 
			 SimpleDateFormat startFormatter = new SimpleDateFormat("dd/MM/yyyy");  

			    Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDateVar);  


				    Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDateVar);  
				 
		
			 List<LinkCheckDto> linkCheck = linkCheckReportsRepository.findByCreatedAtFilter(startDate , endDate, 1L);
			
			 if(linkCheck.size() > 0) {
				 
				 Assert.assertNotNull(linkCheck);
				 
			 }else {
				
				 exceptionRule.expect(HandledException.class);
				    exceptionRule.expectMessage("No Report is present for this User");
		}
	}
		
		@Test
		public void testGetAllLoginReports() throws HandledException, ParseException {
			
			String startDatevar="14/03/2022"; 
			String endDatevar="15/03/2022"; 
			 SimpleDateFormat startformatter = new SimpleDateFormat("dd/MM/yyyy");  

			    Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDatevar);  


				    Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDatevar); 
				 
				    User user = userRepository.getById(1L);
				    
				    if(user == null)
				    {
				    	exceptionRule.expect(HandledException.class);
					    exceptionRule.expectMessage("No Report is present for this User");
			
				    }
				    else {	
				    	
			List<LoginDto> login = loginReportsRepository.findByUserAndCreatedAtBetween(user, startDate, endDate);	
			    
			 if(login.size() > 0) {
					
					Assert.assertNotNull(login);
					
				}
					 else {
						 
						 exceptionRule.expect(HandledException.class);
						    exceptionRule.expectMessage("No Report is present for this User");
					 }
		}
		}
		@Test
		public void testGetAllAnnouncementReports() throws HandledException, ParseException {
			
			String startDatevar="14/03/2022"; 
			String endDatevar="15/03/2022"; 
			 SimpleDateFormat startformatter = new SimpleDateFormat("dd/MM/yyyy");  

			    Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDatevar);  

			    Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDatevar);
			    
			 List<AnnouncementDto> announcement = announcementReportsRepository.findByCreatedAtFilter(startDate, endDate, 1L);

			 if(announcement.size() > 0) {
			
			Assert.assertNotNull(announcement);
		
		}
			 else {
				 
				 exceptionRule.expect(HandledException.class);
				    exceptionRule.expectMessage("No Report is present for this User");
			 }
		}
		
		
		@Test
		public void  testGetAllTrainTransmissionReports() throws HandledException, ParseException {
			
			 
			String startDatevar="14/03/2022"; 
			String endDatevar="15/03/2022"; 
			 SimpleDateFormat startformatter = new SimpleDateFormat("dd/MM/yyyy");  

			    Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDatevar);  

			    Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDatevar);
			    
			List<TrainTransmissionDto> trainTransmission = trainTransmissionReportsRepository.findByCreatedAtFilter(startDate, endDate, 1L);
				
			if(trainTransmission.size() > 0) {
				
			Assert.assertNotNull(trainTransmission);
		}
			
			else {
				
				 exceptionRule.expect(HandledException.class);
				    exceptionRule.expectMessage("No Report is present for this User");	
			}
		}
	
		@Test
		public void testGetAllCgdbTransmissionReports() throws HandledException, ParseException {
			
			 
			String startDatevar="14/03/2022"; 
			String endDatevar="15/03/2022"; 
			 SimpleDateFormat startformatter = new SimpleDateFormat("dd/MM/yyyy");  

			    Date startDate=new SimpleDateFormat("dd/MM/yyyy").parse(startDatevar);  

			    Date endDate=new SimpleDateFormat("dd/MM/yyyy").parse(endDatevar);
			    
			List<CgdbTransmissionDto> CgdbTransmission = cgdbTransmissionReportsRepository.findByCreatedAtFilter(startDate, endDate,1L);
			if(CgdbTransmission .size() > 0) {
				
			Assert.assertNotNull(CgdbTransmission);
		}
			else {
				
				 exceptionRule.expect(HandledException.class);
				    exceptionRule.expectMessage("No Report is present for this User");
			}
		}
	
		@Test
		private HashMap<String, Object> testcustomResponseUser( User userData) {
			
			HashMap<String, Object> userMap =  new HashMap<>();
			
			userMap.put("id", userData.getId());
			userMap.put("firstname", userData.getFirstname());
			userMap.put("lastname", userData.getLastname());

			return userMap;
			
		}

}