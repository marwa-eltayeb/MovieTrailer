package com.marwaeltayeb.movietrailer.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void insert(MovieEntry movieEntry);

    @Query("SELECT * from movie_table")
    LiveData<List<MovieEntry>> getAllMovies();

}
