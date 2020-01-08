package com.marwaeltayeb.movietrailer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.ReviewAdapter;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.database.MovieRoomViewModel;
import com.marwaeltayeb.movietrailer.databinding.ActivityMovieBinding;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.network.ReviewViewModel;
import com.marwaeltayeb.movietrailer.network.TrailerViewModel;
import com.marwaeltayeb.movietrailer.utils.Genres;
import com.marwaeltayeb.movietrailer.utils.SharedPreferencesUtils;
import com.marwaeltayeb.movietrailer.utils.Utility;

import java.util.List;

import static com.marwaeltayeb.movietrailer.R.id.listOfReviews;
import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;
import static com.marwaeltayeb.movietrailer.utils.Constant.MOVIE;

public class MovieActivity extends AppCompatActivity {

    private ActivityMovieBinding binding;

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private RecyclerView reviewsRecyclerView, trailersRecyclerView;

    private ReviewViewModel reviewViewModel;
    private TrailerViewModel trailerViewModel;
    private MovieRoomViewModel movieRoomViewModel;

    public static String idOfMovie;
    private String title;
    private String formattedDate;
    private String vote;
    private String description;
    private String language;
    private String poster;
    private String backDrop;

    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_movie);

        reviewViewModel = ViewModelProviders.of(this).get(ReviewViewModel.class);
        trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        movieRoomViewModel = ViewModelProviders.of(this).get(MovieRoomViewModel.class);

        setupRecyclerViews();

        receiveMovieDetails();

        getReviews();
        getTrailers();

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFavourite();
            }
        });
        binding.txtSeaAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSeeAllActivity();
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

    private void receiveMovieDetails() {
        // Receive the movie object
        Intent intent = getIntent();
        movie = (Movie) intent.getSerializableExtra(MOVIE);

        idOfMovie = movie.getMovieId();
        title = movie.getMovieTitle();
        vote = movie.getMovieVote();
        description = movie.getMovieDescription();
        formattedDate = Utility.formatDate(movie.getMovieReleaseDate());
        language = movie.getMovieLanguage();
        backDrop = movie.getMovieBackdrop();
        poster = movie.getMoviePoster();

        binding.titleOfMovie.setText(title);
        binding.ratingOfMovie.setText(vote);
        binding.descriptionOfMovie.setText(description);
        binding.releaseDateOfMovie.setText(formattedDate + " " + "|");
        binding.languageOfMovie.setText(language);

        Glide.with(this)
                .load(IMAGE_URL + backDrop)
                .into(binding.backdropImage);

        getGenres();

        if (!isNetworkConnected()) {
            trailersRecyclerView.setVisibility(View.GONE);
            binding.noTrailers.setVisibility(View.VISIBLE);

            reviewsRecyclerView.setVisibility(View.GONE);
            binding.noReviews.setVisibility(View.VISIBLE);
        }

        // If movie is inserted
        if (SharedPreferencesUtils.getInsertState(this, idOfMovie)) {
            binding.fab.setImageResource(R.drawable.favorite_red);
        }
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
        } catch (NullPointerException e) {
            e.printStackTrace();
        }

        if (movie.getGenreIds() == null) {
            binding.genreOne.setVisibility(View.GONE);
            binding.genreTwo.setVisibility(View.GONE);
            binding.genreThree.setVisibility(View.GONE);
        }

        binding.genreOne.setText(Genres.getGenres().get(genre_one));
        binding.genreTwo.setText(Genres.getGenres().get(genre_two));
        binding.genreThree.setText(Genres.getGenres().get(genre_three));

    }

    private void toggleFavourite() {
        // If movie is not bookmarked
        if (!SharedPreferencesUtils.getInsertState(this, idOfMovie)) {
            binding.fab.setImageResource(R.drawable.favorite_red);
            insertFavoriteMovie();
            SharedPreferencesUtils.setInsertState(this, idOfMovie, true);
            showSnackBar("Bookmark Added");
        } else {
            binding.fab.setImageResource(R.drawable.favorite_border_red);
            deleteFavoriteMovieById();
            SharedPreferencesUtils.setInsertState(this, idOfMovie, false);
            showSnackBar("Bookmark Removed");
        }
    }

    private void insertFavoriteMovie() {
        movie = new Movie(idOfMovie, title, vote, description, formattedDate, language, poster, backDrop);
        movieRoomViewModel.insert(movie);
    }

    private void deleteFavoriteMovieById() {
        movieRoomViewModel.deleteById(Integer.parseInt(idOfMovie));
    }

    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        return (networkInfo != null && networkInfo.isConnected());
    }

    private void showSnackBar(String text) {
        Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_SHORT).show();
    }

    private void goToSeeAllActivity(){
        Intent intent = new Intent(this,SeeAllActivity.class);
        startActivity(intent);
    }
}
