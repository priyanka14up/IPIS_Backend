/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Public Announcement Dto
 */
package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;

public class PublicAnnouncementDto {
	
	 private Long id;
	 
	 @NotBlank(message = "File Name is mandatory")
	 private String fileName;
		
	 @NotBlank(message = "File Location is mandatory")
	 private  String fileLocation;
	 
	 private  String fileUrl;
	 
	 private boolean fileAdded;
		
	 @NotBlank(message = "Message Type is mandatory")
	 private  String messageType;

	public Long getId() {
		return id;
	}

	public boolean isFileAdded() {
		return fileAdded;
	}

	public void setFileAdded(boolean fileAdded) {
		this.fileAdded = fileAdded;
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

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Override
	public String toString() {
		return "PublicAnnouncementDto [id=" + id + ", fileName=" + fileName + ", fileLocation=" + fileLocation
				+ ", fileUrl=" + fileUrl + ", fileAdded=" + fileAdded + ", messageType=" + messageType + "]";
	}

	public Object findById(long l) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
