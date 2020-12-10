package com.marwaeltayeb.movietrailer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.marwaeltayeb.movietrailer.models.GenresTypeConverter;
import com.marwaeltayeb.movietrailer.models.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
@TypeConverters({GenresTypeConverter.class})
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

