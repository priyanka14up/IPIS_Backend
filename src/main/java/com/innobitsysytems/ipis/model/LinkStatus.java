/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: LinkStatus Model
 */
package com.innobitsysytems.ipis.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.device.Device;


@Entity
@Table(name="linkstatus")
@EntityListeners(AuditingEntityListener.class)

public class LinkStatus {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@OnDelete(action = OnDeleteAction.CASCADE)
	@OneToOne(mappedBy = "linkStatus")
	private Device device;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "linkTime", nullable = false)
	private Date linkTime;
	 
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "responseTime", nullable = false)
	private Date responseTime;
	 
    @Column(name = "response", nullable = false)
	private  String response;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Device getDevice() {
		return device;
	}

	public void setDevice(Device device) {
		this.device = device;
	}

	public Date getLinkTime() {
		return linkTime;
	}

	public void setLinkTime(Date linkTime) {
		this.linkTime = linkTime;
	}

	public Date getResponseTime() {
		return responseTime;
	}

	public void setResponseTime(Date responseTime) {
		this.responseTime = responseTime;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "LinkStatus [id=" + id + ", device=" + device + ", linkTime=" + linkTime + ", responseTime="
				+ responseTime + ", response=" + response + "]";
	}

	

}