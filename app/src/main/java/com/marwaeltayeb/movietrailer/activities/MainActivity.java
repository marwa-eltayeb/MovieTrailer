package com.marwaeltayeb.movietrailer.activities;

import android.app.Dialog;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.adapters.MovieAdapter;
import com.marwaeltayeb.movietrailer.adapters.SearchAdapter;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.MovieApiResponse;
import com.marwaeltayeb.movietrailer.network.MovieViewModel;
import com.marwaeltayeb.movietrailer.network.RetrofitClient;
import com.marwaeltayeb.movietrailer.receiver.NetworkChangeReceiver;
import com.marwaeltayeb.movietrailer.utils.OnNetworkListener;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;
import static com.marwaeltayeb.movietrailer.utils.Constant.MOVIE;

public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieAdapterOnClickHandler,
        SharedPreferences.OnSharedPreferenceChangeListener, OnNetworkListener {

    public static Dialog progressDialog;
    public static Context contextOfApplication;

    RecyclerView recyclerView;
    MovieAdapter movieAdapter;
    SearchAdapter searchAdapter;
    List<Movie> searchedMovieList;
    MovieViewModel movieViewModel;
    TextView no_movies_found;

    private NetworkChangeReceiver mNetworkReceiver;
    private Snackbar snack;

    public static String sort;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        contextOfApplication = getApplicationContext();

        progressDialog = createProgressDialog(MainActivity.this);

        snack = Snackbar.make(findViewById(android.R.id.content), getResources().getString(R.string.no_internet_connection), Snackbar.LENGTH_INDEFINITE);

        // Get movieViewModel
        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel.class);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sort = sharedPreferences.getString(getString(R.string.sort_key), getString(R.string.popular_value));

        setViews();

        loadMovies();

        // Register the listener
        PreferenceManager.getDefaultSharedPreferences(this)
                .registerOnSharedPreferenceChangeListener(this);

        mNetworkReceiver = new NetworkChangeReceiver();
        mNetworkReceiver.setOnNetworkListener(this);


    }

    private void setViews() {
        recyclerView = findViewById(R.id.movie_list);
        recyclerView.setLayoutManager(new GridLayoutManager(this, (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) ? 3 : 4));
        recyclerView.setHasFixedSize(true);
        no_movies_found = findViewById(R.id.no_movies_found);

        movieAdapter = new MovieAdapter(this, this);
    }

    public static Context getContextOfApplication() {
        return contextOfApplication;
    }

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

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                if (searchedMovieList != null) {
                    searchAdapter.clear();
                    loadMovies();
                }
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
        switch (item.getItemId()) {
            case R.id.setting:
                Intent settingsIntent = new Intent(this, SettingActivity.class);
                startActivity(settingsIntent);
                return true;

            case R.id.favorites:
                Intent favoriteIntent = new Intent(this, FavoriteActivity.class);
                startActivity(favoriteIntent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Search for movies.
     */
    private String Search(String query) {
        RetrofitClient.getInstance()
                .getMovieService().searchForMovies(query, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        if (response.body() != null) {
                            searchedMovieList = response.body().getMovies();
                            if (searchedMovieList.isEmpty()) {
                                getNoResult();
                            }
                            Log.v("onResponse", searchedMovieList.size() + " Movies");
                            searchAdapter = new SearchAdapter(getApplicationContext(), searchedMovieList, new SearchAdapter.SearchAdapterOnClickHandler() {
                                @Override
                                public void onClick(Movie movie) {
                                    Intent intent = new Intent(MainActivity.this, MovieActivity.class);
                                    // Pass an object of movie class
                                    intent.putExtra(MOVIE, (movie));
                                    startActivity(intent);
                                }
                            });
                        }
                        recyclerView.setAdapter(searchAdapter);
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        Log.v("onFailure", " Failed to get movies");
                    }
                });
        return query;
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


    private boolean isNetworkConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        } else {
            progressDialog.dismiss();
            showSnackBar();
            return false;
        }
    }

    public void showSnackBar() {
        snack.setAction("CLOSE", new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                snack.dismiss();
            }
        });
        snack.setActionTextColor(getResources().getColor(R.color.colorAccent));
        snack.show();
    }

    @Override
    public void onClick(Movie movie) {
        Intent intent = new Intent(MainActivity.this, MovieActivity.class);
        // Pass an object of movie class
        intent.putExtra(MOVIE, (movie));
        startActivity(intent);
    }

    public static Dialog createProgressDialog(Context context) {
        Dialog progressDialog = new Dialog(context);
        progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        progressDialog.setCancelable(false);
        progressDialog.setContentView(R.layout.dialog_layout);
        progressDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDialog.show();
        return progressDialog;
    }

    public static String getSort() {
        return sort;
    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.sort_key))) {
            sort = sharedPreferences.getString(getString(R.string.sort_key), getString(R.string.popular_value));
            movieViewModel.clear();
            loadMovies();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private void loadMovies() {
        if (isNetworkConnected()) {
            // Observe the moviePagedList from ViewModel
            movieViewModel.moviePagedList.observe(this, new Observer<PagedList<Movie>>() {
                @Override
                public void onChanged(@Nullable PagedList<Movie> movies) {
                    // In case of any changes, submitting the movies to adapter
                    movieAdapter.submitList(movies);
                    // when screen is rotated
                    if (movies != null && !movies.isEmpty()) {
                        progressDialog.dismiss();
                    }
                }
            });
        }

        recyclerView.setAdapter(movieAdapter);
        movieAdapter.notifyDataSetChanged();
    }

    private void registerNetworkBroadcastForNougat() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            registerReceiver(mNetworkReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        registerNetworkBroadcastForNougat();
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(mNetworkReceiver);
    }

    @Override
    public void onNetworkConnected() {
        snack.dismiss();
        progressDialog.show();
        loadMovies();
    }

    @Override
    public void onNetworkDisconnected() {
        showSnackBar();
    }
}

