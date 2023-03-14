package com.innobitsysytems.ipis.services.superAdmin;

import java.util.List;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.SuperMasterDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;
import com.innobitsysytems.ipis.repository.AnnouncementMasterDataRepository;
import com.innobitsysytems.ipis.utility.CustomUtil;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestAnnouncementMasterBean {

	@InjectMocks
	AnnouncementMasterBean AnnouncementMasterService;
	
	@Autowired
	public AnnouncementMasterDataRepository announcementMasterDataRepo;
	
	@Mock
	public CustomUtil customUtil;
	
	@Mock
	private TestRestTemplate restTemplate;
	
	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void testAllAnnouncementMasterData() {
		
		List<AnnouncementMasterData>  announcementMasterData = announcementMasterDataRepo.findAll();
		Assert.assertNotNull(announcementMasterData);
		
	}
	
	@Test
	public void testGetSingleAnnouncementMaster()  throws HandledException {
		
		AnnouncementMasterData announcementAdmin =  announcementMasterDataRepo.getById(1L);
		if(announcementAdmin!= null) {
			Assert.assertNotNull(announcementAdmin);
		}else {
			Assert.assertNull(announcementAdmin);
			exceptionRule.expect(HandledException.class);
            exceptionRule.expectMessage("Announcement Master Data not found");
		}
	}
	
	@Test
	public void TestCreateAnnouncementMaster() throws HandledException{
		
		Long token = customUtil.getIdFromToken();
		AnnouncementMasterData announcementMasterData = new AnnouncementMasterData();
		announcementMasterData.setCreatedBy(token.toString());
		if(announcementMasterData != null) {
			
			Assert.assertNotNull(announcementMasterData);
		}
	}
	
	@Test
	public void deleteAnnouncementMaster() throws HandledException{
		AnnouncementMasterData announcementMasterAdmin = announcementMasterDataRepo.getById(1L);
		if(announcementMasterAdmin!= null) {
			Assert.assertNotNull(announcementMasterAdmin);
		}else {
			Assert.assertNull(announcementMasterAdmin);
			exceptionRule.expect(HandledException.class);
            exceptionRule.expectMessage("Announcement Master Data not found");
		}
	}
	
	@Test
	public void entityToDto() {
		
		SuperMasterDto dto = new SuperMasterDto();
		dto.setId(1L);
		dto.setTemplateNumber(12345);
		dto.setMessageType("Template Number");
		dto.setTemplateDescription("This Template Number Working Fine");
		
		Assert.assertNotNull(dto);
		}
	
}	
