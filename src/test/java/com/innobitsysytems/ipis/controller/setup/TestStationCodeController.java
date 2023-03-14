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
import com.innobitsysytems.ipis.model.setup.Intensity;
import com.innobitsysytems.ipis.model.setup.StationCode;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestStationCodeController  {

	@Autowired
	private TestRestTemplate restTemplate;

	@Test
	public void testGetAllStationCode() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange( "/stationcode",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}

	@Test
	public void testGetStationCodeById() {
		StationCode stationcode = restTemplate.getForObject( "/stationcode/-1", StationCode.class);
		Assert.assertNotNull(stationcode);
	}

	@Test
	public void testCreateStationCode() {
		StationCode stationcode = new StationCode();
		stationcode.setCreatedAt(null);
		stationcode.setCreatedBy("admin");
		ResponseEntity<StationCode> postResponse = restTemplate.postForEntity( "/stationcode", stationcode, StationCode.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}

    
    @Test
    public void testupdateStationCode() {

    	int id = 0;
    	StationCode stationcode = restTemplate.getForObject( "/stationcode/-1" + id, StationCode.class);
    	restTemplate.put( "/stationcode/" + id, stationcode);
		StationCode updatedStationCode = restTemplate.getForObject( "/stationcode/" + id, StationCode.class);
		Assert.assertNotNull(updatedStationCode);
    	
	}


	@Test
	public void testDeleteStation() {
		int id = 2;
		StationCode stationcode = restTemplate.getForObject( "/stationcode/" + id, StationCode.class);
		Assert.assertNotNull(stationcode);
		restTemplate.delete( "/playlist/" + id);
		try {
			stationcode = restTemplate.getForObject( "/stationcode/" + id, StationCode.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
}

