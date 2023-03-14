package com.innobitsysytems.ipis.services.ntes;

import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import static java.lang.Long.parseLong;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.NtesDto;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.setup.TrainRepository;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestNtesBean {
	
	 @Autowired
	    private OnlineTrainRepository onlineTrainRepo;
	 
	    @Autowired
	    private TrainRepository trainRepo;	

	@Test
public void syncToDb() 
	{
	
//		//NtesDto[] apiData= {{},{},{}};
//		List<String> outputStream =  Arrays.stream(apiData).map(NtesDto::getTrainno).collect(Collectors.toList());
//
//        for(int i = 0 ; i < outputStream.size() ; i++)
//        {
//
//            try{
//
//                 if(trainRepo.existsByTrainNo(parseLong(outputStream.get(i))))
//                 {
//
//                     //Update Database
//                     //apiData
//                     Train  trainData =  trainRepo.findByTrainNo( parseLong(outputStream.get(i)));
//
//                     trainData.setSourceStation(apiData[i].getStation());
//                      trainData.setTrainType(apiData[i].getTraintype());
//                      trainData.getTrainName().setHindiTrainName(apiData[i].getTrainname());
//                      trainData.setDestinationStation(apiData[i].getDstn());
//                      trainData.getTrainDetails().setPlatformNo(apiData[i].getPlatformno());
//                 }
//                 else{
//
//                     //Insert into Database
//                     // trainRepo.save();
//
//                 }
//
//            }
//            catch( Exception ex){
//
//
//
//
//            }
//
//
//        }
	}
}


