/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Handled Exception
 */
package com.innobitsysytems.ipis.exception;

public class HandledException extends Exception {
	
	
	private String code;

    public HandledException(String code, String message) {
        super(message);
        this.setCode(code);
    }

    public HandledException(String code, String message, Throwable cause) {
        super(message, cause);
        this.setCode(code);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

	public String getAnnouncementMasterData() {
		// TODO Auto-generated method stub
		return null;
	}

    public String getAnnouncementMasterPageDescription() {
        return null;
    }
}
