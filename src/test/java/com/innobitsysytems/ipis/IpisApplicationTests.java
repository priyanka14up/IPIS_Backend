package com.innobitsysytems.ipis;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Assert;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.HttpClientErrorException;
import com.innobitsysytems.ipis.model.User;



@RunWith(SpringRunner.class)
@SpringBootTest(classes = IpisApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IpisApplicationTests {

        @Autowired
        private TestRestTemplate restTemplate;

        @LocalServerPort
        private int port;

        private String getRootUrl() {
                return "http://localhost:" + port;
        }

        @Test
        public void contextLoads() {
        }

        @Test
        public void testGetAllUsers() {
                HttpHeaders headers = new HttpHeaders();
                HttpEntity<String> entity = new HttpEntity<String>(null, headers);

                ResponseEntity<String> response = restTemplate.exchange(getRootUrl() + "/users",
                                HttpMethod.GET, entity, String.class);

                Assert.assertNotNull(response.getBody());
        }

	@Test
	public void testGetUserById() {
		User user = restTemplate.getForObject(getRootUrl() + "/users/0", User.class);
		Assert.assertNotNull(user);
	}

        @Test
        public void testCreateUser() {
                User user = new User();
                user.setPassword("admin@gmail.com");
                user.setEmail("admin");
                user.setCreatedBy("admin");
                user.setUpdatedBy("admin");

                ResponseEntity<User> postResponse = restTemplate.postForEntity(getRootUrl() + "/users", user, User.class);
                Assert.assertNotNull(postResponse);
                Assert.assertNotNull(postResponse.getBody());
        }

        @Test
        public void testUpdatePost() {
                int id = 0;
                User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
                user.setEmail("admin1");
                user.setPassword("123456");

                restTemplate.put(getRootUrl() + "/users/" + id, user);

                User updatedUser = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
                Assert.assertNotNull(updatedUser);
        }

        @Test
        public void testDeletePost() {
                int id = 2;
                User user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
                Assert.assertNotNull(user);

                restTemplate.delete(getRootUrl() + "/users/" + id);

                try {
                        user = restTemplate.getForObject(getRootUrl() + "/users/" + id, User.class);
                } catch (final HttpClientErrorException e) {
                        Assert.assertEquals(e.getStatusCode(), HttpStatus.NOT_FOUND);
                }
        }

}