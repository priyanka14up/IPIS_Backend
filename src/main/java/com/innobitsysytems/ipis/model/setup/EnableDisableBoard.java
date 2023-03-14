/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Enable Disable Board Model
 */
package com.innobitsysytems.ipis.model.setup;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="enabledisableboard")
@EntityListeners(AuditingEntityListener.class)
public class EnableDisableBoard {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

	@NotBlank(message = "Multi Line Display Board is mandatory")
	@Column(name = "mldb", nullable = false)
    private String mldb;
	
	@NotBlank(message = "Platform Display Board is mandatory")
	@Column(name = "pfdb", nullable = false)
    private String pfdb;
	
	@NotBlank(message = "At a Glance Display Board is mandatory")
	@Column(name = "agdb", nullable = false)
    private String agdb;
	
	@NotBlank(message = "Coach Guidannce Display Board is mandatory")
	@Column(name = "cgdb", nullable = false)
    private String cgdb;
	
	@NotBlank(message = "Public Announcement is mandatory")
	@Column(name = "pa", nullable = false)
    private String pa;
	
	@NotBlank(message = "In Video Display is mandatory")
	@Column(name = "ivdDisplay", nullable = false)
    private String ivdDisplay;
	
	@NotBlank(message = "Out Video Display is mandatory")
	@Column(name = "ovdDisplay", nullable = false)
    private String ovdDisplay;
	
	@NotBlank(message = "TV Display is mandatory")
	@Column(name = "TvDisplay", nullable = false)
    private String TvDisplay;
	
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

	public String getMldb() {
		return mldb;
	}

	public void setMldb(String mldb) {
		this.mldb = mldb;
	}

	public String getPfdb() {
		return pfdb;
	}

	public void setPfdb(String pfdb) {
		this.pfdb = pfdb;
	}

	public String getAgdb() {
		return agdb;
	}

	public void setAgdb(String agdb) {
		this.agdb = agdb;
	}

	public String getCgdb() {
		return cgdb;
	}

	public void setCgdb(String cgdb) {
		this.cgdb = cgdb;
	}

	public String getPa() {
		return pa;
	}

	public void setPa(String pa) {
		this.pa = pa;
	}

	public String getIvdDisplay() {
		return ivdDisplay;
	}

	public void setIvdDisplay(String ivdDisplay) {
		this.ivdDisplay = ivdDisplay;
	}

	public String getOvdDisplay() {
		return ovdDisplay;
	}

	public void setOvdDisplay(String ovdDisplay) {
		this.ovdDisplay = ovdDisplay;
	}

	public String getTvDisplay() {
		return TvDisplay;
	}

	public void setTvDisplay(String tvDisplay) {
		TvDisplay = tvDisplay;
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
		return "EnableDisableBoard [id=" + id + ", mldb=" + mldb + ", pfdb=" + pfdb + ", agdb=" + agdb + ", cgdb="
				+ cgdb + ", pa=" + pa + ", ivdDisplay=" + ivdDisplay + ", ovdDisplay=" + ovdDisplay + ", TvDisplay="
				+ TvDisplay + ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt
				+ ", updatedBy=" + updatedBy + "]";
	}

	
    


}
