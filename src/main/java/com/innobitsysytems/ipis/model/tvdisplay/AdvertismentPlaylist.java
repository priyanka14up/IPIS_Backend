/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Advertisment Playlist Model
 */
package com.innobitsysytems.ipis.model.tvdisplay;

import java.util.*;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "advertismentPlaylist")
@EntityListeners(AuditingEntityListener.class)
public class AdvertismentPlaylist {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@Column(name = "queueSequence",columnDefinition = "text[]", nullable = true)
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private  String[] queueSequence;
	
	@Column(name="playlistName")
	private String playlistName;
	
	@Column(name = "isRepeatable")
	private Boolean isRepeatable;

	@Column(name = "startDate")
	private String startDate;
	
	@Column(name = "endDate")
	private Integer endDate;
	
	@Column(name = "validity")
	private Integer validity;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false)
	private Date createdAt;

	@Column(name = "createdBy", nullable = false)
	@CreatedBy
	private String createdBy;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = false)
	private Date updatedAt;

	@Column(name = "updatedBy", nullable = true)
	@LastModifiedBy
	private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String[] getQueueSequence() {
		return queueSequence;
	}

	public void setQueueSequence(String[] queueSequence) {
		this.queueSequence = queueSequence;
	}

	public String getPlaylistName() {
		return playlistName;
	}

	public void setPlaylistName(String playlistName) {
		this.playlistName = playlistName;
	}

	public Boolean getIsRepeatable() {
		return isRepeatable;
	}

	public void setIsRepeatable(Boolean isRepeatable) {
		this.isRepeatable = isRepeatable;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public Integer getEndDate() {
		return endDate;
	}

	public void setEndDate(Integer endDate) {
		this.endDate = endDate;
	}

	public Integer getValidity() {
		return validity;
	}

	public void setValidity(Integer validity) {
		this.validity = validity;
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
		return "AdvertismentPlaylist [id=" + id + ", queueSequence=" + Arrays.toString(queueSequence)
				+ ", playlistName=" + playlistName + ", isRepeatable=" + isRepeatable + ", startDate=" + startDate
				+ ", endDate=" + endDate + ", validity=" + validity + ", createdAt=" + createdAt + ", createdBy="
				+ createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	
	
	
	

}
