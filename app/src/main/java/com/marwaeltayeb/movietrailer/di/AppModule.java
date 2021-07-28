package com.marwaeltayeb.movietrailer.di;

import android.content.Context;

import androidx.room.Room;

import com.marwaeltayeb.movietrailer.database.MovieRepository;
import com.marwaeltayeb.movietrailer.database.MovieRoomDatabase;
import com.marwaeltayeb.movietrailer.network.MovieService;
import com.marwaeltayeb.movietrailer.repositories.ReviewRepository;
import com.marwaeltayeb.movietrailer.repositories.SearchRepository;
import com.marwaeltayeb.movietrailer.repositories.TrailerRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.marwaeltayeb.movietrailer.database.MovieRoomDatabase.MIGRATION_1_2;
import static com.marwaeltayeb.movietrailer.utils.Constant.BASE_URL;
import static com.marwaeltayeb.movietrailer.utils.Constant.DATABASE_NAME;

@Module
@InstallIn(SingletonComponent.class)
abstract class AppModule {


    @Provides
    @Singleton
    static Retrofit provideRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    @Singleton
    @Provides
    static MovieService provideMovieService(Retrofit retrofit){
        return retrofit.create(MovieService.class);
    }

    @Singleton
    @Provides
    static TrailerRepository provideTrailerRepository(MovieService movieService){
        return new TrailerRepository(movieService);
    }

    @Singleton
    @Provides
    static ReviewRepository provideReviewRepository(MovieService movieService){
        return new ReviewRepository(movieService);
    }

    @Singleton
    @Provides
    static SearchRepository provideSearchRepository(MovieService movieService){
        return new SearchRepository(movieService);
    }

    @Singleton
    @Provides
    static MovieRepository provideMovieRepository(MovieRoomDatabase db){
        return new MovieRepository(db.movieDao());
    }

    @Singleton
    @Provides
    static MovieRoomDatabase provideDataBase(@ApplicationContext Context context) {
        return Room.databaseBuilder(
                context,
                MovieRoomDatabase.class,
                DATABASE_NAME)
                .addMigrations(MIGRATION_1_2)
                .build();
    }
}
