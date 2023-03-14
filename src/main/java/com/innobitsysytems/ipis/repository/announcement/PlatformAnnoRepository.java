/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Platform Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobitsysytems.ipis.model.announcement.PlatformAnnouncement;
import org.springframework.stereotype.Repository;

@Repository
public interface PlatformAnnoRepository extends JpaRepository<PlatformAnnouncement, Long> {

}
