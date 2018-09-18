package com.sbsatter.projectreview.model;

import lombok.Data;

import java.util.List;

/**
 * Created by sbsatter on 9/19/18.
 */
@Data
public class SearchResults {
    public List<Movie> Search;
    public String totalResults;
    public String Response;
}
