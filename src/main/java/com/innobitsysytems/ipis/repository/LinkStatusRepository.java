/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Link Status Repository
 */
package com.innobitsysytems.ipis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.LinkStatus;

@Repository
public interface LinkStatusRepository extends JpaRepository<LinkStatus, Long> {

}
