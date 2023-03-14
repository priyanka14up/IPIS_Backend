/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Default Messages Controller
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

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.DefaultMessages;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")

public class DefaultMessagesController {
	
	private static final Logger log = LoggerFactory.getLogger(DefaultMessagesController.class);
	
	@Autowired
	public SetupService setupService;
	
	 @GetMapping("/defaultmessages")
	 public ResponseEntity<Object> getAllDefaultMessages() {
		 
		 List Data =  setupService.allMessages();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
	   
	  }
	 
	 @PostMapping("/defaultmessages")
	  public ResponseEntity<Object> createDefaultMessages(HttpServletRequest request, @Valid@RequestBody DefaultMessages defaultMessages)
		      throws HandledException {
		 try {
				
			 HashMap<String, Object> msg =  setupService.postMessages(request, defaultMessages);
			 return ResponseHandler.generateResponse("success", HttpStatus.OK, msg);
			 
			}catch(HandledException e) {
				
				return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
			}
	 }
	 
	 @PostMapping("/setmessages/{boardType}/{platformNo}")
	 public ResponseEntity<Object> setDefaultMessages(@PathVariable(value = "boardType") String boardType, @PathVariable(value = "platformNo") String platformNo)
		      throws HandledException {
		 
		 Object obj = null;
		 setupService.setDefaultMsg(boardType, platformNo);
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);
	 }
	 
}
