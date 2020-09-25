package com.elhady.filme.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.elhady.filme.model.Movie;

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