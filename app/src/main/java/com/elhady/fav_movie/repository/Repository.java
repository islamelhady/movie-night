package com.elhady.fav_movie.repository;

import com.elhady.fav_movie.model.Actor;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.model.MovieResponse;
import com.elhady.fav_movie.network.MovieApiService;
import com.google.gson.JsonObject;

import java.util.HashMap;

import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    public static final String TAG = "Repository";

    MovieApiService movieApiService;

    @Inject
    public Repository(MovieApiService movieApiService) {
        this.movieApiService = movieApiService;
    }

    public Observable<MovieResponse> getCurrentlyShowing(HashMap<String, String> map){
        return movieApiService.getCurrentlyShowing(map);
    }

    public Observable<MovieResponse>  getPopular(HashMap<String, String> map){
        return movieApiService.getPopular(map);
    }

    public Observable<MovieResponse>  getTopRated(HashMap<String, String> map){
        return movieApiService.getTopRated(map);
    }

    public Observable<MovieResponse>  getUpcoming(HashMap<String, String> map){
        return movieApiService.getUpcoming(map);
    }

    public Observable<Movie>  getMovieDetails(int movieId, HashMap<String, String> map){
        return movieApiService.getMovieDetails(movieId, map);
    }

    public Observable<JsonObject>  getCast(int movieId, HashMap<String, String> map){
        return movieApiService.getCast(movieId,map);
    }

    public Observable<Actor>  getActorDetails(int personId,HashMap<String,String> map){
        return movieApiService.getActorDetails(personId,map);
    }


}
