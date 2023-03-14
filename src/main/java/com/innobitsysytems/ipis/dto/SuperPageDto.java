/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Super Page Dto
 */
package com.innobitsysytems.ipis.dto;

import java.util.Arrays;

public class SuperPageDto {
private Long id;
    private Integer pageNumber;

    private String pageDuration;
    private String templateDescription;
    private String messageDisplay;
    private String messageLanguage;
     private String[] tags;
     private String[] audioList;

    public String getTemplateDescription() {
        return templateDescription;
    }

    public void setTemplateDescription(String templateDescription) {
        this.templateDescription = templateDescription;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public String getMessageLanguage() {
        return messageLanguage;
    }

    public void setMessageLanguage(String messageLanguage) {
        this.messageLanguage = messageLanguage;
    }

    public String getMessageDisplay() {
        return messageDisplay;
    }

    public void setMessageDisplay(String messageDisplay) {
        this.messageDisplay = messageDisplay;
    }

    public String[] getAudioList() {
        return audioList;
    }

    public void setAudioList(String[] audioList) {
        this.audioList = audioList;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getPageDuration() {
        return pageDuration;
    }

    public void setPageDuration(String pageDuration) {
        this.pageDuration = pageDuration;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    

     @Override
    public String toString() {
        return "SuperPageDto{" +
                "id=" + id +
                ", pageNumber=" + pageNumber +
                ", pageDuration='" + pageDuration + '\'' +
                ", templateDescription='" + templateDescription + '\'' +
                ", messageDisplay='" + messageDisplay + '\'' +
                ", messageLanguage='" + messageLanguage + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", audioList=" + Arrays.toString(audioList) +
                '}';
    }

}
