package com.marwaeltayeb.movietrailer;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.marwaeltayeb.movietrailer.models.MovieApiResponse;
import com.marwaeltayeb.movietrailer.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RetrofitClient.getInstance()
                .getMovieService().getMovies("19cc511b297f733789a2a3bf0bc6a3b3",1)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        MovieApiResponse movieApiResponse = response.body();
                        Toast.makeText(MainActivity.this, movieApiResponse.movies.size() + "", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {

                    }
                });

    }
}
