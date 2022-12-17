package com.theLearningcLub.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.Model_Class.Testseriesmodel;
import com.theLearningcLub.R;
import com.theLearningcLub.utils.SessionManager;

import java.util.List;


public class Testseriesadapter extends RecyclerView.Adapter<Testseriesadapter.MyViewHolder> {

    SessionManager sessionManager;
    boolean iscolor = true;
    List<Testseriesmodel> moviesList;

    Context mcontext;
    int row_index = -1;
    String not_select = "2",select="1";


    private static final String[] tensNames = {
            "a",
            "b",
            "c",
            "d",
            "e",
            "f",
            "g",
            "h",
            "i",
            "j"
    };

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView title;
        LinearLayout ll_ans_1;

        public MyViewHolder(View view) {
            super(view);
            mcontext = view.getContext();

            title = view.findViewById(R.id.texview_item_rec);
            ll_ans_1 = view.findViewById(R.id.ll_ans_1);
        }
    }


    public Testseriesadapter(Context context, List<Testseriesmodel> moviesList) {
        this.moviesList = moviesList;
        this.mcontext = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quiz_ans_layout, parent, false);

        return new MyViewHolder(itemView);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBindViewHolder(final MyViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        sessionManager = new SessionManager(mcontext);
        try{
            final Testseriesmodel movie = moviesList.get(position);

             holder.title.setText(Html.fromHtml(movie.getTitle()));


            holder.title.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    sessionManager.setSavedNotAnswer(select);
                    System.out.println("prefManagerSecond.setSavedNotAnswer======     "+select);
                    row_index = position;
                    notifyDataSetChanged();

                }
            });

            if (row_index == position) {
                holder.ll_ans_1.setBackground(mcontext.getResources().getDrawable(R.drawable.bg_green));
                holder.title.setTextColor(mcontext.getResources().getColor(R.color.white));

                sessionManager.setSavedDATA(holder.title.getText().toString());

                sessionManager.setSavedANSWER_ID(movie.getYear());
                sessionManager.setSavedCORRECT_ID(movie.getYear());
                sessionManager.setSavedSTATUS(movie.getGenre());
                sessionManager.setQID(movie.getQuestion_id());

                System.out.println("Adapter set Selected value >>>>>>>>>  "+sessionManager.getSavedNotAnswer());
            } else {
                holder.ll_ans_1.setBackground(mcontext.getResources().getDrawable(R.drawable.bg_white));
                holder.title.setTextColor(Color.BLACK);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return moviesList.size();
    }
}