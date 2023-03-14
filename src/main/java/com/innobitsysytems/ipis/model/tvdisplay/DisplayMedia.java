/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Media Model
 */
package com.innobitsysytems.ipis.model.tvdisplay;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "displayMedia")
@EntityListeners(AuditingEntityListener.class)
public class DisplayMedia {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", updatable = false, nullable = false)
	private Long id;
	
	@ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "advPlaylistId", referencedColumnName = "id", nullable = false)
    private AdvertismentPlaylist advPlaylist;
	
	@NotBlank(message = "Display Type is mandatory")
	@Column(name="displayType")
	private String displayType;
	
	@NotBlank(message = "Media Location is mandatory")
	@Column(name="mediaLocation")
	private String mediaLocation;
	
	@NotBlank(message = "Media Name is mandatory")
	@Column(name="mediaName")
	private String mediaName;
	
	@Column(name="fileAdded")
	private boolean fileAdded =false;
	
	public boolean isFileAdded() {
		return fileAdded;
	}

	public void setFileAdded(boolean fileAdded) {
		this.fileAdded = fileAdded;
	}

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

	public AdvertismentPlaylist getAdvPlaylist() {
		return advPlaylist;
	}

	public void setAdvPlaylist(AdvertismentPlaylist advPlaylist) {
		this.advPlaylist = advPlaylist;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getMediaLocation() {
		return mediaLocation;
	}

	public void setMediaLocation(String mediaLocation) {
		this.mediaLocation = mediaLocation;
	}

	public String getMediaName() {
		return mediaName;
	}

	public void setMediaName(String mediaName) {
		this.mediaName = mediaName;
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
		return "DisplayMedia [id=" + id + ", advPlaylist=" + advPlaylist + ", displayType=" + displayType
				+ ", mediaLocation=" + mediaLocation + ", mediaName=" + mediaName
				+ ", createdAt=" + createdAt + ", createdBy=" + createdBy + ", updatedAt=" + updatedAt + ", updatedBy="
				+ updatedBy + "]";
	}

	
	
}
