package com.theLearningcLub.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.R;
import com.theLearningcLub.model.historyview.BodyHistoryViewModel;

import java.util.List;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.MyHolder>{
    List<BodyHistoryViewModel> bodyHistoryViewModels;
    Context context;

    public HistoryAdapter(List<BodyHistoryViewModel> bodyHistoryViewModels, Context context) {
        this.bodyHistoryViewModels = bodyHistoryViewModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.history_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.tvHistoryDes.setText(bodyHistoryViewModels.get(position).getMessage());
        holder.tvFirstName.setText(bodyHistoryViewModels.get(position).getName());
        holder.tvAnswerHistory.setText("DLC : "+bodyHistoryViewModels.get(position).getFeedback());

        if (bodyHistoryViewModels.get(position).getFeedback() != null
                && (!TextUtils.equals(bodyHistoryViewModels.get(position).getFeedback(), "null"))
                && (!TextUtils.isEmpty(bodyHistoryViewModels.get(position).getFeedback()))) {

            holder.tvAnswerHistory.setVisibility(View.VISIBLE);
        }else {
            holder.tvAnswerHistory.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return bodyHistoryViewModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tvFirstName,tvHistoryDes,tvAnswerHistory;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvHistoryDes = itemView.findViewById(R.id.tvHistoryDes);
            tvFirstName = itemView.findViewById(R.id.tvFirstName);
            tvAnswerHistory = itemView.findViewById(R.id.tvAnswerHistory);

        }
    }
}
