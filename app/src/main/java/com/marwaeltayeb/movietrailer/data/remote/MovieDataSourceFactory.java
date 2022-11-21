package com.marwaeltayeb.movietrailer.data.remote;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.movietrailer.data.model.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource = new MutableLiveData<>();

    public static MovieDataSource movieDataSource;

    public MovieDataSourceFactory(MovieService movieService, String sort){
        // Getting our Data source object
        movieDataSource = new MovieDataSource(movieService, sort);
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        // Posting the Data source to get the values
        movieLiveDataSource.postValue(movieDataSource);

        // Returning the Data source
        return movieDataSource;
    }
}
