package com.marwaeltayeb.movietrailer.network;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.models.Trailer;

import java.util.List;

public class TrailerViewModel extends ViewModel {

    private final TrailerRepository trailerRepository;

    public TrailerViewModel() {
        trailerRepository = new TrailerRepository();
    }

    public LiveData<List<Trailer>> getAllTrailers() {
        return trailerRepository.getMutableLiveData();
    }
}
