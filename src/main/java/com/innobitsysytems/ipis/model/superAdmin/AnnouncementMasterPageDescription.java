/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Master Page Description Model
 */
package com.innobitsysytems.ipis.model.superAdmin;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="announcementMasterPageDescription")
@EntityListeners(AuditingEntityListener.class)
public class AnnouncementMasterPageDescription {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;

	@Enumerated(EnumType.STRING)
	@Column(name="pageName")
	private PageName pageName;
	
//	@ManyToOne(cascade=CascadeType.ALL)
//	@JoinColumn(name = "templateNumber",referencedColumnName = "templateNumber")
//	private AnnouncementMasterData announcementMasterData;
	
	@Column(name = "pageNumber")
	private Integer pageNumber;

	@Column(name="pageDuration")
	private String pageDuration;
	
	@Column(name = "templateDescription")
	private String templateDescription;
	
	@Column(name = "messageDisplay")
	private String messageDisplay;

	@Column(name = "messageLanguage")
	private  String messageLanguage;

	@Column(name = "audioList", columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
	private  String[] audioList;

	@Column(name = "tags", columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
	private String[] tags;

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

	public PageName getPageName() {
		return pageName;
	}

	public void setPageName(PageName pageName) {
		this.pageName = pageName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}

	public String getTemplateDescription() {
		return templateDescription;
	}

	public void setTemplateDescription(String pageDescription) {
		this.templateDescription = pageDescription;
	}

	public String getMessageDisplay() {
		return messageDisplay;
	}

	public void setMessageDisplay(String messageDisplay) {
		this.messageDisplay = messageDisplay;
	}

	public String getPageDuration() {
		return pageDuration;
	}

	public void setPageDuration(String pageDuration) {
		this.pageDuration = pageDuration;
	}

	public String getMessageLanguage() {
		return messageLanguage;
	}

	public void setMessageLanguage(String messageLanguage) {
		this.messageLanguage = messageLanguage;
	}

	public String[] getAudioList() {
		return audioList;
	}

	public void setAudioList(String[] audioList) {
		this.audioList = audioList;
	}

	public String[] getTags() {
		return tags;
	}

	public void setTags(String[] tags) {
		this.tags = tags;
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
		return "AnnouncementMasterPageDescription [id=" + id + ", pageNumber=" + pageNumber + ", pageDescription="
				+ templateDescription + ", messageDisplay=" + messageDisplay + ", pageDuration=" + pageDuration
				+ ", pageName=" + pageName + ", messageLanguage=" + messageLanguage + ", audioList="
				+ Arrays.toString(audioList) + ", tags=" + Arrays.toString(tags) + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}



}
