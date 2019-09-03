package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Trailer;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    // Declare an arrayList for trailers
    private List<Trailer> trailerList;


    private int mItemSelected = -1;

    public TrailerAdapter(Context mContext, List<Trailer> trailerList) {
        this.mContext = mContext;
        this.trailerList = trailerList;
    }

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer currentMovie = trailerList.get(position);
        // Get key of the trailer
        String key = currentMovie.getKeyOfTrailer();
        // Get url of the trailer
        String url = mContext.getString(R.string.youtube_url) + key;
        // Get id of the thumbnails
        String imageId = getYouTubeId(url);
        Log.d("imageId", imageId);
        // Load the thumbnails into ImageView
        Glide.with(mContext)
                .load(mContext.getString(R.string.thumbnail_firstPart) + imageId + mContext.getString(R.string.thumbnail_secondPart))
                .into(holder.trailerOfMovie);
        holder.nameOfTrailer.setText(currentMovie.getNameOfTrailer());
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.size();
    }


    class TrailerViewHolder extends RecyclerView.ViewHolder {

        // Create view instances
        ImageView trailerOfMovie;
        TextView nameOfTrailer;

        private TrailerViewHolder(View itemView) {
            super(itemView);
            trailerOfMovie = itemView.findViewById(R.id.trailerOfMovie);
            nameOfTrailer = itemView.findViewById(R.id.nameOfTrailer);

            trailerOfMovie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Get the position of the view in the adapter
                    mItemSelected = getAdapterPosition();
                    // Get key of the trailer
                    String key = trailerList.get(mItemSelected).getKeyOfTrailer();
                    String url = v.getContext().getString(R.string.youtube_url) + key;
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                        v.getContext().startActivity(intent);
                    }
                }
            });
        }
    }

    private String getYouTubeId(String youTubeUrl) {
        String pattern = "(?<=youtu.be/|watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*";
        Pattern compiledPattern = Pattern.compile(pattern);
        Matcher matcher = compiledPattern.matcher(youTubeUrl);
        if (matcher.find()) {
            return matcher.group();
        } else {
            return "error";
        }
    }
}
