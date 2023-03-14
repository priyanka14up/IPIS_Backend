/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: BoardSetting Model
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
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="boardsetting")
@EntityListeners(AuditingEntityListener.class)
public class BoardSetting {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;
	
	@NotBlank(message = "Board Type is mandatory")
	@Column(name = "boardType", nullable = false)
    private String boardType;
	
	@NotBlank(message = "Display Time is mandatory")
	@Column(name = "displayTime", nullable = false)
    private String displayTime;
	
	@NotBlank(message = "Effect is mandatory")
	@Column(name = "effect", nullable = false)
    private String effect;
	
	@NotBlank(message = "Speed is mandatory")
	@Column(name = "speed", nullable = false)
    private String speed;
	
	@NotBlank(message = "Letter Size is mandatory")
	@Column(name = "letterSize", nullable = false)
    private String letterSize;
	
	@NotNull(message = "Character Gap is mandatory")
	@Column(name = "characterGap", nullable = false)
    private Integer characterGap;
	
	@NotNull(message = "Page Change Time is mandatory")
	@Column(name = "pageChangeTime", nullable = false)
    private Integer pageChangeTime;
	
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

	public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTimeNumber) {
		this.displayTime = displayTimeNumber;
	}

	public String getEffect() {
		return effect;
	}

	public void setEffect(String effect) {
		this.effect = effect;
	}

	public String getSpeed() {
		return speed;
	}

	public void setSpeed(String speed) {
		this.speed = speed;
	}

	public String getLetterSize() {
		return letterSize;
	}

	public void setLetterSize(String letterSize) {
		this.letterSize = letterSize;
	}

	public Integer getCharacterGap() {
		return characterGap;
	}

	public void setCharacterGap(Integer characterGap) {
		this.characterGap = characterGap;
	}

	public Integer getPageChangeTime() {
		return pageChangeTime;
	}

	public void setPageChangeTime(Integer pageChangeTime) {
		this.pageChangeTime = pageChangeTime;
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
		return "BoardSetting [id=" + id + ", boardType=" + boardType + ", displayTime=" + displayTime + ", effect="
				+ effect + ", speed=" + speed + ", letterSize=" + letterSize + ", characterGap=" + characterGap
				+ ", pageChangeTime=" + pageChangeTime + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}

	
    
    

}
