/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Login Reports Repository
 */
package com.innobitsysytems.ipis.repository.reports;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.dto.LoginDto;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.reports.Login;

@Repository
public interface LoginReportsRepository extends JpaRepository<Login, Long> {
	
	
	List<Login> findByUser(User user);
	
	List<Long> deleteByUser(User user);
	
	List<LoginDto> findByUserAndCreatedAtBetween(User user, @Param("DateStart") Date TimeStart, @Param("DateEnd") Date TimeEnd);
	
	
}

