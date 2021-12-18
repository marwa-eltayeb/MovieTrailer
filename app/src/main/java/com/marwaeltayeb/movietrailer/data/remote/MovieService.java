package com.marwaeltayeb.movietrailer.data.remote;

import com.marwaeltayeb.movietrailer.data.model.MovieApiResponse;
import com.marwaeltayeb.movietrailer.data.model.ReviewApiResponse;
import com.marwaeltayeb.movietrailer.data.model.TrailerApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MovieService {

    @GET("movie/{sort}")
    Call<MovieApiResponse> getMovies(@Path("sort") String sortBy,@Query("page") int page,@Query("api_key") String apiKey);

    @GET("search/movie")
    Call<MovieApiResponse> searchForMovies(@Query("query") String query ,@Query("api_key") String apiKey);

    @GET("movie/{movieId}/videos")
    Call<TrailerApiResponse> getTrailers(@Path("movieId") String movieId , @Query("api_key") String apiKey);

    @GET("movie/{movieId}/reviews")
    Call<ReviewApiResponse> getReviews(@Path("movieId") String movieId , @Query("api_key") String apiKey);

}
