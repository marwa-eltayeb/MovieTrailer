package com.marwaeltayeb.movietrailer.database;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.lifecycle.LiveData;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.SmallTest;

import com.marwaeltayeb.movietrailer.data.local.MovieDao;
import com.marwaeltayeb.movietrailer.data.local.MovieDatabase;
import com.marwaeltayeb.movietrailer.data.model.Movie;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(AndroidJUnit4.class)
@SmallTest
public class MovieDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MovieDatabase movieDatabase;
    private MovieDao movieDao;

    @Before
    public void setUp() {
        movieDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(), MovieDatabase.class)
                .allowMainThreadQueries()
                .build();

        movieDao = movieDatabase.movieDao();
    }

    @After
    public void tearDown() {
        movieDatabase.close();
    }

    @Test
    public void insertMovie() {
        Movie movie = new Movie("145", "Maze Runner", "3.4", "desc", "23-3-2020", "en", "", "", new ArrayList<Integer>());
        movieDao.insert(movie);

        LiveData<List<Movie>> liveDataAllMovies = movieDao.getAllMovies();
        List<Movie> movieList = LiveDataUtilAndroidTest.getOrAwaitValue(2L, TimeUnit.SECONDS,liveDataAllMovies);
        assertTrue(movieList.contains(movie));
    }

    @Test
    public void deleteMovie() {
        Movie movie = new Movie("145", "Maze Runner", "3.4", "desc", "23-3-2020", "en", "", "", new ArrayList<Integer>());
        movieDao.insert(movie);
        movieDao.deleteById(Integer.parseInt(movie.movieId));

        LiveData<List<Movie>> liveDataAllMovies = movieDao.getAllMovies();
        List<Movie> movieList = LiveDataUtilAndroidTest.getOrAwaitValue(2L, TimeUnit.SECONDS,liveDataAllMovies);
        assertFalse(movieList.contains(movie));
    }

    @Test
    public void deleteAllMovies() {
        Movie movie1 = new Movie("145", "Maze Runner", "3.4", "desc", "23-3-2020", "en", "", "", new ArrayList<Integer>());
        Movie movie2 = new Movie("145", "Maze Runner", "3.4", "desc", "23-3-2020", "en", "", "", new ArrayList<Integer>());
        Movie movie3 = new Movie("145", "Maze Runner", "3.4", "desc", "23-3-2020", "en", "", "", new ArrayList<Integer>());

        movieDao.insert(movie1);
        movieDao.insert(movie2);
        movieDao.insert(movie3);
        movieDao.deleteAll();

        LiveData<List<Movie>> liveDataAllMovies = movieDao.getAllMovies();
        List<Movie> movieList = LiveDataUtilAndroidTest.getOrAwaitValue(2L, TimeUnit.SECONDS,liveDataAllMovies);
        assertEquals(movieList.size(), 0);
    }
}




