package com.elhady.movie_night.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elhady.movie_night.db.FavoriteMovie;
import com.elhady.movie_night.model.Actor;
import com.elhady.movie_night.model.Cast;
import com.elhady.movie_night.model.Genre;
import com.elhady.movie_night.model.Movie;
import com.elhady.movie_night.model.MovieResponse;
import com.elhady.movie_night.repository.Repository;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.observers.DisposableObserver;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class HomeViewModel extends ViewModel {
    private static final String TAG = "HomeViewModel";

    private Repository repository;
    private MutableLiveData<ArrayList<Movie>> currentMoviesList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> popularMoviesList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> topRatedMoviesList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> upcomingMoviesList = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Movie>> queriesMovies = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Cast>> movieCastList = new MutableLiveData<>();
    private MutableLiveData<Movie> movieDetails = new MutableLiveData<>();
    private MutableLiveData<Actor> actorDetails = new MutableLiveData<>();

    private final CompositeDisposable disposable = new CompositeDisposable();

    @ViewModelInject
    public HomeViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Movie>> getCurrentlyShowingList(){
        return currentMoviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getPopularMoviesList(){
        return popularMoviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getTopRatedMoviesList(){
        return topRatedMoviesList;
    }

    public MutableLiveData<ArrayList<Movie>> getUpcomingMoviesList(){
        return upcomingMoviesList;
    }

    public MutableLiveData<Movie> getMovie() {
        return movieDetails;
    }

    public MutableLiveData<Actor> getActor() {
        return actorDetails;
    }

    public MutableLiveData<ArrayList<Cast>> getMovieCastList() {
        return movieCastList;
    }

    public MutableLiveData<ArrayList<Movie>> getQueriesMovies() {
        return queriesMovies;
    }


    public void getCurrentlyShowingMovies(HashMap<String, String> map){
        disposable.add(repository.getCurrentlyShowing(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<MovieResponse, ArrayList<Movie>>() {
                    @Override
                    public ArrayList<Movie> apply(MovieResponse movieResponse) throws Throwable {
                        return movieResponse.getResults();
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableObserver<ArrayList<Movie>>() {
                    @Override
                    public void onNext(@io.reactivex.rxjava3.annotations.NonNull ArrayList<Movie> movies) {
                        currentMoviesList.setValue(movies);
                    }

                    @Override
                    public void onError(@io.reactivex.rxjava3.annotations.NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                })
        );
    }

    public void getPopularMovies(HashMap<String, String> map){
        disposable.add(repository.getPopular(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->popularMoviesList.setValue(result.getResults()),
                        error-> Log.e(TAG, "getPopularMovies: " + error.getMessage() ))
        );
    }

    public void getTopRatedMovies(HashMap<String, String> map) {
        disposable.add(repository.getTopRated(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> topRatedMoviesList.setValue(result.getResults()),
                        error -> Log.e(TAG, "getTopRated: " + error.getMessage()))
        );
    }

    public void getUpcomingMovies(HashMap<String, String> map) {
        disposable.add(repository.getUpcoming(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> upcomingMoviesList.setValue(result.getResults()),
                        error -> Log.e(TAG, "getUpcoming: " + error.getMessage()))
        );
    }

    public void getMovieDetails(int movieId, HashMap<String, String> map) {
        disposable.add(repository.getMovieDetails(movieId, map)
                .subscribeOn(Schedulers.io())
                .map(new Function<Movie, Movie>() {
                    @Override
                    public Movie apply(Movie movie) throws Throwable {
                        ArrayList<String> genreNames = new ArrayList<>();
                        // MovieResponse gives list of genre(object) so we will map each id to it genre name here.a

                        for(Genre genre : movie.getGenres()){
                            genreNames.add(genre.getName());
                        }
                        movie.setGenre_names(genreNames);
                        return movie;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> movieDetails.setValue(result),
                        error -> Log.e(TAG, "getMovieDetails: " + error.getMessage()))
        );
    }

    public void getCast(int movieId, HashMap<String, String> map) {
        disposable.add(repository.getCast(movieId, map)
                .subscribeOn(Schedulers.io())
                .map(new Function<JsonObject, ArrayList<Cast>>() {
                    @Override
                    public ArrayList<Cast> apply(JsonObject jsonObject) throws Throwable {
                        JsonArray jsonArray = jsonObject.getAsJsonArray("cast");
                        return  new Gson().fromJson(jsonArray.toString(), new TypeToken<ArrayList<Cast>>(){}.getType());
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> movieCastList.setValue(result),
                        error -> Log.e(TAG, "getCastList: " + error.getMessage()))
        );
    }

    public void getActorDetails(int personId, HashMap<String,String> map) {
        disposable.add(repository.getActorDetails(personId, map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> actorDetails.setValue(result),
                        error -> Log.e(TAG, "getActorDetails: " + error.getMessage()))
        );
    }

    public void getQueriedMovies(HashMap<String, String> map){
        disposable.add(repository.getMoviesBySearch(map)
                .subscribeOn(Schedulers.io())
                .map(new Function<JsonObject, ArrayList<Movie>>() {
                         @Override
                         public ArrayList<Movie> apply(JsonObject jsonObject) throws Throwable {
                             JsonArray jsonArray = jsonObject.getAsJsonArray("results");
                             ArrayList<Movie> movieList = new Gson().fromJson(jsonArray.toString(),
                                     new TypeToken<ArrayList<Movie>>(){}.getType());
                             return movieList;
                         }
                     }
                )
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result->queriesMovies.setValue(result),
                        error-> Log.e(TAG, "getPopularMovies: " + error.getMessage() ))
        );
    }

    // room methods

    public void insertMovie(FavoriteMovie favoriteMovie){
        Log.e(TAG, "insertMovie: " );
        repository.insertMovie(favoriteMovie);
    }

    public void deleteMovie(int movieId){
        repository.deleteMovie(movieId);
    }

    public FavoriteMovie getFavoriteListMovie(int movieId){
        return  repository.getFavoriteListMovie(movieId);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        disposable.clear();
    }
}
