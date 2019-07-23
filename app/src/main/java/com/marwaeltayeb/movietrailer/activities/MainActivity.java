package com.marwaeltayeb.movietrailer.activities;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.MovieAdapter;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.network.MovieViewModel;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler {

    PagedList<Movie> moviesList;
    RecyclerView recyclerView;
    MovieAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set up recyclerView
        recyclerView = findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        recyclerView.setHasFixedSize(true);

        // Get movieViewModel
        MovieViewModel movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // Create the Adapter
        adapter = new MovieAdapter(this, this);


        if (isNetworkConnected()) {
            // Observe the moviePagedList from ViewModel
            movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(@Nullable PagedList<Movie> movies) {
                    // In case of any changes, submitting the movies to adapter
                    adapter.submitList(movies);
                    moviesList = adapter.getCurrentList();
                }
            });
        }

        // Set the adapter
        recyclerView.setAdapter(adapter);
    }

    /**
     * Initialize the contents of the Activity's options menu.
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        MenuItem searchViewItem = menu.findItem(R.id.search);

        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_for_movies));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                if (isNetworkConnected()) {

                    if (moviesList.size() == 0) {
                        getNoResult();
                    }

                    /*
                    RetrofitClient.getInstance()
                            .getMovieService().searchForMovies(query, API_KEY)
                            .enqueue(new Callback<MovieApiResponse>() {
                                @Override
                                public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                                    if (response.body() != null) {
                                        List<Movie> movieList = new ArrayList<>();
                                        MovieAdapter MovieAdapter = new MovieAdapter(getApplicationContext(), movieList);
                                        recyclerView.setAdapter(MovieAdapter);
                                        movieList = response.body().getMovies();
                                        MovieAdapter.setMovieList(movieList);
                                        Toast.makeText(getApplicationContext(), movieList.size() + " Movies", Toast.LENGTH_SHORT).show();
                                    }
                                }

                                @Override
                                public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                                    Toast.makeText(getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                                }
                            });
                    */
                    Toast.makeText(MainActivity.this, moviesList.size() + "", Toast.LENGTH_SHORT).show();
                    //Log.d("Movies List", moviesList.size() + "");
                }

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }


    /**
     * Setup the specific action that occurs when any of the items in the Options Menu are selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            // Display the SettingActivity
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void getNoResult() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        builder.setMessage(getResources().getString(R.string.no_match));

        // Create and show the AlertDialog
        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                alertDialog.dismiss();
                timer.cancel();
            }
        }, 2000);
    }

    private boolean isNetworkConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            return false;
        }
    }

    @Override
    public void onClick(String titleOfMovie) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        //intent.putExtra(Constant.TITLE, titleOfMovies);
        startActivity(intent);
    }
}
