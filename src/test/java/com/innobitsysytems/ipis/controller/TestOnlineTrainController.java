package com.innobitsysytems.ipis.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;

@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestOnlineTrainController {
	
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testGetAllOnlineTrainData() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<OnlineTrain> entity = new HttpEntity<OnlineTrain>(null, headers);

		ResponseEntity<OnlineTrain> response = restTemplate.exchange("/onlineTrain",
				HttpMethod.GET, entity, OnlineTrain.class);

		
		Assert.assertNotNull(response.getBody());

	}
	
	@Test
	public void testPutOnlineTrainData() {
		OnlineTrain onlineTrain = new OnlineTrain();
		int TrainNumber = 12356;

	 onlineTrain= restTemplate.getForObject("/onlineTrain/" + TrainNumber, OnlineTrain.class);
	 onlineTrain.setTrainName("Tejas");
	 onlineTrain.setTrainStatus("Running");
		restTemplate.put("/onlineTrain/" + TrainNumber, onlineTrain);
		OnlineTrain updatetrain = restTemplate.getForObject("/onlineTrain/" + TrainNumber, OnlineTrain.class);
		Assert.assertNotNull(updatetrain);	

	}
	
	@Test
	public void deleteOnlineTrainData() {
		int trainNumber = 12356;
		OnlineTrain onlineTrain = restTemplate.getForObject("/onlineTrain/" + trainNumber, OnlineTrain.class);
		Assert.assertNotNull(onlineTrain);
		restTemplate.delete("/onlineTrain/" + trainNumber);
		try {
			onlineTrain = restTemplate.getForObject("/onlineTrain/" + trainNumber, OnlineTrain.class);
		} 
		catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
		
	}

	}
	

