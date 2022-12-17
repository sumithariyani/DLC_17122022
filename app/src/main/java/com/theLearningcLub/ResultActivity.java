package com.theLearningcLub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Range;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ResultActivity extends BaseActivity {
    String status,response,s_user_id,s_correct_ans,s_not_correct_ans,s_not_ans,user_status,percents;
    Button buttonresult;
    String s_update_id ;
    int sca;
    int snca;
    int sna;
    PieChart pieChart;
    JSONArray contacts;
    TextView txtremark;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_result);

        s_update_id = sessionManager.getSavedUPDATE_ID();
        s_user_id = sessionManager.getSavedUserId();

        user_status="3";

        new UserStatusTask().execute();
        new result_quiz_TASK().execute();

        buttonresult = findViewById(R.id.buttonresult);
        txtremark = findViewById(R.id.txtremark);
        pieChart = findViewById(R.id.piechart);


        buttonresult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in=new Intent(ResultActivity.this,HomeActivity.class);
                startActivity(in);
                finish();

            }
        });
    }
    @Override
    public void onBackPressed() {
        new androidx.appcompat.app.AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();
                    }
                }).setNegativeButton("No", null).show();
    }

    class result_quiz_TASK extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", s_user_id);
            hashMap.put("update_id",s_update_id);
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.get_introduction_scorecardApi, 2, hashMap);

            System.out.println("get_introduction_scorecard Response >>>>>>>>>>>>>>>>>>>>>>> " + response);
            System.out.println("get_introduction_scorecard Parameter >>>>>>>>>>>>>>>>>>>>>> " + hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");

                contacts = jsonObject.getJSONArray("datatwo");

                if(contacts == null && contacts.length() <= 0) {
                    alertdialogbox();
                } else {
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        s_correct_ans = c.getString("correct_answer");
                        s_not_correct_ans = c.getString("wrong_answer");
                        s_not_ans = c.getString("not_attamp_question");
                        percents = c.getString("percents");

                        sca = Integer.parseInt(s_correct_ans);
                        snca = Integer.parseInt(s_not_correct_ans);
                        sna = Integer.parseInt(s_not_ans);

                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        protected void onPostExecute (String s){
            super.onPostExecute(s);
            hideProgressDialog();
            try {

                float notans,notcorrans,corrans;
               // notans= Float.parseFloat(String.valueOf(sna));
                notcorrans = Float.parseFloat(String.valueOf(snca));
               corrans = Float.parseFloat(String.valueOf(sca));

               ArrayList<Entry> yvalues = new ArrayList<Entry>();
              //  yvalues.add(new Entry(notans, 2)); //not Answer
                yvalues.add(new Entry(notcorrans, 1)); //not Correct Answer
                yvalues.add(new Entry(corrans, 0)); //correct Answer

                PieDataSet dataSet = new PieDataSet(yvalues, "");

                ArrayList<String> xVals = new ArrayList<String>();

                xVals.add("");
                xVals.add("");
              //  xVals.add("");
                PieData data = new PieData(xVals, dataSet);
                dataSet.setColors(new int[]{getResources().getColor(R.color.color_red)},getResources().getColor(R.color.color_app_orange));
                pieChart.setDrawHoleEnabled(true);
                pieChart.setTransparentCircleRadius(30f);
                pieChart.setHoleRadius(30f);
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.WHITE);
                data.setValueFormatter(new PercentFormatter());
                pieChart.setData(data);
                pieChart.notifyDataSetChanged();
                pieChart.invalidate();
                pieChart.isShown();
                pieChart.setUsePercentValues(true);

                String percen = percents.split("\\.")[0];

                System.out.println(" percen before dot      "+percen);

                int get_per= Integer.parseInt(percen);

                if (new Range<Integer>(0, 40).contains(get_per)){
                    System.out.println("percen inside 0 to 40      "+percen);

                    txtremark.setText("Need Immediate Improvement");

                }else if (new Range<Integer>(40, 60).contains(get_per)){
                    System.out.println("percen inside 40 to 60      "+percen);

                    txtremark.setText("Need  Improvement");
                }else if (new Range<Integer>(60, 90).contains(get_per)){
                    System.out.println("percen inside 60 to 90     "+percen);
                    txtremark.setText("Good");
                }else if (new Range<Integer>(90, 100).contains(get_per)){
                    System.out.println("percen inside 90 to 100    "+percen);
                    txtremark.setText("Excellent");
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public void alertdialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(ResultActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.desc_custom_dialog,null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        Button btn_positive = dialogView.findViewById(R.id.btn_ok_desc);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.setnokey("0");
                Intent in=new Intent(ResultActivity.this,HomeActivity.class);
                startActivity(in);
                dialog.dismiss();
            }
        });
        dialog.show();
    }
    class UserStatusTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("user_status", user_status);
            hashMap.put("key", sessionManager.getLoginkey());
            // hashMap.put("contact",allcontact);
            response = webRequest.makeWebServiceCall(AllUrl.update_user_statusApi, 2, hashMap);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
