package com.elhady.fav_movie.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.elhady.fav_movie.R;

import com.elhady.fav_movie.ui.fragments.SearchMovies;
import com.elhady.fav_movie.ui.fragments.Home;
import com.elhady.fav_movie.ui.fragments.Favorite;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainBottomActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        FrameLayout frameLayout = findViewById(R.id.frame_container);

        changeFragment(0);

        navView.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.home_movie:
                changeFragment(0);
                return true;
            case R.id.favorite_movie:
                changeFragment(1);
                return true;
            case R.id.search_movie:
                changeFragment(2);
                return true;
        }
        return false;
    }

    private void changeFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Home();
                break;
            case 1:
                fragment = new Favorite();
                break;
            case 2:
                fragment = new SearchMovies();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }

}