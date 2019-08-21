package com.marwaeltayeb.movietrailer.adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.models.Trailer;

import java.util.List;

/**
 * Created by Marwa on 8/7/2019.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    private Context mContext;
    // Declare an arrayList for trailers
    private List<Trailer> trailerList;

    // Create a final private TrailerAdapterOnClickHandler called mClickHandler
    private TrailerAdapterOnClickHandler clickHandler;

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
        holder.trailerOfMovie.setVideoURI(Uri.parse("https://www.youtube.com/watch?v=" + currentMovie.getKeyOfTrailer()));
        //holder.trailerOfMovie.start();
        holder.nameOfTrailer.setText(currentMovie.getNameOfTrailer());
    }

    @Override
    public int getItemCount() {
        if (trailerList == null) {
            return 0;
        }
        return trailerList.size();
    }

    /**
     * The interface that receives onClick messages.
     */
    public interface TrailerAdapterOnClickHandler {
        void onClick();
    }

    /*
    public TrailerAdapter(Context mContext, List<Trailer> trailerList, TrailerAdapterOnClickHandler clickHandler) {
        this.mContext = mContext;
        this.trailerList = trailerList;
        this.clickHandler = clickHandler;
    }
    */


    class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        // Create view instances
        VideoView trailerOfMovie;
        TextView nameOfTrailer;

        private TrailerViewHolder(View itemView) {
            super(itemView);
            trailerOfMovie = itemView.findViewById(R.id.trailerOfMovie);
            nameOfTrailer = itemView.findViewById(R.id.nameOfTrailer);
            // Register a callback to be invoked when this view is clicked.
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            // Send title through click
            clickHandler.onClick();
        }
    }
}
