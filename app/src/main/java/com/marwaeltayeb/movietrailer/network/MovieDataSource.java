package com.marwaeltayeb.movietrailer.network;

import android.arch.paging.PageKeyedDataSource;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.activities.MainActivity;
import com.marwaeltayeb.movietrailer.models.Movie;
import com.marwaeltayeb.movietrailer.models.MovieApiResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.marwaeltayeb.movietrailer.network.MovieService.API_KEY;

public class MovieDataSource extends PageKeyedDataSource<Integer, Movie> {


    private static final int FIRST_PAGE = 1;

    static final int PAGE_SIZE = 20;

    private Context applicationContext = MainActivity.getContextOfApplication();
    private SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(applicationContext);
    private String sort = sharedPreferences.getString(applicationContext.getString(R.string.sort_key), applicationContext.getString(R.string.popular_value));

    @Override
    public void loadInitial(@NonNull final LoadInitialParams<Integer> params, @NonNull final LoadInitialCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getMovieService().getMovies(sort,FIRST_PAGE, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        MainActivity.progressDialog.dismiss();
                        Toast.makeText(applicationContext, sort + "", Toast.LENGTH_SHORT).show();
                        if (response.body() != null) {
                            // Fetch data and pass the result null for the previous page
                            callback.onResult(response.body().getMovies(), null, FIRST_PAGE + 1);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        //Toast.makeText(Context.getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                        MainActivity.progressDialog.dismiss();
                    }
                });
    }

    @Override
    public void loadBefore(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getMovieService().getMovies(sort,params.key, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        /*
                         If the current page is greater than one,
                         we are decrementing the page number
                         else there is no previous page.
                        */
                        Integer adjacentKey = (params.key > 1) ? params.key - 1 : null;
                        if (response.body() != null) {

                            // Passing the loaded data and the previous page key
                            callback.onResult(response.body().getMovies(), adjacentKey);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        //Toast.makeText(Context.getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void loadAfter(@NonNull final LoadParams<Integer> params, @NonNull final LoadCallback<Integer, Movie> callback) {
        RetrofitClient.getInstance()
                .getMovieService().getMovies(sort,params.key, API_KEY)
                .enqueue(new Callback<MovieApiResponse>() {
                    @Override
                    public void onResponse(Call<MovieApiResponse> call, Response<MovieApiResponse> response) {
                        if (response.body() != null) {
                            // If the response has next page, increment the next page number
                            Integer key = response.body().getMovies().size() == PAGE_SIZE ? params.key + 1 : null;

                            // Passing the loaded data and next page value
                            callback.onResult(response.body().getMovies(), key);
                        }
                    }

                    @Override
                    public void onFailure(Call<MovieApiResponse> call, Throwable t) {
                        //Toast.makeText(Context.getApplicationContext(), "No Movies", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}

