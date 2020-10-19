package com.elhady.movie_night.di;

import android.app.Application;

import androidx.room.Room;

import com.elhady.movie_night.Utils.Constants;
import com.elhady.movie_night.db.FavoriteDao;
import com.elhady.movie_night.db.MovieDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public class DatabaseModule {


    @Provides
    @Singleton
    MovieDatabase provideMovieDatabase(Application application){
        return Room.databaseBuilder(application,MovieDatabase.class, Constants.DataBaseName)
                .fallbackToDestructiveMigration()
                .allowMainThreadQueries()
                .build();
    }

    @Provides
    @Singleton
    FavoriteDao provideFavoriteDao(MovieDatabase movieDatabase){
        return movieDatabase.favoriteDao();
    }
}
