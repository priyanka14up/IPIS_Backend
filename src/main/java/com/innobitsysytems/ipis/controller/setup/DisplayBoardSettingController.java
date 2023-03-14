/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Board Setting Controller
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobitsysytems.ipis.dto.BoardDiagnosticDto;
import com.innobitsysytems.ipis.dto.BoardLedDto;
import com.innobitsysytems.ipis.dto.SoftResetDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.exception.ResourceNotFoundException;
import com.innobitsysytems.ipis.model.setup.BoardSetting;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")
public class DisplayBoardSettingController {
	
    private static final Logger log = LoggerFactory.getLogger(DisplayBoardSettingController.class);

	@Autowired
	public SetupService setupService;
	
	@GetMapping("/boardset")
	public ResponseEntity<Object> getAllBoardSetting() {
		 
		 List Data =  setupService.allSetting();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
	    
	  }
	
	@GetMapping("/boardset/{id}")
	public ResponseEntity<Object> getBoardSettingById(@PathVariable(value = "id") Long id) throws HandledException {
		
		try {
			
			HashMap<String, Object> boardSetting =  setupService.singleSetting(id);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, boardSetting);
			 
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
		
	  }
	 
	 @PostMapping("/boardset")
	 public ResponseEntity<Object> createBoardSetting(HttpServletRequest request, @Valid@RequestBody BoardSetting boardSetting) throws HandledException {
		 
		 try {
				
			 HashMap<String, Object> postdetail =  setupService.postSetting(request, boardSetting);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
		
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 
	 
	 @PostMapping("/led")
	  public ResponseEntity<Object> createBoardLedTesting(HttpServletRequest request, @Valid@RequestBody BoardLedDto boardLed)
		      throws HandledException {
		 
		 try {
				
			 HashMap<String, Object> postdetail =  setupService.postTesting(request, boardLed);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
		
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 

 
	 @PostMapping("/board")
	  public ResponseEntity<Object> createBoardDiagonistics(HttpServletRequest request, @Valid@RequestBody BoardDiagnosticDto boardDiagnostic)
		      throws ResourceNotFoundException {
		 
		 try {
				
			 HashMap<String, Object> postdetail =  setupService.postBoard(request, boardDiagnostic);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
		
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	
	 }
	 
		
		@GetMapping("/board")
		public ResponseEntity<Object> getAllBoardDiagonistics() {
			 
			 List Data =  setupService.allSetting();
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
		    
		}
		
		 
		 @PostMapping("/softReset")
			public ResponseEntity<Object> displatTadb(HttpServletRequest request,@Valid@RequestBody SoftResetDto softreset)throws HandledException
			{
				
				try {
					
					Object obj = null;
					setupService.softReset(request,softreset);
					return ResponseHandler.generateResponse("success",HttpStatus.OK, obj);
					
				}catch(HandledException e) {
					
					return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
					
				}
				
			}
		
}