package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        backdropImage = findViewById(R.id.backdropImage);
        movieTitle = findViewById(R.id.titleOfMovie);
        movieRating = findViewById(R.id.ratingOfMovie);
        movieDescription = findViewById(R.id.descriptionOfMovie);
        moveReleaseDate = findViewById(R.id.releaseDateOfMovie);
        movieLanguage = findViewById(R.id.languageOfMovie);

        Intent intent = getIntent();
        String titleOfMovie = intent.getStringExtra(TITLE_OF_MOVIE);
        String ratingOfMovie = intent.getStringExtra(RATING_OF_MOVIE);
        String imageOfMovie = intent.getStringExtra(BACKDROP_OF_MOVIE);
        String descriptionOfMovie = intent.getStringExtra(DESCRIPTION_OF_MOVIE);
        String releaseDateOfMovie = intent.getStringExtra(RELEASE_DATE);
        String languageOfMovie = intent.getStringExtra(LANGUAGE_OF_MOVIE);

        movieTitle.setText(titleOfMovie);
        movieRating.setText(ratingOfMovie);
        movieDescription.setText(descriptionOfMovie);
        moveReleaseDate.setText(releaseDateOfMovie  + " " + "|");
        movieLanguage.setText(languageOfMovie);

        Glide.with(this)
                .load(IMAGE_URL + imageOfMovie)
                .into(backdropImage);


    }
}
