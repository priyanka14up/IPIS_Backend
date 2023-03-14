/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Message Repository 
 */
package com.innobitsysytems.ipis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.setup.BoardSetting;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long>{
	
	@Query("SELECT i FROM Message i WHERE i.displayBoard=:n and i.platformNo=:p and i.deviceId=:q")
	public Message getMsgByDeviceType(@Param("n") String displayBoard, @Param("p") String platformNo, @Param("q") String deviceId);
	
	List<Message> findByDisplayBoard(String displayBoard);
	
	List<Message> findByDisplayStatus(Boolean displayStatus);
	
	//Message findByBoardType(String boardType);

}
