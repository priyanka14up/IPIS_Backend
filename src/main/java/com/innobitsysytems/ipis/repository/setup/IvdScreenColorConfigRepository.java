package com.innobitsysytems.ipis.repository.setup;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.setup.IvdScreenColorConfig;

@Repository
public interface IvdScreenColorConfigRepository extends JpaRepository<IvdScreenColorConfig, Long>{
	  IvdScreenColorConfig findById(long id);
	
}
