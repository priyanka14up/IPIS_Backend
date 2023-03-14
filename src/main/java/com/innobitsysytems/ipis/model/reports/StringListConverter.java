/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2022
 * Purpose: String List Converter
 */
package com.innobitsysytems.ipis.model.reports;

import java.util.Arrays;
import java.util.List;

import javax.persistence.AttributeConverter;
import static java.util.Collections.*;

public class StringListConverter implements AttributeConverter<List<String>, String> {
	
private static final String SPLIT_CHAR = ";";
    
    @Override
    public String convertToDatabaseColumn(List<String> stringList) {
        return stringList != null ? String.join(SPLIT_CHAR, stringList) : "";
    }

    @Override
    public List<String> convertToEntityAttribute(String string) {
        return string != null ? Arrays.asList(string.split(SPLIT_CHAR)) : emptyList();
    }

}
