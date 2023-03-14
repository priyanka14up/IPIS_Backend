/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Model 
 */

package com.innobitsysytems.ipis.model.tvdisplay;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="display")
@EntityListeners(AuditingEntityListener.class)
public class Display {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@NotBlank(message = "Display Type is mandatory")
	@Column(name="displayType")
	private String displayType;
	
	@NotNull(message = "Enable Display is mandatory")
	@Column(name="enableDisplay")
	private Boolean enableDisplay;
	
	@Column(name="showMessage")
	private Boolean showMessage;
	
	@Column(name="showCoach")
	private Boolean showCoach;
	
	@Column(name="showMedia")
	private Boolean showMedia;
	
	@NotNull(message = "Display Timeout is mandatory")
	@Column(name="displayTimeout")
	private Integer displayTimeout;
	
	@NotNull(message = "Grid Row Height is mandatory")
	@Column(name="gridRowHeight")
	private Integer gridRowHeight;
	
	@NotNull(message = "trainNoWidth is mandatory")
	@Column(name="trainNoWidth")
	private Integer trainNoWidth;
	
	@NotNull(message = "Expected Time Width is mandatory")
	@Column(name="expectedTimeWidth")
	private Integer expectedTimeWidth;
	
	@NotNull(message = "Arrival Departure Width is mandatory")
	@Column(name="arrivalDepartureWidth")
	private Integer arrivalDepartureWidth;
	
	@NotNull(message = "Platform Width is mandatory")
	@Column(name="platformWidth")
	private Integer platformWidth;
	
	@NotNull(message = "Grid Page Time is mandatory")
	@Column(name="gridPageTime")
	private Integer gridPageTime;
	
	@NotNull(message = "Message Scroll Speed is mandatory")
	@Column(name="messageScrollSpeed")
	private Integer messageScrollSpeed;
	
	@NotNull(message = "Media Start Interval Time is mandatory")
	@Column(name="mediaStartIntervalTime")
	private Integer mediaStartIntervalTime;
	
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

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public Boolean getEnableDisplay() {
		return enableDisplay;
	}

	public void setEnableDisplay(Boolean enableDisplay) {
		this.enableDisplay = enableDisplay;
	}

	public Boolean getShowMessage() {
		return showMessage;
	}

	public void setShowMessage(Boolean showMessage) {
		this.showMessage = showMessage;
	}

	public Boolean getShowCoach() {
		return showCoach;
	}

	public void setShowCoach(Boolean showCoach) {
		this.showCoach = showCoach;
	}

	public Boolean getShowMedia() {
		return showMedia;
	}

	public void setShowMedia(Boolean showMedia) {
		this.showMedia = showMedia;
	}

	public Integer getDisplayTimeout() {
		return displayTimeout;
	}

	public void setDisplayTimeout(Integer displayTimeout) {
		this.displayTimeout = displayTimeout;
	}

	public Integer getGridRowHeight() {
		return gridRowHeight;
	}

	public void setGridRowHeight(Integer gridRowHeight) {
		this.gridRowHeight = gridRowHeight;
	}

	public Integer getTrainNoWidth() {
		return trainNoWidth;
	}

	public void setTrainNoWidth(Integer trainNoWidth) {
		this.trainNoWidth = trainNoWidth;
	}

	public Integer getExpectedTimeWidth() {
		return expectedTimeWidth;
	}

	public void setExpectedTimeWidth(Integer expectedTimeWidth) {
		this.expectedTimeWidth = expectedTimeWidth;
	}

	public Integer getArrivalDepartureWidth() {
		return arrivalDepartureWidth;
	}

	public void setArrivalDepartureWidth(Integer arrivalDepartureWidth) {
		this.arrivalDepartureWidth = arrivalDepartureWidth;
	}

	public Integer getPlatformWidth() {
		return platformWidth;
	}

	public void setPlatformWidth(Integer platformWidth) {
		this.platformWidth = platformWidth;
	}

	public Integer getGridPageTime() {
		return gridPageTime;
	}

	public void setGridPageTime(Integer gridPageTime) {
		this.gridPageTime = gridPageTime;
	}

	public Integer getMessageScrollSpeed() {
		return messageScrollSpeed;
	}

	public void setMessageScrollSpeed(Integer messageScrollSpeed) {
		this.messageScrollSpeed = messageScrollSpeed;
	}

	public Integer getMediaStartIntervalTime() {
		return mediaStartIntervalTime;
	}

	public void setMediaStartIntervalTime(Integer mediaStartIntervalTime) {
		this.mediaStartIntervalTime = mediaStartIntervalTime;
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
		return "Display [id=" + id + ", displayType=" + displayType + ", enableDisplay=" + enableDisplay
				+ ", showMessage=" + showMessage + ", showCoach=" + showCoach + ", showMedia=" + showMedia
				+ ", displayTimeout=" + displayTimeout + ", gridRowHeight=" + gridRowHeight + ", trainNoWidth="
				+ trainNoWidth + ", expectedTimeWidth=" + expectedTimeWidth + ", arrivalDepartureWidth="
				+ arrivalDepartureWidth + ", platformWidth=" + platformWidth + ", gridPageTime=" + gridPageTime
				+ ", messageScrollSpeed=" + messageScrollSpeed + ", mediaStartIntervalTime=" + mediaStartIntervalTime
				+ ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}

	
}
