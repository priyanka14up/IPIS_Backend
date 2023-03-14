package com.innobitsysytems.ipis.licensing;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.utility.CustomUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class LicenseController {

	@Autowired
	public CustomUtil customUtil;

	@Autowired
	public LicenseService licenseService;

	@PostMapping("/license")
	public ResponseEntity<Object> keyAuthenicate(@RequestBody KeyInputDto keyInputDto) throws HandledException {

		try {

			boolean macflag = licenseService.macAuthenticate(keyInputDto);

			boolean flag = licenseService.keyAuthenticate(keyInputDto);
			if (flag == true && macflag == true) {
				return ResponseHandler.generateResponse("product key verified successfully", HttpStatus.OK, flag);
			} else {
				return ResponseHandler.generateResponse("not a valid product key", HttpStatus.EXPECTATION_FAILED, flag);
			}

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

	@PostMapping("/keygenerate")
	public ResponseEntity<Object> keyGenarate(@RequestBody KeyInputDto keyInputDto) throws HandledException {

		try {

			String key = licenseService.keyGenerate(keyInputDto);

			return ResponseHandler.generateResponse("key generated successfully", HttpStatus.OK, key);
		}

		catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
		}

	}

}
