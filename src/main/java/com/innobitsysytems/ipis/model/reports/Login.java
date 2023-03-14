/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Login Model
 */
package com.innobitsysytems.ipis.model.reports;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.User;

@Entity
@Table(name = "loginreports")
@EntityListeners(AuditingEntityListener.class)
public class Login {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@Column(name = "firstName", nullable = false)
    private String firstName;
	
	@Column(name = "lastName", nullable = false)
    private String lastName;
	
	@Column(name = "role", nullable = false)
    private String role;
	
	@Column(name = "loginDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime loginDateTime;
	
	@Column(name = "logoutDateTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime logoutDateTime;
	
    @Column(name = "activities", columnDefinition = "TEXT")
    @Convert(converter = StringListConverter.class)
    private List<String> activities;

	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
    private  Date createdAt;
	
	@ManyToOne( fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
	private User user;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

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

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Login [id=" + id + ", firstName=" + firstName + ", lastName=" + lastName + ", role=" + role
				+ ", loginDateTime=" + loginDateTime + ", logoutDateTime=" + logoutDateTime + ", activities="
				+ activities + ", createdAt=" + createdAt + ", user=" + user + "]";
	}

	
}
