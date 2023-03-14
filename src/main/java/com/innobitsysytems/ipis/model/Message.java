/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Message Model
 */
package com.innobitsysytems.ipis.model;

import java.util.Date;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="message")
@EntityListeners(AuditingEntityListener.class)
public class Message {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@NotBlank(message = "Display Board is mandatory")
	@Column(name = "displayBoard")
    private String displayBoard;
	
	@NotBlank(message = "Message in English is mandatory")
	@Column(name = "messageEnglish")
    private  String messageEnglish;
	
	@Column(name = "messageRegional")
    private  String messageRegional;
	
	@Column(name = "messageHindi")
    private  String messageHindi;
    
	@NotBlank(message = "PlatformNo is mandatory")
    @Column(name = "platformNo")
    private  String platformNo;
    
	@NotBlank(message = "Device Id is mandatory")
    @Column(name = "deviceId")
    private  String deviceId;
    
	@NotBlank(message = "Speed is mandatory")
    @Column(name = "speed")
    private  String speed;
    
	@NotBlank(message = "Message Effect is mandatory")
    @Column(name = "messageEffect")
    private  String messageEffect;
	
	//@NotBlank(message = "Message LetterSize is mandatory")
    @Column(name = "letterSize")
    private  String letterSize;
	
	//@NotBlank(message = "Message Gap is mandatory")
    @Column(name = "characterGap")
    private String characterGap;
	
	//@NotBlank(message = "Message PageChangeTime is mandatory")
    @Column(name = "PageChangeTime")
    private String PageChangeTime;
	
	@Column(name = "displayStatus")
    private  Boolean displayStatus;
    
    @CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt", nullable = false)
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

	public String getDisplayBoard() {
		return displayBoard;
	}

	public void setDisplayBoard(String displayBoard) {
		this.displayBoard = displayBoard;
	}

	public String getMessageEnglish() {
		return messageEnglish;
	}

	public void setMessageEnglish(String messageEnglish) {
		this.messageEnglish = messageEnglish;
	}

	public String getMessageRegional() {
		return messageRegional;
	}

	public void setMessageRegional(String messageRegional) {
		this.messageRegional = messageRegional;
	}

	public String getMessageHindi() {
		return messageHindi;
	}

	public void setMessageHindi(String messageHindi) {
		this.messageHindi = messageHindi;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public String getDeviceId() {
		return deviceId;
	}

	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getMessageEffect() {
		return messageEffect;
	}

	public void setMessageEffect(String messageEffect) {
		this.messageEffect = messageEffect;
	}

	 
	
	
	
	public String getLetterSize() {
		return letterSize;
	}

	public void setLetterSize(String letterSize) {
		this.letterSize = letterSize;
	}

	public String getCharacterGap() {
		return characterGap;
	}

	public void setCharacterGap(String characterGap) {
		this.characterGap = characterGap;
	}

	public String getPageChangeTime() {
		return PageChangeTime;
	}

	public void setPageChangeTime(String pageChangeTime) {
		PageChangeTime = pageChangeTime;
	}

	public Boolean getDisplayStatus() {
		return displayStatus;
	}

	public void setDisplayStatus(Boolean displayStatus) {
		this.displayStatus = displayStatus;
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

	@Override
	public String toString() {
		return "Message [id=" + id + ", displayBoard=" + displayBoard + ", messageEnglish=" + messageEnglish
				+ ", messageRegional=" + messageRegional + ", messageHindi=" + messageHindi + ", platformNo="
				+ platformNo + ", deviceId=" + deviceId + ", speed=" + speed + ", messageEffect=" + messageEffect
				+ ", letterSize=" + letterSize + ", characterGap=" + characterGap + ", PageChangeTime=" + PageChangeTime
				+ ", displayStatus=" + displayStatus + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}

	

	
	
}
