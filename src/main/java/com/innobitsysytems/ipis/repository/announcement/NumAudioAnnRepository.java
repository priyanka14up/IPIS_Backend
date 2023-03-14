/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Number Audio Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobitsysytems.ipis.model.announcement.NumAudioAnnouncement;
import org.springframework.stereotype.Repository;

@Repository
public interface NumAudioAnnRepository extends JpaRepository<NumAudioAnnouncement, Long> {

}
