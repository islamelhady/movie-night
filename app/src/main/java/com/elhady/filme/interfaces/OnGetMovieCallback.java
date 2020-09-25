package com.elhady.filme.interfaces;

import com.elhady.filme.model.Movie;

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);

    void onError();
}
