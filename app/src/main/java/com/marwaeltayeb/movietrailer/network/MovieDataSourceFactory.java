package com.marwaeltayeb.movietrailer.network;

import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.movietrailer.models.Movie;

public class MovieDataSourceFactory extends DataSource.Factory {

    // Creating the mutable live database
    private MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource = new MutableLiveData<>();

    static MovieDataSource movieDataSource;

    @Override
    public DataSource<Integer, Movie> create() {
        // Getting our Data source object
        movieDataSource = new MovieDataSource();

        // Posting the Data source to get the values
        movieLiveDataSource.postValue(movieDataSource);

        // Returning the Data source
        return movieDataSource;
    }


    // Getter for Movie live DataSource
    public MutableLiveData<PageKeyedDataSource<Integer, Movie>> getMovieLiveDataSource() {
        return movieLiveDataSource;
    }
}
