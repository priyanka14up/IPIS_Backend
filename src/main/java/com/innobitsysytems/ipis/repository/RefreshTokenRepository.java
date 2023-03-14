/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Refresh Token Repository
 */
package com.innobitsysytems.ipis.repository;

import com.innobitsysytems.ipis.model.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
	
    Optional<RefreshToken> findByToken(String token);

    void deleteByToken(String token);
}