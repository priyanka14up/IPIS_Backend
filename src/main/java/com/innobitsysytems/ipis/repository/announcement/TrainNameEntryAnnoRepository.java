
/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Name Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import com.innobitsysytems.ipis.model.announcement.TrainNameEntryAnnouncement;

import org.springframework.stereotype.Repository;


@Repository
public interface TrainNameEntryAnnoRepository extends JpaRepository<TrainNameEntryAnnouncement, Long> {

}
