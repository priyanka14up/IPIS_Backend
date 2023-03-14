/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Playlist Dto
 */
package com.innobitsysytems.ipis.dto;

import javax.persistence.Column;
import javax.validation.constraints.NotBlank;


public class AnnouncementPlaylistDto {
	
	private Long id;
	 
	 @NotBlank(message = "File Name is mandatory")
	 private String fileName;
	 
	 private String fileLocation;
	 
	 
	 private  Long announcementId;
		
	public Long getAnnouncementId() {
		return announcementId;
	}



	public void setAnnouncementId(Long announcementId) {
		this.announcementId = announcementId;
	}



	public Long getId() {
		return id;
	}
	
	

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	@Override
	public String toString() {
		return "AnnouncementPlaylistDto [id=" + id + ", fileName=" + fileName + ", fileLocation=" + fileLocation
				+ ", announcementId=" + announcementId + "]";
	}

	

}
