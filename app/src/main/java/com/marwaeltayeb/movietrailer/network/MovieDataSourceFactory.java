package com.marwaeltayeb.movietrailer.network;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;
import android.arch.paging.PageKeyedDataSource;

import com.marwaeltayeb.movietrailer.models.Movie;

public class MovieDataSourceFactory extends DataSource.Factory {

    // Creating the mutable live data
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> itemLiveDataSource = new MutableLiveData<>();

    @Override
    public DataSource<Integer, Movie> create() {
        // Getting our Data source object
        MovieDataSource movieDataSource = new MovieDataSource();

        // Posting the Data source to get the values
        itemLiveDataSource.postValue(movieDataSource);

        // Returning the Data source
        return movieDataSource;
    }


    // Getter for Movie live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getItemLiveDataSource() {
        return itemLiveDataSource;
    }
}
