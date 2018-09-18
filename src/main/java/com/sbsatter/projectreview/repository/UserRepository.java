package com.sbsatter.projectreview.repository;

import com.sbsatter.projectreview.model.User;
import com.sbsatter.projectreview.model.rowmapper.UserRowMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
		String query = "Select * from user where username like ? order by id desc limit 1";
		
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
	
	public User add(User user) {
		SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
				.withTableName("user")
				.usingGeneratedKeyColumns("id");
		
		Map<String, Object> parameters = new HashMap<String, Object>();
		
		parameters.put("name", user.getName());
		parameters.put("phone", user.getPhone());
		parameters.put("password", user.getPassword());
		parameters.put("role", user.getRole());
		parameters.put("username", user.getUsername());
		
		Number id = simpleJdbcInsert.executeAndReturnKey(parameters);
		if (id == null) {
			log.error("Failed to insert {}", user);
			return null;
		}
		user.setId(id.intValue());
		return user;
	}
}
