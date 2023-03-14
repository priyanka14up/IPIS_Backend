package com.innobitsysytems.ipis.repository;

import java.time.LocalDate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.onlineTrain.PacketCount;

import novj.publ.util.DateTime.Date;
@Repository
public interface PacketCountRepository extends JpaRepository<PacketCount, Integer>{
	public PacketCount findBydate(LocalDate date);

}
