package com.elhady.fav_movie.interfaces;

import com.elhady.fav_movie.model.Genre;

import java.util.List;

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}
