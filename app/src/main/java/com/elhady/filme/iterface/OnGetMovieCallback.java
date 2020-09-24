package com.elhady.filme.iterface;

import com.elhady.filme.model.Movie;

public interface OnGetMovieCallback {
    void onSuccess(Movie movie);

    void onError();
}
