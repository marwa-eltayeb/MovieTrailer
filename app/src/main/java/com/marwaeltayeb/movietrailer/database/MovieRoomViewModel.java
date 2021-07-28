package com.marwaeltayeb.movietrailer.database;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.marwaeltayeb.movietrailer.models.Movie;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieRoomViewModel extends AndroidViewModel {
    
    private final MovieRepository movieRepository;

    private final LiveData<List<Movie>> mAllMovies;

    @Inject
    public MovieRoomViewModel(Application application, MovieRepository movieRepository) {
        super(application);
        this.movieRepository = movieRepository;
        mAllMovies = movieRepository.getAllMovies();
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return mAllMovies;
    }

    public void insert(Movie movie) {
        movieRepository.insert(movie);
    }

    public void deleteById(int movieId) {
        movieRepository.deleteById(movieId);
    }

    public void deleteAll() {
        movieRepository.deleteAll();
    }
}
