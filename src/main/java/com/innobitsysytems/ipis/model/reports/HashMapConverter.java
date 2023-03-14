/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: HashMapConverter
 */
package com.innobitsysytems.ipis.model.reports;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


import javax.persistence.AttributeConverter;
//import com.baeldung.hibernate.interceptors.CustomInterceptor;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class HashMapConverter implements AttributeConverter<List<CoachDetailsReports>, String>{
	private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<CoachDetailsReports> customerInfo) {

        String customerInfoJson = null;
        try {
            customerInfoJson = objectMapper.writeValueAsString(customerInfo);
        } catch (final JsonProcessingException e) {
        	System.out.println("JSON writing error"+ e);
        }

        return customerInfoJson;
    }

    @Override
    public List<CoachDetailsReports> convertToEntityAttribute(String customerInfoJSON) {

         List<CoachDetailsReports> customerInfo = null;
        try {
            customerInfo = objectMapper.readValue(customerInfoJSON, ArrayList.class);
        } catch (final IOException e) {
            System.out.println("JSON reading error"+e);
        }

        return customerInfo;
    }
}
