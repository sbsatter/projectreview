package com.sbsatter.projectreview.repository;

import com.sbsatter.projectreview.model.User;
import com.sbsatter.projectreview.model.rowmapper.UserRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by sbsatter on 9/18/18.
 */
@Repository
@Slf4j
public class UserRepository {
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public User findByUsername(String username) {
		User user;
		String query = "Select * from user where username = ?";
		
		try {
			user = jdbcTemplate.queryForObject(query, new Object[] {username}, new UserRowMapper());
			return user;
		} catch (DataAccessException dae) {
			log.error("An exception occurred when executing the following query:");
			log.error(query.replace("?", username));
			log.error(dae.getLocalizedMessage());
		}
		return null;
	}
	
	
	public List<User> findAll() {
		List<User> users;
		String query = "Select * from user";
		
		try {
			users = jdbcTemplate.query(query, new UserRowMapper());
			return users;
		} catch (DataAccessException dae) {
			log.error("An exception occurred when executing the following query:");
			log.error(query);
			log.error(dae.getLocalizedMessage());
		}
		return null;
	}
}
