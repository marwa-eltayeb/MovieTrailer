package com.marwaeltayeb.movietrailer.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.activities.WebViewActivity;
import com.marwaeltayeb.movietrailer.models.Review;

import static com.marwaeltayeb.movietrailer.utils.Constant.URL_OF_REVIEW;

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
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = getItem(position);
        holder.bind(currentReview);
    }

    class ReviewViewHolder extends RecyclerView.ViewHolder{

        TextView reviewOfMovie;
        TextView authorOfReview;
        TextView urlOfReview;

        private ReviewViewHolder(View itemView) {
            super(itemView);
            reviewOfMovie = itemView.findViewById(R.id.reviewOfMovie);
            authorOfReview = itemView.findViewById(R.id.authorOfReview);
            urlOfReview = itemView.findViewById(R.id.urlOfReview);

            urlOfReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                    String url = getCurrentList().get(getAdapterPosition()).getUrl();
                    intent.putExtra(URL_OF_REVIEW , url);
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Review review){
            reviewOfMovie.setText(review.getContent());
            authorOfReview.setText("written by" + " " + review.getAuthor());
        }
    }
}
