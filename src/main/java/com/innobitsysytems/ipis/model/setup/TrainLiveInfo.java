/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Live Info Model
 */
package com.innobitsysytems.ipis.model.setup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="trainliveinfo")
@EntityListeners(AuditingEntityListener.class)

public class TrainLiveInfo {
	
	@Id
    @Column(name = "trainNo", nullable = false)
    private Long trainNo;
	
	@OneToOne(mappedBy = "trainLiveInfo")
	private Train train;
	
	@Column(name = "arrDepartStatus")
    private String arrDepartStatus;
	
	@Column(name = "runningStatus")
    private String runningStatus;
	
	@Column(name = "sat")	
    private String sat;
	
	@Column(name = "sdt")
    private String sdt;
	
	@Column(name = "edt")
    private String edt;
	
	@Column(name = "eat")
    private String eat;
	
	@Column(name = "late")
    private String late;
	
	@Column(name = "platformNo", nullable = false)
    private String platformNo;

	public Long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(Long trainNo) {
		this.trainNo = trainNo;
	}

	public Train getTrain() {
		return train;
	}

	public void setTrain(Train train) {
		this.train = train;
	}

	public String getArrDepartStatus() {
		return arrDepartStatus;
	}

	public void setArrDepartStatus(String arrDepartStatus) {
		this.arrDepartStatus = arrDepartStatus;
	}

	public String getRunningStatus() {
		return runningStatus;
	}

	public void setRunningStatus(String runningStatus) {
		this.runningStatus = runningStatus;
	}

	public String getSat() {
		return sat;
	}

	public void setSat(String sat) {
		this.sat = sat;
	}

	public String getSdt() {
		return sdt;
	}

	public void setSdt(String sdt) {
		this.sdt = sdt;
	}

	public String getEdt() {
		return edt;
	}

	public void setEdt(String edt) {
		this.edt = edt;
	}

	public String getEat() {
		return eat;
	}

	public void setEat(String eat) {
		this.eat = eat;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}



}
