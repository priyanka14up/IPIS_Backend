/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: System Configuration
 */
package com.innobitsysytems.ipis.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SystemConfig {

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder.build();
	}
}
