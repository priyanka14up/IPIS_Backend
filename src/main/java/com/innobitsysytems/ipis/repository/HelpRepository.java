/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Help Repository
 */
package com.innobitsysytems.ipis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.Help;

@Repository
public interface HelpRepository  extends JpaRepository<Help, Long>{
	
	
	

}
