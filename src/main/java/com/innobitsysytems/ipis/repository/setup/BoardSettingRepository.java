/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Board Setting Repository
 */
package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.BoardSetting;

@Repository
public interface BoardSettingRepository  extends JpaRepository<BoardSetting, Long>{
	
	BoardSetting findByBoardType(String boardType);

}
