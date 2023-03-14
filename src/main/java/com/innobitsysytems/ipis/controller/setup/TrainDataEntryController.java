/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Data Entry Controller
 */

package com.innobitsysytems.ipis.controller.setup;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.exception.ResourceNotFoundException;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.model.setup.TrainDetails;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")

public class TrainDataEntryController {

	private static final Logger log = LoggerFactory.getLogger(TrainDataEntryController.class);

	@Autowired
	public SetupService setupService;

	@GetMapping("/traindata")
	public ResponseEntity<Object> getAllTrain() {

		long[] Data = setupService.allTrain();
		return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);

	}

	@GetMapping("/allTrainData")
	  public ResponseEntity<Object> getAllTrainData() throws HandledException {
	    
		List Data =  setupService.getAllTrainData();
		 return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
	}
	  

	@GetMapping("/traindata/{trainNo}")
	public ResponseEntity<Object> getTrainById(@PathVariable(value = "trainNo") Long trainNo)
			throws ResourceNotFoundException {

		try {

			HashMap<String, Object> data = setupService.singleTrain(trainNo);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, data);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/traindata")
	public ResponseEntity<Object> createTrain(HttpServletRequest request, @Valid @RequestBody Train train)
			throws HandledException {

		try {

			HashMap<String, Object> data = setupService.postTrain(request, train);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, data);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/traindata/{trainNo}")
	public ResponseEntity<Object> updateTrain(@PathVariable(value = "trainNo") Long trainNo, @RequestBody Train train)
			throws HandledException {

		try {

			HashMap<String, Object> data = setupService.updateTrain(trainNo, train);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, data);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/traindata/{trainNo}")
	public ResponseEntity<Object> deleteOnlineTrainData(@PathVariable(value = "trainNo") Long trainNo)
			throws HandledException {

		try {

			Map<String, Boolean> onlineTrainData = setupService.deleteTrainData(trainNo);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrainData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

}
