package com.elhady.filme.interfaces;

import com.elhady.filme.model.Genre;

import java.util.List;

public interface OnGetGenresCallback {
    void onSuccess(List<Genre> genres);

    void onError();
}
