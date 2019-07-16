package com.marwaeltayeb.movietrailer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.MovieAdapter;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.network.MovieViewModel;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up recyclerView
        RecyclerView recyclerView = findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        recyclerView.setHasFixedSize(true);

        // Get movieViewModel
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // Create the Adapter
        final MovieAdapter adapter = new MovieAdapter(this);

        // Observe the moviePagedList from ViewModel
        movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
            @Override
            public void onChanged(@Nullable PagedList<Movie> movies) {
                // In case of any changes, submitting the movies to adapter
                adapter.submitList(movies);
            }
        });

        // Set the adapter
        recyclerView.setAdapter(adapter);

    }
}
