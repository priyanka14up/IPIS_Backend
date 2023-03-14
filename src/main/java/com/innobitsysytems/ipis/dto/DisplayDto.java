/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Dto
 */
package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class DisplayDto {
	
	private Long id;

	@NotBlank(message = "Display Type is mandatory")
	private String displayType;
	
	@NotNull(message = "Enable Display is mandatory")
	private Boolean enableDisplay;
	
	private Boolean showMessage;
	
	private Boolean showCoach;
	
	private Boolean showMedia;
	
	@NotNull(message = "Display Timeout is mandatory")
	private Integer displayTimeout;
	
	@NotNull(message = "Grid Row Height is mandatory")
	private Integer gridRowHeight;
	
	@NotNull(message = "trainNoWidth is mandatory")
	private Integer trainNoWidth;
	
	@NotNull(message = "Expected Time Width is mandatory")
	private Integer expectedTimeWidth;
	
	@NotNull(message = "Arrival Departure Width is mandatory")
	private Integer arrivalDepartureWidth;
	
	@NotNull(message = "Platform Width is mandatory")
	private Integer platformWidth;
	
	@NotNull(message = "Grid Page Time is mandatory")
	private Integer gridPageTime;
	
	@NotNull(message = "Message Scroll Speed is mandatory")
	private Integer messageScrollSpeed;
	
	@NotNull(message = "Media Start Interval Time is mandatory")
	private Integer mediaStartIntervalTime;

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

	@Override
	public String toString() {
		return "DisplayDto [id=" + id + ", displayType=" + displayType + ", enableDisplay=" + enableDisplay
				+ ", showMessage=" + showMessage + ", showCoach=" + showCoach + ", showMedia=" + showMedia
				+ ", displayTimeout=" + displayTimeout + ", gridRowHeight=" + gridRowHeight + ", trainNoWidth="
				+ trainNoWidth + ", expectedTimeWidth=" + expectedTimeWidth + ", arrivalDepartureWidth="
				+ arrivalDepartureWidth + ", platformWidth=" + platformWidth + ", gridPageTime=" + gridPageTime
				+ ", messageScrollSpeed=" + messageScrollSpeed + ", mediaStartIntervalTime=" + mediaStartIntervalTime
				+ "]";
	}

	
	
	

}
