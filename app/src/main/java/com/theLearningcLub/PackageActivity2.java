package com.theLearningcLub;

import android.annotation.SuppressLint;
import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Paint;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.theLearningcLub.Model_Class.PDFModel;
import com.theLearningcLub.Model_Class.Purachase_package_free_video_Model;
import com.theLearningcLub.adapter.PackageVideoAdapter;
import com.theLearningcLub.adapter.PdfAdapter;
import com.theLearningcLub.adapter.ReviewAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityPackageBinding;
import com.theLearningcLub.model.review.ReviewModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class PackageActivity2 extends BaseFragment implements View.OnClickListener{

    ArrayList<PDFModel> pdfModelArrayList = new ArrayList<>();
    ArrayList<Purachase_package_free_video_Model> purachase_package_video_modelslist = new ArrayList<>();

    ActivityPackageBinding activityPackageBinding;
    ReviewAdapter reviewAdapter;

    String status,s_price,s_user_id,message,set_key;
    String title,s_name,s_pack_id,s_pack_offer_price="",s_pack_name,response;
    String s_pack_price,s_pack_desc,s_pack_time_pride,s_pack_image,s_packid,s_is_free,rating;
    public int TotalReview = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityPackageBinding = ActivityPackageBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText("");
        Log.e("TAG", "onCreateView: "+"pageold" );
        activityPackageBinding.tvPackageRs.setPaintFlags(activityPackageBinding.tvPackageRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        activityPackageBinding.tvRs.setPaintFlags(activityPackageBinding.tvRs.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);

        activityPackageBinding.tvDescription.setOnClickListener(this);
        activityPackageBinding.tvVideo.setOnClickListener(this);
        activityPackageBinding.tvPdf.setOnClickListener(this);
        activityPackageBinding.tvReview.setOnClickListener(this);
        activityPackageBinding.tvAddToCart.setOnClickListener(this);

        @SuppressLint("WrongConstant") RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL,false);
        activityPackageBinding.rvVideo.setLayoutManager(mLayoutManager1);
        activityPackageBinding.rvVideo.setItemAnimator(new DefaultItemAnimator());
        activityPackageBinding.rvVideo.setHasFixedSize(true);
        activityPackageBinding.rvVideo.setNestedScrollingEnabled(false);

        assert getArguments() != null;
        set_key = getArguments().getString("key");

        if (set_key.equals("2")){
            s_name = getArguments().getString("name");
            title = getArguments().getString("title");
            s_pack_id = getArguments().getString("packid");
            s_is_free = getArguments().getString("is_free");
            s_price = getArguments().getString("price");
            s_pack_offer_price = getArguments().getString("offer_price");
            s_pack_desc = getArguments().getString("description");
            s_pack_image = getArguments().getString("image");
            rating = getArguments().getString("rating");

            System.out.println("s_price search ======        "+s_price);
            System.out.println("s_pack_offer_price search =========    "+s_pack_offer_price);
            System.out.println("s_is_free search =========    "+s_is_free);

        }else {
            title = getArguments().getString("title");
            s_price = getArguments().getString("price");
            s_pack_offer_price = getArguments().getString("offerprice");
            s_pack_image = getArguments().getString("image");
            s_pack_id = getArguments().getString("packid");
            s_pack_desc = getArguments().getString("Desc");
            s_is_free = getArguments().getString("is_free");
            s_name = getArguments().getString("name");
            rating = getArguments().getString("rating");

            System.out.println("s_price======        "+s_price);
            System.out.println("s_pack_offer_price=========    "+s_pack_offer_price);
            System.out.println("s_is_free=========    "+s_is_free);
        }

//        ReviewApi();

        if (s_pack_offer_price.isEmpty()) {
            activityPackageBinding.tvPackageRs.setVisibility(View.GONE);
            activityPackageBinding.tvPrice.setVisibility(View.VISIBLE);
            activityPackageBinding.tvPackageRs.setText(s_price + "");
        } else {
            activityPackageBinding.tvPrice.setText(s_price+"");
            activityPackageBinding.tvPrice.setVisibility(View.VISIBLE);
            activityPackageBinding.tvPrice.setText(s_pack_offer_price + "");
        }

        if (set_key.equals("2")){
            new AddDataTask().execute();
        }else {
            activityPackageBinding.tvPackageRs.setText(s_price+"");

            if (s_pack_offer_price.isEmpty()) {
                System.out.println("*********************            ");
                activityPackageBinding.tvPackageRs.setVisibility(View.GONE);
                activityPackageBinding.tvRsIcon.setVisibility(View.GONE);
                activityPackageBinding.tvRs.setVisibility(View.GONE);
                activityPackageBinding.tvAddToCart.setVisibility(View.GONE);
                activityPackageBinding.tvPrice.setText(s_price + "");
            } else {
                activityPackageBinding.tvPrice.setVisibility(View.VISIBLE);
                activityPackageBinding.tvRsIcon.setVisibility(View.VISIBLE);
                activityPackageBinding.tvRs.setVisibility(View.VISIBLE);
                activityPackageBinding.tvAddToCart.setVisibility(View.VISIBLE);
                activityPackageBinding.tvPackageRs.setText(s_price);
                activityPackageBinding.tvPrice.setText(s_pack_offer_price + "");
            }

            activityPackageBinding.tvPackDescription.setText(Html.fromHtml(s_pack_desc));
            System.out.println("s_pack_desc>>>>>>>>>>>         "+s_pack_desc);
            activityPackageBinding.tvPackName.setText(title);
            activityPackageBinding.tvRating.setText(rating);
            activityPackageBinding.tvPackagesName.setText(title);
        }
        Glide.with(mContext).load(s_pack_image.replace("http://","https://"))
                .placeholder(R.drawable.bg_white).into(activityPackageBinding.ivPackagesImage);
        Glide.with(mContext).load(R.drawable.imnotfound)
                .placeholder(R.drawable.imnotfound).into(activityPackageBinding.imNoData);
//        activityPackageBinding.tvAddToCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                new AddtoCartAs1().execute();
//            }
//        });

        CartCountApi();


        /*  Review Bug Fixed here */

        ReviewApi();
        activityPackageBinding.tvDescription.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn_light_orange_1));
        activityPackageBinding.tvDescription.setTextColor(getResources().getColor(R.color.white));
        activityPackageBinding.tvVideo.setBackgroundColor(getResources().getColor(R.color.white));
        activityPackageBinding.tvVideo.setTextColor(getResources().getColor(R.color.black));
        activityPackageBinding.tvPdf.setBackgroundColor(getResources().getColor(R.color.white));
        activityPackageBinding.tvPdf.setTextColor(getResources().getColor(R.color.black));
        activityPackageBinding.tvReview.setBackgroundColor(getResources().getColor(R.color.white));
        activityPackageBinding.tvReview.setTextColor(getResources().getColor(R.color.black));
        activityPackageBinding.tvNoData.setText("");
        activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
        activityPackageBinding.cardAdd.setVisibility(View.VISIBLE);
        activityPackageBinding.llVideo.setVisibility(View.GONE);

        /* >>>>>>>>> END <<<<<<<<<< */


        return activityPackageBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvReview){
            activityPackageBinding.tvReview.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_green_round));
            activityPackageBinding.tvReview.setTextColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvDescription.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvDescription.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvVideo.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvVideo.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvPdf.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvPdf.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.cardAdd.setVisibility(View.GONE);
            activityPackageBinding.llVideo.setVisibility(View.VISIBLE);
            activityPackageBinding.tvNoData.setText("");
            ReviewApiAdapter();

        }else if (view.getId() == R.id.tvDescription){
            activityPackageBinding.tvDescription.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn_light_orange_1));
            activityPackageBinding.tvDescription.setTextColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvVideo.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvVideo.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvPdf.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvPdf.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvReview.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvReview.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvNoData.setText("");

            activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
            activityPackageBinding.cardAdd.setVisibility(View.VISIBLE);
            activityPackageBinding.llVideo.setVisibility(View.GONE);

        }else if (view.getId() == R.id.tvVideo){
            activityPackageBinding.tvVideo.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn_pirple_round));
            activityPackageBinding.tvVideo.setTextColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvDescription.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvDescription.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvPdf.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvPdf.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvReview.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvReview.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvNoData.setText("");
            activityPackageBinding.cardAdd.setVisibility(View.GONE);
            activityPackageBinding.llVideo.setVisibility(View.VISIBLE);

            new view_download_list_asy().execute();
//            new view_download_list_asy1().execute();

//            view_download_list_asy();

        }else if (view.getId() == R.id.tvPdf){
            activityPackageBinding.tvPdf.setBackground(ContextCompat.getDrawable(mContext,R.drawable.bg_btn_shed_blue_round));
            activityPackageBinding.tvPdf.setTextColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvDescription.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvDescription.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvVideo.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvVideo.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvReview.setBackgroundColor(getResources().getColor(R.color.white));
            activityPackageBinding.tvReview.setTextColor(getResources().getColor(R.color.black));
            activityPackageBinding.tvNoData.setText("");
            activityPackageBinding.cardAdd.setVisibility(View.GONE);
            activityPackageBinding.llVideo.setVisibility(View.VISIBLE);

            new view_PDF_list_asy().execute();

        }else if (view.getId() == R.id.tvAddToCart){
            new AddtoCartAs1().execute();
        }
    }

    class AddDataTask extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_name",s_name);
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());

            response= webRequest.makeWebServiceCall(AllUrl.package_detailApi,2,hashMap);
            System.out.println("package_detail parameter >>>>>>>>>>>>>>>        "+hashMap);
            System.out.println("package_detail Response >>>>>>>>>>>>>>>          "+response);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray contacts = jsonObject.getJSONArray("data");
                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    s_pack_image = c.getString("pack_image");
                    s_pack_name = c.getString("pack_name");
                    s_pack_price = c.getString("price");
                    s_pack_offer_price = c.getString("offer_price");
                    s_pack_desc = c.getString("description");
                    s_pack_time_pride = c.getString("time_perioud");
                    s_packid =  c.getString("pack_id");
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
                if(status.equals("success")){
                    activityPackageBinding.tvPackName.setText(s_pack_name);
                    activityPackageBinding.tvPrice.setText(s_pack_offer_price);
                    if(s_pack_offer_price.isEmpty()){
                        activityPackageBinding.tvRs.setVisibility(View.GONE);
                        activityPackageBinding.tvAddToCart.setVisibility(View.GONE);
                    }else {
                        activityPackageBinding.tvAddToCart.setVisibility(View.VISIBLE);
                        activityPackageBinding.tvRs.setVisibility(View.VISIBLE);
                    }
                    activityPackageBinding.tvPackageRs.setText(s_pack_price);

                    activityPackageBinding.tvPackDescription.setText(Html.fromHtml(s_pack_desc));

                    Glide.with(mContext).load(s_pack_image.replace("http://","https://"))
                            .placeholder(R.drawable.bg_white).into(activityPackageBinding.ivPackagesImage);

                    s_price = s_pack_price;
                    s_pack_id = s_packid;
                    s_name = s_pack_name;
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    private void ReviewApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.view_ratingApi+"?pack_id="+s_pack_id+"&key="+sessionManager.getLoginkey()+"user_id="+sessionManager.getSavedUserId(),
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("ReviewApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (jsonArray.isNull(0)){
                                activityPackageBinding.tvNoData.setText("");
                                activityPackageBinding.tvNoData.setVisibility(View.VISIBLE);
                                activityPackageBinding.rvVideo.setVisibility(View.GONE);
                                activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
                                Glide.with(PackageActivity2.this).asGif().load(R.drawable.nodatagif).into(activityPackageBinding.noDataFoundGif);
                            }else {
                                ReviewModel reviewModel = new Gson().fromJson(response,ReviewModel.class);
                                if (reviewModel.getStatus().equalsIgnoreCase(AllUrl.status)){
                                    for (int i = 0; i < reviewModel.getData().size(); i++) {
                                        TotalReview = TotalReview + Integer.parseInt(reviewModel.getData().get(i).getReview());
                                        activityPackageBinding.tvReviewTotal.setText(""+reviewModel.getData().size()+" "+getResources().getString(R.string._30_review));
                                        activityPackageBinding.tvReviewTotal.setText(""+TotalReview+" "+getResources().getString(R.string._30_review));
                                        reviewAdapter = new ReviewAdapter(reviewModel.getData(),mContext);
                                        activityPackageBinding.rvVideo.setAdapter(reviewAdapter);
                                    }
                                    activityPackageBinding.tvNoData.setVisibility(View.GONE);
                                    activityPackageBinding.rvVideo.setVisibility(View.VISIBLE);
                                    activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
                                    reviewAdapter.notifyDataSetChanged();
                                }
                            }

                            hideProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("ReviewApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("ReviewApi params>>>>>>>>>>>>>>" + params);
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


    private void ReviewApiAdapter(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.view_ratingApi+"?pack_id="+s_pack_id+"&key="+sessionManager.getLoginkey()+"user_id="+sessionManager.getSavedUserId(),
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("ReviewApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (jsonArray.isNull(0)){
                                activityPackageBinding.tvNoData.setText("");
                                activityPackageBinding.tvNoData.setVisibility(View.VISIBLE);
                                activityPackageBinding.rvVideo.setVisibility(View.GONE);
                                activityPackageBinding.noDataFoundGif.setVisibility(View.VISIBLE);
                                Glide.with(PackageActivity2.this).asGif().load(R.drawable.nodatagif).into(activityPackageBinding.noDataFoundGif);
                            }else {
                                ReviewModel reviewModel = new Gson().fromJson(response,ReviewModel.class);
                                if (reviewModel.getStatus().equalsIgnoreCase(AllUrl.status)){
                                    for (int i = 0; i < reviewModel.getData().size(); i++) {
                                        TotalReview = TotalReview + Integer.parseInt(reviewModel.getData().get(i).getReview());
                                        activityPackageBinding.tvReviewTotal.setText(""+reviewModel.getData().size()+" "+getResources().getString(R.string._30_review));
//                                        activityPackageBinding.tvReviewTotal.setText(""+TotalReview+" "+getResources().getString(R.string._30_review));
                                        reviewAdapter = new ReviewAdapter(reviewModel.getData(),mContext);
                                        activityPackageBinding.rvVideo.setAdapter(reviewAdapter);
                                    }
                                    activityPackageBinding.tvNoData.setVisibility(View.GONE);
                                    activityPackageBinding.rvVideo.setVisibility(View.VISIBLE);
                                    activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
                                    reviewAdapter.notifyDataSetChanged();
                                }
                            }

                            hideProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("ReviewApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("ReviewApi params>>>>>>>>>>>>>>" + params);
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


    class AddtoCartAs1 extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            s_user_id = sessionManager.getSavedUserId();

            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("user_id",s_user_id);
            hashMap.put("pack_id",s_pack_id);
            hashMap.put("pack_type","0");
            if (s_pack_offer_price.isEmpty()){
                hashMap.put("price",s_price);
            }else{
                hashMap.put("price",s_pack_offer_price);
            }

            hashMap.put("key", sessionManager.getLoginkey());

            String responce = webRequest.makeWebServiceCall(AllUrl.add_cartApi,2,hashMap);

            System.out.println("Add TO Cart params >>>>>>>>>>>>>>>"+hashMap);
            System.out.println("Add TO Cart Response >>>>>>>>>>>>>>>"+responce);

            try {
                JSONObject jsonObject = new JSONObject(responce);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                status = jsonObject1.getString("status");
                message = jsonObject1.getString("msg");

            }catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            try {
                if(status.equals("true")) {
                    CommonFunction.showToastSingle(mContext,message);
                    CartCountApi();
                }else {
                    CommonFunction.showToastSingle(mContext,message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }



    class view_download_list_asy extends AsyncTask<String, String, String> {

        String status,s_id,s_desc,s_video_date,s_title,s_video,s_image,s_v_id,is_free="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            purachase_package_video_modelslist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {





            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_id",s_pack_id);
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());
//
            String response = webRequest.makeWebServiceCall(AllUrl.defaenc_dvidn_newApi,2,hashMap);
//
            System.out.println(" View defaenc_dvidn mypack video response >>>>>>>>>>>>>>>  " +response);
            System.out.println(" View defaenc_dvidn mypack video parameter ns >>>>>>>>>>>>>>>  "+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status=jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject c = jsonArray.getJSONObject(i);

                    JSONArray jsonArray1 = c.getJSONArray("vid_data");
                    for (int p = 0; p < jsonArray1.length(); p++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(p);

                        s_id = jsonObject1.getString("mar_id");
                        s_desc = jsonObject1.getString("description");
                        s_video_date = jsonObject1.getString("videos_date");
                        s_title = jsonObject1.getString("Title");
                        s_video = jsonObject1.getString("videncd");
                        s_image = jsonObject1.getString("image");
                        s_v_id = jsonObject1.getString("vid_id");
                        if (jsonObject1.has("is_free")) {
                            is_free = jsonObject1.getString("is_free");
                        }
                        String videoviewtime=jsonObject1.getString("view_status");
                        String videotime=jsonObject1.getString("v_duration");

                        Purachase_package_free_video_Model video_model = new Purachase_package_free_video_Model();
                        video_model.setVideo_id(s_v_id);
                        video_model.setVideo_title(s_title);
                        video_model.setVideo(s_video);
                        video_model.setVideo_desc(s_desc);
                        video_model.setVideo_date(s_video_date);
                        video_model.setVideo_image(s_image);
                        video_model.setIs_free(is_free);
                        video_model.setVideoview_Time(videoviewtime);
                        video_model.setVideototal_duration(videotime);
                        purachase_package_video_modelslist.add(video_model);

                    }
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
            if (status.equals("true")){

                activityPackageBinding.rvVideo.setAdapter(new
                        PackageVideoAdapter(s_is_free,purachase_package_video_modelslist, mContext, new FilterClick() {
                    @Override
                    public void filterClick(int position) {
                        ArrayList<String> videolist = new ArrayList<>();
                        ArrayList<String> videotitle = new ArrayList<>();
                        for (int i = 0; i < purachase_package_video_modelslist.size(); i++) {
                            if (s_is_free.equals("0")) {
                                if (purachase_package_video_modelslist.get(i).getIs_free().equals("0")) {
                                } else {
                                    videolist.add(purachase_package_video_modelslist.get(i).getVideo());
                                    videotitle.add(purachase_package_video_modelslist.get(i).getVideo_title());
                                }
                            } else {
                                videolist.add(purachase_package_video_modelslist.get(i).getVideo());
                                videotitle.add(purachase_package_video_modelslist.get(i).getVideo_title());
                            }
                        }
                        int viewvdideo=Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;

                        if (s_is_free.equals("0")){
                            if (purachase_package_video_modelslist.get(position).getIs_free().equals("0")){
                                Toast.makeText(mContext, "To watch this amazing video, please purchase the package.", Toast.LENGTH_SHORT).show();
                            }else if (purachase_package_video_modelslist.get(position).getIs_free().equals("1")){
                                Intent in = new Intent(mContext,VideoActivity.class);
                                in.putExtra("URI",purachase_package_video_modelslist.get(position).getVideo());
                                in.putExtra("VIDEO_TITLE",purachase_package_video_modelslist.get(position).getVideo_title());
                                in.putExtra("VIDEO_DESC",purachase_package_video_modelslist.get(position).getVideo_desc());
                                in.putExtra("s_pack_id",s_pack_id);
                                in.putExtra("s_pack_name",s_pack_name);
                                in.putExtra("VIDEO_ID",purachase_package_video_modelslist.get(position).getVideo_id());
                                in.putExtra("is_free",s_is_free);
                                in.putExtra("videoType","1");
                                in.putExtra("from","0");
                                in.putExtra("position",position);
                                in.putExtra("viewduration",viewvdideo);
                                in.putStringArrayListExtra("videoArrayList", videolist);
                                in.putStringArrayListExtra("titleArrayList", videotitle);
//                                Bundle bundle=new Bundle();
//                                bundle.putParcelableArrayList("videoArrayList",purachase_package_video_modelslist);
//                                in.putExtras(bundle);
//                                System.out.println("pack id >>>>>>>>>>>>>>>>>>>>>>>>>>  "+s_pack_id);
//                                System.out.println("vid id >>>>>>>>>>>>>>>>>>>>>>>>>>    "+purachase_package_video_modelslist.get(position).getVideo());
                                startActivity(in);
                            }
                        }else {
                            Intent in = new Intent(mContext,VideoActivity.class);
                            in.putExtra("URI",purachase_package_video_modelslist.get(position).getVideo());
                            in.putExtra("VIDEO_TITLE",purachase_package_video_modelslist.get(position).getVideo_title());
                            in.putExtra("VIDEO_DESC",purachase_package_video_modelslist.get(position).getVideo_desc());
                            in.putExtra("s_pack_id",s_pack_id);
                            in.putExtra("s_pack_name",s_pack_name);
                            in.putExtra("VIDEO_ID",purachase_package_video_modelslist.get(position).getVideo_id());
                            in.putExtra("is_free",s_is_free);
                            in.putExtra("videoType","1");
                            in.putExtra("from","0");
                            in.putExtra("position",position);
                            in.putExtra("viewduration",viewvdideo);
                            in.putStringArrayListExtra("videoArrayList", videolist);
                            in.putStringArrayListExtra("titleArrayList", videotitle);
//                            Bundle bundle=new Bundle();
//                            bundle.putParcelableArrayList("videoArrayList",purachase_package_video_modelslist);
//                            in.putExtras(bundle);
//                            System.out.println("pack id >>>>>>>>>>>>>>>>>>>>>>>>>>  "+s_pack_id);
//                            System.out.println("vid id >>>>>>>>>>>>>>>>>>>>>>>>>>    "+purachase_package_video_modelslist.get(position).getVideo());
                            startActivity(in);
                        }
                    }
                }));
                activityPackageBinding.tvNoData.setVisibility(View.GONE);
                activityPackageBinding.rvVideo.setVisibility(View.VISIBLE);
                activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
            }else {
                activityPackageBinding.tvNoData.setVisibility(View.VISIBLE);
                activityPackageBinding.rvVideo.setVisibility(View.GONE);
                activityPackageBinding.tvNoData.setText("");
                activityPackageBinding.noDataFoundGif.setVisibility(View.VISIBLE);
                Glide.with(PackageActivity2.this).asGif().load(R.drawable.nodatagif).into(activityPackageBinding.noDataFoundGif);
            }
        }
    }

    class view_download_list_asy1 extends AsyncTask<String, String, String> {

        String status,s_id,s_desc,s_video_date,s_title,s_video,s_image,s_v_id,is_free="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            purachase_package_video_modelslist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {





            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_new_id",s_pack_id);
//            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());
//
            String response = webRequest.makeWebServiceCall(AllUrl.view_package_video_list,2,hashMap);
//
            System.out.println(" View defaenc_dvidn mypack video response >>>>>>>>>>>>>>>  " +response);
            System.out.println(" View defaenc_dvidn mypack video parameter >>>>>>>>>>>>>>>  " +hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status=jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("body");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    s_id = jsonObject1.getString("videos_id");
                    s_desc = jsonObject1.getString("video_description");
                    s_video_date = jsonObject1.getString("videos_date");
                    s_title = jsonObject1.getString("video_name");
                    s_video = jsonObject1.getString("videncd");
                    s_image = jsonObject1.getString("image");
                    s_v_id = jsonObject1.getString("videos_id");
                    if (jsonObject1.has("free_status")) {
                        is_free = jsonObject1.getString("free_status");
                    }
                    String videoviewtime;
                    String videotime;
                    if(jsonObject1.has("view_status")){

                        videoviewtime=jsonObject1.getString("view_status");
                        videotime=jsonObject1.getString("v_duration");
                    }else {

                        videoviewtime="0";
                        videotime="0";
                    }



                    Purachase_package_free_video_Model video_model = new Purachase_package_free_video_Model();
                    video_model.setVideo_id(s_v_id);
                    video_model.setVideo_title(s_title);
                    video_model.setVideo(s_video);
                    video_model.setVideo_desc(s_desc);
                    video_model.setVideo_date(s_video_date);
                    video_model.setVideo_image(s_image);
                    video_model.setIs_free(is_free);
                    video_model.setVideoview_Time(videoviewtime);
                    video_model.setVideototal_duration(videotime);
                    purachase_package_video_modelslist.add(video_model);

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
            if (status.equals("true")){

                activityPackageBinding.rvVideo.setAdapter(new
                        PackageVideoAdapter(s_is_free,purachase_package_video_modelslist, mContext, new FilterClick() {
                    @Override
                    public void filterClick(int position) {
                        ArrayList<String> videolist = new ArrayList<>();
                        ArrayList<String> videotitle = new ArrayList<>();
                        for (int i = 0; i < purachase_package_video_modelslist.size(); i++) {
                            if (s_is_free.equals("0")) {
                                if (purachase_package_video_modelslist.get(i).getIs_free().equals("0")) {
                                } else {
                                    videolist.add(purachase_package_video_modelslist.get(i).getVideo());
                                    videotitle.add(purachase_package_video_modelslist.get(i).getVideo_title());
                                }
                            } else {
                                videolist.add(purachase_package_video_modelslist.get(i).getVideo());
                                videotitle.add(purachase_package_video_modelslist.get(i).getVideo_title());
                            }
                        }
                        int viewvdideo=Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;

                        if (s_is_free.equals("0")){
                            if (purachase_package_video_modelslist.get(position).getIs_free().equals("0")){
                                Toast.makeText(mContext, "To watch this amazing video, please purchase the package.", Toast.LENGTH_SHORT).show();
                            }else if (purachase_package_video_modelslist.get(position).getIs_free().equals("1")){
                                Intent in = new Intent(mContext,VideoActivity.class);
                                in.putExtra("URI",purachase_package_video_modelslist.get(position).getVideo());
                                in.putExtra("VIDEO_TITLE",purachase_package_video_modelslist.get(position).getVideo_title());
                                in.putExtra("VIDEO_DESC",purachase_package_video_modelslist.get(position).getVideo_desc());
                                in.putExtra("s_pack_id",s_pack_id);
                                in.putExtra("s_pack_name",s_pack_name);
                                in.putExtra("VIDEO_ID",purachase_package_video_modelslist.get(position).getVideo_id());
                                in.putExtra("is_free",s_is_free);
                                in.putExtra("videoType","1");
                                in.putExtra("from","1");
                                in.putExtra("position",position);
                                in.putExtra("viewduration",viewvdideo);
                                in.putStringArrayListExtra("videoArrayList", videolist);
                                in.putStringArrayListExtra("titleArrayList", videotitle);
//                                Bundle bundle=new Bundle();
//                                bundle.putParcelableArrayList("videoArrayList",purachase_package_video_modelslist);
//                                in.putExtras(bundle);
//                                System.out.println("pack id >>>>>>>>>>>>>>>>>>>>>>>>>>  "+s_pack_id);
//                                System.out.println("vid id >>>>>>>>>>>>>>>>>>>>>>>>>>    "+purachase_package_video_modelslist.get(position).getVideo());
                                startActivity(in);
                            }
                        }else {
                            Intent in = new Intent(mContext,VideoActivity.class);
                            in.putExtra("URI",purachase_package_video_modelslist.get(position).getVideo());
                            in.putExtra("VIDEO_TITLE",purachase_package_video_modelslist.get(position).getVideo_title());
                            in.putExtra("VIDEO_DESC",purachase_package_video_modelslist.get(position).getVideo_desc());
                            in.putExtra("s_pack_id",s_pack_id);
                            in.putExtra("s_pack_name",s_pack_name);
                            in.putExtra("VIDEO_ID",purachase_package_video_modelslist.get(position).getVideo_id());
                            in.putExtra("is_free",s_is_free);
                            in.putExtra("videoType","1");
                            in.putExtra("from","1");
                            in.putExtra("position",position);
                            in.putExtra("viewduration",viewvdideo);
                            in.putStringArrayListExtra("videoArrayList", videolist);
                            in.putStringArrayListExtra("titleArrayList", videotitle);
//                            Bundle bundle=new Bundle();
//                            bundle.putParcelableArrayList("videoArrayList",purachase_package_video_modelslist);
//                            in.putExtras(bundle);
//                            System.out.println("pack id >>>>>>>>>>>>>>>>>>>>>>>>>>  "+s_pack_id);
//                            System.out.println("vid id >>>>>>>>>>>>>>>>>>>>>>>>>>    "+purachase_package_video_modelslist.get(position).getVideo());
                            startActivity(in);
                        }
                    }
                }));
                activityPackageBinding.tvNoData.setVisibility(View.GONE);
                activityPackageBinding.rvVideo.setVisibility(View.VISIBLE);
                activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
            }else {
                activityPackageBinding.tvNoData.setVisibility(View.VISIBLE);
                activityPackageBinding.rvVideo.setVisibility(View.GONE);
                activityPackageBinding.tvNoData.setText("");
                activityPackageBinding.noDataFoundGif.setVisibility(View.VISIBLE);
                Glide.with(PackageActivity2.this).asGif().load(R.drawable.nodatagif).into(activityPackageBinding.noDataFoundGif);
            }
        }
    }

    class view_PDF_list_asy extends AsyncTask<String, String, String> {

        String status,pdf_id,pdf_name,pdf_path,base_urls,date;
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pdfModelArrayList.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_new_id",s_pack_id);
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response =  webRequest.makeWebServiceCall(AllUrl.packages_view_package_pdf_listApi,2,hashMap);

            System.out.println("view_package_pdf_list response >>>>>>>>>>>>>>>  " +response);
            System.out.println("view_package_pdf_list parameter >>>>>>>>>>>>>>>  "+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status = jsonObject.getString("status");
                jsonArray = jsonObject.getJSONArray("body");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                    pdf_id = jsonObject1.getString("pdf_id");
                    pdf_name = jsonObject1.getString("pdf_name");
                    pdf_path = jsonObject1.getString("pdf_path");
                    base_urls = jsonObject1.getString("base_urls");
                    date = jsonObject1.getString("date");

                    PDFModel pdfModel = new PDFModel(pdf_id, pdf_name, pdf_path, base_urls, date);
                    pdfModelArrayList.add(pdfModel);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (status.equals("true")){
                activityPackageBinding.noDataFoundGif.setVisibility(View.GONE);
                activityPackageBinding.rvVideo.setVisibility(View.VISIBLE);
                activityPackageBinding.tvNoData.setVisibility(View.GONE);
                activityPackageBinding.rvVideo.setAdapter(new PdfAdapter(pdfModelArrayList, mContext, new FilterClick() {
                    @Override
                    public void filterClick(int position) {
                        if (s_is_free.equals("1")) {
                            String selected_pdf_file = pdfModelArrayList.get(position).getBase_urls()+pdfModelArrayList.get(position).getPdf_path();
                            Download_PDF(selected_pdf_file,pdfModelArrayList.get(position).getPdf_name());
                        } else if (s_is_free.equals("0")) {
                            Toast.makeText(mContext, "To download this amazing PDF, please purchase the package.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "To download this amazing PDF, please purchase the package.", Toast.LENGTH_SHORT).show();
                        }
                    }
                }));
            }else {
                activityPackageBinding.tvNoData.setVisibility(View.VISIBLE);
                activityPackageBinding.rvVideo.setVisibility(View.GONE);
                activityPackageBinding.tvNoData.setText("");
                activityPackageBinding.noDataFoundGif.setVisibility(View.VISIBLE);
                Glide.with(PackageActivity2.this).asGif().load(R.drawable.nodatagif).into(activityPackageBinding.noDataFoundGif);
            }
        }
    }

    public void Download_PDF(String pdf_file,String pdf_name){
        Toast.makeText(mContext, "Downloading Start...", Toast.LENGTH_SHORT).show();
        String url = pdf_file;
        DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
        request.setDescription("PDF Downloading...");
        request.setTitle(pdf_name);
// in order for this if to run, you must use the android 3.2 to compile your app
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            request.allowScanningByMediaScanner();
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        }
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, pdf_name+".pdf");

// get download service and enqueue file
        DownloadManager manager = (DownloadManager) getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
        manager.enqueue(request);

        BroadcastReceiver onComplete = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)){
                    Toast.makeText(context, "Successfully Downloaded", Toast.LENGTH_SHORT).show();
                }
            }
        };
        getActivity().registerReceiver(onComplete,new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
    }

    @Override
    public void onResume() {
        super.onResume();
        new view_download_list_asy().execute();
    }
}