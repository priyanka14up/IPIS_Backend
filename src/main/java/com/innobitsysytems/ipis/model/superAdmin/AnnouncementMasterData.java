/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Master Data Model
 */
package com.innobitsysytems.ipis.model.superAdmin;

import java.util.Date;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name ="announcementMasterData")
@EntityListeners(AuditingEntityListener.class)
public class AnnouncementMasterData {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name = "templatePageNumber" )
	private List<AnnouncementMasterPageDescription> announcementMasterPageDescription;
	
	@NotNull(message = "Template Number is mandatory")
	@Column(name = "templateNumber", unique=true)
	private Integer templateNumber;
	
	@NotBlank(message = "Message Type is mandatory")
	@Column(name = "messageType")
	private String messageType;
	
	@NotBlank(message = "Template Description is mandatory")
	@Column(name = "templateDescription")
	private String templateDescription;

	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createdAt")
    private Date createdAt;

    @Column(name = "createdBy", nullable = true)
    @CreatedBy
    private String createdBy;
    
    @UpdateTimestamp
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updatedAt")
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

	public Integer getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(Integer templateNumber) {
		this.templateNumber = templateNumber;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
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
		return "AnnouncementMasterData [id=" + id + ", templateNumber=" + templateNumber + ", messageType="
				+ messageType + ", templateDescription=" + templateDescription + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + ", getId()="
				+ getId() + ", getTemplateNumber()=" + getTemplateNumber() + ", getMessageType()=" + getMessageType()
				+ ", getTemplateDescription()=" + getTemplateDescription() + ", getCreatedAt()=" + getCreatedAt()
				+ ", getCreatedBy()=" + getCreatedBy() + ", getUpdatedAt()=" + getUpdatedAt() + ", getUpdatedBy()="
				+ getUpdatedBy() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	
    
}
