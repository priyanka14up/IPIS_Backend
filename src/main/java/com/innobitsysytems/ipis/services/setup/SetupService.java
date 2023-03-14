/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Setup Service
 */
package com.innobitsysytems.ipis.services.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.dto.BoardDiagnosticDto;
import com.innobitsysytems.ipis.dto.BoardLedDto;
import com.innobitsysytems.ipis.dto.SoftResetDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.setup.BoardSetting;
import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.model.setup.DefaultMessages;
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.model.setup.IvdScreenColorConfig;
import com.innobitsysytems.ipis.model.setup.StationCode;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.model.setup.TrainDetails;
import com.innobitsysytems.ipis.model.setup.TrainStatus;
import com.innobitsysytems.ipis.model.setup.TrainStatusColor;
import com.innobitsysytems.ipis.model.setup.WebConfiguration;

public interface SetupService {
	
	//Station Details Functions
	
	public List allStationDetails();
	
	public List<String> allPlatforms()throws HandledException;
	
	public HashMap<String,Object> allStationNameAndCodes()throws HandledException;
	
	public HashMap<String, Object> postStationDetails(HttpServletRequest request, StationDetails stationDetails) throws HandledException;
	
	// Coach Data Entry Functions
	
	public List allCoach();
    
	public HashMap<String, Object> singleCoach(Long id) throws HandledException; 
	
	public HashMap<String, Object> postCoach (HttpServletRequest request, CoachData coachData) throws HandledException;
	
	public HashMap<String, Object> updateCoach(Long id, CoachData coachData) throws HandledException;
	
	public Map<String, Boolean> deleteCoach(Long id)throws HandledException;
	
	public List allCoachNames() throws HandledException;
	
	public List allCoachesCode() throws HandledException;
	
	//Station Code Entry Function
	
	public List allCode();
	
	public List allCodes()throws HandledException;
			
	
	public HashMap<String, Object>  singleSCode(Long id) throws HandledException;
	
	public HashMap<String, Object>  postSCode(HttpServletRequest request, StationCode stationCode) throws HandledException;
	
	public HashMap<String, Object>  updateSCode(Long id, StationCode stationCode) throws HandledException;
	
	public Map<String, Boolean> deleteSCode(Long id)throws HandledException;
	
	//Train Status Entry Function
	
	public List allStatus();
	
	public List getOnlyTrainStatusCoulmn() throws HandledException; 
	
	public HashMap<String, Object> singleStatus(Long id) throws HandledException;
	
	public HashMap<String, Object> postStatus(HttpServletRequest request, TrainStatus trainStatus) throws HandledException;
	
	public HashMap<String, Object> updateStatus(Long id, TrainStatus trainStatus) throws HandledException;
	
	public Map<String, Boolean> deleteStatus(Long id) throws HandledException;
	
	//Display Board Setting Function
	
	public List allSetting();
		
	public HashMap<String, Object> singleSetting(Long id) throws HandledException;
		
	public HashMap<String, Object> postSetting(HttpServletRequest request, BoardSetting boardSetting) throws HandledException;

	//Display Board Diagnostics Function
		
	public HashMap<String, Object> postBoard(HttpServletRequest request, BoardDiagnosticDto boardDiagnostic ) throws HandledException;

	
	//Display Board LED Testing Function
				
	public HashMap<String, Object> postTesting(HttpServletRequest request, BoardLedDto boardLed) throws HandledException;

	
	//Enable Disable Board Function
	
	public List allEnable();
	
	public HashMap<String, Object> postEnable(HttpServletRequest request, EnableDisableBoard enableDisableBoard) throws HandledException;
	
	//Default Messages Function
	
	public List allMessages();
					
	public HashMap<String, Object> postMessages(HttpServletRequest request, DefaultMessages defaultMessages) throws HandledException;
				
	public void setDefaultMsg(String boardType, String platformNo ) throws HandledException;
	
	//Web Configuration Function
	
	public List allWeb();
		
	public HashMap<String, Object> postWeb(HttpServletRequest request, WebConfiguration webConfiguration) throws HandledException;
	
	//Train Data Entry Functions
	
	public long[] allTrain();
	
	public HashMap<String, Object> singleTrain(Long trainNo) throws HandledException;
	
	public HashMap<String, Object> postTrain(HttpServletRequest request, Train train) throws HandledException;

	public HashMap<String, Object> updateTrain(Long trainNo, Train train) throws HandledException;

	public String uplLoadStationCodeFile(StandardMultipartHttpServletRequest request,String language)throws HandledException;

	public String uploadTrainStatusFile(StandardMultipartHttpServletRequest request, String language)throws HandledException;

	public Map<String, Boolean> deleteUploadedFile(String filename,String language)throws HandledException, Exception, ServletException;
	
	public Map<String, Boolean> deleteStationCodeUpload(String filename,String language)throws HandledException, Exception, ServletException;

	public void autoTrainUpload() throws Exception;

	public Map<String, Boolean> deleteTrainData(Long trainNo) throws HandledException;
	

	public List getAllTrainData() throws HandledException;

	public void autoDeleteTrain()throws HandledException;

	public String uplLoadStationFile(StandardMultipartHttpServletRequest request)throws HandledException, Exception;
	//post train color 
	public HashMap<String, Object> postTrainStatusColor(TrainStatusColor trainStatusColor);
	
	public HashMap<String, Object> updateTrainStatusColor(TrainStatusColor trainStatusColor);
	
	public HashMap<String, Object> postIvdScreenColorConfig(IvdScreenColorConfig ivdScreenColorConfig);
	public HashMap<String, Object> updateIvdScreenColorConfig(IvdScreenColorConfig ivdScreenColorConfig);
	public HashMap<String, Object> getTrainStatusColor(String status,String ad);

	public HashMap<String, Object> getIvdScreenColorConfig(long id);
	
	public HashMap<String, Object> sendTrainColorPacket();
	public HashMap<String, Object> sendDefaultMessageForIvdOvd(String data, Enum url, DeviceType boardType, String platform, String speed, String effect, String letterSize,int gap);

	public HashMap<String, Object> sendVideoPacket();
	
	public void softReset(HttpServletRequest request,@Valid@RequestBody SoftResetDto softreset) throws HandledException;

	public StationDetails statusAutoTadd(boolean auto) throws HandledException;
	

}