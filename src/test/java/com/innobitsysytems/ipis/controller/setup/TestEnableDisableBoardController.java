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
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestEnableDisableBoardController  {

	@Autowired
	private TestRestTemplate restTemplate;
	

	@Test
	public void testGetAllEnableDisableBoard() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange("/enable",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	
	@Test
	public void testCreateEnableDisableBoard() {
		EnableDisableBoard enabledisableboard = new EnableDisableBoard();
		enabledisableboard.setCreatedAt(null);
		enabledisableboard.setCreatedBy("admin");
		ResponseEntity<EnableDisableBoard> postResponse = restTemplate.postForEntity("/enable", enabledisableboard, EnableDisableBoard.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
}

