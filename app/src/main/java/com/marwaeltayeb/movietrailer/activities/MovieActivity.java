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
import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.models.ReviewApiResponse;
import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.models.TrailerApiResponse;
import com.marwaeltayeb.movietrailer.network.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.R.id.languageOfMovie;
import static com.marwaeltayeb.movietrailer.R.id.listOfReviews;
import static com.marwaeltayeb.movietrailer.Util.Constant.BACKDROP_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.DESCRIPTION_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.ID_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.IMAGE_URL;
import static com.marwaeltayeb.movietrailer.Util.Constant.LANGUAGE_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.RATING_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.RELEASE_DATE;
import static com.marwaeltayeb.movietrailer.Util.Constant.TITLE_OF_MOVIE;
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
        movieTitle = findViewById(R.id.titleOfMovie);
        movieRating = findViewById(R.id.ratingOfMovie);
        movieDescription = findViewById(R.id.descriptionOfMovie);
        moveReleaseDate = findViewById(R.id.releaseDateOfMovie);
        movieLanguage = findViewById(languageOfMovie);

        Intent intent = getIntent();

        idOfMovie = intent.getStringExtra(ID_OF_MOVIE);
        //Toast.makeText(this, idOfMovie + "", Toast.LENGTH_SHORT).show();
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
        moveReleaseDate.setText(formattedDate + " " + "|");
        movieLanguage.setText(languageOfMovie);

        Glide.with(this)
                .load(IMAGE_URL + imageOfMovie)
                .into(backdropImage);


        //.setMovementMethod(new ScrollingMovementMethod());


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

    //420818
    //301528
    private void getReviews() {
        RetrofitClient.getInstance()
                .getMovieService().getReviews(("420818"), API_KEY)
                .enqueue(new Callback<ReviewApiResponse>() {
                    @Override
                    public void onResponse(Call<ReviewApiResponse> call, Response<ReviewApiResponse> response) {
                        if (response.body() != null) {
                            reviewList = response.body().getReviews();
                            reviewAdapter = new ReviewAdapter(getApplicationContext(), reviewList);
                            //Toast.makeText(MovieActivity.this, reviewList.size() + " ", Toast.LENGTH_SHORT).show();
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
                .getMovieService().getTrailers(("301528"), API_KEY)
                .enqueue(new Callback<TrailerApiResponse>() {

                    @Override
                    public void onResponse(Call<TrailerApiResponse> call, Response<TrailerApiResponse> response) {
                        if (response.body() != null) {
                            trailerList = response.body().getTrailers();
                            trailerAdapter = new TrailerAdapter(getApplicationContext(), trailerList);
                            //Toast.makeText(MovieActivity.this, trailerList.size() + " ", Toast.LENGTH_SHORT).show();
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
