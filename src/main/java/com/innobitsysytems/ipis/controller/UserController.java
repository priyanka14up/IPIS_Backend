/**
 * Name: Rahul Kumar Pandey & Apoorva Gupta
 * Copyright: Innobit Systems, 2021
 * Purpose: User Controller
 */

package com.innobitsysytems.ipis.controller;

import com.innobitsysytems.ipis.exception.HandledException;
import com.innobitsysytems.ipis.exception.ResourceNotFoundException;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.UserRole;
import com.innobitsysytems.ipis.repository.UserRepository;
import com.innobitsysytems.ipis.repository.UserRoleRepository;
import com.innobitsysytems.ipis.dto.AuthTokenRequest;
import com.innobitsysytems.ipis.dto.AuthenticationResponse;
import com.innobitsysytems.ipis.dto.ChangePassword;
import com.innobitsysytems.ipis.dto.CreateUser;
import com.innobitsysytems.ipis.dto.LoginRequest;
import com.innobitsysytems.ipis.dto.RefreshTokenRequest;
import com.innobitsysytems.ipis.errors.ResponseHandler;
import com.innobitsysytems.ipis.services.AuthService;
import com.innobitsysytems.ipis.services.RefreshTokenService;
import com.innobitsysytems.ipis.utility.CustomUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.validation.constraints.NotNull;
import org.springframework.data.domain.Sort;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1")
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private AuthService authService;

	@Autowired
	private RefreshTokenService refreshTokenService;

	@Autowired
	private UserRoleRepository userRoleRepository;

	@Autowired
	public CustomUtil customUtil;

	@GetMapping("/users")
	public List<User> getAllUsers() {

		List<User> user = userRepository.findAll(Sort.by(Sort.Direction.ASC, "firstname"));
		List<User> newUserList = new ArrayList<User>();

		for (int i = 0; i < user.size(); i++) {

			if ((user.get(i).getUserRole().getId() == 2) || (user.get(i).getUserRole().getId() == 3)
					|| (user.get(i).getUserRole().getId() == 4)) {

				newUserList.add(user.get(i));

			}

		}

		return newUserList;

	}

	@GetMapping("/user/{id}")
	public ResponseEntity<Object> getUsersById(@PathVariable(value = "id") Long userId)
			throws ResourceNotFoundException {

		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));

		return ResponseEntity.ok().body(user);

	}

	@PostMapping("/users")
	public ResponseEntity<Object> createUser(@RequestBody CreateUser createUser) throws ResourceNotFoundException {

		User user = new User();
		Optional<User> existEmail = userRepository.findByEmail(createUser.getEmail());
		Optional<User> existMobileNo = userRepository.findByMobileNo(createUser.getMobileNo());

		if (existEmail.isPresent() && existMobileNo.isPresent()) {

			throw new ResourceNotFoundException("Email id and Mobile Number both already exists");

		} else {

			if (existEmail.isPresent() || existMobileNo.isPresent()) {

				if (existEmail.isPresent()) {

					throw new ResourceNotFoundException("Email id already exists");

				} else {

					throw new ResourceNotFoundException("Mobile Number already exists");

				}
			}

		}

		try {

			UserRole userRole = userRoleRepository.findByRoleText(createUser.getRole()).orElseThrow(
					() -> new ResourceNotFoundException("User role not  found with role  :: " + createUser.getRole()));
			user.setUserRole(userRole);

		} catch (ResourceNotFoundException e) {

			ResponseEntity.badRequest().body(e);
		}

		Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"); // Minimum eight characters, at
																						// least one letter and one
																						// number:
		Matcher matcher = pattern.matcher(createUser.getPassword());

		if (matcher.matches()) {

			user.setPassword(passwordEncoder.encode(createUser.getPassword()));

		} else {

			throw new ResourceNotFoundException("Password should be alphanumeric and minimum length of 8 ");
		}

		user.setFirstname(createUser.getFirstname());
		user.setLastname(createUser.getLastname());
		user.setEmail(createUser.getEmail());
		user.setMobileNo(createUser.getMobileNo());
		user.setCreatedBy(createUser.getCreatedBy());
		user.setStatus("");
		user.setCreatedAt(new Date());

		final User createdUser = userRepository.save(user);

		return ResponseEntity.ok(createdUser);
	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/user/{id}")
	public ResponseEntity<User> updateUser(@PathVariable(value = "id") @NotNull Long userId,
			@RequestBody User userDetails) throws ResourceNotFoundException {

		UserRole userRole = userDetails.getUserRole();
		long id = userRole.getId();
		User user = null;
		try {
			user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found on :: " + userId));
		} catch (ResourceNotFoundException e) {
			ResponseEntity.badRequest().body(e);
		}

		if (userDetails.getPassword() != "") {
			user.setPassword(passwordEncoder.encode(userDetails.getPassword()));

		}

		try {
			userRole = userRoleRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("User role not  found with id  :: " + id));
			user.setUserRole(userRole);
		} catch (ResourceNotFoundException e) {
			ResponseEntity.badRequest().body(e);
		}

		user.setFirstname(userDetails.getFirstname());
		user.setLastname(userDetails.getLastname());
		user.setEmail(userDetails.getEmail());
		user.setMobileNo(userDetails.getMobileNo());
		user.setStatus("");
		user.setUpdatedBy(userDetails.getUpdatedBy());
		user.setUpdatedAt(new Date());
		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

	@CrossOrigin(allowedHeaders = "*")
	@DeleteMapping("/user/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") @NotNull Long userId)
			throws ResourceNotFoundException {

		Long loggedInUserId = customUtil.getIdFromToken();
		System.out.println("lo" + loggedInUserId);
		System.out.println("us" + userId);
		if (userId.equals(loggedInUserId)) {

			throw new ResourceNotFoundException("User can't delete himself");

		}

		User user = null;

		try {

			user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found for id : " + userId));

		} catch (ResourceNotFoundException e) {

			ResponseEntity.badRequest().body(e);
		}

		String role = user.getUserRole().getRoleText();
		User LoggedInUser = userRepository.getById(loggedInUserId);
		String LoggedUserRole = LoggedInUser.getUserRole().getRoleText();

		if (LoggedUserRole.equals("ROLE_SUPER ADMIN")) {

			userRepository.deleteById(userId);

		} else if (LoggedUserRole.equals("ROLE_ADMIN")) {

			if ((role.equals("ROLE_ADMIN")) || (role.equals("ROLE_STATION MASTER")) || (role.equals("ROLE_OPERATOR"))) {

				userRepository.deleteById(userId);

			} else {

				throw new ResourceNotFoundException("Admin can't delete Super Admin");

			}

		} else if (LoggedUserRole.equals("ROLE_STATION MASTER")) {

			if ((role.equals("ROLE_STATION MASTER")) || (role.equals("ROLE_OPERATOR"))) {

				userRepository.deleteById(userId);

			} else {

				throw new ResourceNotFoundException("Station Master can't delete Super Admin or Admin");
			}
		}

		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;

	}

	@PostMapping("/login")
	public AuthenticationResponse login(@RequestBody LoginRequest loginRequest) {

		return authService.login(loginRequest);

	}

	@PostMapping("/trainlogin")
	public ResponseEntity<Object> trainLogin(@RequestBody LoginRequest loginRequest) throws HandledException {

		try {

			User user = authService.trainLogin(loginRequest);
			return ResponseHandler.generateResponse("success", HttpStatus.OK, user);

		} catch (HandledException e) {

			return ResponseHandler.generateResponse(e.getMessage(), HttpStatus.BAD_REQUEST, null);

		}

	}

	@PostMapping("/refresh/token")
	public AuthenticationResponse refreshTokens(@Valid @RequestBody RefreshTokenRequest refreshTokenRequest) {
		return authService.refreshToken(refreshTokenRequest);
	}

	@PostMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestBody AuthTokenRequest refreshTokenRequest) {
		refreshTokenService.deleteRefreshToken(refreshTokenRequest.getAuthToken());
		return ResponseEntity.status(HttpStatus.OK).body("Refresh Token Deleted Successfully!!");
	}

	@CrossOrigin(allowedHeaders = "*")
	@PutMapping("/user/changePassword/{id}")
	public ResponseEntity<User> updatePassword(@PathVariable(value = "id") Long userId,
			@RequestBody ChangePassword changePassword) throws ResourceNotFoundException {

		User user = null;

		try {

			user = userRepository.findById(userId)
					.orElseThrow(() -> new ResourceNotFoundException("User not found on: " + userId));

		} catch (ResourceNotFoundException e) {

			ResponseEntity.badRequest().body(e);
		}

		if (!passwordEncoder.matches(changePassword.getOldpassword(), user.getPassword())) {

			throw new ResourceNotFoundException(" Old Password should be matching with the previously set password ");
		}

		if (changePassword.getOldpassword().equals(changePassword.getNewpassword())) {

			throw new ResourceNotFoundException(" New Password should not be same as the old password ");

		}

		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,20}$"); // Minimum eight characters, at
																							// least one letter and one
																							// number:
		Matcher matcher = pattern.matcher(changePassword.getNewpassword());

		if (matcher.matches()) {

			user.setPassword(passwordEncoder.encode(changePassword.getNewpassword()));
			user.setVerified(true);

		} else {

			throw new ResourceNotFoundException("Password should be alphanumeric and minimum length of 8 ");
		}

		final User updatedUser = userRepository.save(user);
		return ResponseEntity.ok(updatedUser);
	}

}
