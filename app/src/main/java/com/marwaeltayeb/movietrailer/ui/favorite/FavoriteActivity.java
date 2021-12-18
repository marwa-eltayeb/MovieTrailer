package com.marwaeltayeb.movietrailer.ui.favorite;

import static com.marwaeltayeb.movietrailer.utils.Constant.MOVIE;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.data.model.Movie;
import com.marwaeltayeb.movietrailer.databinding.ActivityFavoriteBinding;
import com.marwaeltayeb.movietrailer.ui.movie.MovieActivity;
import com.marwaeltayeb.movietrailer.utils.SharedPreferencesUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FavoriteActivity extends AppCompatActivity implements FavoriteAdapter.FavoriteAdapterOnClickHandler {

    private ActivityFavoriteBinding binding;
    private FavoriteAdapter favoriteAdapter;
    private FavoriteViewModel favoriteViewModel;
    private TextView noBookmarks;
    public static boolean isFavoriteActivityRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_favorite);

        setupViews();

        favoriteViewModel = new ViewModelProvider(this).get(FavoriteViewModel.class);

        loadMoviesFromDatabase();
    }

    private void setupViews() {
        binding.favoriteList.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        binding.favoriteList.setHasFixedSize(true);
        noBookmarks = findViewById(R.id.noBookmarks);

        favoriteAdapter = new FavoriteAdapter(this, this);
        binding.favoriteList.setAdapter(favoriteAdapter);
    }

    private void loadMoviesFromDatabase() {
        favoriteViewModel.getAllFavoriteMovies().observe(this, favoriteMovies -> {
            // Update the cached copy of the movies in the adapter.
            favoriteAdapter.submitList(favoriteMovies);
            Log.d("favoriteList", favoriteMovies.size() + "");
            if(favoriteMovies.isEmpty()){
                noBookmarks.setVisibility(View.VISIBLE);
                binding.favoriteList.setVisibility(View.GONE);
                noBookmarks.setText(getString(R.string.no_bookmarks));
            }
        });
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(FavoriteActivity.this, MovieActivity.class);
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
            favoriteViewModel.deleteAll();
            SharedPreferencesUtils.clearSharedPreferences(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        isFavoriteActivityRunning = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        isFavoriteActivityRunning = false;
    }
}

