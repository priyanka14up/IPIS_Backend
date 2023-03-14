/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.Train;

@Repository
public interface TrainRepository extends JpaRepository<Train, Long>{
	
	public Train findByTrainNo(Long trainNo);

    boolean  existsByTrainNo(Long trainNo);

//    @Modifying
//    public void updateByTrainNo(@Param("trainNo") Long trainNo);
	

}
