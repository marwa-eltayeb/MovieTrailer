package com.marwaeltayeb.movietrailer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.models.TrailerApiResponse;
import com.marwaeltayeb.movietrailer.network.MovieService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.utils.Constant.API_KEY;
import static com.marwaeltayeb.movietrailer.activities.MovieActivity.idOfMovie;

public class TrailerRepository {

    private List<Trailer> trailerList = new ArrayList<>();
    private final MutableLiveData<List<Trailer>> mutableLiveData = new MutableLiveData<>();

    private final MovieService movieService;

    @Inject
    public TrailerRepository(MovieService movieService){
        this.movieService = movieService;
    }

    public MutableLiveData<List<Trailer>> getMutableLiveData() {
        movieService.getTrailers((idOfMovie), API_KEY)
                .enqueue(new Callback<TrailerApiResponse>() {
                    @Override
                    public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> response) {
                        Log.v("onResponse", "Succeeded Trailers");
                        if (response.body() != null) {
                            trailerList = response.body().getTrailers();
                            mutableLiveData.setValue(trailerList);
                        }
                    }

                    @Override
                    public void onFailure(Call<TrailerApiResponse> call, Throwable t) {
                        Log.v("onFailure", "Failed to get Trailers");
                    }
                });

        return mutableLiveData;
    }
}
