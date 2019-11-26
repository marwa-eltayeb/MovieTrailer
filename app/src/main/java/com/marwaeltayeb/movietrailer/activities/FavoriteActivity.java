package com.marwaeltayeb.movietrailer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.FavoriteAdapter;
import com.marwaeltayeb.movietrailer.database.MovieRoomViewModel;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.utils.SharedPreferencesUtils;

import java.util.List;

import static com.marwaeltayeb.movietrailer.utils.Constant.MOVIE;

public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.FavoriteAdapterOnClickHandler {

    private RecyclerView recyclerView;
    private FavoriteAdapter favoriteAdapter;
    private MovieRoomViewModel movieRoomViewModel;
    private TextView noBookmarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        // Set up recyclerView
        recyclerView = findViewById(R.id.favorite_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        recyclerView.setHasFixedSize(true);
        noBookmarks = findViewById(R.id.noBookmarks);

        favoriteAdapter = new FavoriteAdapter(this, this);
        movieRoomViewModel = ViewModelProviders.of(this).get(MovieRoomViewModel.class);

        loadMoviesFromDatabase();
    }

    /**
     * Load movies from database
     */
    private void loadMoviesFromDatabase() {
        // Observe the movieList from ViewModel
        movieRoomViewModel.getAllFavoriteMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable final List<Movie> favoriteMovies) {
                // Update the cached copy of the movies in the adapter.
                favoriteAdapter.submitList(favoriteMovies);
                Log.v("favoriteList", favoriteMovies.size() + "");
                if(favoriteMovies.isEmpty()){
                    noBookmarks.setVisibility(View.VISIBLE);
                    recyclerView.setVisibility(View.GONE);
                    noBookmarks.setText(getString(R.string.no_bookmarks));
                }
            }
        });

        // Set the adapter
        recyclerView.setAdapter(favoriteAdapter);
        favoriteAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(FavoriteActivity.this, MovieActivity.class);
        // Pass an object of movie class
        intent.putExtra(MOVIE, (movie));
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorite, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.delete_all) {
            movieRoomViewModel.deleteAll();
            SharedPreferencesUtils.clearSharedPreferences(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

