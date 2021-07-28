package com.marwaeltayeb.movietrailer.database;

import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.marwaeltayeb.movietrailer.models.Movie;

import java.util.List;

public class MovieRepository {

    private final MovieDao mMovieDao;
    private final LiveData<List<Movie>> mAllMovies;

    public MovieRepository(MovieDao mMovieDao) {
        this.mMovieDao = mMovieDao;
        mAllMovies = mMovieDao.getAllMovies();
    }

    LiveData<List<Movie>> getAllMovies() {
        return mAllMovies;
    }

    public void insert(Movie movie) {
        new InsertAsyncTask(mMovieDao).execute(movie);
    }

    private static class InsertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncTaskDao;

        InsertAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    public void deleteById(int movieId) {
        new DeleteByIdAsyncTask(mMovieDao).execute(movieId);
    }

    private static class DeleteByIdAsyncTask extends AsyncTask<Integer, Void, Void> {

        private MovieDao mAsyncTaskDao;

        DeleteByIdAsyncTask(MovieDao dao) {
            mAsyncTaskDao = dao;
        }

        @Override
        protected Void doInBackground(final Integer... params) {
            mAsyncTaskDao.deleteById(params[0]);
            return null;
        }
    }

    public void deleteAll(){
        new DeleteAllAsyncTask(mMovieDao).execute();
    }

    private static class DeleteAllAsyncTask extends AsyncTask<Void,Void,Void>{

        private MovieDao mAsyncTaskDao;

        private DeleteAllAsyncTask(MovieDao deo){
            this.mAsyncTaskDao = deo;
        }

        @Override
        protected Void doInBackground(Void...v) {
            mAsyncTaskDao.deleteAll();
            return null;
        }
    }
}
