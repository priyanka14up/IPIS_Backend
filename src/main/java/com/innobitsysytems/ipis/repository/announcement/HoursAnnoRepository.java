/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Time Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobitsysytems.ipis.model.announcement.HoursAnnouncement;

import org.springframework.stereotype.Repository;

@Repository
public interface HoursAnnoRepository extends JpaRepository<HoursAnnouncement, Long>{

}
