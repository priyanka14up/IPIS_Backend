package com.innobitsysytems.ipis.model.announcement;


/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Code Model
 */

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="trainNameEntryAnnouncements")
@EntityListeners(AuditingEntityListener.class)
public class TrainNameEntryAnnouncement {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@Column(name = "trainName", nullable = true)
    private String trainName;
	
	@Column(name = "englishWaveFile", nullable = true)
    private String englishWaveFile;
	
	@Column(name = "hindiWaveFile", nullable = true)
    private String hindiWaveFile;
	
	@Column(name = "regionalWaveFile", nullable = true)
    private String regionalWaveFile;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "updated_by", nullable = true)
    @LastModifiedBy
    private String updatedBy;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
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

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "TrainNameEntryAnnouncement [id=" + id + ", trainName=" + trainName + ", englishWaveFile="
				+ englishWaveFile + ", hindiWaveFile=" + hindiWaveFile + ", regionalWaveFile=" + regionalWaveFile
				+ ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}

	


}
