package com.theLearningcLub.fragment;

import static com.theLearningcLub.HomeActivity.bottomSheetDashboard;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theLearningcLub.HomeActivity;
import com.theLearningcLub.Model_Class.Nitification1;
import com.theLearningcLub.NoInternetActivity;
import com.theLearningcLub.R;
import com.theLearningcLub.adapter.NotificationAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends BaseFragment implements View.OnClickListener {

    NotificationAdapter notificationAdapter;
    ListView rvNotification;
    TextView tvNoData;
    LinearLayout llEmptyNotification;

    ArrayList<Nitification1> nitification1s = new ArrayList<>();
    String /*suserid,*/status,key="0",cartkey;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_notification, container, false);

//        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//        bottomSheetDashboard.getMenu().getItem(2).setChecked(true);

        HomeActivity.iv_menu_main.setVisibility(View.VISIBLE);
        HomeActivity.tvUserHello.setText(getResources().getString(R.string.notification));
        HomeActivity.ivBack.setVisibility(View.GONE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);
        HomeActivity.ivWallet.setVisibility(View.VISIBLE);

        rvNotification = view.findViewById(R.id.rvNotification);
        tvNoData = view.findViewById(R.id.tvNoData);
        llEmptyNotification = view.findViewById(R.id.llEmptyNotification);

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

//        String s = sessionManager.getSavedCART_NO();
//        suserid = sessionManager.getSavedUserId();

        rvNotification.isSmoothScrollbarEnabled();
        rvNotification.setDrawingCacheEnabled(true);
        rvNotification.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);

        cartkey="1";
        CartCountApi();

        key = sessionManager.getnokey();

        new NotificationAs().execute();

        return view;
    }

    @Override
    public void onClick(View view) {

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
            return nitification1s.size();
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
                v = mInflater.inflate(R.layout.notification_layout, null);

                holder = new ListContent();
                holder.title = v.findViewById(R.id.tvTitle);
                holder.subtitle = v.findViewById(R.id.tvDes);
                holder.date=v.findViewById(R.id.tvDate);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
            try{
                holder.title.setText(nitification1s.get(position).getTitle());

                String inputText =nitification1s.get(position).getSubtitle();

                if(inputText.length()>80)
                {
                    String text = inputText.substring(0,80).replace("\n"," ")+"...";
                    String fulltext = inputText;

                    SpannableString ss = new SpannableString(text + "Read more");

                    ClickableSpan span1 = new ClickableSpan() {
                        @Override
                        public void onClick(View textView) {
                            // do some thing
                            SpannableString ss1 = new SpannableString(fulltext + "...Read Less");
                            ClickableSpan span2 = new ClickableSpan() {
                                @Override
                                public void onClick(View textView) {
                                    // do some thing
                                    holder.subtitle.setText(ss);
                                    holder.subtitle.setMovementMethod(LinkMovementMethod.getInstance());

                                }
                            };
                            ss1.setSpan(span2, fulltext.length(), ss1.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                            ss1.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.color_orange)), fulltext.length(), ss1.length(),
                                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);


                            holder.subtitle.setText(ss1);
                            holder.subtitle.setMovementMethod(LinkMovementMethod.getInstance());
                        }
                    };
                    ss.setSpan(span1, 83, 92, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    ss.setSpan(new ForegroundColorSpan(getContext().getResources().getColor(R.color.color_orange)), 0, 0,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

                    holder.subtitle.setText(ss);
                    holder.subtitle.setMovementMethod(LinkMovementMethod.getInstance());
                }
                else
                {
                    holder.subtitle.setText(Html.fromHtml(inputText));
                }
//                holder.subtitle.setText(Html.fromHtml(nitification1s.get(position).getSubtitle()+"<font color='#F27023'> Read more... </font>"));

                holder.date.setText(nitification1s.get(position).getDate());
                holder.time.setText(nitification1s.get(position).getTime());

            }catch (Exception e){
                e.printStackTrace();
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
//                    CommonFunction.showToastSingle(getContext(),nitification1s.get(position).getSubtitle());
                }
            });

            return v;
        }
    }

    static class ListContent {
        TextView title,subtitle,time,date;
    }

    class NotificationAs extends AsyncTask<String, String, String> {

        String s_u,s_title,s_date,s_desc,status,s_time;
        JSONArray contacts;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            nitification1s.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            String response= request.makeWebServiceCall(AllUrl.view_notApi,2,hashMap);

            System.out.println("notification>>>>res>>>>>"+response);
            System.out.println("notification hash map>>>>>"+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                contacts = jsonObject.getJSONArray("data");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    s_u = c.getString("user_id");
                    s_date = c.getString("dates");
                    s_title = c.getString("title");
                    s_desc = c.getString("message");
                    s_time = c.getString("time");

                    Nitification1 nitification1=new Nitification1();
                    nitification1.setTitle(s_title);
                    nitification1.setSubtitle(s_desc);
                    nitification1.setDate(s_date);
                    nitification1.setTime(s_time);
                    nitification1s.add(nitification1);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (status.equals("success")) {
                    if (contacts.isNull(0)) {
                        llEmptyNotification.setVisibility(View.VISIBLE);
                        rvNotification.setVisibility(View.GONE);
                    } else {
                        llEmptyNotification.setVisibility(View.GONE);
                        rvNotification.setVisibility(View.VISIBLE);
                        rvNotification.setAdapter(new MyArrayAdapter(mContext));
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
                llEmptyNotification.setVisibility(View.VISIBLE);
                rvNotification.setVisibility(View.GONE);
            }

        }
    }

    private void CartCountApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.count_cartApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("CartCountApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            String s_cartcount = jsonObject.getString("cart_count");

                            if (status.equals("success")) {
                                if (s_cartcount.isEmpty()) {
                                    HomeActivity.badgeCart.setVisibility(View.GONE);
                                } else if (s_cartcount.equals("0")) {
                                    HomeActivity.badgeCart.setVisibility(View.GONE);
                                } else {
                                    HomeActivity.badgeCart.setVisibility(View.VISIBLE);
                                    HomeActivity.badgeCart.setText(s_cartcount);
                                }

                                if(cartkey.equals("0")) {
                                    if(s_cartcount.equals("0")){
                                        llEmptyNotification.setVisibility(View.VISIBLE);
                                        rvNotification.setVisibility(View.GONE);
                                    }else{
                                        llEmptyNotification.setVisibility(View.GONE);
                                        rvNotification.setVisibility(View.VISIBLE);
                                    }
                                }
                            }
                            hideProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("CartCountApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("CartCountApi params>>>>>>>>>>>>>>" + params);
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