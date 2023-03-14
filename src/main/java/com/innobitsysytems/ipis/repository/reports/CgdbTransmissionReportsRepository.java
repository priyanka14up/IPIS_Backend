/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2021
 * Purpose: Cgdb Transmission Reports Repository
 */
package com.innobitsysytems.ipis.repository.reports;

import java.util.Date;
import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.dto.CgdbTransmissionDto;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.reports.CgdbTransmission;
import com.innobitsysytems.ipis.model.reports.Login;

@Repository
public interface CgdbTransmissionReportsRepository extends JpaRepository<CgdbTransmission, Long>{
	
	@Query("select new  com.innobitsysytems.ipis.dto.CgdbTransmissionDto(ct.pdcPort,ct.trainNo,ct.trainName,ct.platformNo,ct.data,ct.localDateTime,ct.createdAt,ct.user.firstname,ct.user.lastname) from CgdbTransmission ct where ct.user.id=?3 and ct.createdAt between ?1 and ?2")
//	@Query("select new  com.innobitsysytems.ipis.dto.LinkCheckDto(lc.deviceName,lc.ipAddress,lc.deviceType,lc.status,lc.port,lc.linkTime,lc.responseTime,lc.localDateTime,lc.createdAt,lc.user.firstname,lc.user.lastname) from LinkCheck lc where lc.user.id=?3 and lc.createdAt between ?1 and ?2")
	List<CgdbTransmissionDto> findByCreatedAtFilter(
			@Param("DateStart") Date TimeStart,
			@Param("DateEnd") Date TimeEnd,
			@Param("userid") Long userid);
	
	List<CgdbTransmission> findByUser(User user);
}
