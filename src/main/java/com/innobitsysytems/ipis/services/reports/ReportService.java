/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2021
 * Purpose: Report Service
 */
package com.innobitsysytems.ipis.services.reports;

import java.util.*;

import com.innobitsysytems.ipis.dto.AnnouncementDto;
import com.innobitsysytems.ipis.dto.CgdbTransmissionDto;
import com.innobitsysytems.ipis.dto.LinkCheckDto;
import com.innobitsysytems.ipis.dto.LoginDto;
import com.innobitsysytems.ipis.dto.TrainTransmissionDto;
import com.innobitsysytems.ipis.exception.HandledException;


public interface ReportService {

	public List getAllUsers();
	
	public List<LinkCheckDto> getAllLinkCheckReports(Date DateStart, Date DateEnd, Long id)throws HandledException;
	
	public List<LoginDto> getAllLoginReports(Date DateStart, Date DateEnd, Long id) throws HandledException;
	
	public List<AnnouncementDto> getAllAnnouncementReports(Date DateStart, Date DateEnd, Long id) throws HandledException;
	
	public List<TrainTransmissionDto> getAllTrainTransmissionReports(Date DateStart, Date DateEnd, Long id) throws HandledException;
	
	public List<CgdbTransmissionDto> getAllCgdbTransmissionReports(Date DateStart, Date DateEnd, Long id) throws HandledException;
	
}
