package com.marwaeltayeb.movietrailer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewApiResponse {

    @SerializedName("id")
    private Integer id;
    @SerializedName("results")
    private List<Review> reviews;

    public Integer getId() {
        return id;
    }

    public List<Review> getReviews() {
        return reviews;
    }
}
