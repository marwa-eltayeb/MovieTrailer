package com.marwaeltayeb.movietrailer.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.marwaeltayeb.movietrailer.models.GenresTypeConverter;
import com.marwaeltayeb.movietrailer.models.Movie;

@Database(entities = {Movie.class}, version = 2)
@TypeConverters({GenresTypeConverter.class})
public abstract class MovieRoomDatabase extends RoomDatabase {

    public static final Migration MIGRATION_1_2 = new Migration(1, 2) {
        @Override
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE movie_table "
                    + " ADD COLUMN genres TEXT");
        }
    };

    public abstract MovieDao movieDao();
}

