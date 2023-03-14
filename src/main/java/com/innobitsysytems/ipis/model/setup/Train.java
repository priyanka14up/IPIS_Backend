/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="train")
@EntityListeners(AuditingEntityListener.class)

public class Train {
	
	@Id
    @Column(name = "trainNo", nullable = false)
	@NotNull(message = "Train Number is mandatory")
    private Long trainNo;
	
	@OneToOne(cascade=CascadeType.ALL)
	@NotNull(message = "Train Name is mandatory")
	@JoinColumn(name = "trainNameId", referencedColumnName = "trainNo")
	private TrainName trainName;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "trainDetailId", referencedColumnName = "trainNo")
	private TrainDetails trainDetails;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "coachDetailId", referencedColumnName = "trainNo")
	private CoachDetails coachDetails;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "trainLiveInfoId", referencedColumnName = "trainNo")
	private TrainLiveInfo trainLiveInfo;
	
	@NotBlank(message = "Source Station is mandatory")
	@Column(name = "sourceStation", nullable = false)
    private String sourceStation;
	
	@NotBlank(message = "Destination Station is mandatory")
	@Column(name = "destinationStation", nullable = false)
    private String destinationStation;
	
	@NotBlank(message = "Train Direction is mandatory")
	@Column(name = "trainDirection", nullable = false)
    private String trainDirection;
	
	@NotNull(message = "Intermediate Station details is mandatory")
	@Column(name = "viaStation", nullable = false,columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
	private String[] viaStation;
	
	@NotBlank(message = "Train Type is mandatory")
	@Column(name = "trainType", nullable = false)
    private String trainType;
	
	@NotBlank(message = "Train Arriving Departuure Status is mandatory")
	@Column(name = "trainArrDepStatus", nullable = false)
    private String trainArrDepStatus;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;

	public Long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(Long trainNo) {
		this.trainNo = trainNo;
	}

	public TrainName getTrainName() {
		return trainName;
	}

	public void setTrainName(TrainName trainName) {
		this.trainName = trainName;
	}

	public TrainDetails getTrainDetails() {
		return trainDetails;
	}

	public void setTrainDetails(TrainDetails trainDetails) {
		this.trainDetails = trainDetails;
	}

	public CoachDetails getCoachDetails() {
		return coachDetails;
	}

	public void setCoachDetails(CoachDetails coachDetails) {
		this.coachDetails = coachDetails;
	}

	public TrainLiveInfo getTrainLiveInfo() {
		return trainLiveInfo;
	}

	public void setTrainLiveInfo(TrainLiveInfo trainLiveInfo) {
		this.trainLiveInfo = trainLiveInfo;
	}

	public String getSourceStation() {
		return sourceStation;
	}

	public void setSourceStation(String sourceStation) {
		this.sourceStation = sourceStation;
	}

	public String getDestinationStation() {
		return destinationStation;
	}

	public void setDestinationStation(String destinationStation) {
		this.destinationStation = destinationStation;
	}

	public String getTrainDirection() {
		return trainDirection;
	}

	public void setTrainDirection(String trainDirection) {
		this.trainDirection = trainDirection;
	}

	public String[] getViaStation() {
		return viaStation;
	}

	public void setViaStation(String[] viaStation) {
		this.viaStation = viaStation;
	}

	public String getTrainType() {
		return trainType;
	}

	public void setTrainType(String trainType) {
		this.trainType = trainType;
	}

	public String getTrainArrDepStatus() {
		return trainArrDepStatus;
	}

	public void setTrainArrDepStatus(String trainArrDepStatus) {
		this.trainArrDepStatus = trainArrDepStatus;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	@Override
	public String toString() {
		return "Train [trainNo=" + trainNo + ", trainName=" + trainName + ", trainDetails=" + trainDetails
				+ ", coachDetails=" + coachDetails + ", trainLiveInfo=" + trainLiveInfo + ", sourceStation="
				+ sourceStation + ", destinationStation=" + destinationStation + ", trainDirection=" + trainDirection
				+ ", viaStation=" + Arrays.toString(viaStation) + ", trainType=" + trainType + ", trainArrDepStatus="
				+ trainArrDepStatus + ", createdAt=" + createdAt + ", createdBy=" + createdBy + "]";
	}
    
	
}
