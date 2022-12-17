package com.theLearningcLub;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.flexbox.FlexDirection;
import com.google.android.flexbox.FlexboxLayoutManager;
import com.google.android.flexbox.JustifyContent;
import com.google.gson.Gson;
import com.theLearningcLub.adapter.QuizAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityQuizBinding;
import com.theLearningcLub.model.quiz.QuizModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class QuizActivity extends BaseFragment implements View.OnClickListener{

    ActivityQuizBinding activityQuizBinding;
    QuizAdapter quizAdapter;
    int selectDataPosition = -1;
    String selectId;
    QuizModel quizModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityQuizBinding = ActivityQuizBinding.inflate(inflater,container,false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.quiz));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);

        PurachasePackagesApi();

        activityQuizBinding.tvPayNowBtn.setOnClickListener(this);

        FlexboxLayoutManager layoutManager = new FlexboxLayoutManager(mContext);
        layoutManager.setFlexDirection(FlexDirection.ROW);
        layoutManager.setJustifyContent(JustifyContent.FLEX_START);
        activityQuizBinding.rvQuiz.setLayoutManager(layoutManager);

        return activityQuizBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvPayNow_btn){
            if (TextUtils.isEmpty(selectId)){
                Toast.makeText(mContext, "Please select quiz", Toast.LENGTH_SHORT).show();
            }else {
                Intent intent = new Intent(mContext,Activity_Quiz_Main.class);
                intent.putExtra("pack_id",quizModel.getData().get(selectDataPosition).getPackId());
                intent.putExtra("pack_name",quizModel.getData().get(selectDataPosition).getPackName());

                sessionManager.setKEY_moretest("1");
                sessionManager.setPackname2(quizModel.getData().get(selectDataPosition).getPackName());
                sessionManager.setSavedPackId2(quizModel.getData().get(selectDataPosition).getPackId());

                startActivity(intent);
            }
        }
    }

    private void PurachasePackagesApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.open_quizApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("PurachasePackagesApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            quizModel = new Gson().fromJson(response,QuizModel.class);
                            if (quizModel.getStatus().equalsIgnoreCase(AllUrl.status)){
                                quizAdapter = new QuizAdapter(quizModel.getData(), mContext, position -> {
                                    selectDataPosition = position;
                                    selectId = quizModel.getData().get(position).getPackId();
                                });
                                activityQuizBinding.rvQuiz.setAdapter(quizAdapter);
                            }
                            hideProgressDialog();
                        }catch (Exception ex){
                            hideProgressDialog();
                            ex.printStackTrace();
                        }

                    }, error -> {
                System.out.println("PurachasePackagesApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("PurachasePackagesApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(getActivity(), NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }
}