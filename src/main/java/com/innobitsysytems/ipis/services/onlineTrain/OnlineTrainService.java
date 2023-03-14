/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Online Train Service
 */
package com.innobitsysytems.ipis.services.onlineTrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.innobitsysytems.ipis.dto.OnlineTrainDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;

public interface OnlineTrainService {
	
	public List <OnlineTrain> allTrains()throws HandledException;
	
	public OnlineTrain postTrain(HttpServletRequest request, OnlineTrain onlineTrain)throws HandledException;
	
	public OnlineTrain updateTrain(Long trainNumber, OnlineTrain onlineTrain)throws HandledException;
	
	public Map<String, Boolean> deleteTrainData(Long trainNumber)throws HandledException;
	
	public void displayTadb() throws HandledException;
	public void sendTrainColorData() throws HandledException;
	
	public void clearCgs()throws HandledException;
	
	public void displayCgdbData()throws HandledException;
	
	public String played() throws HandledException;
	public List<String> player() throws HandledException;
	public String announcementPlayer(String pausePlay,int repeat) throws Exception;
	public String announcePlayer(int repeat) throws Exception;
	public String pauseAnnouncement() throws HandledException;
	public String stopAnnouncement() throws HandledException;
	public String nextPlayer(String pausePlay, int repeat,int next) throws Exception ;

	public void displayAutoTadd() throws HandledException;




}
