package com.elhady.filme.ui;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.elhady.filme.R;

import com.elhady.filme.fragment.ui.upcoming.UpcomingFragment;
import com.elhady.filme.fragment.ui.toprated.TopRatedShowFragment;
import com.elhady.filme.fragment.ui.popshow.PopularShowFragment;
import com.elhady.filme.fragment.ui.favorite.FavoriteShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

public class MainBottomActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        FrameLayout frameLayout = findViewById(R.id.frame_container);

        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("filme");
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.

//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setTitle("Tv Shows");

        changeFragment(0);

        navView.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.popular_shows:
                changeFragment(0);
                return true;
            case R.id.top_rated_shows:
                changeFragment(1);
                return true;
            case R.id.upcoming_shows:
                changeFragment(2);
                return true;
            case R.id.favorite_shows:
                changeFragment(3);
                return true;
        }
        return false;
    }

    private void changeFragment(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new PopularShowFragment();
                break;
            case 1:
                fragment = new TopRatedShowFragment();
                break;
            case 2:
                fragment = new UpcomingFragment();
                break;
            case 3:
                fragment = new FavoriteShowFragment();
                break;
        }
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.frame_container, fragment)
                .commit();
    }
}