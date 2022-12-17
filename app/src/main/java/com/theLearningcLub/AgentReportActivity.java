package com.theLearningcLub;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityAgentReportBinding;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Objects;

public class AgentReportActivity extends BaseFragment implements View.OnClickListener {

    ActivityAgentReportBinding activityAgentReportBinding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activityAgentReportBinding = ActivityAgentReportBinding.inflate(inflater, container, false);

        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.agent_report));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);
        HomeActivity.ivWallet.setVisibility(View.GONE);

        activityAgentReportBinding.tvAgentReport.setOnClickListener(this);
        activityAgentReportBinding.rlAmountEarn.setOnClickListener(this);

        new AgentInfoAsyncTask().execute();

        return activityAgentReportBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvAgentReport){

            Fragment myFragment = new AgentDetailActivity();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,myFragment,"agentDetail");
            fragmentTransaction.addToBackStack("agentDetail");
            fragmentTransaction.commit();
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.agent_s_details));
            HomeActivity.iv_menu_main.setVisibility(View.GONE);
            HomeActivity.ivBack.setVisibility(View.VISIBLE);
//            HomeActivity.ivNotification.setVisibility(View.GONE);
            HomeActivity.rlCart.setVisibility(View.GONE);
            sessionManager.setfragmentclick("true");

        }else if (view.getId() == R.id.rlAmountEarn){

            Fragment myFragment = new AgentEarnDetailActivity();
            FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
            fragmentTransaction.replace(R.id.flFrameLayout,myFragment,"agentDetail");
            fragmentTransaction.addToBackStack("agentDetail");
            fragmentTransaction.commit();
            HomeActivity.tvUserHello.setText(getResources().getString(R.string.agent_report));
            HomeActivity.iv_menu_main.setVisibility(View.GONE);
            HomeActivity.ivBack.setVisibility(View.VISIBLE);
//            HomeActivity.ivNotification.setVisibility(View.GONE);
            HomeActivity.rlCart.setVisibility(View.GONE);
            sessionManager.setfragmentclick("true");

        }
    }

    class AgentInfoAsyncTask extends AsyncTask<String,String,String> {
        String status,user_fname,post_name,amount_earned,amount_outstanding,amount_deducted,amount_received;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());

            String response = request.makeWebServiceCall(AllUrl.MLM_agent_infoApi,2,hashMap);

            System.out.println("agent_info response .... "+response);
            System.out.println("agent_info params >>>>>>>>>>>>>>>>   "+hashMap);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            hideProgressDialog();
            try {
                JSONObject jsonObject=new JSONObject(response);
                status= jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("body");

                System.out.println("cart data =============         "+jsonArray);

                JSONObject c=jsonArray.getJSONObject(0);
                //     s_card_id = c.getString("card_id");
                user_fname = c.getString("user_fname");
                post_name = c.getString("post_name");
                amount_earned= c.getString("amount_earned");
                amount_outstanding= c.getString("amount_outstanding");
                amount_deducted= c.getString("amount_deducted");
                amount_received= c.getString("amount_received");

//                activityAgentReportBinding.tvName.setText(user_fname);



                activityAgentReportBinding.tvName.setText(HomeActivity.s_first);


                activityAgentReportBinding.tvPost.setText(post_name);
                activityAgentReportBinding.tvEarn.setText(getString(R.string.rs)+" "+amount_earned);
                activityAgentReportBinding.tvOutstsnding.setText(getString(R.string.rs)+" "+amount_outstanding);
                activityAgentReportBinding.tvTax.setText(getString(R.string.rs)+" "+amount_deducted);
                activityAgentReportBinding.tvReceived.setText(getString(R.string.rs)+" "+amount_received);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}