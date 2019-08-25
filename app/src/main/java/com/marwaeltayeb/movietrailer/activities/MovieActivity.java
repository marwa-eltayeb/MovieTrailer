package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.Util.Utility;
import com.marwaeltayeb.movietrailer.adapters.ReviewAdapter;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.ReviewApiResponse;
import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.models.TrailerApiResponse;
import com.marwaeltayeb.movietrailer.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.R.id.descriptionOfMovie;
import static com.marwaeltayeb.movietrailer.R.id.languageOfMovie;
import static com.marwaeltayeb.movietrailer.R.id.listOfReviews;
import static com.marwaeltayeb.movietrailer.R.id.ratingOfMovie;
import static com.marwaeltayeb.movietrailer.R.id.titleOfMovie;
import static com.marwaeltayeb.movietrailer.Util.Constant.IMAGE_URL;
import static com.marwaeltayeb.movietrailer.Util.Constant.MOVIE;
import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;

public class MovieActivity extends AppCompatActivity {

    ImageView backdropImage;
    TextView movieTitle;
    TextView movieRating;
    TextView movieDescription;
    TextView moveReleaseDate;
    TextView movieLanguage;

    List<Review> reviewList;
    List<Trailer> trailerList;
    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter;
    RecyclerView reviewsRecyclerView, trailersRecyclerView;

    String idOfMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        backdropImage = findViewById(R.id.backdropImage);
        movieTitle = findViewById(titleOfMovie);
        movieRating = findViewById(ratingOfMovie);
        movieDescription = findViewById(descriptionOfMovie);
        moveReleaseDate = findViewById(R.id.releaseDateOfMovie);
        movieLanguage = findViewById(languageOfMovie);

        // Receive the movie object
        Intent intent = getIntent();
        Movie movie = (Movie) intent.getSerializableExtra(MOVIE);

        idOfMovie = movie.getMovieId();
        movieTitle.setText(movie.getMovieTitle());
        movieRating.setText(movie.getMovieVote());
        movieDescription.setText(movie.getMovieDescription());
        String formattedDate = Utility.formatDate(movie.getMovieReleaseDate());
        moveReleaseDate.setText(formattedDate + " " + "|");
        movieLanguage.setText(movie.getMovieLanguage());

        Glide.with(this)
                .load(IMAGE_URL + movie.getMovieBackdrop())
                .into(backdropImage);

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

}
