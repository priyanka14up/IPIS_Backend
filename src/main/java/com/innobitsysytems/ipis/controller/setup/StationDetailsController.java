/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Details Controller
 */

package com.innobitsysytems.ipis.controller.setup;

import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")

public class StationDetailsController {
	
	private static final Logger log = LoggerFactory.getLogger(StationDetailsController.class);
	
	@Autowired
	public SetupService setupService;

	@GetMapping("/stationdetails")
	  public ResponseEntity<Object>  getAllStation() {
		 
		 List details =  setupService.allStationDetails();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
	    
	  }
	
	//get all station names and codes
			@GetMapping("/stationname")
			  public ResponseEntity<Object>  allStationNameAndCodes() throws HandledException
			{

				try {
					
					HashMap<String, Object> data =  setupService.allStationNameAndCodes();
					 return ResponseHandler.generateResponse("success", HttpStatus.OK, data);
					 
					 
					}catch(HandledException e) {
						
						return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
					}
			}
	 
	 @PostMapping("/stationdetails")
	 public ResponseEntity<Object> createStation(HttpServletRequest request, @Valid @RequestBody StationDetails stationDetails)
		      throws HandledException {
		 
		 try {
				
			 HashMap<String, Object> postdetail =  setupService.postStationDetails(request, stationDetails);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	    
		 
	 } 
	 
	 @GetMapping("/platforms")
	  public ResponseEntity<Object> getAllPlatforms() throws HandledException {
		 try
		 {
		 List<String> details =  setupService.allPlatforms();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
		 }
		 catch(HandledException e)
		 {

			 return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		 
		 }
	  }
	 
		@PostMapping("/uploadStationFile")
		public ResponseEntity<Object> createFile(StandardMultipartHttpServletRequest request)
				throws HandledException, Exception {
			try {
			String filename = setupService.uplLoadStationFile(request);

				return ResponseHandler.generateResponse("success", HttpStatus.OK, filename);
			} catch (Exception e) {
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

			}
		}
	
		@GetMapping("/autoTadd/{auto}")
		public ResponseEntity<Object> autoTadd(@PathVariable(value = "auto") boolean auto) throws Exception {

			try {

				 StationDetails postdetail=setupService.statusAutoTadd(auto);

				return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);

			} catch (HandledException e) {

				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}


		}
	 
}
