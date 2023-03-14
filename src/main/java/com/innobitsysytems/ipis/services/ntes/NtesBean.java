/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Ntes Bean
 */
package com.innobitsysytems.ipis.services.ntes;


import com.innobitsysytems.ipis.dto.NtesDto;
import com.innobitsysytems.ipis.model.onlineTrain.OnlineTrain;
import com.innobitsysytems.ipis.model.setup.CoachDetails;
import com.innobitsysytems.ipis.model.setup.Train;
import com.innobitsysytems.ipis.model.setup.TrainDetails;
import com.innobitsysytems.ipis.model.setup.TrainLiveInfo;
import com.innobitsysytems.ipis.model.setup.TrainName;
import com.innobitsysytems.ipis.repository.OnlineTrainRepository;
import com.innobitsysytems.ipis.repository.setup.TrainRepository;
import com.innobitsysytems.ipis.utility.CustomUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static java.lang.Long.parseLong;

@Service
public class NtesBean implements  NtesService{

	
    @Autowired
    private OnlineTrainRepository onlineTrainRepo;
    @Autowired
    private TrainRepository trainRepository;

	@Autowired
	public CustomUtil customUtil;
	
	@Value( "${spring.mergedTrain}" )
    private Boolean mergedTrain;
  
   //@Transactional
    @Override
    public void scheduledTrainSync(NtesDto[] apiData){
    	
    	List<String> outputStream =  Arrays.stream(apiData).map(NtesDto::getTrainno).collect(Collectors.toList());
    	
    	for(int i = 0 ; i < outputStream.size() ; i++){
        
            try{
            	
            	TrainName trainName = new TrainName();
            	TrainDetails trainDetails = new TrainDetails();
            	TrainLiveInfo trainLiveInfo = new TrainLiveInfo();
				CoachDetails coachDetails = new CoachDetails();
				
            	// Update the train details
            	if(trainRepository.existsByTrainNo(parseLong(outputStream.get(i)))){
            		
            		Train  trainData =  trainRepository.findByTrainNo( parseLong(outputStream.get(i)));
            		String[] coaches=new String[] {apiData[i].getArrivalcoachposition()};
            		int maxLength=coaches.length;
            		
					trainData.setTrainNo(trainData.getTrainNo());
					trainData.setTrainName(trainName);
               	 	trainData.setTrainDetails(trainDetails);
               	 	trainData.setCoachDetails(coachDetails);
               	 	trainData.setTrainLiveInfo(trainLiveInfo);
                	trainData.setSourceStation(apiData[i].getStation());
                	trainData.setDestinationStation(apiData[i].getDstnname());
                	trainData.setTrainDirection(".");
                	trainData.setViaStation(new String[] {""});
                	trainData.setTrainType(apiData[i].getTraintype());
                	trainData.setTrainArrDepStatus(apiData[i].getDaysofarrival());
                	trainData.setCreatedBy("");
                	trainData.setCreatedAt(trainData.getCreatedAt());
					 
					trainLiveInfo.setTrainNo(trainData.getTrainNo());
                	trainLiveInfo.setArrDepartStatus(apiData[i].getDaysofarrival());
                	trainLiveInfo.setRunningStatus(null);
                	trainLiveInfo.setSdt(null);
                 	trainLiveInfo.setSat(null);
                	trainLiveInfo.setEat(null);
                	trainLiveInfo.setEdt("");
                	trainLiveInfo.setLate(null);
                	trainLiveInfo.setPlatformNo(apiData[i].getPlatformno());
                	 
                	trainName.setTrainNo(trainData.getTrainNo());
                	trainName.setEnglishTrainName(apiData[i].getTrainname());
                	trainName.setHindiTrainName(apiData[i].getTrainnamehindi());
                	trainName.setRegionalTrainName(apiData[i].getTraintypenamehindi());
                	trainName.setEnglishWaveFile("");
                	trainName.setHindiWaveFile("");
					trainName.setRegionalWaveFile("");
					trainName.setCreatedBy(" ");
					trainName.setCreatedAt(trainData.getCreatedAt());
					
					trainDetails.setTrainNo(trainData.getTrainNo());
					trainDetails.setScheduleArrivalTime(apiData[i].getSta());
                	trainDetails.setScheduleDepartureTime(apiData[i].getStd());
                	 
                	trainDetails.setMaximumCoach(maxLength);
					trainDetails.setRunningDays(new String[] {""});
                	trainDetails.setPlatformNo(apiData[i].getPlatformno());
                	trainDetails.setMergedTrains(mergedTrain);
					trainDetails.setMergedTrainNo(maxLength);
					trainDetails.setCreatedBy(" ");
					trainDetails.setCreatedAt(trainData.getCreatedAt());
					 
					coachDetails.setTrainNo(trainData.getTrainNo());
					coachDetails.setFrontSideEnd(" ");
					coachDetails.setBackSideEnd(" ");
                	coachDetails.setCoaches(coaches);
                	coachDetails.setCreatedBy(" ");
                	coachDetails.setUpdatedBy(" ");
                	coachDetails.setCreatedAt(trainData.getCreatedAt());
                	 
                	trainRepository.save(trainData);
                	 
               }else {
				   
				   // Post the train Data
                   
                   Train trainInformation = new Train();
                   String[] coaches=new String[] {apiData[i].getArrivalcoachposition()};
                   int maxLength=coaches.length;
                   trainInformation.setTrainNo(parseLong(apiData[i].getTrainno()));
                	
                   trainLiveInfo.setTrainNo(parseLong(apiData[i].getTrainno()));
                   trainLiveInfo.setArrDepartStatus(apiData[i].getDaysofarrival());
                   trainLiveInfo.setRunningStatus(null);
                   trainLiveInfo.setSdt(null);
                   trainLiveInfo.setSat(null);
                   trainLiveInfo.setEat(null);
                   trainLiveInfo.setEdt("");
                   trainLiveInfo.setLate(null);
                   trainLiveInfo.setPlatformNo(apiData[i].getPlatformno());
                	
                   trainInformation.setTrainNo(parseLong(apiData[i].getTrainno()));
                   trainInformation.setTrainType(apiData[i].getTraintype());
                   trainInformation.setSourceStation(apiData[i].getStation());
                   trainInformation.setDestinationStation(apiData[i].getDstnname());
                   trainInformation.setTrainDirection(".");
                   trainInformation.setViaStation(new String[] {""});
                   trainInformation.setTrainArrDepStatus(apiData[i].getDaysofarrival());
                	 
                   trainName.setTrainNo(parseLong(apiData[i].getTrainno()));
                   trainName.setEnglishTrainName(apiData[i].getTrainname());
                   trainName.setHindiTrainName(apiData[i].getTrainnamehindi());
                   trainName.setEnglishWaveFile("");
                   trainName.setHindiWaveFile("");
				   trainName.setRegionalWaveFile("");
				   trainName.setRegionalTrainName(apiData[i].getTraintypenamehindi());
				   trainName.setCreatedBy(" ");
					
                    trainDetails.setTrainNo(parseLong(apiData[i].getTrainno()));
                	trainDetails.setPlatformNo(apiData[i].getPlatformno());
                	trainDetails.setScheduleArrivalTime(apiData[i].getSta());
                	trainDetails.setScheduleDepartureTime(apiData[i].getStd());
                	trainDetails.setMaximumCoach(maxLength);
					trainDetails.setRunningDays(new String[] {""});
					trainDetails.setPlatformNo(apiData[i].getPlatformno());
					trainDetails.setMergedTrains(mergedTrain);
					trainDetails.setMergedTrainNo(Integer.parseInt(apiData[i].getTrainno()));
					trainDetails.setCreatedBy("");
					 
					coachDetails.setTrainNo(parseLong(apiData[i].getTrainno()));
                	coachDetails.setCoaches(coaches);
                	coachDetails.setCreatedBy("");
                	coachDetails.setBackSideEnd(" ");
                	coachDetails.setFrontSideEnd(" ");
                	
                	trainInformation.setTrainName(trainName);
                	trainInformation.setTrainDetails(trainDetails);
                	trainInformation.setCoachDetails(coachDetails);
                	trainInformation.setTrainLiveInfo(trainLiveInfo);
                	
                	trainRepository.save(trainInformation);
             
                }
                 
                if(onlineTrainRepo.existsByTrainNumber(parseLong(outputStream.get(i)))) {
                
         			OnlineTrain onlinetrainData=onlineTrainRepo.findBytrainNumber(parseLong(outputStream.get(i)));
                	 
                	onlinetrainData.setTrainName(apiData[i].getTrainname());
                	onlinetrainData.setCoaches(new String[] {apiData[i].getArrivalcoachposition()});
                	onlinetrainData.setArrDep(apiData[i].getDaysofarrival());
                	onlinetrainData.setPlatformNo(apiData[i].getPlatformno());
                    onlinetrainData.setSTA(onlinetrainData.getSTA());
                    onlinetrainData.setSTD(onlinetrainData.getSTD());
                    onlinetrainData.setBackEnd("");
                    onlinetrainData.setFrontEnd("");
                    onlinetrainData.setETA(onlinetrainData.getETA());
                    onlinetrainData.setETD(onlinetrainData.getETD());
                    onlinetrainData.setRepeatAnnouncement(null);
                    onlinetrainData.setLate("");
                    onlinetrainData.setTrainStatus(null);
                    onlineTrainRepo.save(onlinetrainData);
                      	 
                }else {
					
					OnlineTrain trainInfo=new OnlineTrain();
                    trainInfo.setTrainNumber(parseLong(apiData[i].getTrainno()));
                    trainInfo.setTrainName(apiData[i].getTrainname());
                    trainInfo.setCoaches(new String[] {apiData[i].getArrivalcoachposition()});
                    trainInfo.setArrDep(apiData[i].getDaysofarrival());
                    trainInfo.setPlatformNo(apiData[i].getPlatformno());
                    trainInfo.setSTA(null);
                    trainInfo.setSTD(null);
                    trainInfo.setBackEnd("");
                    trainInfo.setFrontEnd("");
                    trainInfo.setETA(null);
                    trainInfo.setETD(null);
                    trainInfo.setRepeatAnnouncement(null);
                    trainInfo.setLate("");
                    trainInfo.setTrainStatus(null);
                    onlineTrainRepo.save(trainInfo);
                      	 
                      	 
                    }
             
            }catch( Exception ex){
            	
            }
                                                                                                                                                                                     
        }

    }

	@Override
	public void liveTrainSync(NtesDto[] apiData) {
		
		List<String> outputStream =  Arrays.stream(apiData).map(NtesDto::getTrainno).collect(Collectors.toList());
      
	  	for(int i = 0 ; i < outputStream.size() ; i++){
        
            try{

            	TrainName trainName = new TrainName();
           	 	TrainDetails trainDetails = new TrainDetails();
           	 	TrainLiveInfo trainLiveInfo = new TrainLiveInfo();
				CoachDetails coachDetails = new CoachDetails();
            
				if(trainRepository.existsByTrainNo(parseLong(outputStream.get(i)))){
					
					String[] coaches=new String[] {apiData[i].getArrivalcoachposition()};
                	int maxLength=coaches.length;
                	 
                    Train  trainData =  trainRepository.findByTrainNo( parseLong(outputStream.get(i)));
                    trainData.setTrainNo(trainData.getTrainNo());
 					trainData.setTrainName(trainName);
                	trainData.setTrainDetails(trainDetails);
                	trainData.setCoachDetails(coachDetails);
                	trainData.setTrainLiveInfo(trainLiveInfo);
                 	trainData.setSourceStation(apiData[i].getStation());
                 	trainData.setDestinationStation(apiData[i].getDstnname());
                 	trainData.setTrainDirection(".");
                 	trainData.setViaStation(new String[] {""});
                 	trainData.setTrainType(apiData[i].getTraintype());
                 	trainData.setTrainArrDepStatus(apiData[i].getDaysofarrival());
                 	trainData.setCreatedBy("");
                 	trainData.setCreatedAt(trainData.getCreatedAt());
 					 
 					trainLiveInfo.setTrainNo(trainData.getTrainNo());
                 	trainLiveInfo.setArrDepartStatus(apiData[i].getDaysofarrival());
                 	trainLiveInfo.setRunningStatus(null);
                 	trainLiveInfo.setSdt(null);
                  	trainLiveInfo.setSat(null);
                 	trainLiveInfo.setEat(null);
                 	trainLiveInfo.setEdt("");
                 	trainLiveInfo.setLate(null);
                 	trainLiveInfo.setPlatformNo(apiData[i].getPlatformno());
                 	 
                 	trainName.setTrainNo(trainData.getTrainNo());
                 	trainName.setEnglishTrainName(apiData[i].getTrainname());
                 	trainName.setHindiTrainName(apiData[i].getTrainnamehindi());
                 	trainName.setRegionalTrainName(apiData[i].getTraintypenamehindi());
                 	trainName.setEnglishWaveFile("");
                 	trainName.setHindiWaveFile("");
 					trainName.setRegionalWaveFile("");
 					trainName.setCreatedBy(" ");
 					trainName.setCreatedAt(trainData.getCreatedAt());
 					
 					trainDetails.setTrainNo(trainData.getTrainNo());
 					trainDetails.setScheduleArrivalTime(apiData[i].getSta());
                 	trainDetails.setScheduleDepartureTime(apiData[i].getStd());
                 	trainDetails.setMaximumCoach(maxLength);
 					trainDetails.setRunningDays(new String[] {""});
                 	trainDetails.setPlatformNo(apiData[i].getPlatformno());
                 	trainDetails.setMergedTrains(mergedTrain);
 					trainDetails.setMergedTrainNo(Integer.parseInt(apiData[i].getTrainno()));
 					trainDetails.setCreatedBy(" ");
 					trainDetails.setCreatedAt(trainData.getCreatedAt());
 					 
 					coachDetails.setTrainNo(trainData.getTrainNo());
 					coachDetails.setFrontSideEnd(" ");
 					coachDetails.setBackSideEnd(" ");
                 	coachDetails.setCoaches(new String[] {apiData[i].getArrivalcoachposition()});
                 	coachDetails.setCreatedBy(" ");
                 	coachDetails.setUpdatedBy(" ");
                 	coachDetails.setCreatedAt(trainData.getCreatedAt());
                 	 
                 	trainRepository.save(trainData);
                 	 
                }else {
					
					Train trainInformation = new Train();
                    String[] coaches=new String[] {apiData[i].getArrivalcoachposition()};
                    int maxLength=coaches.length;
                     	
                    trainInformation.setTrainNo(parseLong(apiData[i].getTrainno()));
                      	
                    trainLiveInfo.setTrainNo(parseLong(apiData[i].getTrainno()));
                    trainLiveInfo.setArrDepartStatus(apiData[i].getDaysofarrival());
                	trainLiveInfo.setRunningStatus(null);
                    trainLiveInfo.setSdt(null);
                    trainLiveInfo.setSat(null);
                    trainLiveInfo.setEat(null);
                    trainLiveInfo.setEdt("");
                    trainLiveInfo.setLate(null);
                    trainLiveInfo.setPlatformNo(apiData[i].getPlatformno());
                      	
                    trainInformation.setTrainNo(parseLong(apiData[i].getTrainno()));
                    trainInformation.setTrainType(apiData[i].getTraintype());
                    trainInformation.setSourceStation(apiData[i].getStation());
                    trainInformation.setDestinationStation(apiData[i].getDstnname());
                    trainInformation.setTrainDirection(".");
                    trainInformation.setViaStation(new String[] {""});
                    trainInformation.setTrainArrDepStatus(apiData[i].getDaysofarrival());
                      	 
                    trainName.setTrainNo(parseLong(apiData[i].getTrainno()));
                    trainName.setEnglishTrainName(apiData[i].getTrainname());
                    trainName.setHindiTrainName(apiData[i].getTrainnamehindi());
                    trainName.setEnglishWaveFile("");
                    trainName.setHindiWaveFile("");
      				trainName.setRegionalWaveFile("");
      				trainName.setRegionalTrainName(apiData[i].getTraintypenamehindi());
      				trainName.setCreatedBy(" ");
      					
                    trainDetails.setTrainNo(parseLong(apiData[i].getTrainno()));
                    trainDetails.setPlatformNo(apiData[i].getPlatformno());
                    trainDetails.setScheduleArrivalTime(apiData[i].getSta());
                	trainDetails.setScheduleDepartureTime(apiData[i].getStd());
                    trainDetails.setMaximumCoach(maxLength);
      				trainDetails.setRunningDays(new String[] {""});
      				trainDetails.setPlatformNo(apiData[i].getPlatformno());
      				trainDetails.setMergedTrains(mergedTrain);
      				trainDetails.setMergedTrainNo(Integer.parseInt(apiData[i].getTrainno()));
      				trainDetails.setCreatedBy("");
      					 
      				coachDetails.setTrainNo(parseLong(apiData[i].getTrainno()));
      				coachDetails.setCoaches(coaches);
                    coachDetails.setCreatedBy("");
                    coachDetails.setBackSideEnd(" ");
                    coachDetails.setFrontSideEnd(" ");
                      	
                    trainInformation.setTrainName(trainName);
                    trainInformation.setTrainDetails(trainDetails);
                    trainInformation.setCoachDetails(coachDetails);
                    trainInformation.setTrainLiveInfo(trainLiveInfo);
                      	
                    trainRepository.save(trainInformation);
                   
                }
                
                if(onlineTrainRepo.existsByTrainNumber(parseLong(outputStream.get(i)))) {

                	OnlineTrain onlinetrainData=onlineTrainRepo.findBytrainNumber(parseLong(outputStream.get(i)));
                	 
                	onlinetrainData.setTrainName(apiData[i].getTrainname());
                	onlinetrainData.setCoaches(new String[] {apiData[i].getArrivalcoachposition()});
                	onlinetrainData.setArrDep(apiData[i].getDaysofarrival());
                	onlinetrainData.setPlatformNo(apiData[i].getPlatformno());
                    onlinetrainData.setSTA(onlinetrainData.getSTA());
                    onlinetrainData.setSTD(onlinetrainData.getSTD());
                    onlinetrainData.setBackEnd("");
                    onlinetrainData.setFrontEnd("");
                    onlinetrainData.setETA(onlinetrainData.getETA());
                    onlinetrainData.setETD(onlinetrainData.getETD());
                    onlinetrainData.setRepeatAnnouncement(null);
                    onlinetrainData.setLate("");
                    onlinetrainData.setTrainStatus(null);
                    onlineTrainRepo.save(onlinetrainData);
         			
                }else {
					
					OnlineTrain trainInfo=new OnlineTrain();
                    
					trainInfo.setTrainNumber(parseLong(apiData[i].getTrainno()));
                    trainInfo.setTrainName(apiData[i].getTrainname());
                    trainInfo.setCoaches(new String[] {apiData[i].getArrivalcoachposition()});
                    trainInfo.setArrDep(apiData[i].getDaysofarrival());
                    trainInfo.setPlatformNo(apiData[i].getPlatformno());
                    trainInfo.setSTA(null);
                    trainInfo.setSTD(null);
                    trainInfo.setBackEnd("");
                    trainInfo.setFrontEnd("");
                    trainInfo.setETA(null);
                    trainInfo.setETD(null);
                    trainInfo.setRepeatAnnouncement(null);
                    trainInfo.setLate("");
                    trainInfo.setTrainStatus(null);
                    onlineTrainRepo.save(trainInfo);
                    	  
                    }
                
            }catch( Exception ex){
            	
            }
                                                                                                                                                                                     
        }
	}
}
