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
import com.theLearningcLub.model.quizqus.AnswerQuizQusModel;

import java.util.List;

public class QuizAnswerAdapter extends RecyclerView.Adapter<QuizAnswerAdapter.MyHolder>{
    List<AnswerQuizQusModel> answerQuizQusModels;
    Context context;

    public QuizAnswerAdapter(List<AnswerQuizQusModel> answerQuizQusModels, Context context) {
        this.answerQuizQusModels = answerQuizQusModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_ans_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.texview_item_rec.setText(answerQuizQusModels.get(position).getAnswer());
    }

    @Override
    public int getItemCount() {
        return answerQuizQusModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        LinearLayout ll_myChapter;
        TextView texview_item_rec;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            ll_myChapter = itemView.findViewById(R.id.ll_myChapter);
            texview_item_rec = itemView.findViewById(R.id.texview_item_rec);
        }
    }
}
