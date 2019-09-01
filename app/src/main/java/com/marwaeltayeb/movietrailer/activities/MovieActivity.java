package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.Util.Utility;
import com.marwaeltayeb.movietrailer.adapters.ReviewAdapter;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.databinding.ActivityMovieBinding;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.ReviewApiResponse;
import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.models.TrailerApiResponse;
import com.marwaeltayeb.movietrailer.network.RetrofitClient;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.R.id.listOfReviews;
import static com.marwaeltayeb.movietrailer.Util.Constant.IMAGE_URL;
import static com.marwaeltayeb.movietrailer.Util.Constant.MOVIE;
import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;

public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;

    List<Review> reviewList;
    List<Trailer> trailerList;
    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter;
    RecyclerView reviewsRecyclerView, trailersRecyclerView;

    private String idOfMovie;
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_movie);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        // Receive the movie object
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(MOVIE);

        idOfMovie = movie.getMovieId();
        binding.titleOfMovie.setText(movie.getMovieTitle());
        binding.ratingOfMovie.setText(movie.getMovieVote());
        binding.descriptionOfMovie.setText(movie.getMovieDescription());
        String formattedDate = Utility.formatDate(movie.getMovieReleaseDate());
        binding.releaseDateOfMovie.setText(formattedDate + " " + "|");
        binding.languageOfMovie.setText(movie.getMovieLanguage());

        Glide.with(this)
                .load(IMAGE_URL + movie.getMovieBackdrop())
                .into(binding.backdropImage);

        getGenres();

        setupRecyclerViews();
        getReviews();
        getTrailers();

    }

    private void setupRecyclerViews() {
        // Trailers
        trailersRecyclerView = findViewById(R.id.listOfTrailers);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Reviews
        reviewsRecyclerView = findViewById(listOfReviews);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
    }

    private void getReviews() {
        RetrofitClient.getInstance()
                .getMovieService().getReviews((idOfMovie), API_KEY)
                .enqueue(new Callback<ReviewApiResponse>() {
                    @Override
                    public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                        if (response.body() != null) {
                            reviewList = response.body().getReviews();
                            reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewList);
                        }
                        reviewsRecyclerView.setAdapter(reviewAdapter);
                    }

                    @Override
                    public void onFailure(Call<ReviewApiResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to get Reviews", Toast.LENGTH_SHORT).show();
                        t.printStackTrace();
                    }
                });
    }

    private void getTrailers() {
        RetrofitClient.getInstance()
                .getMovieService().getTrailers((idOfMovie), API_KEY)
                .enqueue(new Callback<TrailerApiResponse>() {

                    @Override
                    public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> response) {
                        if (response.body() != null) {
                            trailerList = response.body().getTrailers();
                            trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerList);
                        }
                        trailersRecyclerView.setAdapter(trailerAdapter);
                    }

                    @Override
                    public void onFailure(Call<TrailerApiResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "Failed to get Trailers", Toast.LENGTH_SHORT).show();

                    }
                });
    }

    private void getGenres() {
        int genre_one = 0;
        int genre_two = 0;
        int genre_three = 0;

        try {
            genre_one = movie.getGenreIds().get(0);
            genre_two = movie.getGenreIds().get(1);
            genre_three = movie.getGenreIds().get(2);
        } catch (IndexOutOfBoundsException e) {
            e.printStackTrace();
        }

        HashMap<Integer, String> map = new HashMap<Integer, String>();
        map.put(28, "Action");
        map.put(12, "Adventure");
        map.put(16, "Animation");
        map.put(35, "Comedy");
        map.put(80, "Crime");
        map.put(99, "Documentary");
        map.put(18, "Drama");
        map.put(10751, "Family");
        map.put(14, "Fantasy");
        map.put(36, "History");
        map.put(27, "Horror");
        map.put(10402, "Music");
        map.put(9648, "Mystery");
        map.put(10749, "Romance");
        map.put(878, "Science Fiction");
        map.put(10770, "TV Movie");
        map.put(53, "Thriller");
        map.put(10752, "War");
        map.put(37, "Western");

        binding.genreOne.setText(map.get(genre_one));
        binding.genreTwo.setText(map.get(genre_two));
        binding.genreThree.setText(map.get(genre_three));

    }

}
