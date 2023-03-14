/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Master Bean
 */
package com.innobitsysytems.ipis.services.superAdmin;

import java.util.*;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.innobitsysytems.ipis.dto.SuperMasterDto;
//import com.innobitsysytems.ipis.dto.SuperPageDto;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterData;
import com.innobitsysytems.ipis.repository.AnnouncementMasterDataRepository;
import com.innobitsysytems.ipis.utility.CustomUtil;

@Service
public class AnnouncementMasterBean implements AnnouncementMasterService{
	
	@Autowired
	public AnnouncementMasterDataRepository announcementMasterDataRepo;
	
	@Autowired
	public CustomUtil customUtil;
	
	
	
	
	@Override
	public List<SuperMasterDto> allAnnouncementMasterData() {
		
		
		List<AnnouncementMasterData>  announcementMasterData = announcementMasterDataRepo.findAll();
		
		return entityToDto(announcementMasterData);
		
	}

	
    @Override
    public SuperMasterDto getSingleAnnouncementMaster( Long id) throws HandledException {
    	
    AnnouncementMasterData announcementadmin =  announcementMasterDataRepo.findById(id)
    .orElseThrow(()-> new HandledException("Not_Found","Announcement Master Data not found on :"+id));
    
    return entityToDto(announcementadmin);    
    }
    
    
	@Override
    public SuperMasterDto createAnnouncementMaster(HttpServletRequest request, AnnouncementMasterData announcementMasterData) throws HandledException 
	{
    	
    	Long token = customUtil.getIdFromToken();	
    	
    		announcementMasterData.setCreatedBy(token.toString());
    		announcementMasterDataRepo.save(announcementMasterData);
    	return entityToDto(announcementMasterData);
    	
    }
    
    @Override
    public SuperMasterDto putAnnouncementMaster(Long id, AnnouncementMasterData Amd) throws HandledException{
    	
    	AnnouncementMasterData announcementMasterData1 = announcementMasterDataRepo.findById(id)
    	.orElseThrow(() -> new HandledException("NOT_FOUND","Announcement Master Data not found on:"+id));
    	announcementMasterData1.setTemplateNumber(Amd.getTemplateNumber());
    	announcementMasterData1.setMessageType(Amd.getMessageType());
    	announcementMasterData1.setTemplateDescription(Amd.getTemplateDescription());
    	announcementMasterDataRepo.save(announcementMasterData1);
    	return entityToDto(announcementMasterData1);
    	
    }
    
    @Override
    public Map<String, Boolean> deleteAnnouncementMaster(Long id) throws HandledException {
    	
    	AnnouncementMasterData announcementMasteradmin = announcementMasterDataRepo.findById(id)
    			.orElseThrow(() -> new HandledException("Not_FOUND", "Announcement Master Data not found on :" + id));
    	
    	announcementMasterDataRepo.delete(announcementMasteradmin);
    	Map<String, Boolean> response = new HashMap<>();
    	response.put("deleted", Boolean.TRUE);
    	return response;
    	
    }
    
    public SuperMasterDto entityToDto(AnnouncementMasterData announcementMasterData)
    {
        SuperMasterDto dto = new SuperMasterDto();
        dto.setId(announcementMasterData.getId());
        dto.setTemplateNumber(announcementMasterData.getTemplateNumber());
        dto.setMessageType(announcementMasterData.getMessageType());
        dto.setTemplateDescription(announcementMasterData.getTemplateDescription());
        return dto;
    }
    public List<SuperMasterDto> entityToDto(List<AnnouncementMasterData> announcementMasterData)
    {
    	return announcementMasterData.stream().map(x -> entityToDto(x)).collect(Collectors.toList());
    }
    public AnnouncementMasterData dtoToEntity(SuperMasterDto dto)
    {
        AnnouncementMasterData md = new AnnouncementMasterData();
        md.setId(dto.getId());
        md.setTemplateNumber(dto.getTemplateNumber());
        md.setMessageType(dto.getMessageType());
        md.setTemplateDescription(dto.getTemplateDescription());
     

        return md;
    }

    public List<AnnouncementMasterData> dtoToEntity(List<SuperMasterDto> dto)
    {
        return dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
    }
}
	
    