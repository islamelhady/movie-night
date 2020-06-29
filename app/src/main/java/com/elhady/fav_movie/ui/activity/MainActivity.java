package com.elhady.fav_movie.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elhady.fav_movie.R;
import com.elhady.fav_movie.iterface.OnGetGenresCallback;
import com.elhady.fav_movie.iterface.OnGetMoviesCallback;
import com.elhady.fav_movie.model.Genre;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.repository.MoviesRepository;
import com.elhady.fav_movie.ui.adapter.MoviesAdapter;

import java.security.acl.LastOwnerException;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private MoviesRepository moviesRepository;
    private MoviesAdapter adapter;
    private RecyclerView moviesList;
    private List<Genre> movieGenres;


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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRepository = MoviesRepository.getInstance();

        moviesList = findViewById(R.id.movies_list);
        moviesList.setLayoutManager(new LinearLayoutManager(this));

        setupOnScrollListener();

        getGenres();
    }

    private void setupOnScrollListener() {
        final LinearLayoutManager manager = new LinearLayoutManager(this);
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

    // 1. isFetchingMovies is set to true to stop fetching movies for the meantime when scrolling.
    // 2. We refactored onSuccess(…) a bit and now accepts a page. We check first
    //    if adapter == null then we initialize it. For succeeding calls to getMovies(…)
    //    we just append the movies that we receive from the page that we specified.
    // 3. We set currentPage = page which was next page that we requested.
    // 4. We set isFetchingMovies to false to allow for fetching of movies again.
    private void getMovies(int page) {
        isFetchingMovies = true;
        moviesRepository.getMovies(page, new OnGetMoviesCallback() {
            @Override
            public void onSuccess(int page, List<Movie> movies) {
                Log.d("MoviesRepository", "Current Page = " + page);
                if (adapter == null) {
                    adapter = new MoviesAdapter(movies, movieGenres);
                    moviesList.setAdapter(adapter);
                } else {
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

    private void showError() {
        Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_movies, menu);
        return super.onCreateOptionsMenu(menu);
    }
}