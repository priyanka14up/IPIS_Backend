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
import com.innobitsysytems.ipis.model.setup.TrainName;
import com.innobitsysytems.ipis.model.setup.TrainStatus;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTrainStatusController  {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllTrainStatus() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange("/trainstatus",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetTrainStatusById() {
		TrainStatus trainstatus = restTemplate.getForObject("/trainstatus/-1", TrainStatus.class);
		Assert.assertNotNull(trainstatus);
	}

	@Test
	public void testCreateTrainStatus() {
		TrainStatus trainstatus = new TrainStatus();
		trainstatus.setCreatedAt(null);
		trainstatus.setCreatedBy("admin");
		ResponseEntity<TrainStatus> postResponse = restTemplate.postForEntity("/trainstatus", trainstatus, TrainStatus.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

    
    @Test
    public void testUpdateTrainStatus() {
    	int id = 0;
    	TrainStatus trainstatus = restTemplate.getForObject("/trainstatus/-1" + id, TrainStatus.class);
    	restTemplate.put("/trainstatus/" + id, trainstatus);
    	TrainStatus updatedTrainStatus = restTemplate.getForObject("/trainstatus/" + id, TrainStatus.class);
		Assert.assertNotNull(updatedTrainStatus);
	}
 
	@Test
	public void testDeleteTrainStatus() {
		int id = 2;
		TrainStatus trainstatus = restTemplate.getForObject("/trainstatus/" + id, TrainStatus.class);
		Assert.assertNotNull(trainstatus);
		restTemplate.delete("/trainstatus/" + id);
		try {
			trainstatus = restTemplate.getForObject("/trainstatus/" + id, TrainStatus.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
	
}

