/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Cgdb Transmission Dto
 */
package com.innobitsysytems.ipis.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Convert;

import com.innobitsysytems.ipis.model.reports.CoachDetailsReports;
import com.innobitsysytems.ipis.model.reports.HashMapConverter;

public class CgdbTransmissionDto {

	private String pdcPort;
	private long trainNo;
	private String trainName;
	private String platformNo;
	@Convert(converter = HashMapConverter.class)
	private List<CoachDetailsReports> data;
	private LocalDateTime localDateTime;
	private  Date createdAt;
	private String firstname;
	 private String lastname;
	 
	 
	public CgdbTransmissionDto(String pdcPort, long trainNo, String trainName, String platformNo,
			List<CoachDetailsReports> data, LocalDateTime localDateTime, Date createdAt, String firstname,
			String lastname) {
		super();
		this.pdcPort = pdcPort;
		this.trainNo = trainNo;
		this.trainName = trainName;
		this.platformNo = platformNo;
		this.data = data;
		this.localDateTime = localDateTime;
		this.createdAt = createdAt;
		this.firstname = firstname;
		this.lastname = lastname;
	}
	public String getPdcPort() {
		return pdcPort;
	}
	public void setPdcPort(String pdcPort) {
		this.pdcPort = pdcPort;
	}
	public long getTrainNo() {
		return trainNo;
	}
	public void setTrainNo(long trainNo) {
		this.trainNo = trainNo;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}
	public List<CoachDetailsReports> getData() {
		return data;
	}
	public void setData(List<CoachDetailsReports> data) {
		this.data = data;
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
		return "CgdbTransmissionDto [pdcPort=" + pdcPort + ", trainNo=" + trainNo + ", trainName=" + trainName
				+ ", platformNo=" + platformNo + ", data=" + data + ", localDateTime=" + localDateTime + ", createdAt="
				+ createdAt + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}
	 
	
}
