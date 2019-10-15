package com.marwaeltayeb.movietrailer.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.marwaeltayeb.movietrailer.models.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie_database";

    private static MovieRoomDatabase sInstance;

    static MovieRoomDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (MovieRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();

}

