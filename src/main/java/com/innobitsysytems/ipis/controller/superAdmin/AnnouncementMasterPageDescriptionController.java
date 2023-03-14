/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2022
 * Purpose: Announcement Maste rPage Description Controller
 */
package com.innobitsysytems.ipis.controller.superAdmin;

import java.util.*;
import com.innobitsysytems.ipis.dto.SuperPageDto;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.repository.AnnouncementMasterPageDescriptionRepository;
import com.innobitsysytems.ipis.services.superAdmin.AnnouncementAdminDescriptionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("api/v1")
public class AnnouncementMasterPageDescriptionController{

    @Autowired
    public AnnouncementAdminDescriptionService announcementAdminDescriptionService;
    @Autowired
    public AnnouncementMasterPageDescriptionRepository announcementMasterPageDescriptionRepository;


            @GetMapping("/announcementMasterPageDescription")

            public ResponseEntity<Object> getAll()
            {
                List<SuperPageDto> pageData = announcementAdminDescriptionService.findAllPage();

                return ResponseHandler.generateResponse("success",HttpStatus.OK,pageData);
            }


            @GetMapping("/announcementMasterPageDescription/{id}")
            public ResponseEntity<Object> getAnnouncementAdminPageById(@PathVariable(value = "id")Long id) throws HandledException {
                {

                    try {
                        SuperPageDto singleannouncementPage = announcementAdminDescriptionService.getSingleAnnouncementPageDescriptionbyId(id);
                        return ResponseHandler.generateResponse("success", HttpStatus.OK, singleannouncementPage);
                    } catch (HandledException e) {

                        return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
                    }
                }
            }


    @PostMapping("/save")
    public ResponseEntity<Object> save(HttpServletRequest request, @Valid @RequestBody SuperPageDto superPageDto) throws HandledException
    {

        try {

            SuperPageDto postPage =  announcementAdminDescriptionService.postPage(request, superPageDto);
            return ResponseHandler.generateResponse("success", HttpStatus.OK, superPageDto);


        }catch(HandledException e) {

            return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);
        }
    }


}
