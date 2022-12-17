package com.theLearningcLub.adapter;

import android.content.Context;
import android.content.Intent;
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
import com.theLearningcLub.Model_Class.ClassModel;
import com.theLearningcLub.PackageActivity;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.SessionManager;

import java.util.List;

public class ChaptervideoAdapter extends RecyclerView.Adapter<ChaptervideoAdapter.MyViewHolder> {

    private final List<ClassModel> homevClassModels;
    private Context mcontext;
    SessionManager sessionManager;
    int[] product_color = {R.drawable.bg_btn_blue,
            R.drawable.bg_btn_pink,
            R.drawable.bg_btn_green};


    public class MyViewHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_background_color;
        TextView tv_product_price;
        TextView tvRs;
        TextView packagesName,tvPrice;
        AppCompatImageView ivPackagesImage;
        RelativeLayout rlPackagesDetail;

        public MyViewHolder(View view) {
            super(view);
            mcontext = view.getContext();
            sessionManager = new SessionManager(mcontext);

            ll_background_color = itemView.findViewById(R.id.ll_background_color);
            tv_product_price = itemView.findViewById(R.id.tv_product_price);
            tvRs = itemView.findViewById(R.id.tvRs);
            packagesName = itemView.findViewById(R.id.packagesName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            ivPackagesImage = itemView.findViewById(R.id.ivPackagesImage);
            rlPackagesDetail = itemView.findViewById(R.id.rlPackagesDetail);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(mcontext, PackageActivity.class);
                    intent.putExtra("image",homevClassModels.get(getAdapterPosition()).getImage_drawable());
                    intent.putExtra("title",homevClassModels.get(getAdapterPosition()).getName());
                    intent.putExtra("price",homevClassModels.get(getAdapterPosition()).getPrice());
                    intent.putExtra("offerprice",homevClassModels.get(getAdapterPosition()).getOfferprice());
                    intent.putExtra("Desc",homevClassModels.get(getAdapterPosition()).getDesc());
                    intent.putExtra("packid",homevClassModels.get(getAdapterPosition()).getPack_id());
                    intent.putExtra("userid",homevClassModels.get(getAdapterPosition()).getUserid());
                    intent.putExtra("is_free",homevClassModels.get(getAdapterPosition()).getIs_free());
                    intent.putExtra("rating",homevClassModels.get(getAdapterPosition()).getReting_bar());
                    intent.putExtra("key","1");
                    mcontext.startActivity(intent);
                }
            });
        }
    }


    public ChaptervideoAdapter(Context context, List<ClassModel> moviesList) {
        this.homevClassModels = moviesList;
        this.mcontext = context;
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

        ClassModel movie = homevClassModels.get(position);

        holder.tv_product_price.setPaintFlags(holder.tv_product_price.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.tvRs.setPaintFlags(holder.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        holder.ll_background_color.setBackground(mcontext.getResources().getDrawable(product_color[position % 3]));

        holder.packagesName.setText(movie.getName());
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