/**
 * Name: Kaustubh Garg
 * Copyright: Innobit Systems, 2021
 * Purpose: Login Dto
 */
package com.innobitsysytems.ipis.dto;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class LoginDto {

	private String firstName;
	private String lastName;
	private String role;
	private LocalDateTime loginDateTime;
	private LocalDateTime logoutDateTime;
	private List<String> activities;
	private  Date createdAt;
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public LocalDateTime getLoginDateTime() {
		return loginDateTime;
	}
	public void setLoginDateTime(LocalDateTime loginDateTime) {
		this.loginDateTime = loginDateTime;
	}
	public LocalDateTime getLogoutDateTime() {
		return logoutDateTime;
	}
	public void setLogoutDateTime(LocalDateTime logoutDateTime) {
		this.logoutDateTime = logoutDateTime;
	}
	public List<String> getActivities() {
		return activities;
	}
	public void setActivities(List<String> activities) {
		this.activities = activities;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public LoginDto(String firstName, String lastName, String role, LocalDateTime loginDateTime,
			LocalDateTime logoutDateTime, List<String> activities, Date createdAt) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.role = role;
		this.loginDateTime = loginDateTime;
		this.logoutDateTime = logoutDateTime;
		this.activities = activities;
		this.createdAt = createdAt;
	}
	@Override
	public String toString() {
		return "LoginDto [firstName=" + firstName + ", lastName=" + lastName + ", role=" + role + ", loginDateTime="
				+ loginDateTime + ", logoutDateTime=" + logoutDateTime + ", activities=" + activities + ", createdAt="
				+ createdAt + "]";
	}
	
}
