package com.innobitsysytems.ipis.dto;

import javax.validation.constraints.NotBlank;

public class SoftResetDto {
	
		
		public String getBoardType() {
		return boardType;
	}

	public void setBoardType(String boardType) {
		this.boardType = boardType;
	}

		@NotBlank(message = "Board Type is mandatory")
	    private String boardType;
		
}
