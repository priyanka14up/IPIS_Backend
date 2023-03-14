/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Create User
 */
package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;

public class CreateUser {

	@NotBlank(message="first name should not be blank")
	private String firstname;
	@NotBlank(message="lastname should not be blank")
	private String lastname;
	@NotBlank(message="email should not be blank")
	private String email;
	@NotBlank(message="mobileNo should not be blank")
	private String mobileNo;
	@NotBlank(message="role should not be blank")
	private String role;
	@NotBlank(message="password should not be blank")
	private String password;
	private String createdBy;
	
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
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getCreatedBy() {
		return createdBy;
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	
	
}
