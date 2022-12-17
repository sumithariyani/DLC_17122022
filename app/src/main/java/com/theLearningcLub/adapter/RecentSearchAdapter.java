package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.R;
import com.theLearningcLub.model.search.BodySearchViewModel;

import java.util.List;

public class RecentSearchAdapter extends RecyclerView.Adapter<RecentSearchAdapter.MyHolder>{
    List<BodySearchViewModel> bodySearchViewModels;
    Context context;
    TrendingAdapter trendingAdapter;

    public RecentSearchAdapter(List<BodySearchViewModel> bodySearchViewModels, Context context) {
        this.bodySearchViewModels = bodySearchViewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recent_search_layout, parent, false);
        int height = parent.getMeasuredHeight() / 4;
        view.setMinimumHeight(height);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvSearchTitle.setText(bodySearchViewModels.get(position).getCatName());

//        trendingAdapter = new TrendingAdapter(bodySearchViewModels.get(position).getData(),context);
//        holder.rvSearchView.setAdapter(trendingAdapter);
    }

    @Override
    public int getItemCount() {
        return bodySearchViewModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tvSearchTitle;
        RecyclerView rvSearchView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            rvSearchView = itemView.findViewById(R.id.rvSearchView);
            tvSearchTitle = itemView.findViewById(R.id.tvSearchTitle);
        }
    }
}
