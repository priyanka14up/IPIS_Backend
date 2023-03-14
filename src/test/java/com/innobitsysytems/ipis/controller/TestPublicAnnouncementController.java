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
	public class TestPublicAnnouncementController  {

		@Autowired
		private TestRestTemplate restTemplate;

		@Test
		public void testGetAllFile() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/announcement",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());
		}

		@Test
		public void testGetFileById() {
			PublicAnnouncement announcement = restTemplate.getForObject("/announcement/-1", PublicAnnouncement.class);
			System.out.println(announcement.getFileName());
			Assert.assertNotNull(announcement);
		}

		@Test
		public void testCreateFile() {
			PublicAnnouncement announcement = new PublicAnnouncement();
			announcement.setCreatedAt(null);
			announcement.setCreatedBy("admin");
			announcement.setFileName("announcement");
		

			ResponseEntity<PublicAnnouncement> postResponse = restTemplate.postForEntity("/announcement", announcement, PublicAnnouncement.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
			
		}

		@Test
		public void testDeleteFile() {
			int id = 2;
			PublicAnnouncement announcement = restTemplate.getForObject("/announcement/" + id, PublicAnnouncement.class);
			Assert.assertNotNull(announcement);
			restTemplate.delete("/announcement/" + id);
			try {
				announcement = restTemplate.getForObject("/announcement/" + id, PublicAnnouncement.class);
			} catch (final HttpClientErrorException e) {
				Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
		
		
		@Test
		public void testMoveToPlaylist() {
			int id = 2;
			PublicAnnouncement announcement = restTemplate.getForObject("/announcement/" + id, PublicAnnouncement.class);
			Assert.assertNotNull(announcement);
			restTemplate.delete("/announcement/" + id);
			try {
				announcement = restTemplate.getForObject("/announcement/" + id, PublicAnnouncement.class);
			} catch (final HttpClientErrorException e) {
				Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
		
		@Test
		public void testGetAllPlaylist() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange( "/playlist",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());
		}

		@Test
		public void testGetMediaById() {
			AnnouncementPlaylist announcement = restTemplate.getForObject( "/playlist/-1", AnnouncementPlaylist.class);
			Assert.assertNotNull(announcement);
		}
		

		@Test
		public void testCreateDevice() {
			AnnouncementPlaylist announcement = new AnnouncementPlaylist();
			announcement.setCreatedAt(null);
			announcement.setCreatedBy("admin");
			announcement.setFileName("playlist");
			ResponseEntity<AnnouncementPlaylist> postResponse = restTemplate.postForEntity("/playlist", announcement, AnnouncementPlaylist.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
		}

		@Test
		public void testDeleteDevicev1() {
			int id = 2;
			AnnouncementPlaylist announcement = restTemplate.getForObject("/playlist/" + id, AnnouncementPlaylist.class);
			Assert.assertNotNull(announcement);
			restTemplate.delete("/playlist/" + id);

			try {
				announcement = restTemplate.getForObject("/playlist/" + id, AnnouncementPlaylist.class);
			} catch (final HttpClientErrorException e) {
				Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
		 
	    @Test
	    public void testMoveUp() {
			AnnouncementPlaylist announcement = new AnnouncementPlaylist();
			ResponseEntity<AnnouncementPlaylist> postResponse = restTemplate.postForEntity( "/playlist/moveup/4/6", announcement, AnnouncementPlaylist.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
		}
	    
	    @Test
	    public void testMoveDown() {
	   		AnnouncementPlaylist announcement = restTemplate.getForObject("/playlist/movedown/6/4", AnnouncementPlaylist.class);
	   		Assert.assertNotNull(announcement);
	   	}
	}


	
	

