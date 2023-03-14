/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Coaches Detail Model
 */
package com.innobitsysytems.ipis.model.device;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class CoachesDetail implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	@NotBlank
	private Integer coachNo;
    
	@NotBlank
    private String coachName;
	
	@NotBlank
    private String ipAddress;
	
	@NotBlank
    private String status;

	public Integer getCoachNo() {
		return coachNo;
	}

	public void setCoachNo(Integer coachNo) {
		this.coachNo = coachNo;
	}

	public String getCoachName() {
		return coachName;
	}

	public void setCoachName(String coachName) {
		this.coachName = coachName;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public CoachesDetail(@NotBlank Integer coachNo,  @NotBlank String coachName, @NotBlank String ipAddress,
			@NotBlank String status) {
		super();
		this.coachNo = coachNo;
		this.coachName = coachName;
		this.ipAddress = ipAddress;
		this.status = status;
	}
	
	

}
