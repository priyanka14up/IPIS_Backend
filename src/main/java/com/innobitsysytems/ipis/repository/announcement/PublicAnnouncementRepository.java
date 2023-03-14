/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Public Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;

@Repository
public interface PublicAnnouncementRepository extends JpaRepository<PublicAnnouncement, Long>{
	
	PublicAnnouncement findByFileLocation (String fileLocation);

}
