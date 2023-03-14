/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Name Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;

import com.innobitsysytems.ipis.model.announcement.TrainNameAnnouncements;
import org.springframework.stereotype.Repository;


@Repository
public interface TrainNameAnnoRepository extends JpaRepository<TrainNameAnnouncements, Long> {

}
