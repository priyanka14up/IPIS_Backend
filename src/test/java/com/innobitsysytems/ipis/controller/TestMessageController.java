package com.innobitsysytems.ipis.controller;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
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
import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.repository.MessageRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestMessageController {

	@Autowired
	private TestRestTemplate restTemplate;
	@Autowired
	private MessageRepository mesgrepo;
	
	@Test
	public void testGetAllMessage() {
		HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);
			ResponseEntity<String> response = restTemplate.exchange("/message",
					HttpMethod.GET, entity, String.class);
			Assert.assertNotNull(response.getBody());
			}
	
	@Test
	public void testGetMessageById() {
		Message message = restTemplate.getForObject("/message/1149", Message.class);
		System.out.println(message.toString());
		Assert.assertNotNull(message);	
	}
	
	@Test
	public void testGetMsgDevicePlatform() {
		Message message = restTemplate.getForObject("/message/ivd", Message.class);
		System.out.println(message.toString());
		Assert.assertNotNull(message);
	}
	
	@Test
	public void testGetMsgDeviceId() {
		Message message = restTemplate.getForObject("/message/ivd/2", Message.class);
		Assert.assertNotNull(mesgrepo);
	}
	@Test
	public void testCreateMessageData() {
		Message message = new Message();
		message.setCreatedBy("2");
		message.setDisplayBoard("ovd");
		message.setDeviceId("55");
		message.setMessageEnglish("train is comming");
		message.setMessageHindi("हाय ओव्डी");
		message.setMessageRegional("ਹੈਲੋ Ovd");
		message.setMessageEffect("stable");
		ResponseEntity<Message> postResponse = restTemplate.postForEntity("/message", message, Message.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());	
	}
	
	@Test
	public void testDisplayMessage() {
		Message message = new Message();
		message.setDisplayBoard("OVD");
		ResponseEntity<Message> postResponse = restTemplate.postForEntity("/message/ovd/12", message, Message.class);
		Assert.assertNotNull(postResponse);
		Assert.assertNotNull(postResponse.getBody());	
	}
	
	@Test
	public void testUpdateMessage() {
		Message message = new Message();
		int id = 1666;
		Message msg = restTemplate.getForObject("/message/" + id, Message.class);
		msg.setDisplayStatus(false);
		msg.setMessageEnglish("message check");
		restTemplate.put("/message/" + id, msg);
		Message updatedMessage = restTemplate.getForObject("/messasge/" + id, Message.class);
		Assert.assertNotNull(updatedMessage);		
	}
	
	@Test
	public void testDeleteMessageData() {
		int id = 2;
		Message msg = restTemplate.getForObject("/message/" + id, Message.class);
		Assert.assertNotNull(msg);
		restTemplate.delete("/message/" + id);
		try {
			msg = restTemplate.getForObject("/message/" + id, Message.class);
		} catch (final HttpClientErrorException e) {
			Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
		}
	}
	}

