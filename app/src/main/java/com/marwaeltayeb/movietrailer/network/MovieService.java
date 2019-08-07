package com.marwaeltayeb.movietrailer.network;

import com.marwaeltayeb.movietrailer.models.MovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Marwa on 7/7/2019.
 */

public interface MovieService {

    String API_KEY = "19cc511b297f733789a2a3bf0bc6a3b3";

    @GET("movie/{sort}")
    Call<MovieApiResponse> getMovies(@Path("sort") String sortBy,@Query("page") int page,@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieApiResponse> searchForMovies(@Query("query") String query ,@Query("api_key") String apiKey);

    @GET("movie/{movieId}/videos")
    Call<MovieApiResponse> getTrailers(@Path("movieId") int movieId ,@Query("api_key") String apiKey);

    @GET("movie/{movieId}/reviews")
    Call<MovieApiResponse> getReviews(@Path("movieId") int movieId ,@Query("api_key") String apiKey);

}
