/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Status Model
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
@Table(name="trainstatus")
@EntityListeners(AuditingEntityListener.class)
public class TrainStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@NotBlank(message = "Status Code is mandatory")
	@Column(name = "statusCode", nullable = false)
    private String statusCode;
	
	@Column(name="aord",nullable=true)
	private String aord;
	
	public String getAord() {
		return aord;
	}

	public void setAord(String aord) {
		this.aord = aord;
	}

	@NotBlank(message = "English Status is mandatory")
	@Column(name = "englishStatus", nullable = false)
    private String englishStatus;
	
	@NotBlank(message = "Hindi Status is mandatory")
	@Column(name = "hindiStatus", nullable = false)
    private String hindiStatus;
	
	@Column(name = "regionalStatus", nullable = false)
    private String regionalStatus;
	
	@Column(name = "englishFile", nullable = true)
    private String englishFile;
	
	@Column(name = "hindiFile", nullable = false)
    private String hindiFile;
	
	@Column(name = "regionalFile", nullable = false)
    private String regionalFile;
	
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

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getEnglishStatus() {
		return englishStatus;
	}

	public void setEnglishStatus(String englishStatus) {
		this.englishStatus = englishStatus;
	}

	public String getHindiStatus() {
		return hindiStatus;
	}

	public void setHindiStatus(String hindiStatus) {
		this.hindiStatus = hindiStatus;
	}

	public String getRegionalStatus() {
		return regionalStatus;
	}

	public void setRegionalStatus(String regionalStatus) {
		this.regionalStatus = regionalStatus;
	}

	public String getEnglishFile() {
		return englishFile;
	}

	public void setEnglishFile(String englishFile) {
		this.englishFile = englishFile;
	}

	public String getHindiFile() {
		return hindiFile;
	}

	public void setHindiFile(String hindiFile) {
		this.hindiFile = hindiFile;
	}

	public String getRegionalFile() {
		return regionalFile;
	}

	public void setRegionalFile(String regionalFile) {
		this.regionalFile = regionalFile;
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
		return "TrainStatus [id=" + id + ", statusCode=" + statusCode + ", aord=" + aord + ", englishStatus="
				+ englishStatus + ", hindiStatus=" + hindiStatus + ", regionalStatus=" + regionalStatus
				+ ", englishFile=" + englishFile + ", hindiFile=" + hindiFile + ", regionalFile=" + regionalFile
				+ ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}

	

}