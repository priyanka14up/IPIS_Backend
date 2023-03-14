/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Board Diagnostic Dto
 */
package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class BoardDiagnosticDto {
	
	@NotBlank(message = "Board Type is mandatory")
    private String boardType;
	
	@NotNull(message = "Platform Number is mandatory")
    private Integer platformNo;
	
	@NotBlank(message = "Device Id is mandatory")
    private String deviceId;
	
	@NotBlank(message = "Test Command is mandatory")
    private String testCommand;
	
	@NotBlank(message = "Sent Data is mandatory")
    private String sentData;
	
	@NotBlank(message = "Response Time is mandatory")
    private String responseTime;
	
	@NotBlank(message = "Received Data is mandatory")
    private String receivedData;

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public Integer getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(Integer platformNo) {
		this.platformNo = platformNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getTestCommand() {
		return testCommand;
	}

	public void setTestCommand(String testCommand) {
		this.testCommand = testCommand;
	}

	public String getSentData() {
		return sentData;
	}

	public void setSentData(String sentData) {
		this.sentData = sentData;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public String getReceivedData() {
		return receivedData;
	}

	public void setReceivedData(String receivedData) {
		this.receivedData = receivedData;
	}
	
	
	

}
