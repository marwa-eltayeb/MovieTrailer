package com.marwaeltayeb.movietrailer.adapters;

import android.arch.paging.PagedListAdapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Movie;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {


    private Context mContext;

    protected MovieAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);

        if (movie != null) {
            holder.movieTitle.setText(movie.getMovieTitle());
            holder.movieRating.setText(movie.getMovieVote());
            // Load the Movie poster into ImageView
            Glide.with(mContext)
                    .load(movie.getMoviePoster())
                    .into(holder.moviePoster);
        } else {
            Toast.makeText(mContext, "Movie is null", Toast.LENGTH_LONG).show();
        }
    }

    // It determine if two list objects are the same or not
    private static DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>(){
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.movieId == newMovie.movieId;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.equals(newMovie);
        }
    };

    class MovieViewHolder extends RecyclerView.ViewHolder {

        // Create view instances
        TextView movieTitle;
        TextView movieRating;
        ImageView moviePoster;

        private MovieViewHolder(View itemView) {
            super(itemView);
            movieTitle = (TextView) itemView.findViewById(R.id.movie_title);
            movieRating = (TextView) itemView.findViewById(R.id.movie_rating);
            moviePoster = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }
}

