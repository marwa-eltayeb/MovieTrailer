package com.marwaeltayeb.movietrailer.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.marwaeltayeb.movietrailer.models.GenresTypeConverter;
import com.marwaeltayeb.movietrailer.models.Movie;

@Database(entities = {Movie.class}, version = 2, exportSchema = false)
@TypeConverters({GenresTypeConverter.class})
public abstract class MovieRoomDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "movie_database";

    private static MovieRoomDatabase sInstance;

    static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE movie_table "
                    + " ADD COLUMN genres TEXT");
        }
    };


    static MovieRoomDatabase getDatabase(final Context context) {
        if (sInstance == null) {
            synchronized (MovieRoomDatabase.class) {
                if (sInstance == null) {
                    sInstance = Room.databaseBuilder(context.getApplicationContext(),
                            MovieRoomDatabase.class, DATABASE_NAME)
                            .addMigrations(MIGRATION_1_2)
                            .build();
                }
            }
        }
        return sInstance;
    }

    public abstract MovieDao movieDao();

}

