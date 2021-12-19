package com.marwaeltayeb.movietrailer.ui.main;

import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.data.model.Movie;
import com.marwaeltayeb.movietrailer.databinding.MovieItemBinding;

public class SearchAdapter extends ListAdapter<Movie, SearchAdapter.SearchViewHolder>{

    private final Context mContext;

    private final SearchAdapterOnClickHandler clickHandler;

    public SearchAdapter(Context mContext,SearchAdapterOnClickHandler clickHandler) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
        this.clickHandler = clickHandler;
    }

    private static final DiffUtil.ItemCallback<Movie> DIFF_CALLBACK = new DiffUtil.ItemCallback<Movie>() {
        @Override
        public boolean areItemsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.getMovieId().equals(newItem.getMovieId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Movie oldItem, @NonNull Movie newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public SearchViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MovieItemBinding movieItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.movie_item, parent, false);
        return new SearchViewHolder(movieItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Movie currentMovie = getItem(position);
        holder.bind(currentMovie);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private final MovieItemBinding binding;

        private SearchViewHolder(MovieItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            clickHandler.onClick(getCurrentList().get(getAdapterPosition()));
        }

        public void bind(Movie movie){
            binding.movieTitle.setText(movie.getMovieTitle());
            binding.movieRating.setText(movie.getMovieVote());

            Glide.with(mContext)
                    .load(IMAGE_URL + movie.getMoviePoster())
                    .into(binding.moviePoster);
        }
    }

    public interface SearchAdapterOnClickHandler {
        void onClick(Movie movie);
    }
}
