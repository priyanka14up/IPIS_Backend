/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Intensity Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "IntensitySetting")
@EntityListeners(AuditingEntityListener.class)
public class Intensity {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name = "intensityValue")
	private Integer intensityValue;

	@Column(name = "dayIntensity")
	private Integer dayIntensity;

	@Column(name = "dayStartTime")
	private String dayStartTime;

	@Column(name = "nightIntensity")
	private Integer nightIntensity;

	@Column(name = "nightStartTime")
	private String nightStartTime;

	@Column(name = "intensityMode")
	private String intensityMode;

	@Column(name = "Device")
	private String device;

	@Column(name = "platform")
	private String platform;

	@Column(name = "mode")
	private String mode;
	
	@Column(name ="deviceId",columnDefinition = "text", nullable = true)
	private String deviceId;

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdAt", nullable = false)
	private Date createdAt;

	@Column(name = "createdBy", nullable = false)
	@CreatedBy
	private int createdBy;

	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updatedAt", nullable = true)
	private Date updatedAt;

	@Column(name = "updatedBy", nullable = true)
	@LastModifiedBy
	private int updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getIntensityValue() {
		return intensityValue;
	}

	public void setIntensityValue(Integer intensityValue) {
		this.intensityValue = intensityValue;
	}

	public Integer getDayIntensity() {
		return dayIntensity;
	}

	public void setDayIntensity(Integer dayIntensity) {
		this.dayIntensity = dayIntensity;
	}

	public String getDayStartTime() {
		return dayStartTime;
	}

	public void setDayStartTime(String dayStartTime) {
		this.dayStartTime = dayStartTime;
	}

	public Integer getNightIntensity() {
		return nightIntensity;
	}

	public void setNightIntensity(Integer nightIntensity) {
		this.nightIntensity = nightIntensity;
	}

	public String getNightStartTime() {
		return nightStartTime;
	}

	public void setNightStartTime(String nightStartTime) {
		this.nightStartTime = nightStartTime;
	}

	public String getIntensityMode() {
		return intensityMode;
	}

	public void setIntensityMode(String intensityMode) {
		this.intensityMode = intensityMode;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public int getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(int createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public int getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(int updatedBy) {
		this.updatedBy = updatedBy;
	}

	@Override
	public String toString() {
		return "Intensity [id=" + id + ", intensityValue=" + intensityValue + ", dayIntensity=" + dayIntensity
				+ ", dayStartTime=" + dayStartTime + ", nightIntensity=" + nightIntensity + ", nightStartTime="
				+ nightStartTime + ", intensityMode=" + intensityMode + ", device=" + device + ", platform=" + platform
				+ ", mode=" + mode + ", deviceId=" + deviceId + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}

}
