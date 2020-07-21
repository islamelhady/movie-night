package com.elhady.fav_movie.iterface;

import com.elhady.fav_movie.model.Trailer;

import java.util.List;

public interface OnGetTrailersCallback {
    void onSuccess(List<Trailer> trailers);

    void onError();
}
