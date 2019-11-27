package com.marwaeltayeb.movietrailer.models;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "movie_table")
public class Movie implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int databaseId;
    @SerializedName("id")
    public String movieId;
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
    @ColumnInfo(name = "genres")
    @Ignore
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;

    public Movie(String movieId, @NonNull String movieTitle, String movieVote, String movieDescription, String movieReleaseDate, String movieLanguage, String moviePoster,String movieBackdrop) {
        this.movieId =movieId;
        this.movieTitle = movieTitle;
        this.movieVote = movieVote;
        this.movieDescription = movieDescription;
        this.movieReleaseDate = movieReleaseDate;
        this.movieLanguage = movieLanguage;
        this.moviePoster = moviePoster;
        this.movieBackdrop =movieBackdrop;
    }

    @Ignore
    public Movie(){}

    public int getDatabaseId() {
        return databaseId;
    }

    public String getMovieId() {
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

    @Ignore
    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    @NonNull
    @Override
    public String toString() {
        return this.movieTitle;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public void setMovieVote(String movieVote) {
        this.movieVote = movieVote;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public void setMovieBackdrop(String movieBackdrop) {
        this.movieBackdrop = movieBackdrop;
    }

    public void setMovieDescription(String movieDescription) {
        this.movieDescription = movieDescription;
    }

    public void setMovieReleaseDate(String movieReleaseDate) {
        this.movieReleaseDate = movieReleaseDate;
    }

    public void setMovieLanguage(String movieLanguage) {
        this.movieLanguage = movieLanguage;
    }

    @Ignore
    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }

}
