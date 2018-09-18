package com.sbsatter.projectreview.repository;

import com.sbsatter.projectreview.model.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.Assert;

import java.util.List;

/**
 * Created by sbsatter on 9/18/18.
 */
@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class TestUserRepository {
	@Autowired
	private UserRepository userRepository;
	
	@Test
	public void testFindByUsername() {
		String username = "user";
		User user = userRepository.findByUsername(username);
		Assert.notNull(user, "User object is null");
		Assert.isTrue(user.getUsername().equals(username), "Username did not match");
		log.info("Test run successfully: {}", user);
	}
	
	@Test
	public void testAllUsers() {
		List<User> users = userRepository.findAll();
		log.info("{}", users);
	}
	
}
