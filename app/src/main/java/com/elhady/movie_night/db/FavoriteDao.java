package com.elhady.movie_night.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface FavoriteDao {

    @Insert
    void insert(FavoriteMovie favoriteMovie);

    @Query("DELETE From favorite_table WHERE id = :movieId")
    void delete(int movieId);

    @Query("DELETE FROM favorite_table")
    void clearFavoriteList();

    @Query("SELECT * FROM favorite_table")
    LiveData<List<FavoriteMovie>> getFavoriteList();

    @Query("SELECT * FROM favorite_table WHERE id = :movieId ")
    FavoriteMovie getFavoriteListMovie(int movieId);
}
