/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Device Controller
 */

package com.innobitsysytems.ipis.controller;

import java.util.*;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.services.device.DeviceService;
import com.innobitsysytems.ipis.utility.CommonUtil;

@RestController
@RequestMapping("/api/v1/interface")
public class DeviceController {

    @Autowired
    public DeviceService deviceService;
    
    @Autowired
    private CommonUtil commonUtil;

    @GetMapping("/devices")
    public ResponseEntity<Object> getAllDevices() throws HandledException{

    	try {
    		
    		 HashMap<String, Object> deviceData = deviceService.list();
    	     return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);
    		
    	}catch(HandledException e) {
    		
    		  return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
    	}
       

    }

    @GetMapping("/devices/{id}")
    public ResponseEntity<Object> getDevicesById(@PathVariable(value = "id") long id) throws HandledException {

        try {

            HashMap<String, Object> deviceData = deviceService.getSingleDevice(id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

        } catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @PostMapping("/devices")
    public ResponseEntity<Object> createDevice(HttpServletRequest request, @RequestBody Device device) throws HandledException {

        try {

            HashMap<String, Object> deviceData = deviceService.create(request, device);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

        } catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @CrossOrigin(allowedHeaders = "*")
    @PutMapping("/devices/{id}")
    public ResponseEntity<Object> updateDevice(@PathVariable(value = "id") Long id, @RequestBody Device deviceDetails) throws HandledException {

        try {

            HashMap<String, Object> deviceData = deviceService.update(id, deviceDetails);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

        } catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/devicesv1/{id}")
    public ResponseEntity<Object> deleteDevicev1(@PathVariable(value = "id") long id) throws HandledException {

        try {

            Map<String, Boolean> deviceData = deviceService.deletev1(id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

        } catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @CrossOrigin(allowedHeaders = "*")
    @DeleteMapping("/devicesv2/{id}")
    public ResponseEntity<Object> deleteDevicev2(@PathVariable(value = "id") long id) throws HandledException {

        try {

            Map<String, Object> deviceData = deviceService.deletev2(id);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

        } catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }

    @PostMapping("/setConfiguration")
    public ResponseEntity<Object> setConfiguration() throws HandledException {

        try {
        	
        	Object obj = null;
        	deviceService.setConfiguration();
            return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);

        } catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }
    
    @GetMapping("/statuscds")
    public ResponseEntity<Object> getCdsDeviceStatus() throws HandledException {

    	try {
    		
    		ArrayList deviceData = deviceService.getCdsStatus();
    		commonUtil.linkCheck(deviceData);
    		return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);
    		 
    	}catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }

    }
    
    @GetMapping("/statuspdc/{platformNo}")
    public ResponseEntity<Object> getPdcDeviceStatus(@PathVariable String[] platformNo) throws HandledException {

    	try {
    		
    		ArrayList deviceData = deviceService.getPdcStatus(platformNo);
    		commonUtil.linkCheck(deviceData);
    		return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);
    		
    	}catch (HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }   

    }

}
