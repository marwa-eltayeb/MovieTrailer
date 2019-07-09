package com.marwaeltayeb.movietrailer.network;

import com.marwaeltayeb.movietrailer.models.MovieApiResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Marwa on 7/7/2019.
 */

public interface MovieService {

    @GET("movie/popular")
    Call<MovieApiResponse> getMovies(@Query("page") int page,@Query("api_key") String apiKey);

}
