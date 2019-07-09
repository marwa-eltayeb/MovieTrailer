package com.marwaeltayeb.movietrailer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

class Movie {
    @SerializedName("id")
    public int movieId;
    @SerializedName("vote_average")
    public double movieVote;
    @SerializedName("title")
    public String movieTitle;
    @SerializedName("poster_path")
    public String moviePoster;
    @SerializedName("overview")
    public String movieDescription;
    @SerializedName("release_date")
    public String movieReleaseDate;
    @SerializedName("original_language")
    public String movieLanguage;
    @SerializedName("genre_ids")
    public List genreIds;
}

public class MovieApiResponse {
    @SerializedName("page")
    public int pageNumber;
    @SerializedName("results")
    public List<Movie> movies;
}
