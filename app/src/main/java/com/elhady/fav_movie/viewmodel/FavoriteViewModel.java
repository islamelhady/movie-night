package com.elhady.fav_movie.viewmodel;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.elhady.fav_movie.db.FavoriteMovie;
import com.elhady.fav_movie.repository.Repository;

import java.util.List;

public class FavoriteViewModel extends ViewModel {
    private static final String TAG = "WishListViewModel";
    private Repository repository;
    private LiveData<List<FavoriteMovie>> favoriteMoviesList;

    @ViewModelInject
    public FavoriteViewModel(Repository repository) {
        this.repository = repository;
        favoriteMoviesList = repository.getFavoriteList();
    }

    public LiveData<List<FavoriteMovie>> getFavoriteMoviesList() {
        return favoriteMoviesList;
    }

    public void clearWishList(){
        repository.clearFavoriteList();
    }
}
