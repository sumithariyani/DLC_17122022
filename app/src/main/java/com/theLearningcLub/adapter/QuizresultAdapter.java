package com.theLearningcLub.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.Model_Class.Models.Body;
import com.theLearningcLub.R;

import java.util.List;

public class QuizresultAdapter extends RecyclerView.Adapter<QuizresultAdapter.ViewHolder> {
    AnswerAdapter adapter;
    Context context;

    public QuizresultAdapter(Context context, List<Body> list) {
        this.context = context;
        this.list = list;
    }
    List<Body> list;

    @Override
    public QuizresultAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.quiz_result_layout, parent, false);
        return new QuizresultAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QuizresultAdapter.ViewHolder holder, int position) {
//        holder.question.setTextColor();
        Body model = list.get(position);
        int count = position+1;


        holder.question.setText("Q."+count+" "+model.getQQus());
        adapter = new AnswerAdapter(context,model.getQOption(),model.getIsCorrect(),model.getQsOptions());
        holder.answerRecycler.setAdapter(adapter);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView question;
        RecyclerView answerRecycler;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            question = itemView.findViewById(R.id.question_tv);
            answerRecycler = itemView.findViewById(R.id.answerRecycler);

        }
    }
}
