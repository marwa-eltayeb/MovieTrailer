package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context mContext;
    // Declare an arrayList for trailers
    private List<Review> reviewList;

    public ReviewAdapter(Context mContext, List<Review> reviewList) {
        this.mContext = mContext;
        this.reviewList = reviewList;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_item, parent, false);
        return new ReviewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review currentReview = reviewList.get(position);
        holder.reviewOfMovie.setText(currentReview.getContent());
        holder.authorOfReview.setText(currentReview.getAuthor());
        holder.urlOfReview.setText(currentReview.getUrl());
    }

    @Override
    public int getItemCount() {
        if (reviewList == null) {
            return 0;
        }
        return reviewList.size();
    }


    class ReviewViewHolder extends RecyclerView.ViewHolder{
        // Create view instances
        TextView reviewOfMovie;
        TextView authorOfReview;
        TextView urlOfReview;

        private ReviewViewHolder(View itemView) {
            super(itemView);
            reviewOfMovie = itemView.findViewById(R.id.reviewOfMovie);
            authorOfReview = itemView.findViewById(R.id.authorOfReview);
            urlOfReview = itemView.findViewById(R.id.urlOfReview);
        }
    }



}
