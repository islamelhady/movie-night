package com.elhady.fav_movie.iterface;

import com.elhady.fav_movie.model.Movie;

import java.util.List;

public interface OnGetMoviesCallback {
    void onSuccess(List<Movie> movies);

    void onError();
}

