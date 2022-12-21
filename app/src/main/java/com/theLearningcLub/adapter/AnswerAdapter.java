package com.theLearningcLub.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.Model_Class.Models.QsOption;
import com.theLearningcLub.R;

import java.util.List;

public class AnswerAdapter extends RecyclerView.Adapter<AnswerAdapter.ViewHolder> {

    public AnswerAdapter(Context context,String currect_ans,String iscurrect, List<QsOption> list) {
        this.context = context;
        this.list = list;
        this.currect_ans = currect_ans;
        this.iscurrect = iscurrect;
    }

    List<QsOption> list;
    Context context;
    String currect_ans;
    String iscurrect;

    @NonNull
    @Override
    public AnswerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.answer_layout, parent, false);
        return new AnswerAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnswerAdapter.ViewHolder holder, int position) {
        QsOption model = list.get(position);

        if(iscurrect.equals("2")){
            if(!model.getQuizStatus().equals("") && !model.getQuizStatus().equals("0")) {
                holder.textView.setText(model.getQuizAnswer() + " (Skipped)");
            }else{
                holder.textView.setText(model.getQuizAnswer());
            }
        }else{
            holder.textView.setText(model.getQuizAnswer());
        }

//        Log.e("TAG", "onBindViewHolder: "+model.getQuizStatus() );

        if (iscurrect.equals("0")){
            if (currect_ans.equals(model.getQuizAnswer())){
                holder.textView.setTextColor(holder.textView.getContext().getResources().getColor(R.color.color_red));
            }
        }
        if(!model.getQuizStatus().equals("") && !model.getQuizStatus().equals("0")){
            holder.textView.setTextColor(holder.textView.getContext().getResources().getColor(R.color.color_green_ans));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.answerQuizQusModel);
        }
    }
}
