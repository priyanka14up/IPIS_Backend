/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Online Train Repository
 */
package com.innobitsysytems.ipis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;

@Repository
public interface OnlineTrainRepository extends JpaRepository<OnlineTrain, Long> {

	public OnlineTrain findBytrainNumber(Long trainNo);
	public boolean existsByTrainNumber(Long trainNo);
	public List<OnlineTrain> findByCGDB(boolean CGDB);
	public List<OnlineTrain> findByTD(boolean TD);
	public List<OnlineTrain> findByAnnouncement(boolean announcement);
	//public List<OnlineTrain> findByAuto(boolean auto);

}
