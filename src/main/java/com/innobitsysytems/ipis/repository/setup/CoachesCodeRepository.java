package com.innobitsysytems.ipis.repository.setup;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.model.setup.CoachesCode;

@Repository
public interface CoachesCodeRepository extends JpaRepository<CoachesCode, Long>{
	
	//CoachData findByEngCoachName(String engCoachName);

}