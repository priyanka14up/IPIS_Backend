/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Online Train Controller
 */

package com.innobitsysytems.ipis.controller;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.services.onlineTrain.OnlineTrainService;

@RestController
@RequestMapping("/api/v1")

public class OnlineTrainController {

	@Autowired
	public OnlineTrainService onlineTrainService;

	@GetMapping("/onlineTrain")
	public ResponseEntity<Object> getAllOnlineTrainData() throws HandledException {
		try {

			List<OnlineTrain> trainData = onlineTrainService.allTrains();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, trainData);
		} catch (HandledException e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@PostMapping("/addToGrid")
	public ResponseEntity<Object> addToGrid(HttpServletRequest request, @Valid @RequestBody OnlineTrain onlineTrain)
			throws HandledException {

		try {

			OnlineTrain postTrain = onlineTrainService.postTrain(request, onlineTrain);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, postTrain);
		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/displayTadb")
	public ResponseEntity<Object> displatTadb() throws HandledException {

		try {

			Object obj = null;
			onlineTrainService.displayTadb();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@PostMapping("/clearCgs")
	public ResponseEntity<Object> clearCgs() throws HandledException {
		try {
			Object obj = null;
			onlineTrainService.clearCgs();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);
		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@PostMapping("/displayCgdb")
	public ResponseEntity<Object> postOnlineTrainData() throws HandledException {

		try {

			Object obj = null;
			onlineTrainService.displayCgdbData();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@PostMapping("/playerPause")
	public ResponseEntity<Object> pausePlayer() throws HandledException {

		try {

			String onlineTrains = onlineTrainService.pauseAnnouncement();

			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrains);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/playerStop")
	public ResponseEntity<Object> stopPlayer() throws HandledException {

		try {

			String onlineTrains = onlineTrainService.stopAnnouncement();

			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrains);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/player/{repeat}")
	public ResponseEntity<Object> player(@PathVariable int repeat) throws Exception {

		try {

			String onlineTrains = onlineTrainService.announcePlayer(repeat);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrains);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/nextplay/{id}/{repeat}/{next}")
	public ResponseEntity<Object> nextPlayer(@PathVariable String id, @PathVariable int repeat, @PathVariable int next)
			throws Exception {

		try {

			String onlineTrains = onlineTrainService.nextPlayer(id, repeat, next);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrains);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/onlineTrain/{trainNumber}")
	public ResponseEntity<Object> putOnlineTrainData(@PathVariable(value = "trainNumber") @NotNull Long trainNumber,
			@RequestBody OnlineTrain onlineTrain) throws HandledException {

		try {

			OnlineTrain onlineTrainData = onlineTrainService.updateTrain(trainNumber, onlineTrain);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrainData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/onlineTrain/{trainNumber}")
	public ResponseEntity<Object> deleteOnlineTrainData(@PathVariable(value = "trainNumber") Long trainNumber)
			throws HandledException {

		try {

			Map<String, Boolean> onlineTrainData = onlineTrainService.deleteTrainData(trainNumber);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, onlineTrainData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

}
