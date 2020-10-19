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
import com.elhady.movie_night.databinding.HomeItemBinding;
import com.elhady.movie_night.model.Movie;
import com.elhady.movie_night.ui.fragments.HomeDirections;

import java.util.ArrayList;

public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.HomeViewHolder> {

    private HomeItemBinding binding;
    private Context context;
    private ArrayList<Movie> moviesList;

    public HomeAdapter(Context context, ArrayList<Movie> moviesList) {
        this.context = context;
        this.moviesList = moviesList;
    }

    public void setMoviesList(ArrayList<Movie> moviesList) {
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HomeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        binding = HomeItemBinding.inflate(inflater, parent, false);
        return new HomeViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull HomeViewHolder holder, int position) {
        holder.binding.movieItemRelativeLayout.setClipToOutline(true);
        holder.binding.movieItemName.setText(moviesList.get(position).getTitle());
        holder.binding.movieItemVotes.setText(moviesList.get(position).getVote_count()+"");

        Glide.with(context).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                .into(holder.binding.movieItemImage);

        holder.binding.movieItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                HomeDirections.ActionHome2ToMovieDetails action = HomeDirections
                        .actionHome2ToMovieDetails(moviesList.get(position).getId());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    class HomeViewHolder extends RecyclerView.ViewHolder {

        private HomeItemBinding binding;

        public HomeViewHolder(HomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
