/**
 * Name: Amit Yadav
 * Copyright: Innobit Systems, 2021
 * Purpose: Utility to get User Token
 */
package com.innobitsysytems.ipis.utility;

import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.repository.UserRepository;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class CustomUtil {

    @Autowired
    private UserRepository userRepo;

    public long getIdFromToken() {

        long retValue = 0;

        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        
        String username = userDetails.getUsername();

        Optional<User> users = userRepo.findByEmail(username);

        if (users.isPresent()) {

            User userValue = users.get();

            retValue = userValue.getId();
            
        } else {

            retValue = 0;
        }

        return retValue;

    }
    
}
