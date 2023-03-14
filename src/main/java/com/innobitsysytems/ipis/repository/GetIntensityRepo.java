/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Help Repository
 */
package com.innobitsysytems.ipis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.Help;
import com.innobitsysytems.ipis.model.setup.GetIntensity;

@Repository
public interface GetIntensityRepo  extends JpaRepository<GetIntensity, Long>{
	
	
	

}
