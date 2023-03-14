/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Details Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.StationDetails;

@Repository
public interface StationDetailsRepository extends JpaRepository<StationDetails, Long>{
	
	 StationDetails findByStationName(String stationName);

	 @Query(value = "SELECT a.station_name,a.station_code FROM stationdetails a", nativeQuery = true)
	 Object getStationNamesAndCodes();
}
