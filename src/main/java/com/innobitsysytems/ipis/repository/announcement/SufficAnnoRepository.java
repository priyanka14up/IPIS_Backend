/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Suffic Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.announcement.SuffixAnnouncement;

@Repository
public interface SufficAnnoRepository extends JpaRepository<SuffixAnnouncement, Long>{

}
