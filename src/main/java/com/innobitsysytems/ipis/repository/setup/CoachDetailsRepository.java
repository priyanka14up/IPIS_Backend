/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Coach Details Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.CoachDetails;

@Repository
public interface CoachDetailsRepository extends JpaRepository<CoachDetails, Long>{

}
