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
import com.elhady.movie_night.ui.fragments.ActorDetailsDirections;

import java.util.ArrayList;

public class KnownForMoviesAdapter extends RecyclerView.Adapter<KnownForMoviesAdapter.KnownForMoviesViewHolder> {
    private ArrayList<Movie> moviesList;
    private Context mContext;
    private HomeItemBinding binding;

    public KnownForMoviesAdapter(Context mContext, ArrayList<Movie> moviesList) {
        this.mContext = mContext;
        this.moviesList = moviesList;
    }

    public void setMoviesList(ArrayList<Movie> moviesList){
        this.moviesList = moviesList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public KnownForMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        binding = HomeItemBinding.inflate(inflater,parent,false);
        return new KnownForMoviesViewHolder(binding);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onBindViewHolder(@NonNull KnownForMoviesViewHolder holder, int position) {
        holder.binding.movieItemRelativeLayout.setClipToOutline(true);
        holder.binding.movieItemName.setText(moviesList.get(position).getTitle());
        holder.binding.movieItemVotes.setText(moviesList.get(position).getVote_count()+"");

        Glide.with(mContext).load(Constants.ImageBaseURLw500 + moviesList.get(position).getPoster_path())
                .into(holder.binding.movieItemImage);

        holder.binding.movieItemRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActorDetailsDirections.ActionActorDetailsToMovieDetails action = ActorDetailsDirections
                        .actionActorDetailsToMovieDetails(moviesList.get(position).getId());
                Navigation.findNavController(view).navigate(action);
            }
        });

    }

    @Override
    public int getItemCount() {
        return moviesList == null ? 0 : moviesList.size();
    }

    class KnownForMoviesViewHolder extends RecyclerView.ViewHolder{

        private HomeItemBinding binding;
        public KnownForMoviesViewHolder(HomeItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}

