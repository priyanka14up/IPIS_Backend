	package com.innobitsysytems.ipis.controller;

	import org.junit.jupiter.api.Test;
	import org.springframework.boot.test.context.SpringBootTest;

	import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.junit.Assert;
	import org.junit.Before;
	import org.junit.runner.RunWith;
	import org.mockito.Mock;
	import org.springframework.beans.factory.annotation.Autowired;
	import org.springframework.boot.test.web.client.TestRestTemplate;
	import org.springframework.boot.web.server.LocalServerPort;
	import org.springframework.http.*;
	import org.springframework.test.context.junit4.SpringRunner;
	import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
	import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;

	import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.dto.AuthenticationResponse;
import com.innobitsysytems.ipis.dto.ChangePassword;
import com.innobitsysytems.ipis.dto.LoginRequest;
import com.innobitsysytems.ipis.dto.RefreshTokenRequest;
import com.innobitsysytems.ipis.errors.ResponseHandler;
	import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.exception.ResourceNotFoundException;
import com.innobitsysytems.ipis.model.User;
	import com.innobitsysytems.ipis.model.announcement.AnnouncementPlaylist;
import com.innobitsysytems.ipis.model.device.Device;
import com.innobitsysytems.ipis.services.AuthService;
import com.innobitsysytems.ipis.services.announcement.AnnouncementService;


	@RunWith(SpringRunner.class)
	@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
	public class TestUserController  {

		@Autowired
		private TestRestTemplate restTemplate;
		
		@Autowired
		private AuthService authService;
	

		@Test
		public void testGetAllUsers() {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			ResponseEntity<String> response = restTemplate.exchange("/user",
					HttpMethod.GET, entity, String.class);

			Assert.assertNotNull(response.getBody());
		}

		@Test
		public void testGetUsersById() {
			
			User user = restTemplate.getForObject("/user/-1", User.class);
			System.out.println(user.getId());
			Assert.assertNotNull(user);
		}

		@Test
		public void testCreateUser() {
			
			User user = new User();
			user.setCreatedAt(null);
			user.setCreatedBy("admin");
			ResponseEntity<User> postResponse = restTemplate.postForEntity( "/user", user, User.class);
			Assert.assertNotNull(postResponse);
			Assert.assertNotNull(postResponse.getBody());
			
		}

		

		@Test
		public void testUpdateUser() {
			int id = 0;
			User user = restTemplate.getForObject("/user/" + id, User.class);
			restTemplate.put("/user/" + id, user);
			User updatedUser = restTemplate.getForObject("/user/" + id, User.class);
			Assert.assertNotNull(updatedUser);
		}
	    

		@Test
		public void testDeleteUser() {
			int id = 2;
			User user = restTemplate.getForObject("/user/" + id, User.class);
			Assert.assertNotNull(user);
			restTemplate.delete("/user/" + id);

			try {
				user = restTemplate.getForObject("/user/" + id, User.class);
			} catch (final HttpClientErrorException e) {
				Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
			}
		}
		
			
//		    public void testLlogin(@RequestBody LoginRequest loginRequest) {
//		       
//		        return authService.login(loginRequest);
//
//		    }
//
//		    
//		    public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//		        return authService.refreshToken(refreshTokenRequest);
//		    }
//
//		  
//		    public ResponseEntity<String> logout(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
//		        refreshTokenService.deleteRefreshToken(refreshTokenRequest.getRefreshToken());
//		        return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
//		    }
//		
//		 @CrossOrigin(allowedHeaders = "*")
//		    @PutMapping("/user/changePassword/{id}")
//		    public ResponseEntity<User> updatePassword(
//		            @PathVariable(value = "id") Long userId, @RequestBody ChangePassword changePassword)
//		            throws ResourceNotFoundException {
//		        User user = null;
//		        try {
//		            user
//		                    = userRepository
//		                            .findById(userId)
//		                            .orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
//		        } catch (ResourceNotFoundException e) {
//		            ResponseEntity.badRequest()
//		                    .body(e);
//		        }
//
//		        if (!passwordEncoder.matches(changePassword.getOldpassword(), user.getPassword())) {
//		            throw new ResourceNotFoundException(" Old Password should be matching with the previously set passowrd ");
//		        }
//
//		        Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"); //Minimum eight characters, at least one letter and one number:
//		        Matcher matcher = pattern.matcher(changePassword.getNewpassword());
//
//		        if (matcher.matches()) {
//		            user.setPassword(passwordEncoder.encode(changePassword.getNewpassword()));
//		            user.setVerified(true);
//		        } else {
//
//		            throw new ResourceNotFoundException("Password should be alphanumeric and minimum length of 8 ");
//		        }
//		        final User updatedUser = userRepository.save(user);
//		        return ResponseEntity.ok(updatedUser);
//		    }
//		
	}
