package com.elhady.fav_movie.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.elhady.fav_movie.model.Movie;

import java.util.List;

@Dao
public interface MovieDao {
    @Insert
    public void insertMovie(Movie movie);

    @Delete
    void delete(Movie movie);

    @Query("delete from movie_table where id =:movie_id")
    public void deleteMovie(int movie_id);

    @Query("select * from movie_table")
    public LiveData<List<Movie>> getMovies();
}
