package com.elhady.fav_movie.iterface;

import com.elhady.fav_movie.model.GenresResponse;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.model.MoviesResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TMDbApi {
    @GET("movie/popular")
    Call<MoviesResponse> getPopularMovies(@Query("api_key") String apiKey,
                                          @Query("language") String language,
                                          @Query("page") int page);

    @GET("genre/movie/list")
    Call<GenresResponse> getGenres(@Query("api_key") String apiKey,
                                   @Query("language") String language);


    @GET("movie/top_rated")
    Call<MoviesResponse> getTopRatedMovies(@Query("api_key") String apiKey,
                                           @Query("language") String language,
                                           @Query("page") int page
    );

    @GET("movie/upcoming")
    Call<MoviesResponse> getUpcomingMovies(@Query("api_key") String apiKey,
                                           @Query("language") String language,
                                           @Query("page") int page
    );

    @GET("movie/{movie_id}")
    Call<Movie> getMovie(@Path("movie_id") int id,
                         @Query("api_key") String apiKEy,
                         @Query("language") String language);


}
