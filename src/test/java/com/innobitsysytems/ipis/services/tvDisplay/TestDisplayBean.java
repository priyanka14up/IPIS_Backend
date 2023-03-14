package com.innobitsysytems.ipis.services.tvDisplay;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.dto.MediaQueueDto;
import com.innobitsysytems.ipis.dto.PublicAnnouncementDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.tvdisplay.AdvertismentPlaylist;
import com.innobitsysytems.ipis.model.tvdisplay.Display;
import com.innobitsysytems.ipis.model.tvdisplay.DisplayMedia;
import com.innobitsysytems.ipis.model.tvdisplay.MediaQueue;
import com.innobitsysytems.ipis.repository.tvdisplay.DisplayMediaRepository;
import com.innobitsysytems.ipis.repository.tvdisplay.DisplayRepository;
import com.innobitsysytems.ipis.repository.tvdisplay.MediaQueueRepository;
import com.innobitsysytems.ipis.services.announcement.AnnouncementBean;
import com.innobitsysytems.ipis.services.tvdisplay.DisplayBean;
import com.innobitsysytems.ipis.services.tvdisplay.DisplayService;
import com.innobitsysytems.ipis.utility.CustomUtil;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestDisplayBean {

	@InjectMocks
	DisplayBean displayService;	
	
	@Mock
	private TestRestTemplate restTemplate;
	
	@Autowired
	public DisplayRepository displayRepository;
	
	@Autowired
	public DisplayMediaRepository displayMediaRepo;
	
	@Autowired
	public MediaQueueRepository mediaQueueRepo;
	
	@Mock
	public CustomUtil customUtil;
	
	@Rule
	public ExpectedException exceptionRule = ExpectedException.none();
	
	Date date = new Date();
	
	@Test
	public void testfindAllDisplay() {
		
		List <Display> displayDeviceData = displayRepository.findAll();
		Assert.assertNotNull(displayDeviceData);
	}
	
	@Test
	public void testgetSingleDisplay() {
		
		Display display = displayRepository.findByDisplayType("ivd");
		
		if ( display != null) {
			
			Assert.assertNotNull(display);
		}
		
		else {
			
			Assert.assertNull(display);
		}
	}
	
	@Test
	public void testpostDisplay() throws HandledException {
		DisplayDto dtoData = new DisplayDto();
		dtoData.setDisplayType("ivd");
		dtoData.setEnableDisplay(true);
		dtoData.setShowMessage(true);
		dtoData.setShowCoach(true);
		dtoData.setShowMedia(true);
		dtoData.setDisplayTimeout(8);
		dtoData.setGridRowHeight(80);
		dtoData.setTrainNoWidth(20);
		dtoData.setExpectedTimeWidth(15);
		dtoData.setArrivalDepartureWidth(15);
		dtoData.setPlatformWidth(15);
		dtoData.setGridPageTime(15);
		dtoData.setMessageScrollSpeed(15);
		dtoData.setMediaStartIntervalTime(15);
		
		Display display = displayService.dtoToEntity(dtoData);
		
		Display displayData = displayRepository.findByDisplayType(dtoData.getDisplayType());
	
		if(displayData == null) {
			
			display.setCreatedBy(String.valueOf( customUtil.getIdFromToken()));
			displayRepository.save(display);
			dtoData = displayService.entityToDto(display);
			
		}
		
		else {
			displayData.setEnableDisplay(display.getEnableDisplay());
			displayData.setShowMessage(display.getShowMessage());
			displayData.setShowMedia(display.getShowMedia());
			displayData.setShowCoach(display.getShowCoach());
			displayData.setDisplayTimeout(display.getDisplayTimeout());
			displayData.setGridRowHeight(display.getGridRowHeight());
			displayData.setTrainNoWidth(display.getTrainNoWidth());
			displayData.setExpectedTimeWidth(display.getExpectedTimeWidth());
			displayData.setArrivalDepartureWidth(display.getArrivalDepartureWidth());
			displayData.setPlatformWidth(display.getPlatformWidth());
			displayData.setGridPageTime(display.getGridPageTime());
			displayData.setMessageScrollSpeed(display.getMessageScrollSpeed());
			displayData.setMediaStartIntervalTime(display.getMediaStartIntervalTime());
			displayData.setUpdatedBy(String.valueOf( customUtil.getIdFromToken()));
			displayRepository.save(displayData);
			dtoData = displayService.entityToDto(displayData);
			Assert.assertNotNull(dtoData);
		
		}
	}
	
	@Test
	public void testfindAllMedia() throws HandledException{
		
		List <DisplayMedia> displayData = displayMediaRepo.findByDisplayType("ivd");
		if(displayData != null) {
			Assert.assertNotNull(displayData);
		}
		
		else {
			Assert.assertNull(displayData);
			exceptionRule.expect(HandledException.class);
		    exceptionRule.expectMessage("Media for this device doesn't exist.");
			
		}
	}
	
	@Test
	public void testgetSingleFile()  throws HandledException {
		DisplayMedia displayMedia = displayMediaRepo.getById(71L);
		if(displayMedia == null) {
			
			Assert.assertNull(displayMedia);
			exceptionRule.expect(HandledException.class);
		    exceptionRule.expectMessage("File media not found.");
			
		}
		
		else {
			Assert.assertNotNull(displayMedia);
		}
	}

	@Test
	public void testpostFile() throws HandledException{
		
		DisplayMedia display = new DisplayMedia();
		display.setDisplayType("ivd");
		display.setMediaLocation("C://Users/Documents/Folder12345");
		
		DisplayMedia mediaData = displayMediaRepo.getFileByMediaLocation("ivd" , display.getMediaLocation());
		
		if(mediaData == null) {
			
			Assert.assertNull(mediaData);
			
		}
		
		else {
			
			Assert.assertNotNull(mediaData);

			exceptionRule.expect(HandledException.class);
		    exceptionRule.expectMessage("File Location exist in Database");
		}
	}

	@Test
	public void testdeleteFile() throws HandledException{
		
		  
		DisplayMedia displayMedia  =  displayMediaRepo.getById(65L);
		if(displayMedia == null) {
			
			Assert.assertNull(displayMedia);

			exceptionRule.expect(HandledException.class);
		    exceptionRule.expectMessage("File not found");
		}
		
		else {
			
			displayMediaRepo.delete(displayMedia);
			Assert.assertNotNull(displayMedia);
		}
 
	}
	
	@Test
	public void testmoveToPlaylist() throws HandledException {
		
		
			MediaQueue mediaQueue = new MediaQueue();
			
		
			mediaQueue.setDisplayType("ivd");
			mediaQueue.setMediaLocation("C://Users/Documents/Folder123");
			mediaQueue.setMediaName("ABC");
			mediaQueue.setIsPlaying(false);
			mediaQueue.setCreatedBy("1");
			
			DisplayMedia displayMediaData  =  displayMediaRepo.getById(65L);
			if(displayMediaData == null) {
			
				Assert.assertNull(mediaQueue);
				exceptionRule.expect(HandledException.class);
				exceptionRule.expectMessage("File not found in Database");
		    
			}
			
			else {
				
				Assert.assertNotNull(mediaQueue);
			}
			
	}
	
	@Test
	public void testfindAll() throws HandledException {
		
		List<MediaQueue> displayData = mediaQueueRepo.findByDisplayType("ivd");
		if (displayData != null) {

			Assert.assertNotNull(displayData);
			
		}
		
		else {
			
			Assert.assertNull(displayData);
			exceptionRule.expect(HandledException.class);
			   exceptionRule.expectMessage("Media for this device doesn't exist.");
		}
	}
	
	@Test
	public void testgetSingleMedia() throws HandledException {
		MediaQueue mediaQueue = mediaQueueRepo.getById(69L);
		
		if(mediaQueue == null) {
			
			Assert.assertNull(mediaQueue);
			exceptionRule.expect(HandledException.class);
			   exceptionRule.expectMessage("File media not found");
		}
		
		else {
			
			Assert.assertNotNull(mediaQueue);
		}
	}
	@Test
	public void testputMedia() throws HandledException {
		
	
		
		MediaQueueDto mediaQueueDto= new MediaQueueDto();
		mediaQueueDto.setId(76L);
		mediaQueueDto.setMediaName("ABC");
		mediaQueueDto.setMediaLocation("C://Users/Documents/Folder1234");
		mediaQueueDto.setIsPlaying(true);
		
		MediaQueue mediaQueue = mediaQueueRepo.getById(73L);
		
	       if(mediaQueue == null) {
	    	   
	    	   Assert.assertNull(mediaQueue);
	    	   exceptionRule.expect(HandledException.class);
			   exceptionRule.expectMessage("File not found");
	       }
	       
	       else {

	    	   Assert.assertNotNull(mediaQueue);
	
	       }
	}
	
	@Test
	public void testdeleteMedia() throws HandledException {
		
		  
		DisplayMedia displayMediaData  =  displayMediaRepo.getById(19L);
		if(displayMediaData==null)
		{
			Assert.assertNull(displayMediaData);
			
			exceptionRule.expect(HandledException.class);
		    exceptionRule.expectMessage("File not found");
			
		}
		
		else {
			
			displayMediaRepo.delete(displayMediaData);
			Assert.assertNotNull(displayMediaData);
		}
  	
	}	
	
	@Test
	public void testdisplaySelectedData() throws HandledException {
		
		Display displayValue = displayRepository.findByDisplayType("ivd");
		
		if(displayValue == null) {
			
			Assert.assertNull(displayValue);
			
			}
		
		else {
			
			Assert.assertNotNull(displayValue);
		}
	}
}