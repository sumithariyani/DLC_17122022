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
import com.theLearningcLub.model.vocabulary.DatumVocabularyModel;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.FilterClickCart;
import com.theLearningcLub.utils.SessionManager;

import java.util.List;

public class VocabularyAdapter2 extends RecyclerView.Adapter<VocabularyAdapter2.MyHolder>{
    List<DatumVocabularyModel> datumVocabularyModels;
    Context context;
    SessionManager sessionManager;
    int[] product_color = {R.drawable.bg_btn_blue,
            R.drawable.bg_btn_pink,
            R.drawable.bg_btn_green};
    FilterClick filterClick;
    FilterClickCart filterClickCart;

    public VocabularyAdapter2(List<DatumVocabularyModel> datumVocabularyModels, Context context, FilterClick filterClick, FilterClickCart filterClickCart) {
        this.datumVocabularyModels = datumVocabularyModels;
        this.context = context;
        this.filterClick = filterClick;
        this.filterClickCart = filterClickCart;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.vocabulary_layout2, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        sessionManager = new SessionManager(context);

        holder.tv_product_price.setPaintFlags(holder.tv_product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRs.setPaintFlags(holder.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ll_background_color.setBackground(context.getResources().getDrawable(product_color[position % 3]));

        holder.packagesName.setText(datumVocabularyModels.get(position).getPackName());
        holder.tvPackagesRating.setText(datumVocabularyModels.get(position).getRateStar());
        Glide.with(context).load(datumVocabularyModels.get(position).getPackImage().replace("http://","https://"))
                .placeholder(R.drawable.bg_white).into(holder.ivPackagesImage);

        holder.tv_product_price.setText(datumVocabularyModels.get(position).getPrice());

        if (datumVocabularyModels.get(position).getOfferPrice() != null && !datumVocabularyModels.get(position).getOfferPrice().isEmpty()
                && !datumVocabularyModels.get(position).getOfferPrice().equals("null") && !datumVocabularyModels.get(position).getOfferPrice().equals("")) {
            holder.tvPrice.setText(datumVocabularyModels.get(position).getOfferPrice());
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
        return datumVocabularyModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_background_color;
        TextView tv_product_price;
        TextView tvRs;
        TextView packagesName,tvPrice,tvPackagesRating;
        AppCompatImageView ivPackagesImage,ivAddToCart;
        RelativeLayout rlPackagesDetail;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            ll_background_color = itemView.findViewById(R.id.ll_background_color);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tvRs = itemView.findViewById(R.id.tvRs);
            packagesName = itemView.findViewById(R.id.packagesName);
            tvPackagesRating = itemView.findViewById(R.id.tvPackagesRating);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivPackagesImage = itemView.findViewById(R.id.ivPackagesImage);
            rlPackagesDetail = itemView.findViewById(R.id.rlPackagesDetail);
            ivAddToCart = itemView.findViewById(R.id.ivAddToCart);
        }
    }
}
