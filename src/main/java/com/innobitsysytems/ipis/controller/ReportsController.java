/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2022
 * Purpose: Reports Controller
 */

package com.innobitsysytems.ipis.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.innobitsysytems.ipis.services.reports.ReportService;
import com.innobitsysytems.ipis.dto.AnnouncementDto;
import com.innobitsysytems.ipis.dto.CgdbTransmissionDto;
import com.innobitsysytems.ipis.dto.LinkCheckDto;
import com.innobitsysytems.ipis.dto.LoginDto;
import com.innobitsysytems.ipis.dto.TrainTransmissionDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;

@RestController
@RequestMapping("/api/v1")
public class ReportsController {

	@Autowired
	private ReportService reportService;

	@GetMapping("/reports/users")
	public ResponseEntity<Object> getAllUsers() {

		List userData = reportService.getAllUsers();
		return ResponseHandler.generateResponse("success", HttpStatus.OK, userData);

	}

	@GetMapping("/reports/linkCheck")
	public ResponseEntity<Object> getAllLinkCheckReports(
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateStart,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateEnd,
			@RequestParam(required = true) Long userid) throws HandledException {

		try {

			List<LinkCheckDto> linkcheck = reportService.getAllLinkCheckReports(DateStart, DateEnd, userid);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, linkcheck);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/reports/login")
	public ResponseEntity<Object> getAllLoginReports(
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateStart,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateEnd,
			@RequestParam(required = true) Long userid) throws HandledException {

		try {

			List<LoginDto> login = reportService.getAllLoginReports(DateStart, DateEnd, userid);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, login);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/reports/announcement")
	public ResponseEntity<Object> getAllAnnouncementReports(
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateStart,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateEnd,
			@RequestParam(required = true) Long userid) throws HandledException {

		try {

			List<AnnouncementDto> announcement = reportService.getAllAnnouncementReports(DateStart, DateEnd, userid);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, announcement);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/reports/traintransmission")
	public ResponseEntity<Object> getAllTrainTransmissionReports(
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateStart,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateEnd,
			@RequestParam(required = true) Long userid) throws HandledException {

		try {

			List<TrainTransmissionDto> trainTransmission = reportService.getAllTrainTransmissionReports(DateStart,
					DateEnd, userid);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, trainTransmission);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@GetMapping("/reports/cgdbtransmission")
	public ResponseEntity<Object> getAllCgdbTransmissionReports(
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateStart,
			@RequestParam(required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date DateEnd,
			@RequestParam(required = true) Long userid) throws HandledException {

		try {

			List<CgdbTransmissionDto> cgdbTransmission = reportService.getAllCgdbTransmissionReports(DateStart, DateEnd,
					userid);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, cgdbTransmission);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}
}
