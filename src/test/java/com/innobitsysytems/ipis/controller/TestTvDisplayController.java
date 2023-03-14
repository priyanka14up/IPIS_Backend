package com.innobitsysytems.ipis.controller;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.dto.DisplayMediaDto;
import com.innobitsysytems.ipis.dto.MediaQueueDto;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestTvDisplayController {

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

		@Test
		public void testGetAllMedia() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			ResponseEntity<String> response = restTemplate.exchange("/media/ivd", HttpMethod.GET, entity,
					String.class);
			Assert.assertNotNull(response.getBody());
		}

		@Test
		public void testGetMediaById() {

			DisplayMediaDto displayMediaDto = restTemplate.getForObject("/filemedia/1564", DisplayMediaDto.class);
			Assert.assertNotNull(displayMediaDto);
		}

		@Test
		public void createMediaData() {
			DisplayMediaDto displayMediaDto = new DisplayMediaDto();
			displayMediaDto.setMediaLocation("C://Users/Documents/Folder35");
			displayMediaDto.setMediaName("Filename");
			ResponseEntity<DisplayMediaDto> postResponse = restTemplate.postForEntity("/filemedia/ivd", displayMediaDto, DisplayMediaDto.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
		}

		@Test
		public void testDeleteMediaeData() {
			int id = 1566;
			DisplayMediaDto displayMediaDto = restTemplate.getForObject("/filemedia/" + id, DisplayMediaDto.class);
			Assert.assertNotNull(displayMediaDto);
			restTemplate.delete("/filemedia/" + id);

			try {
				displayMediaDto = restTemplate.getForObject("/filemedia/" + id, DisplayMediaDto.class);
			} catch (final HttpClientErrorException e) {
				Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
		
		@Test
		public void movetoplaylist() {
			DisplayMediaDto displayMediaDto = new DisplayMediaDto();
			ResponseEntity<DisplayMediaDto> postResponse = restTemplate.postForEntity("/movetoplaylist/1566", displayMediaDto, DisplayMediaDto.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
		}
		
		@Test
		public void testGetAllQueue() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/queuemedia/ivd",
					HttpMethod.GET, entity, String.class);

			Assert.assertNotNull(response.getBody());
		}
		@Test
		public void testGetQueueById() {

			MediaQueueDto mediaQueueDto = restTemplate.getForObject("/queue/1666", MediaQueueDto.class);
			Assert.assertNotNull(mediaQueueDto);
		}
		
		@Test
		public void testCreateQueueData(){
			MediaQueueDto mediaQueueDto =new MediaQueueDto();
			mediaQueueDto.setMediaName("ABC");
			mediaQueueDto.setMediaLocation("C://Users/Documents/Folder35");
			mediaQueueDto.setIsPlaying(true);
			ResponseEntity<MediaQueueDto> postResponse = restTemplate.postForEntity( "/queue/ivd", mediaQueueDto, MediaQueueDto.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());

		}
		
		@Test
		public void testPutQueueData() {
			int id=1666;
			MediaQueueDto mediaQueueDto = restTemplate.getForObject("/queue/" + id, MediaQueueDto.class);
			mediaQueueDto.setMediaName("media1");
			restTemplate.put("/users/" + id,mediaQueueDto );

			MediaQueueDto updatedMediaQueue = restTemplate.getForObject("/queue/" + id, MediaQueueDto.class);
			Assert.assertNotNull(updatedMediaQueue);
		}
		
		@Test
		public void testMoveUp() {

			MediaQueueDto mediaQueueDto = restTemplate.getForObject("/moveup/1554/1666", MediaQueueDto.class);
			Assert.assertNotNull(mediaQueueDto);
		}
		
		@Test
		public void testMoveDown() {
			MediaQueueDto mediaQueueDto = restTemplate.getForObject("/movedown/1555/1554", MediaQueueDto.class);
			Assert.assertNotNull(mediaQueueDto);
			
		}
		
	
	
}
