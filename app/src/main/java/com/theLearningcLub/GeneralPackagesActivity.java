package com.theLearningcLub;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.theLearningcLub.adapter.GeneralAdapter;
import com.theLearningcLub.adapter.GeneralAdapter2;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityGeneralPackagesBinding;
import com.theLearningcLub.model.general.BodyGeneralKnowledgeModel;
import com.theLearningcLub.model.general.GeneralKnowledgeModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.FilterClickCart;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GeneralPackagesActivity extends BaseFragment{

    ActivityGeneralPackagesBinding activityGeneralPackagesBinding;
    String DEVICEID;
    String pack_id,offer_price,price;

    String versionName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityGeneralPackagesBinding = ActivityGeneralPackagesBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        PackageManager pm = getActivity().getPackageManager();
        String pkgName = getActivity().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pkgInfo.versionName;

        if (activityGeneralPackagesBinding.rvGeneralPackages.getLayoutManager() == null){
            activityGeneralPackagesBinding.rvGeneralPackages.setLayoutManager(new GridLayoutManager(mContext,2));
        }

        DEVICEID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        HomeVoApi();

        return activityGeneralPackagesBinding.getRoot();
    }

    private void HomeVoApi() {
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.get_category_viewApi + "?key=" + sessionManager.getLoginkey() + "&device_id=" + DEVICEID + "&user_id=" + sessionManager.getSavedUserId() + "&android_version=" + Build.VERSION.RELEASE + "&app_version=" + versionName,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("HomeVoApi response>>>>>>>>>>>>>>>" + response);
                        System.out.println("HomeVoApi url>>>>>>>>>>>>>>>" + AllUrl.get_category_viewApi + "?key=" + sessionManager.getLoginkey() + "&device_id=" + DEVICEID + "&user_id=" + sessionManager.getSavedUserId() + "&android_version=" + Build.VERSION.RELEASE + "&app_version=" + versionName);

                        try {
                            GeneralKnowledgeModel generalKnowledgeModel = new Gson().fromJson(response, GeneralKnowledgeModel.class);
                            if (generalKnowledgeModel.getStatus()) {
                                for (int i = 0; i < generalKnowledgeModel.getBody().size(); i++) {
                                    BodyGeneralKnowledgeModel bodyGeneralKnowledgeModel = generalKnowledgeModel.getBody().get(i);
                                    for (int j = 0; j < bodyGeneralKnowledgeModel.getData().size(); j++) {
                                        activityGeneralPackagesBinding.rvGeneralPackages.setAdapter(new GeneralAdapter2(bodyGeneralKnowledgeModel.getData(), mContext, new FilterClick() {
                                            @Override
                                            public void filterClick(int position) {
                                                Fragment fragment = new PackageActivity2();
                                                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                                fragmentTransaction.replace(R.id.flFrameLayout,fragment,"myCart");
                                                fragmentTransaction.addToBackStack("myCart");
                                                fragmentTransaction.commit();

                                                Bundle bundle = new Bundle();
                                                bundle.putString("image", bodyGeneralKnowledgeModel.getData().get(position).getPackImage());
                                                bundle.putString("title", bodyGeneralKnowledgeModel.getData().get(position).getPackName());
                                                bundle.putString("price", bodyGeneralKnowledgeModel.getData().get(position).getPrice());
                                                bundle.putString("offerprice", bodyGeneralKnowledgeModel.getData().get(position).getOfferPrice());
                                                bundle.putString("Desc", bodyGeneralKnowledgeModel.getData().get(position).getDescription());
                                                bundle.putString("packid", bodyGeneralKnowledgeModel.getData().get(position).getPackId());
                                                bundle.putString("userid", sessionManager.getSavedUserId());
                                                bundle.putString("is_free", bodyGeneralKnowledgeModel.getData().get(position).getIsFree());
                                                bundle.putString("rating", bodyGeneralKnowledgeModel.getData().get(position).getRateStar());
                                                bundle.putString("key", "1");
                                                fragment.setArguments(bundle);
                                            }
                                        }, new FilterClickCart() {
                                            @Override
                                            public void filterClickCart(int position) {
                                                pack_id = bodyGeneralKnowledgeModel.getData().get(position).getPackId();
                                                price = bodyGeneralKnowledgeModel.getData().get(position).getPrice();
                                                offer_price = bodyGeneralKnowledgeModel.getData().get(position).getOfferPrice();
                                                new AddtoCartAs1().execute();
                                            }
                                        }));
                                    }
                                }
                            }
                            hideProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("HomeVoApi error>>>>>>>>>>>>>." + error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("HomeVoApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        } else {
            Intent intent = new Intent(getActivity(), NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext, getResources().getString(R.string.net_connection));
        }
    }

    class AddtoCartAs1 extends AsyncTask<String,String,String> {

        String message,status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("pack_id",pack_id);
            hashMap.put("pack_type","0");
            if (offer_price.isEmpty()){
                hashMap.put("price",price);
            }else{
                hashMap.put("price",offer_price);
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

    private void CartCountApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.count_cartApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        String status,s_cartcount;

                        System.out.println("CartCountApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            s_cartcount = jsonObject.getString("cart_count");

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
}