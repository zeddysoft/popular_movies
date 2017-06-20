package com.zeddysoft.popularmovies.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.zeddysoft.popularmovies.R;
import com.zeddysoft.popularmovies.models.Movie;
import com.zeddysoft.popularmovies.models.Trailer;

import java.util.List;

/**
 * Created by azeez on 6/19/17.
 */

public class TrailerAdapter  extends RecyclerView.Adapter<TrailerAdapter.TrailerViewHolder> {

    List<Trailer> trailers;
    TrailerPlayListener listener;

    public TrailerAdapter(List<Trailer> trailers, TrailerPlayListener listener){
        this.trailers = trailers;
        this.listener = listener;
    }

    @Override
    public TrailerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);

        return new TrailerViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(TrailerViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.name.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public class TrailerViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView name;
        public ImageView play;

        public TrailerViewHolder(View view) {
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            play = (ImageView) view.findViewById(R.id.play);
            play.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            listener.onTrailerClicked(trailers.get(getLayoutPosition()));
        }
    }

    public interface TrailerPlayListener{
       void onTrailerClicked(Trailer trailer);
    }
}
