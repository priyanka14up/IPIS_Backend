/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Help Controller
 */

package com.innobitsysytems.ipis.controller;

import com.innobitsysytems.ipis.model.Help;
import com.innobitsysytems.ipis.repository.HelpRepository;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class HelpController {
	
	@Autowired
	private HelpRepository helpRepository;
	
	@GetMapping("/help")
	public List<Help> getAllHelp(){
		
		return helpRepository.findAll();
	}
}
