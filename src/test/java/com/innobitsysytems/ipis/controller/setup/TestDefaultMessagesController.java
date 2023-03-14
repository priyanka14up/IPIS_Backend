package com.innobitsysytems.ipis.controller.setup;

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
import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.model.setup.DefaultMessages;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDefaultMessagesController  {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllDefaultMessages() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange("/defaultmessages",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
		}

	@Test
	public void testCreateDefaultMessages() {
		DefaultMessages defaultmessages = new DefaultMessages();
		defaultmessages.setCreatedAt(null);
		defaultmessages.setCreatedBy("admin");
		ResponseEntity<DefaultMessages> postResponse = restTemplate.postForEntity("/defaultmessages", defaultmessages, DefaultMessages.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

    @Test
    public void testSetDefaultMessages() {
    	int id = 0;
    	DefaultMessages defaultmessages = restTemplate.getForObject("/setmessages/" + id, DefaultMessages.class);
    	restTemplate.put("/setmessages/" + id, defaultmessages);
		DefaultMessages updatedMsg = restTemplate.getForObject("/setmessages/" + id, DefaultMessages.class);
		Assert.assertNotNull(updatedMsg);
    }
	}

