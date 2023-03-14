/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Web Configuration Controller
 */
package com.innobitsysytems.ipis.controller.setup;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.WebConfiguration;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")

public class WebConfigurationController {
	
	private static final Logger log = LoggerFactory.getLogger(WebConfigurationController.class);

	@Autowired
	public SetupService setupService;
	
	 @GetMapping("/webconf")
	  public ResponseEntity<Object> getAllWebConf() {
		 
		 List Data =  setupService.allWeb();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
	   
	  }
	 
	 @PostMapping("/webconf")
	  public ResponseEntity<Object> createWebConf(HttpServletRequest request, @Valid@RequestBody WebConfiguration webConfiguration)
		      throws HandledException {
		
		 try {
				
			 HashMap<String, Object> postdetail =  setupService.postWeb(request, webConfiguration);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
		
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			} 
	 }

}
