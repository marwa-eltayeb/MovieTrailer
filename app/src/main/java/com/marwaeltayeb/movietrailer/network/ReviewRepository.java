package com.marwaeltayeb.movietrailer.network;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.ReviewApiResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.activities.MovieActivity.idOfMovie;
import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;

public class ReviewRepository {

    private List<Review> reviewList = new ArrayList<>();
    private final MutableLiveData<List<Review>> mutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Review>> getMutableLiveData() {
        RetrofitClient.getInstance()
                .getMovieService().getReviews((idOfMovie), API_KEY)
                .enqueue(new Callback<ReviewApiResponse>() {
                    @Override
                    public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                        Log.v("onResponse", "Succeeded reviews");
                        if (response.body() != null) {
                            reviewList = response.body().getReviews();
                            mutableLiveData.setValue(reviewList);
                        }
                    }

                    @Override
                    public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
                        Log.v("onFailure", "Failed to get Reviews");
                    }
                });

        return mutableLiveData;
    }
}
