/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Refresh Token Service
 */

package com.innobitsysytems.ipis.services;

import com.innobitsysytems.ipis.exception.SpringLoginException;
import com.innobitsysytems.ipis.model.RefreshToken;
import com.innobitsysytems.ipis.repository.RefreshTokenRepository;
import com.innobitsysytems.ipis.utility.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;
@Service
@Transactional
public class RefreshTokenService {

	@Autowired
    private  RefreshTokenRepository refreshTokenRepository;
	@Autowired
	private CommonUtil commonUtil;
    
    public RefreshToken generateRefreshToken() {
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setCreatedDate(Instant.now());

        return refreshTokenRepository.save(refreshToken);
    }

    void validateRefreshToken(String token) {
        refreshTokenRepository.findByToken(token)
                .orElseThrow(() -> new SpringLoginException("Invalid refresh Token"));
    }

    public void deleteRefreshToken(String token) {

        refreshTokenRepository.deleteByToken(token);
        commonUtil.LogoutReport(token);
    }
}