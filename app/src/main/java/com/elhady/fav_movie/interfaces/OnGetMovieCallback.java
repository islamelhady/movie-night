package com.elhady.fav_movie.interfaces;

import com.elhady.fav_movie.model.Movie;

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);

    void onError();
}
