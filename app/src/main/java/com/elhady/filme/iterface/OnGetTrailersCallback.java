package com.elhady.filme.iterface;

import com.elhady.filme.model.Trailer;

import java.util.List;

public interface OnGetTrailersCallback {
    void onSuccess(List<Trailer> trailers);

    void onError();
}
