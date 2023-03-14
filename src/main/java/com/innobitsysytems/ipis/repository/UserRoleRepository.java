/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: User Role Repository
 */
package com.innobitsysytems.ipis.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.innobitsysytems.ipis.model.UserRole;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Long>{
	
	Optional <UserRole> findByRoleText(String roleText);
	
}
