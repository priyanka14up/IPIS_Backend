/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Default Messages Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Arrays;
import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="defaultmessages")
@EntityListeners(AuditingEntityListener.class)
public class DefaultMessages {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@NotBlank(message = "MLDB Default Message is mandatory")
	@Column(name = "mldbDefaultMessage", nullable = false)
    private String mldbDefaultMessage;
	
	@NotBlank(message = "PFD Default Message is mandatory")
	@Column(name = "pfdDefaultMessage", nullable = false)
    private String pfdDefaultMessage;
	
	@NotBlank(message = "IVD Default Message is mandatory")
	@Column(name = "ivdDefaultMessage", nullable = false)
    private String ivdDefaultMessage;
	
	@NotBlank(message = "OVD Default Message is mandatory")
	@Column(name = "ovdDefaultMessage", nullable = false)
    private String ovdDefaultMessage;
	
	public String getIvdDefaultMessage() {
		return ivdDefaultMessage;
	}

	public void setIvdDefaultMessage(String ivdDefaultMessage) {
		this.ivdDefaultMessage = ivdDefaultMessage;
	}

	public String getOvdDefaultMessage() {
		return ovdDefaultMessage;
	}

	public void setOvdDefaultMessage(String ovdDefaultMessage) {
		this.ovdDefaultMessage = ovdDefaultMessage;
	}

	public String getTvDisplayDefaultMessage() {
		return tvDisplayDefaultMessage;
	}

	public void setTvDisplayDefaultMessage(String tvDisplayDefaultMessage) {
		this.tvDisplayDefaultMessage = tvDisplayDefaultMessage;
	}

	@NotBlank(message = "TV Display Default Message is mandatory")
	@Column(name = "tvDisplayDefaultMessage", nullable = false)
    private String tvDisplayDefaultMessage;
	
	
	@NotBlank(message = "AGDB Default Message is mandatory")
	@Column(name = "agdbDefaultMessage", nullable = false)
    private String agdbDefaultMessage;
	
	@NotNull(message = "CGDB Default Message is mandatory")
	@Column(name = "cgdbDefaultMessage", nullable = false, columnDefinition = "text[]")
	@Type(type = "com.innobitsysytems.ipis.model.GenericArrayUserType")
    private String[] cgdbDefaultMessage;
	
	@NotNull(message = "Language is mandatory")
	@Column(name = "language", nullable = false)
    private String language;
	
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

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMldbDefaultMessage() {
		return mldbDefaultMessage;
	}

	public void setMldbDefaultMessage(String mldbDefaultMessage) {
		this.mldbDefaultMessage = mldbDefaultMessage;
	}

	public String getPfdDefaultMessage() {
		return pfdDefaultMessage;
	}

	public void setPfdDefaultMessage(String pfdDefaultMessage) {
		this.pfdDefaultMessage = pfdDefaultMessage;
	}

	public String getAgdbDefaultMessage() {
		return agdbDefaultMessage;
	}

	public void setAgdbDefaultMessage(String agdbDefaultMessage) {
		this.agdbDefaultMessage = agdbDefaultMessage;
	}

	public String[] getCgdbDefaultMessage() {
		return cgdbDefaultMessage;
	}

	public void setCgdbDefaultMessage(String[] cgdbDefaultMessage) {
		this.cgdbDefaultMessage = cgdbDefaultMessage;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
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
		return "DefaultMessages [id=" + id + ", mldbDefaultMessage=" + mldbDefaultMessage + ", pfdDefaultMessage="
				+ pfdDefaultMessage + ", agdbDefaultMessage=" + agdbDefaultMessage + ", cgdbDefaultMessage="
				+ Arrays.toString(cgdbDefaultMessage) + ", language=" + language + ", createdAt=" + createdAt
				+ ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}




}
