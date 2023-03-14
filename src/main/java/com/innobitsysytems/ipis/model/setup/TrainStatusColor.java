package com.innobitsysytems.ipis.model.setup;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.HashMapRgbDetails;

@Entity
@Table(name="train_status_color")
@EntityListeners(AuditingEntityListener.class)
public class TrainStatusColor {
	@Override
	public String toString() {
		return "TrainStatusColor [id=" + id + ", status=" + status + ", trainADColor=" + trainADColor
				+ ", trainNoColor=" + trainNoColor + ", trainPfColor=" + trainPfColor + ", trainTimeColor=" + trainTimeColor
				+ ", trainNameColor=" + trainNameColor + "]";
	}

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

	@Column(name = "train_status",nullable = false)
    @NotBlank(message = "status is mandatory")
    private  String status;
	
	@Column(name = "arrival_departure",nullable = false)
    private  String arrDep;

	@Column(name = "train_ad_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails trainADColor;
    
    @Column(name = "train_no_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails trainNoColor;
    
    @Column(name = "train_pf_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails trainPfColor;
    
    @Column(name = "train_time_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails trainTimeColor;
    
    @Column(name = "train_name_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails trainNameColor;

	public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public RgbDetails getTrainADColor() {
		return trainADColor;
	}

	public void setTrainADColor(RgbDetails trainADColor) {
		this.trainADColor = trainADColor;
	}

	public RgbDetails getTrainNoColor() {
		return trainNoColor;
	}

	public void setTrainNoColor(RgbDetails trainNoColor) {
		this.trainNoColor = trainNoColor;
	}

	public RgbDetails getTrainPfColor() {
		return trainPfColor;
	}

	public void setTrainPfColor(RgbDetails trainPfColor) {
		this.trainPfColor = trainPfColor;
	}

	public RgbDetails getTrainTimeColor() {
		return trainTimeColor;
	}

	public void setTrainTimeColor(RgbDetails trainTimeColor) {
		this.trainTimeColor = trainTimeColor;
	}

	public RgbDetails getTrainNameColor() {
		return trainNameColor;
	}

	public void setTrainNameColor(RgbDetails trainNameColor) {
		this.trainNameColor = trainNameColor;
	}

	public String getArrDep() {
		return arrDep;
	}

	public void setArrDep(String arrDep) {
		this.arrDep = arrDep;
	}

	 

}
