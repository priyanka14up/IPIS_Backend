/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Name Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.TrainName;

@Repository
public interface TrainNameRepository extends JpaRepository<TrainName, Long>{

	public TrainName findByTrainNo(Long trainNo);
}
