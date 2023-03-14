/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Transmission Model
 */
package com.innobitsysytems.ipis.model.reports;

import java.time.LocalDateTime;
import java.util.Date;

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

@Entity
@Table(name = "traintransmissionreports")
@EntityListeners(AuditingEntityListener.class)
public class TrainTransmission {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@Column(name = "trainName", nullable = false)
    private String trainName;
	
	@ManyToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "userId",referencedColumnName = "id",nullable = false)
	@Fetch(FetchMode.JOIN)
	private User user;
	
	@Column(name = "trainNameRegional")
    private String trainNameRegional;
	
	@Column(name = "trainNo")
    private long trainNo;
	
	@Column(name = "scheuduledArrival", nullable = false)
    private String scheuduledArrival;
	
	@Column(name = "scheuduledDeparture", nullable = false)
    private String scheuduledDeparture;
	
	@Column(name = "late", nullable = false)
    private String late;
	
	@Column(name = "actualArrival", nullable = false)
    private String actualArrival;
	
	@Column(name = "actualDeparture", nullable = false)
    private String actualDeparture;
	
	@Column(name = "trainStatus")
    private String trainStatus;
	
	@Column(name = "route")
    private String route;
	
	@Column(name = "local_date_time", columnDefinition = "TIMESTAMP")
	private LocalDateTime localDateTime;
	
	@Column(name = "trainDescription")
    private String description;
	
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

	public String getTrainName() {
		return trainName;
	}

	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	public void setTrainName(String trainName) {
		this.trainName = trainName;
	}

	public String getTrainNameRegional() {
		return trainNameRegional;
	}

	public void setTrainNameRegional(String trainNameRegional) {
		this.trainNameRegional = trainNameRegional;
	}

	public long getTrainNo() {
		return trainNo;
	}

	public void setTrainNo(long trainNo) {
		this.trainNo = trainNo;
	}

	public String getScheuduledArrival() {
		return scheuduledArrival;
	}

	public void setScheuduledArrival(String scheuduledArrival) {
		this.scheuduledArrival = scheuduledArrival;
	}

	public String getScheuduledDeparture() {
		return scheuduledDeparture;
	}

	public void setScheuduledDeparture(String scheuduledDeparture) {
		this.scheuduledDeparture = scheuduledDeparture;
	}

	public String getLate() {
		return late;
	}

	public void setLate(String late) {
		this.late = late;
	}

	public String getActualArrival() {
		return actualArrival;
	}

	public void setActualArrival(String actualArrival) {
		this.actualArrival = actualArrival;
	}

	public String getActualDeparture() {
		return actualDeparture;
	}

	public void setActualDeparture(String actualDeparture) {
		this.actualDeparture = actualDeparture;
	}

	public String getTrainStatus() {
		return trainStatus;
	}

	public void setTrainStatus(String trainStatus) {
		this.trainStatus = trainStatus;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	
	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "TrainTransmission [id=" + id + ", trainName=" + trainName + ", user=" + user + ", trainNameRegional="
				+ trainNameRegional + ", trainNo=" + trainNo + ", scheuduledArrival=" + scheuduledArrival
				+ ", scheuduledDeparture=" + scheuduledDeparture + ", late=" + late + ", actualArrival=" + actualArrival
				+ ", actualDeparture=" + actualDeparture + ", trainStatus=" + trainStatus + ", route=" + route
				+ ", localDateTime=" + localDateTime + ", description=" + description + ", createdAt=" + createdAt
				+ "]";
	}

	
	
	
}
