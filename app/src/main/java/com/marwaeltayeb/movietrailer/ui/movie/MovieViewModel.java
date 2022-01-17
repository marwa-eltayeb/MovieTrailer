package com.marwaeltayeb.movietrailer.ui.movie;

import static com.marwaeltayeb.movietrailer.data.local.MovieDatabase.databaseWriteExecutor;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.data.MovieRepository;
import com.marwaeltayeb.movietrailer.data.model.Cast;
import com.marwaeltayeb.movietrailer.data.model.CastApiResponse;
import com.marwaeltayeb.movietrailer.data.model.Movie;
import com.marwaeltayeb.movietrailer.data.model.Review;
import com.marwaeltayeb.movietrailer.data.model.ReviewApiResponse;
import com.marwaeltayeb.movietrailer.data.model.Trailer;
import com.marwaeltayeb.movietrailer.data.model.TrailerApiResponse;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

@HiltViewModel
public class MovieViewModel extends ViewModel {

    private static final String TAG = "MovieViewModel";

    private final MutableLiveData<List<Trailer>> mutableTrailerList = new MutableLiveData<>();
    private final MutableLiveData<List<Review>> mutableReviewList = new MutableLiveData<>();
    private final MutableLiveData<List<Cast>> mutableCastList = new MutableLiveData<>();

    private final MovieRepository movieRepository;

    @Inject
    public MovieViewModel(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    public void insert(Movie movie) {
        databaseWriteExecutor.execute(() -> {
            movieRepository.insert(movie);
        });
    }

    public void deleteById(int movieId) {
        databaseWriteExecutor.execute(() -> {
            movieRepository.deleteById(movieId);
        });
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

    public LiveData<List<Review>> getAllReviews() {
        movieRepository.getReviewList()
                .enqueue(new Callback<ReviewApiResponse>() {
                    @Override
                    public void onResponse(@NonNull Call<ReviewApiResponse> call, @NonNull Response<ReviewApiResponse> response) {
                        Log.d(TAG, "Succeeded reviews");
                        if (response.body() != null) {
                            List<Review> reviewList = response.body().getReviews();
                            mutableReviewList.setValue(reviewList);
                        }
                    }

                    @Override
                    public void onFailure(@NonNull Call<ReviewApiResponse> call, @NonNull Throwable t) {
                        Log.d(TAG, t.getMessage());
                    }
                });

        return mutableReviewList;
    }

    public LiveData<List<Cast>> getAllCast() {
        movieRepository.getCastList().enqueue(new Callback<CastApiResponse>() {
            @Override
            public void onResponse(Call<CastApiResponse> call, Response<CastApiResponse> response) {
                Log.d(TAG, "Succeeded cast");
                if (response.body() != null) {
                    List<Cast> castList = response.body().getCast();
                    mutableCastList.setValue(castList);
                }
            }

            @Override
            public void onFailure(Call<CastApiResponse> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

        return mutableCastList;
    }
}

