package com.elhady.fav_movie.di;

import android.app.Application;

import androidx.room.Room;

import com.elhady.fav_movie.Utils.Constants;
import com.elhady.fav_movie.db.FavoriteDao;
import com.elhady.fav_movie.db.MovieDatabase;

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
