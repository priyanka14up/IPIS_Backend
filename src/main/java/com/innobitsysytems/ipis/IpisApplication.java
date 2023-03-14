/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Ipis Application
 */
package com.innobitsysytems.ipis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.innobitsysytems.ipis.exception.HandledException;

@SpringBootApplication
@EnableScheduling
public class IpisApplication {

	public static void main(String[] args) throws HandledException {
		System.setProperty("spring.devtools.restart.enabled", "false");
		SpringApplication.run(IpisApplication.class, args);

	}

}
