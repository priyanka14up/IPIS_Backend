/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Playlist Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;

@Repository
public interface AnnouncementPlaylistRepository extends JpaRepository<AnnouncementPlaylist, Long>{
	
	

}
