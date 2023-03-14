/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Transmission Reports Repository
 */
package com.innobitsysytems.ipis.repository.reports;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.dto.TrainTransmissionDto;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.reports.Announcement;
import com.innobitsysytems.ipis.model.reports.TrainTransmission;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

@Repository
public interface TrainTransmissionReportsRepository extends JpaRepository<TrainTransmission, Long> {
	
	@Query("select new  com.innobitsysytems.ipis.dto.TrainTransmissionDto(tt.trainName,tt.trainNameRegional,tt.trainNo,tt.scheuduledArrival,tt.scheuduledDeparture,tt.late,tt.actualArrival,tt.actualDeparture,tt.trainStatus,tt.route,tt.localDateTime,tt.createdAt,tt.user.firstname,tt.user.lastname) from TrainTransmission tt where tt.user.id=?3 and tt.createdAt between ?1 and ?2")
	List<TrainTransmissionDto> findByCreatedAtFilter(
			@Param("DateStart") Date TimeStart,
			@Param("DateEnd") Date TimeEnd,
			@Param("userid") Long userid);
	
	List<TrainTransmission> findByUser(User user);
}
