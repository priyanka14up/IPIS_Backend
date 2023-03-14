/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Media Repository
 */
package com.innobitsysytems.ipis.repository.tvdisplay;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.tvdisplay.DisplayMedia;

@Repository
public interface DisplayMediaRepository extends JpaRepository<DisplayMedia, Long>{
	
	DisplayMedia findByMediaLocation( String mediaLocation);
	
	List<DisplayMedia> findByDisplayType( String displayType );

	@Query("SELECT i FROM DisplayMedia i WHERE i.displayType=:n and i.mediaLocation=:p")
	public DisplayMedia getFileByMediaLocation(@Param("n") String displayType, @Param("p") String mediaLocation);


}
