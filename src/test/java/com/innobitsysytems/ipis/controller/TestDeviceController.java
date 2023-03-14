package com.innobitsysytems.ipis.controller;

import javax.validation.constraints.NotBlank;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.model.LinkStatus;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;
import com.innobitsysytems.ipis.services.device.DeviceService;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDeviceController {
	@Autowired
	private TestRestTemplate restTemplate;
	
	 @Mock
	    DeviceService deviceService;

	@Test
	public void testGetAllDevices() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		ResponseEntity<String> response = restTemplate.exchange("/devices",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}
	
	@Test
	public void testGetDevicesById() {
		LinkStatus device = restTemplate.getForObject("/devices/-1", LinkStatus.class);
		Assert.assertNotNull(device);
	}
	
	@Test
	public void testcreateDevice() {
		LinkStatus device = new LinkStatus();
		ResponseEntity<LinkStatus> postResponse = restTemplate.postForEntity("/devices",device, LinkStatus.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
	}
	
	@Test
	public void testUpdateDevice() {
		int id = 0;
		LinkStatus device = restTemplate.getForObject("/devices/" + id, LinkStatus.class);
		restTemplate.put("/devices/" + id, device);
		LinkStatus updatedDevice = restTemplate.getForObject("/devices/" + id, LinkStatus.class);
		Assert.assertNotNull(updatedDevice);
	}
}
