
/**
 * Name: Priyanka Upadhyay
 * Copyright: Innobit Systems, 2022
 * Purpose: Train No Announcement Repository
 */
package com.innobitsysytems.ipis.repository.announcement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.innobitsysytems.ipis.model.announcement.SchedulesToArriveAt;


@Repository
public interface SchedulesToAnnoRepository extends JpaRepository<SchedulesToArriveAt, Long>{

	
}




