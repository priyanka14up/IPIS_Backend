/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: User Repository
 */
package com.innobitsysytems.ipis.repository;

import com.innobitsysytems.ipis.model.User;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmailOrMobileNo(String email,String mobileNo);
	
	Optional<User> findByEmail(String email);
	
	@Query("SELECT i FROM User i WHERE i.email=:n ")
	 public User getUserByEmail(@Param("n") String email);   

	Optional<User> findByMobileNo(String mobileNo);
}
