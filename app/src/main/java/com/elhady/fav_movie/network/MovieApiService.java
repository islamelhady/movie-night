package com.elhady.fav_movie.network;

import com.elhady.fav_movie.model.MovieResponse;

import java.util.HashMap;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface MovieApiService {

    @GET("movie/now_playing")
    Observable<MovieResponse> getCurrentlyShowing(@QueryMap HashMap<String,String> queries);

    @GET("movie/popular")
    Observable<MovieResponse> getPopular(@QueryMap HashMap<String,String> queries);

    @GET("movie/upcoming")
    Observable<MovieResponse> getUpcoming(@QueryMap HashMap<String,String> queries);

    @GET("movie/top_rated")
    Observable<MovieResponse> getTopRated(@QueryMap HashMap<String,String> queries);
}
