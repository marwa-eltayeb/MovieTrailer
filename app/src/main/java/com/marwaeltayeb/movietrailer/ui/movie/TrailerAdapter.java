package com.marwaeltayeb.movietrailer.ui.movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.data.model.Trailer;
import com.marwaeltayeb.movietrailer.databinding.TrailerItemBinding;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TrailerAdapter extends ListAdapter<Trailer, TrailerAdapter.TrailerViewHolder> {

    private final Context mContext;

    public TrailerAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    private static final DiffUtil.ItemCallback<Trailer> DIFF_CALLBACK = new DiffUtil.ItemCallback<Trailer>() {
        @Override
        public boolean areItemsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
            return oldItem.getKeyOfTrailer().equals(newItem.getKeyOfTrailer());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Trailer oldItem, @NonNull Trailer newItem) {
            return oldItem.getNameOfTrailer().equals(newItem.getNameOfTrailer());
        }
    };

    @NonNull
    @Override
    public TrailerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TrailerItemBinding trailerItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.trailer_item, parent, false);
        return new TrailerViewHolder(trailerItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerViewHolder holder, int position) {
        Trailer currentTrailer = getItem(position);
        holder.bind(currentTrailer);
    }

    class TrailerViewHolder extends RecyclerView.ViewHolder {

        private final TrailerItemBinding binding;

        private TrailerViewHolder(TrailerItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.trailerOfMovie.setOnClickListener(v -> {
                String key = getCurrentList().get(getAdapterPosition()).getKeyOfTrailer();
                String url = v.getContext().getString(R.string.youtube_url) + key;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                if (intent.resolveActivity(v.getContext().getPackageManager()) != null) {
                    v.getContext().startActivity(intent);
                }
            });
        }

        public void bind(Trailer trailer){
            String key = trailer.getKeyOfTrailer();
            String url = mContext.getString(R.string.youtube_url) + key;
            // Get id of the thumbnails
            String imageId = getYouTubeId(url);

            Glide.with(mContext)
                    .load(mContext.getString(R.string.thumbnail_firstPart) + imageId + mContext.getString(R.string.thumbnail_secondPart))
                    .into(binding.trailerOfMovie);
            binding.nameOfTrailer.setText(trailer.getNameOfTrailer());
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
