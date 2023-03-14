package com.innobitsysytems.ipis.controller.superAdmin;
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
import com.innobitsysytems.ipis.model.setup.TrainStatus;
import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAnnouncementMasterDataController  {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllAnnouncementMasterData() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange("/announcementMasterData",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void getAnnouncementMasterDataById() {
		AnnouncementMasterData announcement = restTemplate.getForObject("/announcementMasterData/-1", AnnouncementMasterData.class);
		Assert.assertNotNull(announcement);
	}

	@Test
	public void testCreateAnnouncementMasterData() {
		AnnouncementMasterData announcement = new AnnouncementMasterData();
		announcement.setCreatedAt(null);
		announcement.setCreatedBy("admin");
		ResponseEntity<AnnouncementMasterData> postResponse = restTemplate.postForEntity("/announcementMasterData", announcement, AnnouncementMasterData.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

    
    @Test
    public void updateAnnouncementMasterData() {
    	int id = 0;
    	AnnouncementMasterData announcement = restTemplate.getForObject("/announcementMasterData/-1" + id, AnnouncementMasterData.class);
		restTemplate.put("/trainstatus/" + id, announcement);
		AnnouncementMasterData updatedMasterData = restTemplate.getForObject("/announcementMasterData/" + id, AnnouncementMasterData.class);
		Assert.assertNotNull(updatedMasterData);
  
	}
 	

	@Test
	public void testDeleteAnnouncementMasterData() {
		int id = 2;
		AnnouncementMasterData announcement = restTemplate.getForObject("/announcementMasterData/" + id, AnnouncementMasterData.class);
		Assert.assertNotNull(announcement);
		restTemplate.delete("/announcementMasterData/" + id);
		try {
			announcement = restTemplate.getForObject("/announcementMasterData/" + id, AnnouncementMasterData.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
	}

