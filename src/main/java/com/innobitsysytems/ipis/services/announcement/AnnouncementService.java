/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Public Announcement Service
 */

package com.innobitsysytems.ipis.services.announcement;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.innobitsysytems.ipis.dto.AnnouncementPlaylistDto;
import com.innobitsysytems.ipis.dto.PublicAnnouncementDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;

public interface AnnouncementService {
	
	public List<PublicAnnouncement> list()throws HandledException;
	
	public PublicAnnouncementDto getSingleAnnouncement(Long id) throws HandledException;
	
	public PublicAnnouncementDto create(HttpServletRequest request, PublicAnnouncementDto publicAnn)throws HandledException;
	
	public Map<String, Boolean> delete(Long id)throws HandledException;
	
	public HashMap<String, Object> moveToPlaylist(Long id) throws HandledException;
	
	//Public Announcement Playlist Functions Declaration
	
	public List<AnnouncementPlaylist> getAll()throws HandledException;
	
	public AnnouncementPlaylistDto getSingleFile(Long id) throws HandledException;
	
	public AnnouncementPlaylistDto createPlaylist(HttpServletRequest request, AnnouncementPlaylistDto announcementPlaylist)throws HandledException;
	
	public HashMap<String, Object> deleteFile(Long id,Long announcementId)throws HandledException;
	
	public List<AnnouncementPlaylist> moveUp(List<Long> ids, Long id) throws HandledException;
	
	public List<AnnouncementPlaylist> moveDown(List<Long> ids, Long id) throws HandledException;
	public String musicPlayer(int repeat) throws Exception;
	public String nextPlay()throws HandledException;;
	public String stop() throws HandledException;
	public String pausePlay() throws HandledException;

	
	public String uplLoadFile(HttpServletRequest request)throws Exception;
	
	public List<AnnouncementPlaylist> createManualRecord(HttpServletRequest request, AnnouncementPlaylist publicAnn) throws HandledException;

	public PublicAnnouncement createManualRecordNew(HttpServletRequest request, PublicAnnouncement publicAnn)
			throws HandledException;
}
