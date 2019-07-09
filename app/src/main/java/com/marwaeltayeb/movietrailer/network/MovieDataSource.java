package com.marwaeltayeb.movietrailer.network;

import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.MovieApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Marwa on 7/10/2019.
 */

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {


    private static final int FIRST_PAGE = 1;

    public static final int PAGE_SIZE = 20;

    private static final String API_KEY = "19cc511b297f733789a2a3bf0bc6a3b3";


    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getMovieService().getMovies(FIRST_PAGE, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        if (response.body() != null) {
                            // Fetch data and pass the result null for the previous page
                            callback.onResult(response.body().getMovies(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        //Toast.makeText(Context.getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getMovieService().getMovies(params.key, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        /*
                         If the current page is greater than one,
                         we are decrementing the page number
                         else there is no previous page.
                        */
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            // Passing the loaded data and the previous page key
                            callback.onResult(response.body().getMovies(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        //Toast.makeText(Context.getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getMovieService().getMovies(params.key, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        if (response.body() != null) {
                            // If the response has next page, increment the next page number
                            Integer key = response.body().getMovies().size() == PAGE_SIZE ? params.key + 1 : null;

                            // Passing the loaded data and next page value
                            callback.onResult(response.body().getMovies(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        //Toast.makeText(Context.getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

