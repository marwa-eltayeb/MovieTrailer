package com.marwaeltayeb.movietrailer.data.local;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.marwaeltayeb.movietrailer.data.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Movie movie);

    @Query("DELETE FROM movie_table WHERE movieid = :movie_id")
    void deleteById(int movie_id);

    @Query("SELECT * from movie_table")
    LiveData<List<Movie>> getAllMovies();

    @Query("DELETE FROM movie_table")
    void deleteAll();
}
