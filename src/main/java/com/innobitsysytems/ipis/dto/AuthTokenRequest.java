/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Authorization Token Request
 */

package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;

public class AuthTokenRequest {
	
	@NotBlank
	private String authToken;

	public String getAuthToken() {
		
		return authToken;
	}

	public void setAuthToken(String authToken) {
		
		this.authToken = authToken;
	}
	   
	 
		
}
