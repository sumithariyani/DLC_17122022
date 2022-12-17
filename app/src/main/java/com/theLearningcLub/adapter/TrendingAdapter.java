package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.PackageActivity;
import com.theLearningcLub.R;
import com.theLearningcLub.model.search.DatumSearchViewModel;

import java.util.List;

public class TrendingAdapter extends RecyclerView.Adapter<TrendingAdapter.MyHolder>{
    List<DatumSearchViewModel> datumSearchViewModels;
    Context context;

    public TrendingAdapter(List<DatumSearchViewModel> datumSearchViewModels, Context context) {
        this.datumSearchViewModels = datumSearchViewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.trending_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvPackagesName.setText(datumSearchViewModels.get(position).getPackName());
        holder.llSearchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, PackageActivity.class);
                intent.putExtra("searchModel",datumSearchViewModels.get(position));
                intent.putExtra("key","2");
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumSearchViewModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvPackagesName;
        LinearLayout llSearchView;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvPackagesName = itemView.findViewById(R.id.tvPackagesName);
            llSearchView = itemView.findViewById(R.id.llSearchView);
        }
    }
}
