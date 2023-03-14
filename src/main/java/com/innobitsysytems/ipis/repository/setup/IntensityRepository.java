/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Intensity Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.Intensity;

@Repository
public interface IntensityRepository  extends JpaRepository<Intensity, Long>
{

	 
	 Intensity findByIntensityMode(String intensityMode);
	 Intensity findByMode(String mode);
	 
	// List<Intensity> findByIntensitiesMode(String intensityMode);
	
}
