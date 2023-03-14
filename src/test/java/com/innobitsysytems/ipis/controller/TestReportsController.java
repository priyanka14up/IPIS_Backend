package com.innobitsysytems.ipis.controller;

	import org.junit.jupiter.api.Test;
	import org.springframework.boot.test.context.SpringBootTest;

	import java.util.List;

	import org.junit.Assert;
	import org.junit.Before;
	import org.junit.runner.RunWith;
	import org.mockito.Mock;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.web.client.TestRestTemplate;
	import org.springframework.boot.web.server.LocalServerPort;
	import org.springframework.http.*;
	import org.springframework.test.context.junit4.SpringRunner;
	import org.springframework.test.web.servlet.MockMvc;
	import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
	import org.springframework.web.client.HttpClientErrorException;

	import com.innobitsysytems.ipis.IpisApplication;
	import com.innobitsysytems.ipis.errors.ResponseHandler;
	import com.innobitsysytems.ipis.exception.HandledException;
	import com.innobitsysytems.ipis.model.User;
	import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class TestReportsController  {

		@Autowired
		private TestRestTemplate restTemplate;


		@Test
		public void testGetAllUsers() {
			
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/reports/users",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());	
		}

		@Test
		public void testGetAllLinkCheckReports() {

			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/reports/linkCheck",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());	
		}

		@Test
		public void testGetAllLoginReports() {
			
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange( "/reports/login",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());		
		}

		@Test
		public void testGetAllAnnouncementReports() {
			
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/reports/announcement",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());	
		}
		
		@Test
		public void testGetAllTrainTransmissionReports() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/reports/traintransmission",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());
			}
		
		@Test
		public void testGetAllCgdbTransmissionReports() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange( "/reports/cgdbtransmission",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());
		}
}
