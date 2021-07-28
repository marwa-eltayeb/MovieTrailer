package com.marwaeltayeb.movietrailer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.repositories.SearchRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class SearchViewModel extends ViewModel {

    private final SearchRepository searchRepository;

    @Inject
    public SearchViewModel(SearchRepository searchRepository) {
        this.searchRepository = searchRepository;
    }

    public LiveData<List<Movie>> getSearchedMovies(String query) {
        return searchRepository.getMutableLiveData(query);
    }
}
