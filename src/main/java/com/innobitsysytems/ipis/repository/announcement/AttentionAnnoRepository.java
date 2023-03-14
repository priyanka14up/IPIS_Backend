	/**
	 * Name: Priyanka Upadhyay
	 * Copyright: Innobit Systems, 2021
	 * Purpose: Train No Announcement Repository
	 */
	package com.innobitsysytems.ipis.repository.announcement;

	import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
	import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.AttentionAnnouncements;


	@Repository
	public interface AttentionAnnoRepository extends JpaRepository<AttentionAnnouncements, Long>{

		
	}
	
	
	

