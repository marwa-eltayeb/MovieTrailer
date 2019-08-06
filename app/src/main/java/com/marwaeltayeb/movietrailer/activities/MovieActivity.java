package com.marwaeltayeb.movietrailer.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.marwaeltayeb.movietrailer.R;

import static com.marwaeltayeb.movietrailer.Util.Constant.RATING_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.TITLE_OF_MOVIE;

public class MovieActivity extends AppCompatActivity {

    ImageView backdropImage;
    TextView movieTitle;
    TextView movieRating;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);

        backdropImage = findViewById(R.id.backdropImage);
        movieTitle = findViewById(R.id.titleOfMovie);
        movieRating = findViewById(R.id.ratingOfMovie);

        Intent intent = getIntent();
        String titleOfMovie = intent.getStringExtra(TITLE_OF_MOVIE);
        String ratingOfMovie = intent.getStringExtra(RATING_OF_MOVIE);
        movieTitle.setText(titleOfMovie);
        movieRating.setText(ratingOfMovie);


    }
}
