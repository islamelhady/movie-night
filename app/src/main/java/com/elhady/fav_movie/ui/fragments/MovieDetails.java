package com.elhady.fav_movie.ui.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.elhady.fav_movie.R;
import com.elhady.fav_movie.databinding.MovieDetailsLayoutBinding;
import com.elhady.fav_movie.model.Cast;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.viewmodel.HomeViewModel;

import java.util.ArrayList;
import java.util.HashMap;


public class MovieDetails extends Fragment {

    private MovieDetailsLayoutBinding binding;

    public MovieDetails() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = MovieDetailsLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}