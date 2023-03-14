package com.innobitsysytems.ipis.services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import com.innobitsysytems.ipis.IpisApplication;
import com.innobitsysytems.ipis.model.User;
import com.innobitsysytems.ipis.model.UserRole;
import com.innobitsysytems.ipis.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TestUserDetailsServiceImpl {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void TestLoadUserByUsername() {

		User user = new User();

		user.setEmail("priyankua73@gmail.com");

		User userOptional = userRepository.getUserByEmail(user.getEmail());

		if (userOptional == null) {

			Assert.assertNull(userOptional);
		}

		else {

			UserRole userRole = userOptional.getUserRole();
			Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

			String S[] = userRole.getUserPermissions();
			String role = userRole.getRoleText();
			authorities.add(new SimpleGrantedAuthority(role));
			for (int i = 0; i < S.length; i++) {
				authorities.add(new SimpleGrantedAuthority(S[i]));
			}

			Assert.assertNotNull(authorities);
		}
	}

}
