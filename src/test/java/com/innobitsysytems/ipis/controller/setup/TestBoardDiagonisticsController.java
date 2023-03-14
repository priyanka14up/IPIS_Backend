package com.innobitsysytems.ipis.controller.setup;

	import org.junit.jupiter.api.Test;
	import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
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
import com.innobitsysytems.ipis.dto.BoardDiagnosticDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
	import com.innobitsysytems.ipis.exception.HandledException;
	import com.innobitsysytems.ipis.model.User;
	import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
	import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class TestBoardDiagonisticsController  {

		@Autowired
		private TestRestTemplate restTemplate;
		
		Date dateTime = new Date();

		@Test
		public void testCreateBoardDiagonistics() {
			BoardDiagnosticDto boardDiagnostic = new BoardDiagnosticDto();
			boardDiagnostic.setBoardType("cds");
			boardDiagnostic.setPlatformNo(1);
			boardDiagnostic.setSentData("data");
			ResponseEntity<BoardDiagnosticDto> postResponse = restTemplate.postForEntity("/board", boardDiagnostic, BoardDiagnosticDto.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
		}

	}


	
	

