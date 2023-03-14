/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Online Train Bean
 */
package com.innobitsysytems.ipis.services.onlineTrain;

import java.io.BufferedInputStream;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Mixer;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.UnsupportedAudioFileException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.BoardSetting;
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.model.setup.TrainDetails;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.setup.BoardSettingRepository;
import com.innobitsysytems.ipis.repository.setup.StationDetailsRepository;
import com.innobitsysytems.ipis.repository.setup.TrainDetailsRepository;
import com.innobitsysytems.ipis.repository.setup.TrainRepository;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.AnnouncementUtilty;
import com.innobitsysytems.ipis.utility.CommonUtil;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

@Service
public class OnlineTrainBean implements OnlineTrainService {

	@Autowired
	OnlineTrainRepository onlineTrainRepository;

	@Autowired
	TrainRepository trainRepository;

	 @Autowired
	 DeviceRepository deviceRepository;
	 
	@Autowired
	TrainDetailsRepository trainDetailsRepository;

	@Autowired
	RequestWrapper requestWrapper;

	@Autowired
	BoardSettingRepository boardSettingRepo;

	@Autowired
	CommonUtil commonUtil;

	@Autowired
	StationDetailsRepository stationDetailRepo;

	@Autowired
	AnnouncementUtilty announcementUtil;

	public String id;

	public String pausePlay;

	public String hrs, min;

	@Override
	public List<OnlineTrain> allTrains() throws HandledException {
		List<OnlineTrain> onlineTrain = onlineTrainRepository.findAll(Sort.by(Sort.Direction.ASC, "STA"));

		return onlineTrain;
	}

	@Override
	public OnlineTrain postTrain(HttpServletRequest request, OnlineTrain onlineTrain) throws HandledException

	{

		try {
			onlineTrain.setETA(onlineTrain.getSTA());
			onlineTrain.setETD(onlineTrain.getSTD());
			onlineTrainRepository.save(onlineTrain);

			return onlineTrain;
		} catch (Exception e) {
			throw new HandledException("exception in adding data to grid", e.getMessage());
		}
	}

	@Override
	public OnlineTrain updateTrain(Long trainNumber, OnlineTrain onlineTrain) throws HandledException {

		OnlineTrain onlineTrainData = onlineTrainRepository.findById(trainNumber)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Train Data not found on :" + trainNumber));

		System.out.println(onlineTrainData);
		onlineTrainData.setArrDep(onlineTrain.getArrDep());

		onlineTrainData.setTrainStatus(onlineTrain.getTrainStatus());
		onlineTrainData.setPlatformNo(onlineTrain.getPlatformNo());
		onlineTrainData.setTD(onlineTrain.isTD());
		onlineTrainData.setCGDB(onlineTrain.isCGDB());
		onlineTrainData.setAnnouncement(onlineTrain.isAnnouncement());
		onlineTrainData.setRepeatAnnouncement(onlineTrain.getRepeatAnnouncement());
		onlineTrainData.setCoaches(onlineTrain.getCoaches());
		onlineTrainData.setLate(onlineTrain.getLate());
		onlineTrainData.setArrDep(onlineTrain.getArrDep());

		onlineTrainData.setTrainStatus(onlineTrain.getTrainStatus());
		onlineTrainData.setPlatformNo(onlineTrain.getPlatformNo());
		onlineTrainData.setLate(onlineTrain.getLate());
		String lt = onlineTrain.getLate();
		if (lt != null) {
			String[] lateSplit = lt.split(":");
			String lth = lateSplit[0];
			String ltm = lateSplit[1];
			int ltmi = Integer.parseInt(ltm);
			int lthi = Integer.parseInt(lth);
			Train train = trainRepository.findByTrainNo(trainNumber);
			String sta = train.getTrainDetails().getScheduleArrivalTime();
			String std = train.getTrainDetails().getScheduleDepartureTime();

			SimpleDateFormat df = new SimpleDateFormat("HH:mm");
			Date d, d1;
			try {
				d = df.parse(sta);
				Calendar cal = Calendar.getInstance();
				cal.setTime(d);
				cal.add(Calendar.HOUR, lthi);
				cal.add(Calendar.MINUTE, ltmi);

				String eta = df.format(cal.getTime());
				onlineTrainData.setETA(eta);

				d1 = df.parse(std);
				Calendar cal1 = Calendar.getInstance();
				cal1.setTime(d1);
				cal1.add(Calendar.HOUR, lthi);
				cal1.add(Calendar.MINUTE, ltmi);

				String etd = df.format(cal1.getTime());
				onlineTrainData.setETD(etd);

			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			onlineTrainData.setETD(onlineTrain.getSTA());
		}
		onlineTrainData.setTD(onlineTrain.isTD());
		onlineTrainData.setCGDB(onlineTrain.isCGDB());
		onlineTrainData.setAnnouncement(onlineTrain.isAnnouncement());
		onlineTrainData.setRepeatAnnouncement(onlineTrain.getRepeatAnnouncement());
		onlineTrainData.setCoaches(onlineTrain.getCoaches());
//		onlineTrainRepository.save(onlineTrainData);
		onlineTrainRepository.save(onlineTrainData);

		return onlineTrainData;
	}

	@Override
	public Map<String, Boolean> deleteTrainData(Long trainNumber) throws HandledException {

		OnlineTrain onlineTrain = onlineTrainRepository.findById(trainNumber)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "TrainData not found on : " + trainNumber));

		onlineTrainRepository.delete(onlineTrain);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

	@Override
	public void displayTadb() throws HandledException {

		List<OnlineTrain> onlineTrain = onlineTrainRepository.findByTD(true);
		if (onlineTrain.size() > 0) {

			commonUtil.onlineTrainTadb(onlineTrain);
			requestWrapper.tadb(onlineTrain, RequestTypes.TADB);
			
		} else {

			throw new HandledException("NOT_FOUND", "Select TD box to display train information on display boards");

		}
	}

	@Override
	public void displayAutoTadd() throws HandledException {

		List<OnlineTrain> onlineTrain = onlineTrainRepository.findByTD(true);
		if (onlineTrain.size() > 0) {

			requestWrapper.tadb(onlineTrain, RequestTypes.TADB);

		} else {

			throw new HandledException("NOT_FOUND", "Select TD box to display train information on display boards");

		}
	}

	
	
	@Override
	public void clearCgs() throws HandledException {

		List<OnlineTrain> onlineTrain = onlineTrainRepository.findByCGDB(true);
		requestWrapper.cgs(onlineTrain, RequestTypes.CGS);

	}

	@Override
	public void displayCgdbData() throws HandledException {

		String speed, letterSize, effectCode;
		int gap,timeDelay;
		List<OnlineTrain> onlineTrainCgdb = onlineTrainRepository.findByCGDB(true);
		
		BoardSetting boardData = boardSettingRepo.findByBoardType(DeviceType.cgdb.toString());
		if (boardData != null) {

			speed = boardData.getSpeed();
			letterSize = boardData.getLetterSize();
			effectCode = boardData.getEffect();
			gap=boardData.getCharacterGap();
			timeDelay=boardData.getPageChangeTime();

		} else {

			throw new HandledException("NOT_FOUND", "Fill Board Setting Fields for CGDB");

		}

		if (onlineTrainCgdb.size() > 0) {

			commonUtil.onlineTrainCgdb(onlineTrainCgdb);
			
			requestWrapper.cgdb(RequestTypes.CGDB, onlineTrainCgdb, speed, letterSize, effectCode,gap,timeDelay);

		} else {

			throw new HandledException("NOT_FOUND", "Select CGDB box to display train information on display boards");

		}

	}

	@Override
	public String played() throws HandledException {

		String audio = "";

		List<StationDetails> station = stationDetailRepo.findAll();

		List<OnlineTrain> onlineTrain = onlineTrainRepository.findByAnnouncement(true);

		String[] stationData = station.get(0).getAnnouncementPreference();

		List<OnlineTrain> arrofStr = new ArrayList<>();

		commonUtil.trainAnnouncement(onlineTrain);

		for (int i = 0; i < onlineTrain.size(); i++) {

			OnlineTrain arrofStr1 = onlineTrain.get(i);

			arrofStr.add(arrofStr1);

			for (int j = 0; j < stationData.length; j++) {
				announcementUtil.languages(stationData[j]);

				String data = arrofStr.get(i).getTrainNumber().toString();

				audio += announcementUtil.TrainNumber(data);

				String trainName = arrofStr.get(i).getTrainName().toString();
				audio += announcementUtil.TrainName(data);

				String aord = arrofStr.get(i).getArrDep().toString();
				announcementUtil.Aord(aord);

				String platformNo = arrofStr.get(i).getPlatformNo().toString();
				audio += announcementUtil.PlatformNo(platformNo);

				String trainStatus = arrofStr.get(i).getTrainStatus();
				audio += announcementUtil.trainStatus(trainStatus, aord);

				String sta = arrofStr.get(i).getSTA();
				String eta = arrofStr.get(i).getETA();
				String late = arrofStr.get(i).getLate();

				String std = arrofStr.get(i).getSTD();
				String etd = arrofStr.get(i).getETD();

//				if (late.equals("0") && (aord.equals("A"))) {
//					// audio += announcementUtil.STA(sta);
//				} else {
//					// audio+=announcementUtil.ETA(eta);
//				}
//
//				if (late.equals("0") && (aord.equals("D"))) {
//					// audio += announcementUtil.STD(std);
//				} else {
//					// audio += announcementUtil.ETD(etd);
//				}

			} // end for prefrences

		} // announcement tick
		return audio;

	}

	public List<String> player() {
		String s = "play";
		String audio = "";

		Train train = new Train();
		List<StationDetails> station = stationDetailRepo.findAll();

		List<OnlineTrain> onlineTrain = onlineTrainRepository.findByAnnouncement(true);

		List<String> audioList = new ArrayList<String>();

		String[] stationData = station.get(0).getAnnouncementPreference();

		List<OnlineTrain> arrofStr = new ArrayList<>();

		commonUtil.trainAnnouncement(onlineTrain);

		List<String> arrofFiles = new ArrayList<>();

		for (int i = 0; i < onlineTrain.size(); i++) {

			OnlineTrain arrofStr1 = onlineTrain.get(i);

			arrofStr.add(arrofStr1);

			String aord = arrofStr.get(i).getArrDep().toString();
			announcementUtil.Aord(aord);

			String tStatus = onlineTrain.get(i).getTrainStatus();
			System.out.println(tStatus);

			for (int j = 0; j < stationData.length; j++) {

				audio += announcementUtil.Chimes();
				
				if (tStatus.equals("Running Right Time")) {

					String data = arrofStr.get(i).getTrainNumber().toString();
		
					if (stationData[j].equals("English")) {
						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						 //String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

//						audio += announcementUtil.Itis();

//						audio += announcementUtil.ExpectedTo(aord);

						// its is expected to arrive at
						String eta = arrofStr.get(i).getETA();

						String[] etaSplit = eta.split(":");

						hrs = etaSplit[0];
						min = etaSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();
						
					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						//audio += announcementUtil.Itis();

						audio += announcementUtil.From();

						String eta = arrofStr.get(i).getETA();

						// audio+=announcementUtil.ETA(eta);

						String[] etaSplit = eta.split(":");

						hrs = etaSplit[0];
						min = etaSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						audio += announcementUtil.HindiAddition2();
						//audio += announcementUtil.ExpectedTo(aord);
					} // end of hindi if

				} // status if close
				 if (tStatus.equals("Will Arrive Shortly")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);
						
						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						audio += announcementUtil.HindiAddition();
						
						//par aayegi

					} // end of hindi if

				} else if (tStatus.equals("Is Arriving On")) {

					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

					} // end of hindi if

				} else if (tStatus.equals("Has Arrived On")) {

					String data = arrofStr.get(i).getTrainNumber().toString();

					System.out.println("train no---"+data);
					
					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

					} // end of hindi if

				} else if (tStatus.equals("Running Late")) {

					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.SchedulesToArrive();

						String sta = arrofStr.get(i).getSTA();

						String[] staSplit = sta.split(":");

						hrs = staSplit[0];
						min = staSplit[1];
						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String late = arrofStr.get(i).getLate();
						String[] lateSplit = late.split(":");
						hrs = lateSplit[0];
						min = lateSplit[1];

						
						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						audio += announcementUtil.Inconvenience();

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();
						

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						audio += announcementUtil.SchedulesToArrive();

						String sta = arrofStr.get(i).getSTA();
						String[] staSplit = sta.split(":");
						hrs = staSplit[0];
						min = staSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						audio += announcementUtil.From();

						// String late=arrofStr.get(i).getLate();
						String late = arrofStr.get(i).getLate();
						String[] lateSplit = late.split(":");
						hrs = lateSplit[0];
						min = lateSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();
						// audio += announcementUtil.LateAnno(late);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						audio += announcementUtil.Inconvenience();

					} // end of hindi if

				}
				if (tStatus.equals("Cancelled")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						audio += announcementUtil.Inconvenience();

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						audio += announcementUtil.Inconvenience();

					} // end of hindi if
				} else if (tStatus.equals("Indefinite Late")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						audio += announcementUtil.Inconvenience();

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						audio += announcementUtil.Inconvenience();

					} // end of hindi if

				} else if (tStatus.equals("Terminated")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String stationName = station.get(0).getStationCode();

						System.out.println("station name" + stationName);
						
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}
						
					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}
						
						
						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

					} // end of hindi if

				} else if (tStatus.equals("Platform Change")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						
						
						audio += announcementUtil.Inconvenience();

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);
						
						
						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);
						
						audio += announcementUtil.HindiAddition();
						
						
						
						audio += announcementUtil.Inconvenience();

					} // end of hindi if

				} else if (tStatus.equals("Is Ready To Leave")) {

					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);


						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

					} // end of hindi if

				} else if (tStatus.equals("Is On Platform")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

					} // end of hindi if

				} else if (tStatus.equals("Has Left")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

					} // end of hindi if

				} else if (tStatus.equals("Rescheduled")) {

					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						//audio += announcementUtil.Itis();

						//audio += announcementUtil.ExpectedTo(aord);
						// audio += announcementUtil.ExpectedTo(aord);

						// its is expected to arrive at
						String etd = arrofStr.get(i).getETD();

						// audio+=announcementUtil.ETD(etd);
						String[] etdSplit = etd.split(":");

						hrs = etdSplit[0];
						min = etdSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();
						
						audio += announcementUtil.Inconvenience();
					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						//audio += announcementUtil.Itis();

						String etd = arrofStr.get(i).getETD();

						// audio+=announcementUtil.ETD(etd);
						String[] etdSplit = etd.split(":");

						hrs = etdSplit[0];
						min = etdSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						// audio += announcementUtil.ExpectedTo(aord);
						audio += announcementUtil.HindiAddition1();
						

						audio += announcementUtil.Inconvenience();

					} // end of hindi if

				}

				else if (tStatus.equals("Diverted")) {

					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Inconvenience();
					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						audio += announcementUtil.Itis();

						String etd = arrofStr.get(i).getETD();

						// audio+=announcementUtil.ETD(etd);
						String[] etdSplit = etd.split(":");

						hrs = etdSplit[0];
						min = etdSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						// audio += announcementUtil.ExpectedTo(aord);

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

						audio += announcementUtil.Inconvenience();

					} // end of hindi if

				}

				else if (tStatus.equals("Scheduled departure")) {
					String data = arrofStr.get(i).getTrainNumber().toString();

					if (stationData[j].equals("English")) {

						announcementUtil.languages(stationData[j]);

						// for attention
						audio += announcementUtil.Attention();

						// train number

						audio += announcementUtil.TrainNumber(data);

						// train name
						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						// from
						audio += announcementUtil.From();

						// station
						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						// to
						audio += announcementUtil.To();

						// station
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						// via
						audio += announcementUtil.Via();

						// stations
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						// status
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);

						String std = arrofStr.get(i).getSTD();

						// audio+=announcementUtil.STD(std);
						String[] stdSplit = std.split(":");

						hrs = stdSplit[0];
						min = stdSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						audio += announcementUtil.From();

						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);

					} else if (stationData[j].equals("Hindi")) {
						announcementUtil.languages(stationData[j]);

						audio += announcementUtil.Attention();

						List<Train> trains = trainRepository.findAll();
						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String sourceStation = trains.get(k).getSourceStation();
								audio += announcementUtil.Stations(sourceStation);
							}
						}

						audio += announcementUtil.From();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String[] viaStations = trains.get(k).getViaStation();
								for (int m = 0; m < viaStations.length; m++) {
									audio += announcementUtil.Stations(viaStations[m]);

								}
							}
						}

						audio += announcementUtil.Via();

						for (int k = 0; k < trains.size(); k++) {
							if (trains.get(k).getTrainNo().toString().equals(data)) {
								String destinationStation = trains.get(k).getDestinationStation();
								audio += announcementUtil.Stations(destinationStation);
							}
						}

						audio += announcementUtil.To();

						audio += announcementUtil.TrainNumber(data);

						//String trainName = arrofStr.get(i).getTrainName().toString();
						audio += announcementUtil.TrainName(data);

						
						String trainStatus = arrofStr.get(i).getTrainStatus();
						audio += announcementUtil.trainStatus(trainStatus, aord);
						
						String std = arrofStr.get(i).getSTD();

						// audio+=announcementUtil.STD(std);
						String[] stdSplit = std.split(":");

						hrs = stdSplit[0];
						min = stdSplit[1];

						audio += announcementUtil.TimeAnno(hrs);
						audio += announcementUtil.Hrs();

						audio += announcementUtil.TimeAnno(min);
						audio += announcementUtil.Min();

						
						audio += announcementUtil.HindiAddition4();
						String platformNo = arrofStr.get(i).getPlatformNo().toString();
						audio += announcementUtil.PlatformNo(platformNo);
						
						audio += announcementUtil.HindiAddition3();

					} // end of hindi if

				}
			} // end for prefrences(j)
			audioList.add(audio);
			audioList.add("#");
			audio = "";

		} // announcement tick(i)
		return audioList;

	}

//	int count = 0;
	int next = 0;
	int counter = 0;

	int totalLength;

	public AdvancedPlayer player;
	public FileInputStream fileInputStream;
	public BufferedInputStream bufferedInputStream;
	public int x = 0;
	public int m;
	public boolean play = false;
	public Clip clip;
	public AudioInputStream audioInputStream;
	public int stopper;
	Thread thread;

	@Override

	public String announcePlayer(int repeat) throws Exception {
		this.play = true;

		List<String> audioList = player();
		List<String> pathList = new ArrayList<String>();
		System.out.println(audioList.toString());
		for (int i = 0; i < audioList.size(); i++) {
			System.out.println(audioList.get(i));
			this.counter = i;
			String audioD[] = audioList.get(i).split("#");
			for (this.m = this.x; this.m < audioD.length; this.m++) {
				System.out.println("349");
				if (this.m == audioD.length - 1) {
					this.x = 0;
				}
				for (int v = 0; v < repeat; v++) {
					String paths[] = audioD[this.m].split(",");
					System.out.println("352");

					for (int k = 0; k < paths.length; k++) {
						System.out.println("356");
						System.out.println(paths[k]);

//						this.fileInputStream = new FileInputStream(paths[k]);
//						this.bufferedInputStream = new BufferedInputStream(fileInputStream);
//
//						totalLength = fileInputStream.available();
//						System.out.println("toatalLength");
//
//						if (this.play == true) {
//
//							this.player = new AdvancedPlayer((bufferedInputStream));
//							this.player.play();
//						}
						audioInputStream = AudioSystem.getAudioInputStream(new File(paths[k]).getAbsoluteFile());

						// create clip reference
						AudioFormat format = audioInputStream.getFormat();
						long frames = audioInputStream.getFrameLength();
						long durationInSeconds = (long) ((frames + 0.0) / format.getFrameRate());
						if (this.play == true) {
							clip = AudioSystem.getClip();
							// open audioInputStream to the clip
							clip.open(audioInputStream);
							clip.setFramePosition(0);
							clip.start();
							thread.sleep(durationInSeconds * 1000 + 1000);
						}
					}

				}
			}

		}
		return "playing successfully";

	}

	public String pauseAnnouncement() throws HandledException {
		this.x = this.m;
		this.play = false;
		if (this.player != null) {

			this.player.close();
		}
		return "paused announcement";

	}

	public String stopAnnouncement() throws HandledException {
		this.x = 0;
		this.play = false;
//		if (this.player != null) {
//
//			this.player.close();
//		}
		try {
		if (!clip.isActive()) {
		      System.err.println("Timer task stopping clip.");
		      clip.stop();
		    }
		return "stop successfully";
		}
		catch(Exception e) {
			return "stop successfully";
		}

	}

	public String nextPlayer(String pausePlay, int repeat, int next) throws Exception {
		this.announcePlayer(0);
		this.id = pausePlay;
		this.next = next;
		// TODO Auto-generated method stub
		System.out.println(243);

		List<String> audioList = player();
		if (audioList.size() <= counter) {
			this.next = 0;
		}
		int n = this.counter;

		for (int i = 0 + next; i < audioList.size(); i++) {
			if (next == 1) {
				next++;
				continue;
			}
			String audioD[] = audioList.get(i).split("#");
			for (int j = 0; j < audioD.length; j++) {

				for (int v = 0; v < repeat; v++) {
					String paths[] = audioD[j].split(",");

					for (int k = 0; k < paths.length; k++) {
						FileInputStream fileInputStream = new FileInputStream(paths[k]);
						Player player = new Player((fileInputStream));

						if (id.equals("0")) {
							player.close();
							fileInputStream.close();
							break;
						}
						player.play();
						this.next = 0;
						player.close();

					}

				}

			}

		}
		return pausePlay;

	}

	public String announcementPlayer(String pausePlay, int repeat) throws Exception {
		this.id = pausePlay;
		int nxt = 0;
		int close = 1;
		int nxt1 = nxt;
		// int repeat=2;

		System.out.println("248");
		System.out.println(pausePlay);

		String list = played();
		System.out.println(list);
		String[] split = list.split(",");

		for (int v = 0; v < repeat; v++) {

			for (int i = 0; i < split.length; i++) {
				FileInputStream fileInputStream = new FileInputStream(split[i]);
				Player player = new Player((fileInputStream));

				System.out.println(split[i]);
				if (id.equals("0")) {
					System.out.println("inside if");
					player.close();
					fileInputStream.close();
					break;
				}

				player.play();
				player.close();

			}
		}

		return list;

	}

	@Override
	public void sendTrainColorData() throws HandledException {
		// TODO Auto-generated method stub
		
	}

	
//	@Override
//	public void displayAutoTadd(boolean auto) throws HandledException {
//
//		
//		
//		onlineTrainRepository.save(message);
//		List<OnlineTrain> onlineTrain = onlineTrainRepository.findByAuto(true);
//		if (onlineTrain.size() > 0) {
//
//			commonUtil.onlineTrainTadb(onlineTrain);
//			
//			requestWrapper.tadb(onlineTrain, RequestTypes.TADB);
//
//		} else {
//
//			throw new HandledException("NOT_FOUND", "Select TD box to display train information on display boards");
//
//		}
//	}
//	
	
}
