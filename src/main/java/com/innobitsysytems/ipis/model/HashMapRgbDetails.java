package com.innobitsysytems.ipis.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innobitsysytems.ipis.model.setup.RgbDetails;

public class HashMapRgbDetails implements AttributeConverter<RgbDetails, String>{
	private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(RgbDetails parentDetails) {

        String parentDetailsJson = null;
        
        try {
        	
        	parentDetailsJson = objectMapper.writeValueAsString(parentDetails);
        	
        } catch (final JsonProcessingException e) {
        	
        	System.out.println("JSON writing error"+ e);
        }

        return parentDetailsJson;
        
    }
     
    
    @Override
    public RgbDetails convertToEntityAttribute(String rgbDetails) {

    	RgbDetails parentDetails = null;
        
        try {
        	
        	parentDetails = objectMapper.readValue(rgbDetails,RgbDetails.class);
        	
        } catch (final IOException e) {
        	
            System.out.println("JSON reading error"+e);
        }

        return parentDetails;
    }
    
   
}