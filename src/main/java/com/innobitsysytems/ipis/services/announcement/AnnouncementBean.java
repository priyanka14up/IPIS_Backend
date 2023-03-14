/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Public Announcement Bean
 */
package com.innobitsysytems.ipis.services.announcement;

import static org.mockito.ArgumentMatchers.longThat;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.dto.AnnouncementPlaylistDto;
import com.innobitsysytems.ipis.dto.PublicAnnouncementDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.model.reports.Announcement;
import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;
import com.innobitsysytems.ipis.repository.announcement.AnnouncementPlaylistRepository;
import com.innobitsysytems.ipis.repository.announcement.PublicAnnouncementRepository;
import com.innobitsysytems.ipis.utility.CommonUtil;
import com.innobitsysytems.ipis.repository.setup.EnableDisableBoardRepository;
import com.innobitsysytems.ipis.repository.setup.StationDetailsRepository;
import com.innobitsysytems.ipis.utility.AnnouncementUtilty;
import com.innobitsysytems.ipis.utility.CustomUtil;

import javazoom.jl.player.Player;
import javazoom.jl.player.advanced.AdvancedPlayer;

@Service
public class AnnouncementBean implements AnnouncementService {

	@Autowired
	public PublicAnnouncementRepository publicAnnRepo;

	@Autowired
	public AnnouncementPlaylistRepository announcementPlaylistRepo;

	@Autowired
	public CustomUtil customUtil;

	@Autowired
	public CommonUtil commonUtil;

	@Autowired
	public AnnouncementUtilty announcementUtil;

	@Autowired
	public StationDetailsRepository stationDetailRepo;
	
	
	@Autowired
	public EnableDisableBoardRepository enableDisableBoardRepository;

	Date date = new Date();

	List<String> playListFiles = new ArrayList<String>();
	List<String> filename = new ArrayList<String>();
	public int sw = 0;

	// announcemt palyer
	public boolean moveup = false;
	public FileInputStream fileInputStream;
	public AdvancedPlayer player;
	public BufferedInputStream bufferedInputStream;
	public int stopLength;
	public boolean flag = false;
	public boolean previous = false;
	public boolean next = false;
	public boolean move = false;

	String homeDir = System.getProperty("user.home") + "\\";

	public boolean resume = false;
	public int x = 0;
	public int m;

	String folder = "Audio\\Upload\\";
	String pathPrefix = homeDir.concat(folder);

	@Override
	public List<PublicAnnouncement> list() throws HandledException {
		try {
			List<PublicAnnouncement> pAData = publicAnnRepo.findAll();

			return pAData;

		} catch (Exception e) {

			throw new HandledException("NOT_FOUND", "No Data is present in Public Announcement");
		}

	}

	@Override
	public PublicAnnouncementDto getSingleAnnouncement(Long id) throws HandledException {
		PublicAnnouncement pAData = publicAnnRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Public Announcement File not found in Database"));

		return entityToDto(pAData);
	}

	@Override
	public PublicAnnouncementDto create(HttpServletRequest request, PublicAnnouncementDto publicAnn)
			throws HandledException {

		PublicAnnouncement pAData = publicAnnRepo.findByFileLocation(publicAnn.getFileLocation());
		PublicAnnouncement publicAnnouncement = dtoToEntity(publicAnn);

		if (pAData == null) {

			commonUtil.updateActivities("Add File to Public Announcement ",
					String.valueOf(customUtil.getIdFromToken()));
			publicAnnRepo.save(publicAnnouncement);
			return entityToDto(publicAnnouncement);

		} else {

			throw new HandledException("CHECK_PARAMETERS", "File exist in Database.");
		}

	}

	@Override
	public Map<String, Boolean> delete(Long id) throws HandledException {
		this.move = true;
		PublicAnnouncement pAData = publicAnnRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found in Database"));

		publicAnnRepo.delete(pAData);
		Map<String, Boolean> response = new HashMap<>();
		announcementList.removeIf(e -> e.getId().equals(id));

		response.put("deleted", Boolean.TRUE);
		pAData.getFileLocation();

		return response;
	}

	public List<AnnouncementPlaylist> announcementList = new ArrayList<AnnouncementPlaylist>();

	@Override
	public HashMap<String, Object> moveToPlaylist(Long id) throws HandledException {

		this.move = true;
		PublicAnnouncement announcementData = publicAnnRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found in Database"));

		List<PublicAnnouncement> announcementAllData = publicAnnRepo.findAll();

		AnnouncementPlaylist announcementPlaylist = new AnnouncementPlaylist();
		commonUtil.Announcement(announcementData);
		announcementData.setFileAdded(true);
		announcementPlaylist.setFileName(announcementData.getFileName());
		announcementPlaylist.setFileLocation(announcementData.getFileLocation());
		announcementPlaylist.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
		announcementPlaylist.setAnnouncementId(id);
		announcementPlaylistRepo.save(announcementPlaylist);

		String[] str = announcementPlaylist.getFileLocation().split("C:\\\\fakepath\\\\");
		String st = "";
		for (int k = 0; k < str.length; k++) {

			st = str[k].replace("\n", "").replace("\r", "");
			st = pathPrefix.concat(st);

		}
		playListFiles.add(st);
		announcementList.add(announcementPlaylist);

		HashMap<String, Object> annf = new HashMap<>();
		annf.put("announcementList", announcementList);
		annf.put("announcementData", announcementAllData);

		return annf;

	}

	@Override
	public List<AnnouncementPlaylist> getAll() throws HandledException {
		playListFiles.clear();
		List<AnnouncementPlaylist> pAData = announcementPlaylistRepo.findAll();
		announcementList = pAData;

		for (int i = 0; i < pAData.size(); i++) {

			String[] str = pAData.get(i).getFileLocation().split("C:\\\\fakepath\\\\");
			String st = "";
			for (int k = 0; k < str.length; k++) {

				st = str[k].replace("\n", "").replace("\r", "");
				st = pathPrefix.concat(st);

			}
			playListFiles.add(st);
		}

		if (pAData.size() > 0) {

			List<AnnouncementPlaylistDto> pADataDto = playlistEntityToDto(pAData);
			return pAData;

		} else {

			throw new HandledException("NOT_FOUND", "No File is present in Playlist");

		}

	}

	@Override
	public AnnouncementPlaylistDto getSingleFile(Long id) throws HandledException {

		AnnouncementPlaylist pAData = announcementPlaylistRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "Public Announcement File not found on :" + id));

		return playlistEntityToDto(pAData);
	}

	// not in use
	@Override
	public AnnouncementPlaylistDto createPlaylist(HttpServletRequest request,
			AnnouncementPlaylistDto announcementPlaylist) throws HandledException {

		AnnouncementPlaylist publicAnnouncement = playlistDtoToEntity(announcementPlaylist);
		announcementPlaylistRepo.save(publicAnnouncement);
		return playlistEntityToDto(publicAnnouncement);

	}

	@Override
	public HashMap<String, Object> deleteFile(Long id, Long announcementId) throws HandledException {

		this.playListFiles.clear();
		this.moveup = false;
		AnnouncementPlaylist pAData = announcementPlaylistRepo.findById(id)
				.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found on : " + id));

		announcementPlaylistRepo.delete(pAData);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		for (int i = 0; i < announcementList.size(); i++) {
			if (announcementList.get(i).getId().equals(id)) {

				announcementList.remove(i);

			}
		}
		for (int f = 0; f < announcementList.size(); f++) {

			String[] str = announcementList.get(f).getFileLocation().split("C:\\\\fakepath\\\\");
			String st = "";
			for (int k = 0; k < str.length; k++) {

				st = str[k].replace("\n", "").replace("\r", "");
				st = pathPrefix.concat(st);

			}
			playListFiles.add(st);
		}
		List<PublicAnnouncement> announcementAllData = publicAnnRepo.findAll();

		HashMap<String, Object> annf = new HashMap<>();

		try {
			PublicAnnouncement announcementData = publicAnnRepo.findById(announcementId)

					.orElseThrow(() -> new HandledException("NOT_FOUND", "File not found in Database"));
			announcementData.setFileAdded(false);
			publicAnnRepo.save(announcementData);

		} catch (Exception e) {
			try {
				annf.put("announcementList", announcementList);
				annf.put("announcementData", announcementAllData);

//			ann.put("d")
				System.out.println(" inside catch 289");
				return annf;
			} catch (Exception ex) {
				throw new HandledException(ex.toString(), "Exception in deleting the file");
			}

		}

		annf.put("announcementList", announcementList);
		annf.put("announcementData", announcementAllData);
		System.out.println(" inside catch 303");

		return annf;

//		return announcementList;
	}

	@Override
	public List<AnnouncementPlaylist> moveUp(List<Long> ids, Long id) throws HandledException {
		this.moveup = true;
		this.move = true;
		this.announcementList.clear();
		this.playListFiles.clear();

		int flag = 0;
		for (int i = 0; i < ids.size(); i++) {
			flag++;

			if (ids.get(i).equals(id)) {

				break;
			}
		}
		Collections.swap(ids, flag - 1, flag - 2);
		List<AnnouncementPlaylist> announcementPlaylist = new ArrayList<>();

		for (int i = 0; i < ids.size(); i++) {

			AnnouncementPlaylist announcementByid = announcementPlaylistRepo.findById(ids.get(i))
					.orElseThrow(() -> new HandledException("NOT_FOUND", "Announcement Id is not found"));
			String arr = announcementByid.getFileLocation();

			announcementPlaylist.add(announcementByid);

			announcementList.add(announcementByid);

		}
		for (int f = 0; f < announcementPlaylist.size(); f++) {

			String[] str = announcementPlaylist.get(f).getFileLocation().split("C:\\\\fakepath\\\\");
			String st = "";
			for (int k = 0; k < str.length; k++) {

				st = str[k].replace("\n", "").replace("\r", "");
				st = pathPrefix.concat(st);

			}
			playListFiles.add(st);

		}
		System.out.println(playListFiles.toString());

		return announcementPlaylist;
	}

	@Override
	public List<AnnouncementPlaylist> moveDown(List<Long> ids, Long id) throws HandledException {
		this.moveup = true;
		this.move = true;
		this.playListFiles.clear();
		announcementList.clear();

		int flag = 0;
		for (int i = 0; i < ids.size(); i++) {
			flag++;

			if (ids.get(i).equals(id)) {

				break;
			}
		}

		Collections.swap(ids, flag, flag - 1);
		List<AnnouncementPlaylist> announcementPlaylist = new ArrayList<>();
		for (int i = 0; i < ids.size(); i++) {

			AnnouncementPlaylist announcementByid = announcementPlaylistRepo.findById(ids.get(i))
					.orElseThrow(() -> new HandledException("NOT_FOUND", "Announcement Playlist id is not found"));

			announcementPlaylist.add(announcementByid);
			announcementList.add(announcementByid);

		}
		for (int f = 0; f < announcementPlaylist.size(); f++) {

			String[] str = announcementPlaylist.get(f).getFileLocation().split("C:\\\\fakepath\\\\");
			String st = "";
			for (int k = 0; k < str.length; k++) {

				st = str[k].replace("\n", "").replace("\r", "");
				st = pathPrefix.concat(st);

			}
			playListFiles.add(st);
			System.out.println(playListFiles.get(f));
		}

		return announcementPlaylist;
	}

	// Conversion Functions

//Conversion Functions

	public PublicAnnouncementDto entityToDto(PublicAnnouncement publicAnn) {

		PublicAnnouncementDto Dto = new PublicAnnouncementDto();
		Dto.setId(publicAnn.getId());
		Dto.setFileName(publicAnn.getFileName());
		Dto.setFileLocation(publicAnn.getFileLocation());
		Dto.setMessageType(publicAnn.getMessageType());
		Dto.setFileUrl(publicAnn.getFileUrl());

		return Dto;

	}

	public List<PublicAnnouncementDto> entityToDto(List<PublicAnnouncement> publicAnn) {

		return publicAnn.stream().map(x -> entityToDto(x)).collect(Collectors.toList());

	}

	public PublicAnnouncement dtoToEntity(PublicAnnouncementDto Dto) {

		PublicAnnouncement publicAnn = new PublicAnnouncement();
		publicAnn.setId(Dto.getId());
		publicAnn.setFileName(Dto.getFileName());
		publicAnn.setFileLocation(Dto.getFileLocation());
		publicAnn.setMessageType(Dto.getMessageType());
		publicAnn.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
		publicAnn.setFileUrl(Dto.getFileUrl());

		return publicAnn;
	}

	// { Playlist Dto Function }

	public AnnouncementPlaylistDto playlistEntityToDto(AnnouncementPlaylist publicAnn) {

		AnnouncementPlaylistDto Dto = new AnnouncementPlaylistDto();
		Dto.setId(publicAnn.getId());
		Dto.setFileName(publicAnn.getFileName());

		return Dto;

	}

	public List<AnnouncementPlaylistDto> playlistEntityToDto(List<AnnouncementPlaylist> publicAnn) {

		return publicAnn.stream().map(x -> playlistEntityToDto(x)).collect(Collectors.toList());

	}

	public AnnouncementPlaylist playlistDtoToEntity(AnnouncementPlaylistDto Dto) {

		AnnouncementPlaylist publicAnn = new AnnouncementPlaylist();
		publicAnn.setId(Dto.getId());
		publicAnn.setFileName(Dto.getFileName());
		publicAnn.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));

		return publicAnn;
	}
	public Clip clip;
	public AudioInputStream audioInputStream;
	public int stopper;
	Thread thread;
	String waveOrMp3;

	public String musicPlayer(int repeat) throws Exception {

		this.flag = true;
		this.previous = false;
//		this.move=false;

		List<String> moveupdata = new ArrayList<String>();

		List<String> filename = new ArrayList<String>();
		
		
		try {
			
			 List<EnableDisableBoard> enableDiable=enableDisableBoardRepository.findAll();
	            if( enableDiable.get(0).getPa()!=null && enableDiable.get(0).getPa().equals("enable") )
	            {
	             	
			List<AnnouncementPlaylist> announcementData = announcementPlaylistRepo.findAll();

			

			for (int i = 0; i < announcementData.size(); i++) {

				String[] str = announcementData.get(i).getFileLocation().split("C:\\\\fakepath\\\\");
				String st = "";
				for (int k = 0; k < str.length; k++) {

					st = str[k].replace("\n", "").replace("\r", "");
					st = pathPrefix.concat(st);

				}

				System.out.println(announcementList);

			}

			for (int r = 0; r < repeat; r++) {
				for (this.m = this.x; this.m < playListFiles.size(); this.m++) {
					if (previous == true) {
						break;
					}
					String pathsplit[]=playListFiles.get(this.m).split(".");
					System.out.println(playListFiles.get(this.m).endsWith("mp3"));
					System.out.println(pathsplit.length+" lenth of split");
					fileInputStream = new FileInputStream(playListFiles.get(this.m));
					bufferedInputStream = new BufferedInputStream(fileInputStream);
					if (flag == true) {
						if (this.m == playListFiles.size() - 1) {
							this.x = 0;
						}
						if(playListFiles.get(this.m).endsWith("mp3")) {
							this.waveOrMp3="mp3";
						this.player = new AdvancedPlayer(bufferedInputStream);
						player.play();
						}
						else if(playListFiles.get(this.m).endsWith("wav")) {
							this.waveOrMp3="wav";
							audioInputStream = AudioSystem.getAudioInputStream(new File(playListFiles.get(this.m)).getAbsoluteFile());

							// create clip reference
											AudioFormat format = audioInputStream.getFormat();
											long frames = audioInputStream.getFrameLength();
											long durationInSeconds = (long) ((frames + 0.0) / format.getFrameRate());
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
	            else {
	            	throw new HandledException("Public Announcement is Disabled","Disabled");
	            }
			return null;
			
		} catch (Exception e) {
			System.out.println(e);
        	throw new HandledException("Exception in playing ,public announcement is disabled or audion device not connected",e.toString());
		}

	}

	public String pausePlay() throws HandledException {
		this.x = this.m;
		System.out.println(this.x + "  value of x");
		System.out.println(this.m + "  value of m");
		this.flag = false;
		
		if(waveOrMp3.equals("mp3")) {
			if (this.player != null) {
				this.player.close();
			}
			}else if(waveOrMp3.equals("wav")) {
				if(this.clip.isRunning()) {
					this.clip.close();
					this.clip.drain();
				}
			}

		return "stop successfully";

	}

	public String stop() throws HandledException {
		this.x = 0;
		this.flag = false;
		if(waveOrMp3.equals("mp3")) {
		if (this.player != null) {
			this.player.close();
		}
		}else if(waveOrMp3.equals("wav")) {
			if(this.clip.isRunning()) {
				this.clip.close();
				this.clip.drain();
			}
		}
		return "stop playing";

	}

	@Override
	public String nextPlay() throws HandledException {
		// TODO Auto-generated method stub
		if (player != null) {
			player.close();
		}
		if(clip.isRunning()) {
		clip.close();
		}
		return "played next announcement";
	}

	public String uplLoadFile(HttpServletRequest request) throws Exception {
		
		final Part p1 = request.getPart("file");
		String filename = p1.getSubmittedFileName();
		String homeDir = System.getProperty("user.home") + "\\";
		File audiofolder = new File(homeDir+"\\Audio");
		audiofolder.mkdir();
		String path = homeDir + "\\Audio\\Upload\\" + filename;
		String folderPath = homeDir + "\\Audio\\Upload";
		File fol = new File(folderPath);
		fol.mkdir();

		for (Part part : request.getParts()) {
			File file = new File(path);
			part.write(path);
		}

		return filename;

	}

	@Override
	public List<AnnouncementPlaylist> createManualRecord(HttpServletRequest request, AnnouncementPlaylist publicAnn)
			throws HandledException {
		// TODO Auto-generated method stub

		long announcementId = 1;
		publicAnn.setFileLocation("C:\\fakepath\\".concat(publicAnn.getFileName()));
		publicAnn.setAnnouncementId(announcementId);
		publicAnn.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
		String[] str = publicAnn.getFileLocation().split("C:\\\\fakepath\\\\");
		String st = "";
		for (int k = 0; k < str.length; k++) {

			st = str[k].replace("\n", "").replace("\r", "");
			st = pathPrefix.concat(st);

		}
		playListFiles.add(st);

		announcementList.add(publicAnn);
		announcementPlaylistRepo.save(publicAnn);
		return announcementList;

	}
	
	public PublicAnnouncement createManualRecordNew(HttpServletRequest request, PublicAnnouncement publicAnn)
			throws HandledException {
		// TODO Auto-generated method stub

		long announcementId = 1;
		publicAnn.setFileLocation("C:\\fakepath\\".concat(publicAnn.getFileName()));
		publicAnn.setCreatedBy(String.valueOf(customUtil.getIdFromToken()));
		publicAnn.setFileAdded(publicAnn.getFileAdded());
		publicAnn.setFileName(publicAnn.getFileName());
		publicAnn.setMessageType("Recorded Message");
		
		String[] str = publicAnn.getFileLocation().split("C:\\\\fakepath\\\\");
		String st = "";
		for (int k = 0; k < str.length; k++) {

			st = str[k].replace("\n", "").replace("\r", "");
			st = pathPrefix.concat(st);

		}
		publicAnnRepo.save(publicAnn);
		return publicAnn;

	}


}