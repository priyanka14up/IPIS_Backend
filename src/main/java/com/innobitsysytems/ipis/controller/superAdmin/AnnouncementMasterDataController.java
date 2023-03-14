/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2022
 * Purpose: Announcement Master Data Controller
 */
package com.innobitsysytems.ipis.controller.superAdmin;

import java.util.*;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.innobitsysytems.ipis.dto.SuperMasterDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;
import com.innobitsysytems.ipis.repository.AnnouncementMasterDataRepository;
import com.innobitsysytems.ipis.services.superAdmin.AnnouncementMasterService;
import com.sun.istack.NotNull;

@RestController
@RequestMapping("api/v1")
public class AnnouncementMasterDataController {

	@Autowired
	public AnnouncementMasterService announcementMasterService;
	
	@Autowired
	public AnnouncementMasterDataRepository announcementMasterDataRepository;
	
	@GetMapping("/announcementMasterData")
	 public ResponseEntity<Object> getAllAnnouncementMasterData() {
		
		List<SuperMasterDto> amdData = announcementMasterService.allAnnouncementMasterData();
		return ResponseHandler.generateResponse("success", HttpStatus.OK, amdData);
		
	}
	
	@GetMapping("/announcementMasterData/{id}")
	public ResponseEntity<Object> getAnnouncementMasterDataById(@PathVariable(value = "id") Long id) throws HandledException {
	  
	try {
		
		SuperMasterDto singleannouncementMasterData =  announcementMasterService.getSingleAnnouncementMaster ( id );
		return ResponseHandler.generateResponse("success", HttpStatus.OK, singleannouncementMasterData);
		  
	}catch(HandledException e) {
		
		return ResponseHandler.generateResponse(e.getAnnouncementMasterData(), HttpStatus.BAD_REQUEST, null);
		
		}
	}
	
	@PostMapping("/announcementMasterData")	
	public ResponseEntity<Object> createAnnouncementMasterData(HttpServletRequest request, @RequestBody AnnouncementMasterData announcementMasterData) throws HandledException 
	{
		
		try {
			
			SuperMasterDto postdetail =  announcementMasterService.createAnnouncementMaster(request, announcementMasterData);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
			
				 
		}catch(HandledException e) {
			
			return ResponseHandler.generateResponse(e.getAnnouncementMasterData(), HttpStatus.BAD_REQUEST, null);
			
		}
		
	 }
		
	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/announcementMasterData/{id}")
	public ResponseEntity<Object> updateAnnouncementMasterData(@PathVariable(value = "id") @NotNull Long id,  @RequestBody AnnouncementMasterData Amd)throws HandledException 
	{
		try {
			
			SuperMasterDto putdetails =  announcementMasterService.putAnnouncementMaster(id, Amd);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, putdetails);
				 
		}catch(HandledException e) {
			
			return ResponseHandler.generateResponse(e.getAnnouncementMasterData(), HttpStatus.BAD_REQUEST, null);
			
		}
		
	}		 
	 	 
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/announcementMasterData/{id}")
	public ResponseEntity<Object> deleteAnnouncementMasterData(@PathVariable(value = "id") long id) throws HandledException {
		
		try {
			
			Map<String, Boolean> amdData = announcementMasterService.deleteAnnouncementMaster(id);
			return ResponseHandler.generateResponse("success",HttpStatus.OK, amdData);
					  
		}catch(HandledException e) {
			
			return ResponseHandler.generateResponse(e.getAnnouncementMasterData(), HttpStatus.BAD_REQUEST, null);
			
		}
		
	}

}

		 