package com.theLearningcLub.adapter;

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
import com.theLearningcLub.Model_Class.ClassModel1;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.FilterClickCart;

import java.util.List;

public class ChaptervideoAdapter1New extends RecyclerView.Adapter<ChaptervideoAdapter1New.MyViewHolder> {

    private final List<ClassModel1> homevClassModels;
    private Context mcontext;
    int[] product_color = {R.drawable.bg_btn_blue,
            R.drawable.bg_btn_pink,
            R.drawable.bg_btn_green};
    FilterClick filterClick;
    FilterClickCart filterClickCart;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_background_color;
        TextView tv_product_price;
        TextView tvRs;
        TextView packagesName,tvPrice,tvPackagesRating;
        AppCompatImageView ivPackagesImage,ivAddToCart;
        RelativeLayout rlPackagesDetail;

        public MyViewHolder(View view) {
            super(view);
            mcontext = view.getContext();

            ll_background_color = itemView.findViewById(R.id.ll_background_color);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tvRs = itemView.findViewById(R.id.tvRs);
            packagesName = itemView.findViewById(R.id.packagesName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivPackagesImage = itemView.findViewById(R.id.ivPackagesImage);
            tvPackagesRating = itemView.findViewById(R.id.tvPackagesRating);
            rlPackagesDetail = itemView.findViewById(R.id.rlPackagesDetail);
            ivAddToCart = itemView.findViewById(R.id.ivAddToCart);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterClick.filterClick(getAdapterPosition());
                }
            });

            ivAddToCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterClickCart.filterClickCart(getAdapterPosition());
                }
            });


        }
    }


    public ChaptervideoAdapter1New(Context context, List<ClassModel1> moviesList, FilterClick filterClick, FilterClickCart filterClickCart) {
        this.homevClassModels = moviesList;
        this.mcontext = context;
        this.filterClick = filterClick;
        this.filterClickCart = filterClickCart;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocabulary_layout2, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ClassModel1 movie = homevClassModels.get(position);

        holder.tv_product_price.setPaintFlags(holder.tv_product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRs.setPaintFlags(holder.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ll_background_color.setBackground(mcontext.getResources().getDrawable(product_color[position % 3]));

        holder.packagesName.setText(movie.getName());
        holder.tvPackagesRating.setText(movie.getReting_bar());
        Glide.with(mcontext).load(homevClassModels.get(position).getImage_drawable()
                        .replace("http://","https://"))
                .placeholder(R.drawable.bg_white).into(holder.ivPackagesImage);

        holder.tv_product_price.setText(movie.getPrice());

        if (movie.getOfferprice() != null && !movie.getOfferprice().isEmpty()
                && !movie.getOfferprice().equals("null") && !movie.getOfferprice().equals("")) {
            holder.tvPrice.setText(movie.getOfferprice());
            holder.ivAddToCart.setVisibility(View.VISIBLE);
        }else {
            holder.ivAddToCart.setVisibility(View.GONE);
            holder.tvPrice.setText(mcontext.getResources().getString(R.string.free));
        }
    }

    @Override
    public int getItemCount() {
        return homevClassModels.size();
    }
}
