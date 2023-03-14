/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Status Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.TrainStatus;

@Repository
public interface TrainStatusRepository extends JpaRepository<TrainStatus, Long>{
	
	TrainStatus findByStatusCode(String statusCode);
	TrainStatus findByEnglishFile(String englishFile);


}