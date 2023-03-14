/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Spring Login Exception
 */
package com.innobitsysytems.ipis.exception;

public class SpringLoginException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SpringLoginException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public SpringLoginException(String exMessage) {
        super(exMessage);
    }
}