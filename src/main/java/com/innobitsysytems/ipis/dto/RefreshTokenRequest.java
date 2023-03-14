/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Refresh Token Request
 */
package com.innobitsysytems.ipis.dto;


import javax.validation.constraints.NotBlank;


public class RefreshTokenRequest {
    @NotBlank
    private String refreshToken;
    private String username;
    
    public String getRefreshToken() {
		return refreshToken;
	}
	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	
	
}