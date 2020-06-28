package com.elhady.fav_movie.ui.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.elhady.fav_movie.R;
import com.elhady.fav_movie.iterface.OnGetMoviesCallback;
import com.elhady.fav_movie.model.Movie;
import com.elhady.fav_movie.repository.MoviesRepository;
import com.elhady.fav_movie.ui.adapter.MoviesAdapter;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView moviesList;
    private MoviesAdapter adapter;

    private MoviesRepository moviesRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        moviesRepository = MoviesRepository.getInstance();

        moviesList = findViewById(R.id.movies_list);
        moviesList.setLayoutManager(new LinearLayoutManager(this));

        moviesRepository.getMovies(new OnGetMoviesCallback() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MoviesAdapter(movies);
                moviesList.setAdapter(adapter);
            }

            @Override
            public void onError() {
                Toast.makeText(MainActivity.this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}