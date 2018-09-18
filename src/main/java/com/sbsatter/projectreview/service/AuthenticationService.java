package com.sbsatter.projectreview.service;

import com.sbsatter.projectreview.model.User;
import com.sbsatter.projectreview.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * Created by sbsatter on 9/18/18.
 */
@Service
public class AuthenticationService implements UserDetailsService {
	@Autowired
	private UserRepository userRepository;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		final User user = userRepository.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("User name: " + username + " not found.");
		}
		final String roleName = user.getRole();
		GrantedAuthority authority = new SimpleGrantedAuthority(roleName);
		UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(),
				user.getPassword(), Arrays.asList(authority));
		return userDetails;
	}
}
