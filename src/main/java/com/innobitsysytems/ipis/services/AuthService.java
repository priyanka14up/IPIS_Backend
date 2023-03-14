/**
 * Name: Rahul Pandey
 * Copyright: Innobit Systems, 2021
 * Purpose: Authentication Service
 */

package com.innobitsysytems.ipis.services;

import com.innobitsysytems.ipis.dto.AuthenticationResponse;
import com.innobitsysytems.ipis.dto.LoginRequest;
import com.innobitsysytems.ipis.dto.RefreshTokenRequest;
import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.repository.UserRepository;
import com.innobitsysytems.ipis.security.JwtProvider;
import com.innobitsysytems.ipis.utility.CommonUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.innobitsysytems.ipis.model.User;
import java.time.Instant;
import java.util.Optional;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

@Service
@Transactional
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    private JwtProvider jwtProvider;
    
    @Autowired
    private RefreshTokenService refreshTokenService;
    
    @Autowired
    private UserRepository userRepo;

    @Autowired
    private CommonUtil commonUtil;

    public AuthenticationResponse login(LoginRequest loginRequest) throws AuthenticationException {
    	
        Authentication authenticate = null;

        //Authentication Request
        authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
                loginRequest.getPassword()));

        Optional<User> userOptional = userRepo.findByEmailOrMobileNo(loginRequest.getUsername(), loginRequest.getUsername());

        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : "));
       
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        String token = jwtProvider.generateToken(authenticate);
        
		AuthenticationResponse authenticationResponse = AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenService.generateRefreshToken().getToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(loginRequest.getUsername())
                .fname(user.getFirstname())
                .lname(user.getLastname())
                .id(user.getId())
                .role(user.getUserRole().getRoleText())
                .isVerified(user.isVerified())              
                .build();
		commonUtil.LoginReports(user, authenticationResponse);
        return authenticationResponse;
    }
    
 public User trainLogin(LoginRequest loginRequest) throws HandledException {
    	

        Authentication authenticate = null;

        //Authentication Request
       authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
               loginRequest.getPassword()));

        Optional<User> userOptional = userRepo.findByEmailOrMobileNo(loginRequest.getUsername(), loginRequest.getUsername());
        User user = userOptional.orElseThrow(() -> new UsernameNotFoundException("No user " + "Found with username : "));
        
        String role=user.getUserRole().getRoleText();
        
        if(role.equals("ROLE_SUPER ADMIN")||role.equals("ROLE_ADMIN")){
        	
        	return user;
        
        }else{
        	
        	throw new HandledException("CHECK_PARAMETERS", "Station Master is not Permitted to access Train Data Entry ");
        
        }
      
    }
 
    public AuthenticationResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
        String token = jwtProvider.generateTokenWithUserName(refreshTokenRequest.getUsername());
        return AuthenticationResponse.builder()
                .authenticationToken(token)
                .refreshToken(refreshTokenRequest.getRefreshToken())
                .expiresAt(Instant.now().plusMillis(jwtProvider.getJwtExpirationInMillis()))
                .username(refreshTokenRequest.getUsername())
                .build();
    }

    public boolean isLoggedIn() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return !(authentication instanceof AnonymousAuthenticationToken) && authentication.isAuthenticated();
    }
    
}
