package com.theLearningcLub;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.EarnDetailModel;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class AgentEarnDetailActivity extends BaseFragment {

    ListView listView;
    TextView tvAgentNo;
    ArrayList<EarnDetailModel> upstreamModelArrayList = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_agent_earn_detail, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        HomeActivity.tvUserHello.setText(getResources().getString(R.string.agent_report));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);


        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        listView = view.findViewById(R.id.listView);
        tvAgentNo = view.findViewById(R.id.tvAgentNo);
        new UpcomingStreamAsyncTask().execute();
        return view;
    }

    class UpcomingStreamAsyncTask extends AsyncTask<String,String,String> {

        String status,user_fname,amount,date;
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            upstreamModelArrayList.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request=new WebRequest();

            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());

            String response = request.makeWebServiceCall(AllUrl.MLM_users_earn_amt_dtlApi,2,hashMap);

            System.out.println("users_earn_amt_dtl.... "+response);
            System.out.println("users_earn_amt_dtl param >>>>>>>>>>>>>>>>   "+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                jsonArray = jsonObject.getJSONArray("body");

                System.out.println("cart data =============         "+jsonArray);
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject c =jsonArray.getJSONObject(i);
                    user_fname = c.getString("user_fname");
                    amount = c.getString("amount");
                    date = c.getString("date");

                    EarnDetailModel upstreamModel = new EarnDetailModel(user_fname,amount,date);
                    upstreamModelArrayList.add(upstreamModel);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            try {
                if (!jsonArray.isNull(0)){
                    listView.setAdapter(new MyArrayAdapter(mContext));
                    tvAgentNo.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    ImageView noDataFoundGif = getView().findViewById(R.id.noDataFoundGif);
                    noDataFoundGif.setVisibility(View.GONE);
                }else {
                    tvAgentNo.setVisibility(View.GONE);
                    listView.setVisibility(View.GONE);
                    ImageView noDataFoundGif = getView().findViewById(R.id.noDataFoundGif);
                    noDataFoundGif.setVisibility(View.VISIBLE);
                    Glide.with(AgentEarnDetailActivity.this).asGif().load(R.drawable.nodatagif).into(noDataFoundGif);
                }
            }catch (Exception e){
                e.printStackTrace();
                tvAgentNo.setVisibility(View.GONE);
                listView.setVisibility(View.GONE);
                ImageView noDataFoundGif = getView().findViewById(R.id.noDataFoundGif);
                noDataFoundGif.setVisibility(View.VISIBLE);
                Glide.with(AgentEarnDetailActivity.this).asGif().load(R.drawable.nodatagif).into(noDataFoundGif);
            }
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
            return upstreamModelArrayList.size();
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
                v = mInflater.inflate(R.layout.row_earn_dtl, null);
                holder = new ListContent();

                holder.tv_f_name = v.findViewById(R.id.tv_f_name);
                holder.tv_name = v.findViewById(R.id.tv_name);
                holder.tv_amt = v.findViewById(R.id.tv_amt);
                holder.tv_date = v.findViewById(R.id.tv_date);

                v.setTag(holder);
            } else {
                holder = (ListContent) v.getTag();
            }
            try{
                holder.tv_name.setText(upstreamModelArrayList.get(position).getUser_fname());
                holder.tv_amt.setText(upstreamModelArrayList.get(position).getAmount());
                holder.tv_f_name.setText(upstreamModelArrayList.get(position).getUser_fname().substring(0,1).toUpperCase());
                holder.tv_date.setText(CommonFunction.parseDateToddMMyyyy(upstreamModelArrayList.get(position).getDate()));
            }catch (Exception e){
                e.printStackTrace();
            }
            return v;
        }
    }

    static class ListContent {
        TextView tv_f_name,tv_name,tv_amt,tv_date;
    }
}