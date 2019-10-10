package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.activities.WebViewActivity;
import com.marwaeltayeb.movietrailer.models.Review;

import java.util.List;

import static com.marwaeltayeb.movietrailer.utils.Constant.URL_OF_REVIEW;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>{

    private Context mContext;
    // Declare an arrayList for trailers
    private List<Review> reviewList;

    private int mItemSelected= -1;


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
        holder.authorOfReview.setText("written by" + " " + currentReview.getAuthor());
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

            urlOfReview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), WebViewActivity.class);
                    // Get the position of the view in the adapter
                    mItemSelected = getAdapterPosition();
                    notifyDataSetChanged();
                    // Get url of the review
                    String url = reviewList.get(mItemSelected).getUrl();
                    // Send the url with the intent
                    intent.putExtra(URL_OF_REVIEW , url);
                    v.getContext().startActivity(intent);


                }
            });
        }


    }

}
