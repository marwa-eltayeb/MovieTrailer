package com.marwaeltayeb.movietrailer.ui.seeall;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.data.MovieRepository;
import com.marwaeltayeb.movietrailer.data.model.Trailer;
import com.marwaeltayeb.movietrailer.data.model.TrailerApiResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
class SeeAllViewModel extends ViewModel {

    private static final String TAG = "SeeAllViewModel";

    private final MutableLiveData<List<Trailer>> mutableTrailerList = new MutableLiveData<>();

    private final MovieRepository movieRepository;

    @Inject
    public SeeAllViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public LiveData<List<Trailer>> getAllTrailers() {
        movieRepository.getTrailerList()
                .enqueue(new Callback<TrailerApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<TrailerApiResponse> call, @NonNull Response<TrailerApiResponse> response) {
                        Log.d(TAG, "Succeeded Trailers");
                        if (response.body() != null) {
                            List<Trailer> trailerList = response.body().getTrailers();
                            mutableTrailerList.setValue(trailerList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<TrailerApiResponse> call, @NonNull Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });

        return mutableTrailerList;
    }
}
