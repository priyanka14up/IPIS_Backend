/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Setup Bean
 */
package com.innobitsysytems.ipis.services.setup;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.controller.setup.TrainStatusColorController;
import com.innobitsysytems.ipis.dto.BoardDiagnosticDto;
import com.innobitsysytems.ipis.dto.BoardLedDto;
import com.innobitsysytems.ipis.dto.SoftResetDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.library.TcpClient;
import com.innobitsysytems.ipis.model.announcement.TrainNameEntryAnnouncement;
import com.innobitsysytems.ipis.model.announcement.TrainStatusEntryAnnouncement;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.BoardSetting;
import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.model.setup.CoachDetails;
import com.innobitsysytems.ipis.model.setup.CoachesCode;
import com.innobitsysytems.ipis.model.setup.DefaultMessages;
import com.innobitsysytems.ipis.model.setup.DeviceSchema;
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.model.setup.Intensity;
import com.innobitsysytems.ipis.model.setup.IvdScreenColorConfig;
import com.innobitsysytems.ipis.model.setup.StationCode;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.model.setup.TrainDetails;
import com.innobitsysytems.ipis.model.setup.TrainLiveInfo;
import com.innobitsysytems.ipis.model.setup.TrainName;
import com.innobitsysytems.ipis.model.setup.TrainStatus;
import com.innobitsysytems.ipis.model.setup.TrainStatusColor;
import com.innobitsysytems.ipis.model.setup.WebConfiguration;
import com.innobitsysytems.ipis.model.tvdisplay.MediaQueue;
import com.innobitsysytems.ipis.repository.announcement.TrainNameEntryAnnoRepository;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.announcement.TrainStatusAnnoRepository;
import com.innobitsysytems.ipis.repository.setup.BoardSettingRepository;
import com.innobitsysytems.ipis.repository.setup.CoachDataRepository;
import com.innobitsysytems.ipis.repository.setup.CoachDetailsRepository;
import com.innobitsysytems.ipis.repository.setup.CoachesCodeRepository;
import com.innobitsysytems.ipis.repository.setup.DefaultMessagesRepository;
import com.innobitsysytems.ipis.repository.setup.EnableDisableBoardRepository;
import com.innobitsysytems.ipis.repository.setup.IntensityRepository;
import com.innobitsysytems.ipis.repository.setup.IvdScreenColorConfigRepository;
import com.innobitsysytems.ipis.repository.setup.StationCodeRepository;
import com.innobitsysytems.ipis.repository.setup.StationDetailsRepository;
import com.innobitsysytems.ipis.repository.setup.TrainDetailsRepository;
import com.innobitsysytems.ipis.repository.setup.TrainLiveInfoRepository;
import com.innobitsysytems.ipis.repository.setup.TrainNameRepository;
import com.innobitsysytems.ipis.repository.setup.TrainRepository;
import com.innobitsysytems.ipis.repository.setup.TrainStatusColorRepository;
import com.innobitsysytems.ipis.repository.setup.TrainStatusRepository;
import com.innobitsysytems.ipis.repository.setup.WebConfigurationRepository;
import com.innobitsysytems.ipis.repository.tvdisplay.MediaQueueRepository;
import com.innobitsysytems.ipis.services.CommunicationService;
import com.innobitsysytems.ipis.threadmgnt.RequestTypes;
import com.innobitsysytems.ipis.threadmgnt.RequestWrapper;
import com.innobitsysytems.ipis.utility.Command;
import com.innobitsysytems.ipis.utility.CommonUtil;
import com.innobitsysytems.ipis.utility.Constants;
import com.innobitsysytems.ipis.utility.CustomUtil;
import com.innobitsysytems.ipis.utility.Protocol;
import com.innobitsysytems.ipis.utility.TcpPacketUtil;

@Service
public class SetupBean implements SetupService {
	private static final Logger logger = LoggerFactory.getLogger(SetupBean.class);

	@Autowired
	public StationDetailsRepository stationDetailsRepository;

	@Autowired
	public OnlineTrainRepository onlineTrainRepository;

	@Autowired
	public CoachDataRepository coachDataRepository;

	@Autowired
	public StationCodeRepository stationCodeRepository;

	@Autowired
	public TrainStatusRepository trainStatusRepository;

	@Autowired
	public BoardSettingRepository boardSettingRepository;

	@Autowired
	public EnableDisableBoardRepository enableDisableBoardRepository;

	@Autowired
	public DefaultMessagesRepository defaultMessagesRepository;

	@Autowired
	public WebConfigurationRepository webConfigurationRepository;

	@Autowired
	public TrainRepository trainRepository;

	@Autowired
	public TrainNameRepository trainNameRepository;

	@Autowired
	public TrainDetailsRepository trainDetailsRepository;

	@Autowired
	public CoachDetailsRepository coachDetailsRepository;

	@Autowired
	public TrainLiveInfoRepository trainLiveInfoRepository;

	@Autowired
	public TrainNameEntryAnnoRepository trainNameEntryAnnoRepository;

	@Autowired
	public CustomUtil customUtil;

	@Autowired
	private RequestWrapper requestWrapper;

	@Autowired
	public CommonUtil commonUtil;

	@Autowired
	public CoachesCodeRepository CoachesCodeRepository;

	@Autowired
	public MediaQueueRepository mediaQueueRepo;

	@Autowired
	public TrainStatusAnnoRepository trainStatusAnnoRepository;

	@Autowired
	public TrainStatusColorRepository trainStatusColorRepository;

	@Autowired
	public IvdScreenColorConfigRepository ivdScreenColorConfigRepository;

	@Autowired
	private DeviceRepository deviceRepository;

	@Autowired
    private TcpClient tcpClient;

	@Autowired
	private IntensityRepository intensityRepository;

	@Autowired
	private BoardSettingRepository boardSettingRepo;

	//Station Details Override Functions
	@Autowired
	private TcpPacketUtil tcpPacket;

	@Override
	public List allStationDetails() {

		List<StationDetails> stationDetails = stationDetailsRepository.findAll();

		List StationDetailsList = new ArrayList<>();

		for (int i = 0; i < stationDetails.size(); i++) {
			StationDetailsList.add(customStationDetails(stationDetails.get(i)));
		}

		return StationDetailsList;

	}

	@Override
	public HashMap<String, Object> allStationNameAndCodes() throws HandledException {

		List<StationDetails> stationDetails = stationDetailsRepository.findAll();

		StationDetails data = new StationDetails();
		if (stationDetails.size() > 0) {
			data = stationDetails.get(0);

		} else {
			throw new HandledException("Not Found", "No station details exists in database");
		}
		return customResponseStationName(data);
	}

	@Override
	public HashMap<String, Object> postStationDetails(HttpServletRequest request, StationDetails stationDetails)
			throws HandledException {

		HashMap<String, Object> stationDetailsMap = new HashMap<>();
		List<StationDetails> detailExist = stationDetailsRepository.findAll();

		if (detailExist.size() == 0) {

			stationDetails.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Post Station Details in SetUp ", String.valueOf(customUtil.getIdFromToken()));
			stationDetailsRepository.save(stationDetails);
			stationDetailsMap = customStationDetails(stationDetails);

		} else {

			String stationName = detailExist.get(0).getStationName();
			long id = detailExist.get(0).getId();
			StationDetails station = stationDetailsRepository.findByStationName(stationName);
			station.setStationName(stationDetails.getStationName());
			station.setDivisionName(stationDetails.getDivisionName());
			station.setRegionName(stationDetails.getRegionName());
			station.setStationCode(stationDetails.getStationCode());
			station.setDivisionCode(stationDetails.getDivisionCode());
			station.setRegionCode(stationDetails.getRegionCode());
			station.setNorthEastEnd(stationDetails.getNorthEastEnd());
			station.setSouthWestEnd(stationDetails.getSouthWestEnd());
			station.setAutoLoadTrain(stationDetails.getAutoLoadTrain());
			station.setAutoDelete(stationDetails.getAutoDelete());
			station.setAutoLoadTrainEveryMin(stationDetails.getAutoLoadTrainEveryMin());
			station.setAutoDeleteTrainEveryMin(stationDetails.getAutoDeleteTrainEveryMin());
			//			station.setManuallyGetTrainForNextHours(stationDetails.getManuallyGetTrainForNextHours());
			station.setAutoSendDataTimeInterval(stationDetails.getAutoSendDataTimeInterval());
			//			station.setAutoDeleteTrainTimeInterval(stationDetails.getAutoDeleteTrainTimeInterval());
			station.setAvailablePlatforms(stationDetails.getAvailablePlatforms());
			station.setListOfPlatforms(stationDetails.getListOfPlatforms());
			station.setEnableIntegration(stationDetails.getEnableIntegration());
			//station.setTypeOfIntegration(stationDetails.getTypeOfIntegration());
			station.setFileLocation(stationDetails.getFileLocation());
			station.setLanguages(stationDetails.getLanguages());
			station.setAnnouncementPreference(stationDetails.getAnnouncementPreference());
			station.setNtesUpdateEnable(stationDetails.getNtesUpdateEnable());
			station.setNtesUpdateTimeInMin(stationDetails.getNtesUpdateTimeInMin());
			station.setNtesPortType(stationDetails.getNtesPortType());
			station.setPortNo(stationDetails.getPortNo());
			//station.setDeviceSchema(DeviceSchema.IPBasedDevices);
			station.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Update Station Details in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			station.setAutoTadd(stationDetails.getAutoTadd());		
			station.setAutoSendDataTimeInterval(stationDetails.getAutoSendDataTimeInterval());	
			stationDetailsRepository.save(station);
			stationDetailsMap = customStationDetails(station);

		}

		return stationDetailsMap;

	}

	public List<String> allPlatforms() throws HandledException {
		List<StationDetails> stationDetails = stationDetailsRepository.findAll();
		List<String> platformsList = new ArrayList<>();

		
		if (stationDetails.size() > 0) {
			for (int i = 0; i < stationDetails.size(); i++) {
				String[] listOfPlatforms = stationDetails.get(i).getListOfPlatforms();

				for (int j = 0; j < listOfPlatforms.length; j++) {
					platformsList.add(listOfPlatforms[j]);

				}
				
			}

		} else {
			throw new HandledException("Not Found", "Enter Station Details first");
		}
		return platformsList;

	}

	// Coach Data Entry Override Functions

	@Override
	public List allCoach() {

		List<CoachData> coachData = coachDataRepository.findAll();

		List coachList = new ArrayList<>();

		for (int i = 0; i < coachData.size(); i++) {
			coachList.add(customResponseCoach(coachData.get(i)));
		}

		return coachList;
	}

	@Override
	public HashMap<String, Object> singleCoach(Long id) throws HandledException {

		CoachData coach = coachDataRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Coach not found on : " + id));

		return customResponseCoach(coach);

	}

	@Override
	public HashMap<String, Object> postCoach(HttpServletRequest request, CoachData coach) throws HandledException {

		CoachData data = coachDataRepository.findByEngCoachName(coach.getEngCoachName());
		CoachesCode obj = new CoachesCode();

		if (data == null) {

			coach.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Post Coach Data Entry in SetUp ", String.valueOf(customUtil.getIdFromToken()));

			coachDataRepository.save(coach);
			obj.setEngCoachName(coach.getEngCoachName());
			CoachesCodeRepository.save(obj);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Coach Data exist");
		}

		return customResponseCoach(coach);
	}

	@Override
	public HashMap<String, Object> updateCoach(Long id, CoachData coachData) throws HandledException {

		CoachData coach = coachDataRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Coach not found for this id : " + id));

		CoachData coachnamedata = coachDataRepository.findByEngCoachName(coachData.getEngCoachName());

		if (coachnamedata == null || coachnamedata.getEngCoachName().equals(coach.getEngCoachName())) {

			coach.setEngCoachName(coachData.getEngCoachName());
			coach.setHindiCoachName(coachData.getHindiCoachName());
			coach.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Update Coach Data Entry in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			coachDataRepository.save(coach);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Coach Data exist");
		}

		return customResponseCoach(coach);

	}

	@Override
	public Map<String, Boolean> deleteCoach(Long id) throws HandledException {

		CoachData coach = coachDataRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Coach not found on : " + id));

		commonUtil.updateActivities("Deleted Coach Data Entry in SetUp ", String.valueOf(customUtil.getIdFromToken()));
		coachDataRepository.delete(coach);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;

	}

	@Override
	public List allCoachNames() throws HandledException {

		List<String> arrOfCoachNames = new ArrayList<>();

		List<CoachData> coachData = coachDataRepository.findAll();

		if (coachData.size() > 0) {
			for (int i = 0; i < coachData.size(); i++) {
				String coachName = coachData.get(i).getEngCoachName();

				arrOfCoachNames.add(coachName);
			}

		} else {
			throw new HandledException("Not Found", "No Coach exists in database");
		}
		return arrOfCoachNames;

	}

	@Override
	public List allCoachesCode() throws HandledException {

		List<CoachesCode> coachesCodes = CoachesCodeRepository.findAll();
		List coachList = new ArrayList<>();

		for (int i = 0; i < coachesCodes.size(); i++) {
			coachList.add(customResponseCoachCodes(coachesCodes.get(i)));
		}

		return coachList;

	}

	//Station Code Entry Override Functions

	@Override
	public List allCode() {

		List<StationCode> stationData = stationCodeRepository.findAll();

		List stationCodeList = new ArrayList<>();

		for (int i = 0; i < stationData.size(); i++) {
			stationCodeList.add(customResponseStationCode(stationData.get(i)));
		}

		return stationCodeList;
	}

	// for getting station code column from database

	@Override
	public List allCodes() throws HandledException {
		List<String> arrOfStationCodes = new ArrayList<>();

		List<StationCode> stationData = stationCodeRepository.findAll();

		if (stationData.size() > 0) {
			for (int i = 0; i < stationData.size(); i++) {
				String arrOfStationCode = stationData.get(i).getStationCode();

				arrOfStationCodes.add(arrOfStationCode);
			}

		} else {
			throw new HandledException("Not Found", "No station details exists in database");
		}
		return arrOfStationCodes;

	}

	@Override
	public HashMap<String, Object> singleSCode(Long id) throws HandledException {

		StationCode stationcode = stationCodeRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Coach not found on : " + id));

		return customResponseStationCode(stationcode);
	}

	public String englishAudioFilePathStationName = "";
	public String hindiAudioFilePathStationName = "";
	public String regionalAudioFilePathStationName = "";

	@Override
	public HashMap<String, Object> postSCode(HttpServletRequest request, StationCode stationCode)
			throws HandledException {

		StationCode data = stationCodeRepository.findByStationCode(stationCode.getStationCode());

		if (data == null) {

			commonUtil.updateActivities("Post Station Code Entry in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			stationCode.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			stationCode.setEnglishWaveFile(englishAudioFilePathStationName);
			stationCode.setHindiWaveFile(hindiAudioFilePathStationName);
			stationCode.setRegionalWaveFile(regionalAudioFilePathStationName);
			stationCodeRepository.save(stationCode);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Station Code exist");

		}

		return customResponseStationCode(stationCode);
	}

	@Override
	public HashMap<String, Object> updateSCode(Long id, StationCode stationCode) throws HandledException {

		StationCode station = stationCodeRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Station Code not found for this id : " + id));

		station.setEnglishStationName(stationCode.getEnglishStationName());
		station.setHindiStationName(stationCode.getHindiStationName());
		station.setRegionalStationName(stationCode.getRegionalStationName());
		station.setEnglishWaveFile(englishAudioFilePathStationName);
		station.setHindiWaveFile(hindiAudioFilePathStationName);
		station.setRegionalWaveFile(regionalAudioFilePathStationName);
		station.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
		commonUtil.updateActivities("Update Station Code Entry in SetUp ", String.valueOf(customUtil.getIdFromToken()));
		stationCodeRepository.save(station);

		return customResponseStationCode(station);
	}

	@Override
	public Map<String, Boolean> deleteSCode(Long id) throws HandledException {

		StationCode station = stationCodeRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Station Code not found on : " + id));
		List<String> sPathlist = new ArrayList<String>();

		commonUtil.updateActivities("Deleted Station Code Entry in SetUp ",
				String.valueOf(customUtil.getIdFromToken()));
		stationCodeRepository.delete(station);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		if (station.getEnglishWaveFile() != null)
			sPathlist.add(station.getEnglishWaveFile());
		if (station.getHindiWaveFile() != null)
			sPathlist.add(station.getHindiWaveFile());
		if (station.getRegionalWaveFile() != null)
			sPathlist.add(station.getHindiWaveFile());
		try {

			for (int i = 0; i < sPathlist.size(); i++) {
				String path = sPathlist.get(i);
				Files.deleteIfExists(Paths.get(path));
			}
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}

		System.out.println("Deletion successful.");

		return response;
	}

	//Train Status Entry Override Functions

	@Override
	public List allStatus() {

		List<TrainStatus> trainstatus = trainStatusRepository.findAll();

		List trainstatusList = new ArrayList<>();

		for (int i = 0; i < trainstatus.size(); i++) {
			trainstatusList.add(customResponseTrainStatus(trainstatus.get(i)));
		}

		return trainstatusList;

	}

	// for getting only train status column from database
	@Override
	public List getOnlyTrainStatusCoulmn() throws HandledException {

		List<String> arrOfTrainStatus = new ArrayList<>();

		List<TrainStatus> trainStatusData = trainStatusRepository.findAll();

		if (trainStatusData.size() > 0) {
			for (int i = 0; i < trainStatusData.size(); i++) {
				String trainstatus = trainStatusData.get(i).getEnglishStatus();
				arrOfTrainStatus.add(trainstatus);
			}

		} else {
			throw new HandledException("Not found!", "No train status exists in database");
		}
		return arrOfTrainStatus;
	}

	@Override
	public HashMap<String, Object> singleStatus(Long id) throws HandledException {

		TrainStatus train = trainStatusRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Train Status not found on : " + id));

		return customResponseTrainStatus(train);

	}

	public String englishAudioFilePath = "";
	public String hindiAudioFilePath = "";
	public String regionalAudioFilePath = "";

	public TrainStatusEntryAnnouncement trainStatusEntryEntity;

	@Override
	public HashMap<String, Object> postStatus(HttpServletRequest request, TrainStatus trainStatus)
			throws HandledException {

			commonUtil.updateActivities("Post Train Status Entry in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			trainStatus.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			trainStatus.setEnglishFile(englishAudioFilePath);
			trainStatus.setHindiFile(hindiAudioFilePath);
			trainStatus.setRegionalFile(regionalAudioFilePath);
			trainStatusRepository.save(trainStatus);
			
		this.englishAudioFilePath = "";
		this.hindiAudioFilePath = "";
		this.regionalAudioFilePath = "";

		return customResponseTrainStatus(trainStatus);
	}

	@Override
	public HashMap<String, Object> updateStatus(Long id, TrainStatus trainStatus) throws HandledException {

		TrainStatus train = trainStatusRepository.findById(id).orElseThrow(
				() -> new HandledException("CHECK_PARAMETERS", "Train Status not found for this id : " + id));

		TrainStatus trainByCode = trainStatusRepository.findByStatusCode(trainStatus.getStatusCode());

		if (trainByCode == null || train.getStatusCode().equals(trainByCode.getStatusCode())) {

			train.setStatusCode(trainStatus.getStatusCode());
			train.setEnglishStatus(trainStatus.getEnglishStatus());
			train.setHindiStatus(trainStatus.getHindiStatus());
			train.setRegionalStatus(trainStatus.getRegionalStatus());
			train.setEnglishFile(trainStatus.getEnglishFile());
			train.setHindiFile(trainStatus.getHindiFile());
			train.setRegionalFile(trainStatus.getRegionalFile());
			train.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Update Train Status Entry in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			trainStatusRepository.save(train);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Train Status exist");
		}

		return customResponseTrainStatus(train);

	}

	@Override
	public Map<String, Boolean> deleteStatus(Long id) throws HandledException {

		TrainStatus train = trainStatusRepository.findById(id)
				.orElseThrow(() -> new HandledException("CHECK_PARAMETERS", "Train Status not found on : " + id));
		List<String> pathlist = new ArrayList<String>();

		commonUtil.updateActivities("Deleted Train Status Entry in SetUp ",
				String.valueOf(customUtil.getIdFromToken()));
		trainStatusRepository.delete(train);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		if (train.getEnglishFile() != null) {
			pathlist.add(train.getEnglishFile());
		}
		if (train.getEnglishFile() != null)
			pathlist.add(train.getEnglishFile());
		if (train.getHindiFile() != null)
			pathlist.add(train.getHindiFile());
		if (train.getRegionalFile() != null)
			pathlist.add(train.getRegionalFile());
		try {

			for (int i = 0; i < pathlist.size(); i++) {
				String path = pathlist.get(i);
				Files.deleteIfExists(Paths.get(path));
			}
		} catch (NoSuchFileException e) {
			System.out.println("No such file/directory exists");
		} catch (DirectoryNotEmptyException e) {
			System.out.println("Directory is not empty.");
		} catch (IOException e) {
			System.out.println("Invalid permissions.");
		}

		System.out.println("Deletion successful.");

		return response;

	}

	//Display Board Setting Override Functions

	@Override
	public List allSetting() {

		List<BoardSetting> boardSetting = boardSettingRepository.findAll();

		List boardList = new ArrayList<>();

		for (int i = 0; i < boardSetting.size(); i++) {
			boardList.add(customResponseBoardSetting(boardSetting.get(i)));
		}

		return boardList;
	}

	@Override
	public HashMap<String, Object> singleSetting(Long id) throws HandledException {

		BoardSetting boardSetting = boardSettingRepository.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Board Diagonistics not found on : " + id));

		return customResponseBoardSetting(boardSetting);

	}


	public HashMap<String, Object> postSetting(HttpServletRequest request, BoardSetting boardSetting)
			throws HandledException {

		BoardSetting boardSettingData = boardSettingRepository.findByBoardType(boardSetting.getBoardType());

		if (boardSettingData == null) {

			commonUtil.updateActivities("Post Display Board Setting in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			boardSetting.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			boardSettingRepository.save(boardSetting);

		} else {

			commonUtil.updateActivities("Update Display Board Setting in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			boardSetting.setId(boardSettingData.getId());
			boardSetting.setBoardType(boardSetting.getBoardType());
			boardSetting.setDisplayTime(boardSetting.getDisplayTime());
			boardSetting.setEffect(boardSetting.getEffect());
			boardSetting.setSpeed(boardSetting.getSpeed());
			boardSetting.setLetterSize(boardSetting.getLetterSize());
			boardSetting.setCharacterGap(boardSetting.getCharacterGap());
			boardSetting.setPageChangeTime(boardSetting.getPageChangeTime());
			boardSetting.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			boardSetting.setCreatedAt(boardSettingData.getCreatedAt());
			boardSetting.setCreatedBy(boardSettingData.getCreatedBy());

			boardSettingRepository.save(boardSetting);

		}

		return customResponseBoardSetting(boardSetting);

	}


	//Display Board Diagnostics Override Functions

	@Override
	public HashMap<String, Object> postBoard(HttpServletRequest request, BoardDiagnosticDto boardDiagnostic)
			throws HandledException {

		//		 boardDiagonisticsRepository.save(boardDiagonistics);

		return customResponseBoardDiagnostics(boardDiagnostic);

	}

	//Display Board LED Testing Override Functions

	@Override
	public HashMap<String, Object> postTesting(HttpServletRequest request, BoardLedDto boardLed)
			throws HandledException {

		return customResponseBoardTesting(boardLed);

	}

	//Enable Disable Board Override Functions

	@Override
	public List allEnable() {

		List<EnableDisableBoard> enableBoard = enableDisableBoardRepository.findAll();

		List enableBoardList = new ArrayList<>();

		for (int i = 0; i < enableBoard.size(); i++) {

			enableBoardList.add(customResponseEnable(enableBoard.get(i)));

		}

		return enableBoardList;

	}

	@Override
	public HashMap<String, Object> postEnable(HttpServletRequest request, EnableDisableBoard enableDisableBoard)
			throws HandledException {

		List<EnableDisableBoard> enableBoard = enableDisableBoardRepository.findAll();
		HashMap<String, Object> enableBoardMap = new HashMap<>();

		if (enableBoard.size() == 0) {

			enableDisableBoard.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Post Enable/Disable Board Setting in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			enableDisableBoardRepository.save(enableDisableBoard);
			enableBoardMap = customResponseEnable(enableDisableBoard);

		} else {

			long id = enableBoard.get(0).getId();
			Optional<EnableDisableBoard> enabledisableboard = enableDisableBoardRepository.findById(id);
			EnableDisableBoard boardData = enabledisableboard.get();

			boardData.setMldb(enableDisableBoard.getMldb());
			boardData.setPfdb(enableDisableBoard.getPfdb());
			boardData.setAgdb(enableDisableBoard.getAgdb());
			boardData.setCgdb(enableDisableBoard.getCgdb());
			boardData.setPa(enableDisableBoard.getPa());
			boardData.setIvdDisplay(enableDisableBoard.getIvdDisplay());
			boardData.setOvdDisplay(enableDisableBoard.getOvdDisplay());
			boardData.setTvDisplay(enableDisableBoard.getTvDisplay());
			boardData.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Update Enable/Disable Board Setting in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			enableDisableBoardRepository.save(boardData);
			enableBoardMap = customResponseEnable(boardData);

		}

		return enableBoardMap;

	}

	//Default Messages Override Functions

	@Override
	public List allMessages() {

		List<DefaultMessages> defaultMessages = defaultMessagesRepository.findAll();

		List defaultMsgList = new ArrayList<>();

		for (int i = 0; i < defaultMessages.size(); i++) {

			defaultMsgList.add(customResponseDefault(defaultMessages.get(i)));

		}

		return defaultMsgList;

	}

	@Override
	public HashMap<String, Object> postMessages(HttpServletRequest request, DefaultMessages defaultMessages)
			throws HandledException {

		List<DefaultMessages> defaultMsg = defaultMessagesRepository.findAll();
		HashMap<String, Object> defaultMsgMap = new HashMap<>();

		if (defaultMsg.size() == 0) {

			defaultMessages.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Post Default Message in SetUp ", String.valueOf(customUtil.getIdFromToken()));
			defaultMessagesRepository.save(defaultMessages);
			defaultMsgMap = customResponseDefault(defaultMessages);

		} else {

			long id = defaultMsg.get(0).getId();
			Optional<DefaultMessages> defaultMsgData = defaultMessagesRepository.findById(id);
			DefaultMessages defaultMsgObject = defaultMsgData.get();

			defaultMsgObject.setMldbDefaultMessage(defaultMessages.getMldbDefaultMessage());
			defaultMsgObject.setPfdDefaultMessage(defaultMessages.getPfdDefaultMessage());
			defaultMsgObject.setAgdbDefaultMessage(defaultMessages.getAgdbDefaultMessage());
			defaultMsgObject.setCgdbDefaultMessage(defaultMessages.getCgdbDefaultMessage());
			defaultMsgObject.setOvdDefaultMessage(defaultMessages.getOvdDefaultMessage());
			defaultMsgObject.setIvdDefaultMessage(defaultMessages.getIvdDefaultMessage());
			defaultMsgObject.setTvDisplayDefaultMessage(defaultMessages.getTvDisplayDefaultMessage());
			// defaultMsgObject.setCgdbDefaultMessage(defaultMessages.getCgdbDefaultMessage());

			defaultMsgObject.setLanguage(defaultMessages.getLanguage());
			defaultMsgObject.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Update Default Message in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			defaultMessagesRepository.save(defaultMsgObject);
			defaultMsgMap = customResponseDefault(defaultMsgObject);

		}

		return defaultMsgMap;

	}

	@Override
	public void setDefaultMsg(String boardType, String platformNo) throws HandledException {

		
		List<DefaultMessages> defaultMsg;
		BoardSetting boardSettingData;
		String speed = null;
		String effect = null;
		String letterSize = null;
		int gap=0;
		int timeDelay=0;
		try {

			
			defaultMsg = defaultMessagesRepository.findAll();
			

		} catch (NullPointerException e) {

			throw new HandledException("CHECK_PARAMETERS",
					"Default Message for" + boardType + "doen't exist in Database");
		}

		try {

			boardSettingData = boardSettingRepository.findByBoardType(boardType);
			speed = boardSettingData.getSpeed();
			effect = boardSettingData.getEffect();
			letterSize = boardSettingData.getLetterSize();
			gap=boardSettingData.getCharacterGap();
			timeDelay=boardSettingData.getPageChangeTime();

		} catch (NullPointerException e) {

			System.out.println(e.getLocalizedMessage());

		}
		System.out.println("boardType="+boardType);


		switch (boardType) {

		case "mldb":
			
			String mldbData = defaultMsg.get(0).getMldbDefaultMessage();
			requestWrapper.postRequest(mldbData, RequestTypes.DEFAULT_MSG, boardType, platformNo, speed, effect,letterSize,gap,timeDelay);
			break;
			
		case "ivd":
			
			String ivdData = defaultMsg.get(0).getIvdDefaultMessage();
			sendDefaultMessageForIvdOvd(ivdData, RequestTypes.DEFAULT_MSG, DeviceType.ivd, platformNo, speed, effect,letterSize,gap);
			// requestWrapper.postRequest(ivdData, RequestTypes.DEFAULT_MSG, boardType, platformNo, speed, effect,letterSize,gap);
			break;

		case "ovd":
			
			String ovdData = defaultMsg.get(0).getIvdDefaultMessage();
			sendDefaultMessageForIvdOvd(ovdData, RequestTypes.DEFAULT_MSG, DeviceType.ovd, platformNo, speed, effect,letterSize,gap);
			// requestWrapper.postRequest(ivdData, RequestTypes.DEFAULT_MSG, boardType, platformNo, speed, effect,letterSize,gap);
			break;

		case "pfdb":
			String pfdbData = defaultMsg.get(0).getPfdDefaultMessage();
			requestWrapper.postRequest(pfdbData, RequestTypes.DEFAULT_MSG, boardType, platformNo, speed, effect,letterSize,gap,timeDelay);
			break;

		case "agdb":
			
			String agdbData = defaultMsg.get(0).getAgdbDefaultMessage();
			requestWrapper.postRequest(agdbData, RequestTypes.DEFAULT_MSG, boardType, platformNo, speed, effect,letterSize,gap,timeDelay);
			break;

		case "cgdb":
			
			String[] cgdbData = defaultMsg.get(0).getCgdbDefaultMessage();
			requestWrapper.postRequest(cgdbData, RequestTypes.DEFAULT_MSG, boardType, platformNo, speed, effect,letterSize,gap,timeDelay);
			break;

		default:

			break;

		}

		commonUtil.updateActivities("Display Selected Default Message in SetUp ",
				String.valueOf(customUtil.getIdFromToken()));
		
	}

	/**
	 * @author: Mukul Agrawal
	 * Functionality: sending data from train color status and ivd-ovd config table to create packet.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  no params.
	 */

	@Override
	public HashMap<String, Object> sendDefaultMessageForIvdOvd(String data, Enum url, DeviceType boardType, String platform, String speed, String effect, String letterSize,int gap) {
		HashMap<String, Object> ivdOvdDefaultMessage = new HashMap<>();
		try {
			
			short lColumn = 1, rColumn = 432, tRow = 128, bRow = 1;
			
			byte[] packetData;
			List <Device> ivdOvdData = deviceRepository.findAllBydeviceType(boardType);
			for(Device device: ivdOvdData) {
				String ipAddress = device.getIpAddress();
				String[] ipAddArray = ipAddress.split("[, . ']+");
				
				
				List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
				if(boardType.toString().equals("ivd"))
				{
					
		             if(enableDiable.get(0).getIvdDisplay().equals("enable"))
		             {
		            	 if(ipAddArray[2].equals(platform)){
		 					packetData = tcpPacket.getDefaultMessageCommandPacket(lColumn,rColumn,tRow,bRow,data,boardType,speed,effect,letterSize,gap);
		 					byte[] configuredPacket=tcpPacket.getConfigDataPacket(boardType.toString(), Command.DefaultDataPacket.toString(), ipAddress, packetData);
							InetAddress ia = InetAddress.getByName("127.0.0.1");
		 					tcpClient.sendTcpMsg(configuredPacket,ia,25000,null);	
		 				}
		 				else{
		 					continue;
		 				}
		                  
		             }
		             else
		             {
		            	 throw new HandledException("ivd devices are disabled","Disabled");
		            	 
		             }
				}
				else if(boardType.toString().equals("ovd"))
				{

		             if(enableDiable.get(0).getOvdDisplay().equals("enable"))
		             {
		            	 if(ipAddArray[2].equals(platform)){
		 					packetData = tcpPacket.getDefaultMessageCommandPacket(lColumn,rColumn,tRow,bRow,data,boardType,speed,effect,letterSize,gap);
		 					byte[] configuredPacket=tcpPacket.getConfigDataPacket(boardType.toString(), Command.DefaultDataPacket.toString(), ipAddress, packetData);
		 					sendCommand("10.10.3.47",configuredPacket,5055);	
		 				}
		 				else{
		 					continue;
		 				}
		                  
		             }
		             else
		             {
		            	 throw new HandledException("ovd devices are disabled","Disabled");
		            	 
		             }
				}	
			}
			ivdOvdDefaultMessage.put(Constants.SUCCESS_MESSAGE, "Data sent");
			return ivdOvdDefaultMessage;
		}catch(Exception e) {
			logger.error("Error occured in sendDefaultmessage :{}",e.getMessage());
			ivdOvdDefaultMessage.put(Constants.ERROR_MESSAGE, Constants.ERROR_OCCURED_AT_SERVER);
			return ivdOvdDefaultMessage;
		}
	}

	//Web Configuration Override Functions

	@Override
	public List allWeb() {

		List<WebConfiguration> webconf = webConfigurationRepository.findAll();

		List webconfList = new ArrayList<>();

		for (int i = 0; i < webconf.size(); i++) {

			webconfList.add(customResponseWeb(webconf.get(i)));

		}
		return webconfList;

	}

	@Override
	public HashMap<String, Object> postWeb(HttpServletRequest request, WebConfiguration webConfiguration)
			throws HandledException {

		List<WebConfiguration> webconf = webConfigurationRepository.findAll();
		HashMap<String, Object> webMap = new HashMap<>();

		if (webconf.size() == 0) {

			webConfiguration.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Post Web Configuration in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			webConfigurationRepository.save(webConfiguration);
			webMap = customResponseWeb(webConfiguration);

		} else {

			long id = webconf.get(0).getId();
			Optional<WebConfiguration> webconfiguration = webConfigurationRepository.findById(id);
			WebConfiguration webData = webconfiguration.get();
			webData.setFtpAddress(webConfiguration.getFtpAddress());
			webData.setUsername(webConfiguration.getUsername());
			webData.setPassword(webConfiguration.getPassword());
			webData.setDirectoryName(webConfiguration.getDirectoryName());
			webData.setEnableWebUpload(webConfiguration.getEnableWebUpload());
			webData.setConnectivity(webConfiguration.getConnectivity());
			webData.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Update Web Configuration in SetUp ",
					String.valueOf(customUtil.getIdFromToken()));
			webConfigurationRepository.save(webData);
			webMap = customResponseWeb(webData);

		}

		return webMap;

	}

	// Train Details Override Functions

	@Override
	public long[] allTrain() {

		List<Train> train = trainRepository.findAll();
		long[] trainNoArray = new long[train.size()];

		for (int i = 0; i < train.size(); i++) {

			long trainNo = train.get(i).getTrainNo();
			trainNoArray[i] = trainNo;

		}
		return trainNoArray;

	}

	@Override
	public List getAllTrainData() throws HandledException {
		// TODO Auto-generated method stub

		List<Train> trainDetails = trainRepository.findAll();

		List trainDetailsList = new ArrayList<>();

		for (int i = 0; i < trainDetails.size(); i++) {
			trainDetailsList.add(customTrainDetails(trainDetails.get(i)));
		}

		return trainDetailsList;

	}

	@Override
	public HashMap<String, Object> singleTrain(Long trainNo) throws HandledException {

		Train train = trainRepository.findById(trainNo)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Train Details not found on : " + trainNo));

		return customResponseTrainData(train);

	}

	@Override
	public HashMap<String, Object> postTrain(HttpServletRequest request, Train train) throws HandledException {

		Train data = trainRepository.findByTrainNo(train.getTrainNo());

		if (data == null) {

			TrainDetails trainDetails = new TrainDetails();
			TrainName trainName = new TrainName();
			TrainLiveInfo trainLiveInfo = new TrainLiveInfo();
			CoachDetails coachDetails = new CoachDetails();

			trainName.setTrainNo(train.getTrainNo());
			trainName.setEnglishTrainName(train.getTrainName().getEnglishTrainName());
			trainName.setHindiTrainName(train.getTrainName().getHindiTrainName());
			trainName.setRegionalTrainName(train.getTrainName().getRegionalTrainName());
			trainName.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			trainName.setEnglishWaveFile("");
			trainName.setHindiWaveFile("");
			trainName.setRegionalWaveFile("");

			trainDetails.setTrainNo(train.getTrainNo());
			trainDetails.setScheduleArrivalTime(train.getTrainDetails().getScheduleArrivalTime());
			trainDetails.setScheduleDepartureTime(train.getTrainDetails().getScheduleDepartureTime());
			trainDetails.setMaximumCoach(train.getTrainDetails().getMaximumCoach());
			trainDetails.setRunningDays(train.getTrainDetails().getRunningDays());
			trainDetails.setPlatformNo(train.getTrainDetails().getPlatformNo());
			trainDetails.setMergedTrains(train.getTrainDetails().getMergedTrains());
			trainDetails.setMergedTrainNo(train.getTrainDetails().getMergedTrainNo());
			trainDetails.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));

			trainLiveInfo.setTrainNo(train.getTrainNo());
			trainLiveInfo.setArrDepartStatus("");
			trainLiveInfo.setRunningStatus("");
			trainLiveInfo.setSat("");
			trainLiveInfo.setSdt("");
			trainLiveInfo.setEdt("");
			trainLiveInfo.setEat("");
			trainLiveInfo.setLate("");
			trainLiveInfo.setPlatformNo(train.getTrainDetails().getPlatformNo());

			coachDetails.setTrainNo(train.getTrainNo());
			coachDetails.setFrontSideEnd(train.getCoachDetails().getFrontSideEnd());
			coachDetails.setBackSideEnd(train.getCoachDetails().getBackSideEnd());
			coachDetails.setCoaches(train.getCoachDetails().getCoaches());
			coachDetails.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));

			train.setTrainNo(train.getTrainNo());
			train.setSourceStation(train.getSourceStation());
			train.setDestinationStation(train.getDestinationStation());
			train.setTrainDirection(train.getTrainDirection());
			train.setViaStation(train.getViaStation());
			train.setTrainType(train.getTrainType());
			train.setTrainArrDepStatus(train.getTrainArrDepStatus());
			train.setTrainName(trainName);
			train.setTrainDetails(trainDetails);
			train.setTrainLiveInfo(trainLiveInfo);
			train.setCoachDetails(coachDetails);
			train.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Post Train Data Entry in SetUp ", String.valueOf(customUtil.getIdFromToken()));

			String homeDir = System.getProperty("user.home") + "\\";
			File audiofolder = new File(homeDir + "\\Audio");
			audiofolder.mkdir();
			File enfFolder = new File(homeDir + "\\Audio\\English");
			enfFolder.mkdir();

			File hindiFol = new File(homeDir + "\\Audio\\Hindi");
			hindiFol.mkdir();

			File regFol = new File(homeDir + "\\Audio\\Regional");
			regFol.mkdir();

			File trainNameFolEng = new File(homeDir + "\\Audio\\English\\TrainName");
			trainNameFolEng.mkdir();

			File trainNameFolHind = new File(homeDir + "\\Audio\\Hindi\\TrainName");
			trainNameFolHind.mkdir();

			File trainNameFolReg = new File(homeDir + "\\Audio\\Regional\\TrainName");
			trainNameFolReg.mkdir();

			String engFolderPath = "\\Audio\\English\\TrainName\\";
			String trainname = train.getTrainName().getEnglishTrainName();
			String trainNo=String.valueOf(train.getTrainNo());
			String hindiFolderPath = "\\Audio\\Hindi\\TrainName\\";
			String regFolderPath = "\\Audio\\Regional\\TrainName\\";

			TrainNameEntryAnnouncement trainEntry = new TrainNameEntryAnnouncement();
			trainEntry.setTrainName(trainNo);
			trainEntry.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			trainEntry.setEnglishWaveFile(engFolderPath.concat(trainNo).concat(".wav"));
			trainEntry.setHindiWaveFile(hindiFolderPath.concat(trainNo).concat(".wav"));
			trainEntry.setRegionalWaveFile(regFolderPath.concat(trainNo).concat(".wav"));
			trainNameEntryAnnoRepository.save(trainEntry);
			trainRepository.save(train);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Train Number exist");
		}

		return customResponseTrainData(train);

	}

	@Override
	public HashMap<String, Object> updateTrain(Long trainNo, Train train) throws HandledException {

		Train trainData = trainRepository.findById(trainNo)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Train Details not found on : " + trainNo));

		trainData.getTrainName().setEnglishTrainName(train.getTrainName().getEnglishTrainName());
		trainData.getTrainName().setHindiTrainName(train.getTrainName().getHindiTrainName());
		trainData.getTrainName().setRegionalTrainName(train.getTrainName().getRegionalTrainName());

		trainData.getTrainDetails().setScheduleArrivalTime(train.getTrainDetails().getScheduleArrivalTime());
		trainData.getTrainDetails().setScheduleDepartureTime(train.getTrainDetails().getScheduleDepartureTime());
		trainData.getTrainDetails().setMaximumCoach(train.getTrainDetails().getMaximumCoach());
		trainData.getTrainDetails().setRunningDays(train.getTrainDetails().getRunningDays());
		trainData.getTrainDetails().setPlatformNo(train.getTrainDetails().getPlatformNo());
		trainData.getTrainDetails().setMergedTrains(train.getTrainDetails().getMergedTrains());
		trainData.getTrainDetails().setMergedTrainNo(train.getTrainDetails().getMergedTrainNo());

		trainData.getTrainLiveInfo().setPlatformNo(train.getTrainDetails().getPlatformNo());

		trainData.getCoachDetails().setFrontSideEnd(train.getCoachDetails().getFrontSideEnd());
		trainData.getCoachDetails().setBackSideEnd(train.getCoachDetails().getBackSideEnd());
		trainData.getCoachDetails().setCoaches(train.getCoachDetails().getCoaches());
		trainData.getCoachDetails().setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));

		trainData.setSourceStation(train.getSourceStation());
		trainData.setDestinationStation(train.getDestinationStation());
		trainData.setTrainDirection(train.getTrainDirection());
		trainData.setViaStation(train.getViaStation());
		trainData.setTrainType(train.getTrainType());
		trainData.setTrainArrDepStatus(train.getTrainArrDepStatus());
		commonUtil.updateActivities("Update Train Data Entry in SetUp ", String.valueOf(customUtil.getIdFromToken()));
		trainRepository.save(trainData);

		OnlineTrain onlineTrainData = onlineTrainRepository.findBytrainNumber(trainNo);
		if (onlineTrainData != null) {
			onlineTrainData.setTrainName(train.getTrainName().getEnglishTrainName());
			onlineTrainData.setSTA(train.getTrainDetails().getScheduleArrivalTime());
			onlineTrainData.setSTD(train.getTrainDetails().getScheduleDepartureTime());
			onlineTrainData.setCoaches(train.getCoachDetails().getCoaches());
			onlineTrainData.setPlatformNo(train.getTrainDetails().getPlatformNo());
			onlineTrainData.setETA(train.getTrainDetails().getScheduleArrivalTime());
			onlineTrainData.setETD(train.getTrainDetails().getScheduleDepartureTime());
			onlineTrainRepository.save(onlineTrainData);
		}
		return customResponseTrainData(trainData);

	}

	//Custom Response

	private HashMap<String, Object> customStationDetails(StationDetails stationDetails) {

		HashMap<String, Object> stationDetailsMap = new HashMap<>();

		stationDetailsMap.put("id", stationDetails.getId());
		stationDetailsMap.put("stationName", stationDetails.getStationName());
		stationDetailsMap.put("divisionName", stationDetails.getDivisionName());
		stationDetailsMap.put("regionName", stationDetails.getRegionName());
		stationDetailsMap.put("stationCode", stationDetails.getStationCode());
		stationDetailsMap.put("divisionCode", stationDetails.getDivisionCode());
		stationDetailsMap.put("regionCode", stationDetails.getRegionCode());
		stationDetailsMap.put("northEastEnd", stationDetails.getNorthEastEnd());
		stationDetailsMap.put("southWestEnd", stationDetails.getSouthWestEnd());
		stationDetailsMap.put("languages", stationDetails.getLanguages());
		stationDetailsMap.put("announcementPreference", stationDetails.getAnnouncementPreference());
		stationDetailsMap.put("enableIntegration", stationDetails.getEnableIntegration());
		//stationDetailsMap.put("typeOfIntegration", stationDetails.getTypeOfIntegration());
		stationDetailsMap.put("fileLocation", stationDetails.getFileLocation());
		//stationDetailsMap.put("deviceSchema", stationDetails.getDeviceSchema());
		stationDetailsMap.put("availablePlatforms", stationDetails.getAvailablePlatforms());
		stationDetailsMap.put("listOfPlatforms", stationDetails.getListOfPlatforms());
		stationDetailsMap.put("autoLoadTrainEveryMin", stationDetails.getAutoLoadTrainEveryMin());
		stationDetailsMap.put("autoDeleteTrainEveryMin", stationDetails.getAutoDeleteTrainEveryMin());
		//		stationDetailsMap.put("manuallyGetTrainForNextHours", stationDetails.getManuallyGetTrainForNextHours());
		stationDetailsMap.put("autoSendDataTimeInterval", stationDetails.getAutoSendDataTimeInterval());
		stationDetailsMap.put("autoDeleteTrainTimeInterval", stationDetails.getAutoDeleteTrainEveryMin());
		stationDetailsMap.put("autoLoadTrain", stationDetails.getAutoLoadTrain());
		stationDetailsMap.put("autoDelete", stationDetails.getAutoDelete());
		stationDetailsMap.put("ntesUpdateEnable", stationDetails.getNtesUpdateEnable());
		stationDetailsMap.put("ntesPortType", stationDetails.getNtesPortType());
		stationDetailsMap.put("ntesUpdateTimeInMin", stationDetails.getNtesUpdateTimeInMin());
		stationDetailsMap.put("portNo", stationDetails.getPortNo());
		stationDetailsMap.put("autoTadd", stationDetails.getAutoTadd());
		stationDetailsMap.put("autoSendDataTimeInterval", stationDetails.getAutoSendDataTimeInterval());

		return stationDetailsMap;

	}

	private HashMap<String, Object> customTrainDetails(Train trainList) {

		HashMap<String, Object> trainMap = new HashMap<>();

		trainMap.put("trainNo", trainList.getTrainNo());
		trainMap.put("englishTrainName", trainList.getTrainName().getEnglishTrainName());
		trainMap.put("hindiTrainName", trainList.getTrainName().getHindiTrainName());
		trainMap.put("regionalTrainName", trainList.getTrainName().getRegionalTrainName());
		trainMap.put("sourceStation", trainList.getSourceStation());
		trainMap.put("destinationStation", trainList.getDestinationStation());
		trainMap.put("trainArrDepStatus", trainList.getTrainArrDepStatus());
		trainMap.put("trainDirection", trainList.getTrainDirection());
		trainMap.put("mergedTrains", trainList.getTrainDetails().getMergedTrains());
		trainMap.put("mergedTrainNo", trainList.getTrainDetails().getMergedTrainNo());
		trainMap.put("runningDays", trainList.getTrainDetails().getRunningDays());
		trainMap.put("platformNo", trainList.getTrainDetails().getPlatformNo());
		trainMap.put("scheduleArrivalTime", trainList.getTrainDetails().getScheduleArrivalTime());
		trainMap.put("scheduleDepartureTime", trainList.getTrainDetails().getScheduleDepartureTime());
		trainMap.put("maximumCoach", trainList.getTrainDetails().getMaximumCoach());
		trainMap.put("trainType", trainList.getTrainType());
		trainMap.put("viaStation", trainList.getViaStation());
		trainMap.put("coaches", trainList.getCoachDetails().getCoaches());
		trainMap.put("frontSideEnd", trainList.getCoachDetails().getFrontSideEnd());
		trainMap.put("backSideEnd", trainList.getCoachDetails().getBackSideEnd());
		return trainMap;
	}

	private HashMap<String, Object> customResponseCoach(CoachData coach) {

		HashMap<String, Object> coachMap = new HashMap<>();

		coachMap.put("id", coach.getId());
		coachMap.put("engCoachName", coach.getEngCoachName());
		coachMap.put("hindiCoachName", coach.getHindiCoachName());

		return coachMap;

	}

	private HashMap<String, Object> customResponseCoachCodes(CoachesCode coach) {

		HashMap<String, Object> coachMap = new HashMap<>();

		coachMap.put("id", coach.getId());
		coachMap.put("engCoachName", coach.getEngCoachName());

		return coachMap;

	}

	private HashMap<String, Object> customResponseStationCode(StationCode stationcode) {

		HashMap<String, Object> stationcodeMap = new HashMap<>();

		stationcodeMap.put("id", stationcode.getId());
		stationcodeMap.put("stationCode", stationcode.getStationCode());
		stationcodeMap.put("englishStationName", stationcode.getEnglishStationName());
		stationcodeMap.put("hindiStationName", stationcode.getHindiStationName());
		stationcodeMap.put("regionalStationName", stationcode.getRegionalStationName());
		stationcodeMap.put("englishWaveFile", stationcode.getEnglishWaveFile());
		stationcodeMap.put("hindiWaveFile", stationcode.getHindiWaveFile());
		stationcodeMap.put("regionalWaveFile", stationcode.getRegionalWaveFile());

		return stationcodeMap;

	}

	private HashMap<String, Object> customResponseTrainStatus(TrainStatus trainStatus) {

		HashMap<String, Object> trainStatusMap = new HashMap<>();

		trainStatusMap.put("id", trainStatus.getId());
		trainStatusMap.put("statusCode", trainStatus.getStatusCode());
		trainStatusMap.put("englishStatus", trainStatus.getEnglishStatus());
		trainStatusMap.put("hindiStatus", trainStatus.getHindiStatus());
		trainStatusMap.put("regionalStatus", trainStatus.getRegionalStatus());
		trainStatusMap.put("englishFile", trainStatus.getEnglishFile());
		trainStatusMap.put("hindiFile", trainStatus.getHindiFile());
		trainStatusMap.put("regionalFile", trainStatus.getRegionalFile());
		//		trainStatusMap.put("arrivalDeparture", trainStatus.getAord());

		return trainStatusMap;

	}

	private HashMap<String, Object> customResponseBoardSetting(BoardSetting boardSetting) {

		HashMap<String, Object> boardSettingMap = new HashMap<>();

		boardSettingMap.put("id", boardSetting.getId());
		boardSettingMap.put("boardType", boardSetting.getBoardType());
		boardSettingMap.put("displayTime", boardSetting.getDisplayTime());
		boardSettingMap.put("effect", boardSetting.getEffect());
		boardSettingMap.put("speed", boardSetting.getSpeed());
		boardSettingMap.put("letterSize", boardSetting.getLetterSize());
		boardSettingMap.put("characterGap", boardSetting.getCharacterGap());
		boardSettingMap.put("pageChangeTime", boardSetting.getPageChangeTime());

		return boardSettingMap;

	}

	private HashMap<String, Object> customResponseBoardDiagnostics(BoardDiagnosticDto boardDiagnostic) {

		HashMap<String, Object> boardDiagonisticsMap = new HashMap<>();

		boardDiagonisticsMap.put("boardType", boardDiagnostic.getBoardType());
		boardDiagonisticsMap.put("platformNo", boardDiagnostic.getPlatformNo());
		boardDiagonisticsMap.put("deviceId", boardDiagnostic.getDeviceId());
		boardDiagonisticsMap.put("testCommand", boardDiagnostic.getTestCommand());
		boardDiagonisticsMap.put("sentData", boardDiagnostic.getSentData());
		boardDiagonisticsMap.put("responseTime", boardDiagnostic.getResponseTime());
		boardDiagonisticsMap.put("receivedData", boardDiagnostic.getReceivedData());

		return boardDiagonisticsMap;

	}

	private HashMap<String, Object> customResponseBoardTesting(BoardLedDto boardLed) {

		HashMap<String, Object> boardTestingMap = new HashMap<>();

		boardTestingMap.put("boardType", boardLed.getBoardType());
		boardTestingMap.put("platformNo", boardLed.getPlatformNo());
		boardTestingMap.put("deviceId", boardLed.getDeviceId());
		boardTestingMap.put("testPattern", boardLed.getTestPattern());
		boardTestingMap.put("time", boardLed.getTime());
		boardTestingMap.put("installationTest", boardLed.getInstallationTest());
		boardTestingMap.put("ledAutoTest", boardLed.getLedAutoTest());

		return boardTestingMap;

	}

	private HashMap<String, Object> customResponseEnable(EnableDisableBoard enableDisableBoard) {

		HashMap<String, Object> enableDisableMap = new HashMap<>();

		enableDisableMap.put("id", enableDisableBoard.getId());
		enableDisableMap.put("mldb", enableDisableBoard.getMldb());
		enableDisableMap.put("pfdb", enableDisableBoard.getPfdb());
		enableDisableMap.put("agdb", enableDisableBoard.getAgdb());
		enableDisableMap.put("cgdb", enableDisableBoard.getCgdb());
		enableDisableMap.put("ivdDisplay", enableDisableBoard.getIvdDisplay());
		enableDisableMap.put("ovdDisplay", enableDisableBoard.getOvdDisplay());
		enableDisableMap.put("tvDisplay", enableDisableBoard.getTvDisplay());
		enableDisableMap.put("pa", enableDisableBoard.getPa());

		return enableDisableMap;

	}

	private HashMap<String, Object> customResponseDefault(DefaultMessages defaultMessages) {

		HashMap<String, Object> defaultMessageseMap = new HashMap<>();

		defaultMessageseMap.put("id", defaultMessages.getId());
		defaultMessageseMap.put("mldbDefaultMessage", defaultMessages.getMldbDefaultMessage());
		defaultMessageseMap.put("pfdDefaultMessage", defaultMessages.getPfdDefaultMessage());
		defaultMessageseMap.put("agdbDefaultMessage", defaultMessages.getAgdbDefaultMessage());
		defaultMessageseMap.put("cgdbDefaultMessage", defaultMessages.getCgdbDefaultMessage());
		defaultMessageseMap.put("ovdDefaultMessage", defaultMessages.getOvdDefaultMessage());
		defaultMessageseMap.put("ivdDefaultMessage", defaultMessages.getIvdDefaultMessage());
		defaultMessageseMap.put("tvDisplayDefaultMessage", defaultMessages.getTvDisplayDefaultMessage());

		defaultMessageseMap.put("language", defaultMessages.getLanguage());

		return defaultMessageseMap;

	}

	private HashMap<String, Object> customResponseWeb(WebConfiguration webConfiguration) {

		HashMap<String, Object> webMap = new HashMap<>();

		webMap.put("id", webConfiguration.getId());
		webMap.put("ftpAddress", webConfiguration.getFtpAddress());
		webMap.put("username", webConfiguration.getUsername());
		webMap.put("password", webConfiguration.getPassword());
		webMap.put("directoryName", webConfiguration.getDirectoryName());
		webMap.put("enableWebUpload", webConfiguration.getEnableWebUpload());
		webMap.put("connectivity", webConfiguration.getConnectivity());

		return webMap;

	}

	private HashMap<String, Object> customResponseStationName(StationDetails stationDetails) {

		HashMap<String, Object> stationDetailsMap = new HashMap<>();

		stationDetailsMap.put("stationName", stationDetails.getStationName());
		stationDetailsMap.put("stationCode", stationDetails.getStationCode());

		return stationDetailsMap;

	}

	private HashMap<String, Object> customResponseTrainData(Train train) {

		HashMap<String, Object> trainMap = new HashMap<>();

		trainMap.put("trainNo", train.getTrainNo());
		trainMap.put("englishTrainName", train.getTrainName().getEnglishTrainName());
		trainMap.put("hindiTrainName", train.getTrainName().getHindiTrainName());
		trainMap.put("regionalTrainName", train.getTrainName().getRegionalTrainName());
		trainMap.put("sourceStation", train.getSourceStation());
		trainMap.put("destinationStation", train.getDestinationStation());
		trainMap.put("trainArrDepStatus", train.getTrainArrDepStatus());
		trainMap.put("trainDirection", train.getTrainDirection());
		trainMap.put("mergedTrains", train.getTrainDetails().getMergedTrains());
		trainMap.put("mergedTrainNo", train.getTrainDetails().getMergedTrainNo());
		trainMap.put("runningDays", train.getTrainDetails().getRunningDays());
		trainMap.put("platformNo", train.getTrainDetails().getPlatformNo());
		trainMap.put("scheduleArrivalTime", train.getTrainDetails().getScheduleArrivalTime());
		trainMap.put("scheduleDepartureTime", train.getTrainDetails().getScheduleDepartureTime());
		trainMap.put("maximumCoach", train.getTrainDetails().getMaximumCoach());
		trainMap.put("trainType", train.getTrainType());
		trainMap.put("viaStation", train.getViaStation());
		trainMap.put("coaches", train.getCoachDetails().getCoaches());
		trainMap.put("frontSideEnd", train.getCoachDetails().getFrontSideEnd());
		trainMap.put("backSideEnd", train.getCoachDetails().getBackSideEnd());

		return trainMap;

	}
	//upload train status file

	@Override
	public String uplLoadStationCodeFile(StandardMultipartHttpServletRequest request, String language)
			throws HandledException {
		try {
			final Part p1 = request.getPart("file");
			String filename = p1.getSubmittedFileName();
			String homeDir = System.getProperty("user.home") + "\\";
			File audiofolder = new File(homeDir + "\\Audio");
			audiofolder.mkdir();
			if (language.equals("English")) {
				String path = homeDir + "\\Audio\\English\\StationName\\" + filename;
				File englishFolder = new File(homeDir + "\\Audio\\English");
				englishFolder.mkdir();
				String folderPath = homeDir + "\\Audio\\English\\StationName";
				File fol = new File(folderPath);
				fol.mkdir();
				englishAudioFilePathStationName = path;

				for (Part part : request.getParts()) {
					File file = new File(path);
					part.write(path);
				}

			}
			if (language.equals("Hindi")) {
				String path = homeDir + "\\Audio\\Hindi\\StationName\\" + filename;
				File hindifolder = new File(homeDir + "\\Audio\\Hindi");
				hindifolder.mkdir();
				String folderPath = homeDir + "\\Audio\\Hindi\\StationName";
				File fol = new File(folderPath);
				fol.mkdir();
				hindiAudioFilePathStationName = path;

				for (Part part : request.getParts()) {
					File file = new File(path);
					part.write(path);
				}
			}
			if (language.equals("Regional")) {
				String path = homeDir + "\\Audio\\Regional\\StationName\\" + filename;
				File hindifolder = new File(homeDir + "\\Audio\\Regional");
				hindifolder.mkdir();
				String folderPath = homeDir + "\\Audio\\Regional\\StationName";
				File fol = new File(folderPath);
				fol.mkdir();
				regionalAudioFilePathStationName = path;

				for (Part part : request.getParts()) {
					File file = new File(path);
					part.write(path);
				}
			}

			//	trainStatusEntryEntity.setAudioFile();
			return filename;

		} catch (Exception e) {
			throw new HandledException("upload error", e.getMessage());
		}
	}

	// train status file upload
	public String uploadTrainStatusFile(StandardMultipartHttpServletRequest request, String language)
			throws HandledException {

		// Case statements
		try {

			final Part p1 = request.getPart("file");
			String filename = p1.getSubmittedFileName();
			String homeDir = System.getProperty("user.home") + "\\";
			File audiofolder = new File(homeDir + "\\Audio");
			audiofolder.mkdir();
			if (language.equals("English")) {
				String path = homeDir + "\\Audio\\English\\trainStatus\\" + filename;
				File englishFolder = new File(homeDir + "\\Audio\\English");
				englishFolder.mkdir();
				String folderPath = homeDir + "\\Audio\\English\\trainStatus";
				File fol = new File(folderPath);
				fol.mkdir();
				englishAudioFilePath = path;

				for (Part part : request.getParts()) {
					File file = new File(path);
					part.write(path);
				}

			}
			if (language.equals("Hindi")) {
				String path = homeDir + "\\Audio\\Hindi\\trainStatus\\" + filename;
				File hindifolder = new File(homeDir + "\\Audio\\Hindi");
				hindifolder.mkdir();
				String folderPath = homeDir + "\\Audio\\Hindi\\trainStatus";
				File fol = new File(folderPath);
				fol.mkdir();
				hindiAudioFilePath = path;

				for (Part part : request.getParts()) {
					File file = new File(path);
					part.write(path);
				}
			}
			if (language.equals("Regional")) {
				String path = homeDir + "\\Audio\\Regional\\trainStatus\\" + filename;
				File hindifolder = new File(homeDir + "\\Audio\\Regional");
				hindifolder.mkdir();
				String folderPath = homeDir + "\\Audio\\Regional\\trainStatus";
				File fol = new File(folderPath);
				fol.mkdir();
				regionalAudioFilePath = path;

				for (Part part : request.getParts()) {
					File file = new File(path);
					part.write(path);
				}
			}

			//	trainStatusEntryEntity.setAudioFile();
			return filename;

		} catch (Exception e) {
			throw new HandledException("upload error", e.getMessage());
		}
	}

	@Override
	public Map<String, Boolean> deleteUploadedFile(String filename, String language)
			throws HandledException, Exception, ServletException {
		// TODO Auto-generated method stub

		System.out.println(language);
		String homeDir = System.getProperty("user.home") + "\\";

		Map<String, Boolean> response = new HashMap<>();

		if (language.equals("English")) {
			try {

				String folderPath = homeDir + "\\Audio\\English\\trainStatus\\";

				String path = folderPath + filename;
				Files.deleteIfExists(Paths.get(path));
			} catch (NoSuchFileException e) {
				System.out.println(e.toString());
			}
			response.put("deleted", Boolean.TRUE);

		}
		if (language.equals("Hindi")) {
			try {
				String folderPath = homeDir + "\\Audio\\Hindi\\trainStatus\\";

				String path = folderPath + filename;
				Files.deleteIfExists(Paths.get(path));
			} catch (NoSuchFileException e) {
				System.out.println(e.toString());
			}
			response.put("deleted", Boolean.TRUE);

		}

		if (language.equals("Regional")) {
			try {
				String folderPath = homeDir + "\\Audio\\Regional\\trainStatus\\";

				String path = folderPath + filename;
				Files.deleteIfExists(Paths.get(path));
			} catch (NoSuchFileException e) {
				System.out.println(e.toString());
			}
			System.out.println("Deletion successful.");
			response.put("deleted", Boolean.TRUE);

		}
		return response;

	}

	@Override
	public Map<String, Boolean> deleteStationCodeUpload(String filename, String language)
			throws HandledException, Exception, ServletException {
		// TODO Auto-generated method stub

		System.out.println(language);
		String homeDir = System.getProperty("user.home") + "\\";

		Map<String, Boolean> response = new HashMap<>();

		if (language.equals("English")) {
			try {

				String folderPath = homeDir + "\\Audio\\English\\StationName\\";

				String path = folderPath + filename;
				Files.deleteIfExists(Paths.get(path));
			} catch (NoSuchFileException e) {
				System.out.println(e.toString());
			}
			response.put("deleted", Boolean.TRUE);

		}
		if (language.equals("Hindi")) {
			try {
				
				String folderPath = homeDir + "\\Audio\\Hindi\\StationName\\";

				String path = folderPath + filename;
				Files.deleteIfExists(Paths.get(path));
			} catch (NoSuchFileException e) {
				System.out.println(e.toString());
			}
			response.put("deleted", Boolean.TRUE);

		}

		if (language.equals("Regional")) {
			try {
				String folderPath = homeDir + "\\Audio\\Regional\\StationName\\";

				String path = folderPath + filename;
				Files.deleteIfExists(Paths.get(path));
			} catch (NoSuchFileException e) {
				System.out.println(e.toString());
			}
			System.out.println("Deletion successful.");
			response.put("deleted", Boolean.TRUE);

		}
		return response;

	}

	// auto load

	@Override
	public void autoTrainUpload() throws Exception {
		{
			
			StationDetails sd = stationDetailsRepository.findAll().get(0);
if(!sd.equals(null))
{
			boolean autoload = stationDetailsRepository.findAll().get(0).getAutoLoadTrain();
			int autoLoadTime = stationDetailsRepository.findAll().get(0).getAutoLoadTrainEveryMin();

			String day = LocalDate.now().getDayOfWeek().name();
			String dayL = day.toLowerCase();
			String daySubstring3 = dayL.substring(0, 3);
			String daySubstring4 = dayL.substring(0, 4);

			if (autoload == true) {

				List<Train> train = trainRepository.findAll();
				if(train.size()!=0) {
				SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.ENGLISH);

				SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

				try {

					for (int i = 0; i < train.size(); i++) {
						boolean dayFlag = false;
						String sat = train.get(i).getTrainDetails().getScheduleArrivalTime();
						String[] runningdays = train.get(i).getTrainDetails().getRunningDays();
						for (int k = 0; k < runningdays.length; k++) {
							if (daySubstring3.equals(runningdays[k].toLowerCase())
									|| daySubstring4.equals(runningdays[k].toLowerCase())) {
								dayFlag = true;
							}
						}
						if (dayFlag == true) {
							Calendar date = Calendar.getInstance();
							Date currentTime = date.getTime();
							String st = Integer.toString(currentTime.getDate());
							String st1 = Integer.toString(currentTime.getMonth() + 1);
							String st2 = Integer.toString(date.getWeekYear());

							String stastr = st + "-" + st1 + "-" + st2;
							Date satTime = formatter.parse(stastr + " " + sat);
							String satarr[] = sat.split(":");

							if (satarr[0].equals("12")) {
								satTime.setHours(12);
							}
							String cuForm = dateFormat.format(currentTime);
							Date newCurr = dateFormat.parse(cuForm);
							long timeInSecs = date.getTimeInMillis();
							Date afterAdding10Mins = new Date(timeInSecs + (autoLoadTime * 60 * 1000));
							String neafterAdding10Mins = dateFormat.format(afterAdding10Mins);
							Date newAfterAdding = dateFormat.parse(neafterAdding10Mins);

							if (satTime.after(currentTime) && satTime.before(afterAdding10Mins)) {
								Long trainNo = train.get(i).getTrainNo();
								OnlineTrain onlineTrain1 = onlineTrainRepository.findBytrainNumber(trainNo);
								if (onlineTrain1 == null) {
									OnlineTrain onlineTrain = new OnlineTrain();
									onlineTrain.setTrainStatus("Running Right Time");
									onlineTrain.setCGDB(true);
									onlineTrain.setTD(true);
									onlineTrain.setCGDB(true);
									onlineTrain.setAnnouncement(true);
									onlineTrain.setTrainNumber(train.get(i).getTrainNo());
									onlineTrain.setTrainName(train.get(i).getTrainName().getEnglishTrainName());
									onlineTrain.setArrDep(train.get(i).getTrainArrDepStatus());
									onlineTrain.setBackEnd(train.get(i).getCoachDetails().getBackSideEnd());
									onlineTrain.setCoaches(train.get(i).getCoachDetails().getCoaches());
									onlineTrain.setETA(train.get(i).getTrainDetails().getScheduleArrivalTime());
									onlineTrain.setETD(train.get(i).getTrainDetails().getScheduleDepartureTime());
									onlineTrain.setFrontEnd(train.get(i).getCoachDetails().getFrontSideEnd());
									onlineTrain.setPlatformNo(train.get(i).getTrainDetails().getPlatformNo());
									onlineTrain.setSTA(train.get(i).getTrainDetails().getScheduleArrivalTime());
									onlineTrain.setSTD(train.get(i).getTrainDetails().getScheduleDepartureTime());

									onlineTrainRepository.save(onlineTrain);
								}

							}
						}
					}
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
			}
		}		
		}

	}

	@Override
	public void autoDeleteTrain() throws HandledException {
		// TODO Auto-generated method stub
		StationDetails sd = stationDetailsRepository.findAll().get(0);
		if(!sd.equals(null))
		{
		
		boolean autoDelete = stationDetailsRepository.findAll().get(0).getAutoDelete();
		int autoDeleteTime = stationDetailsRepository.findAll().get(0).getAutoDeleteTrainEveryMin();
		
		if (autoDelete == true) {

			List<OnlineTrain> onlineTrain = onlineTrainRepository.findAll();
			if(onlineTrain.size()!=0) {
			SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.ENGLISH);

			SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");

			try {

				for (int i = 0; i < onlineTrain.size(); i++) {
					String edt = onlineTrain.get(i).getETD();

					if (edt == null || edt.equals("") || edt.equals("00") || edt.equals("0")) {
						edt = "00:00";
					}

					Calendar date = Calendar.getInstance();
					Date currentTime = date.getTime();
					String st = Integer.toString(currentTime.getDate());
					String st1 = Integer.toString(currentTime.getMonth() + 1);
					String st2 = Integer.toString(date.getWeekYear());

					String stastr = st + "-" + st1 + "-" + st2;

					Date edtTime = formatter.parse(stastr + " " + edt);

					String edtarr[] = edt.split(":");

					if (edtarr[0].equals("12")) {
						edtTime.setHours(12);
					}
					long timeInSecs = date.getTimeInMillis();

					Date afterAddingDeleteTime = new Date(edtTime.getTime() + (autoDeleteTime * 60 * 1000));

					if (currentTime.after(afterAddingDeleteTime)) {
						Long trainNo = onlineTrain.get(i).getTrainNumber();
						OnlineTrain onlineTrain1 = onlineTrainRepository.findBytrainNumber(trainNo);
						if (onlineTrain1 != null) {
							System.out.println("1980");
							onlineTrainRepository.delete(onlineTrain1);
						}

					}

				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		}
		}	
	}

	@Override
	public Map<String, Boolean> deleteTrainData(Long trainNo) throws HandledException {

		Train train = trainRepository.findById(trainNo)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "TrainData not found on : " + trainNo));

		trainRepository.delete(train);

		OnlineTrain onlineTrain = onlineTrainRepository.findBytrainNumber(trainNo);
		if (onlineTrain != null) {
			onlineTrainRepository.delete(onlineTrain);
		}
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);

		return response;
	}

	@Override
	public String uplLoadStationFile(StandardMultipartHttpServletRequest request) throws Exception {
		// TODO Auto-generated method stub

		final Part p1 = request.getPart("file");
		String filename = p1.getSubmittedFileName();
		String homeDir = System.getProperty("user.home") + "\\";
		File audiofolder = new File(homeDir + "\\StationDetailFile");
		audiofolder.mkdir();
		String path = homeDir + "\\StationDetailFile\\" + filename;

		for (Part part : request.getParts()) {
			File file = new File(path);
			part.write(path);
		}

		return filename;
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: Addind train status color in table.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  TrainStatusCOlor object.
	 */
	@Override
	public HashMap<String, Object> postTrainStatusColor(TrainStatusColor trainStatusColor) {
		try{
			if(trainStatusColor != null ) {
				trainStatusColor.setArrDep(trainStatusColor.getStatus().substring(0, 1));
				trainStatusColor.setStatus(trainStatusColor.getStatus().substring(1, trainStatusColor.getStatus().length()));
				trainStatusColorRepository.save(trainStatusColor);
				return customResponseTrainStatusColor(trainStatusColor);
			}
		}
		catch(Exception e) {
			logger.error("Error occured in sendTrainColorPacket :{}",e.getMessage());
		}
		return customResponseTrainStatusColor(null);
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: Addind Ivd screen color config in table.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  IvdScreenColorConfig object.
	 */
	@Override
	public HashMap<String, Object> postIvdScreenColorConfig(IvdScreenColorConfig ivdScreenColorConfig) {
		try {
			HashMap<String, Object> stationDetailsMap = new HashMap<>();
			IvdScreenColorConfig listIvdScreenColorConfig = ivdScreenColorConfigRepository.findById(1L);
			if(ivdScreenColorConfig != null) {
				if(ivdScreenColorConfig.getBackgroundColor() == null) stationDetailsMap.put(Constants.ERROR_MESSAGE, Constants.NO_BACKGROUND_COLOR_FOUND);
				else if(ivdScreenColorConfig.getHorizontalColor() == null)  stationDetailsMap.put(Constants.ERROR_MESSAGE, Constants.NO_HORIZONTAL_COLOR_FOUND);
				else if(ivdScreenColorConfig.getVerticalColor() == null) stationDetailsMap.put(Constants.ERROR_MESSAGE, Constants.NO_VERTICAL_COLOR_FOUND);
				else if(ivdScreenColorConfig.getMessageColor() == null) stationDetailsMap.put(Constants.ERROR_MESSAGE, Constants.NO_MESSAGE_COLOR_FOUND);
				if(stationDetailsMap.containsKey(Constants.ERROR_MESSAGE)) return stationDetailsMap;
			}
			if(listIvdScreenColorConfig == null) {
				ivdScreenColorConfigRepository.save(ivdScreenColorConfig);
			}else { 
				listIvdScreenColorConfig.setBackgroundColor(ivdScreenColorConfig.getBackgroundColor());
				listIvdScreenColorConfig.setHorizontalColor(ivdScreenColorConfig.getHorizontalColor());
				listIvdScreenColorConfig.setVerticalColor(ivdScreenColorConfig.getVerticalColor());
				listIvdScreenColorConfig.setMessageColor(ivdScreenColorConfig.getMessageColor());
				ivdScreenColorConfigRepository.save(listIvdScreenColorConfig);
			}
			return customResponseIvdScreenColorConfig(ivdScreenColorConfig);
		}
		catch(Exception e) {
			logger.error("Error occured in sendTrainColorPacket :{}",e.getMessage());
		} 
		return customResponseTrainStatusColor(null);
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: updating train status color in table.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  TrainStatusColor object.
	 */
	@Override
	public HashMap<String, Object> updateTrainStatusColor(TrainStatusColor trainStatusColor) {
		try {
			TrainStatusColor fetchTrainStatusColor =   trainStatusColorRepository.findById(trainStatusColor.getId());
			if(fetchTrainStatusColor!=null) {
				if(trainStatusColor.getStatus() != null) {
					fetchTrainStatusColor.setArrDep(trainStatusColor.getStatus().substring(0, 1));
					fetchTrainStatusColor.setStatus(trainStatusColor.getStatus().substring(1, trainStatusColor.getStatus().length()));
				}
				if(trainStatusColor.getTrainADColor() != null) fetchTrainStatusColor.setTrainADColor(trainStatusColor.getTrainADColor());
				if(trainStatusColor.getTrainNameColor() != null) fetchTrainStatusColor.setTrainNameColor(trainStatusColor.getTrainNameColor());
				if(trainStatusColor.getTrainNoColor() != null) fetchTrainStatusColor.setTrainNoColor(trainStatusColor.getTrainNoColor());
				if(trainStatusColor.getTrainPfColor() != null) fetchTrainStatusColor.setTrainPfColor(trainStatusColor.getTrainPfColor());
				if(trainStatusColor.getTrainTimeColor() != null) fetchTrainStatusColor.setTrainTimeColor(trainStatusColor.getTrainTimeColor());
				return customResponseTrainStatusColor(trainStatusColorRepository.save(fetchTrainStatusColor));
			} 
		}
		catch(Exception e) {
			logger.error("Error occured in sendTrainColorPacket :{}",e.getMessage());
		}
		return customResponseTrainStatusColor(null);
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: updating Ivd-screen-color-config in table.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  IvdScreenColorConfig object.
	 */
	@Override
	public HashMap<String, Object> updateIvdScreenColorConfig(IvdScreenColorConfig ivdScreenColorConfig) {
		try {
			IvdScreenColorConfig fetchIvdScreenColorConfig = ivdScreenColorConfigRepository.findById(ivdScreenColorConfig.getId());
			if(fetchIvdScreenColorConfig!= null) {
				if(ivdScreenColorConfig.getBackgroundColor() != null) fetchIvdScreenColorConfig.setBackgroundColor(ivdScreenColorConfig.getBackgroundColor());
				if(ivdScreenColorConfig.getHorizontalColor() != null) fetchIvdScreenColorConfig.setHorizontalColor(ivdScreenColorConfig.getHorizontalColor());
				if(ivdScreenColorConfig.getMessageColor() != null) fetchIvdScreenColorConfig.setMessageColor(ivdScreenColorConfig.getMessageColor());
				if(ivdScreenColorConfig.getVerticalColor() != null) fetchIvdScreenColorConfig.setVerticalColor(ivdScreenColorConfig.getVerticalColor());
				return customResponseIvdScreenColorConfig(ivdScreenColorConfigRepository.save(fetchIvdScreenColorConfig));
			} 
		}catch(Exception e) {
			logger.error("Error occured in sendTrainColorPacket :{}",e.getMessage());
		}
		return customResponseIvdScreenColorConfig(null);
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: Creating custom response for train status color.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  TrainStatusColor object.
	 */
	private HashMap<String, Object> customResponseTrainStatusColor (TrainStatusColor  trainStatusColor ) {
		HashMap<String, Object> stationDetailsMap = new HashMap<>();
		if(trainStatusColor != null) stationDetailsMap.put(Constants.SUCCESS_MESSAGE, Constants.TRAIN_COLOR_ADDEDD);
		else stationDetailsMap.put(Constants.ERROR_MESSAGE, Constants.ERROR_OCCURED_AT_SERVER);
		return stationDetailsMap;
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: Creating custom response for ivd-screen-color-config.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  IvdScreenColorConfig object.
	 */
	private HashMap<String, Object> customResponseIvdScreenColorConfig (IvdScreenColorConfig  ivdScreenColorConfig) {
		HashMap<String, Object> stationDetailsMap = new HashMap<>();
		if(ivdScreenColorConfig != null) stationDetailsMap.put(Constants.SUCCESS_MESSAGE, Constants.IVD_SCREEN_COLOR_ADDED_SUCCESSFULLY);
		else stationDetailsMap.put(Constants.ERROR_MESSAGE, Constants.ERROR_OCCURED_AT_SERVER);
		return stationDetailsMap;
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: fetching train status color by status and arrival departure..
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  status. arrival-departure
	 */
	@Override
	public HashMap<String, Object> getTrainStatusColor(String status,String arrDep) {
		TrainStatusColor trainStatusColor = trainStatusColorRepository.findByStatusAndArrDep(status,arrDep);
		return customResponseOfGetTrainStatusColor(trainStatusColor);
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: Setting custom response of fetching train status color by status and arrival departure..
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  TrainStatusColor object
	 */
	private HashMap<String, Object> customResponseOfGetTrainStatusColor(TrainStatusColor trainStatusColor) {
		HashMap<String, Object> trainStatusColorMap = new HashMap<>();
		if(trainStatusColor != null) {
			trainStatusColorMap.put(Constants.ID, trainStatusColor.getId());
			trainStatusColorMap.put(Constants.STATUS, trainStatusColor.getArrDep()+trainStatusColor.getStatus());
			trainStatusColorMap.put(Constants.TRAINADCOLOR, trainStatusColor.getTrainADColor());
			trainStatusColorMap.put(Constants.TRAINNAMECOLOR, trainStatusColor.getTrainNameColor());
			trainStatusColorMap.put(Constants.TRAINNOCOLOR, trainStatusColor.getTrainNoColor());
			trainStatusColorMap.put(Constants.TRAINPFCOLOR, trainStatusColor.getTrainPfColor());
			trainStatusColorMap.put(Constants.TRAINTIMECOLOR, trainStatusColor.getTrainTimeColor());
		}else {
			trainStatusColorMap.put(Constants.ERROR_MESSAGE, Constants.NO_DATA_FOUND);
		}
		return trainStatusColorMap;
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: fetching ivd-color-config by id.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  id.
	 */
	@Override
	public HashMap<String, Object> getIvdScreenColorConfig(long id) {
		IvdScreenColorConfig ivdScreenColorConfig = ivdScreenColorConfigRepository.findById(id);
		return customResponseOfGetIvdScreenColorConfig(ivdScreenColorConfig);
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: sending custom response of ivd screen color config get request.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  no params.
	 */
	private HashMap<String, Object> customResponseOfGetIvdScreenColorConfig(IvdScreenColorConfig ivdScreenColorConfig) {
		HashMap<String, Object> ivdScreenColorConfigMap = new HashMap<>();
		if(ivdScreenColorConfig != null) {
			ivdScreenColorConfigMap.put(Constants.IVD_ID, ivdScreenColorConfig.getId());
			ivdScreenColorConfigMap.put(Constants.BACKGROUND_COLOR, ivdScreenColorConfig.getBackgroundColor());
			ivdScreenColorConfigMap.put(Constants.HORIZONTAL_COLOR, ivdScreenColorConfig.getHorizontalColor());
			ivdScreenColorConfigMap.put(Constants.VERTICAL_COLOR, ivdScreenColorConfig.getVerticalColor());
			ivdScreenColorConfigMap.put(Constants.MESSAGE_COLOR, ivdScreenColorConfig.getMessageColor());

		}else {
			ivdScreenColorConfigMap.put(Constants.ERROR_MESSAGE, Constants.NO_DATA_FOUND);
		}
		return ivdScreenColorConfigMap;
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: sending data from train color status and ivd-ovd config table to create packet.
	 * @return: hash map of success or error message
	 * @createdAt: 06/12/2022
	 * @param:  no params.
	 */
	@Override
	public HashMap<String, Object> sendTrainColorPacket() {
		HashMap<String, Object> ivdScreenColorConfigMap = new HashMap<>();
		try {
			byte[] packetData;
			int allIntensity = getAutoManualIntensity();
			int displayTimeOut = getDisplayTimeOut();
			IvdScreenColorConfig ivdScreenColorConfig =ivdScreenColorConfigRepository.findById(1);
			List<TrainStatusColor> trainStatusColor = trainStatusColorRepository.findAll();
			List <Device> ivdData = deviceRepository.findAllBydeviceType(DeviceType.ivd);
			for(Device device: ivdData) {
				String ipAddress = device.getIpAddress();
				int singleIntensity = getMaunalSingleIntensity(ipAddress, device);
				if(singleIntensity!=-1){
					 packetData = tcpPacket.getTrainColorCommandPacket(ivdScreenColorConfig,trainStatusColor, ipAddress,singleIntensity,displayTimeOut);
				}
				else{
					 packetData = tcpPacket.getTrainColorCommandPacket(ivdScreenColorConfig,trainStatusColor, ipAddress,allIntensity,displayTimeOut);
				}
				byte[] configuredPacket=tcpPacket.getConfigDataPacket(DeviceType.ivd.toString(), Command.SetConfiguration.toString(), ipAddress, packetData);
				InetAddress ia = InetAddress.getByName("127.0.0.1");
		 		tcpClient.sendTcpMsg(configuredPacket,ia,25000,null);
			}
			ivdScreenColorConfigMap.put(Constants.SUCCESS_MESSAGE, "Data sent");
			return ivdScreenColorConfigMap;
		}catch(Exception e) {
			logger.error("Error occured in sendTrainColorPacket :{}",e.getMessage());
			ivdScreenColorConfigMap.put(Constants.ERROR_MESSAGE, Constants.ERROR_OCCURED_AT_SERVER);
			return ivdScreenColorConfigMap;
		}
	}

	/**
	 * @author: Mukul Agrawal
	 * Functionality: Getting Display time out.
	 * @return: display time out 
	 * @createdAt: 09/12/2022
	 * @param:  no params.
	 */

	public Integer getDisplayTimeOut(){
		try{
			BoardSetting boardSettingData = boardSettingRepo.findByBoardType((DeviceType.ivd).toString());
			String displayTimeOut=boardSettingData.getDisplayTime();
			return Integer.parseInt(displayTimeOut);
		}
		catch(Exception e) {
			logger.error("Error occured in getting display time out  :{}",e.getMessage());
			return null;
		}
	}

	/**
	 * @author: Mukul Agrawal
	 * Functionality: Getting Intensoty for auto or manual Intensity.
	 * @return: Intensity 
	 * @createdAt: 09/12/2022
	 * @param:  no params.
	 */

	public Integer getAutoManualIntensity() throws HandledException{
		List <Intensity> intensityData = intensityRepository.findAll();
		for(Intensity intensity: intensityData) {
			if(intensity.getIntensityMode().equals("Auto")){
				Calendar date = Calendar.getInstance();
				Date currentTime = date.getTime();
				System.out.println("current time" + currentTime);

				SimpleDateFormat formatMinutes = new SimpleDateFormat("mm");
				String getMinutes = formatMinutes.format(new Date());

				SimpleDateFormat formatHours = new SimpleDateFormat("HH");
				String getHours = formatHours.format(new Date());

				SimpleDateFormat formatSecs = new SimpleDateFormat("ss");
				String getSecs = formatSecs.format(new Date());

				System.out.println("hh:mm=" + getHours + ":" + getMinutes);
				System.out.println("intensity.getDayStartTime()==" + intensity.getDayStartTime());
				String getDayStartTime = intensity.getDayStartTime();
				String getNightStartTime = intensity.getNightStartTime();

				LocalTime systemTime, dayStartTime, nightStartTime;

				int sysHr = Integer.parseInt(getHours);
				int sysMm = Integer.parseInt(getMinutes);
				systemTime = LocalTime.of(sysHr, sysMm);

				String[] dayTime = getDayStartTime.split(":");
				int dayHr = Integer.parseInt(dayTime[0]);
				int dayMm = Integer.parseInt(dayTime[1]);
				dayStartTime = LocalTime.of(dayHr, dayMm);

				String[] nightTime = getNightStartTime.split(":");
				int nightHr = Integer.parseInt(nightTime[0]);
				int nightMm = Integer.parseInt(nightTime[1]);
				nightStartTime = LocalTime.of(nightHr, nightMm);

				int value1 = systemTime.compareTo(dayStartTime);
				System.out.println("value1=" + value1);
				int value2 = systemTime.compareTo(nightStartTime);
				System.out.println("value2=" + value2);

				if ((value1 >= 0) && (value2 < 0)) {
					System.out.println("time comarison day start case");
					requestWrapper.postRequest(intensity.getDayIntensity(), RequestTypes.SET_INTENSITY,
							intensity.getIntensityMode());
							return intensity.getDayIntensity();

				} else if ((value2 >= 0) && (value1 < 0)) {
					requestWrapper.postRequest(intensity.getNightIntensity(), RequestTypes.SET_INTENSITY,
							intensity.getIntensityMode());
							return intensity.getNightIntensity();
				} else {
					throw new HandledException("CHECK_PARAMETERS", "Day time and night time clashes");
				}
			}
			else if(intensity.getIntensityMode().equals("Manual") && intensity.getMode().equals("All")){
				return intensity.getIntensityValue();
			}
		}
		return -1;
	}

	
	/**
	 * @author: Mukul Agrawal
	 * Functionality: Getting Intensity for single Devices.
	 * @return: Intensity 
	 * @createdAt: 09/12/2022
	 * @param:  no params.
	 */
	

	public int getMaunalSingleIntensity(String ipAddress, Device device) {
		String[] ipAddArray = ipAddress.split("[, . ']+");
		String platform = ipAddArray[2];
		String deviceId = ipAddArray[3];
		List<Intensity> ivdData = intensityRepository.findAll();
		for (Intensity intensity : ivdData) {	
			String a= intensity.getIntensityMode()	;
			String b= intensity.getMode() 	;
			if (intensity.getIntensityMode().equalsIgnoreCase("Manual") && intensity.getMode().equalsIgnoreCase("single")) {
				System.out.println("ss");
				if (intensity.getPlatform().equals(platform) && intensity.getDeviceId().equals(deviceId)) {
					return intensity.getIntensityValue();
				}
			}
		}
		return -1;
	}

	/**
	 * @author: Fardeen Mirza
	 * Functionality: sending bytes to ivd-ovd handler
	 * @return: void
	 * @createdAt: 23/11/2022
	 * @param:  IP, array of bytes.
	 */
	public void sendPacketChunksCommand(String ip, byte[] cmd, int port) {
		try {
			Socket socket = new Socket(ip,port);
			InputStream fromServer = socket.getInputStream();
			OutputStream toServer = socket.getOutputStream();
			socket.setSoTimeout(0);
			toServer.write(cmd);
			fromServer.close();
			toServer.close();
			socket.close(	);
		} catch (IOException ex) {
			logger.error("Error occured while sending the byte chunks array to ivd-ovd :{}",ex.getMessage());
		}
	}
	/**
	 * @author: Fardeen Mirza
	 * Functionality: sending bytes to ivd-ovd handler
	 * @return: void
	 * @createdAt: 23/11/2022
	 * @param:  IP, array of bytes.
	 */
	public void sendCommand(String ip, byte[] cmd, int port) {
		try {
			Socket socket = new Socket(ip,port);
			InputStream fromServer = socket.getInputStream();
			OutputStream toServer = socket.getOutputStream();
			socket.setSoTimeout(0);
			toServer.write(cmd);
			fromServer.close();
			toServer.close();
			socket.close();
		} catch (IOException ex) {
			logger.error("Error occured while sending the byte array to ivd-ovd :{}",ex.getMessage());
		}
	}
	/**
	 * @author: Fardeen Mirza
	 * Functionality: sending bytes chunks to ivd-ovd handler
	 * @return: void
	 * @createdAt: 23/11/2022
	 * @param:  IP, list of array of bytes, port
	 */
	public void sendChunksOfPacketCommand(String ip, List<byte[]> finalListOfByteArray, int port) {
		try {
			Socket socket = new Socket(ip,port);
			InputStream fromServer = socket.getInputStream();
			OutputStream toServer = socket.getOutputStream();
			socket.setSoTimeout(0);
			for(int i=0;i<finalListOfByteArray.size();i++) {
				toServer.write(finalListOfByteArray.get(i));
			}
			fromServer.close();
			toServer.close();
			socket.close();
		} catch (IOException ex) {
			logger.error("Error occured while sending the byte array chunks to ivd-ovd :{}",ex.getMessage());
		}
	}
	

	/**
	 * @author: Fardeen Mirza
	 * Functionality: sending video bytes to ivd-ovd handler
	 * @return: void
	 * @createdAt: 07/12/2022.
	 * @param: no param
	 */
	@Override
	public HashMap<String, Object> sendVideoPacket() {
		HashMap<String, Object> postdetail = new HashMap<>();
		
		try {
			File videofile = null;
			List<MediaQueue> listOfMediaQueue = mediaQueueRepo.findByDisplayType("ivd");
			sendCommand("10.10.3.47", new byte[listOfMediaQueue.size()], 6000);
			for(MediaQueue mediaQueue:listOfMediaQueue) {
				if(Boolean.TRUE.equals(mediaQueue.getIsPlaying())) {
					List<byte[] > finalListOfByteArray = new ArrayList<>();
					StringBuilder homeDir = new StringBuilder(System.getProperty("user.home"));
					homeDir.append("/TvMedia/");
					homeDir.append(mediaQueue.getMediaLocation().substring(12, mediaQueue.getMediaLocation().length()));
					videofile = new File(homeDir.toString());
					byte[] videoBytes = FileUtils.readFileToByteArray(videofile);
					int blockSize = 60000;
					int blockCount = (videoBytes.length + blockSize - 1) / blockSize;
					byte[] range = null;
					for (int i = 1; i < blockCount; i++) {
							int idx = (i - 1) * blockSize;
							range = Arrays.copyOfRange(videoBytes, idx, idx + blockSize);
							finalListOfByteArray.add(range);
							//sendCommand("10.10.3.52",range,6000);
					}
					int end = -1;
					if (videoBytes.length % blockSize == 0) end = videoBytes.length;
					else end = videoBytes.length % blockSize + blockSize * (blockCount - 1);
					range = Arrays.copyOfRange(videoBytes, (blockCount - 1) * blockSize, end);
					finalListOfByteArray.add(range);
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					byte[] dummy="0".getBytes();
					InetAddress ia = InetAddress.getByName("127.0.0.1");
					try {
						tcpClient.sendTcpMsg(dummy,ia,25000,finalListOfByteArray);
					} catch (HandledException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					 
				}
			}
			// byte[] pathAddedInListBytes = new byte[3];
			 
			// sendCommand("10.10.3.52", pathAddedInListBytes, 6000);
			postdetail.put(Constants.SUCCESS_MESSAGE, Constants.VIDEO_PACKET_SENT);
		}catch (IOException ex) {
			logger.error("Error occured while sending the video byte array to ivd-ovd :{}",ex.getMessage());
		}
		return postdetail;
	}


	@Override
	public void softReset(HttpServletRequest request, SoftResetDto softreset) throws HandledException {
	
		
		if (softreset.getBoardType()!=null) {

			requestWrapper.softResetRequest(softreset.getBoardType(),Command.SoftReset);

		} else {

			throw new HandledException("NOT_FOUND", "Please Select board to Reset it");

		}
	}
		
	
	

	@Override
	public StationDetails statusAutoTadd(boolean auto)
			throws HandledException {

		HashMap<String, Object> stationDetailsMap = new HashMap<>();
		List<StationDetails> detailExist = stationDetailsRepository.findAll();
		StationDetails status=null;
		
		if (detailExist.size() != 0) {
			
			  status=detailExist.get(0);
			status.setAutoTadd(auto);
			stationDetailsRepository.save(status);
			//stationDetailsMap = customStationDetails(status);	
		} else {

			throw new HandledException("Station is not created","Please created station first");
		}

		return status;
	}

	
	
	
	
	
	
	
	
}