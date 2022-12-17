package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.R;
import com.theLearningcLub.model.agentreport.BodyAgentReportModel;

import java.util.List;

public class AgentDetailAdapter extends RecyclerView.Adapter<AgentDetailAdapter.MyHolder>{
    List<BodyAgentReportModel> bodyAgentReportModels;
    Context context;

    public AgentDetailAdapter(List<BodyAgentReportModel> bodyAgentReportModels, Context context) {
        this.bodyAgentReportModels = bodyAgentReportModels;
        this.context = context;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.agent_detail_layout, parent, false);
        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.tvName.setText(bodyAgentReportModels.get(position).getLevelAgentName());
        holder.tvRank.setText(bodyAgentReportModels.get(position).getLevel());
    }

    @Override
    public int getItemCount() {
        return bodyAgentReportModels.size();
    }
    public static class MyHolder extends RecyclerView.ViewHolder {

        TextView tvName,tvRank;

        public MyHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            tvRank = itemView.findViewById(R.id.tvRank);
        }
    }
}
