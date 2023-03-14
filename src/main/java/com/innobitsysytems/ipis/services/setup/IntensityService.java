/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Intensity Service
 */
package com.innobitsysytems.ipis.services.setup;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.setup.Intensity;

public interface IntensityService {

	public HashMap<String, Object> getAutoIntensity() throws HandledException;

	public HashMap<String, Object> create(Intensity intensity) throws HandledException;

	public HashMap<String, Object> getManualIntensity() throws HandledException;

	public List<Intensity> getAllIntensity() throws Exception;

	//public Intensity saveIntensity(Intensity intensity) throws Exception;
	
	public HashMap<String, Object> saveIntensity(Intensity intensity) throws Exception;

	public HashSet<String> getDevicePlatform(DeviceType deviceType) throws Exception;

	public List<String> getDeviceId(DeviceType deviceType, String platformNo) throws Exception;

	public void getConfig(DeviceType deviceType) throws HandledException ;

	public HashMap<String, Object> getConfig(Intensity intensity) throws HandledException;
}
