/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Configuration Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.announcement.AnnouncementConfig;

@Repository
public interface AnnouncementConfigRepository extends JpaRepository<AnnouncementConfig, Long> {

}
