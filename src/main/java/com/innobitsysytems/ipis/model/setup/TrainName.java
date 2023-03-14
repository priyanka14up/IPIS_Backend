/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Name Model
 */
package com.innobitsysytems.ipis.model.setup;

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

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="trainname")
@EntityListeners(AuditingEntityListener.class)
public class TrainName {
	
	@Id
    @Column(name = "trainNo", nullable = false)
    private Long trainNo;
	
	@OneToOne(mappedBy = "trainName")
	private Train train;
	
	@NotBlank(message = "English Train Name is mandatory")
	@Column(name = "englishTrainName")
    private String englishTrainName;
	
	@Column(name = "hindiTrainName", nullable = false)
    private String hindiTrainName;
	
	@Column(name = "regionalTrainName", nullable = false)
    private String regionalTrainName;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
	
	@Column(name = "englishWaveFile", nullable = false)
    private String englishWaveFile;
	
	@Column(name = "hindiWaveFile", nullable = false)
    private String hindiWaveFile;
	
	@Column(name = "regionalWaveFile", nullable = false)
    private String regionalWaveFile;

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

	public String getEnglishTrainName() {
		return englishTrainName;
	}

	public void setEnglishTrainName(String englishTrainName) {
		this.englishTrainName = englishTrainName;
	}

	public String getHindiTrainName() {
		return hindiTrainName;
	}

	public void setHindiTrainName(String hindiTrainName) {
		this.hindiTrainName = hindiTrainName;
	}

	public String getRegionalTrainName() {
		return regionalTrainName;
	}

	public void setRegionalTrainName(String regionalTrainName) {
		this.regionalTrainName = regionalTrainName;
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

	public String getEnglishWaveFile() {
		return englishWaveFile;
	}

	public void setEnglishWaveFile(String englishWaveFile) {
		this.englishWaveFile = englishWaveFile;
	}

	public String getHindiWaveFile() {
		return hindiWaveFile;
	}

	public void setHindiWaveFile(String hindiWaveFile) {
		this.hindiWaveFile = hindiWaveFile;
	}

	public String getRegionalWaveFile() {
		return regionalWaveFile;
	}

	public void setRegionalWaveFile(String regionalWaveFile) {
		this.regionalWaveFile = regionalWaveFile;
	}

	@Override
	public String toString() {
		return "TrainName [trainNo=" + trainNo + ", train=" + train + ", englishTrainName=" + englishTrainName
				+ ", hindiTrainName=" + hindiTrainName + ", regionalTrainName=" + regionalTrainName + ", createdAt="
				+ createdAt + ", createdBy=" + createdBy + ", englishWaveFile=" + englishWaveFile + ", hindiWaveFile="
				+ hindiWaveFile + ", regionalWaveFile=" + regionalWaveFile + "]";
	}


	
}
