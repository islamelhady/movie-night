package com.elhady.fav_movie.ui.fragments;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.elhady.fav_movie.adapter.FavoriteAdapter;
import com.elhady.fav_movie.databinding.FavoriteLayoutBinding;
import com.elhady.fav_movie.db.FavoriteMovie;
import com.elhady.fav_movie.viewmodel.FavoriteViewModel;

import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class Favorite extends Fragment {

    private FavoriteViewModel viewModel;
    private FavoriteLayoutBinding binding;
    private FavoriteAdapter adapter;
    private List<FavoriteMovie> moviesList;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FavoriteLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return (view);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel = new ViewModelProvider(Favorite.this).get(FavoriteViewModel.class);

        intiRecyclerView();
        observeData();

        binding.clearWishList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                viewModel.clearWishList();
                Toast.makeText(getContext(),"Favorite List Cleared!",Toast.LENGTH_SHORT).show();
                moviesList.clear();
                adapter.setMoviesList(moviesList);
            }
        });
    }

    private void observeData() {
        viewModel.getFavoriteMoviesList().observe(getViewLifecycleOwner(), new Observer<List<FavoriteMovie>>() {
            @Override
            public void onChanged(List<FavoriteMovie> favoriteMovies) {
                if (favoriteMovies.size() == 0 || favoriteMovies == null){
                    binding.placeHolderText.setVisibility(View.VISIBLE);
                    binding.noItemsPlaceHolder.setVisibility(View.VISIBLE);
                }
                else{
                    binding.placeHolderText.setVisibility(View.GONE);
                    binding.noItemsPlaceHolder.setVisibility(View.GONE);
                    adapter.setMoviesList(favoriteMovies);
                    moviesList = favoriteMovies;
                }
            }
        });
    }

    private void intiRecyclerView() {
        binding.wishListRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),2));
        adapter  = new FavoriteAdapter(getContext(),moviesList);
        binding.wishListRecyclerView.setAdapter(adapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}