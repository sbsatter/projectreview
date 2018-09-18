package com.sbsatter.projectreview.model;

import lombok.Data;

/**
 * Created by sbsatter on 9/19/18.
 */
@Data
public class Rating {
    public Integer id;
    public User user;
    public String imdbID;
    public String rating;
}
