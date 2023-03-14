/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Status Entry Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import com.innobitsysytems.ipis.model.announcement.TrainStatusEntryAnnouncement;
import org.springframework.stereotype.Repository;

@Repository
public interface TrainStatusAnnoRepository extends JpaRepository<TrainStatusEntryAnnouncement, Long> {

}
