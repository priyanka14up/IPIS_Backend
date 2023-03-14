/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Children Details Model
 */
package com.innobitsysytems.ipis.model.device;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

public class ChildrenDetails implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@NotBlank
	private String portNumber;
    
	@NotBlank
    private long id;
	
	@NotBlank
	private DeviceType deviceType;

	public String getPortNumber() {
		return portNumber;
	}

	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public DeviceType getDeviceType() {
		return deviceType;
	}

	public void setDeviceType(DeviceType deviceType) {
		this.deviceType = deviceType;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public ChildrenDetails(@NotBlank String portNumber, @NotBlank long id, @NotBlank DeviceType deviceType) {
		super();
		this.portNumber = portNumber;
		this.id = id;
		this.deviceType = deviceType;
	}

	
	}
