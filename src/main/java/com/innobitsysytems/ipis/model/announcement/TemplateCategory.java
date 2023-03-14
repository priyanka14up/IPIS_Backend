/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Template Category Model
 */
package com.innobitsysytems.ipis.model.announcement;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="templateCategory")
@EntityListeners(AuditingEntityListener.class)

public class TemplateCategory {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@NotBlank(message = "Template Number is mandatory")
	@Column(name = "templateNumber")
    private String templateNumber;
	
	@NotBlank(message = "Template Desc is mandatory")
	@Column(name = "templateDesc")
    private  String templateDesc;
	
	@NotBlank(message = "Message Type is mandatory")
	@Column(name = "messageType")
    private  String messageType;
	
	@NotBlank(message = "message is mandatory")
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

	public String getTemplateNumber() {
		return templateNumber;
	}

	public void setTemplateNumber(String templateNumber) {
		this.templateNumber = templateNumber;
	}

	public String getTemplateDesc() {
		return templateDesc;
	}

	public void setTemplateDesc(String templateDesc) {
		this.templateDesc = templateDesc;
	}

	public String getMessageType() {
		return messageType;
	}

	public void setMessageType(String messageType) {
		this.messageType = messageType;
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
		return "TemplateCategory [id=" + id + ", templateNumber=" + templateNumber + ", templateDesc=" + templateDesc
				+ ", messageType=" + messageType + ", message=" + message + ", createdAt=" + createdAt + ", createdBy="
				+ createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	 
	 

}
