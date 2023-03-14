package com.innobitsysytems.ipis.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class TrainTransmissionDto {
	private String trainName;
	private String trainNameRegional;
	private long trainNo;
	private String scheuduledArrival;
	private String scheuduledDeparture;
	private String late;
	private String actualArrival;
	private String actualDeparture;
	private String trainStatus;
	private String route;
	private LocalDateTime localDateTime;
	private  Date createdAt;
	private String firstname;
	 private String lastname;
	 
	public TrainTransmissionDto(String trainName, String trainNameRegional, long trainNo, String scheuduledArrival,
			String scheuduledDeparture, String late, String actualArrival, String actualDeparture, String trainStatus,
			String route, LocalDateTime localDateTime, Date createdAt, String firstname, String lastname) {
		super();
		this.trainName = trainName;
		this.trainNameRegional = trainNameRegional;
		this.trainNo = trainNo;
		this.scheuduledArrival = scheuduledArrival;
		this.scheuduledDeparture = scheuduledDeparture;
		this.late = late;
		this.actualArrival = actualArrival;
		this.actualDeparture = actualDeparture;
		this.trainStatus = trainStatus;
		this.route = route;
		this.localDateTime = localDateTime;
		this.createdAt = createdAt;
		this.firstname = firstname;
		this.lastname = lastname;
	}

	public String getTrainName() {
		return trainName;
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

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	@Override
	public String toString() {
		return "TrainTransmissionDto [trainName=" + trainName + ", trainNameRegional=" + trainNameRegional
				+ ", trainNo=" + trainNo + ", scheuduledArrival=" + scheuduledArrival + ", scheuduledDeparture="
				+ scheuduledDeparture + ", late=" + late + ", actualArrival=" + actualArrival + ", actualDeparture="
				+ actualDeparture + ", trainStatus=" + trainStatus + ", route=" + route + ", localDateTime="
				+ localDateTime + ", createdAt=" + createdAt + ", firstname=" + firstname + ", lastname=" + lastname
				+ "]";
	}

	 
}
