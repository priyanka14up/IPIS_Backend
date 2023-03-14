/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Cgdb Transmission Model
 */
package com.innobitsysytems.ipis.model.reports;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.device.CoachesDetail;
import com.innobitsysytems.ipis.model.device.HashMapCoachDetails;

import javax.persistence.Convert;

@Entity
@Table(name = "cgdbtransmissionreports")
@EntityListeners(AuditingEntityListener.class)
public class CgdbTransmission {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId", referencedColumnName = "id", nullable = false)
	@Fetch(FetchMode.JOIN)
	private User user;
	
	@Column(name = "pdcPort")
    private String pdcPort;

	@Column(name = "trainNo", nullable = false)
    private long trainNo;
	
	@Column(name = "trainName", nullable = false)
    private String trainName;
	
	@Column(name = "platformNo", nullable = false)
    private String platformNo;
	
	
	@Column(columnDefinition = "TEXT")
	@Convert(converter = HashMapCoachDetails.class)
    private List<CoachesDetail> data;
	
	@Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime localDateTime;
	
	@CreationTimestamp
    @Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created_at", nullable = false)
    private  Date createdAt;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getPdcPort() {
		return pdcPort;
	}

	public void setPdcPort(String pdcPort) {
		this.pdcPort = pdcPort;
	}

	public long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(long trainNo) {
		this.trainNo = trainNo;
	}

	public String getTrainName() {
		return trainName;
	}

	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getPlatformNo() {
		return platformNo;
	}

	public void setPlatformNo(String platformNo) {
		this.platformNo = platformNo;
	}

	public List<CoachesDetail> getData() {
		return data;
	}

	public void setData(List<CoachesDetail> data) {
		this.data = data;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return "CgdbTransmission [id=" + id + ", user=" + user + ", pdcPort=" + pdcPort + ", trainNo=" + trainNo
				+ ", trainName=" + trainName + ", platformNo=" + platformNo + ", data=" + data + ", localDateTime="
				+ localDateTime + ", createdAt=" + createdAt + "]";
	}

	
}
