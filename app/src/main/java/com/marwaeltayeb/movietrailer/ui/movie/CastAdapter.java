package com.marwaeltayeb.movietrailer.ui.movie;

import static com.marwaeltayeb.movietrailer.utils.Constant.IMAGE_URL;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.marwaeltayeb.movietrailer.R;
import com.marwaeltayeb.movietrailer.data.model.Cast;
import com.marwaeltayeb.movietrailer.databinding.CastItemBinding;

public class CastAdapter extends ListAdapter<Cast, CastAdapter.CastViewHolder> {

    private final Context mContext;

    public CastAdapter(Context mContext) {
        super(DIFF_CALLBACK);
        this.mContext = mContext;
    }

    private static final DiffUtil.ItemCallback<Cast> DIFF_CALLBACK = new DiffUtil.ItemCallback<Cast>() {
        @Override
        public boolean areItemsTheSame(@NonNull Cast oldItem, @NonNull Cast newItem) {
            return oldItem.getNameOfActor().equals(newItem.getNameOfActor());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Cast oldItem, @NonNull Cast newItem) {
            return oldItem.getProfileOfActor().equals(newItem.getNameOfActor());
        }
    };

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CastItemBinding castItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.cast_item, parent, false);
        return new CastViewHolder(castItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {
        Cast currentCast = getItem(position);
        holder.bind(currentCast);
    }

    class CastViewHolder extends RecyclerView.ViewHolder {

        private final CastItemBinding binding;

        private CastViewHolder(CastItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(Cast cast){
            if (cast != null) {
                binding.actorName.setText(cast.getNameOfActor());
                binding.actorCharacter.setText(cast.getCharacter());

                Glide.with(mContext)
                        .load(IMAGE_URL + cast.getProfileOfActor())
                        .placeholder(R.drawable.ic_profile)
                        .into(binding.actorProfile);
            } else {
                Toast.makeText(mContext, "Cast is null", Toast.LENGTH_LONG).show();
            }
        }
    }

}
