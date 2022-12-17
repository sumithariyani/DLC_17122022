package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.R;
import com.theLearningcLub.model.grammer.DatumGrammarModel;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

public class GrammarAdapter extends RecyclerView.Adapter<GrammarAdapter.MyHolder>{
    List<DatumGrammarModel> datumGrammarModels;
    Context context;
    int[] product_color = {R.drawable.bg_btn_pirple_1,
            R.drawable.bg_btn_orange_red,
            R.drawable.bg_btn_shed_green};
    FilterClick filterClick;


    public GrammarAdapter(List<DatumGrammarModel> datumGrammarModels, Context context,FilterClick filterClick) {
        this.datumGrammarModels = datumGrammarModels;
        this.context = context;
        this.filterClick = filterClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocabulary_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_product_price.setPaintFlags(holder.tv_product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRs.setPaintFlags(holder.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ll_background_color.setBackground(context.getResources().getDrawable(product_color[position % 3]));

        holder.packagesName.setText(datumGrammarModels.get(position).getPackName());
        holder.tvPrice.setText(datumGrammarModels.get(position).getOfferPrice());
        Glide.with(context).load(datumGrammarModels.get(position).getPackImage().replace("http://","https://"))
                .placeholder(R.drawable.bg_white).into(holder.ivPackagesImage);

        holder.tv_product_price.setText(datumGrammarModels.get(position).getPrice());

        if (datumGrammarModels.get(position).getOfferPrice() != null && !datumGrammarModels.get(position).getOfferPrice().isEmpty()
                && !datumGrammarModels.get(position).getOfferPrice().equals("null") && !datumGrammarModels.get(position).getOfferPrice().equals("")) {
            holder.tvPrice.setText(datumGrammarModels.get(position).getOfferPrice());
        }else {
            holder.tvPrice.setText(context.getResources().getString(R.string.free));
        }

        holder.rlPackagesDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClick.filterClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumGrammarModels.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_background_color;
        TextView tv_product_price;
        TextView tvRs;
        TextView packagesName,tvPrice;
        AppCompatImageView ivPackagesImage;
        RelativeLayout rlPackagesDetail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ll_background_color = itemView.findViewById(R.id.ll_background_color);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tvRs = itemView.findViewById(R.id.tvRs);
            packagesName = itemView.findViewById(R.id.packagesName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivPackagesImage = itemView.findViewById(R.id.ivPackagesImage);
            rlPackagesDetail = itemView.findViewById(R.id.rlPackagesDetail);
        }
    }
}
