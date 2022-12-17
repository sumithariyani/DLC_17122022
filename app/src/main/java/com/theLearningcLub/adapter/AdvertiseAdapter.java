package com.theLearningcLub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.AdvertiseModel;
import com.theLearningcLub.R;

import java.util.List;

public class AdvertiseAdapter extends RecyclerView.Adapter<AdvertiseAdapter.MyViewHolder> {

    private final List<AdvertiseModel> homevClassModels;
    private Context mcontext;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public MyViewHolder(View view) {
            super(view);
            mcontext = view.getContext();

            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }


    public AdvertiseAdapter(Context context, List<AdvertiseModel> moviesList) {
        this.homevClassModels = moviesList;
        this.mcontext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_ads, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        AdvertiseModel movie = homevClassModels.get(position);
        Glide.with(mcontext).load(movie.getAdvertise_image().replace("http://","https://")).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return homevClassModels.size();
    }

}