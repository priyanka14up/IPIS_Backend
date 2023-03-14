/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Coach Data Controller
 */

package com.innobitsysytems.ipis.controller.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")

public class CoachDataController {
	
	private static final Logger log = LoggerFactory.getLogger(CoachDataController.class);
	
	@Autowired
	public SetupService setupService;
	
	 @GetMapping("/coach")
	 public ResponseEntity<Object> getAllCoach() {
		 
		 List coachData =  setupService.allCoach();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, coachData);
	    
	  }
	
	@GetMapping("/coach/{id}")
	public ResponseEntity<Object> getCoachById(@PathVariable(value = "id") Long id) throws HandledException {
		  
		try {
			
			HashMap<String, Object> singlecoach =  setupService.singleCoach(id);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, singlecoach);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	
	  }
	 
	 @PostMapping("/coach")
	 public ResponseEntity<Object> createCoachData(HttpServletRequest request, @Valid @RequestBody CoachData coachData) throws HandledException 
	 {
		 
		 try {
				
			 HashMap<String, Object> postdetail =  setupService.postCoach(request, coachData);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
		
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 
	 @CrossOrigin(allowedHeaders = "*")
	 @PutMapping("/coach/{id}")
	 public ResponseEntity<Object> updateStation(@PathVariable(value = "id") @NotNull Long id,  @RequestBody CoachData coachData)throws HandledException 
	 {
		 
		 try {
				
			 HashMap<String, Object> putdetails =  setupService.updateCoach(id, coachData);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, putdetails);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 
	 @CrossOrigin(allowedHeaders = "*")
	  @DeleteMapping("/coach/{id}")
	  public ResponseEntity<Object> deleteStation(@PathVariable(value = "id") long id) throws HandledException {
		 
		  try {
				
				 Map<String, Boolean> coachData =  setupService.deleteCoach(id);
				 return ResponseHandler.generateResponse("success",HttpStatus.OK, coachData);
				 
				 
				}catch(HandledException e) {
					
					return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
				}
	  }

	 
	 @GetMapping("/allcoachnames")
	  public ResponseEntity<Object> getAllCoachNames() throws HandledException {
		 try
		 {
		 List details =  setupService.allCoachNames();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
		 }
		 catch(HandledException e)
		 {

			 return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		 
		 }
	  }
	 
	 
	 @GetMapping("/coachescode")
	  public ResponseEntity<Object> getAllCoachesCode() throws HandledException {
		 try
		 {
		 List details =  setupService.allCoachesCode();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
		 }
		 catch(HandledException e)
		 {

			 return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		 
		 }
	  }


}
