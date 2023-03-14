/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Board Led Dto
 */
package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BoardLedDto {
	
	@NotBlank(message = "Board Type is mandatory")
    private String boardType;
	
	@NotBlank(message = "Device Id is mandatory")
    private String deviceId;
	
	@NotBlank(message = "Test Pattern is mandatory")
    private String testPattern;
	
	@NotNull(message = "Platform Number is mandatory")
    private Integer platformNo;
	
	@NotNull(message = "Installation Test is mandatory")
    private Boolean installationTest;
	
	@NotNull(message = "LedAutoTest is mandatory")
    private Boolean ledAutoTest;
	
	@NotNull(message = "Time is mandatory")
    private Long time;

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTestPattern() {
		return testPattern;
	}

	public void setTestPattern(String testPattern) {
		this.testPattern = testPattern;
	}

	public Integer getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(Integer platformNo) {
		this.platformNo = platformNo;
	}

	public Boolean getInstallationTest() {
		return installationTest;
	}

	public void setInstallationTest(Boolean installationTest) {
		this.installationTest = installationTest;
	}

	public Boolean getLedAutoTest() {
		return ledAutoTest;
	}

	public void setLedAutoTest(Boolean ledAutoTest) {
		this.ledAutoTest = ledAutoTest;
	}

	public Long getTime() {
		return time;
	}

	public void setTime(Long time) {
		this.time = time;
	}
	
}
