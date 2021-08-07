package com.marwaeltayeb.movietrailer.database;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.models.Movie;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class MovieRoomViewModel extends ViewModel {
    
    private final MovieRepository movieRepository;

    @Inject
    public MovieRoomViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Movie>> getAllFavoriteMovies() {
        return movieRepository.getAllMovies();
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
