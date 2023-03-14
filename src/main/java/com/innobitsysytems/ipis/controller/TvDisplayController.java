/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Display Media Controller
 */

package com.innobitsysytems.ipis.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
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
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.support.ServletContextResource;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.dto.DisplayDto;
import com.innobitsysytems.ipis.dto.DisplayMediaDto;
import com.innobitsysytems.ipis.dto.MediaQueueDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.tvdisplay.MediaQueue;
import com.innobitsysytems.ipis.services.notification.NotificationService;
import com.innobitsysytems.ipis.services.tvdisplay.DisplayService;

@RestController
@RequestMapping("/api/v1")

public class TvDisplayController {

	@Autowired
	public DisplayService displayService;

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/display")
	public ResponseEntity<Object> getAllMessage() throws HandledException {

		try {

			List<DisplayDto> displayData = displayService.findAllDisplay();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, displayData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/display/{displayType}")
	public ResponseEntity<Object> getMessageById(@PathVariable(value = "displayType") String displayType)
			throws HandledException {

		try {

			DisplayDto singleDisplay = displayService.getSingleDisplay(displayType);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, singleDisplay);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/display")
	public ResponseEntity<Object> createMessageData(HttpServletRequest request,
			@Valid @RequestBody DisplayDto displayDto) throws HandledException {

		try {

			DisplayDto postDisplay = displayService.postDisplay(request, displayDto);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, postDisplay);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/media/{displayType}")
	public ResponseEntity<Object> getAllMedia(@PathVariable(value = "displayType") String displayType)
			throws HandledException {

		try {

			List<DisplayMediaDto> mediaData = displayService.findAllMedia(displayType);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/filemedia/{id}")
	public ResponseEntity<Object> getMediaById(@PathVariable(value = "id") Long id) throws HandledException {

		try {

			DisplayMediaDto mediaData = displayService.getSingleFile(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	// upload file media
	@PostMapping("/uploadFilemedia")
	public ResponseEntity<Object> uploadFilemedia(StandardMultipartHttpServletRequest request)
			throws HandledException, Exception {
		try {
			String filename = displayService.uploadFilemedia(request);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, filename);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}
	}

	@PostMapping("/filemedia/{displayType}")
	public ResponseEntity<Object> createMediaData(@PathVariable(value = "displayType") String displayType,
			HttpServletRequest request, @Valid @RequestBody DisplayMediaDto displayMediaDto) throws HandledException {

		try {

			DisplayMediaDto mediaData = displayService.postFile(displayType, request, displayMediaDto);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/filemedia/{id}")
	public ResponseEntity<Object> deleteMediaData(@PathVariable(value = "id") long id) throws HandledException {

		try {

			Map<String, Boolean> mediaData = displayService.deleteFile(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/movetoplaylist/{id}")
	public ResponseEntity<Object> movetoplaylist(@PathVariable(value = "id") long id) throws Exception {

		try {

			HashMap<String, Object> mediaData = displayService.moveToPlaylist(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/queuemedia/{displayType}")
	public ResponseEntity<Object> getAllQueue(@PathVariable(value = "displayType") String displayType)
			throws HandledException {

		try {

			List<MediaQueueDto> mediaData = displayService.findAll(displayType);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/queue/{id}")
	public ResponseEntity<Object> getQueueById(@PathVariable(value = "id") Long id) throws HandledException {

		try {

			MediaQueueDto mediaData = displayService.getSingleMedia(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/queue/{displayType}")
	public ResponseEntity<Object> createQueueData(@PathVariable(value = "displayType") String displayType,
			HttpServletRequest request, @Valid @RequestBody MediaQueueDto mediaQueueDto) throws HandledException {

		try {

			MediaQueueDto mediaData = displayService.postMedia(displayType, request, mediaQueueDto);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);
		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/queue/{id}")
	public ResponseEntity<Object> putQueueData(@PathVariable(value = "id") long id,
			@RequestBody MediaQueueDto mediaQueueDto) throws HandledException {

		try {

			MediaQueueDto mediaData = displayService.putMedia(id, mediaQueueDto);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/queue/{id}")
	public ResponseEntity<Object> deleteQueueData(@PathVariable(value = "id") long id) throws HandledException {

		try {

			Map<String, Boolean> mediaData = displayService.deleteMedia(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/moveup/{ids}/{id}")
	public ResponseEntity<Object> moveUp(@PathVariable List<Long> ids, @PathVariable Long id) throws Exception {

		try {

			List<MediaQueue> mediaData = displayService.moveUp(ids, id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/movedown/{ids}/{id}")
	public ResponseEntity<Object> moveDown(@PathVariable List<Long> ids, @PathVariable Long id) throws Exception {

		try {

			List<MediaQueue> mediaData = displayService.moveDown(ids, id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/mediaDisplay/{displayType}")
	public ResponseEntity<Object> mediaDisplay(@PathVariable String displayType) throws HandledException {

		try {

			Object obj = null;

			displayService.displaySelectedData(displayType);
			notificationService.tvDisplayNotification("change");

			return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/getTvDisplay")
	public ResponseEntity<Object> getTvDisplay() throws HandledException {

		try {

			HashMap<String, Object> displayData = displayService.getTvDisplay();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, displayData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/displayFiles")
	public ResponseEntity<Object> getAllFileMedia() throws Exception {

		try {

			List<String> displayData = displayService.getAllFileMedia();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, displayData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/exportdb/{filename}")
	public ResponseEntity<Object> exportDb(@PathVariable String filename) throws Exception {

		try {
			System.out.println("insidekjj55555555555");

			List<String> displayData = displayService.exportDb(filename);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, displayData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@PostMapping("/importdb/{filename}")
	public ResponseEntity<Object> importDb(@PathVariable String filename) throws Exception {

		try {

			Object obj = null;

			displayService.importDb(filename);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/uploadDb")
	public ResponseEntity<Object> uploadDb(StandardMultipartHttpServletRequest request)
			throws HandledException, Exception {
		try {
			String filename = displayService.uploadDb(request);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, filename);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}
	}

}
