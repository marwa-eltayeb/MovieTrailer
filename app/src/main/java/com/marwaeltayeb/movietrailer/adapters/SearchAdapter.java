package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Movie;

import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new SearchViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchViewHolder holder, int position) {
        Movie currentMovie = getItem(position);
        holder.bind(currentMovie);
    }

    class SearchViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

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
            clickHandler.onClick(getCurrentList().get(getAdapterPosition()));
        }

        public void bind(Movie movie){
            movieTitle.setText(movie.getMovieTitle());
            movieRating.setText(movie.getMovieVote());

            Glide.with(mContext)
                    .load(IMAGE_URL + movie.getMoviePoster())
                    .into(moviePoster);
        }
    }

    public interface SearchAdapterOnClickHandler {
        void onClick(Movie movie);
    }
}
