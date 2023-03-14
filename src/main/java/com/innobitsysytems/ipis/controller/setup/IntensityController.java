/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Intensity Controller
 */

package com.innobitsysytems.ipis.controller.setup;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.model.setup.Intensity;
import com.innobitsysytems.ipis.services.setup.IntensityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/api/v1/setup")

public class IntensityController {

	private static final Logger log = LoggerFactory.getLogger(IntensityController.class);

	@Autowired
	private IntensityService intensityService;

	@GetMapping("/allintensity")
	public ResponseEntity<Object> getAllIntensity() throws Exception {
		try {

			List<Intensity> intensity = intensityService.getAllIntensity();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, intensity);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/intensity/Auto")
	public ResponseEntity<Object> getAutoIntensity() throws HandledException {

		try {

			HashMap<String, Object> intensity = intensityService.getAutoIntensity();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, intensity);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/intensity/Manual")
	public ResponseEntity<Object> getManualIntensity() throws HandledException {

		try {

			HashMap<String, Object> intensity = intensityService.getManualIntensity();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, intensity);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

//not in use
	@PostMapping("/newintensity")
	public ResponseEntity<Object> saveAutoIntensity(@RequestBody Intensity intensity) throws HandledException {

		try {

			HashMap<String, Object> savedIntensity = intensityService.create(intensity);
			return ResponseHandler.generateResponse("successufully sent", HttpStatus.OK, savedIntensity);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/intensity")
	public ResponseEntity<Object> saveIntensity(@RequestBody Intensity intensity) throws Exception {

		try {

			HashMap<String, Object> savedIntensity = intensityService.saveIntensity(intensity);
			//Intensity savedIntensity = intensityService.saveIntensity(intensity);
			return ResponseHandler.generateResponse("successufully sent", HttpStatus.OK, savedIntensity);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}
	
	

	@GetMapping("/platform/{deviceType}")
	public ResponseEntity<Object> getMsgDevicePlatform( @PathVariable DeviceType deviceType ) throws Exception {
		
		try {
			
			HashSet<String> platform =  intensityService.getDevicePlatform ( deviceType);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, platform);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			} 
		
	}
	

	
	@GetMapping("/{deviceType}/{platformNo}")
	public ResponseEntity<Object> getMsgDeviceId( @PathVariable DeviceType deviceType, @PathVariable String platformNo ) throws Exception {
		
		try {
			
			List<String> deviceId =  intensityService.getDeviceId ( deviceType,  platformNo);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceId);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
				
			} 
		
	}
	
	@PostMapping("/getConfiguration")
	public ResponseEntity<Object> getConfig(@RequestBody Intensity intensity ) throws Exception {
		
		
			
			try {
				
				Object obj = null;
				HashMap<String, Object> intensity1 =intensityService.getConfig(intensity);
				return ResponseHandler.generateResponse("success",HttpStatus.OK, intensity1);
				
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
				
			}
			
//			try {
//
//				HashMap<String, Object> savedIntensity = intensityService.saveIntensity(intensity);
//				//Intensity savedIntensity = intensityService.saveIntensity(intensity);
//				return ResponseHandler.generateResponse("successufully sent", HttpStatus.OK, savedIntensity);
//
//			} catch (HandledException e) {
//
//				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
//			}
//			
			
			
			
	}
	

}
