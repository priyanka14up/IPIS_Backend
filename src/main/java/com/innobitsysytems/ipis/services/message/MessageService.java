/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Message Service
 */
package com.innobitsysytems.ipis.services.message;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.DeviceType;

public interface MessageService {
	
	public List allMessage() throws HandledException;
	
	public HashMap<String, Object> getSingleMessage(Long id) throws HandledException;
	
	public HashMap<String, Object> createMessage(HttpServletRequest request, Message message) throws HandledException;

	public HashMap<String, Object> putMessage(Long id, Message message) throws HandledException;
	
	public  Map<String, Boolean> deleteMessage(Long id) throws HandledException;
	
	public List<String> getDeviceId(DeviceType deviceType, String platformNo) throws HandledException;
	
	public HashSet<String> getDevicePlatform(DeviceType deviceType) throws HandledException;
	
	public void displayMedia() throws HandledException;

}
