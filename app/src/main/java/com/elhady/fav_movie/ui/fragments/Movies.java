package com.elhady.fav_movie.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elhady.fav_movie.Utils.Constants;
import com.elhady.fav_movie.adapter.CategoryMoviesAdapter;
import com.elhady.fav_movie.databinding.MoviesLayoutBinding;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;

public class Movies extends Fragment {

    private MoviesLayoutBinding binding;
    private HomeViewModel viewModel;
    private HashMap<String, String> map;
    private String moviesCategory="";
    private CategoryMoviesAdapter adapter;
    private ArrayList<Movie> moviesList;

    public Movies() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MoviesLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        map = new HashMap<>();
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        MoviesArgs args = MoviesArgs.fromBundle(getArguments());
        moviesCategory = args.getMovieCategory();

        map.put("api_key", Constants.API_KEY);
        map.put("page","1");

        initRecyclerView();
        observeData();
        getMoviesList();
    }

    private void initRecyclerView() {
        binding.moviesRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
        adapter = new CategoryMoviesAdapter(getContext(),moviesList);
        binding.moviesRecyclerView.setAdapter(adapter);
    }

    private void observeData(){
        switch (moviesCategory){
            case Constants.Current:
                viewModel.getCurrentlyShowingList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onChanged(ArrayList<Movie> movies) {
                        adapter.setMovieList(movies);
                    }
                });
                break;
            case Constants.Upcoming:
                viewModel.getUpcomingMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onChanged(ArrayList<Movie> movies) {
                        adapter.setMovieList(movies);
                    }
                });
                break;
            case Constants.TopRated:
                viewModel.getTopRatedMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onChanged(ArrayList<Movie> movies) {
                        adapter.setMovieList(movies);
                    }
                });
                break;
            case Constants.Popular:
                viewModel.getPopularMoviesList().observe(getViewLifecycleOwner(), new Observer<ArrayList<Movie>>() {
                    @Override
                    public void onChanged(ArrayList<Movie> movies) {
                        adapter.setMovieList(movies);
                    }
                });
                break;
        }
    }

    private void getMoviesList(){
        switch (moviesCategory){
            case Constants.Current:
                viewModel.getCurrentlyShowingMovies(map);
                break;
            case Constants.Upcoming:
                viewModel.getUpcomingMovies(map);
                break;
            case Constants.TopRated:
                viewModel.getTopRatedMovies(map);
                break;
            case Constants.Popular:
                viewModel.getPopularMovies(map);
                break;
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}