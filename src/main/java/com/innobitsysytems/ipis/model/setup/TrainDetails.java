/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Details Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
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

/**
 * @author apoorva gupta
 *
 */

@Entity
@Table(name="traindetails")
@EntityListeners(AuditingEntityListener.class)

public class TrainDetails {
	
	@Id
    @Column(name = "trainNo", nullable = false)
    private Long trainNo;
	
	@OneToOne(mappedBy = "trainDetails")
	private Train train;
	
	@NotBlank(message = "Schedule Arrival Time is mandatory")
	@Column(name = "scheduleArrivalTime", nullable = false)
    private String scheduleArrivalTime;
	
	@NotBlank(message = "Schedule Departure Time is mandatory")
	@Column(name = "scheduleDepartureTime", nullable = false)
    private String scheduleDepartureTime;
	 
	@NotNull(message = "Maximum Coach is mandatory")
	@Column(name = "maximumCoach", nullable = false)
    private Integer maximumCoach;
	
	@NotNull(message = "Running Days is mandatory")
	@Column(name = "runningDays", nullable = false,columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
	private String[] runningDays;
	
	@NotBlank(message = "Platform Number is mandatory")
	@Column(name = "platformNo", nullable = false)
    private String platformNo;
	
	@Column(name = "mergedTrains", nullable = false)
    private Boolean mergedTrains;
	
	@Column(name = "mergedTrainNo", nullable = false)
    private Integer mergedTrainNo;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

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

	public String getScheduleArrivalTime() {
		return scheduleArrivalTime;
	}

	public void setScheduleArrivalTime(String scheduleArrivalTime) {
		this.scheduleArrivalTime = scheduleArrivalTime;
	}

	public String getScheduleDepartureTime() {
		return scheduleDepartureTime;
	}

	public void setScheduleDepartureTime(String scheduleDepartureTime) {
		this.scheduleDepartureTime = scheduleDepartureTime;
	}

	public Integer getMaximumCoach() {
		return maximumCoach;
	}

	public void setMaximumCoach(Integer maximumCoach) {
		this.maximumCoach = maximumCoach;
	}

	public String[] getRunningDays() {
		return runningDays;
	}

	public void setRunningDays(String[] runningDays) {
		this.runningDays = runningDays;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public Boolean getMergedTrains() {
		return mergedTrains;
	}

	public void setMergedTrains(Boolean mergedTrains) {
		this.mergedTrains = mergedTrains;
	}

	public Integer getMergedTrainNo() {
		return mergedTrainNo;
	}

	public void setMergedTrainNo(Integer mergedTrainNo) {
		this.mergedTrainNo = mergedTrainNo;
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
		return "TrainDetails [trainNo=" + trainNo + ", train=" + train + ", scheduleArrivalTime=" + scheduleArrivalTime
				+ ", scheduleDepartureTime=" + scheduleDepartureTime + ", maximumCoach=" + maximumCoach
				+ ", runningDays=" + Arrays.toString(runningDays) + ", platformNo=" + platformNo + ", mergedTrains="
				+ mergedTrains + ", mergedTrainNo=" + mergedTrainNo + ", createdAt=" + createdAt + ", createdBy="
				+ createdBy + "]";
	}
                     


	
}
