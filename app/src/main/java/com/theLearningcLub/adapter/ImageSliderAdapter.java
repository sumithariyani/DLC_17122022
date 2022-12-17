package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.R;
import com.theLearningcLub.model.imageslider.DatatwoImageSliderModel;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.MyHolder>{
    List<DatatwoImageSliderModel> datatwoImageSliderModels;
    Context context;

    public ImageSliderAdapter(List<DatatwoImageSliderModel> datatwoImageSliderModels, Context context) {
        this.datatwoImageSliderModels = datatwoImageSliderModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_ads, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        Glide.with(context).load(datatwoImageSliderModels.get(position).getAdvertiseImage().replace("http://","https://")).into(holder.ivImage);
    }

    @Override
    public int getItemCount() {
        return datatwoImageSliderModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        ImageView ivImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivImage = itemView.findViewById(R.id.ivImage);
        }
    }
}
