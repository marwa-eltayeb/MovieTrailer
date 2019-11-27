package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Movie;

import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;

public class FavoriteAdapter extends ListAdapter<Movie, FavoriteAdapter.FavoriteHolder> {

    private Context mContext;
    private Movie movie;

    // Create a final private FavoriteAdapterOnClickHandler called mClickHandler
    private FavoriteAdapter.FavoriteAdapterOnClickHandler clickHandler;

    /**
     * The interface that receives onClick messages.
     */
    public interface FavoriteAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public FavoriteAdapter(Context mContext, FavoriteAdapter.FavoriteAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getMovieId() == newItem.getMovieId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getMovieTitle().equals(newItem.getMovieTitle()) &&
                    oldItem.getMovieDescription().equals(newItem.getMovieDescription());
        }
    };

    @NonNull
    @Override
    public FavoriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new FavoriteHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteHolder holder, int position) {
        movie = getItem(position);

        if (movie != null) {
            holder.movieTitle.setText(movie.getMovieTitle());
            holder.movieRating.setText(movie.getMovieVote());

            RequestOptions options = new RequestOptions()
                    .centerCrop()
                    .placeholder(R.drawable.maze_ruuner)
                    .error(R.drawable.dots)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .priority(Priority.HIGH)
                    .dontAnimate()
                    .dontTransform();

            // Load the Movie poster into ImageView
            Glide.with(mContext)
                    .load(IMAGE_URL + movie.getMoviePoster())
                    //.apply(options)
                    .into(holder.moviePoster);

            holder.moviePoster.setImageResource(R.drawable.no_preview);
        }
    }

    public Movie getMovieAt(int position) {
        return getItem(position);
    }

    class FavoriteHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        // Create view instances
        TextView movieTitle;
        TextView movieRating;
        ImageView moviePoster;

        public FavoriteHolder(View itemView) {
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
            movie = getItem(position);
            // Send movie through click
            clickHandler.onClick(movie);
        }
    }



}
