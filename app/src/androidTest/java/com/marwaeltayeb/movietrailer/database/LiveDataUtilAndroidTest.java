package com.marwaeltayeb.movietrailer.database;

import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;

import com.marwaeltayeb.movietrailer.data.model.Movie;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@VisibleForTesting(otherwise = VisibleForTesting.NONE)
class LiveDataUtilAndroidTest {


    public static List<Movie> getOrAwaitValue(Long time, TimeUnit timeUnit ,final LiveData<List<Movie>> liveData){

        final List<Movie> data = new ArrayList<>();
        final CountDownLatch latch = new CountDownLatch(1);

        Observer<List<Movie>> observer = new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> o) {
                data.addAll(o);
                latch.countDown();
                liveData.removeObserver(this);
            }
        };

        liveData.observeForever(observer);

        try {
            // Don't wait indefinitely if the LiveData is not set.
            if (!latch.await(time, timeUnit)) {
                throw new TimeoutException("LiveData value was never set.");
            }
        } catch (InterruptedException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            liveData.removeObserver(observer);
        }

        return data;
    }
}

