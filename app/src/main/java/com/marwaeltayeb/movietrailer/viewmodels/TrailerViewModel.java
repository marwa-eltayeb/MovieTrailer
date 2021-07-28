package com.marwaeltayeb.movietrailer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.repositories.TrailerRepository;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class TrailerViewModel extends ViewModel {

    private final TrailerRepository trailerRepository;

    @Inject
    public TrailerViewModel(TrailerRepository trailerRepository) {
        this.trailerRepository = trailerRepository;
    }

    public LiveData<List<Trailer>> getAllTrailers() {
        return trailerRepository.getMutableLiveData();
    }
}
