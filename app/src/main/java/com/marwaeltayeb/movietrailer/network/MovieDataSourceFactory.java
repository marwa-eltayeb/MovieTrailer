package com.marwaeltayeb.movietrailer.network;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.paging.DataSource;
import androidx.paging.PageKeyedDataSource;

import com.marwaeltayeb.movietrailer.models.Movie;

public class MovieDataSourceFactory extends DataSource.Factory<Integer, Movie> {

    // Creating the mutable live database
    private final MutableLiveData<PageKeyedDataSource<Integer, Movie>> movieLiveDataSource = new MutableLiveData<>();

    public static MovieDataSource movieDataSource;

    private final MovieService movieService;

    public MovieDataSourceFactory(MovieService movieService){
        this.movieService = movieService;
    }

    @NonNull
    @Override
    public DataSource<Integer, Movie> create() {
        // Getting our Data source object
        movieDataSource = new MovieDataSource(movieService);

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
