package com.marwaeltayeb.movietrailer.network;

import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.models.TrailerApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.activities.MovieActivity.idOfMovie;
import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;

public class TrailerRepository {

    private List<Trailer> trailerList = new ArrayList<>();
    private MutableLiveData<List<Trailer>> mutableLiveData = new MutableLiveData<>();
    private Application application;

    public TrailerRepository(Application application) {
        this.application = application;
    }

    public MutableLiveData<List<Trailer>> getMutableLiveData() {
        RetrofitClient.getInstance()
                .getMovieService().getTrailers((idOfMovie), API_KEY)
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
