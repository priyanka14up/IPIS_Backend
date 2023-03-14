package com.innobitsysytems.ipis.scheduler;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobitsysytems.ipis.dto.NtesDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.StationDetails;
import com.innobitsysytems.ipis.repository.setup.StationDetailsRepository;
import com.innobitsysytems.ipis.services.onlineTrain.OnlineTrainService;
import com.innobitsysytems.ipis.services.setup.SetupService;
import com.innobitsysytems.ipis.services.tvdisplay.DisplayService;

@Component
@RestController
public class OnlineTrainScheduler {
	@Autowired
	public SetupService setupService;
	
	@Autowired
	public OnlineTrainService onlineTrainService;
	
	@Autowired
	public StationDetailsRepository stationDetailsRepository;


	@Scheduled(fixedRate = 5000)
	public void scheduleAutoUploadTrain() throws Exception {
		List<StationDetails> station = stationDetailsRepository.findAll();
		try
		{
		if(station.size()>0)
		{
			
				//System.out.println("true");
				 setupService.autoTrainUpload();
			
		}
		else
		{
			throw new HandledException("Please fill station details first","No station Created");
		}
		}
		catch(Exception e)
		{
			 System.out.println(e.getMessage() + "Fill Station first");

		}			
	}

	@Scheduled(fixedRate = 5000)
	public void scheduleAutoDeleteTrain() throws Exception {
		List<StationDetails> station = stationDetailsRepository.findAll();
		try
		{
		if(station.size()>0)
		{
			
				//System.out.println("true");
				 setupService.autoDeleteTrain();
			
		}
		else
		{
			throw new HandledException("Please fill station details first","No station Created");
		}
		}
		catch(Exception e)
		{
			 System.out.println(e.getMessage() + "Fill Station first");

		}		
		
	}

	@Scheduled(fixedRate = 5000)
	public void scheduleAutoTadd() throws Exception {

		List<StationDetails> station = stationDetailsRepository.findAll();
		try
		{
		if(station.size()>0)
		{
			if(station.get(0).getAutoTadd().equals(true))
			{
				//System.out.println("true");
			onlineTrainService.displayAutoTadd();
			}
		}
		else
		{
			throw new HandledException("Please fill station details first","No station Created");
		}
		}
		catch(Exception e)
		{
			 System.out.println(e.getMessage() + "Fill Station first");

		}	
		
	}
	
//	@GetMapping("/autoupload")
//	public ResponseEntity<Object> getAllTrain() throws Exception {
//
//		String Data = setupService.autoTrainUpload();
//		return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
//
//	}
//
//	@GetMapping("/autoDelete")
//	public ResponseEntity<Object> autoDelete() throws Exception {
//
//		String Data = setupService.autoDeleteTrain();
//		return ResponseHandler.generateResponse("success", HttpStatus.OK, Data);
//
//	}
//	

}
