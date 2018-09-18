package com.sbsatter.projectreview.model;

import lombok.Data;

/**
 * Created by sbsatter on 9/19/18.
 */
@Data
public class Movie {
    public String imdbID;
    public String Title;
    public String Year;
    public String imdbRating;
    public String userRating;
    public String Poster;
}
