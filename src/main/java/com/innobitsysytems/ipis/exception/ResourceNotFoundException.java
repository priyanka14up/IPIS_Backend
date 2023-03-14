/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Resource Not Found Exception
 */
package com.innobitsysytems.ipis.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends Exception {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ResourceNotFoundException(String message) {
        super(message);
      }
	public ResourceNotFoundException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    
    }

