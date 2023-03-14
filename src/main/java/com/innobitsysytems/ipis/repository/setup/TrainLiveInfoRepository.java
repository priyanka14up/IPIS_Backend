/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Live Information Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.TrainLiveInfo;

@Repository
public interface TrainLiveInfoRepository extends JpaRepository<TrainLiveInfo, Long>{

}
