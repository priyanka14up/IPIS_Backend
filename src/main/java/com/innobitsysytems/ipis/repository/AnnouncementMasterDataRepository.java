/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Master Data Repository
 */
package com.innobitsysytems.ipis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;

@Repository
public interface AnnouncementMasterDataRepository extends JpaRepository<AnnouncementMasterData, Long>{

	


}
