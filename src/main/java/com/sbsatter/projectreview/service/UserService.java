package com.sbsatter.projectreview.service;

import com.sbsatter.projectreview.model.User;
import com.sbsatter.projectreview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.ShaPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by sbsatter on 9/18/18.
 */
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ShaPasswordEncoder encoder;
	public User getUser(String username) {
		return userRepository.findByUsername(username);
	}
	
	public List<User> getAllUsers() {
		List<User> users = userRepository.findAll();
		return users;
	}
	
	public User addUser(User user) {
		user.setPassword(encoder.encodePassword(user.getPassword(), null));
		return userRepository.add(user);
	}
}
