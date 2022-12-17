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
import com.theLearningcLub.Model_Class.EventSubModelClass;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

public class ChaptervideoAdapter2 extends RecyclerView.Adapter<ChaptervideoAdapter2.MyViewHolder> {

    private final List<EventSubModelClass> homevClassModels;
    private Context mcontext;
    int[] product_color = {R.drawable.bg_btn_pirple_1,
            R.drawable.bg_btn_orange_red,
            R.drawable.bg_btn_shed_green};
    FilterClick filterClick;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_background_color;
        TextView tv_product_price;
        TextView tvRs;
        TextView packagesName,tvPrice,tvPackagesRating;
        AppCompatImageView ivPackagesImage;
        RelativeLayout rlPackagesDetail;

        public MyViewHolder(View view) {
            super(view);
            mcontext=view.getContext();

            ll_background_color = itemView.findViewById(R.id.ll_background_color);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tvRs = itemView.findViewById(R.id.tvRs);
            packagesName = itemView.findViewById(R.id.packagesName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivPackagesImage = itemView.findViewById(R.id.ivPackagesImage);
            tvPackagesRating = itemView.findViewById(R.id.tvPackagesRating);
            rlPackagesDetail = itemView.findViewById(R.id.rlPackagesDetail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    filterClick.filterClick(getAdapterPosition());
                }
            });
        }
    }


    public ChaptervideoAdapter2(Context context, List<EventSubModelClass> moviesList,FilterClick filterClick) {
        this.homevClassModels = moviesList;
        this.mcontext = context;
        this.filterClick = filterClick;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.vocabulary_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        EventSubModelClass movie = homevClassModels.get(position);

        holder.tv_product_price.setPaintFlags(holder.tv_product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRs.setPaintFlags(holder.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ll_background_color.setBackground(mcontext.getResources().getDrawable(product_color[position % 3]));

        holder.packagesName.setText(movie.getName());
        holder.tvPackagesRating.setText(movie.getReting_bar());
        Glide.with(mcontext).load(homevClassModels.get(position).getImage_drawable().replace("http://","https://"))
                .placeholder(R.drawable.bg_white).into(holder.ivPackagesImage);

        holder.tv_product_price.setText(movie.getPrice());

        if (movie.getOfferprice() != null && !movie.getOfferprice().isEmpty()
                && !movie.getOfferprice().equals("null") && !movie.getOfferprice().equals("")) {
            holder.tvPrice.setText(movie.getOfferprice());
        }else {
            holder.tvPrice.setText(mcontext.getResources().getString(R.string.free));
        }
    }

    @Override
    public int getItemCount() {
        return homevClassModels.size();
    }
}
