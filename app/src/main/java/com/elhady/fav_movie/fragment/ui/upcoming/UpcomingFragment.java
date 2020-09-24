package com.elhady.fav_movie.fragment.ui.upcoming;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.elhady.fav_movie.R;
import com.elhady.fav_movie.iterface.OnGetGenresCallback;
import com.elhady.fav_movie.iterface.OnGetMoviesCallback;
import com.elhady.fav_movie.iterface.OnMoviesClickCallback;
import com.elhady.fav_movie.model.Genre;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.repository.MoviesRepository;
import com.elhady.fav_movie.ui.MovieDetailsActivity;
import com.elhady.fav_movie.adapter.MoviesAdapter;

import java.util.List;


public class UpcomingFragment extends Fragment {
    private MoviesRepository moviesRepository;
    private MoviesAdapter adapter;
    private RecyclerView recyclerView;
    private List<Genre> movieGenres;
    private String upcoming = MoviesRepository.UPCOMING;

    private boolean isFetchingMovies;

    private int currentPage = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.upcoming_fragment, container, false);

        moviesRepository = MoviesRepository.getInstance();

        recyclerView = root.findViewById(R.id.upcoming_show_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        setupOnScrollListener();

        getGenres();

        return root;
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int totalItemCount = manager.getItemCount();
                int visibleItemCount = manager.getChildCount();
                int firstVisibleItem = manager.findFirstVisibleItemPosition();

                if (firstVisibleItem + visibleItemCount >= totalItemCount / 2) {
                    if (!isFetchingMovies) {
                        getMovies(currentPage + 1);
                    }
                }
            }
        });
    }

    private void getGenres() {
        moviesRepository.getGenres(new OnGetGenresCallback() {
            @Override
            public void onSuccess(List<Genre> genres) {
                movieGenres = genres;
                getMovies(currentPage);
            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    private void getMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getMovies(page, upcoming, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres, callback);
                    recyclerView.setAdapter(adapter);
                } else {
                    if (page == 1) {
                        adapter.clearMovies();
                    }
                    adapter.appendMovies(movies);
                }
                currentPage = page;
                isFetchingMovies = false;

            }

            @Override
            public void onError() {
                showError();
            }
        });
    }

    OnMoviesClickCallback callback = new OnMoviesClickCallback() {
        @Override
        public void onClick(Movie movie) {
            Intent intent = new Intent(getContext(), MovieDetailsActivity.class);
            intent.putExtra(MovieDetailsActivity.MOVIE_ID, movie.getId());
            startActivity(intent);
        }
    };

    private void showError() {
        Toast.makeText(getContext(), "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }
}