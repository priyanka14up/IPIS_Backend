/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: User Details Service Implementation
 */
package com.innobitsysytems.ipis.services;

import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.UserRole;
import com.innobitsysytems.ipis.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.Optional;



import java.util.ArrayList;

@Service

public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private  UserRepository userRepository;
	

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) {
    	User user=null;
        try {
        Optional<User> userOptional = userRepository.findByEmailOrMobileNo(username,username);
         user = userOptional
                .orElseThrow(() -> new UsernameNotFoundException("No user " +
                        "Found with username : " + username));
    	}
    	catch(UsernameNotFoundException e)
    	{
    		ResponseEntity.badRequest()
            .body(e);
    	}
        
        UserRole userRole= user.getUserRole();
       
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
       
        String S[]=userRole.getUserPermissions();
        String role = userRole.getRoleText();
        authorities.add(new SimpleGrantedAuthority(role));
        for(int i=0;i<S.length;i++)
        {
        	authorities.add(new SimpleGrantedAuthority(S[i]));
        }
        
        return new org.springframework.security
                .core.userdetails.User(user.getEmail(), user.getPassword(),
                true, true, true,
                true, authorities);
    }


}
