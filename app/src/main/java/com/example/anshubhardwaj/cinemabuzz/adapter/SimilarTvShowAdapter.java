package com.example.anshubhardwaj.cinemabuzz.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anshubhardwaj.cinemabuzz.Interface.SharedItemClickListner;
import com.example.anshubhardwaj.cinemabuzz.R;
import com.example.anshubhardwaj.cinemabuzz.api.ApiClient;
import com.example.anshubhardwaj.cinemabuzz.object.TvShowObject;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SimilarTvShowAdapter extends RecyclerView.Adapter<SimilarTvShowAdapter.NowPlayingViewHolder> {

    private List<TvShowObject> tvShowList;
    private Context context;
    private SharedItemClickListner onItemClickListner;

    public SimilarTvShowAdapter(Context context, SharedItemClickListner onItemClickListner) {
        this.context = context;
        tvShowList = new ArrayList<>();
        this.onItemClickListner = onItemClickListner;
    }

    public void setTvShowList(List<TvShowObject> tvShowList) {
        this.tvShowList = tvShowList;
        notifyDataSetChanged();
    }

    public List<TvShowObject> getTvShowList() {
        return tvShowList;
    }

    @Override
    public SimilarTvShowAdapter.NowPlayingViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.similar_movie, parent, false);
        return new NowPlayingViewHolder(view);
    }


    @Override
    public void onBindViewHolder(SimilarTvShowAdapter.NowPlayingViewHolder holder, int position) {

        TvShowObject movieObject = tvShowList.get(position);

        Picasso.with(context).load(ApiClient.IMAGE_URL + movieObject.getPosterPath()).into(holder.movieImage);
        holder.tv_title.setText(movieObject.getName());

        if (movieObject.getFirstAirDate()!=null){
            String[] date = movieObject.getFirstAirDate().split("-");
            holder.tv_year.setText(String.valueOf(date[0]));
        }


    }

    @Override
    public int getItemCount() {
        if (tvShowList.size() != 0) {
            return tvShowList.size();
        } else {
            return 0;
        }
    }


    public class NowPlayingViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.imageViewMovie)
        ImageView movieImage;
        @BindView(R.id.textViewYear)
        TextView tv_year;
        @BindView(R.id.textViewName)
        TextView tv_title;

        public NowPlayingViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            TvShowObject movieObject = tvShowList.get(getAdapterPosition());
            onItemClickListner.onItemClick(movieObject.getId(), movieImage, movieObject.getPosterPath());
        }
    }
}
