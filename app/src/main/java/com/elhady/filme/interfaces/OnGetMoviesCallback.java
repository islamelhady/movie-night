package com.elhady.filme.interfaces;

import com.elhady.filme.model.Movie;

import java.util.List;

public interface OnGetMoviesCallback {
    //Refactor MoviesRepository.getMovies(…) to accept a page number (Pagination)
    void onSuccess(int page, List<Movie> movies);

    void onError();
}

