/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Media Queue Dto
 */
package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;

public class MediaQueueDto {
	
	private Long id;
	
	@NotBlank(message = "Media Location is mandatory")
	private String mediaLocation;
	
	private Boolean isPlaying;
	
	@NotBlank(message = "Media Name is mandatory")
	private String mediaName;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getMediaLocation() {
		return mediaLocation;
	}

	public void setMediaLocation(String mediaLocation) {
		this.mediaLocation = mediaLocation;
	}

	public Boolean getIsPlaying() {
		return isPlaying;
	}

	public void setIsPlaying(Boolean isPlaying) {
		this.isPlaying = isPlaying;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
	}

	@Override
	public String toString() {
		return "MediaQueueDto [id=" + id + ", mediaLocation=" + mediaLocation + ", isPlaying=" + isPlaying
				+ ", mediaName=" + mediaName + "]";
	}
	
	

}
