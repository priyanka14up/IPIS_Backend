/**
 * Name: Priyanka Upadhyay
 * Copyright: Innobit Systems, 2021
 * Purpose: CoachesCode Model
 */
package com.innobitsysytems.ipis.model.setup;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="coachescode")
@EntityListeners(AuditingEntityListener.class)

public class CoachesCode {
	
	public CoachesCode() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@NotBlank(message = "English Coach Name is mandatory")
	@Column(name = "engCoachName", nullable = false)
    private String engCoachName;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getEngCoachName() {
		return engCoachName;
	}

	public void setEngCoachName(String engCoachName) {
		this.engCoachName = engCoachName;
	}

	public CoachesCode(long id, @NotBlank(message = "English Coach Name is mandatory") String engCoachName) {
		super();
		this.id = id;
		this.engCoachName = engCoachName;
	}
}
