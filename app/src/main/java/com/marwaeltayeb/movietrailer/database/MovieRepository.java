package com.marwaeltayeb.movietrailer.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import java.util.List;

public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<MovieEntry>> mAllMovies;

    MovieRepository(Application application) {
        MovieRoomDatabase db = MovieRoomDatabase.getDatabase(application);
        mMovieDao = db.movieDao();
        mAllMovies = mMovieDao.getAllMovies();
    }

    LiveData<List<MovieEntry>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(MovieEntry movieEntry) {
        new insertAsyncTask(mMovieDao).execute(movieEntry);
    }

    private static class insertAsyncTask extends AsyncTask<MovieEntry, Void, Void> {

        private MovieDao mAsyncTaskDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final MovieEntry... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }
}
