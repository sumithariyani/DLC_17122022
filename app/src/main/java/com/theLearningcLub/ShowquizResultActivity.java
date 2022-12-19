package com.theLearningcLub;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;

import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.FragmentTransaction;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theLearningcLub.Model_Class.Models.Body;
import com.theLearningcLub.Model_Class.Models.QsOption;
import com.theLearningcLub.Model_Class.Models.QuizresultModel;
import com.theLearningcLub.adapter.QuizresultAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityShowquizResultBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowquizResultActivity extends BaseActivity implements View.OnClickListener{

    ActivityShowquizResultBinding activityShowquizResultBinding;
    List<Body> bodylist = new ArrayList<>();
    List<QsOption> optionlist = new ArrayList<>();
    QuizresultAdapter quizresultAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityShowquizResultBinding = DataBindingUtil.setContentView(mContext,R.layout.activity_showquiz_result);

        activityShowquizResultBinding.ivBack.setOnClickListener(this);
        activityShowquizResultBinding.ivSearch.setOnClickListener(this);
        activityShowquizResultBinding.ivCart.setOnClickListener(this);

        quizresultAdapter = new QuizresultAdapter(mContext,bodylist);
        activityShowquizResultBinding.rvReviewQuiz.setAdapter(quizresultAdapter);

        quizResultApi();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.ivSearch){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new SearchActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
        }else if (view.getId() == R.id.ivCart){
            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,new MyCartActivity(),"myPackages");
            fragmentTransaction.addToBackStack("myPackages");
            fragmentTransaction.commit();
        }else if (view.getId() == R.id.ivBack){
            onBackPressed();
        }
    }

    private void quizResultApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.quiz_all_infoApi,
                    response -> {
                        // Display the first 500 characters of the response string.
//                        Log.d("INFO", response);
//                      Log.e(TAG, "onResponse:login "+response );
                        System.out.println("quizResultApi response>>>>>>>>>2>>>>>>" + response);
                        try {
                            if (response != null) {
                                QuizresultModel categoryModel = new Gson().fromJson(response, QuizresultModel.class);
                                if (categoryModel.getMsg().equals("success")) {
                                    for (int i = 0; i < categoryModel.getBody().size(); i++) {
                                        Body body = categoryModel.getBody().get(i);
                                        bodylist.add(body);
                                        for(int j = 0; j < bodylist.get(i).getQsOptions().size(); j++) {
                                            QsOption answer = bodylist.get(i).getQsOptions().get(j);
                                            optionlist.add(answer);
                                        }
                                    }
                                    quizresultAdapter.notifyDataSetChanged();
                                }
                            }
                            hideProgressDialog();

                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("quizResultApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("quiz_id", sessionManager.getSavedPackId2());
                    params.put("user_id", sessionManager.getSavedUserId());
                    System.out.println("quizResultApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(ShowquizResultActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

}