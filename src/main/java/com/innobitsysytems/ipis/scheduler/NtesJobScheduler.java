/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Ntes Job Scheduler
 */
package com.innobitsysytems.ipis.scheduler;

import org.springframework.scheduling.annotation.Scheduled;
import com.innobitsysytems.ipis.dto.NtesDto;
import com.innobitsysytems.ipis.services.ntes.NtesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NtesJobScheduler {
    @Autowired
    NtesService ntesService;


    @Autowired
    RestTemplate restTemplate;


    @Value( "${spring.ntes.url}" )
    private String ntesUrl;

//    @Scheduled(fixedRate = 1000)
   public void syncTrainData() {

        ResponseEntity<NtesDto[]> result = restTemplate.getForEntity(ntesUrl,NtesDto[].class);
        NtesDto[] resultArray = result.getBody();

        //Call Service to Process Data
        ntesService.scheduledTrainSync(resultArray);
        ntesService.liveTrainSync(resultArray);
       



    }









}
