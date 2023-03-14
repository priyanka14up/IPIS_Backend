
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
	@Table(name = "GetIntensity")
	@EntityListeners(AuditingEntityListener.class)
	public class GetIntensity {

		@Id
		@GeneratedValue(strategy = GenerationType.AUTO)
		private Long id;

		@Column(name = "intensityValue")
		private Integer intensityValue;

		@Column(name = "Device")
		private String device;

		@Column(name = "platform")
		private String platform;

		@Column(name ="deviceId",columnDefinition = "text", nullable = true)
		private String deviceId;

		

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



		public String getDeviceId() {
			return deviceId;
		}

		public void setDeviceId(String deviceId) {
			this.deviceId = deviceId;
		}

		@Override
		public String toString() {
			return "GetIntensity [id=" + id + ", intensityValue=" + intensityValue + ", device=" + device
					+ ", platform=" + platform + ", deviceId=" + deviceId + "]";
		}

	}


