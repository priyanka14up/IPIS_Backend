package com.innobitsysytems.ipis.scheduler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.services.tvdisplay.DisplayService;

@Component
@RestController
public class TVScheduler {
	@Autowired
	public DisplayService displayService;
	
	@GetMapping("/autotvDatachange")
	public ResponseEntity<Object> getCriticalTimeTrain() throws Exception {

		String Data = displayService.autoTrainExpectedTime();
		return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);

	}
	
	@Scheduled(fixedRate = 1000)
	public void scheduleAutoTrainEtaNotification() throws Exception {
		displayService.autoTrainExpectedTime();	}
	

}
