/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Media Queue Repository
 */
package com.innobitsysytems.ipis.repository.tvdisplay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.tvdisplay.MediaQueue;

@Repository
public interface MediaQueueRepository  extends JpaRepository<MediaQueue, Long> {
	
	List<MediaQueue> findByDisplayType(String displayType);
	
	MediaQueue findByMediaLocation(String mediaLocation);
	
	@Query("SELECT i FROM MediaQueue i WHERE i.displayType=:n and i.mediaLocation=:p")
	public MediaQueue getMediaQueueByMediaLocation(@Param("n") String displayType, @Param("p") String mediaLocation);
	
	@Query("SELECT i FROM MediaQueue i WHERE i.displayType=:n and i.isPlaying=:p")
	public List<MediaQueue> getMediaQueueByIsPlaying(@Param("n") String displayType, @Param("p") Boolean isPlaying);

}
