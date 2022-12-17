package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.Purachase_package_video_Model;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

public class VideoListAdapter extends RecyclerView.Adapter<VideoListAdapter.MyHolder>{
    List<Purachase_package_video_Model> purachase_package_free_video_models;
    Context context;
    FilterClick filterClick;

    public VideoListAdapter(List<Purachase_package_video_Model> purachase_package_free_video_models, Context context,FilterClick filterClick) {
        this.purachase_package_free_video_models = purachase_package_free_video_models;
        this.context = context;
        this.filterClick = filterClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.video_list_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvVideoName.setText(purachase_package_free_video_models.get(position).getVideo_title());
        Glide.with(context).load(purachase_package_free_video_models.get(position).getVideo_image()
                .replace("http://","https://")).into(holder.ivVideoImage);

        holder.llChapterVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClick.filterClick(position);
            }
        });

        try {

            // Changed from int totalvalue to long totalvalue
            long totalvalue = Integer.parseInt(purachase_package_free_video_models.get(position).getVideototal_duration());
            int viewvalue = Integer.parseInt(purachase_package_free_video_models.get(position).getVideoview_Time());
            if(totalvalue!=0){
                long parcentvalue = (((viewvalue) * 100)/(totalvalue))*100;
                Log.e("TAG", "onBindViewHolder: "+parcentvalue);
                holder.view_filter.getBackground().setLevel((int) parcentvalue);
            }else{
                Log.e("TAG", "onBindViewHolder: "+"000");

                holder.view_filter.getBackground().setLevel(0);

            }
        } catch (Exception exception) {
            System.out.println("Error on VideoListAdapter long value :" + exception.toString());
        }

    }

    @Override
    public int getItemCount() {
        return purachase_package_free_video_models.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        AppCompatImageView ivVideoPack,ivVideoImage;
        AppCompatTextView tvVideoName;
        LinearLayout llChapterVideo;
        View view_filter;
        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ivVideoImage = itemView.findViewById(R.id.ivVideoImage);
            ivVideoPack = itemView.findViewById(R.id.ivVideoPack);
            tvVideoName = itemView.findViewById(R.id.tvVideoName);
            llChapterVideo = itemView.findViewById(R.id.llChapterVideo);
            view_filter = itemView.findViewById(R.id.view_filter);
        }
    }
}
