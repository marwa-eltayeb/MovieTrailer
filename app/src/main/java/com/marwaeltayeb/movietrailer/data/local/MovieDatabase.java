package com.marwaeltayeb.movietrailer.data.local;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.marwaeltayeb.movietrailer.data.model.GenresTypeConverter;
import com.marwaeltayeb.movietrailer.data.model.Movie;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Movie.class}, version = 2)
@TypeConverters({GenresTypeConverter.class})
public abstract class MovieDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = Runtime.getRuntime().availableProcessors();
    public static final ExecutorService databaseWriteExecutor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE movie_table "
                    + " ADD COLUMN genres TEXT");
        }
    };

    public abstract MovieDao movieDao();
}

