/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Train Status Controller
 */

package com.innobitsysytems.ipis.controller.setup;

import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
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
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.TrainStatus;
import com.innobitsysytems.ipis.services.setup.SetupService;

@RestController
@RequestMapping("/api/v1/setup")

public class TrainStatusController {

	private static final Logger log = LoggerFactory.getLogger(TrainStatusController.class);

	@Autowired
	public SetupService setupService;

	@GetMapping("/trainstatus")
	public ResponseEntity<Object> getAllTrainStatus() {

		List trainStatus = setupService.allStatus();
		return ResponseHandler.generateResponse("success", HttpStatus.OK, trainStatus);

	}

	@GetMapping("/alltrainstatus")
	public ResponseEntity<Object> getOnlyTrainStatus() throws HandledException {
		try {

			List details = setupService.getOnlyTrainStatusCoulmn();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, details);
		} catch (HandledException e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@GetMapping("/trainstatus/{id}")
	public ResponseEntity<Object> getTrainStatusById(@PathVariable(value = "id") Long id) throws HandledException {

		try {

			HashMap<String, Object> trainStatus = setupService.singleStatus(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, trainStatus);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/trainstatus")
	public ResponseEntity<Object> createTrainStatus(HttpServletRequest request,
			@Valid @RequestBody TrainStatus trainStatus) throws HandledException {

		try {

			HashMap<String, Object> createStatus = setupService.postStatus(request, trainStatus);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, createStatus);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/trainstatus/{id}")
	public ResponseEntity<Object> updateTrainStatus(@PathVariable(value = "id") @NotNull Long id,
			@RequestBody TrainStatus trainStatus) throws HandledException {
		try {

			HashMap<String, Object> putdetails = setupService.updateStatus(id, trainStatus);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, putdetails);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/trainstatus/{id}")
	public ResponseEntity<Object> deleteTrainStatus(@PathVariable(value = "id") long id) throws HandledException {

		try {

			Map<String, Boolean> statusData = setupService.deleteStatus(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, statusData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	// upload Files

	@PostMapping("/uploadTrainStatusFile/{language}")
	public ResponseEntity<Object> uploadStationCodeFile(StandardMultipartHttpServletRequest request,
			@PathVariable(value = "language") String language) throws HandledException, Exception {
		try {
			System.out.println();

			String filename = setupService.uploadTrainStatusFile(request, language);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, filename);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}
	}

	// delete upload single file
	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/deleteUpload/{language}/{filename}")
	public ResponseEntity<Object> deleteUploadedFile(@PathVariable(value = "language") String language,
			@PathVariable(value = "filename") String filename) throws Exception {
		try {
			String decoderFileName = URLDecoder.decode(filename,"UTF-8");

			Map<String, Boolean> statusData = setupService.deleteUploadedFile(decoderFileName, language);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, statusData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}
}