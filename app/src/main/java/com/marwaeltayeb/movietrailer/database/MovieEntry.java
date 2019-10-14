package com.marwaeltayeb.movietrailer.database;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

@Entity(tableName = "movie_table")
public class MovieEntry implements Serializable {

    private boolean isFavorite;
    @PrimaryKey(autoGenerate = true)
    private int id;
    private String movieId;
    private String title;
    private String vote;
    private String backdrop;
    private String description;
    private String releaseDate;
    private String language;
    @ColumnInfo(name = "genres")
    @Ignore
    private ArrayList<Integer> genreIds;

    @Ignore
    public MovieEntry(){}

    public MovieEntry(boolean isFavorite,String movieId, @NonNull String title, String vote, String description, String releaseDate, String language) {
        this.isFavorite = isFavorite;
        this.id = id;
        this.movieId =movieId;
        this.title = title;
        this.vote = vote;
        this.description = description;
        this.releaseDate = releaseDate;
        this.language = language;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote() {
        return vote;
    }

    public void setVote(String vote) {
        this.vote = vote;
    }

    public String getBackdrop() {
        return backdrop;
    }

    public void setBackdrop(String backdrop) {
        this.backdrop = backdrop;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Ignore
    public ArrayList<Integer> getGenreIds() {
        return genreIds;
    }

    @Ignore
    public void setGenreIds(ArrayList<Integer> genreIds) {
        this.genreIds = genreIds;
    }



}
