package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.R;

import java.util.List;

public class ChapterNameAdapter extends RecyclerView.Adapter<ChapterNameAdapter.MyHolder>{
    List<String> model;
    Context context;

    public ChapterNameAdapter(List<String> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chapter_name_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

    }

    @Override
    public int getItemCount() {
        return 8;
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_chapter_video;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ll_chapter_video = itemView.findViewById(R.id.ll_chapter_video);
        }
    }
}
