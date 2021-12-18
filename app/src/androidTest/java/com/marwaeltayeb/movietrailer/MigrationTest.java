package com.marwaeltayeb.movietrailer;

import androidx.room.testing.MigrationTestHelper;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import com.marwaeltayeb.movietrailer.data.local.MovieDatabase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import static com.marwaeltayeb.movietrailer.data.local.MovieDatabase.MIGRATION_1_2;

@RunWith(AndroidJUnit4.class)
public class MigrationTest {

    private static final String TEST_DB = "migration-test";

    @Rule
    public MigrationTestHelper helper;

    public MigrationTest() {
        helper = new MigrationTestHelper(InstrumentationRegistry.getInstrumentation(),
                MovieDatabase.class.getCanonicalName(),
                new FrameworkSQLiteOpenHelperFactory());
    }

    @Test
    public void migrate1To2() throws IOException {
        // Create the database with version 1
        SupportSQLiteDatabase db = helper.createDatabase(TEST_DB, 1);

        // db has schema version 1. insert some data using SQL queries.
        // You cannot use DAO classes because they expect the latest schema.
        db.execSQL("INSERT INTO movie_table VALUES (1,1, 'Maze Runner','poster','3.4', 'description', '23-3-2020' , 'en', 'horror') ");

        // Prepare for the next version
        db.close();

        // Re-open the database with version 2 and provide MIGRATION_1_2 as the migration process.
        helper.runMigrationsAndValidate(TEST_DB, 2, true, MIGRATION_1_2);

        // MigrationTestHelper automatically verifies the schema changes
    }
}
