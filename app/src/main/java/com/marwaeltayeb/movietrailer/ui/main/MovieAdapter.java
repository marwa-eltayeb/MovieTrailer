package com.marwaeltayeb.movietrailer.ui.main;

import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.data.model.Movie;
import com.marwaeltayeb.movietrailer.databinding.MovieItemBinding;

public class MovieAdapter extends PagedListAdapter<Movie, MovieAdapter.MovieViewHolder> {
    
    private final Context mContext;

    private final MovieAdapterOnClickHandler clickHandler;

    public interface MovieAdapterOnClickHandler {
        void onClick(Movie movie);
    }

    public MovieAdapter(Context mContext, MovieAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.movieId.equals(newMovie.movieId);
        }

        @SuppressLint("DiffUtilEquals")
        @Override
        public boolean areContentsTheSame(@NonNull Movie oldMovie, @NonNull Movie newMovie) {
            return oldMovie.equals(newMovie);
        }
    };

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        return new MovieViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = getItem(position);
        holder.bind(movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final MovieItemBinding binding;

        private MovieViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getAdapterPosition();
            clickHandler.onClick(getCurrentList().get(position));
        }

        public void bind(Movie movie){
            if (movie != null) {
                binding.movieTitle.setText(movie.getMovieTitle());
                binding.movieRating.setText(movie.getMovieVote());

                Glide.with(mContext)
                        .load(IMAGE_URL + movie.getMoviePoster())
                        .into(binding.moviePoster);
            } else {
                Toast.makeText(mContext, "Movie is null", Toast.LENGTH_LONG).show();
            }
        }
    }
}