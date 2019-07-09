package com.marwaeltayeb.movietrailer.network;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PageKeyedDataSource;
import android.arch.paging.PagedList;

import com.marwaeltayeb.movietrailer.models.Movie;

/**
 * Created by Marwa on 7/10/2019.
 */

public class MovieViewModel extends ViewModel {

    // Create liveData for PagedList and PagedKeyedDataSource
    public LiveData<PagedList<Movie>> itemPagedList;
    private LiveData<PageKeyedDataSource<Integer, Movie>> liveDataSource;

    // Constructor
    public MovieViewModel() {

        // Get our data source factory
        MovieDataSourceFactory itemDataSourceFactory = new MovieDataSourceFactory();

        // Get the live data source from data source factory
        liveDataSource = itemDataSourceFactory.getItemLiveDataSource();

        // Get PagedList configuration
        PagedList.Config pagedListConfig =
                (new PagedList.Config.Builder())
                        .setEnablePlaceholders(false)
                        .setPageSize(MovieDataSource.PAGE_SIZE).build();

        // Build the paged list
        itemPagedList = (new LivePagedListBuilder(itemDataSourceFactory, pagedListConfig)).build();
    }
}
