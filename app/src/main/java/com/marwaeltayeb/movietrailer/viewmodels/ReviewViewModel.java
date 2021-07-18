package com.marwaeltayeb.movietrailer.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.marwaeltayeb.movietrailer.models.Review;
import com.marwaeltayeb.movietrailer.repositories.ReviewRepository;

import java.util.List;

public class ReviewViewModel extends ViewModel {

    private final ReviewRepository reviewRepository;

    public ReviewViewModel() {
        reviewRepository = new ReviewRepository();
    }

    public LiveData<List<Review>> getAllReviews() {
        return reviewRepository.getMutableLiveData();
    }
}
