package com.marwaeltayeb.movietrailer.network;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.annotation.NonNull;

import com.marwaeltayeb.movietrailer.models.Review;

import java.util.List;

public class ReviewViewModel extends AndroidViewModel {

    private ReviewRepository reviewRepository;

    public ReviewViewModel(@NonNull Application application) {
        super(application);
        reviewRepository = new ReviewRepository(application);
    }

    public LiveData<List<Review>> getAllReviews() {
        return reviewRepository.getMutableLiveData();
    }
}
