package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.Cart_View_Model_Class;
import com.theLearningcLub.R;
import com.theLearningcLub.model.viewcart.DatumViewCartModel;
import com.theLearningcLub.utils.FilterClick;

import java.util.ArrayList;
import java.util.List;

public class MyCartAdapter extends RecyclerView.Adapter<MyCartAdapter.MyHolder>{
    List<DatumViewCartModel> datumViewCartModels;
    Context context;
    FilterClick filterClick;

    public MyCartAdapter(List<DatumViewCartModel> datumViewCartModels, Context context,FilterClick filterClick) {
        this.datumViewCartModels = datumViewCartModels;
        this.context = context;
        this.filterClick = filterClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_cart_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvOfferPrice.setPaintFlags(holder.tvOfferPrice.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRs.setPaintFlags(holder.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        holder.title.setText(datumViewCartModels.get(position).getPackName());
        holder.textViewPrice.setText(datumViewCartModels.get(position).getPrice());

        Glide.with(context).load(datumViewCartModels.get(position).getPackImage()
                .replace("http://","https://")).into(holder.imageViewMain);

        holder.imageDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterClick.filterClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumViewCartModels.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tvOfferPrice,tvRs;
        AppCompatTextView title;
        AppCompatImageView imageViewMain;
        AppCompatImageView imageDelete;
        AppCompatTextView textViewPrice;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvRs = itemView.findViewById(R.id.tvRs);
            tvOfferPrice = itemView.findViewById(R.id.tvOfferPrice);
            title = itemView.findViewById(R.id.tvCartName);
            imageViewMain = itemView.findViewById(R.id.ivPackImage);
            imageDelete = itemView.findViewById(R.id.ivDelete);
            textViewPrice = itemView.findViewById(R.id.tvPriceItem);
        }
    }
}
