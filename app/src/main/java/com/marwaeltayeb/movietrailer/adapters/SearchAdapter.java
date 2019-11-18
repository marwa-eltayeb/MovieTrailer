package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Movie;

import java.util.List;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.SearchViewHolder>{

    private Context mContext;
    // Declare an arrayList for movies
    private List<Movie> movieList;

    private Movie currentMovie;

    // Create a final private MovieAdapterOnClickHandler called mClickHandler
    private SearchAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface SearchAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public SearchAdapter(Context mContext,List<Movie> movieList,SearchAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.movieList = movieList;
        this.clickHandler = clickHandler;
    }

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        currentMovie = movieList.get(position);
        holder.movieTitle.setText(currentMovie.getMovieTitle());
        holder.movieRating.setText(currentMovie.getMovieVote());
        // Load the Movie poster into ImageView
        String imageUrl2 = "https://image.tmdb.org/t/p/w500";
        Glide.with(mContext)
                .load(imageUrl2 + currentMovie.getMoviePoster())
                //.apply(options)
                .into(holder.moviePoster);
    }

    @Override
    public int getItemCount() {
        if (movieList == null) {
            return 0;
        }
        return movieList.size();
    }

    public void clear() {
        int size = movieList.size();
        movieList.clear();
        notifyItemRangeRemoved(0, size);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        TextView movieTitle;
        TextView movieRating;
        ImageView moviePoster;

        private SearchViewHolder(View itemView) {
            super(itemView);
            movieTitle = itemView.findViewById(R.id.movie_title);
            movieRating = itemView.findViewById(R.id.movie_rating);
            moviePoster = itemView.findViewById(R.id.movie_poster);
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            // Get position of the movie
            currentMovie = movieList.get(position);
            // Send movie through click
            clickHandler.onClick(currentMovie);
        }
    }


}
