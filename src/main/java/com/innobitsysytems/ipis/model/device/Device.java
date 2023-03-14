/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Device Model
 */
package com.innobitsysytems.ipis.model.device;

import java.util.*;
import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.HashMapParent;
import com.innobitsysytems.ipis.model.LinkStatus;

@Entity
@Table(name="devices")
@EntityListeners(AuditingEntityListener.class)
public class Device {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@OneToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "linkStatusId", referencedColumnName = "id")
	private LinkStatus linkStatus;
	
	@Column(name = "ipAddress")
    private String ipAddress;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "deviceType")
    private DeviceType deviceType;
	
	@Column(name = "platformNo",columnDefinition = "text[]", nullable = true)
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private  String[] platformNo;
	
	@Column(columnDefinition = "TEXT")
	@Convert(converter = HashMapParent.class)
    private List<ChildrenDetails> children;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "status")
    private  DeviceStatus status;
	
    @Column(name = "deviceName")
    private  String deviceName;
    
   
    @Column(name = "portNumber")
    private  String portNumber;
    
    @Column(name = "boardType")
    private  String boardType;
    
    @Column(name = "noOfLines")
    private  Integer noOfLines;
    
    @Column(name = "messageLine")
    private  String messageLine;
    
    @Column(name = "enableMsgLine",columnDefinition = "text[]", nullable = true)
    @Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private  String[] enableMsgLine;
    
    @Column(name = "fobIndicatorPosition")
    private  Integer fobIndicatorPosition;
    
    @Column(name = "noOfCoaches")
    private  Integer noOfCoaches;
    
    @Column(name = "startId")
    private  Integer startId;
    
    @Column(name = "englishInfoDisplay")
    private  Boolean englishInfoDisplay;
    
    @Column(name = "hindiInfoDisplay")
    private  Boolean hindiInfoDisplay;
    
    @Column(columnDefinition = "TEXT")
	@Convert(converter = HashMapCoachDetails.class)
    private List<CoachesDetail> coaches ;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "createdBy", nullable = false)
    @CreatedBy
    private String createdBy;

    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt", nullable = false)
    private Date updatedAt;

    @Column(name = "updatedBy", nullable = true)
    @LastModifiedBy
    private String updatedBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public LinkStatus getLinkStatus() {
		return linkStatus;
	}

	public void setLinkStatus(LinkStatus linkStatus) {
		this.linkStatus = linkStatus;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public String[] getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String[] platformNo) {
		this.platformNo = platformNo;
	}

	public List<ChildrenDetails> getChildren() {
		return children;
	}

	public void setChildren(List<ChildrenDetails> children) {
		this.children = children;
	}

	public DeviceStatus getStatus() {
		return status;
	}

	public void setStatus(DeviceStatus status) {
		this.status = status;
	}

	public String getDeviceName() {
		return deviceName;
	}

	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public Integer getNoOfLines() {
		return noOfLines;
	}

	public void setNoOfLines(Integer noOfLines) {
		this.noOfLines = noOfLines;
	}

	public String getMessageLine() {
		return messageLine;
	}

	public void setMessageLine(String messageLine) {
		this.messageLine = messageLine;
	}

	public String[] getEnableMsgLine() {
		return enableMsgLine;
	}

	public void setEnableMsgLine(String[] enableMsgLine) {
		this.enableMsgLine = enableMsgLine;
	}

	public Integer getFobIndicatorPosition() {
		return fobIndicatorPosition;
	}

	public void setFobIndicatorPosition(Integer fobIndicatorPosition) {
		this.fobIndicatorPosition = fobIndicatorPosition;
	}

	public Integer getNoOfCoaches() {
		return noOfCoaches;
	}

	public void setNoOfCoaches(Integer noOfCoaches) {
		this.noOfCoaches = noOfCoaches;
	}

	public Integer getStartId() {
		return startId;
	}

	public void setStartId(Integer startId) {
		this.startId = startId;
	}

	public Boolean getEnglishInfoDisplay() {
		return englishInfoDisplay;
	}

	public void setEnglishInfoDisplay(Boolean englishInfoDisplay) {
		this.englishInfoDisplay = englishInfoDisplay;
	}

	public Boolean getHindiInfoDisplay() {
		return hindiInfoDisplay;
	}

	public void setHindiInfoDisplay(Boolean hindiInfoDisplay) {
		this.hindiInfoDisplay = hindiInfoDisplay;
	}

	public List<CoachesDetail> getCoaches() {
		return coaches;
	}

	public void setCoaches(List<CoachesDetail> coaches) {
		this.coaches = coaches;
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

//	@Override
//	public String toString() {
//		return "Device [id=" + id + ", linkStatus=" + linkStatus + ", ipAddress=" + ipAddress + ", deviceType="
//				+ deviceType + ", platformNo=" + Arrays.toString(platformNo) + ", children=" + children + ", status="
//				+ status + ", deviceName=" + deviceName + ", portNumber=" + portNumber + ", boardType=" + boardType
//				+ ", noOfLines=" + noOfLines + ", messageLine=" + messageLine + ", enableMsgLine="
//				+ Arrays.toString(enableMsgLine) + ", fobIndicatorPosition=" + fobIndicatorPosition + ", noOfCoaches="
//				+ noOfCoaches + ", startId=" + startId + ", englishInfoDisplay=" + englishInfoDisplay
//				+ ", hindiInfoDisplay=" + hindiInfoDisplay + ", coaches=" + coaches + ", createdAt=" + createdAt
//				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
//	}





	
	}