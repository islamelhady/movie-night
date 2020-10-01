package com.elhady.fav_movie.ui.activities;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;

import com.elhady.fav_movie.R;

import com.elhady.fav_movie.ui.fragments.upcoming.UpcomingFragment;
import com.elhady.fav_movie.ui.fragments.toprated.TopRatedShowFragment;
import com.elhady.fav_movie.ui.fragments.popshow.PopularShowFragment;
import com.elhady.fav_movie.ui.fragments.favorite.FavoriteShowFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

public class MainBottomActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bottom_main);
        BottomNavigationView navView = findViewById(R.id.bottom_navigation_view);
        FrameLayout frameLayout = findViewById(R.id.frame_container);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem searchViewItem = menu.findItem(R.id.app_bar_search);
        MenuItem settings = menu.findItem(R.id.setting);

        final SearchView searchView = (SearchView) searchViewItem.getActionView();
        searchView.setQueryHint(getResources().getString(R.string.search_for_movies));

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}