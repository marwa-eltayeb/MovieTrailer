package com.marwaeltayeb.movietrailer.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import java.util.List;

public class MovieRoomViewModel extends AndroidViewModel {
    
    private MovieRepository mRepository;

    private LiveData<List<MovieEntry>> mAllMovies;

    public MovieRoomViewModel(Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();
    }

    public LiveData<List<MovieEntry>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(MovieEntry movieEntry) {
        mRepository.insert(movieEntry);
    }
}
