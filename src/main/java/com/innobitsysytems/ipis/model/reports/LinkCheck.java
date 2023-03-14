/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Link Check Model
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
@Table(name = "linkcheckreports")
@EntityListeners(AuditingEntityListener.class)
public class LinkCheck {

	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId",referencedColumnName = "id",nullable = false)
	@Fetch(FetchMode.JOIN)
	private User user;
	
	@Column(name = "deviceName")
    private String deviceName;
	
	@Column(name = "ipAddress", nullable = false)
    private String ipAddress;
	
	@Column(name = "deviceType", nullable = false)
    private String deviceType;
	
	@Column(name = "status", nullable = false)
    private String status;
	
	@Column(name = "port", nullable = false)
    private String port;
	
	@Column(name = "linkTime" , nullable = false)
	private String linkTime;
	
	@Column(name = "responseTime", nullable = false)
	private String responseTime;
	
	@Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime localDateTime;
	
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

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getLinkTime() {
		return linkTime;
	}

	public void setLinkTime(String linkTime) {
		this.linkTime = linkTime;
	}

	public String getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(String responseTime) {
		this.responseTime = responseTime;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
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
		return "LinkCheck [id=" + id + ", user=" + user + ", deviceName=" + deviceName + ", ipAddress=" + ipAddress
				+ ", deviceType=" + deviceType + ", status=" + status + ", port=" + port + ", linkTime=" + linkTime
				+ ", responseTime=" + responseTime + ", localDateTime=" + localDateTime + ", createdAt=" + createdAt
				+ "]";
	}

	
	
	
}
