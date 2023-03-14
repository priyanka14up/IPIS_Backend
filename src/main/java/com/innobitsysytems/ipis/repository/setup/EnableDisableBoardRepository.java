/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Enable Disable Board Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.EnableDisableBoard;

@Repository
public interface EnableDisableBoardRepository extends JpaRepository<EnableDisableBoard, Long>{

}
