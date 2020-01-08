package com.marwaeltayeb.movietrailer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.databinding.ActivitySeeAllBinding;
import com.marwaeltayeb.movietrailer.models.Trailer;
import com.marwaeltayeb.movietrailer.network.TrailerViewModel;

import java.util.List;

public class SeeAllActivity extends AppCompatActivity {

    ActivitySeeAllBinding binding;
    private TrailerAdapter trailerAdapter;
    private RecyclerView trailersRecyclerView;
    private TrailerViewModel trailerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_all);

        trailerViewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);

        setupRecyclerViews();

        getTrailers();
    }

    private void setupRecyclerViews() {
        // Trailers
        trailersRecyclerView = findViewById(R.id.listOfTrailers);
        trailersRecyclerView.setHasFixedSize(true);
        trailersRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
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

}
