/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Coach Details Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="coachdetails")
@EntityListeners(AuditingEntityListener.class)

public class CoachDetails {

	@Id
    @Column(name = "trainNo", nullable = false)
    private Long trainNo;
	
	@OneToOne(mappedBy = "coachDetails")
	private Train train;
	
	@Column(name = "frontSideEnd", nullable = false)
    private String frontSideEnd;
	
	@Column(name = "backSideEnd", nullable = false)
    private String backSideEnd;
	
	@Column(name = "coaches",columnDefinition = "text[]", nullable = true)
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] coaches;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by")
    @CreatedBy
    private String createdBy;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

  
	@Column(name = "updated_by", nullable = true)
    @LastModifiedBy
    private String updatedBy;


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


	public String getFrontSideEnd() {
		return frontSideEnd;
	}


	public void setFrontSideEnd(String frontSideEnd) {
		this.frontSideEnd = frontSideEnd;
	}


	public String getBackSideEnd() {
		return backSideEnd;
	}


	public void setBackSideEnd(String backSideEnd) {
		this.backSideEnd = backSideEnd;
	}


	public String[] getCoaches() {
		return coaches;
	}


	public void setCoaches(String[] coaches) {
		this.coaches = coaches;
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
		return "CoachDetails [trainNo=" + trainNo + ", train=" + train + ", frontSideEnd=" + frontSideEnd
				+ ", backSideEnd=" + backSideEnd + ", coaches=" + Arrays.toString(coaches) + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}

	
	

	
}
