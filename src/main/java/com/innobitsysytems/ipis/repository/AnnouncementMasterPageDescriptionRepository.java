/**
 * Name: Kajal Kumari
 * Copyright: Innobit Systems, 2021
 * Purpose: Announcement Master Page Description Repository
 */
package com.innobitsysytems.ipis.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.superAdmin.AnnouncementMasterPageDescription;

@Repository
public interface AnnouncementMasterPageDescriptionRepository extends JpaRepository<AnnouncementMasterPageDescription, Long> {
	
    public AnnouncementMasterPageDescription findByTags(String[] tags);

    public AnnouncementMasterPageDescription findByPageNumber(Integer pageNumber);
}
