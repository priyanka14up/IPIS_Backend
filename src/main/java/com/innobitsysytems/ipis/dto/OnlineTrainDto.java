/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Online Train Dto
 */
package com.innobitsysytems.ipis.dto;

import java.util.Date;

public class OnlineTrainDto {
	
	private Long trainNumber;
	private String trainName;
	private String Aord;
	private String trainStatus;
	private String STA;
	private String STD;
	private String ETA;
	private String ETD;
	private String late;
	private Long platformNo;
	private Boolean TD;
	private Boolean CGDB;
	private Boolean Announcement;
	public Long getTrainNumber() {
		return trainNumber;
	}
	public void setTrainNumber(Long trainNumber) {
		this.trainNumber = trainNumber;
	}
	public String getTrainName() {
		return trainName;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}
	public String getAord() {
		return Aord;
	}
	public void setAord(String aord) {
		Aord = aord;
	}
	public String getTrainStatus() {
		return trainStatus;
	}
	public void setTrainStatus(String trainStatus) {
		this.trainStatus = trainStatus;
	}
	
	public String getSTA() {
		return STA;
	}
	public void setSTA(String sTA) {
		STA = sTA;
	}
	public String getSTD() {
		return STD;
	}
	public void setSTD(String sTD) {
		STD = sTD;
	}
	public String getETA() {
		return ETA;
	}
	public void setETA(String eTA) {
		ETA = eTA;
	}
	public String getETD() {
		return ETD;
	}
	public void setETD(String eTD) {
		ETD = eTD;
	}
	public String getLate() {
		return late;
	}
	public void setLate(String late) {
		this.late = late;
	}
	public Long getPlatformNo() {
		return platformNo;
	}
	public void setPlatformNo(Long platformNo) {
		this.platformNo = platformNo;
	}
	public Boolean getTD() {
		return TD;
	}
	public void setTD(Boolean tD) {
		TD = tD;
	}
	public Boolean getCGDB() {
		return CGDB;
	}
	public void setCGDB(Boolean cGDB) {
		CGDB = cGDB;
	}
	public Boolean getAnnouncement() {
		return Announcement;
	}
	public void setAnnouncement(Boolean announcement) {
		Announcement = announcement;
	}
	@Override
	public String toString() {
		return "OnlineTrainDto [trainNumber=" + trainNumber + ", trainName=" + trainName + ", Aord=" + Aord
				+ ", trainStatus=" + trainStatus + ", STA=" + STA + ", STD=" + STD + ", ETA=" + ETA + ", ETD=" + ETD
				+ ", late=" + late + ", platformNo=" + platformNo + ", TD=" + TD + ", CGDB=" + CGDB + ", Announcement="
				+ Announcement + "]";
	}
	
	
}
