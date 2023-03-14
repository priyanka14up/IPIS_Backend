/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Bean
 */
package com.innobitsysytems.ipis.services.tvdisplay;

import static org.mockito.ArgumentMatchers.intThat;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;

import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.dto.DisplayMediaDto;
import com.innobitsysytems.ipis.dto.MediaQueueDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.CoachDetails;
import com.innobitsysytems.ipis.model.setup.DefaultMessages;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.model.tvdisplay.AdvertismentPlaylist;
import com.innobitsysytems.ipis.model.tvdisplay.Display;
import com.innobitsysytems.ipis.model.tvdisplay.DisplayMedia;
import com.innobitsysytems.ipis.model.tvdisplay.MediaQueue;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.MessageRepository;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.setup.DefaultMessagesRepository;
import com.innobitsysytems.ipis.repository.setup.StationDetailsRepository;
import com.innobitsysytems.ipis.repository.tvdisplay.DisplayMediaRepository;
import com.innobitsysytems.ipis.repository.tvdisplay.DisplayRepository;
import com.innobitsysytems.ipis.repository.tvdisplay.MediaQueueRepository;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.services.taurus_sdk.TaurusSdkWrapper;
import com.innobitsysytems.ipis.utility.CommonUtil;
import com.innobitsysytems.ipis.utility.CustomUtil;

import io.micrometer.core.instrument.util.IOUtils;

@Service
public class DisplayBean implements DisplayService {

	@Autowired
	public DisplayRepository displayRepository;

	@Autowired
	public OnlineTrainRepository onlineTrainRepository;

	@Autowired
	public StationDetailsRepository stationDetailsRepository;

//	@Autowired
//	public TvDisplayRepository tvDisplayRepository;

	@Autowired
	public DefaultMessagesRepository defaultMessagesRepository;

	@Autowired
	public MessageRepository messageRepository;

	@Autowired
	public DisplayMediaRepository displayMediaRepo;

	@Autowired
	public MediaQueueRepository mediaQueueRepo;

	@Autowired
	public CustomUtil customUtil;

	@Autowired
	public TaurusSdkWrapper swrapper;

	@Autowired
	public DeviceRepository deviceRepository;

	@Autowired
	public CommonUtil commonUtil;

	@Override
	public List<DisplayDto> findAllDisplay() throws HandledException {

		List<Display> displayDeviceData = displayRepository.findAll();

		return entityToDto(displayDeviceData);

	}

	@Override
	public DisplayDto getSingleDisplay(String displayType) throws HandledException {

		Display display = displayRepository.findByDisplayType(displayType);

		if (display != null) {

			return entityToDto(display);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Entry for " + displayType + " doesn't exist in DataBase");
		}

	}

	@Override
	public DisplayDto postDisplay(HttpServletRequest request, DisplayDto displayDto) throws HandledException {

		Display display = dtoToEntity(displayDto);
		DisplayDto dtoData = new DisplayDto();
		Display displayData = displayRepository.findByDisplayType(displayDto.getDisplayType());

		if (displayData == null) {

			display.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Display Settings in TV Display ", String.valueOf(customUtil.getIdFromToken()));
			displayRepository.save(display);
			dtoData = entityToDto(display);

		} else {

			displayData.setEnableDisplay(display.getEnableDisplay());
			displayData.setShowMessage(display.getShowMessage());
			displayData.setShowMedia(display.getShowMedia());
			displayData.setShowCoach(display.getShowCoach());
			displayData.setDisplayTimeout(display.getDisplayTimeout());
			displayData.setGridRowHeight(display.getGridRowHeight());
			displayData.setTrainNoWidth(display.getTrainNoWidth());
			displayData.setExpectedTimeWidth(display.getExpectedTimeWidth());
			displayData.setArrivalDepartureWidth(display.getArrivalDepartureWidth());
			displayData.setPlatformWidth(display.getPlatformWidth());
			displayData.setGridPageTime(display.getGridPageTime());
			displayData.setMessageScrollSpeed(display.getMessageScrollSpeed());
			displayData.setMediaStartIntervalTime(display.getMediaStartIntervalTime());
			displayData.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Updated Display Settings in TV Display ",
					String.valueOf(customUtil.getIdFromToken()));
			displayRepository.save(displayData);
			dtoData = entityToDto(displayData);

		}

		return dtoData;

	}

	// Override Functions of DisplayMedia

	@Override
	public List<DisplayMediaDto> findAllMedia(String displayType) throws HandledException {

		List<DisplayMedia> displayData = displayMediaRepo.findByDisplayType(displayType);

		if (displayData.size() != 0) {

			List<DisplayMediaDto> displayMediaData = mediaEntityToDto(displayData);
			Collections.reverse(displayMediaData);
			return displayMediaData;

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Media for this device doesn't exist.");
		}

	}

	@Override
	public DisplayMediaDto getSingleFile(Long id) throws HandledException {

		DisplayMedia displayMedia = displayMediaRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File media not found on :" + id));

		return mediaEntityToDto(displayMedia);
	}

	@Override
	public DisplayMediaDto postFile(String displayType, HttpServletRequest request, DisplayMediaDto displayMediaDto)
			throws HandledException {

		DisplayMedia display = mediaDtoToEntity(displayMediaDto);
		AdvertismentPlaylist adPlay = new AdvertismentPlaylist();
		DisplayMedia mediaData = displayMediaRepo.getFileByMediaLocation(displayType,
				displayMediaDto.getMediaLocation());

		if (mediaData == null) {

			String[] emptyArray = {};
			display.setDisplayType(displayType);
			display.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			adPlay.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			adPlay.setQueueSequence(emptyArray);
			display.setAdvPlaylist(adPlay);
			commonUtil.updateActivities("File added in TV Display ", String.valueOf(customUtil.getIdFromToken()));
			displayMediaRepo.save(display);
			return mediaEntityToDto(display);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "File Location exist in Database for " + displayType);
		}
	}

	@Override
	public Map<String, Boolean> deleteFile(Long id) throws HandledException {

		DisplayMedia displayMedia = displayMediaRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found on : " + id));

		displayMediaRepo.delete(displayMedia);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;

	}

//	@Override
//	public HashMap<String, Object> moveToPlaylist(Long id) throws HandledException {
//
//		this.move = true;
//		PublicAnnouncement announcementData = publicAnnRepo.findById(id)
//				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found in Database"));
//
//		List<PublicAnnouncement> announcementAllData = publicAnnRepo.findAll();
//
//		AnnouncementPlaylist announcementPlaylist = new AnnouncementPlaylist();
//		commonUtil.Announcement(announcementData);
//		announcementData.setFileAdded(true);
////		announcementPlaylist.setFileAdded(announcementData.getFileAdded());
//		announcementPlaylist.setFileName(announcementData.getFileName());
//		announcementPlaylist.setFileLocation(announcementData.getFileLocation());
//		announcementPlaylist.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
//		announcementPlaylist.setAnnouncementId(id);
//		announcementPlaylistRepo.save(announcementPlaylist);
//
//		String[] str = announcementPlaylist.getFileLocation().split("C:\\\\fakepath\\\\");
//		String st = "";
//		for (int k = 0; k < str.length; k++) {
//
//			st = str[k].replace("\n", "").replace("\r", "");
//			st = pathPrefix.concat(st);
//
//		}
//		playListFiles.add(st);
//		announcementList.add(announcementPlaylist);
//
//		HashMap<String, Object> annf = new HashMap<>();
//		annf.put("announcementList", announcementList);
//		annf.put("announcementData", announcementAllData);
//
//		return annf;
//
//	}
//

	// To post in media queue
	List<String> playListFiles = new ArrayList<String>();
	public List<MediaQueue> mediaqueList = new ArrayList<MediaQueue>();

	@Override
	public HashMap<String, Object> moveToPlaylist(Long id) throws HandledException, Exception {

		DisplayMedia displayMediaData = displayMediaRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found in Database"));
		List<DisplayMedia> diaplayMediaAllData = displayMediaRepo.findByDisplayType(displayMediaData.getDisplayType());

		MediaQueue mediaQueue = new MediaQueue();
		MediaQueue mediaData = mediaQueueRepo.getMediaQueueByMediaLocation(displayMediaData.getDisplayType(),
				displayMediaData.getMediaLocation());

		if (mediaData == null) {
			displayMediaData.setFileAdded(true);
			mediaQueue.setDisplayType(displayMediaData.getDisplayType());
			mediaQueue.setMediaLocation(displayMediaData.getMediaLocation());
			mediaQueue.setMediaName(displayMediaData.getMediaName());
			mediaQueue.setIsPlaying(false);
			mediaQueue.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			commonUtil.updateActivities("Files are moved to Media Queue in TV Display ",
					String.valueOf(customUtil.getIdFromToken()));
			mediaQueueRepo.save(mediaQueue);

			String fileLocation = mediaQueue.getMediaLocation();
			String[] str = fileLocation.split("C:\\\\fakepath\\\\");
			System.out.println(str[1]);
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			String ipstr = ip.toString();
			String ipstrarr[] = ipstr.split("/");
			playListFiles.add("http://" + ipstrarr[1] + "/" + str[1]);
			mediaqueList.add(mediaData);
			HashMap<String, Object> annf = new HashMap<>();
			annf.put("MediaPlaylist", mediaqueList);
			annf.put("allMediaData", diaplayMediaAllData);

			return annf;

		} else {

			throw new HandledException("CHECK_PARAMETERS",
					"File Location is already present in Media Queue for " + displayMediaData.getDisplayType());
		}

	}

	// Override Functions of QueueMedia

	@Override
	public List<MediaQueueDto> findAll(String displayType) throws HandledException {

		List<MediaQueue> displayData = mediaQueueRepo.findByDisplayType(displayType);

		if (displayData.size() != 0) {

			List<MediaQueueDto> mediaData = queueEntityToDto(displayData);
			Collections.reverse(mediaData);
			return mediaData;

		} else {

			throw new HandledException("CHECK_PARAMETERS", "Media for this device doesn't exist.");
		}

	}

	@Override
	public MediaQueueDto getSingleMedia(Long id) throws HandledException {

		MediaQueue mediaQueue = mediaQueueRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File media not found on :" + id));

		return queueEntityToDto(mediaQueue);
	}

	// not to be used
	@Override
	public MediaQueueDto postMedia(String displayType, HttpServletRequest request, MediaQueueDto mediaQueueDto)
			throws HandledException {

		MediaQueue mediaQueue = queueDtoToEntity(mediaQueueDto);
		MediaQueue mediaData = mediaQueueRepo.getMediaQueueByMediaLocation(displayType,
				mediaQueueDto.getMediaLocation());

		if (mediaData == null) {

			mediaQueue.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
			mediaQueue.setDisplayType(displayType);
			mediaQueueRepo.save(mediaQueue);

			return queueEntityToDto(mediaQueue);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "File Location exist in Media Queue " + displayType);
		}
	}

	@Override
	public MediaQueueDto putMedia(Long id, MediaQueueDto mediaQueueDto) throws HandledException {

		MediaQueue mediaQueue = mediaQueueRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File media not found on :" + id));

		mediaQueue.setUpdatedBy(String.valueOf(customUtil.getIdFromToken()));
		mediaQueue.setIsPlaying(mediaQueueDto.getIsPlaying());
		mediaQueueRepo.save(mediaQueue);
		return queueEntityToDto(mediaQueue);

	}

	@Override
	public Map<String, Boolean> deleteMedia(Long id) throws HandledException {

		MediaQueue mediaQueue = mediaQueueRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found on : " + id));

		mediaQueueRepo.delete(mediaQueue);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}

	public boolean moveup = false;

	@Override
	public List<MediaQueue> moveUp(List<Long> ids, Long id) throws HandledException, Exception {
		this.moveup = true;
//		this.move = true;
		this.mediaqueList.clear();
		this.playListFiles.clear();

		int flag = 0;

		for (int i = 0; i < ids.size(); i++) {

			flag++;

			if (ids.get(i).equals(id)) {

				break;
			}

		}

		Collections.swap(ids, flag - 1, flag - 2);

		List<MediaQueue> listMediaQueue = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {

			MediaQueue mediaByid = mediaQueueRepo.findById(ids.get(i))
					.orElseThrow(() -> new HandledException("NOT_FOUND", "Announcement Id is not found"));
			listMediaQueue.add(mediaByid);
			mediaqueList.add(mediaByid);

		}
		for (int f = 0; f < listMediaQueue.size(); f++) {
			String fileLocation = listMediaQueue.get(f).getMediaLocation();
			String[] str = fileLocation.split("C:\\\\fakepath\\\\");
			System.out.println(str[1]);
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			String ipstr = ip.toString();
			String ipstrarr[] = ipstr.split("/");
			playListFiles.add("http://" + ipstrarr[1] + "/" + str[1]);

		}
		for (int x = 0; x < playListFiles.size(); x++)
			System.out.println(playListFiles.get(x));
		return listMediaQueue;

	}

	@Override
	public List<MediaQueue> moveDown(List<Long> ids, Long id) throws HandledException, Exception {
		this.moveup = true;
//		this.move = true;
		this.mediaqueList.clear();
		this.playListFiles.clear();

		int flag = 0;

		for (int i = 0; i < ids.size(); i++) {

			flag++;

			if (ids.get(i).equals(id)) {

				break;
			}

		}

		Collections.swap(ids, flag, flag - 1);

		List<MediaQueue> listMediaQueue = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {

			Optional<MediaQueue> mediaByid = mediaQueueRepo.findById(ids.get(i));
			listMediaQueue.add(mediaByid.get());
			mediaqueList.add(mediaByid.get());

		}
		for (int f = 0; f < listMediaQueue.size(); f++) {
			String fileLocation = listMediaQueue.get(f).getMediaLocation();
			String[] str = fileLocation.split("C:\\\\fakepath\\\\");
			System.out.println(str[1]);
			ip = InetAddress.getLocalHost();
			hostname = ip.getHostName();
			String ipstr = ip.toString();
			String ipstrarr[] = ipstr.split("/");
			playListFiles.add("http://" + ipstrarr[1] + "/" + str[1]);

		}

		return listMediaQueue;

	}

	// To-Do
	@Override
	public void displaySelectedData(String displayType) throws HandledException {

		Display displayValue = displayRepository.findByDisplayType(displayType);

		if (displayValue != null) {

			if (displayValue.getShowMedia()) {

				List<MediaQueue> showMediaData = mediaQueueRepo.getMediaQueueByIsPlaying(displayType, true);

				if (showMediaData.size() > 0) {

					List<Device> devicepare = deviceRepository.findAll();

					try {

						swrapper.start(devicepare);

					} catch (Exception e) {

						throw new HandledException("initialization ", "initialization Exception", e);

					}

					try {

						swrapper.startTransfer();

					} catch (Exception e) {

						throw new HandledException("initialization ", "start publish Exception", e);

					}

					try {

						swrapper.startPlay();

					} catch (Exception e) {

						throw new HandledException("initialization ", "start play exception", e);

					}

					try {

						swrapper.downLoadScreenshot();

					} catch (Exception e) {

						throw new HandledException("initialization ", "downLoadScreenshot exception", e);

					}

				} else {

					throw new HandledException("NOT_FOUND", "No Media is selected");
				}

			}

			if (displayValue.getShowCoach()) {

				List<MediaQueue> showCoachData = mediaQueueRepo.getMediaQueueByIsPlaying(displayType, true);

			}

			if (displayValue.getShowMessage()) {

				List<MediaQueue> showMessageData = mediaQueueRepo.getMediaQueueByIsPlaying(displayType, true);

			}

		} else {

			throw new HandledException("CHECK_PARAMETERS",
					"Display Control Setting for " + displayType + " doesn't found");
		}

		// sdk call

	}

// Conversion Functions

	public DisplayDto entityToDto(Display display) {

		DisplayDto displayDto = new DisplayDto();
		displayDto.setId(display.getId());
		displayDto.setDisplayType(display.getDisplayType());
		displayDto.setEnableDisplay(display.getEnableDisplay());
		displayDto.setEnableDisplay(display.getShowMessage());
		displayDto.setShowMedia(display.getShowMedia());
		displayDto.setShowCoach(display.getShowCoach());
		displayDto.setShowMessage(display.getShowMessage());
		displayDto.setDisplayTimeout(display.getDisplayTimeout());
		displayDto.setGridRowHeight(display.getGridRowHeight());
		displayDto.setTrainNoWidth(display.getTrainNoWidth());
		displayDto.setExpectedTimeWidth(display.getExpectedTimeWidth());
		displayDto.setArrivalDepartureWidth(display.getArrivalDepartureWidth());
		displayDto.setPlatformWidth(display.getPlatformWidth());
		displayDto.setGridPageTime(display.getGridPageTime());
		displayDto.setMessageScrollSpeed(display.getMessageScrollSpeed());
		displayDto.setMediaStartIntervalTime(display.getMediaStartIntervalTime());

		return displayDto;

	}

	public List<DisplayDto> entityToDto(List<Display> display) {

		return display.stream().map(x -> entityToDto(x)).collect(Collectors.toList());

	}

	public Display dtoToEntity(DisplayDto displayDto) {

		Display display = new Display();
		display.setId(displayDto.getId());
		display.setDisplayType(displayDto.getDisplayType());
		display.setEnableDisplay(displayDto.getEnableDisplay());
		display.setShowMessage(displayDto.getShowMessage());
		display.setShowMedia(displayDto.getShowMedia());
		display.setShowCoach(displayDto.getShowCoach());
		display.setDisplayTimeout(displayDto.getDisplayTimeout());
		display.setGridRowHeight(displayDto.getGridRowHeight());
		display.setTrainNoWidth(displayDto.getTrainNoWidth());
		display.setExpectedTimeWidth(displayDto.getExpectedTimeWidth());
		display.setArrivalDepartureWidth(displayDto.getArrivalDepartureWidth());
		display.setPlatformWidth(displayDto.getPlatformWidth());
		display.setGridPageTime(displayDto.getGridPageTime());
		display.setMessageScrollSpeed(displayDto.getMessageScrollSpeed());
		display.setMediaStartIntervalTime(displayDto.getMediaStartIntervalTime());

		return display;
	}

//CustomResponse DisplayMedia

	public DisplayMediaDto mediaEntityToDto(DisplayMedia displayMedia) {

		DisplayMediaDto displayMediaDto = new DisplayMediaDto();
		displayMediaDto.setId(displayMedia.getId());
		displayMediaDto.setMediaName(displayMedia.getMediaName());
		displayMediaDto.setMediaLocation(displayMedia.getMediaLocation()); //// to set display Type if drop down exist//

		return displayMediaDto;

	}

	public List<DisplayMediaDto> mediaEntityToDto(List<DisplayMedia> displayMedia) {

		return displayMedia.stream().map(x -> mediaEntityToDto(x)).collect(Collectors.toList());

	}

	public DisplayMedia mediaDtoToEntity(DisplayMediaDto displayMediaDto) {

		DisplayMedia displayMedia = new DisplayMedia();
		displayMedia.setId(displayMediaDto.getId());
		displayMedia.setMediaName(displayMediaDto.getMediaName());
		displayMedia.setMediaLocation(displayMediaDto.getMediaLocation());
		displayMedia.setAdvPlaylist(null);
		displayMedia.setDisplayType(null);
		displayMedia.setCreatedBy(null);

		return displayMedia;
	}

//CustomResponse MediaQueue

	public MediaQueueDto queueEntityToDto(MediaQueue mediaQueue) {

		MediaQueueDto mediaDto = new MediaQueueDto();
		mediaDto.setId(mediaQueue.getId());
		mediaDto.setMediaName(mediaQueue.getMediaName());
		mediaDto.setMediaLocation(mediaQueue.getMediaLocation());
		mediaDto.setIsPlaying(mediaQueue.getIsPlaying());

		return mediaDto;

	}

	public List<MediaQueueDto> queueEntityToDto(List<MediaQueue> mediaQueue) {

		return mediaQueue.stream().map(x -> queueEntityToDto(x)).collect(Collectors.toList());

	}

	public MediaQueue queueDtoToEntity(MediaQueueDto mediaQueueDto) {

		MediaQueue mediaQueue = new MediaQueue();
		mediaQueue.setId(mediaQueueDto.getId());
		mediaQueue.setMediaName(mediaQueueDto.getMediaName());
		mediaQueue.setMediaLocation(mediaQueueDto.getMediaLocation());
		mediaQueue.setIsPlaying(mediaQueueDto.getIsPlaying());
		mediaQueue.setDisplayType(null);

		return mediaQueue;
	}

	@Override
	public String uploadFilemedia(StandardMultipartHttpServletRequest request) throws HandledException {
		// TODO Auto-generated method stub

		try {
			final Part p1 = request.getPart("file");
			String filename = p1.getSubmittedFileName();
			String homeDir = System.getProperty("user.home") + "\\";
			File audiofolder = new File(homeDir + "\\TvMedia");
			audiofolder.mkdir();
			String path = homeDir + "\\TvMedia\\" + filename;

			for (Part part : request.getParts()) {
				File file = new File(path);
				part.write(path);
			}
			return filename;

		} catch (Exception e) {
			throw new HandledException("tv display error", e.getMessage());
		}

	}

	@Override
	public HashMap<String, Object> getTvDisplay() throws HandledException {
		// TODO Auto-generated method stub
		HashMap<String, Object> annf = new HashMap<>();
		List<Object> onlineList = new ArrayList<Object>();
	 	List<DefaultMessages>defaultMsg = defaultMessagesRepository.findAll();

//		List<OnlineTrain> onlineList = new 
		List<OnlineTrain> onlineTrains = onlineTrainRepository.findByTD(true);
		List<StationDetails> staionName = stationDetailsRepository.findAll();
		String tvDisplayMessage = defaultMsg.get(0).getTvDisplayDefaultMessage();
		List<Message> msg = messageRepository.findByDisplayBoard("tvDisplay");
		Boolean showMessage = displayRepository.findByDisplayType("tvDisplay").getShowMessage();
		Boolean showCoaches = displayRepository.findByDisplayType("tvDisplay").getShowCoach();
		Boolean showMedia = displayRepository.findByDisplayType("tvDisplay").getShowMedia();
		
		//new added
		int gridRowHeight=displayRepository.findByDisplayType("tvDisplay").getGridRowHeight();
		int trainNumberWidth=displayRepository.findByDisplayType("tvDisplay").getTrainNoWidth();
		int expectedTimeWidth=displayRepository.findByDisplayType("tvDisplay").getExpectedTimeWidth();
		int arrivalDepartWidth=displayRepository.findByDisplayType("tvDisplay").getArrivalDepartureWidth();
		int platformWidth=displayRepository.findByDisplayType("tvDisplay").getPlatformWidth();
		int gridPageTime=displayRepository.findByDisplayType("tvDisplay").getGridPageTime();
		int msgScrollSpeed=displayRepository.findByDisplayType("tvDisplay").getMessageScrollSpeed();
		int mediaStartIntervalTime=displayRepository.findByDisplayType("tvDisplay").getMediaStartIntervalTime();
		
		annf.put("stationName", staionName.get(0).getStationName());
		annf.put("data", onlineTrains);

		try {
			annf.put("message", tvDisplayMessage);
		} catch (Exception e) {
			annf.put("message", null);
		}
		annf.put("showMessage", showMessage);
		annf.put("showCoach", showCoaches);
		annf.put("showMedia", showMedia);
		annf.put("coaches", onlineTrains.get(0).getCoaches());
		annf.put("CoachesTrainNo", onlineTrains.get(0).getTrainNumber());
		annf.put("CochesTrainName", onlineTrains.get(0).getTrainName());
		
		//new lines
		annf.put("gridRowHeight",gridRowHeight);
		annf.put("trainNumberWidth",trainNumberWidth);
		annf.put("expectedTimeWidth",expectedTimeWidth);
		annf.put("arrivalDepartWidth",arrivalDepartWidth);
		annf.put("platformWidth",platformWidth);
		annf.put("gridPageTime",gridPageTime);
		annf.put("msgScrollSpeed",msgScrollSpeed);
		annf.put("mediaStartIntervalTime",mediaStartIntervalTime);
		
		return annf;
	}

	public HashMap<String, Object> getCoches() throws HandledException {
		List<OnlineTrain> onlineTrains = onlineTrainRepository.findByTD(true);

		for (int i = 0; i < onlineTrains.size(); i++) {
//			if(onlineTrains.get(i).getSTA())
		}
		return null;

	}

	private static void readFileToBytes(String filePath) throws Exception {

		File file = new File(filePath);
		byte[] bytes = new byte[(int) file.length()];

		FileInputStream fis = null;
		try {

			fis = new FileInputStream(file);

			// read file into bytes[]
			fis.read(bytes);

		} finally {
			if (fis != null) {
				fis.close();
			}
		}

	}

	InetAddress ip;
	String hostname;

	@Override
	public List<String> getAllFileMedia() throws HandledException, Exception {
		// TODO Auto-generated method stub
//		playListFiles.clear();
		List<String> filnameList = new ArrayList<>();
		List<MediaQueue> mq = mediaQueueRepo.findByDisplayType("tvDisplay");
		for (int i = 0; i < mq.size(); i++) {
			boolean isplay = mq.get(i).getIsPlaying();
			if (isplay == true) {
				String fileLocation = mq.get(i).getMediaLocation();
				String[] str = fileLocation.split("C:\\\\fakepath\\\\");
//				System.out.println(str[1]);
				ip = InetAddress.getLocalHost();
				hostname = ip.getHostName();
				String ipstr = ip.toString();
				String ipstrarr[] = ipstr.split("/");
				filnameList.add("http://" + ipstrarr[1] + "/" + str[1]);
//				playListFiles.add("http://" + ipstrarr[1] + "/" + str[1]);
//				return playListFiles;

			}
		}

		return filnameList;
	}

	@Override
	public List<String> exportDb(String filename) throws HandledException, Exception {
		// TODO Auto-generated method stub
		try {
			String homeDir = System.getProperty("user.home") + "\\";
			System.out.println(homeDir);
			File audiofolder = new File(homeDir + "\\ipisdb_backup");
			audiofolder.mkdir();
			String path = homeDir + "ipisdb_backup\\" + filename + ".sql";
			System.out.println(path);
			// PostgreSQL variables
			String host = "localhost";
			String user = "postgres";
			String dbase = "ipis_db";
			String command = "pg_dump "+ "-v "+ "-w "+ "--if-exists "+ "--clean "+ "-h "+ host+" "+ "-f "+ path+" "+ "-U "+ user+" "+dbase;
			System.out.println(command);
			Process process= Runtime.getRuntime().exec(command.toString());
			BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorStreamOutput = new StringBuilder();
            String errorline;
            while ((errorline = errorStreamReader.readLine()) != null) {
                errorStreamOutput.append(errorline + "\n");
            }

            BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder inputStreamOutput = new StringBuilder();
            String inputline;
            while ((inputline = inputStreamReader.readLine()) != null) {
                inputStreamOutput.append(inputline + "\n");
            }

            int code = process.waitFor();
            if(code != 0){
                System.out.println("SOME ERROR OCCURRED IN UPLOADING FILE TO GCP COMMAND OR WRITING IN LOG FILE");
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return null;
	}

	@Override
	public void importDb(String filename) throws HandledException, Exception {
		// TODO Auto-generated method stub
		try {
			System.out.println("inside import db");
			String homeDir = System.getProperty("user.home") + "\\";
			System.out.println(homeDir);
			File audiofolder = new File(homeDir + "\\ipisdb_import");
			audiofolder.mkdir();
			System.out.println(filename);
			String path = homeDir + "ipisdb_import\\" + filename;
			System.out.println(path + "jkhkjhjkoioioi");
			Runtime r = Runtime.getRuntime();

			// PostgreSQL variables
			String host = "localhost";
			String user = "postgres";
			String dbase = "ipis_db";
//		    String password = "root";

			String command = "psql "+ "-U "+ user+" "+ "-d "+ dbase +" "+ "-1 "+ "-f "+ path;
			System.out.println(command);
			Process process= Runtime.getRuntime().exec(command.toString());

			BufferedReader errorStreamReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            StringBuilder errorStreamOutput = new StringBuilder();
            String errorline;
            while ((errorline = errorStreamReader.readLine()) != null) {
                errorStreamOutput.append(errorline + "\n");
            }
            BufferedReader inputStreamReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            StringBuilder inputStreamOutput = new StringBuilder();
            String inputline;
            while ((inputline = inputStreamReader.readLine()) != null) {
                inputStreamOutput.append(inputline + "\n");
            }
            int code = process.waitFor();
            if(code != 0){
                System.out.println("SOME ERROR OCCURRED IN UPLOADING FILE TO GCP COMMAND OR WRITING IN LOG FILE");
            }
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public String uploadDb(StandardMultipartHttpServletRequest request) throws HandledException, Exception {
		// TODO Auto-generated method stub
		try {
			final Part p1 = request.getPart("file");
			String filename = p1.getSubmittedFileName();
			System.out.println(filename);
			String homeDir = System.getProperty("user.home") + "\\";

			File audiofolder = new File(homeDir + "\\ipisdb_import");
			audiofolder.mkdir();
			audiofolder.mkdir();
			String path = homeDir + "\\ipisdb_import\\" + filename;

			for (Part part : request.getParts()) {
				File file = new File(path);
				part.write(path);
			}
			return filename;

		} catch (Exception e) {
			throw new HandledException("tv display error", e.getMessage());
		}
	}


	@Autowired
	private NotificationService notificationService;

	public String autoTrainExpectedTime() throws Exception {

		int time = 10;
		List<OnlineTrain> train = onlineTrainRepository.findByTD(true);
		SimpleDateFormat formatter = new SimpleDateFormat("dd-M-yyyy hh:mm", Locale.ENGLISH);
		//
		SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
//		System.out.println(train.size() + " train size");

		for (int i = 0; i < train.size(); i++) {

			String eta = train.get(i).getETA();

			Calendar date = Calendar.getInstance();
			Date currentTime = date.getTime();
			String st = Integer.toString(currentTime.getDate());
			String st1 = Integer.toString(currentTime.getMonth() + 1);
			String st2 = Integer.toString(date.getWeekYear());

			String stastr = st + "-" + st1 + "-" + st2;
			Date satTime = formatter.parse(stastr + " " + eta);
			String satarr[] = eta.split(":");
			long timeInSecs = date.getTimeInMillis();
			//System.out.println(timeInSecs + " time in ssec");
			Date afterAdding10Mins = new Date(timeInSecs + (time * 60 * 1000));
			List<String> trainlist = new ArrayList<>();

			// ETD
			String etd = train.get(i).getETD();
			int largestetd = 0;
			String[] lstr = etd.split(":");
			int hr = Integer.parseInt(lstr[0]) * 60;
			int min = 00;
			if (lstr.length > 1) {
				min = Integer.parseInt(lstr[1]);
			}
			int finaletd = hr + min;
			if (largestetd < finaletd) {
				largestetd = finaletd;
			}
			int hours = largestetd / 60; // since both are ints, you get an int.
			int minutes = largestetd % 60;
			String lgHr = String.valueOf(hours);
			String lgmin = String.valueOf(minutes);
			String maxEtd = lgHr + ":" + lgmin;

			Calendar date1 = Calendar.getInstance();
			Date currentTime1 = date.getTime();
			String ed = Integer.toString(currentTime1.getDate());
			String ed1 = Integer.toString(currentTime1.getMonth() + 1);
			String ed2 = Integer.toString(date.getWeekYear());

			String edtstr = ed + "-" + ed1 + "-" + ed2;
			Date edtTime = formatter.parse(edtstr + " " + maxEtd);

			String etdarr[] = maxEtd.split(":");

			if (etdarr[0].equals("12")) {
				edtTime.setHours(12);
			}
			long timeInSecs1 = edtTime.getTime();
			Date afterAdding10MinsETD = new Date(timeInSecs1 + (time * 60 * 1000));
			Date afterAdding11MinsETD = new Date((timeInSecs1) + (10 * 60 * 1000));
			afterAdding11MinsETD.setSeconds(5);

			if (satTime.after(currentTime) && satTime.before(afterAdding10Mins)) {
				notificationService.tvDisplayNotification("tvData");
				trainlist.add(train.get(i).getETD());

			}

			if (currentTime.equals(satTime)) {
				if (currentTime.after(satTime) && currentTime.before(afterAdding10MinsETD)) {
					notificationService.tvDisplayNotification("tvData");
				}
			}
			if (currentTime.after(afterAdding10MinsETD) && currentTime.before(afterAdding11MinsETD)) {
				notificationService.tvDisplayNotification("tvMedia");

			}

		}
		return "change";
	}

}
