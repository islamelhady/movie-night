package com.elhady.filme.ui.fragments.popshow;

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

import com.elhady.filme.R;
import com.elhady.filme.interfaces.OnGetGenresCallback;
import com.elhady.filme.interfaces.OnGetMoviesCallback;
import com.elhady.filme.interfaces.OnMoviesClickCallback;
import com.elhady.filme.model.Genre;
import com.elhady.filme.model.Movie;
import com.elhady.filme.repository.MoviesRepository;
import com.elhady.filme.ui.activities.MovieDetailsActivity;
import com.elhady.filme.adapter.MoviesAdapter;

import java.util.List;


public class PopularShowFragment extends Fragment {

    private MoviesRepository moviesRepository;
    private MoviesAdapter adapter;
    private RecyclerView moviesList;
    private List<Genre> movieGenres;
    private String sortBy = MoviesRepository.POPULAR;

    /**
     * isFetchingMovies:
     * flag that we will use to determine if we are currently fetching the next page.
     * Without this flag, if the we scrolled 50% above it will fetch the same page multiple times and causes duplication.
     * Try commenting out this flag and you will notice that when you scroll,
     * you will see the same movies of next page again and again.
     */
    private boolean isFetchingMovies;
    /*** currentPage:
     * initialized to page 1.
     * Every time we scrolled half of the list we increment it by one which is the next page.
     */
    private int currentPage = 1;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.popular_show_fragment, container, false);
        moviesRepository = MoviesRepository.getInstance();

        moviesList = root.findViewById(R.id.popular_movie_show_rv);
        moviesList.setLayoutManager(new LinearLayoutManager(getContext()));

//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        setupOnScrollListener();

        getGenres();
        return root;
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(getContext());
        moviesList.setLayoutManager(manager);
        moviesList.addOnScrollListener(new RecyclerView.OnScrollListener() {
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
        moviesRepository.getMovies(page, MoviesRepository.POPULAR, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres, callback);
                    moviesList.setAdapter(adapter);
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


//    sortBy = MoviesRepository.POPULAR;
    //getMovies(currentPage);

}