package com.marwaeltayeb.movietrailer.activities;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.TrailerAdapter;
import com.marwaeltayeb.movietrailer.databinding.ActivitySeeAllBinding;
import com.marwaeltayeb.movietrailer.viewmodels.TrailerViewModel;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SeeAllActivity extends AppCompatActivity {

    private ActivitySeeAllBinding binding;
    private TrailerAdapter trailerAdapter;
    private TrailerViewModel trailerViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_see_all);

        trailerViewModel = new ViewModelProvider(this).get(TrailerViewModel.class);

        setupViews();

        getTrailers();
    }

    private void setupViews() {
        binding.listOfTrailers.setHasFixedSize(true);
        binding.listOfTrailers.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        trailerAdapter = new TrailerAdapter(this);
        binding.listOfTrailers.setAdapter(trailerAdapter);
    }

    public void getTrailers() {
        trailerViewModel.getAllTrailers().observe(this, trailers -> {
            trailerAdapter.submitList(trailers);

            if (trailers != null && trailers.isEmpty()) {
                binding.listOfTrailers.setVisibility(View.GONE);
                binding.noTrailers.setVisibility(View.VISIBLE);
            }
        });
    }
}
