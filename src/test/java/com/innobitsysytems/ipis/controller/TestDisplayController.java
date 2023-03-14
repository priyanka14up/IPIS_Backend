package com.innobitsysytems.ipis.controller;

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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.HttpClientErrorException;
import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.model.tvdisplay.Display;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestDisplayController {
	

	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	public void testGetAllMessage() {
		HttpHeaders headers = new HttpHeaders();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);

		ResponseEntity<String> response = restTemplate.exchange("/display",
				HttpMethod.GET, entity, String.class);
		Assert.assertNotNull(response.getBody());
	}
	@Test
	public void testGetMessageById() {
		String displayType = "IVD";
		DisplayDto displayDto= restTemplate.getForObject("/display/displayType", DisplayDto.class);
		Assert.assertNotNull(displayDto);
	}

	
		@Test
		public void testCreateMessageData() {
	DisplayDto displayDto = new DisplayDto();
	displayDto.setDisplayType("IVD");
	displayDto.setEnableDisplay(true);
	displayDto.setShowMessage(false);
	displayDto.setShowCoach(true);
	displayDto.setShowMedia(true);
	displayDto.setDisplayTimeout(8);
	displayDto.setGridRowHeight(80);
	displayDto.setTrainNoWidth(15);
	displayDto.setExpectedTimeWidth(15);
	displayDto.setArrivalDepartureWidth(15);
	displayDto.setPlatformWidth(15);
	displayDto.setGridPageTime(15);
	displayDto.setMessageScrollSpeed(15);
	displayDto.setMediaStartIntervalTime(15);
			

		ResponseEntity<DisplayDto> postResponse = restTemplate.postForEntity("/displays", displayDto, DisplayDto.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());
		
	}

    

	

}
