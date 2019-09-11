package com.marwaeltayeb.movietrailer.network;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.marwaeltayeb.movietrailer.models.Trailer;

import java.util.List;

public class TrailerViewModel extends AndroidViewModel {

    private TrailerRepository trailerRepository;

    public TrailerViewModel(@NonNull Application application) {
        super(application);
        trailerRepository = new TrailerRepository(application);
    }

    public LiveData<List<Trailer>> getAllTrailers() {
        return trailerRepository.getMutableLiveData();
    }
}
