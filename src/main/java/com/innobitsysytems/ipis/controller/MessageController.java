/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Message Controller
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
import com.innobitsysytems.ipis.model.Message;
import com.innobitsysytems.ipis.model.device.DeviceType;
import com.innobitsysytems.ipis.services.message.MessageService;

@RestController
@RequestMapping("/api/v1")
public class MessageController {

	@Autowired
	public MessageService messageService;

	@GetMapping("/message")
	public ResponseEntity<Object> getAllMessage() throws HandledException {

		try {

			List messageData = messageService.allMessage();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, messageData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/message/{id}")
	public ResponseEntity<Object> getMessageById(@PathVariable(value = "id") Long id) throws HandledException {

		try {

			HashMap<String, Object> singlemessage = messageService.getSingleMessage(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, singlemessage);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@GetMapping("/platform/{deviceType}")
	public ResponseEntity<Object> getMsgDevicePlatform(@PathVariable DeviceType deviceType) throws HandledException {

		try {

			HashSet<String> platform = messageService.getDevicePlatform(deviceType);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, platform);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@GetMapping("/message/{deviceType}/{platformNo}")
	public ResponseEntity<Object> getMsgDeviceId(@PathVariable DeviceType deviceType, @PathVariable String platformNo)
			throws HandledException {

		try {

			List<String> deviceId = messageService.getDeviceId(deviceType, platformNo);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, deviceId);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/message")
	public ResponseEntity<Object> createMessageData(HttpServletRequest request, @Valid @RequestBody Message message)
			throws HandledException {

		try {

			HashMap<String, Object> postDetail = messageService.createMessage(request, message);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, postDetail);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/displaymessage")
	public ResponseEntity<Object> displayMessage() throws HandledException {

		try {

			Object obj = null;
			messageService.displayMedia();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, obj);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/message/{id}")
	public ResponseEntity<Object> updateMessage(@PathVariable(value = "id") @NotNull Long id, @RequestBody Message msg)
			throws HandledException {

		try {

			HashMap<String, Object> putdetails = messageService.putMessage(id, msg);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, putdetails);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/message/{id}")
	public ResponseEntity<Object> deleteMessageData(@PathVariable(value = "id") long id) throws HandledException {

		try {

			Map<String, Boolean> messageData = messageService.deleteMessage(id);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, messageData);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

}
