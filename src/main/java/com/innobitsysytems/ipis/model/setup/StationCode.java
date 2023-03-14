/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Code Model
 */
package com.innobitsysytems.ipis.model.setup;

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
@Table(name="stationcode")
@EntityListeners(AuditingEntityListener.class)
public class StationCode {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@NotBlank(message = "Station Code is mandatory")
	@Column(name = "stationCode", nullable = false)
    private String stationCode;
	
	@NotBlank(message = "English Station Name is mandatory")
	@Column(name = "englishStationName", nullable = false)
    private String englishStationName;
	
	@Column(name = "hindiStationName", nullable = false)
    private String hindiStationName;
	
	@Column(name = "regionalStationName", nullable = false)
    private String regionalStationName;
	
//	@NotBlank(message = "English Wave File is mandatory")
	@Column(name = "englishWaveFile", nullable = true)
    private String englishWaveFile;
	
	@Column(name = "hindiWaveFile", nullable = false)
    private String hindiWaveFile;
	
	@Column(name = "regionalWaveFile", nullable = false)
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

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getEnglishStationName() {
		return englishStationName;
	}

	public void setEnglishStationName(String englishStationName) {
		this.englishStationName = englishStationName;
	}

	public String getHindiStationName() {
		return hindiStationName;
	}

	public void setHindiStationName(String hindiStationName) {
		this.hindiStationName = hindiStationName;
	}

	public String getRegionalStationName() {
		return regionalStationName;
	}

	public void setRegionalStationName(String regionalStationName) {
		this.regionalStationName = regionalStationName;
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
		return "StationCode [id=" + id + ", stationCode=" + stationCode + ", englishStationName=" + englishStationName
				+ ", hindiStationName=" + hindiStationName + ", regionalStationName=" + regionalStationName
				+ ", englishWaveFile=" + englishWaveFile + ", hindiWaveFile=" + hindiWaveFile + ", regionalWaveFile="
				+ regionalWaveFile + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + "]";
	}
}
