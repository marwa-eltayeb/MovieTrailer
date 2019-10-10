package com.marwaeltayeb.movietrailer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.utils.Genres;
import com.marwaeltayeb.movietrailer.utils.Utility;
import com.marwaeltayeb.movietrailer.adapters.ReviewAdapter;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.databinding.ActivityMovieBinding;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.network.ReviewViewModel;
import com.marwaeltayeb.movietrailer.network.TrailerViewModel;

import java.util.List;

import static com.marwaeltayeb.movietrailer.R.id.listOfReviews;
import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;
import static com.marwaeltayeb.movietrailer.utils.Constant.MOVIE;

public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;

    ReviewAdapter reviewAdapter;
    TrailerAdapter trailerAdapter;
    RecyclerView reviewsRecyclerView, trailersRecyclerView;

    private ReviewViewModel reviewViewModel;
    private TrailerViewModel trailerViewModel;

    public static String idOfMovie;
    private Movie movie;

    private boolean isFavorite = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);

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

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourite();
            }
        });

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

    public void getTrailers() {
        trailerViewModel.getAllTrailers().observe(this, new Observer<List<Trailer>>() {
            @Override
            public void onChanged(@Nullable List<Trailer> trailers) {
                trailerAdapter = new TrailerAdapter(getApplicationContext(), trailers);

                if (trailers != null && trailers.isEmpty()) {
                    trailersRecyclerView.setVisibility(View.GONE);
                    binding.noTrailers.setVisibility(View.VISIBLE);
                }
                trailersRecyclerView.setAdapter(trailerAdapter);
            }
        });
    }

    public void getReviews() {
        reviewViewModel.getAllReviews().observe(this, new Observer<List<Review>>() {
            @Override
            public void onChanged(@Nullable List<Review> reviews) {
                reviewAdapter = new ReviewAdapter(getApplicationContext(), reviews);

                if (reviews != null && reviews.isEmpty()) {
                    reviewsRecyclerView.setVisibility(View.GONE);
                    binding.noReviews.setVisibility(View.VISIBLE);
                }
                reviewsRecyclerView.setAdapter(reviewAdapter);
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

        binding.genreOne.setText(Genres.getGenres().get(genre_one));
        binding.genreTwo.setText(Genres.getGenres().get(genre_two));
        binding.genreThree.setText(Genres.getGenres().get(genre_three));

    }

    private void toggleFavourite() {
        if (!isFavorite) {
            binding.fab.setImageResource(R.drawable.favorite_red);
            isFavorite = true;
        } else {
            binding.fab.setImageResource(R.drawable.favorite_border_red);
            isFavorite = false;
        }
    }
}
