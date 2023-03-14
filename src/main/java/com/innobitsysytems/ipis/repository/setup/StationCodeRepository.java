/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Code Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import java.sql.ResultSet;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.StationCode;

@Repository
public interface StationCodeRepository extends JpaRepository<StationCode,Long>{
	
	StationCode findByStationCode (String stationCode);
	

	}

