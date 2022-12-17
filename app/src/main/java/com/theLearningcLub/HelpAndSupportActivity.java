package com.theLearningcLub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.theLearningcLub.adapter.HistoryAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityHelpAndSupportBinding;
import com.theLearningcLub.model.historyview.HistoryViewModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HelpAndSupportActivity extends BaseFragment {

    ActivityHelpAndSupportBinding activityHelpAndSupportBinding;
    HistoryAdapter historyAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityHelpAndSupportBinding = ActivityHelpAndSupportBinding.inflate(inflater,container,false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.help_and_support));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);

        activityHelpAndSupportBinding.btnHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String enter_msg = Objects.requireNonNull(activityHelpAndSupportBinding.etHelpMessage.getText()).toString();
                if(enter_msg.isEmpty()) {
                    activityHelpAndSupportBinding.etHelpMessage.setError("Please Enter Message.");
                }else {
                    new HelpNSupportTask().execute(enter_msg);
                }
            }
        });

        activityHelpAndSupportBinding.tvHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonFunction.hideKeyboardFrom(mContext,view);
                activityHelpAndSupportBinding.tvHelpHistory.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn_agent_grey));
                activityHelpAndSupportBinding.tvHelp.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn));
                activityHelpAndSupportBinding.llHelpSupport.setVisibility(View.VISIBLE);
                activityHelpAndSupportBinding.llHistory.setVisibility(View.GONE);
            }
        });

        activityHelpAndSupportBinding.tvHelpHistory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CommonFunction.hideKeyboardFrom(mContext,view);
                activityHelpAndSupportBinding.tvHelpHistory.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn));
                activityHelpAndSupportBinding.tvHelp.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn_agent_grey));
                activityHelpAndSupportBinding.llHelpSupport.setVisibility(View.GONE);
                activityHelpAndSupportBinding.llHistory.setVisibility(View.VISIBLE);

                ViewHistoryApi();
            }
        });

        return activityHelpAndSupportBinding.getRoot();
    }

    class HelpNSupportTask extends AsyncTask<String, String, String> {

        String status,msg;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            String enter_msg = strings[0];

            WebRequest webRequest = new WebRequest();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("name", sessionManager.getSavedNAME());
            hashMap.put("email", sessionManager.getSavedEMAIL());
            hashMap.put("contact", sessionManager.getContact());
            hashMap.put("message",enter_msg );
            hashMap.put("key", sessionManager.getLoginkey());

            String response = webRequest.makeWebServiceCall(AllUrl.add_help_supportApi, 2, hashMap);

            System.out.println("user check Response >>>>>>>>>>>>>>   " + response);
            System.out.println("user check parameter >>>>>>>>>>>>>   " + hashMap);

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");

                status = jsonObject1.getString("status");
                msg = jsonObject1.getString("msg");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (status.equals("true")) {
                    CommonFunction.showToastSingle(mContext, msg);
                    activityHelpAndSupportBinding.etHelpMessage.setText("");
                } else {
                    CommonFunction.showToastSingle(mContext, msg);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //-----------------------View History-------------------------------

    private void ViewHistoryApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.view_help_supportApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("ViewHistoryApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            HistoryViewModel historyViewModel = new Gson().fromJson(response, HistoryViewModel.class);
                            if (historyViewModel.getStatus()) {
                                for (int i = 0; i < historyViewModel.getBody().size(); i++) {
                                    historyAdapter = new HistoryAdapter(historyViewModel.getBody(),mContext);
                                    activityHelpAndSupportBinding.rvHelpAndSupport.setAdapter(historyAdapter);
                                }
                                CommonFunction.showToastSingle(mContext,historyViewModel.getMsg());
                                activityHelpAndSupportBinding.tvNoHistory.setVisibility(View.GONE);
                                activityHelpAndSupportBinding.llHistory.setVisibility(View.VISIBLE);
                                historyAdapter.notifyDataSetChanged();
                            }else {
                                activityHelpAndSupportBinding.tvNoHistory.setText("No History");
                                activityHelpAndSupportBinding.tvNoHistory.setVisibility(View.VISIBLE);
                                activityHelpAndSupportBinding.llHistory.setVisibility(View.GONE);
                                CommonFunction.showToastSingle(mContext,historyViewModel.getMsg());
                            }
                            hideProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("ViewHistoryApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    System.out.println("ViewHistoryApi params>>>>>>>>>>>>>>" + params);
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