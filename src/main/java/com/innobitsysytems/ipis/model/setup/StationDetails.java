/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Details Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="stationdetails")
@EntityListeners(AuditingEntityListener.class)
public class StationDetails {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@NotBlank(message = "Station Name is mandatory")
	@Column(name = "stationName", nullable = false)
    private String stationName;
	
	@NotBlank(message = "Division Name is mandatory")
	@Column(name = "divisionName", nullable = false)
    private String divisionName;
	
	@NotBlank(message = "Region Name is mandatory")
	@Column(name = "regionName", nullable = false)
    private String regionName;
	
	@NotBlank(message = "Station Code is mandatory")
	@Column(name = "stationCode", nullable = false)
    private String stationCode;
	
	@NotBlank(message = "Division Code is mandatory")
	@Column(name = "divisionCode", nullable = false)
    private String divisionCode;
	
	@NotBlank(message = "Region Code is mandatory")
	@Column(name = "regionCode", nullable = false)
    private String regionCode;
	
	@NotBlank(message = "NorthEastEnd is mandatory")
	@Column(name = "northEastEnd", nullable = false)
    private String northEastEnd;
	
	@NotBlank(message = "SouthWestEnd is mandatory")
	@Column(name = "southWestEnd", nullable = false)
    private String southWestEnd;
	
	@Column(name = "autoLoadTrain")
    private Boolean autoLoadTrain;
	
	@Column(name = "autoDelete")
    private Boolean autoDelete;
	
	@NotNull(message = "AutoLoadTrainEveryMin is mandatory")
	@Column(name = "autoLoadTrainEveryMin")
    private Integer autoLoadTrainEveryMin;
	
//	@NotNull(message = "AutoDeleteTrainEveryMin is mandatory")
	@Column(name = "autoDeleteTrainEveryMin",nullable=true)
    private Integer autoDeleteTrainEveryMin;
	
//	@Column(name = "autoLoadEventIntervalTime")
//    private Integer autoLoadEventIntervalTime;
	
//	@Column(name = "manuallyGetTrainForNextHours")
//    private Integer manuallyGetTrainForNextHours;
	
	@Column(name = "autoSendDataTimeInterval")
    private Integer autoSendDataTimeInterval;
	
//	@Column(name = "autoDeleteTrainTimeInterval")
//    private Integer autoDeleteTrainTimeInterval;
	
	@NotNull(message = "Available Platforms is mandatory")
	@Column(name = "availablePlatforms")
    private Integer availablePlatforms;
	
	@NotNull(message = "List Of Platforms is mandatory")
	@Column(name = "listOfPlatforms", nullable = false,columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] listOfPlatforms;
	
	@Column(name = "enableIntegration", nullable = true)
    private Boolean enableIntegration;
	
//	@Enumerated(EnumType.STRING)
//	@Column(name = "typeOfIntegration")
//    private TypeOfIntegration typeOfIntegration;
	
	@Column(name = "fileLocation")
    private String fileLocation;
	
	@NotNull(message = "Languages is mandatory")
	@Column(name = "languages", nullable = false,columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] languages;
	
	@NotNull(message = "Announcement Preference is mandatory")
	@Column(name = "announcementPreference", nullable = false,columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] announcementPreference;
	
	@Column(name = "ntesUpdateEnable")
    private Boolean ntesUpdateEnable;
	
	@Column(name = "ntesUpdateTimeInMin")
    private String ntesUpdateTimeInMin;
	
	@Column(name = "ntesPortType")
    private String ntesPortType;
	
	@Column(name = "portNo")
    private String portNo;
	
//	@NotNull(message = "Device Schema is mandatory")
//	@Enumerated(EnumType.STRING)
//	@Column(name = "deviceSchema", nullable = false)
//    private DeviceSchema deviceSchema;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created_at", nullable = false)
    private Date createdAt;

    @Column(name = "created_by", nullable = false)
    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated_at", nullable = false)
    private Date updatedAt;

    @Column(name = "updated_by", nullable = true)
    @LastModifiedBy
    private String updatedBy;
    
    @Column(name = "autoTadd")
    private Boolean autoTadd;
    

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getStationName() {
		return stationName;
	}

	public void setStationName(String stationName) {
		this.stationName = stationName;
	}

	public String getDivisionName() {
		return divisionName;
	}

	public void setDivisionName(String divisionName) {
		this.divisionName = divisionName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
	}

	public String getStationCode() {
		return stationCode;
	}

	public void setStationCode(String stationCode) {
		this.stationCode = stationCode;
	}

	public String getDivisionCode() {
		return divisionCode;
	}

	public void setDivisionCode(String divisionCode) {
		this.divisionCode = divisionCode;
	}

	public String getRegionCode() {
		return regionCode;
	}

	public void setRegionCode(String regionCode) {
		this.regionCode = regionCode;
	}

	public String getNorthEastEnd() {
		return northEastEnd;
	}

	public void setNorthEastEnd(String northEastEnd) {
		this.northEastEnd = northEastEnd;
	}

	public String getSouthWestEnd() {
		return southWestEnd;
	}

	public void setSouthWestEnd(String southWestEnd) {
		this.southWestEnd = southWestEnd;
	}

	public Boolean getAutoLoadTrain() {
		return autoLoadTrain;
	}

	public void setAutoLoadTrain(Boolean autoLoadTrain) {
		this.autoLoadTrain = autoLoadTrain;
	}

	public Boolean getAutoDelete() {
		return autoDelete;
	}

	public void setAutoDelete(Boolean autoDelete) {
		this.autoDelete = autoDelete;
	}

	public Integer getAutoLoadTrainEveryMin() {
		return autoLoadTrainEveryMin;
	}

	public void setAutoLoadTrainEveryMin(Integer autoLoadTrainEveryMin) {
		this.autoLoadTrainEveryMin = autoLoadTrainEveryMin;
	}

	public Integer getAutoDeleteTrainEveryMin() {
		return autoDeleteTrainEveryMin;
	}

	public void setAutoDeleteTrainEveryMin(Integer autoDeleteTrainEveryMin) {
		this.autoDeleteTrainEveryMin = autoDeleteTrainEveryMin;
	}

//	public Integer getAutoLoadEventIntervalTime() {
//		return autoLoadEventIntervalTime;
//	}

//	public void setAutoLoadEventIntervalTime(Integer autoLoadEventIntervalTime) {
//		this.autoLoadEventIntervalTime = autoLoadEventIntervalTime;
//	}

//	public Integer getManuallyGetTrainForNextHours() {
//		return manuallyGetTrainForNextHours;
//	}

//	public void setManuallyGetTrainForNextHours(Integer manuallyGetTrainForNextHours) {
//		this.manuallyGetTrainForNextHours = manuallyGetTrainForNextHours;
//	}

	

//	public Integer getAutoDeleteTrainTimeInterval() {
//		return autoDeleteTrainTimeInterval;
//	}

//	public void setAutoDeleteTrainTimeInterval(Integer autoDeleteTrainTimeInterval) {
//		this.autoDeleteTrainTimeInterval = autoDeleteTrainTimeInterval;
//	}

	public Integer getAvailablePlatforms() {
		return availablePlatforms;
	}

	public void setAvailablePlatforms(Integer availablePlatforms) {
		this.availablePlatforms = availablePlatforms;
	}

	public String[] getListOfPlatforms() {
		return listOfPlatforms;
	}

	public void setListOfPlatforms(String[] listOfPlatforms) {
		this.listOfPlatforms = listOfPlatforms;
	}

	public Boolean getEnableIntegration() {
		return enableIntegration;
	}

	public void setEnableIntegration(Boolean enableIntegration) {
		this.enableIntegration = enableIntegration;
	}

	

	public String getFileLocation() {
		return fileLocation;
	}

	public void setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
	}

	public String[] getLanguages() {
		return languages;
	}

	public void setLanguages(String[] languages) {
		this.languages = languages;
	}

	public String[] getAnnouncementPreference() {
		return announcementPreference;
	}

	public void setAnnouncementPreference(String[] announcementPreference) {
		this.announcementPreference = announcementPreference;
	}

	public Boolean getNtesUpdateEnable() {
		return ntesUpdateEnable;
	}

	public void setNtesUpdateEnable(Boolean ntesUpdateEnable) {
		this.ntesUpdateEnable = ntesUpdateEnable;
	}

	public String getNtesUpdateTimeInMin() {
		return ntesUpdateTimeInMin;
	}

	public void setNtesUpdateTimeInMin(String ntesUpdateTimeInMin) {
		this.ntesUpdateTimeInMin = ntesUpdateTimeInMin;
	}

	public String getNtesPortType() {
		return ntesPortType;
	}

	public void setNtesPortType(String ntesPortType) {
		this.ntesPortType = ntesPortType;
	}

	public String getPortNo() {
		return portNo;
	}

	public void setPortNo(String portNo) {
		this.portNo = portNo;
	}



	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
	
	public Boolean getAutoTadd() {
		return autoTadd;
	}

	public void setAutoTadd(Boolean autoTadd) {
		this.autoTadd = autoTadd;
	}
	
	
	public Integer getAutoSendDataTimeInterval() {
		return autoSendDataTimeInterval;
	}

	public void setAutoSendDataTimeInterval(Integer autoSendDataTimeInterval) {
		this.autoSendDataTimeInterval = autoSendDataTimeInterval;
	}

	@Override
	public String toString() {
		return "StationDetails [id=" + id + ", stationName=" + stationName + ", divisionName=" + divisionName
				+ ", regionName=" + regionName + ", stationCode=" + stationCode + ", divisionCode=" + divisionCode
				+ ", regionCode=" + regionCode + ", northEastEnd=" + northEastEnd + ", southWestEnd=" + southWestEnd
				+ ", autoLoadTrain=" + autoLoadTrain + ", autoDelete=" + autoDelete + ", autoLoadTrainEveryMin="
				+ autoLoadTrainEveryMin + ", autoDeleteTrainEveryMin=" + autoDeleteTrainEveryMin
				+ ", autoSendDataTimeInterval=" + autoSendDataTimeInterval + ", availablePlatforms="
				+ availablePlatforms + ", listOfPlatforms=" + Arrays.toString(listOfPlatforms) + ", enableIntegration="
				+ enableIntegration + ", fileLocation=" + fileLocation + ", languages=" + Arrays.toString(languages)
				+ ", announcementPreference=" + Arrays.toString(announcementPreference) + ", ntesUpdateEnable="
				+ ntesUpdateEnable + ", ntesUpdateTimeInMin=" + ntesUpdateTimeInMin + ", ntesPortType=" + ntesPortType
				+ ", portNo=" + portNo + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt="
				+ updatedAt + ", updatedBy=" + updatedBy + ", autoTadd=" + autoTadd + "]";
	}
	
}
