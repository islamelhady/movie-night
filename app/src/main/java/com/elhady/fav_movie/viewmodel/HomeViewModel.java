package com.elhady.fav_movie.viewmodel;

import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.model.MovieResponse;
import com.elhady.fav_movie.repository.Repository;

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
}
