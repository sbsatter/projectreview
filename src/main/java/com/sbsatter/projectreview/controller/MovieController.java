package com.sbsatter.projectreview.controller;

import com.sbsatter.projectreview.model.Movie;
import com.sbsatter.projectreview.model.Rating;
import com.sbsatter.projectreview.model.SearchResults;
import com.sbsatter.projectreview.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sbsatter on 9/19/18.
 */
@RequestMapping("/movie")
@Controller
@Slf4j
public class MovieController {
    @Autowired
    private MovieService movieService;

    @GetMapping("/search")
    public String searchPage() {
        return "movie/search";
    }

    @PostMapping("/search")
    public String findByTitle(@ModelAttribute("title") String title, Model model) {
        List<Movie> movies = movieService.findByTitle(title);
        log.info("Movie: {}", movies);
        model.addAttribute("movies", movies);
        return "movie/search";
    }

    @GetMapping("/rate/{imdbID}")
    public String displayRatingPage(Model model, @PathVariable String imdbID) {
        Movie movie = movieService.findByImdbId(imdbID);
        model.addAttribute("movie", movie);
        return "movie/ratings_page";
    }

    @PostMapping("/rate")
    public String rate(@ModelAttribute("movie") Movie movie) {
        if (movie != null && movie.getUserRating() != null) {
            Rating rating = movieService.saveMovieRating(movie);
            log.info("Rating: {}", rating);
        }
        return "redirect:/movie/rate/" + movie.getImdbID();
    }


}
