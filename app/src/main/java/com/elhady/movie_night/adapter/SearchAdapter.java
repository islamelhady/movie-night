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
import com.elhady.movie_night.ui.fragments.SearchMoviesDirections;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder> {
    private MovieItemBinding binding;
    private Context context;
    private ArrayList<Movie> moviesList;
    private String temp;

    public SearchAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public void setMoviesList(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = MovieItemBinding.inflate(inflater,parent,false);
        return new SearchViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
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
                SearchMoviesDirections.ActionSearchMoviesToMovieDetails action =
                        SearchMoviesDirections.actionSearchMoviesToMovieDetails(moviesList.get(position).getId());
                Navigation.findNavController(view).navigate(action);
            }
        });

        holder.binding.movieItemLayout.setClipToOutline(true);

    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    class SearchViewHolder extends RecyclerView.ViewHolder{

        private MovieItemBinding binding;
        public SearchViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
