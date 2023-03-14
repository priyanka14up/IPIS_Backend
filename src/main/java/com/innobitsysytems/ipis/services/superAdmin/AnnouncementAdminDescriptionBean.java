/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Admin DescriptionBean
 */
package com.innobitsysytems.ipis.services.superAdmin;

import com.innobitsysytems.ipis.dto.SuperPageDto;
import com.innobitsysytems.ipis.exception.HandledException;

import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterPageDescription;

import com.innobitsysytems.ipis.repository.AnnouncementMasterPageDescriptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class AnnouncementAdminDescriptionBean implements AnnouncementAdminDescriptionService{

    @Autowired

    public AnnouncementMasterPageDescriptionRepository announcementMasterPageDescriptionRepo;

    @Override
    public List<SuperPageDto> findAllPage() {

        List <AnnouncementMasterPageDescription> displaySuperPageData = announcementMasterPageDescriptionRepo.findAll();
        return entityToDto( displaySuperPageData );

    }


    @Override
    public SuperPageDto getSingleAnnouncementPageDescriptionbyId(Long id) throws HandledException {

        AnnouncementMasterPageDescription page = announcementMasterPageDescriptionRepo.findById( id )
                .orElseThrow(() -> new HandledException("NOT_FOUND", "Page not found on : " + id));


            return entityToDto(page);



    }

@Override
public SuperPageDto postPage(HttpServletRequest request,SuperPageDto superPageDto) throws HandledException {
    AnnouncementMasterPageDescription announcementMasterPageDescription = dtoToEntity(superPageDto);
    SuperPageDto dtoPage = new SuperPageDto();
    AnnouncementMasterPageDescription page = announcementMasterPageDescriptionRepo.findByPageNumber(superPageDto.getPageNumber());

    if(page == null)
    {
        announcementMasterPageDescriptionRepo.save(announcementMasterPageDescription);
        dtoPage = entityToDto(announcementMasterPageDescription);

    }else
    {
        page.setPageName(page.getPageName());
        page.setPageDuration(page.getPageDuration());
        page.setTemplateDescription(page.getTemplateDescription());
        page.setTags(page.getTags());
        page.setId(page.getId());
        page.setPageDuration(page.getPageDuration());
        page.setMessageDisplay(page.getMessageDisplay());
    announcementMasterPageDescriptionRepo.save(page);
    dtoPage = entityToDto(page);


    }
    return dtoPage;
}

        public SuperPageDto entityToDto(AnnouncementMasterPageDescription announcementMasterPageDescription)
        {
            SuperPageDto dto = new SuperPageDto();
            dto.setId(announcementMasterPageDescription.getId());
            dto.setAudioList(announcementMasterPageDescription.getAudioList());
            dto.setMessageDisplay(announcementMasterPageDescription.getMessageDisplay());
            dto.setMessageLanguage(announcementMasterPageDescription.getMessageLanguage());
            dto.setPageNumber(announcementMasterPageDescription.getPageNumber());
            dto.setTags(announcementMasterPageDescription.getTags());
            dto.setTemplateDescription(announcementMasterPageDescription.getTemplateDescription());
            return dto;
        }

        public List<SuperPageDto> entityToDto(List<AnnouncementMasterPageDescription> announcementMasterPageDescription)
        {
            return announcementMasterPageDescription.stream().map(x -> entityToDto(x)).collect(Collectors.toList());

        }
        public AnnouncementMasterPageDescription dtoToEntity(SuperPageDto dto)
        {
            AnnouncementMasterPageDescription pg = new AnnouncementMasterPageDescription();
            pg.setId(dto.getId());
            pg.setPageNumber(dto.getPageNumber());
            pg.setMessageLanguage(dto.getMessageLanguage());
            pg.setPageDuration(dto.getPageDuration());
            pg.setMessageDisplay(dto.getMessageDisplay());
            pg.setTags(dto.getTags());
            pg.setTemplateDescription(dto.getTemplateDescription());

            return pg;
        }

        public List<AnnouncementMasterPageDescription> dtoToEntity(List<SuperPageDto> dto)
        {


            return dto.stream().map(x -> dtoToEntity(x)).collect(Collectors.toList());
        }
    }




