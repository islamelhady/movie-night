package com.elhady.fav_movie.iterface;

import com.elhady.fav_movie.model.Movie;

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);

    void onError();
}
