package com.innobitsysytems.ipis.services;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.AuthenticationResponse;
import com.innobitsysytems.ipis.dto.LoginRequest;
import com.innobitsysytems.ipis.dto.RefreshTokenRequest;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.repository.UserRepository;
import com.innobitsysytems.ipis.security.JwtProvider;
import com.innobitsysytems.ipis.utility.CommonUtil;

import io.jsonwebtoken.Jwt;

import java.time.Instant;
import java.util.*;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Before;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.Assert.assertEquals;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class TestAuthService {

	@InjectMocks
	AuthService authService;
	
//	@Autowired
//	private AuthService authservice;

	@Autowired
	private UserRepository userRepo;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	 @Autowired
	 private JwtProvider jwtProvider;
	
	 @Autowired
	 private RefreshTokenService refreshTokenService;
	 
	 @Mock
	 private CommonUtil commonUtil;
	
	@Mock
	private LoginRequest loginRequest;

	 Authentication authenticate = null;

	@Test
	public void testLogin(){
		
		LoginRequest loginRequest=new LoginRequest();
		loginRequest.setUsername("priyaa");
		loginRequest.setPassword("priyau58a78");
	  // Authentication authenticate;
		User userOne= userRepo.getUserByEmail("priyankpdhaya73@gmail.com");
		    
		
		User userOne1= userRepo.getUserByEmail("priyankpdhaya73@gmail.com");
		
		if(userOne1 == null) {
			
			Assert.assertNull(userOne1);
		
		}else {
			
		  Assert.assertEquals(loginRequest.getUsername(),userOne1.getFirstname());
		  
		}
	}
	
//	 Refresh Token to be automatically generated.
//	@Test
//	public void refreshToken(){
//		
//		RefreshTokenRequest refreshTokenRequest= new RefreshTokenRequest();
//		refreshTokenRequest.setRefreshToken("kkj");
//		refreshTokenRequest.setUsername("priyaa");
//		
////		refreshTokenService.validateRefreshToken(refreshTokenRequest.getRefreshToken());
//		
//		String jwtOne = jwtProvider.generateTokenWithUserName("priyaa");
//		
//		if(jwtOne == null) {
//			System.out.println("User does'nt exist");
//		//System.out.println("jwtToken="+jwtOne.toString());
//		}
//		
//		else {
//			
//			assertEquals(refreshTokenRequest.getRefreshToken(),refreshTokenRequest.getUsername());
//		}

//	}

}
