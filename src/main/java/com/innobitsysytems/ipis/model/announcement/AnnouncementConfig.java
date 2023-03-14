/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Configuration Model
 */
package com.innobitsysytems.ipis.model.announcement;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="announcementConfig")
@EntityListeners(AuditingEntityListener.class)
public class AnnouncementConfig {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@NotNull(message = "Repeat Count is mandatory")
	@Column(name = "repeatCount")
    private Integer repeatCount;
	
	@Column(name = "frequency")
    private Integer frequency;
	
	@Column(name = "languageOrder")
    private  String languageOrder;
	
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

	public Integer getRepeatCount() {
		return repeatCount;
	}

	public void setRepeatCount(Integer repeatCount) {
		this.repeatCount = repeatCount;
	}

	public Integer getFrequency() {
		return frequency;
	}

	public void setFrequency(Integer frequency) {
		this.frequency = frequency;
	}

	public String getLanguageOrder() {
		return languageOrder;
	}

	public void setLanguageOrder(String languageOrder) {
		this.languageOrder = languageOrder;
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
		return "AnnouncementConfig [id=" + id + ", repeatCount=" + repeatCount + ", frequency=" + frequency
				+ ", languageOrder=" + languageOrder + ", createdAt=" + createdAt + ", createdBy=" + createdBy + "]";
	}
	
	
	 

}
