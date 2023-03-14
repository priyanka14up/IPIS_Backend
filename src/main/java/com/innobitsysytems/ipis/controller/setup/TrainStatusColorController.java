package com.innobitsysytems.ipis.controller.setup;
/**
 * @author: Fardeen Mirza
 * @createdAt: 06/12/2022
 */
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.setup.CoachData;
import com.innobitsysytems.ipis.model.setup.Intensity;
import com.innobitsysytems.ipis.model.setup.IvdScreenColorConfig;
import com.innobitsysytems.ipis.model.setup.RgbDetails;
import com.innobitsysytems.ipis.model.setup.TrainStatusColor;
import com.innobitsysytems.ipis.repository.DeviceRepository;
import com.innobitsysytems.ipis.repository.setup.IvdScreenColorConfigRepository;
import com.innobitsysytems.ipis.repository.setup.TrainStatusColorRepository;
import com.innobitsysytems.ipis.services.setup.SetupService;
import com.innobitsysytems.ipis.utility.Constants;

@RestController
@CrossOrigin(allowedHeaders = "*")
@RequestMapping("/api/v1/setup")
public class TrainStatusColorController {
	private static final Logger logger = LoggerFactory.getLogger(TrainStatusColorController.class);

	@Autowired
	public TrainStatusColorRepository trainStatusColorRepository;
	@Autowired
	public IvdScreenColorConfigRepository ivdScreenColorConfigRepository;
	@Autowired
	public SetupService setupService;

	@GetMapping("/get-train-status-color/{status}")
	public ResponseEntity<Object> getStatusColorByStatusName(@PathVariable(value = "status") String status) {
		try {
			String arrDep = String.valueOf(status.charAt(0));
			HashMap<String, Object> postdetail = setupService.getTrainStatusColor( status.substring(1,status.length()),arrDep);
			if(postdetail.containsValue(Constants.NO_DATA_FOUND))
				return ResponseHandler.generateResponse("no records with given status", HttpStatus.NOT_FOUND, postdetail);
			else
				return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}
	
	@GetMapping("/get-ivd-screen-color-config/{id}")
	public ResponseEntity<Object> getIvdScreenColorConfig(@PathVariable(value = "id") long id) {
		try {
			HashMap<String, Object> postdetail = setupService.getIvdScreenColorConfig(id);
			if(postdetail.containsValue(Constants.NO_DATA_FOUND))
				return ResponseHandler.generateResponse("no records with given id", HttpStatus.NOT_FOUND, postdetail);
			else
				return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PostMapping("/add-status-color")
	public  ResponseEntity<Object> saveTrainStatusColor(@RequestBody TrainStatusColor trainStatusColor){
		try {
			HashMap<String, Object> postdetail = setupService.postTrainStatusColor(trainStatusColor);
			if(postdetail.containsValue(Constants.ERROR_OCCURED_AT_SERVER))
				return ResponseHandler.generateResponse(Constants.FAILED_TO_SAVE, HttpStatus.BAD_REQUEST, postdetail);
			return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/add-ivd-screen-color-config")
	public   ResponseEntity<Object> saveIvdScreenColorConfig(@RequestBody IvdScreenColorConfig ivdScreenColorConfig) {
		try {
			HashMap<String, Object> postdetail = setupService.postIvdScreenColorConfig(ivdScreenColorConfig);
			if(postdetail.containsKey(Constants.ERROR_MESSAGE))
				return ResponseHandler.generateResponse(postdetail.get(Constants.ERROR_MESSAGE).toString(), HttpStatus.BAD_REQUEST, postdetail);
			else if(postdetail.containsValue(Constants.ERROR_OCCURED_AT_SERVER))
				return ResponseHandler.generateResponse(Constants.FAILED_TO_SAVE, HttpStatus.BAD_REQUEST, postdetail);
			return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}
	}

	@PutMapping("/update-train-status-color")
	public  ResponseEntity<Object> updateTrainStatusColor(@RequestBody TrainStatusColor trainStatusColor){
		try {
			HashMap<String, Object> postdetail = setupService.updateTrainStatusColor(trainStatusColor);
			if(postdetail.containsValue(Constants.ERROR_OCCURED_AT_SERVER))
				return ResponseHandler.generateResponse(Constants.FAILED_TO_UPDATE, HttpStatus.NOT_FOUND, postdetail);
			return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PutMapping("/update-ivd-screen-config")
	public  ResponseEntity<Object> updateIvdScreenColorConfig(@RequestBody IvdScreenColorConfig ivdScreenColorConfig){
		try {
			HashMap<String, Object> postdetail = setupService.updateIvdScreenColorConfig(ivdScreenColorConfig);
			if(postdetail.containsValue(Constants.ERROR_OCCURED_AT_SERVER))
				return ResponseHandler.generateResponse(Constants.FAILED_TO_UPDATE, HttpStatus.NOT_FOUND, postdetail);
			return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}
	 
	@PostMapping("/send-color-packet")
	public  ResponseEntity<Object> sendTrainColorPacket(){
		try {
			HashMap<String, Object> postdetail = setupService.sendTrainColorPacket();
			if(postdetail.containsValue(Constants.ERROR_OCCURED_AT_SERVER))
				return ResponseHandler.generateResponse(Constants.FAILED_TO_SAVE, HttpStatus.BAD_REQUEST, postdetail);
			return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}
	@PostMapping("/send-video-packet")
	public  ResponseEntity<Object> sendVideoPacket(){
		try {
			HashMap<String, Object> postdetail = setupService.sendVideoPacket();
			if(postdetail.containsValue(Constants.ERROR_OCCURED_AT_SERVER))
				return ResponseHandler.generateResponse(Constants.FAILED_TO_SAVE, HttpStatus.BAD_REQUEST, postdetail);
			return ResponseHandler.generateResponse(Constants.SUCCESS_MESSAGE, HttpStatus.OK, postdetail);
		}catch(Exception e) {
			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}
}
