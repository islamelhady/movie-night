package com.elhady.movie_night.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.WindowManager;

import com.elhady.movie_night.R;

import com.elhady.movie_night.databinding.ActivityBottomMainBinding;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import dagger.hilt.android.AndroidEntryPoint;
import me.ibrahimsn.lib.OnItemSelectedListener;

@AndroidEntryPoint
public class MainBottomActivity extends AppCompatActivity {
    ActivityBottomMainBinding binding;
    NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        binding = ActivityBottomMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = Navigation.findNavController(MainBottomActivity.this,R.id.fragment);

        binding.smoothBar.setOnItemSelectedListener(new OnItemSelectedListener() {
            @Override
            public boolean onItemSelect(int i) {

                switch (i){
                    case 0:
                        navController.navigate(R.id.home2);
                        return true;
                    case 1:
                        navController.navigate(R.id.favorite);
                        return true;
                    case 2:
                        navController.navigate(R.id.searchMovies);
                        return true;
                }
                return  false;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.bottom_nav_menu,menu);
        NavController navController = Navigation.findNavController(this,R.id.fragment);
        binding.smoothBar.setupWithNavController(menu,navController);
        return true;
    }




}