/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Model
 */
package com.innobitsysytems.ipis.model.reports;

import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.User;

@Entity
@Table(name = "announcementreports")
@EntityListeners(AuditingEntityListener.class)
public class Announcement {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId",referencedColumnName = "id",nullable = false)
	@Fetch(FetchMode.JOIN)
	private User user;
	
	@Column(name = "announcementType", nullable = false)
    private String announcementType;
	
	@Column(name = "announcementMessage")
    private String announcementMessage;
	
	
	@Column(name = "announcementTime", columnDefinition = "TIMESTAMP")
	private LocalDateTime announcementTime;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
    private  Date createdAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
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
		return "Announcement [id=" + id + ", user=" + user + ", announcementType=" + announcementType
				+ ", announcementMessage=" + announcementMessage + ", announcementTime=" + announcementTime
				+ ", createdAt=" + createdAt + "]";
	}
	
	
}
