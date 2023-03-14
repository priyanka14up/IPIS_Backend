/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Master Service
 */
package com.innobitsysytems.ipis.services.superAdmin;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.innobitsysytems.ipis.dto.SuperMasterDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;

public interface AnnouncementMasterService {
	
	
	public List<SuperMasterDto> allAnnouncementMasterData();

	public SuperMasterDto getSingleAnnouncementMaster(Long id) throws HandledException;

	public SuperMasterDto createAnnouncementMaster(HttpServletRequest request, AnnouncementMasterData announcementMasterData)
			throws HandledException;

	public SuperMasterDto putAnnouncementMaster(Long id, AnnouncementMasterData Amd) throws HandledException;
 
	public Map<String, Boolean> deleteAnnouncementMaster(Long id) throws HandledException;

	
	
    
	
}
