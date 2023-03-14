/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Coach Details Reports Model
 */
package com.innobitsysytems.ipis.model.reports;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class CoachDetailsReports implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */
@NotBlank
	private String coachSequence;
@NotBlank
    private String ipAddress;
@NotBlank
    private String status;

	public String getCoachSequence() {
		return coachSequence;
	}

	public void setCoachSequence(String coachSequence) {
		this.coachSequence = coachSequence;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
	@Override
	public String toString() {
		return "CoachDetailsReports [coachSequence=" + coachSequence + ", ipAddress=" + ipAddress + ", status=" + status
				+ "]";
	}
}
