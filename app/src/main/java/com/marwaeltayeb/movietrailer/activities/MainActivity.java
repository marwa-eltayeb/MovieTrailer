package com.marwaeltayeb.movietrailer.activities;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.MovieAdapter;
import com.marwaeltayeb.movietrailer.adapters.SearchAdapter;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.MovieApiResponse;
import com.marwaeltayeb.movietrailer.network.MovieViewModel;
import com.marwaeltayeb.movietrailer.network.RetrofitClient;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.Util.Constant.BACKDROP_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.DESCRIPTION_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.ID_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.LANGUAGE_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.RATING_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.Util.Constant.RELEASE_DATE;
import static com.marwaeltayeb.movietrailer.Util.Constant.TITLE_OF_MOVIE;
import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener{

    public static boolean sortValueHasChanged = false;
    public static Dialog progressDialog;
    public static Context contextOfApplication;

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    SearchAdapter searchAdapter;
    List<Movie> movieList;
    MovieViewModel movieViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        progressDialog = createProgressDialog(MainActivity.this);

        // Set up recyclerView
        recyclerView = findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        recyclerView.setHasFixedSize(true);

        // Get movieViewModel
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        // Create the Adapter
        movieAdapter = new MovieAdapter(this, this);

        // Road Movies
        //loadMovies();
        if (isNetworkConnected()) {
            // Observe the moviePagedList from ViewModel
            movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(@Nullable PagedList<Movie> movies) {
                    // In case of any changes, submitting the movies to adapter
                    movieAdapter.submitList(movies);
                }
            });
        }

        // Set the adapter
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();

        // Register the listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);


    }


    /**
     * Get Context.
     */
    public static Context getContextOfApplication(){
        return contextOfApplication;
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
                    Search(query);
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (isNetworkConnected()) {
                    Search(newText);
                }
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Search for movies.
     */
    private String Search(String query){
        RetrofitClient.getInstance()
                .getMovieService().searchForMovies(query, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        if (response.body() != null) {
                            movieList = response.body().getMovies();
                            if(movieList.isEmpty()){
                                getNoResult();
                            }
                            Toast.makeText(getApplicationContext(), movieList.size() + " Movies", Toast.LENGTH_SHORT).show();
                            searchAdapter = new SearchAdapter(getApplicationContext(),movieList,new SearchAdapter.SearchAdapterOnClickHandler(){
                                @Override
                                public void onClick(String titleOfMovie, String ratingOfMovie) {
                                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                                    startActivity(intent);
                                }
                            });
                        }
                        recyclerView.setAdapter(searchAdapter);
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                    }
                });
        return query;
    }

    /**
     * Setup the specific action that occurs when any of the items in the Options Menu are selected.
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.setting) {
            // Display the Setting Activity
            Intent settingsIntent = new Intent(this, SettingActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Get no result for search.
     */
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

    /**
     * Check if there is a network.
     */
    private boolean isNetworkConnected() {
        // Get a reference to the ConnectivityManager to check state of network connectivity
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // Get details on the currently active default data network
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            progressDialog.dismiss();
            //Toast.makeText(this, "No Internet Connection", Toast.LENGTH_LONG).show();
            Snackbar.make(findViewById(android.R.id.content), "No Internet Connection", Snackbar.LENGTH_INDEFINITE)
                    .setAction("CLOSE", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorAccent))
                    .show();
            return false;
        }
    }

    /**
     * Click on the movie for details.
     */
    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        intent.putExtra(ID_OF_MOVIE, movie.getMovieId());
        intent.putExtra(TITLE_OF_MOVIE, movie.getMovieTitle());
        intent.putExtra(RATING_OF_MOVIE, movie.getMovieVote());
        intent.putExtra(BACKDROP_OF_MOVIE, movie.getMovieBackdrop());
        intent.putExtra(DESCRIPTION_OF_MOVIE, movie.getMovieDescription());
        intent.putExtra(RELEASE_DATE, movie.getMovieReleaseDate());
        intent.putExtra(LANGUAGE_OF_MOVIE, movie.getMovieLanguage());
        startActivity(intent);
    }

    /**
     * Create Progress Dialog.
     */
    public static Dialog createProgressDialog(Context context) {
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        return progressDialog;
    }

    /**
     * Listen to any changes
     */
    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sort_key))) {
            loadMovies();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Unregister MainActivity as an OnPreferenceChangedListener to avoid any memory leaks.
        PreferenceManager.getDefaultSharedPreferences(this)
                .unregisterOnSharedPreferenceChangeListener(this);
    }

    /**
     * Load movies
     */
    private void loadMovies(){
        if (isNetworkConnected()) {
            // Observe the moviePagedList from ViewModel
            movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(@Nullable PagedList<Movie> movies) {
                    // In case of any changes, submitting the movies to adapter
                    movieAdapter.submitList(movies);
                    if (movies != null) {
                        Toast.makeText(getApplicationContext(), movies.get(0) + "", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

        // Set the adapter
        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }



}