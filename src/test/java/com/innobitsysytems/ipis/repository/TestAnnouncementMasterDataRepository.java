package com.innobitsysytems.ipis.repository;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;

@DataJpaTest
public class TestAnnouncementMasterDataRepository {
	
	private AnnouncementMasterDataRepository announcementMasterDataRepository;
	
	public void saveAnnouncementMasterTest()
	{
		AnnouncementMasterData announcementMasterData=new AnnouncementMasterData();
		announcementMasterData.setTemplateNumber(1);
		announcementMasterData.setMessageType("arriving");
		announcementMasterData.setTemplateDescription("description");
		//announcementMasterData.setCreatedAt();
		
		
	}
	

}
