/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Link Check Dto
 */
package com.innobitsysytems.ipis.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class LinkCheckDto {
	private String deviceName;
	private String ipAddress;
	private String deviceType;
	private String status;
	private String port;
	private String linkTime;
	private String responseTime;
	private LocalDateTime localDateTime;
	private  Date createdAt;
	private String firstname;
	 private String lastname;
	 
	public LinkCheckDto(String deviceName, String ipAddress, String deviceType, String status, String port,
			String linkTime, String responseTime, LocalDateTime localDateTime, Date createdAt, String firstname,
			String lastname) {
		super();
		this.deviceName = deviceName;
		this.ipAddress = ipAddress;
		this.deviceType = deviceType;
		this.status = status;
		this.port = port;
		this.linkTime = linkTime;
		this.responseTime = responseTime;
		this.localDateTime = localDateTime;
		this.createdAt = createdAt;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLinkTime() {
		return linkTime;
	}

	public void setLinkTime(String linkTime) {
		this.linkTime = linkTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "LinkCheckDto [deviceName=" + deviceName + ", ipAddress=" + ipAddress + ", deviceType=" + deviceType
				+ ", status=" + status + ", port=" + port + ", linkTime=" + linkTime + ", responseTime=" + responseTime
				+ ", localDateTime=" + localDateTime + ", createdAt=" + createdAt + ", firstname=" + firstname
				+ ", lastname=" + lastname + "]";
	}
	 
}
