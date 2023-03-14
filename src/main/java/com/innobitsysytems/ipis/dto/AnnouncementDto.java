/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Dto
 */
package com.innobitsysytems.ipis.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class AnnouncementDto {

	 private String announcementType;
	 private String announcementMessage;
	 private LocalDateTime announcementTime;
	 private String firstname;
	 private String lastname;
	 private  Date createdAt;
	 
	public AnnouncementDto(String announcementType, String announcementMessage, LocalDateTime announcementTime,
			String firstname, String lastname, Date createdAt) {
		super();
		this.announcementType = announcementType;
		this.announcementMessage = announcementMessage;
		this.announcementTime = announcementTime;
		this.firstname = firstname;
		this.lastname = lastname;
		this.createdAt = createdAt;
	
	}

	public String getAnnouncementType() {
		return announcementType;
	}

	public void setAnnouncementType(String announcementType) {
		this.announcementType = announcementType;
	}

	public String getAnnouncementMessage() {
		return announcementMessage;
	}

	public void setAnnouncementMessage(String announcementMessage) {
		this.announcementMessage = announcementMessage;
	}

	public LocalDateTime getAnnouncementTime() {
		return announcementTime;
	}

	public void setAnnouncementTime(LocalDateTime announcementTime) {
		this.announcementTime = announcementTime;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "AnnouncementDto [announcementType=" + announcementType + ", announcementMessage=" + announcementMessage
				+ ", announcementTime=" + announcementTime + ", firstname=" + firstname + ", lastname=" + lastname
				+ ", createdAt=" + createdAt + "]";
	}
	 
	 
	 
	
	
}
