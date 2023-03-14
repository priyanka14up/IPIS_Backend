package com.innobitsysytems.ipis.repository.setup;


import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.TrainStatusColor;
@Repository
public interface TrainStatusColorRepository extends JpaRepository<TrainStatusColor, Long>{
	  TrainStatusColor findById(long id);
	  TrainStatusColor findByStatusAndArrDep(String status, String arrDep);
	  List <TrainStatusColor> findAllById(Long id);
	
}
