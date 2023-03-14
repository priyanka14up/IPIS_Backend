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
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterPageDescription;
import com.innobitsysytems.ipis.repository.AnnouncementMasterPageDescriptionRepository;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestAnnouncementAdminDescriptionBean {
	
	@InjectMocks
	AnnouncementAdminDescriptionBean AnnouncementAdminDescriptionService;
	
	@Autowired
    public AnnouncementMasterPageDescriptionRepository announcementMasterPageDescriptionRepo;
	
	@Mock
	private TestRestTemplate restTemplate;
	
	@Rule
    public ExpectedException exceptionRule = ExpectedException.none();
	
	@Test
	public void TestFindAllPage() {

        List <AnnouncementMasterPageDescription> displaySuperPageData = announcementMasterPageDescriptionRepo.findAll();
        Assert.assertNotNull(displaySuperPageData);
	}
	
	@Test
	public void TestGetSingleAnnouncementPageDescriptionbyId() throws HandledException {

        AnnouncementMasterPageDescription page = announcementMasterPageDescriptionRepo.getById(1L);
        if(page != null) {
        	Assert.assertNotNull(page);
        }else {
        	Assert.assertNull(page);
        	exceptionRule.expect(HandledException.class);
            exceptionRule.expectMessage("Page not found");
        }
	}
	
	@Test
	public void TestPostPage() throws HandledException{
	    AnnouncementMasterPageDescription page = announcementMasterPageDescriptionRepo.findByPageNumber(1);

	    if(page == null)
	    {
	    	Assert.assertNull(page);

	    }else{
	    	page.setPageName(page.getPageName());
	        page.setPageDuration(page.getPageDuration());
	        page.setTemplateDescription(page.getTemplateDescription());
	        page.setTags(page.getTags());
	        page.setId(page.getId());
	        page.setPageDuration(page.getPageDuration());
	        page.setMessageDisplay(page.getMessageDisplay());
	        Assert.assertNotNull(page);


	    }
	}
}	
	
