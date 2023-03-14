/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Coach Data Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.CoachData;

@Repository
public interface CoachDataRepository extends JpaRepository<CoachData, Long>{
	
	CoachData findByEngCoachName(String engCoachName);

}
