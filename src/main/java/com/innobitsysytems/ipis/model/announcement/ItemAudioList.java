/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: ItemAudioList Model
 */
package com.innobitsysytems.ipis.model.announcement;

import java.util.Date;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="itemAudioList")
@EntityListeners(AuditingEntityListener.class)

public class ItemAudioList {
	

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;
	
	@ManyToOne(cascade=CascadeType.ALL)
	@JoinColumn(name = "itemListId", referencedColumnName = "id")
	private ItemList itemList;
	
	@NotNull(message = "Item Id is mandatory")
	@Column(name = "itemId")
    private Integer itemId;
	
	@Column(name = "audioName")
    private  String audioName;
	
	@Column(name = "audioLocation")
    private  String audioLocation;
	
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

	public ItemList getItemList() {
		return itemList;
	}

	public void setItemList(ItemList itemList) {
		this.itemList = itemList;
	}

	public Integer getItemId() {
		return itemId;
	}

	public void setItemId(Integer itemId) {
		this.itemId = itemId;
	}

	public String getAudioName() {
		return audioName;
	}

	public void setAudioName(String audioName) {
		this.audioName = audioName;
	}

	public String getAudioLocation() {
		return audioLocation;
	}

	public void setAudioLocation(String audioLocation) {
		this.audioLocation = audioLocation;
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
		return "ItemAudioList [id=" + id + ", itemList=" + itemList + ", itemId=" + itemId + ", audioName=" + audioName
				+ ", audioLocation=" + audioLocation + ", createdAt=" + createdAt + ", createdBy=" + createdBy
				+ ", updatedAt=" + updatedAt + ", updatedBy=" + updatedBy + "]";
	}
	 
	 



}
