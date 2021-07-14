package com.marwaeltayeb.movietrailer.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

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
    @SerializedName("genre_ids")
    private ArrayList<Integer> genreIds;

    public Movie(String movieId, @NonNull String movieTitle, String movieVote, String movieDescription, String movieReleaseDate, String movieLanguage, String moviePoster, String movieBackdrop, ArrayList<Integer> genreIds) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.movieVote = movieVote;
        this.movieDescription = movieDescription;
        this.movieReleaseDate = movieReleaseDate;
        this.movieLanguage = movieLanguage;
        this.moviePoster = moviePoster;
        this.movieBackdrop = movieBackdrop;
        this.genreIds = genreIds;
    }

    @Ignore
    public Movie() {
    }

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

    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    public void setDatabaseId(int databaseId) {
        this.databaseId = databaseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return getMovieId().equals(movie.getMovieId()) &&
                getMovieVote().equals(movie.getMovieVote()) &&
                getMovieTitle().equals(movie.getMovieTitle()) &&
                getMovieDescription().equals(movie.getMovieDescription()) &&
                getMovieReleaseDate().equals(movie.getMovieReleaseDate()) &&
                getMovieLanguage().equals(movie.getMovieLanguage());
    }


}

