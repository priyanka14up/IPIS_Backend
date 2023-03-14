/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Super Master Dto
 */
package com.innobitsysytems.ipis.dto;

public class SuperMasterDto {
	private Long id;
	private Integer templateNumber;
	private String messageType;
	private String templateDescription;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Integer getTemplateNumber() {
		return templateNumber;
	}
	public void setTemplateNumber(Integer templateNumber) {
		this.templateNumber = templateNumber;
	}
	public String getMessageType() {
		return messageType;
	}
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}
	public String getTemplateDescription() {
		return templateDescription;
	}
	public void setTemplateDescription(String templateDescription) {
		this.templateDescription = templateDescription;
	}
	@Override
	public String toString() {
		return "SuperMasterDto [id=" + id + ", templateNumber=" + templateNumber + ", messageType=" + messageType
				+ ", templateDescription=" + templateDescription + "]";
	}
	
}
