package com.sbsatter.projectreview.model.rowmapper;

import com.sbsatter.projectreview.model.User;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sbsatter on 9/18/18.
 */
public class UserRowMapper implements RowMapper<User> {
	@Override
	public User mapRow(ResultSet resultSet, int i) throws SQLException {
		User user = new User();
		user.setUsername(resultSet.getString("username"));
		user.setPassword(resultSet.getString("password"));
		user.setName(resultSet.getString("name"));
		user.setPhone(resultSet.getString("phone"));
		user.setId(resultSet.getInt("id"));
		user.setRole(resultSet.getString("role"));
		return user;
	}
}
