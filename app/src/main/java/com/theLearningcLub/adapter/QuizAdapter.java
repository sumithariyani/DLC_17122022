package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.R;
import com.theLearningcLub.model.quiz.DatumQuizModel;
import com.theLearningcLub.utils.FilterClick;

import java.util.List;

public class QuizAdapter extends RecyclerView.Adapter<QuizAdapter.MyHolder>{
    List<DatumQuizModel> datumQuizModels;
    Context context;
    FilterClick filterClick;
    int selectPosition = -1;

    public QuizAdapter(List<DatumQuizModel> datumQuizModels, Context context,FilterClick filterClick) {
        this.datumQuizModels = datumQuizModels;
        this.context = context;
        this.filterClick = filterClick;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_layout, parent, false);
        int height = parent.getMeasuredHeight() / 4;
        view.setMinimumHeight(height);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tv_package.setText(datumQuizModels.get(position).getPackName());

        holder.tv_package.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterClick.filterClick(position);
                selectPosition = position;
                notifyDataSetChanged();
                holder.ll_tv_pack.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_quiz_orange));
                holder.tv_package.setTextColor(context.getResources().getColor(R.color.white));
            }
        });

        if (selectPosition == position){
            holder.ll_tv_pack.setBackground(context.getResources().getDrawable(R.drawable.bg_btn_quiz_orange));
            holder.tv_package.setTextColor(context.getResources().getColor(R.color.white));
        }else {
            holder.ll_tv_pack.setBackground(context.getResources().getDrawable(R.drawable.bg_transpernt));
            holder.tv_package.setTextColor(context.getResources().getColor(R.color.black));
        }
    }

    @Override
    public int getItemCount() {
        return datumQuizModels.size();
    }

    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tv_package;
        LinearLayout ll_tv_pack;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tv_package = itemView.findViewById(R.id.tv_package);
            ll_tv_pack = itemView.findViewById(R.id.ll_tv_pack);
        }
    }
}
