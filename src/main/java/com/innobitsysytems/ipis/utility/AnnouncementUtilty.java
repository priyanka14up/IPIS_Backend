/**
 * 
 */
package com.innobitsysytems.ipis.utility;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.innobitsysytems.ipis.model.announcement.AttentionAnnouncements;
import com.innobitsysytems.ipis.model.announcement.ChimesAnnouncement;
import com.innobitsysytems.ipis.model.announcement.ExpectedToAnnouncement;

import com.innobitsysytems.ipis.model.announcement.FromAnnouncement;
import com.innobitsysytems.ipis.model.announcement.HindiAddAnnouncement;
import com.innobitsysytems.ipis.model.announcement.HoursAnnouncement;
import com.innobitsysytems.ipis.model.announcement.InConvenienceAnnouncement;
import com.innobitsysytems.ipis.model.announcement.ItIsAnnouncement;
import com.innobitsysytems.ipis.model.announcement.MinutesAnnouncement;
import com.innobitsysytems.ipis.model.announcement.NumAudioAnnouncement;
import com.innobitsysytems.ipis.model.announcement.PlatformAnnouncement;
import com.innobitsysytems.ipis.model.announcement.SchedulesToArriveAt;
import com.innobitsysytems.ipis.model.announcement.StationAnnouncement;
import com.innobitsysytems.ipis.model.announcement.TimeAnnouncement;
import com.innobitsysytems.ipis.model.announcement.ToAnnouncement;
import com.innobitsysytems.ipis.model.announcement.TrainNameAnnouncements;
import com.innobitsysytems.ipis.model.announcement.TrainNameEntryAnnouncement;
import com.innobitsysytems.ipis.model.announcement.TrainNoPrefixAnnouncements;
import com.innobitsysytems.ipis.model.announcement.TrainStatusEntryAnnouncement;
import com.innobitsysytems.ipis.model.announcement.ViaAnnouncement;
import com.innobitsysytems.ipis.model.setup.StationCode;
import com.innobitsysytems.ipis.model.setup.TrainStatus;
import com.innobitsysytems.ipis.repository.announcement.AttentionAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.ChimesAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.ExpectedToAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.FromAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.HindiAddAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.HoursAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.InconvenienceAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.ItIsAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.MinutesAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.NumAudioAnnRepository;
import com.innobitsysytems.ipis.repository.announcement.PlatformAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.SchedulesToAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.StationAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.TimeAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.ToAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.TrainNameAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.TrainNameEntryAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.TrainNoAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.TrainStatusAnnoRepository;
import com.innobitsysytems.ipis.repository.announcement.ViaAnnoRepository;
import com.innobitsysytems.ipis.repository.setup.StationCodeRepository;
import com.innobitsysytems.ipis.repository.setup.TrainStatusRepository;

/**
 * @author kaustubh garg
 *
 */
@Service
public class AnnouncementUtilty {
	
	@Autowired
	public TrainNoAnnoRepository trainNoAnnouncementrepo;
	

	@Autowired
	public InconvenienceAnnoRepository inconvenienceAnnoRepo;
	
	@Autowired
	public ExpectedToAnnoRepository expectedToAnnoRepo;
	
	@Autowired
	public SchedulesToAnnoRepository schedulesToAnnoRepo;
	
	
	@Autowired
	public ItIsAnnoRepository itIsAnnoRepository;
	
	@Autowired
	public HoursAnnoRepository hoursAnnoRepository;
	
	@Autowired
	public MinutesAnnoRepository minutesAnnoRepository;
	
	@Autowired
	public ViaAnnoRepository viaAnnoRepository;
	
	
	@Autowired
	public ToAnnoRepository toAnnoRepository;
	
	@Autowired
	public StationAnnoRepository stationAnnoRepository;
	
	
	@Autowired
	public ChimesAnnoRepository chimesAnnoRepository;
		
		
	@Autowired
	public AttentionAnnoRepository attentionAnnoRepository;
	
	@Autowired
	public HindiAddAnnoRepository hindiAddAnnoRepository;
	
	
	@Autowired
	public FromAnnoRepository fromAnnoRepository;
	
	
	@Autowired
	public NumAudioAnnRepository numAudioAnnouncementrepo;
	
	@Autowired
	public TrainNameAnnoRepository trainNameAnnoRepo;
	
	@Autowired
	public PlatformAnnoRepository platformAnnoRepo;
	
	@Autowired
	public TrainStatusAnnoRepository trainStatusAnnoRepo;
	
	@Autowired
	public TimeAnnoRepository timeAnnoRepo;
	
	@Autowired
	public TrainStatusRepository trainStatusRepository;
	
	@Autowired
	public StationCodeRepository stationCodeRepository;
	
	@Autowired
	public TrainNameEntryAnnoRepository trainNameEntryAnnoRepository;
	
	String language=null;
	
	String trainStstus=null;
	
	String Aord=null;
	
	String homeDir = System.getProperty("user.home")+"\\";
	
	public String trainStatus(String status){
		
		trainStstus=status;
		return trainStstus;
	}
	
	public String languages(String lang){
		
		language=lang;
		return language;
	}
	
	public String Late(String late) {
		
		return null;
	}

	public String Aord(String aord) {
		
		Aord=aord;
		
		return Aord;
	}
	
	
	public String Attention() {
		
		List<AttentionAnnouncements> AttentionAnn = attentionAnnoRepository.findAll();
		
		String audioStr = "";
		
		for(int i=0;i<AttentionAnn.size();i++) 
		{
			
			if(language.equals(AttentionAnn.get(i).getLanguage()))
			{
				audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(i).getAudioFile().toString())+",";
				}
		}
	
		return audioStr;
		
	}
	
	public String HindiAddition() {
		
		List<HindiAddAnnouncement> AttentionAnn = hindiAddAnnoRepository.findAll();
		
		String audioStr = "";
		
			if(Aord.equals("A"))
			{
				System.out.println(" inside Arival");
				audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(0).getAudioFile().toString())+",";
			}
			else if(Aord.equals("D"))
			{
				System.out.println(" inside D");

				audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(1).getAudioFile().toString())+",";
			}
	
		return audioStr;
		
	}
	
public String HindiAddition1() {
		
		List<HindiAddAnnouncement> AttentionAnn = hindiAddAnnoRepository.findAll();
		
		String audioStr = "";
		
			
				audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(0).getAudioFile().toString())+",";
			
	
		return audioStr;
		
	}
	

public String HindiAddition2() {
	
	List<HindiAddAnnouncement> AttentionAnn = hindiAddAnnoRepository.findAll();
	
	String audioStr = "";
	
		
			audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(2).getAudioFile().toString())+",";
		

	return audioStr;
	
}

public String HindiAddition3() {
	
	List<HindiAddAnnouncement> AttentionAnn = hindiAddAnnoRepository.findAll();
	
	String audioStr = "";
	
		
			audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(3).getAudioFile().toString())+",";
		

	return audioStr;
	
}

public String HindiAddition4() {
	
	List<HindiAddAnnouncement> AttentionAnn = hindiAddAnnoRepository.findAll();
	
	String audioStr = "";
	
		
			audioStr=audioStr.concat(homeDir).concat(AttentionAnn.get(4).getAudioFile().toString())+",";
		

	return audioStr;
	
}
	

	public String TrainNumber(String trainNumber) {
		
		List<TrainNoPrefixAnnouncements> trainAnn = trainNoAnnouncementrepo.findAll();
		List<NumAudioAnnouncement> numAnn = numAudioAnnouncementrepo.findAll();
		String audioStr = "";
		
		for(int i=0;i<trainAnn.size();i++) {
			
			if(language.equals(trainAnn.get(i).getLanguage()))
			{
				audioStr=audioStr.concat(homeDir).concat(trainAnn.get(i).getAudioFile().toString())+",";
			
		
				String[] str = trainNumber.split("",6);
			
				
			for(int j=0;j<str.length;j++) {
				
				for(int k=0; k<numAnn.size();k++) {
					
					if(language.equals(numAnn.get(k).getLanguage()) && str[j].equals(numAnn.get(k).getNumber())) {
							
						audioStr = audioStr.concat(homeDir).concat(numAnn.get(k).getAudioFile().toString())+",";
						break;
						
					}
				}
				
			}
			
		}
	}
	
		return audioStr;
		
	}
	

	public String TrainName(String trainNo) {
		
		String audioStr="";
		
		List<TrainNameEntryAnnouncement> trainNames=trainNameEntryAnnoRepository.findAll();
		
		for(int i=0;i<trainNames.size();i++)
		{
			if(language.equals("English")&&trainNo.equals(trainNames.get(i).getTrainName()))
			{
				System.out.println("trainNo"+trainNo);
				System.out.println("trainNames.get(i).getTrainName())"+trainNames.get(i).getTrainName());
				audioStr = audioStr.concat(homeDir).concat(trainNames.get(i).getEnglishWaveFile().toString())+",";
			}
			else if(language.equals("Hindi")&&trainNo.equals(trainNames.get(i).getTrainName()))
			{
				audioStr = audioStr.concat(homeDir).concat(trainNames.get(i).getHindiWaveFile().toString())+",";
			}
			
		}

		return audioStr;
		
		
	}
	
public String From() {
	
	List<FromAnnouncement> fromAnn = fromAnnoRepository.findAll();
	
	String audioStr = "";
	
	for(int i=0;i<fromAnn.size();i++) 
	{
		
		if(language.equals(fromAnn.get(i).getLanguage()))
		{
			audioStr=audioStr.concat(homeDir).concat(fromAnn.get(i).getAudioFile().toString())+",";
			}
	}

	return audioStr;
	
}

public String Chimes() {
	
	List<ChimesAnnouncement> chimesAnn = chimesAnnoRepository.findAll();
	
	String audioStr = "";
	
	
			audioStr=audioStr.concat(homeDir).concat(chimesAnn.get(0).getAudioFile().toString())+",";
			audioStr=audioStr.concat(homeDir).concat(chimesAnn.get(0).getAudioFile().toString())+",";
	

	return audioStr;
	
}






public String Itis() {
	
	List<ItIsAnnouncement> itAnn = itIsAnnoRepository.findAll();
	
	String audioStr = "";
	
	for(int i=0;i<itAnn.size();i++) 
	{
		
		if(language.equals(itAnn.get(i).getLanguage()))
		{
			audioStr=audioStr.concat(homeDir).concat(itAnn.get(i).getAudioFile().toString())+",";
			}
	}

	return audioStr;
	
}

public String ExpectedTo(String aord)
{
	List<ExpectedToAnnouncement> expectedAnn = expectedToAnnoRepo.findAll();
	
	String audioStr = "";
	
	for(int i=0;i<expectedAnn.size();i++) 
	{
		
		if(language.equals(expectedAnn.get(i).getLanguage())&& aord.equals(expectedAnn.get(i).getAord()))
		{
			audioStr=audioStr.concat(homeDir).concat(expectedAnn.get(i).getAudioFile().toString())+",";
		
			}
	}

	return audioStr;
	
}


public String trainStatus(String trainStat, String aord) {
	
	String audioStr="";
	
	List<TrainStatus> trainStatuses=trainStatusRepository.findAll();
	System.out.println("aord"+aord);
	
	for(int i=0;i<trainStatuses.size();i++)
	{
			if(language.equals("English"))
			{
				
				if(trainStat.equals(trainStatuses.get(i).getStatusCode())&&aord.equals(trainStatuses.get(i).getAord()))
				{
					
					
					audioStr = audioStr.concat(trainStatuses.get(i).getEnglishFile().toString())+",";
				}
				
			}
			else if(language.equals("Hindi"))
			{
				if(trainStat.equals(trainStatuses.get(i).getStatusCode())&&aord.equals(trainStatuses.get(i).getAord()))
				{
					audioStr = audioStr.concat(trainStatuses.get(i).getHindiFile().toString())+",";
				}
						
				//audioStr = audioStr.concat(trainStatuses.get(i).getHindiFile().toString())+",";
			}
		
	}

	return audioStr;
}

public String SchedulesToArrive() {
	
	List<SchedulesToArriveAt> schedulesAnn = schedulesToAnnoRepo.findAll();
	
	String audioStr = "";
	
	for(int i=0;i<schedulesAnn.size();i++) 
	{
		
		if(language.equals(schedulesAnn.get(i).getLanguage()))
		{
			audioStr=audioStr.concat(homeDir).concat(schedulesAnn.get(i).getAudioFile().toString())+",";
			}
	}

	return audioStr;
	
}


public String Stations(String source) {
	
List<StationCode> stationcodes=stationCodeRepository.findAll();
String audioStr = "";
	for(int i=0;i<stationcodes.size();i++)
	{
		if(language.equals("English")&&source.equals(stationcodes.get(i).getStationCode()))
		{
			audioStr = audioStr.concat(stationcodes.get(i).getEnglishWaveFile().toString())+",";
		}
		else if(language.equals("Hindi")&&source.equals(stationcodes.get(i).getStationCode()))
		{
			audioStr = audioStr.concat(stationcodes.get(i).getHindiWaveFile().toString())+",";
		}
	}

	return audioStr;
	
}

public String To() {
	
	List<ToAnnouncement> toAnn = toAnnoRepository.findAll();
	
	String audioStr = "";
	
	for(int i=0;i<toAnn.size();i++) 
	{
		
		if(language.equals(toAnn.get(i).getLanguage()))
		{
//			audioStr += trainAnn.get(i).getAudioFile().toString()+",";
			audioStr=audioStr.concat(homeDir).concat(toAnn.get(i).getAudioFile().toString())+",";
			}
	}

	return audioStr;
	
}

public String Via() {
	
	List<ViaAnnouncement> viaAnn = viaAnnoRepository.findAll();
	
	String audioStr = "";
	
	for(int i=0;i<viaAnn.size();i++) 
	{
		
		if(language.equals(viaAnn.get(i).getLanguage()))
		{
//			audioStr += trainAnn.get(i).getAudioFile().toString()+",";
			audioStr=audioStr.concat(homeDir).concat(viaAnn.get(i).getAudioFile().toString())+",";
			}
	}

	return audioStr;
	
}

public String Inconvenience() {
	
	String audioStr = "";
	List<InConvenienceAnnouncement> inconvAnn = inconvenienceAnnoRepo.findAll();
	
	for(int i=0;i<inconvAnn.size();i++) 
	{
		
		if(language.equals(inconvAnn.get(i).getLanguage()))
		{
//			audioStr += trainAnn.get(i).getAudioFile().toString()+",";
			audioStr=audioStr.concat(homeDir).concat(inconvAnn.get(i).getAudioFile().toString())+",";
			}
	}

	return audioStr;
	}

	public String PlatformNo(String platformNo) {
		
		List<PlatformAnnouncement> platformAnn= platformAnnoRepo.findAll();
		List<NumAudioAnnouncement> numAnn = numAudioAnnouncementrepo.findAll();
		String audioStr = "";
		
		for(int i=0;i<platformAnn.size();i++) {
			
			if(language.equals(platformAnn.get(i).getLanguage())) {
				

				audioStr = audioStr.concat(homeDir).concat( platformAnn.get(i).getAudioFile().toString())+",";
				//audioStr += platformAnn.get(i).getAudioFile().toString()+",";


				
				for(int k=0; k<numAnn.size();k++) {
					
					if(language.equals(numAnn.get(k).getLanguage()) && platformNo.equals(numAnn.get(k).getNumber())) {
						
						audioStr = audioStr.concat(homeDir).concat(numAnn.get(k).getAudioFile().toString())+",";
						break;
						
					}
				}
			}
		}
		return audioStr;
	}


	public String TimeAnno(String time) {
		
		
		String audioStr="";
		
		 List<TimeAnnouncement> timeAnn = timeAnnoRepo.findAll();
		
		 for(int i = 0; i < timeAnn.size();i++)
		 {
				
				if(language.equals(timeAnn.get(i).getLanguage()) && time.equals(timeAnn.get(i).getTime())) {
					audioStr = audioStr.concat(homeDir).concat(timeAnn.get(i).getAudioFile().toString())+",";
					
					
				}
				
		 }
			return audioStr;
		
		
	}
		
	public String Hrs()
	{
		String audioStr="";
		List<HoursAnnouncement> hrsAnn = hoursAnnoRepository.findAll();
		
		for(int j=0;j<hrsAnn.size();j++) 
		{
			
			if(language.equals(hrsAnn.get(j).getLanguage()))
			{
//				audioStr += trainAnn.get(i).getAudioFile().toString()+",";
				audioStr=audioStr.concat(homeDir).concat(hrsAnn.get(j).getAudioFile().toString())+",";
				}
		}	
		return audioStr;
		//break;
	}
		public String Min()
		{
			String audioStr="";
			List<MinutesAnnouncement> minAnn = minutesAnnoRepository.findAll();
			
			for(int j=0;j<minAnn.size();j++) 
			{
				
				if(language.equals(minAnn.get(j).getLanguage()))
				{
//					audioStr += trainAnn.get(i).getAudioFile().toString()+",";
					audioStr=audioStr.concat(homeDir).concat(minAnn.get(j).getAudioFile().toString())+",";
					}
			}	
			return audioStr;
		}
	}
	
			
		
		 
	
	