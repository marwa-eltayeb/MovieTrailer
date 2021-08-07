package com.marwaeltayeb.movietrailer.database;

import androidx.lifecycle.LiveData;

import com.marwaeltayeb.movietrailer.models.Movie;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.inject.Inject;

public class MovieRepository {

    private final MovieDao mMovieDao;
    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    private final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    @Inject
    public MovieRepository(MovieDao mMovieDao) {
        this.mMovieDao = mMovieDao;
    }

    LiveData<List<Movie>> getAllMovies() {
        return mMovieDao.getAllMovies();
    }

    public void insert(Movie movie) {
        databaseWriteExecutor.execute(() -> {
            mMovieDao.insert(movie);
        });
    }

    public void deleteById(int movieId) {
        databaseWriteExecutor.execute(() -> {
            mMovieDao.deleteById(movieId);
        });
    }

    public void deleteAll(){
        databaseWriteExecutor.execute(mMovieDao::deleteAll);
    }
}
