package com.sbsatter.projectreview.repository;

import com.sbsatter.projectreview.model.Rating;
import com.sbsatter.projectreview.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sbsatter on 9/19/18.
 */
@Repository
@Slf4j
public class RatingRepository {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public Rating addRating(Rating rating) {
        SimpleJdbcInsert simpleJdbcInsert = new SimpleJdbcInsert(jdbcTemplate)
                .withTableName("ratings")
                .usingGeneratedKeyColumns("id");
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("user_id", rating.getUser().getId());
        parameters.put("imdb_id", rating.getImdbID());
        parameters.put("rating", rating.getRating());

        Number autoGeneratedId = simpleJdbcInsert.executeAndReturnKey(parameters);
        if (autoGeneratedId == null) {
            log.error("Insert failed");
            // further log
            return null;
        } else {
            rating.setId(autoGeneratedId.intValue());
        }
        return rating;
    }

    public Rating findRatingByUserIdAndImdbId(Integer userId, String imdbId) {
        String query = "Select * from ratings where user_id = ? and imdb_id like ? order by id desc limit 1";
        try {
            return jdbcTemplate.queryForObject(query, new Object[]{userId, imdbId}, new RowMapper<Rating>() {
                @Override
                public Rating mapRow(ResultSet rs, int rowNum) throws SQLException {
                    Rating rating = new Rating();
                    rating.setId(rs.getInt("id"));
                    rating.setRating(rs.getString("rating"));
                    User user = new User();
                    user.setId(rs.getInt("user_id"));
                    rating.setUser(user);
                    rating.setImdbID(rs.getString("imdb_id"));
                    return rating;
                }
            });
        } catch (DataAccessException dae) {
            log.error(dae.getLocalizedMessage());
        }
        return new Rating();
    }

    public boolean updateRating(Rating rating) {
        String query = "Update ratings set rating = ? where id = ?";
        try {
            return jdbcTemplate.update(query, new Object[] {rating.getRating(), rating.getId()}) == 1;
        } catch (DataAccessException dae) {
            log.error(dae.getLocalizedMessage());
        }
        return false;
    }
}