/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Reports Repository
 */
package com.innobitsysytems.ipis.repository.reports;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.dto.AnnouncementDto;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.reports.Announcement;

@Repository
public interface AnnouncementReportsRepository extends JpaRepository<Announcement, Long> {
	
	List<Announcement> findByUser(User user);
	
	@Query("select new  com.innobitsysytems.ipis.dto.AnnouncementDto(a.announcementType,a.announcementMessage,a.announcementTime,a.user.firstname,a.user.lastname,a.createdAt) from Announcement a where a.user.id=?3 and a.createdAt between ?1 and ?2")
	List<AnnouncementDto> findByCreatedAtFilter(
			@Param("DateStart") Date TimeStart,
			@Param("DateEnd") Date TimeEnd,
			@Param("userid") Long userid);
}
