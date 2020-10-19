package com.elhady.movie_night.adapter;

import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.elhady.movie_night.Utils.Constants;
import com.elhady.movie_night.databinding.MovieItemBinding;
import com.elhady.movie_night.model.Movie;
import com.elhady.movie_night.ui.fragments.MoviesDirections;

import java.util.ArrayList;

public class CategoryMoviesAdapter extends RecyclerView.Adapter<CategoryMoviesAdapter.CategoryMoviesViewHolder> {

    private MovieItemBinding binding;
    private Context context;
    private ArrayList<Movie> moviesList;
    private String temp;

    public CategoryMoviesAdapter(Context context, ArrayList<Movie> movieList) {
        this.context = context;
        this.moviesList = movieList;
    }

    public void setMovieList(ArrayList<Movie> movieList) {
        this.moviesList = movieList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = MovieItemBinding.inflate(inflater, parent, false);
        return new CategoryMoviesViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull CategoryMoviesViewHolder holder, int position) {

        holder.binding.movieName.setText(moviesList.get(position).getTitle());

        temp = "";

        for (int i = 0; i < moviesList.get(position).getGenre_ids().size(); i++){
            if(i ==  moviesList.get(position).getGenre_ids().size() -1)
                temp+= Constants.getGenreMap().get(moviesList.get(position).getGenre_ids().get(i));
            else
                temp+= Constants.getGenreMap().get(moviesList.get(position).getGenre_ids().get(i)) + " • ";
        }

        holder.binding.movieGenre.setText(temp);
        holder.binding.movieRating.setRating(moviesList.get(position).getVote_average().floatValue()/2);
        String[] movieYear = moviesList.get(position).getRelease_date()
                .split("-");
        holder.binding.movieYear.setText(movieYear[0]);
        Glide.with(context).load(Constants.ImageBaseURL + moviesList.get(position).getPoster_path())
                .into(holder.binding.movieImage);

        holder.binding.movieItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MoviesDirections.ActionMoviesToMovieDetails action =
                        MoviesDirections.actionMoviesToMovieDetails(moviesList.get(position).getId());
                Navigation.findNavController(view).navigate(action);
            }
        });


        holder.binding.movieItemLayout.setClipToOutline(true);
    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    class CategoryMoviesViewHolder extends RecyclerView.ViewHolder {
        private MovieItemBinding binding;

        public CategoryMoviesViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
