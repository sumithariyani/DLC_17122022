package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;

import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.theLearningcLub.adapter.VocabularyAdapter;
import com.theLearningcLub.adapter.VocabularyAdapter2;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityVocabularyPackageBinding;
import com.theLearningcLub.model.vocabulary.VocabularyModel;
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

public class VocabularyPackageActivity extends BaseFragment {

    ActivityVocabularyPackageBinding activityVocabularyPackageBinding;
    String DEVICEID;
    String pack_id,price,offer_price;

    String versionName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityVocabularyPackageBinding = ActivityVocabularyPackageBinding.inflate(inflater,container,false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.tvUserHello.setText(getResources().getString(R.string.vocabulary_packages));
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);

        PackageManager pm = getActivity().getPackageManager();
        String pkgName = getActivity().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pkgInfo.versionName;


/*

        Display display = requireActivity().getWindowManager().getDefaultDisplay();
        Point size = new Point();
        try {
            display.getRealSize(size);
        } catch (NoSuchMethodError err) {
            display.getSize(size);
        }
        int width = size.x;
        int height = size.y;

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams((int)(width/3),
                LinearLayout.LayoutParams.WRAP_CONTENT); // or set height to any fixed value you want

        your_layout.setLayoutParams(lp);
*/



        if (activityVocabularyPackageBinding.rvVocabularyPackages.getLayoutManager() == null) {
            activityVocabularyPackageBinding.rvVocabularyPackages.setLayoutManager(new GridLayoutManager(mContext, 2));
        }

        DEVICEID = Settings.Secure.getString(getActivity().getContentResolver(),Settings.Secure.ANDROID_ID);

        HomeVoApi();

        return activityVocabularyPackageBinding.getRoot();
    }

    private void HomeVoApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.vocabulary_view_newApi+"?vocabulary_id="+1+"&key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ versionName,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("HomeVoApi response>>>>>>>>>>>>>>>" + response);
                        System.out.println("HomeVoApi url>>>>>>>>>>>>>>>" + AllUrl.vocabulary_view_newApi+"?vocabulary_id="+1+"&key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ versionName);

                        try {
                            VocabularyModel vocabularyModel = new Gson().fromJson(response,VocabularyModel.class);
                            if (vocabularyModel.getStatus().equalsIgnoreCase(AllUrl.status)){
                                activityVocabularyPackageBinding.rvVocabularyPackages.setAdapter(new VocabularyAdapter2(vocabularyModel.getData(), mContext, new FilterClick() {
                                    @Override
                                    public void filterClick(int position) {
                                        Fragment myFragment = new PackageActivity2();
                                        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                                        fragmentTransaction.replace(R.id.flFrameLayout, myFragment, "packages");
                                        fragmentTransaction.addToBackStack("packages");
                                        fragmentTransaction.commit();

                                        Bundle bundle = new Bundle();
                                        bundle.putString("image", vocabularyModel.getData().get(position).getPackImage());
                                        bundle.putString("title", vocabularyModel.getData().get(position).getPackName());
                                        bundle.putString("price", vocabularyModel.getData().get(position).getPrice());
                                        bundle.putString("offerprice", vocabularyModel.getData().get(position).getOfferPrice());
                                        bundle.putString("Desc", vocabularyModel.getData().get(position).getDescription());
                                        bundle.putString("packid", vocabularyModel.getData().get(position).getPackId());
                                        bundle.putString("userid", sessionManager.getSavedUserId());
                                        bundle.putString("is_free", vocabularyModel.getData().get(position).getIsFree());
                                        bundle.putString("rating", vocabularyModel.getData().get(position).getRateStar());
                                        bundle.putString("key", "0");
                                        myFragment.setArguments(bundle);
                                    }
                                }, new FilterClickCart() {
                                    @Override
                                    public void filterClickCart(int position) {
                                        pack_id = vocabularyModel.getData().get(position).getPackId();
                                        price = vocabularyModel.getData().get(position).getPrice();
                                        offer_price = vocabularyModel.getData().get(position).getOfferPrice();
                                        new AddtoCartAs1().execute();
                                    }
                                }));
                            }
                            hideProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("HomeVoApi error>>>>>>>>>>>>>."+error);
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
        }else {
            Intent intent = new Intent(getActivity(), NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

    private void Otpexitdialog(String msg) {
        Dialog dialog1 = new Dialog(mContext);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog1.setContentView(R.layout.otpexit_dialog);
        dialog1.setCancelable(false);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView tvMsg = dialog1.findViewById(R.id.tvMsg);
        tvMsg.setText(msg);

        (dialog1.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.clearSession();
                Intent in = new Intent(mContext, LoginActivity.class);
                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(in);
                mContext.finish();

            }
        });

        dialog1.show();
        dialog1.getWindow().setAttributes(lp);
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