package com.marwaeltayeb.movietrailer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieApiResponse {
    @SerializedName("page")
    private int pageNumber;
    @SerializedName("results")
    private List<Movie> movies;

    public int getPageNumber() {
        return pageNumber;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
