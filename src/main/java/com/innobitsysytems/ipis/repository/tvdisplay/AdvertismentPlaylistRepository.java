/**
 * Name: Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: Advertisment laylist Repository
 */
package com.innobitsysytems.ipis.repository.tvdisplay;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.innobitsysytems.ipis.model.tvdisplay.AdvertismentPlaylist;

@Repository
public interface AdvertismentPlaylistRepository extends JpaRepository<AdvertismentPlaylist, Long>{

}
