package com.marwaeltayeb.movietrailer.repositories;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.MovieApiResponse;
import com.marwaeltayeb.movietrailer.network.MovieService;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.utils.Constant.API_KEY;

public class SearchRepository {

    private List<Movie> searchedList = new ArrayList<>();
    private final MutableLiveData<List<Movie>> mutableLiveData = new MutableLiveData<>();

    private MovieService movieService;

    @Inject
    public SearchRepository(MovieService movieService){
        this.movieService = movieService;
    }

    public MutableLiveData<List<Movie>> getMutableLiveData(String query) {
       movieService.searchForMovies(query, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        Log.v("onResponse", response.body() + " Movies");

                        if (response.body() != null) {
                            searchedList = response.body().getMovies();
                            mutableLiveData.setValue(searchedList);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        Log.v("onFailure", " Failed to get movies");
                    }
                });

        return mutableLiveData;
    }
}
