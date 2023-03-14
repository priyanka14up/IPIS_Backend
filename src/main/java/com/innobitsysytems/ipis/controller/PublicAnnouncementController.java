/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Public Announcement Controller
 */

package com.innobitsysytems.ipis.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.StandardMultipartHttpServletRequest;

import com.innobitsysytems.ipis.dto.AnnouncementPlaylistDto;
import com.innobitsysytems.ipis.dto.PublicAnnouncementDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.announcement.PublicAnnouncement;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;

@RestController
@RequestMapping("/api/v1")
public class PublicAnnouncementController {

	@Autowired
	public AnnouncementService announcementService;

	@GetMapping("/announcement")
	public ResponseEntity<Object> getAllFile() throws HandledException {

		try {

			List<PublicAnnouncement> annData = announcementService.list();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, annData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@GetMapping("/announcement/{id}")
	public ResponseEntity<Object> getFileById(@PathVariable(value = "id") Long id) throws HandledException {

		try {

			PublicAnnouncementDto annData = announcementService.getSingleAnnouncement(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, annData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/announcement")
	public ResponseEntity<Object> createFile(HttpServletRequest request,
			@Valid @RequestBody PublicAnnouncementDto publicAnn) throws HandledException {

		try {

			PublicAnnouncementDto postdetail = announcementService.create(request, publicAnn);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/manualAnnouncement")
	public ResponseEntity<Object> createManualRecord(HttpServletRequest request,
			@Valid @RequestBody PublicAnnouncement publicAnn) throws HandledException {

		try {
			PublicAnnouncement postdetail = announcementService.createManualRecordNew(request, publicAnn);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);
		} catch (HandledException e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<Object> createFile(StandardMultipartHttpServletRequest request)
			throws HandledException, Exception {
		try {
			String filename = announcementService.uplLoadFile(request);

			return ResponseHandler.generateResponse("success", HttpStatus.OK, filename);
		} catch (Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/announcement/{id}")
	public ResponseEntity<Object> deleteFile(@PathVariable(value = "id") long id) throws HandledException {

		try {

			Map<String, Boolean> messageData = announcementService.delete(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, messageData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/announcement/moveToPlaylist/{id}")
	public ResponseEntity<Object> moveToPlaylist(@PathVariable(value = "id") long id) throws HandledException {

		try {

			HashMap<String, Object> postdetail = announcementService.moveToPlaylist(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, postdetail);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/play/{repeat}")
	public ResponseEntity<Object> musicPlay(@PathVariable int repeat) throws Exception {

		try {

			String announcementData = announcementService.musicPlayer(repeat);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcementData);

		} catch (Exception e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/pause")
	public ResponseEntity<Object> musicStop() throws Exception {

		try {

			String announcementData = announcementService.pausePlay();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcementData);

		} catch (Exception e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/stop")
	public ResponseEntity<Object> stopSong() throws Exception {

		try {

			String announcementData = announcementService.stop();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcementData);

		} catch (Exception e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/next")
	public ResponseEntity<Object> musicPause() throws Exception {

		try {

			String announcementData = announcementService.nextPlay();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcementData);

		} catch (Exception e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}
	}

	@GetMapping("/playlist")
	public ResponseEntity<Object> getAllPlaylist() throws HandledException {

		try {

			List<AnnouncementPlaylist> announcementData = announcementService.getAll();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcementData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/playlist/{id}")
	public ResponseEntity<Object> getMediaById(@PathVariable(value = "id") long id) throws HandledException {
		try {

			AnnouncementPlaylistDto announcementData = announcementService.getSingleFile(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcementData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	// not in use
	@PostMapping("/playlist")
	public ResponseEntity<Object> createDevice(HttpServletRequest request,
			@RequestBody AnnouncementPlaylistDto announcementPlaylist) throws HandledException {

		try {

			AnnouncementPlaylistDto deviceData = announcementService.createPlaylist(request, announcementPlaylist);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/playlist/{id}/{announcementId}")
	public ResponseEntity<Object> deleteDevicev1(@PathVariable(value = "id") long id,
			@PathVariable(value = "announcementId") long announcementId) throws HandledException {

		try {

			HashMap<String, Object> deviceData = announcementService.deleteFile(id, announcementId);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@GetMapping("/playlist/moveup/{ids}/{id}")
	public ResponseEntity<Object> moveUp(@PathVariable List<Long> ids, @PathVariable Long id) throws HandledException {

		try {

			List<AnnouncementPlaylist> mediaData = announcementService.moveUp(ids, id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@GetMapping("/playlist/movedown/{ids}/{id}")
	public ResponseEntity<Object> moveDown(@PathVariable List<Long> ids, @PathVariable Long id)
			throws HandledException {

		try {

			List<AnnouncementPlaylist> mediaData = announcementService.moveDown(ids, id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, mediaData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

}
