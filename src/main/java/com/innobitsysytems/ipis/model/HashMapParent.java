/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: HashMapParent
 */
package com.innobitsysytems.ipis.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innobitsysytems.ipis.model.device.ChildrenDetails;


public class HashMapParent implements AttributeConverter<List<ChildrenDetails>, String>{
	private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<ChildrenDetails> parentDetails) {

        String parentDetailsJson = null;
        
        try {
        	
        	parentDetailsJson = objectMapper.writeValueAsString(parentDetails);
        	
        } catch (final JsonProcessingException e) {
        	
        	System.out.println("JSON writing error"+ e);
        }

        return parentDetailsJson;
        
    }
    
    @Override
    public List<ChildrenDetails> convertToEntityAttribute(String parentDetailsJSON) {

        List<ChildrenDetails> parentDetails = null;
        
        try {
        	
        	parentDetails = objectMapper.readValue(parentDetailsJSON, ArrayList.class);
        	
        } catch (final IOException e) {
        	
            System.out.println("JSON reading error"+e);
        }

        return parentDetails;
    }
    
   
}

