/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: User List Model
 */
package com.innobitsysytems.ipis.model.reports;

import javax.validation.constraints.NotBlank;

public class UserList {
	
	@NotBlank
	private Integer id;
	
	@NotBlank
	private String firstname;
	
	@NotBlank
	private String lastname;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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

	@Override
	public String toString() {
		return "UserList [id=" + id + ", firstname=" + firstname + ", lastname=" + lastname + "]";
	}
	
	

}
