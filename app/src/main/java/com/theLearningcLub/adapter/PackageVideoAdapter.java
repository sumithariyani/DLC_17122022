package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.Purachase_package_free_video_Model;
import com.theLearningcLub.R;
import com.theLearningcLub.model.videolist.VidDatumVideoListModel;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

public class PackageVideoAdapter extends RecyclerView.Adapter<PackageVideoAdapter.MyHolder>{
    List<Purachase_package_free_video_Model> purachase_package_free_video_models;
    Context context;
    FilterClick filterClick;
    String isfree;

    public PackageVideoAdapter(String isfree,List<Purachase_package_free_video_Model> purachase_package_free_video_models, Context context,FilterClick filterClick) {
        this.purachase_package_free_video_models = purachase_package_free_video_models;
        this.context = context;
        this.filterClick = filterClick;
        this.isfree=isfree;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.package_video_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvVideoName.setText(purachase_package_free_video_models.get(position).getVideo_title());
        holder.tvDateVideo.setText(purachase_package_free_video_models.get(position).getVideo_date());
        Glide.with(context).load(purachase_package_free_video_models.get(position).getVideo_image()
                .replace("http://","https://")).into(holder.ivVideoImage);

        try{
            if(isfree.equals("0")){
                if (purachase_package_free_video_models.get(position).getIs_free().equals("0")){
                    holder.ivVideoPack.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_1));
                }else /*(purachase_package_free_video_models.get(position).getIs_free().equals("1"))*/{

                    holder.ivVideoPack.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_open));
                }
            }else{
                holder.ivVideoPack.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_lock_open));
            }
        }catch (Exception e){
            System.out.println("s_is_free========= >>>>>>>>>>>> 5  "+e.toString());
        }

        try {

            // Changed from int totalvalue to long totalvalue
            long totalvalue = Integer.parseInt(purachase_package_free_video_models.get(position).getVideototal_duration());
            int viewvalue =0;
            if(purachase_package_free_video_models.get(position).getVideoview_Time() !="-9.22337e15"){
                 viewvalue = Integer.parseInt(purachase_package_free_video_models.get(position).getVideoview_Time());
            }
            if(totalvalue!=0 && viewvalue != 0){
                long parcentvalue = (((viewvalue) * 100)/(totalvalue))*100;
//                Log.e("TAG", "onBindViewHolderolder: "+parcentvalue);
                holder.view_filter.getBackground().setLevel((int) parcentvalue);

            }else {
                holder.view_filter.getBackground().setLevel(0);
            }
        } catch (Exception exception) {
                System.out.println("Error on Packagevideoadapter long value :" + exception.toString());
        }


        holder.llPackagesView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                filterClick.filterClick(position);

            }
        });
    }

    @Override
    public int getItemCount() {
        return purachase_package_free_video_models.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        AppCompatTextView tvVideoName,tvDateVideo;
        AppCompatImageView ivVideoPack;
        AppCompatImageView ivVideoImage;
        LinearLayout llPackagesView;
        View view_filter;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvVideoName = itemView.findViewById(R.id.tvVideoName);
            ivVideoPack = itemView.findViewById(R.id.ivVideoPack);
            tvDateVideo = itemView.findViewById(R.id.tvDateVideo);
            ivVideoImage = itemView.findViewById(R.id.ivVideoImage);
            llPackagesView = itemView.findViewById(R.id.llPackagesView);
            view_filter = itemView.findViewById(R.id.view_filter);
        }
    }
}
