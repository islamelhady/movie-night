package com.elhady.fav_movie.db;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteMovie.class}, version = 1,exportSchema = false)
public abstract class MovieDatabase extends RoomDatabase {

    public abstract FavoriteDao favoriteDao();

}

