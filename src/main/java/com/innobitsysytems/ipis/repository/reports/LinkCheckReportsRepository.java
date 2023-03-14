/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2021
 * Purpose: Link Check Reports Repository
 */
package com.innobitsysytems.ipis.repository.reports;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.innobitsysytems.ipis.dto.LinkCheckDto;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.reports.LinkCheck;

@Repository
public interface LinkCheckReportsRepository extends JpaRepository<LinkCheck, Long> {
	
	@Query("select new  com.innobitsysytems.ipis.dto.LinkCheckDto(lc.deviceName,lc.ipAddress,lc.deviceType,lc.status,lc.port,lc.linkTime,lc.responseTime,lc.localDateTime,lc.createdAt,lc.user.firstname,lc.user.lastname) from LinkCheck lc where lc.user.id=?3 and lc.createdAt between ?1 and ?2")
	List<LinkCheckDto> findByCreatedAtFilter(
			@Param("DateStart") Date TimeStart,
			@Param("DateEnd") Date TimeEnd,
			@Param("userid") Long userid);

	List<LinkCheck> findByUser(User user);

}

