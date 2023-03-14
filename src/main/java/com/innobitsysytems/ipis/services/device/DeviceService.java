/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Device Service
 */
package com.innobitsysytems.ipis.services.device;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.Device;


public interface DeviceService {
	
	public HashMap<String, Object> list() throws HandledException;
	
	public HashMap<String, Object> getSingleDevice(Long id) throws HandledException;
	
	public HashMap<String, Object> create(HttpServletRequest request, Device device)throws HandledException;
	
	public  HashMap<String, Object> update(Long id, Device device) throws HandledException;
	
	public Map<String, Boolean> deletev1(Long id)throws HandledException;
	
	public Map<String, Object> deletev2(Long id)throws HandledException;
	
	public ArrayList getCdsStatus() throws HandledException;
	
	public ArrayList getPdcStatus(String[] platformNo) throws HandledException;
	
	public void setConfiguration() throws HandledException;
	
}
