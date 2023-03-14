/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: ItemList Model
 */
package com.innobitsysytems.ipis.model.announcement;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="itemList")
@EntityListeners(AuditingEntityListener.class)

public class ItemList {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "tempCategoryId", referencedColumnName = "id")
	private TemplateCategory templateCategory;
	
	@NotNull(message = "Template Id is mandatory")
	@Column(name = "templateId")
    private Integer templateId;
	
	@NotBlank(message = "itemNumber is mandatory")
	@Column(name = "itemNumber")
    private  String itemNumber;
	
	@NotBlank(message = "language is mandatory")
	@Column(name = "language")
    private  String language;
	
	@Column(name = "message")
    private  String message;
	
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

	public TemplateCategory getTemplateCategory() {
		return templateCategory;
	}

	public void setTemplateCategory(TemplateCategory templateCategory) {
		this.templateCategory = templateCategory;
	}

	public Integer getTemplateId() {
		return templateId;
	}

	public void setTemplateId(Integer templateId) {
		this.templateId = templateId;
	}

	public String getItemNumber() {
		return itemNumber;
	}

	public void setItemNumber(String itemNumber) {
		this.itemNumber = itemNumber;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
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
		return "ItemList [id=" + id + ", templateCategory=" + templateCategory + ", templateId=" + templateId
				+ ", itemNumber=" + itemNumber + ", language=" + language + ", message=" + message + ", createdAt="
				+ createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy
				+ "]";
	}
	 
	 


}
