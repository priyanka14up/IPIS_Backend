
/**
 * Name: Priyanka Upadhyay
 * Copyright: Innobit Systems, 2021
 * Purpose: Train No Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.innobitsysytems.ipis.model.announcement.ToAnnouncement;

@Repository
public interface ToAnnoRepository extends JpaRepository<ToAnnouncement, Long>{

	
}




