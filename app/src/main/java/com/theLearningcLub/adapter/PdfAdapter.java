package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.PDFModel;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class PdfAdapter extends RecyclerView.Adapter<PdfAdapter.MyHolder>{
    List<PDFModel> pdfModels;
    Context context;
    FilterClick filterClick;

    public PdfAdapter(List<PDFModel> pdfModels, Context context,FilterClick filterClick) {
        this.pdfModels = pdfModels;
        this.context = context;
        this.filterClick = filterClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pdf_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvPdfName.setText(pdfModels.get(position).getPdf_name());
        holder.tvPdfDate.setText(pdfModels.get(position).getDate());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClick.filterClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return pdfModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tvPdfName,tvPdfDate;
        AppCompatImageView ivPdfImage;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvPdfName = itemView.findViewById(R.id.tvPdfName);
            tvPdfDate = itemView.findViewById(R.id.tvPdfDate);
            ivPdfImage = itemView.findViewById(R.id.ivPdfImage);
        }
    }

}
