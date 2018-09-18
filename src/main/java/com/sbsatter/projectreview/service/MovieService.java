package com.sbsatter.projectreview.service;

import com.sbsatter.projectreview.model.Movie;
import com.sbsatter.projectreview.model.Rating;
import com.sbsatter.projectreview.model.SearchResults;
import com.sbsatter.projectreview.model.User;
import com.sbsatter.projectreview.repository.RatingRepository;
import com.sbsatter.projectreview.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sbsatter on 9/19/18.
 */
@Service
@Slf4j
public class MovieService {
    @Value("${remote.api.url}")
    private String url;
    @Value("${api.key}")
    private String key;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RatingRepository ratingRepository;

    public List<Movie> findByTitle(String title) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        headers.add("just_a_sample_header", "just_a_simple_value");
        HttpEntity entity = new HttpEntity(headers);
        String url = this.url + "&s=" + title.trim();
        ResponseEntity<SearchResults> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, SearchResults.class);
        log.info("Status: {}", responseEntity.getStatusCode());
        SearchResults results = responseEntity.getBody();
        List<Movie> movies = results.getSearch();
        return movies;
    }

    public Movie findByImdbId(String id) {
        RestTemplate restTemplate = restTemplateBuilder.build();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity entity = new HttpEntity(headers);
        String url = this.url + "&i=" + id.trim();
        ResponseEntity<Movie> responseEntity = restTemplate.exchange(url, HttpMethod.GET, entity, Movie.class);
        log.info("Status: {}", responseEntity.getStatusCode());
        Movie movie = responseEntity.getBody();
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Rating rating = ratingRepository.findRatingByUserIdAndImdbId(user.getId(), movie.getImdbID());
        if (rating != null) {
            movie.setUserRating(rating.getRating());
        }
        return movie;
    }

    public Rating saveMovieRating(Movie movie) {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName());
        Rating rating = ratingRepository.findRatingByUserIdAndImdbId(user.getId(), movie.getImdbID());
        if (rating.getId() == null) {
            rating.setImdbID(movie.getImdbID());
            rating.setRating(movie.getUserRating());
            rating.setUser(user);
            rating = ratingRepository.addRating(rating);
        } else {
            rating.setRating(movie.getUserRating());
            boolean success = ratingRepository.updateRating(rating);
            log.info("Updated Rating status: {}", success);
        }
        return rating;
    }
}
