package com.marwaeltayeb.movietrailer.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Movie {
    @SerializedName("id")
    public int movieId;
    @SerializedName("vote_average")
    private String movieVote;
    @SerializedName("title")
    private String movieTitle;
    @SerializedName("poster_path")
    private String moviePoster;
    @SerializedName("backdrop_path")
    private String movieBackdrop;
    @SerializedName("overview")
    private String movieDescription;
    @SerializedName("release_date")
    private String movieReleaseDate;
    @SerializedName("original_language")
    private String movieLanguage;
    @SerializedName("genre_ids")
    private List genreIds;

    public int getMovieId() {
        return movieId;
    }

    public String getMovieVote() {
        return movieVote;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public String getMovieBackdrop() {
        return movieBackdrop;
    }

    public String getMovieDescription() {
        return movieDescription;
    }

    public String getMovieReleaseDate() {
        return movieReleaseDate;
    }

    public String getMovieLanguage() {
        return movieLanguage;
    }

    public List getGenreIds() {
        return genreIds;
    }

    @Override
    public String toString() {
        return this.movieTitle;
    }
}
