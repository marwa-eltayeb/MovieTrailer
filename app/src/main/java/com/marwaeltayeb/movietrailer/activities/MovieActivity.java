package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.Util.Utility;
import com.marwaeltayeb.movietrailer.adapters.ReviewAdapter;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.Trailer;

import java.util.List;

import static com.marwaeltayeb.movietrailer.R.id.languageOfMovie;
import static com.marwaeltayeb.movietrailer.Util.Constant.BACKDROP_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.DESCRIPTION_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.IMAGE_URL;
import static com.marwaeltayeb.movietrailer.Util.Constant.LANGUAGE_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.RATING_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.RELEASE_DATE;
import static com.marwaeltayeb.movietrailer.Util.Constant.TITLE_OF_MOVIE;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        backdropImage = findViewById(R.id.backdropImage);
        movieTitle = findViewById(R.id.titleOfMovie);
        movieRating = findViewById(R.id.ratingOfMovie);
        movieDescription = findViewById(R.id.descriptionOfMovie);
        moveReleaseDate = findViewById(R.id.releaseDateOfMovie);
        movieLanguage = findViewById(languageOfMovie);

        Intent intent = getIntent();
        String titleOfMovie = intent.getStringExtra(TITLE_OF_MOVIE);
        String ratingOfMovie = intent.getStringExtra(RATING_OF_MOVIE);
        String imageOfMovie = intent.getStringExtra(BACKDROP_OF_MOVIE);
        String descriptionOfMovie = intent.getStringExtra(DESCRIPTION_OF_MOVIE);
        String releaseDateOfMovie = intent.getStringExtra(RELEASE_DATE);
        String formattedDate = Utility.formatDate(releaseDateOfMovie);
        String languageOfMovie = intent.getStringExtra(LANGUAGE_OF_MOVIE);

        movieTitle.setText(titleOfMovie);
        movieRating.setText(ratingOfMovie);
        movieDescription.setText(descriptionOfMovie);
        moveReleaseDate.setText(formattedDate  + " " + "|");
        movieLanguage.setText(languageOfMovie);

        Glide.with(this)
                .load(IMAGE_URL + imageOfMovie)
                .into(backdropImage);


        setupRecyclerViews();

    }

    private void setupRecyclerViews() {
        // Trailers
        trailersRecyclerView = findViewById(R.id.listOfTrailers);
        trailerAdapter = new TrailerAdapter(this,trailerList);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        trailersRecyclerView.setAdapter(trailerAdapter);

        // Reviews
        reviewsRecyclerView = findViewById(R.id.listOfReviews);
        reviewAdapter = new ReviewAdapter(this,reviewList);
        reviewsRecyclerView.setHasFixedSize(true);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        reviewsRecyclerView.setAdapter(reviewAdapter);
    }
}
