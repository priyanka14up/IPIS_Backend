/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Service
 */
package com.innobitsysytems.ipis.services.tvdisplay;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.dto.DisplayMediaDto;
import com.innobitsysytems.ipis.dto.MediaQueueDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.tvdisplay.MediaQueue;

public interface DisplayService {


	public List<DisplayDto> findAllDisplay() throws HandledException;
	
	public DisplayDto getSingleDisplay(String displayType) throws HandledException;
	
	public DisplayDto postDisplay(HttpServletRequest request, DisplayDto displayDto) throws HandledException;
	
	//DisplayMedia Function Declaration

	public List<DisplayMediaDto> findAllMedia( String displayType )throws HandledException;
	
	public DisplayMediaDto getSingleFile( Long id ) throws HandledException;
	
	public DisplayMediaDto postFile( String displayType, HttpServletRequest request, DisplayMediaDto displayMediaDto ) throws HandledException;
	
	public Map<String, Boolean> deleteFile(Long id) throws HandledException;
	
	public HashMap<String, Object> moveToPlaylist(Long id) throws HandledException, Exception;
	
	//QueueMedia Function Declaration
	
	public List<MediaQueueDto> findAll( String displayType ) throws HandledException;
	
	public MediaQueueDto getSingleMedia( Long id ) throws HandledException;
	
	public MediaQueueDto postMedia( String displayType, HttpServletRequest request, MediaQueueDto mediaQueueDto ) throws HandledException;  
	
	public MediaQueueDto putMedia( Long id, MediaQueueDto mediaQueueDto ) throws HandledException;
	
	public Map<String, Boolean> deleteMedia(Long id) throws HandledException;
	
	public List<MediaQueue> moveUp(List<Long> ids, Long id) throws HandledException, Exception;
	
	public List<MediaQueue> moveDown(List<Long> ids, Long id) throws HandledException, Exception;
	
	public void displaySelectedData(String displayType)throws HandledException;      // To-Do

	public String uploadFilemedia(StandardMultipartHttpServletRequest request)throws HandledException;
 
//	public TvDisplay addDataFromOnlineTrainToTv() throws HandledException;

	public HashMap<String, Object> getTvDisplay() throws HandledException;

	public List<String> getAllFileMedia() throws HandledException, Exception;

	public List<String> exportDb(String filename) throws HandledException, Exception;

	public void importDb(String filename) throws HandledException, Exception;

	public String uploadDb(StandardMultipartHttpServletRequest request)throws HandledException, Exception;

	public String autoTrainExpectedTime() throws Exception;


}
