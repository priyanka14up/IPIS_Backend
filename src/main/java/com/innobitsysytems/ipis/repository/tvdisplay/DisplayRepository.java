/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Repository 
 */
package com.innobitsysytems.ipis.repository.tvdisplay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.tvdisplay.Display;

@Repository
public interface DisplayRepository extends JpaRepository<Display, Long>{
	
	 Display findByDisplayType( String displayType);

}
