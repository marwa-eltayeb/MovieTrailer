package com.marwaeltayeb.movietrailer.ui.movie;

import static com.marwaeltayeb.movietrailer.utils.Constant.URL_OF_REVIEW;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.data.model.Review;
import com.marwaeltayeb.movietrailer.databinding.ReviewItemBinding;
import com.marwaeltayeb.movietrailer.ui.webview.WebViewActivity;

public class ReviewAdapter extends ListAdapter<Review, ReviewAdapter.ReviewViewHolder> {

    public ReviewAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Review> DIFF_CALLBACK = new DiffUtil.ItemCallback<Review>() {
        @Override
        public boolean areItemsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.getId().equals(newItem.getId());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Review oldItem, @NonNull Review newItem) {
            return oldItem.equals(newItem);
        }
    };

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        ReviewItemBinding reviewItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.review_item, parent, false);
        return new ReviewViewHolder(reviewItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = getItem(position);
        holder.bind(currentReview);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        private final ReviewItemBinding binding;

        private ReviewViewHolder(ReviewItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.urlOfReview.setOnClickListener(v -> {
                Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                String url = getCurrentList().get(getAdapterPosition()).getUrl();
                intent.putExtra(URL_OF_REVIEW , url);
                v.getContext().startActivity(intent);
            });
        }

        public void bind(Review review){
            binding.reviewOfMovie.setText(review.getContent());
            binding.authorOfReview.setText("written by" + " " + review.getAuthor());
        }
    }
}
