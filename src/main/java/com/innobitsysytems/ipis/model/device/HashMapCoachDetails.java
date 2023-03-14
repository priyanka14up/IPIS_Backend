/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: HashMap of CoachDetails 
 */
package com.innobitsysytems.ipis.model.device;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.AttributeConverter;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapCoachDetails  implements AttributeConverter<List<CoachesDetail>, String>{
	
	private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CoachesDetail> coachDetails) {

        String coachDetailsJson = null;
        
        try {
        	
        	coachDetailsJson = objectMapper.writeValueAsString(coachDetails);
        	
        } catch (final JsonProcessingException e) {
        	
        	System.out.println("JSON writing error"+ e);
        }

        return coachDetailsJson;
    }

    @Override
    public List<CoachesDetail> convertToEntityAttribute(String coachDetailsJSON) {

        List<CoachesDetail> coachDetails = null;
        
        try {
        	
        	coachDetails = objectMapper.readValue(coachDetailsJSON, ArrayList.class);
        	
        } catch (final IOException e) {
        	
            System.out.println("JSON reading error"+e);
        }

        return coachDetails;
    }
    

}
