
/**
 * Name: Priyanka Upadhyay
 * Copyright: Innobit Systems, 2021
 * Purpose: TrainNo Prefix Announcements Model
 */
package com.innobitsysytems.ipis.model.announcement;
import java.util.Date;

import javax.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="chimesAnnouncement")
@EntityListeners(AuditingEntityListener.class)

public class ChimesAnnouncement {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	
	@Column(name = "audioFile")
    private  String audioFile;
	
	 @CreationTimestamp
	 @Temporal(TemporalType.TIMESTAMP)
	 @GeneratedValue(strategy = GenerationType.AUTO)
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

	public String getAudioFile() {
		return audioFile;
	}

	public void setAudioFile(String audioFile) {
		this.audioFile = audioFile;
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
		return "ChimesAnnouncement [id=" + id + ", audioFile=" + audioFile + ", createdAt=" + createdAt + ", createdBy="
				+ createdBy + "]";
	}

}