/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Online Train 
 */
package com.innobitsysytems.ipis.model.onlineTrain;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;


@Entity
@Table(name="onlineTrain")
@EntityListeners(AuditingEntityListener.class)
public class OnlineTrain {
	
	@Id
//	@NotBlank(message="TrainNumber is mandotry")
	@Column(name = "trainNumber", updatable=false, nullable=false)
	private Long trainNumber;
	
//	@NotBlank(message="Train Name is mandotry")
	@Column(name = "trainName")
	private String trainName;
	
//	@NotBlank(message="Arrival or Departure Name is mandotry")
	@Column(name = "arrDep")
	private String arrDep;
	
//	@NotBlank(message="Train Status is mandotry")
	@Column(name = "trainStatus")
	private String trainStatus;
	
	@Column(name = "STA")
	private String STA;
	
	@Column(name = "STD")
	private String STD;
	
//	@NotBlank(message="Train Name is mandotry")
	@Column(name="late")
	private String late;
	
	@Column(name = "ETA")
	private String ETA;
	
	@Column(name="ETD")
	private String ETD;
	
	@Column(name="platformNo")
	private String platformNo;
	
	@Column(name = "TD")
	private boolean TD;
	
	@Column(name = "CGDB")
	private boolean CGDB;
	
	@Column(name = "announcement")
	private boolean announcement;
	
	@Column(name = "frontEnd")
	private String frontEnd;
	
	@Column(name = "backEnd")
	private String backEnd;
	
	@Column(name = "coaches",columnDefinition = "text[]", nullable = true)
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] coaches;
	
	@Column(name = "repeatAnnouncement")
	private Integer repeatAnnouncement;
	
	
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

	public String getArrDep() {
		return arrDep;
	}

	public void setArrDep(String arrDep) {
		this.arrDep = arrDep;
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

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
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

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public boolean isTD() {
		return TD;
	}

	public void setTD(boolean tD) {
		TD = tD;
	}

	public boolean isCGDB() {
		return CGDB;
	}

	public void setCGDB(boolean cGDB) {
		CGDB = cGDB;
	}

	public boolean isAnnouncement() {
		return announcement;
	}

	public void setAnnouncement(boolean announcement) {
		this.announcement = announcement;
	}

	public String getFrontEnd() {
		return frontEnd;
	}

	public void setFrontEnd(String frontEnd) {
		this.frontEnd = frontEnd;
	}

	public String getBackEnd() {
		return backEnd;
	}

	public void setBackEnd(String backEnd) {
		this.backEnd = backEnd;
	}

	public String[] getCoaches() {
		return coaches;
	}

	public void setCoaches(String[] coaches) {
		this.coaches = coaches;
	}

	public Integer getRepeatAnnouncement() {
		return repeatAnnouncement;
	}

	public void setRepeatAnnouncement(Integer repeatAnnouncement) {
		this.repeatAnnouncement = repeatAnnouncement;
	}


	@Override
	public String toString() {
		return "OnlineTrain [trainNumber=" + trainNumber + ", trainName=" + trainName + ", arrDep=" + arrDep
				+ ", trainStatus=" + trainStatus + ", STA=" + STA + ", STD=" + STD + ", late=" + late + ", ETA=" + ETA
				+ ", ETD=" + ETD + ", platformNo=" + platformNo + ", TD=" + TD + ", CGDB=" + CGDB + ", announcement="
				+ announcement + ", frontEnd=" + frontEnd + ", backEnd=" + backEnd + ", coaches="
				+ Arrays.toString(coaches) + ", repeatAnnouncement=" + repeatAnnouncement + "]";
	}

	

	
}

	
