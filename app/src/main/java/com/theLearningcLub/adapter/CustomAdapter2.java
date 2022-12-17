package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.Model_Class.City_Model;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

public class CustomAdapter2 extends RecyclerView.Adapter<CustomAdapter2.ViewHolder> {
    List<City_Model> list;
    Context context;
    FilterClick filterClick;

    public CustomAdapter2(Context context, List<City_Model> list,FilterClick filterClick) {
        this.context = context;
        this.list = list;
        this.filterClick = filterClick;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View listItem = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_custom_listview, parent, false);
        return new ViewHolder(listItem);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.textView.setText(list.get(position).cityName);

       holder.linearLayout.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               filterClick.filterClick(position);
           }
       });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;
        ImageView imageView2;
        LinearLayout linearLayout;

        public ViewHolder(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.Search_MainTitle_listview);
            imageView2 = itemView.findViewById(R.id.Search_ListView_icon);
            linearLayout = itemView.findViewById(R.id.board_layout);
        }
    }
}
