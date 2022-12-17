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
import com.theLearningcLub.model.general.DatumGeneralKnowledgeModel;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.FilterClickCart;

import java.util.List;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.MyHolder>{
    List<DatumGeneralKnowledgeModel> datumGeneralKnowledgeModels;
    Context context;
    int[] product_color = {R.drawable.bg_btn_light_blue,
            R.drawable.bg_btn_light_orange,
            R.drawable.bg_btn_shed_blue};
    FilterClick filterClick;
    FilterClickCart filterClickCart;

    public GeneralAdapter(List<DatumGeneralKnowledgeModel> datumGeneralKnowledgeModels, Context context,FilterClick filterClick,FilterClickCart filterClickCart) {
        this.datumGeneralKnowledgeModels = datumGeneralKnowledgeModels;
        this.context = context;
        this.filterClick = filterClick;
        this.filterClickCart = filterClickCart;
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

        holder.packagesName.setText(datumGeneralKnowledgeModels.get(position).getPackName());
        holder.tvPrice.setText(datumGeneralKnowledgeModels.get(position).getOfferPrice());
        Glide.with(context).load(datumGeneralKnowledgeModels.get(position).getPackImage().replace("http://","https://"))
                .placeholder(R.drawable.bg_white).into(holder.ivPackagesImage);

        holder.tv_product_price.setText(datumGeneralKnowledgeModels.get(position).getPrice());

        holder.tvPackagesRating.setText(datumGeneralKnowledgeModels.get(position).getRateStar());
        if (datumGeneralKnowledgeModels.get(position).getOfferPrice() != null && !datumGeneralKnowledgeModels.get(position).getOfferPrice().isEmpty()
                && !datumGeneralKnowledgeModels.get(position).getOfferPrice().equals("null") && !datumGeneralKnowledgeModels.get(position).getOfferPrice().equals("")) {
            holder.tvPrice.setText(datumGeneralKnowledgeModels.get(position).getOfferPrice());
            holder.ivAddToCart.setVisibility(View.VISIBLE);
        }else {
            holder.ivAddToCart.setVisibility(View.GONE);
            holder.tvPrice.setText(context.getResources().getString(R.string.free));
        }

        holder.rlPackagesDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClick.filterClick(position);
            }
        });

        holder.ivAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClickCart.filterClickCart(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return datumGeneralKnowledgeModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_background_color;
        TextView tv_product_price;
        TextView tvRs;
        TextView packagesName,tvPrice;
        AppCompatImageView ivPackagesImage,ivAddToCart;
        RelativeLayout rlPackagesDetail;
        TextView tvPackagesRating;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ll_background_color = itemView.findViewById(R.id.ll_background_color);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tvRs = itemView.findViewById(R.id.tvRs);
            packagesName = itemView.findViewById(R.id.packagesName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivPackagesImage = itemView.findViewById(R.id.ivPackagesImage);
            rlPackagesDetail = itemView.findViewById(R.id.rlPackagesDetail);
            ivAddToCart = itemView.findViewById(R.id.ivAddToCart);
            tvPackagesRating = itemView.findViewById(R.id.tvPackagesRating);
        }
    }
}