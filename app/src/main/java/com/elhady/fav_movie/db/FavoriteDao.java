package com.elhady.fav_movie.db;

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
    void clearWishList();

    @Query("SELECT * FROM favorite_table")
    LiveData<List<FavoriteMovie>> getWishList();

    @Query("SELECT * FROM favorite_table WHERE id = :movieId ")
    FavoriteMovie getWishListMovie(int movieId);
}
