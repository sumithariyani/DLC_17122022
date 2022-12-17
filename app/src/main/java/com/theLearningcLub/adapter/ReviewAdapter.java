package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.Review_ClassModel;
import com.theLearningcLub.R;
import com.theLearningcLub.model.review.DatumReviewModel;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyHolder>{
    List<DatumReviewModel> datumReviewModels;
    Context context;

    public ReviewAdapter(List<DatumReviewModel> datumReviewModels, Context context) {
        this.datumReviewModels = datumReviewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.review_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {

        holder.tvUserName.setText(datumReviewModels.get(position).getUser());
        holder.tvUserDate.setText(datumReviewModels.get(position).getRatingDate());
        holder.tvUserDescription.setText(datumReviewModels.get(position).getComments());
        holder.rbUserRating.setRating(Float.parseFloat(datumReviewModels.get(position).getUserRating()));
        Glide.with(context).load(datumReviewModels.get(position).getUserimage()
                .replace("http://","https://"))
                .placeholder(R.drawable.ic_profile_image).into(holder.ivUserImage);
    }

    @Override
    public int getItemCount() {
        return datumReviewModels.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        CircleImageView ivUserImage;
        AppCompatTextView tvUserName,tvUserDate,tvUserDescription;
        RatingBar rbUserRating;

        public MyHolder(@NonNull View itemView) {
            super(itemView);
            tvUserDescription = itemView.findViewById(R.id.tvUserDescription);
            ivUserImage = itemView.findViewById(R.id.ivUserImage);
            tvUserName = itemView.findViewById(R.id.tvUserName);
            tvUserDate = itemView.findViewById(R.id.tvUserDate);
            rbUserRating = itemView.findViewById(R.id.rbUserRating);
        }
    }
}
