package com.elhady.filme.iterface;


import com.elhady.filme.model.Review;

import java.util.List;

public interface OnGetReviewsCallback {
    void onSuccess(List<Review> reviews);

    void onError();
}
