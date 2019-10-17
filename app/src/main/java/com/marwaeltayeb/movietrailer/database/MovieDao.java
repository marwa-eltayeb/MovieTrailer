package com.marwaeltayeb.movietrailer.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.marwaeltayeb.movietrailer.models.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("DELETE FROM movie_table WHERE movieid = :movie_id")
    void deleteById(int movie_id);

    @Query("SELECT * from movie_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("DELETE FROM movie_table")
    void deleteAll();

}
