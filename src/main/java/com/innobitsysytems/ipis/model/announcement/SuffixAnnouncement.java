/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Suffix Announcement Model
 */
package com.innobitsysytems.ipis.model.announcement;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="suffixAnnouncement")
@EntityListeners(AuditingEntityListener.class)

public class SuffixAnnouncement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@NotBlank(message = "English Audio File is mandatory")
	@Column(name = "englishAudioFile")
    private String englishAudioFile;
	
	@NotBlank(message = "Hindi Audio File is mandatory")
	@Column(name = "hindiAudioFile")
    private  String hindiAudioFile;
	
	@NotBlank(message = "Regional Audio File is mandatory")
	@Column(name = "regionalAudioFile")
    private  String regionalAudioFile;
	
	 @CreationTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @Column(name = "createdAt", nullable = false)
	 private Date createdAt;

	 @Column(name = "createdBy", nullable = false)
	 @CreatedBy
	 private String createdBy;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEnglishAudioFile() {
		return englishAudioFile;
	}

	public void setEnglishAudioFile(String englishAudioFile) {
		this.englishAudioFile = englishAudioFile;
	}

	public String getHindiAudioFile() {
		return hindiAudioFile;
	}

	public void setHindiAudioFile(String hindiAudioFile) {
		this.hindiAudioFile = hindiAudioFile;
	}

	public String getRegionalAudioFile() {
		return regionalAudioFile;
	}

	public void setRegionalAudioFile(String regionalAudioFile) {
		this.regionalAudioFile = regionalAudioFile;
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

	@Override
	public String toString() {
		return "SuffixAnnouncement [id=" + id + ", englishAudioFile=" + englishAudioFile + ", hindiAudioFile="
				+ hindiAudioFile + ", regionalAudioFile=" + regionalAudioFile + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + "]";
	}
	 
	 

}
