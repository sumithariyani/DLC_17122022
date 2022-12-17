package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.R;

import java.util.List;

public class MyPackagesAdapter extends RecyclerView.Adapter<MyPackagesAdapter.MyHolder>{
    List<String> model;
    Context context;

    public MyPackagesAdapter(List<String> model, Context context) {
        this.model = model;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_packages_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
//        holder.ll_myChapter.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(context, ChapterNameActivity.class);
//                context.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return 8;
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_myChapter;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ll_myChapter = itemView.findViewById(R.id.ll_myChapter);
        }
    }
}
