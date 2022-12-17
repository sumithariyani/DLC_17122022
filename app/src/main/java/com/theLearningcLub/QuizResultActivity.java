package com.theLearningcLub;

import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityQuizResultBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

public class QuizResultActivity extends BaseActivity implements View.OnClickListener {

    ActivityQuizResultBinding activityQuizResultBinding;

    String s_update_id,status,response,s_user_id,s_correct_ans,s_not_correct_ans,s_not_ans,
            s_rating="0",s_comments,message="",user_status,percents;
    int sca;

    int snca;
    int sna;
    JSONArray contacts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityQuizResultBinding = DataBindingUtil.setContentView(mContext,R.layout.activity_quiz_result);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.quiz_result));

        activityQuizResultBinding.llRate.setOnClickListener(this);
        activityQuizResultBinding.llQuizReview.setOnClickListener(this);
        activityQuizResultBinding.ivCloss.setOnClickListener(this);

        s_update_id = sessionManager.getSavedUPDATE_ID();
        s_user_id = sessionManager.getSavedUserId();

        user_status="4";

        new UserStatusTask().execute();

        new result_quiz_TASK().execute();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivCloss){
            onBackPressed();
        }else if (view.getId() == R.id.llRate){
            startActivity(new Intent(mContext,RateActivity.class));
//            popupalertdialogbox();
        }else if (view.getId() == R.id.llQuizReview){
            startActivity(new Intent(mContext,ShowquizResultActivity.class));
        }
    }

    public void alertdialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizResultActivity.this);
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
                Intent in = new Intent(QuizResultActivity.this,HomeActivity.class);
                startActivity(in);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public void popupalertdialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(QuizResultActivity.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.rating_custom_dialog,null);
        builder.setCancelable(true);
        builder.setView(dialogView);

        Button btn_positive = dialogView.findViewById(R.id.btnsubmit);

        final EditText et_oldpassword = dialogView.findViewById(R.id.popup_edt_comments);
        final RatingBar popup_ratingbar1= dialogView.findViewById(R.id.popup_ratingbar1);

        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s_comments = et_oldpassword.getText().toString();
                s_rating = String.valueOf(popup_ratingbar1.getRating());

                if(s_comments.isEmpty()){
                    et_oldpassword.setError("Please Enter Comments.");
                } else {
                    new AddRatingTask().execute();
                }
                dialog.dismiss();
            }
        });


        dialog.show();
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

            // response = webRequest.makeWebServiceCall(Allurls.MainURL+"get_introduction_scorecard.php", 2, hashMap);
            response = webRequest.makeWebServiceCall(AllUrl.open_get_quiz_answerApi,2,hashMap);

            System.out.println("Result Response >>>>>>>>>>>>>>>>>>>>>>>" + response);
            System.out.println("Result Parameter >>>>>>>>>>>>>>>>>>>>>>" + hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");

                contacts = jsonObject.getJSONArray("datatwo");

                if(contacts == null && contacts.length() <= 0) {
                    alertdialogbox();
                }
                else
                {
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);
                        s_correct_ans = c.getString("correct_answer");
                        s_not_correct_ans = c.getString("wrong_answer");
                        s_not_ans = c.getString("not_attamp_question");
                        percents = c.getString("percents");

                             System.out.println("Corrct Answer >>>>>>>>>>>>>>>>>>>>" + s_correct_ans);
                            System.out.println("NOT Corrct Answer >>>>>>>>>>>>>>>>>>>>" + s_not_correct_ans);
                           System.out.println("NOT Answer >>>>>>>>>>>>>>>>>>>>" + s_not_ans);


                        sca = Integer.parseInt(s_correct_ans);
                        snca = Integer.parseInt(s_not_correct_ans);
                        sna = Integer.parseInt(s_not_ans);

                        //  System.out.println("Corrct Answer >>>>>>>>>>>>>>>>>>>>" + sca);
                        //   System.out.println("NOT Corrct Answer >>>>>>>>>>>>>>>>>>>>" + snca);
                        //  System.out.println("NOT Answer >>>>>>>>>>>>>>>>>>>>" + sna);

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
                activityQuizResultBinding.tvCorrect.setText(s_correct_ans);
                activityQuizResultBinding.Skipped.setText(s_not_ans);
                activityQuizResultBinding.tvIncorrect.setText(s_not_correct_ans);


                notcorrans = Float.parseFloat(String.valueOf(snca));
                corrans = Float.parseFloat(String.valueOf(sca));
                notans = Float.parseFloat(String.valueOf(sna));
                System.out.println("notcorrans >>>>>>>>>>>>>>>>>>>>  " + notcorrans);
                System.out.println("corrans >>>>>>>>>>>>>>>>>>>>  " + corrans);
                System.out.println("notans >>>>>>>>>>>>>>>>>>>>  " + notans);

                ArrayList<Entry> yvalues = new ArrayList<Entry>();
                ArrayList<String> xVals = new ArrayList<String>();

                PieDataSet dataSet = new PieDataSet(yvalues, "");



//                xVals.add("");
//                xVals.add("");


                if (snca != 0) {
                    yvalues.add(new Entry(notcorrans, 0)); //correct Answer
                    xVals.add("");

                }
                if (sca != 0) {

                    yvalues.add(new Entry(corrans, 1)); //not Correct Answer
                    xVals.add("");
                }
                if (sna != 0) {
                    yvalues.add(new Entry(notans, 2)); //not Answer

                    xVals.add("");
                }

                PieData data = new PieData(xVals, dataSet);
                if (snca != 0 && sca != 0 && sna != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_red),getColor(R.color.color_green_ans),getColor(R.color.color_orange_1)});
                }else if (snca != 0 && sca != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_red),getColor(R.color.color_green_ans)});
                }else if(sca != 0 && sna != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_green_ans),getColor(R.color.color_orange_1)});
                } else if (snca != 0 && sna != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_red),getColor(R.color.color_orange_1)});
                } else if (snca != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_red)});
                } else if (sca != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_green_ans)});
                } else if (sna != 0) {
                    dataSet.setColors(new int[]{getColor(R.color.color_orange_1)});
                }

                activityQuizResultBinding.pieChart.setDrawHoleEnabled(true);
                activityQuizResultBinding.pieChart.setTransparentCircleRadius(30f);
                activityQuizResultBinding.pieChart.setHoleRadius(30f);
                data.setValueTextSize(20f);
                data.setValueTextColor(Color.WHITE);
                data.setValueFormatter(new PercentFormatter(new DecimalFormat("###,###,###")));
                activityQuizResultBinding.pieChart.setData(data);
                activityQuizResultBinding.pieChart.notifyDataSetChanged();
                activityQuizResultBinding.pieChart.invalidate();
                activityQuizResultBinding.pieChart.isShown();
                activityQuizResultBinding.pieChart.setUsePercentValues(true);
                activityQuizResultBinding.pieChart.setDescription("");

                activityQuizResultBinding.ivShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DLC ");

                        String shareMessage = "Quiz Name : "+sessionManager.getPackname2()
                                +"\nCorrect : "+sca
                                +"\nIncorrect : "+snca
                                +"\nSkipped : "+sna
                                +"\nDownload now and check your scores from https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                        shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                        startActivity(Intent.createChooser(shareIntent, "choose one"));
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    class AddRatingTask extends  AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("pack_id",sessionManager.getSavedPackId2());
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("comment",s_comments);
            hashMap.put("rateStar",s_rating);
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.add_ratingApi,2,hashMap);

            System.out.println("Add Rating Reaponse >>>>>>>>>>"+response);
            System.out.println("Add Rating parameter >>>>>>>>>"+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                status = jsonObject1.getString("status");
                message = jsonObject1.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            CommonFunction.showToastSingle(QuizResultActivity.this,message+"");
        }
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
            response = webRequest.makeWebServiceCall(AllUrl.update_user_statusApi, 2, hashMap);

            System.out.println("user status Response >>>>>>>>>>>>>> " + response);
            System.out.println("user status parameter >>>>>>>>>>>>>> " + hashMap);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}