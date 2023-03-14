/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Details Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.TrainDetails;

@Repository
public interface TrainDetailsRepository extends JpaRepository<TrainDetails, Long>{

}
