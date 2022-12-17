package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.theLearningcLub.adapter.AgentDetailAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityAgentDetailBinding;
import com.theLearningcLub.model.agentreport.AgentReportModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class AgentDetailActivity extends BaseFragment implements View.OnClickListener{

    ActivityAgentDetailBinding activityAgentDetailBinding;
    AgentDetailAdapter agentDetailAdapter;

    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String> arrayListID = new ArrayList<>();
    ArrayList<String> arrayListrank = new ArrayList<>();
    ArrayList<String> arrayList1 = new ArrayList<>();
    ArrayList<String> arrayListID1 = new ArrayList<>();
    ArrayList<String> arrayListrank1 = new ArrayList<>();
    ArrayList<String> arrayList2 = new ArrayList<>();
    ArrayList<String> arrayListID2 = new ArrayList<>();
    ArrayList<String> arrayListrank2 = new ArrayList<>();
    ArrayList<String> arrayList3 = new ArrayList<>();
    ArrayList<String> arrayListID3 = new ArrayList<>();
    ArrayList<String> arrayListrank3 = new ArrayList<>();
    ArrayList<String> arrayList4 = new ArrayList<>();
    ArrayList<String> arrayListID4 = new ArrayList<>();
    ArrayList<String> arrayListrank4 = new ArrayList<>();

    MyArrayAdapter myArrayAdapter;
    MyArrayAdapter1 myArrayAdapter1;
    MyArrayAdapter2 myArrayAdapter2;
    MyArrayAdapter3 myArrayAdapter3;
    MyArrayAdapter4 myArrayAdapter4;

    String status;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activityAgentDetailBinding = ActivityAgentDetailBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.agent_s_details));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);
        HomeActivity.ivWallet.setVisibility(View.VISIBLE);

        activityAgentDetailBinding.rlUpStream.setOnClickListener(this);
        activityAgentDetailBinding.rlDOwnStream.setOnClickListener(this);
        activityAgentDetailBinding.rlUpStream1.setOnClickListener(this);
        activityAgentDetailBinding.rlDOwnStream1.setOnClickListener(this);

        try {
            UpcomingStreamApi();
            DownlineApi();

            myArrayAdapter = new MyArrayAdapter(mContext);
            activityAgentDetailBinding.rvAgentDetail1.setAdapter(myArrayAdapter);

        }catch (Exception e){
            e.printStackTrace();
        }

        return activityAgentDetailBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.rlUpStream){
            activityAgentDetailBinding.llUpStream.setVisibility(View.VISIBLE);
            activityAgentDetailBinding.llDownStream.setVisibility(View.GONE);
        }else if (view.getId() == R.id.rlDOwnStream){
            activityAgentDetailBinding.llUpStream.setVisibility(View.GONE);
            activityAgentDetailBinding.llDownStream.setVisibility(View.VISIBLE);
        }else if (view.getId() == R.id.rlUpStream1){
            activityAgentDetailBinding.llUpStream.setVisibility(View.VISIBLE);
            activityAgentDetailBinding.llDownStream.setVisibility(View.GONE);
        }else if (view.getId() == R.id.rlDOwnStream1){
            activityAgentDetailBinding.llUpStream.setVisibility(View.GONE);
            activityAgentDetailBinding.llDownStream.setVisibility(View.VISIBLE);
        }
    }

    private void onclickmethod() {
        activityAgentDetailBinding.tvAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityAgentDetailBinding.tvAll.setTextColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvOne.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvTwo.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvThree.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvFour.setTextColor(getResources().getColor(R.color.black));

                activityAgentDetailBinding.tvAll.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_green_report));
                activityAgentDetailBinding.tvOne.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvTwo.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvThree.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvFour.setBackgroundColor(getResources().getColor(R.color.white));

                myArrayAdapter = new MyArrayAdapter(mContext);
                activityAgentDetailBinding.rvAgentDetail1.setAdapter(myArrayAdapter);
            }
        });

        activityAgentDetailBinding.tvOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityAgentDetailBinding.tvAll.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvOne.setTextColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvTwo.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvThree.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvFour.setTextColor(getResources().getColor(R.color.black));

                activityAgentDetailBinding.tvAll.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvOne.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_green_report));
                activityAgentDetailBinding.tvTwo.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvThree.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvFour.setBackgroundColor(getResources().getColor(R.color.white));

                myArrayAdapter1 = new MyArrayAdapter1(mContext);
                activityAgentDetailBinding.rvAgentDetail1.setAdapter(myArrayAdapter1);
            }
        });

        activityAgentDetailBinding.tvTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityAgentDetailBinding.tvAll.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvOne.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvTwo.setTextColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvThree.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvFour.setTextColor(getResources().getColor(R.color.black));

                activityAgentDetailBinding.tvAll.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvOne.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvTwo.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_green_report));
                activityAgentDetailBinding.tvThree.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvFour.setBackgroundColor(getResources().getColor(R.color.white));

                myArrayAdapter2 = new MyArrayAdapter2(mContext);
                activityAgentDetailBinding.rvAgentDetail1.setAdapter(myArrayAdapter2);
            }
        });

        activityAgentDetailBinding.tvThree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityAgentDetailBinding.tvAll.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvOne.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvTwo.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvThree.setTextColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvFour.setTextColor(getResources().getColor(R.color.black));

                activityAgentDetailBinding.tvAll.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvOne.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvTwo.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvThree.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_green_report));
                activityAgentDetailBinding.tvFour.setBackgroundColor(getResources().getColor(R.color.white));

                myArrayAdapter3 = new MyArrayAdapter3(mContext);
                activityAgentDetailBinding.rvAgentDetail1.setAdapter(myArrayAdapter3);
            }
        });

        activityAgentDetailBinding.tvFour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                activityAgentDetailBinding.tvAll.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvOne.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvTwo.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvThree.setTextColor(getResources().getColor(R.color.black));
                activityAgentDetailBinding.tvFour.setTextColor(getResources().getColor(R.color.white));

                activityAgentDetailBinding.tvAll.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvOne.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvTwo.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvThree.setBackgroundColor(getResources().getColor(R.color.white));
                activityAgentDetailBinding.tvFour.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_green_report));

                myArrayAdapter4 = new MyArrayAdapter4(mContext);
                activityAgentDetailBinding.rvAgentDetail1.setAdapter(myArrayAdapter4);
            }
        });
    }

    private void UpcomingStreamApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.MLM_upstream_listApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("UpcomingStreamApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            JSONArray jsonArray = jsonObject.getJSONArray("body");

                            if (status.equals("true")){
                                if (!jsonArray.isNull(0)){
                                    AgentReportModel agentReportModel = new Gson().fromJson(response,AgentReportModel.class);
                                    if (agentReportModel.getStatus()){
                                        for (int i = 0; i < agentReportModel.getBody().size(); i++) {
                                            agentDetailAdapter = new AgentDetailAdapter(agentReportModel.getBody(),mContext);
                                            activityAgentDetailBinding.rvAgentDetail.setAdapter(agentDetailAdapter);
                                        }
                                    }
                                    agentDetailAdapter.notifyDataSetChanged();
                                    activityAgentDetailBinding.tvNoAgentData.setVisibility(View.GONE);
                                    activityAgentDetailBinding.rvAgentDetail.setVisibility(View.VISIBLE);
                                    activityAgentDetailBinding.noDataFoundGif.setVisibility(View.GONE);
                                }else {
                                    activityAgentDetailBinding.tvNoAgentData.setVisibility(View.GONE);
                                    activityAgentDetailBinding.rvAgentDetail.setVisibility(View.GONE);

                                    activityAgentDetailBinding.noDataFoundGif.setVisibility(View.VISIBLE);
                                    Glide.with(AgentDetailActivity.this).asGif().load(R.drawable.nodatagif).into(activityAgentDetailBinding.noDataFoundGif);
                                }
                            }
                            hideProgressDialog();
                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }

                    }, error -> {
                System.out.println("UpcomingStreamApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    System.out.println("UpcomingStreamApi params>>>>>>>>>>>>>>" + params);
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

    private void DownlineApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.MLM_downline_strApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("DownlineApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            JSONArray jsonArray = jsonObject.getJSONArray("body");

                            JSONObject c1 = jsonArray.getJSONObject(0);
                            JSONArray d1 = c1.getJSONArray("d1");
                            System.out.println("d1 =============         "+d1);

                            if (d1.isNull(0)){
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.GONE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.VISIBLE);
                                Glide.with(AgentDetailActivity.this).asGif().load(R.drawable.nodatagif).into(activityAgentDetailBinding.noDataFoundGifD);
                            }else {
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.VISIBLE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.GONE);
                                for (int i = 0; i < d1.length(); i++) {
                                    JSONObject jsonObject1=d1.getJSONObject(i);

                                    String user_fname = jsonObject1.getString("user_fname");
                                    String user_id = jsonObject1.getString("user_id");
//                                    String rank="1";

                                    arrayList.add(user_fname);
                                    arrayListID.add(user_id);
                                    arrayListrank.add("1");

                                    arrayList1.add(user_fname);
                                    arrayListID1.add(user_id);
                                    arrayListrank1.add("1");
                                }
                            }

                            JSONObject c2 = jsonArray.getJSONObject(1);
                            JSONArray d2 = c2.getJSONArray("d2");
                            System.out.println("d2 =============         "+d2);

                            if (d2.isNull(1)){
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.GONE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.VISIBLE);
                                Glide.with(AgentDetailActivity.this).asGif().load(R.drawable.nodatagif).into(activityAgentDetailBinding.noDataFoundGifD);
                            }else {
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.VISIBLE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.GONE);
                                for (int i = 0; i < d2.length(); i++) {
                                    JSONObject jsonObject1=d2.getJSONObject(i);

                                    String user_fname = jsonObject1.getString("user_fname");
                                    String user_id = jsonObject1.getString("user_id");
//                                    String rank="1";

                                    arrayList.add(user_fname);
                                    arrayListID.add(user_id);
                                    arrayListrank.add("2");

                                    arrayList2.add(user_fname);
                                    arrayListID2.add(user_id);
                                    arrayListrank2.add("2");
                                }
                            }

                            JSONObject c3=jsonArray.getJSONObject(2);
                            JSONArray d3=c3.getJSONArray("d3");
                            System.out.println("d3 =============         "+d3);

                            if (d3.isNull(2)){
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.GONE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.VISIBLE);
                                Glide.with(AgentDetailActivity.this).asGif().load(R.drawable.nodatagif).into(activityAgentDetailBinding.noDataFoundGifD);
                            }else {
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.VISIBLE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.GONE);
                                for (int i = 0; i < d3.length(); i++) {
                                    JSONObject jsonObject1=d3.getJSONObject(i);

                                    String user_fname = jsonObject1.getString("user_fname");
                                    String user_id = jsonObject1.getString("user_id");
//                                    String rank="1";

                                    arrayList.add(user_fname);
                                    arrayListID.add(user_id);
                                    arrayListrank.add("3");

                                    arrayList3.add(user_fname);
                                    arrayListID3.add(user_id);
                                    arrayListrank3.add("3");
                                }
                            }

                            JSONObject c4 = jsonArray.getJSONObject(3);
                            JSONArray d4 = c4.getJSONArray("d4");
                            System.out.println("d4 =============         "+d4);

                            if (d4.isNull(3)){
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.GONE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.VISIBLE);
                                Glide.with(AgentDetailActivity.this).asGif().load(R.drawable.nodatagif).into(activityAgentDetailBinding.noDataFoundGifD);
                            }else {
                                activityAgentDetailBinding.tvDetailNo.setVisibility(View.GONE);
                                activityAgentDetailBinding.rvAgentDetail1.setVisibility(View.VISIBLE);
                                activityAgentDetailBinding.noDataFoundGifD.setVisibility(View.GONE);
                                for (int i = 0; i < d4.length(); i++) {
                                    JSONObject jsonObject1=d4.getJSONObject(i);

                                    String user_fname=jsonObject1.getString("user_fname");
                                    String user_id=jsonObject1.getString("user_id");
//                                    String rank="1";

                                    arrayList.add(user_fname);
                                    arrayListID.add(user_id);
                                    arrayListrank.add("4");

                                    arrayList4.add(user_fname);
                                    arrayListID4.add(user_id);
                                    arrayListrank4.add("4");
                                }
                            }
                            myArrayAdapter.notifyDataSetChanged();
                            onclickmethod();
                            hideProgressDialog();
                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }

                    }, error -> {
                System.out.println("DownlineApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    System.out.println("DownlineApi params>>>>>>>>>>>>>>" + params);
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

    private class MyArrayAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter(Activity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.agent_detail_layout, null);
                holder = new ListContent();

                holder.tvName = v.findViewById(R.id.tvName);
                holder.tvRank = v.findViewById(R.id.tvRank);
                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
            try{
                holder.tvName.setText(arrayList.get(position));
                holder.tvRank.setText(arrayListrank.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }

    }

    static class ListContent {
        TextView tvName,tvRank;
    }

    //-------------------Downline 1

    private class MyArrayAdapter1 extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter1(Activity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList1.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.agent_detail_layout, null);
                holder = new ListContent();
                holder.tvName = v.findViewById(R.id.tvName);
                holder.tvRank = v.findViewById(R.id.tvRank);
                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }
            try{
                holder.tvName.setText(arrayList1.get(position));
                holder.tvRank.setText(arrayListrank1.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }

    //-------------------Downline 2

    private class MyArrayAdapter2 extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter2(Activity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList2.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.agent_detail_layout, null);
                holder = new ListContent();
                holder.tvName = v.findViewById(R.id.tvName);
                holder.tvRank = v.findViewById(R.id.tvRank);
                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }
            try{
                holder.tvName.setText(arrayList2.get(position));
                holder.tvRank.setText(arrayListrank2.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }

    //-------------------Downline 3

    private class MyArrayAdapter3 extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter3(Activity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList3.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.agent_detail_layout, null);
                holder = new ListContent();
                holder.tvName = v.findViewById(R.id.tvName);
                holder.tvRank = v.findViewById(R.id.tvRank);
                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }
            try{
                holder.tvName.setText(arrayList3.get(position));
                holder.tvRank.setText(arrayListrank3.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }

    //-------------------Downline 4

    private class MyArrayAdapter4 extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter4(Activity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return arrayList4.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.agent_detail_layout, null);
                holder = new ListContent();
                holder.tvName = v.findViewById(R.id.tvName);
                holder.tvRank = v.findViewById(R.id.tvRank);
                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
            try{
                holder.tvName.setText(arrayList4.get(position));
                holder.tvRank.setText(arrayListrank4.get(position));
            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }
}