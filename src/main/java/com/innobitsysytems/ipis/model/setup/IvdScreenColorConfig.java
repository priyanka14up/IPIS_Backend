package com.innobitsysytems.ipis.model.setup;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.innobitsysytems.ipis.model.HashMapRgbDetails;
@Entity
@Table(name="ivd_screencolor_config")
@EntityListeners(AuditingEntityListener.class)
public class IvdScreenColorConfig {
	@Override
	public String toString() {
		return "IvdScreenColorConfig [id=" + id + ", backgroundColor=" + backgroundColor + ", horizontalColor="
				+ horizontalColor + ", verticalColor=" + verticalColor + ", messageColor=" + messageColor + "]";
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    private long id;

	@Column(name = "background_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails backgroundColor;
    
    @Column(name = "horizontal_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails horizontalColor;
    
    @Column(name = "vertical_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails verticalColor;
    
    @Column(name = "message_color",columnDefinition = "TEXT", nullable = true)
    @Convert(converter = HashMapRgbDetails.class)
    private  RgbDetails messageColor;
    
    public long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public RgbDetails getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(RgbDetails backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public RgbDetails getHorizontalColor() {
		return horizontalColor;
	}

	public void setHorizontalColor(RgbDetails horizontalColor) {
		this.horizontalColor = horizontalColor;
	}

	public RgbDetails getVerticalColor() {
		return verticalColor;
	}

	public void setVerticalColor(RgbDetails verticalColor) {
		this.verticalColor = verticalColor;
	}

	public RgbDetails getMessageColor() {
		return messageColor;
	}

	public void setMessageColor(RgbDetails messageColor) {
		this.messageColor = messageColor;
	}

}
