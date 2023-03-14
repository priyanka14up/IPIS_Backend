/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Station Code Controller
 */

package com.innobitsysytems.ipis.controller.setup;

import java.net.URLDecoder;
import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.StationCode;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")
public class StationCodeController {
	
	private static final Logger log = LoggerFactory.getLogger(StationCodeController.class);

	@Autowired
	public SetupService setupService;
	
	 @GetMapping("/stationcode")
	  public ResponseEntity<Object> getAllStationCode() {
		 
		 List details =  setupService.allCode();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
	    
	  }
	 
	 
	 @GetMapping("/allstationcodes")
	  public ResponseEntity<Object> getAllStationCodes() throws HandledException {
		 try
		 {
		 List details =  setupService.allCodes();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
		 }
		 catch(HandledException e)
		 {

			 return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		 
		 }
	  }
	
	@GetMapping("/stationcode/{id}")
	  public ResponseEntity<Object> getStationCodeById(@PathVariable(value = "id") Long id)
	      throws HandledException {
		  
		try {
			
			
			HashMap<String, Object>  singledetail =  setupService.singleSCode(id);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, singledetail);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	    
	  }
	 
	 @PostMapping("/stationcode")
	  public ResponseEntity<Object> createStationCode(HttpServletRequest request, @Valid @RequestBody StationCode stationCode)
		      throws HandledException {
		 
		 try {
				
			 HashMap<String, Object>  postdetail =  setupService.postSCode(request, stationCode);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 
	 
	 
	//upload train status file
	 
		@PostMapping("/uploadStationCodeFile/{language}")
		public ResponseEntity<Object> uploadStationCodeFile(StandardMultipartHttpServletRequest request ,@PathVariable(value = "language") String  language)
				throws HandledException, Exception {
			try {
			String filename = setupService.uplLoadStationCodeFile(request,language);

				return ResponseHandler.generateResponse("success", HttpStatus.OK, filename);
			} catch (Exception e) {
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

			}
		}
		

	 
		@CrossOrigin(allowedHeaders = "*")
		@DeleteMapping("/deleteStationCodeUpload/{language}/{filename}")
		public ResponseEntity<Object> deleteStationCodeUpload(@PathVariable(value = "language") String language,@PathVariable(value = "filename") String filename) throws Exception {
			System.out.println("inside delete");
			System.out.println();
			try {
				String decoderFileName = URLDecoder.decode(filename,"UTF-8");
				Map<String, Boolean> statusData = setupService.deleteStationCodeUpload(decoderFileName, language);
				return ResponseHandler.generateResponse("success", HttpStatus.OK, statusData);

			} catch (HandledException e) {

				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}

		}
		
	 @CrossOrigin(allowedHeaders = "*")
	 @PutMapping("/stationcode/{id}")
	  public ResponseEntity<Object> updateStationCode(
	      @PathVariable(value = "id") @NotNull Long id,  @RequestBody StationCode stationCode)
	      throws HandledException {
		 
		 try {
				
			 HashMap<String, Object>  putdetails =  setupService.updateSCode(id, stationCode);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, putdetails);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 
	 
	 @CrossOrigin(allowedHeaders = "*")
	  @DeleteMapping("/stationcode/{id}")
	  public ResponseEntity<Object> deleteStation(@PathVariable(value = "id") long id) throws Exception {
		  
		  try {
				
				 Map<String, Boolean> deviceData =  setupService.deleteSCode(id);
				 return ResponseHandler.generateResponse("success",HttpStatus.OK,deviceData);
				 
				 
				}catch(HandledException e) {
					
					return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
				}
	  }

	 

}