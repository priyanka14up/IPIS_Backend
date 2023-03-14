/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcemen tAdmin Description Service
 */
package com.innobitsysytems.ipis.services.superAdmin;

import com.innobitsysytems.ipis.dto.SuperPageDto;
import com.innobitsysytems.ipis.exception.HandledException;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface AnnouncementAdminDescriptionService {
	
	public List<SuperPageDto> findAllPage();

	public SuperPageDto getSingleAnnouncementPageDescriptionbyId(Long id) throws  HandledException;

	public SuperPageDto postPage(HttpServletRequest request,SuperPageDto superPageDto) throws HandledException;

}

