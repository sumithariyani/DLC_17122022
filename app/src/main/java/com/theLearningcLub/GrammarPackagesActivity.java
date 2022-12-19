package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theLearningcLub.Model_Class.ClassModel1;
import com.theLearningcLub.adapter.ChaptervideoAdapter1;
import com.theLearningcLub.adapter.ChaptervideoAdapter1New;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityGrammarPackagesBinding;
import com.theLearningcLub.fragment.HomeFragment;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.FilterClickCart;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class GrammarPackagesActivity extends BaseFragment {

    ActivityGrammarPackagesBinding activityGrammarPackagesBinding;
    String DEVICEID;
    ChaptervideoAdapter1New adapter1New;
    ArrayList<ClassModel1> list1 = new ArrayList<>();
    String status,s_cat_id,s_alpha_id,s_offer_price,s_price,s_title,s_image,s_description,s_pack_id,
            s_rating,is_free;
    String pack_id,offer_price,price;

    String versionName;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityGrammarPackagesBinding = ActivityGrammarPackagesBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.grammar_packages));


        PackageManager pm = getActivity().getPackageManager();
        String pkgName = getActivity().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pkgInfo.versionName;

        if (activityGrammarPackagesBinding.rvGrammarPackages.getLayoutManager() == null){
            activityGrammarPackagesBinding.rvGrammarPackages.setLayoutManager(new GridLayoutManager(mContext,2));
        }

        DEVICEID = Settings.Secure.getString(getActivity().getContentResolver(), Settings.Secure.ANDROID_ID);

        new HomeGrAs().execute();
        new HomeVoAs1().execute();
        adapter1New = new ChaptervideoAdapter1New(mContext, list1, new FilterClick() {
            @Override
            public void filterClick(int position) {
                if(list1.get(position).getFrom()=="1"){

                    Fragment myFragment = new PackageActivity();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flFrameLayout, myFragment, "mywallet");
                    fragmentTransaction.addToBackStack("mywallet");
                    fragmentTransaction.commit();


                    Bundle bundle = new Bundle();
                    bundle.putString("image", list1.get(position).getImage_drawable());
                    bundle.putString("title", list1.get(position).getName());
                    bundle.putString("price", list1.get(position).getPrice());
                    bundle.putString("offerprice", list1.get(position).getOfferprice());
                    bundle.putString("Desc", list1.get(position).getDesc());
                    bundle.putString("packid", list1.get(position).getPack_id());
                    bundle.putString("userid", list1.get(position).getUserid());
                    bundle.putString("is_free", list1.get(position).getIs_free());
                    bundle.putString("rating", list1.get(position).getReting_bar());
                    bundle.putString("key", "1");
//                    Toast.makeText(mContext, ""+list1.get(position).getFrom(), Toast.LENGTH_SHORT).show();
                    myFragment.setArguments(bundle);
                }else{
                    Fragment myFragment = new PackageActivity2();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flFrameLayout, myFragment, "mywallet");
                    fragmentTransaction.addToBackStack("mywallet");
                    fragmentTransaction.commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("image", list1.get(position).getImage_drawable());
                    bundle.putString("title", list1.get(position).getName());
                    bundle.putString("price", list1.get(position).getPrice());
                    bundle.putString("offerprice", list1.get(position).getOfferprice());
                    bundle.putString("Desc", list1.get(position).getDesc());
                    bundle.putString("packid", list1.get(position).getPack_id());
                    bundle.putString("userid", list1.get(position).getUserid());
                    bundle.putString("is_free", list1.get(position).getIs_free());
                    bundle.putString("rating", list1.get(position).getReting_bar());
                    bundle.putString("key", "1");
//                    Toast.makeText(mContext, ""+list1.get(position).getFrom(), Toast.LENGTH_SHORT).show();
                    myFragment.setArguments(bundle);
                }


//                Fragment myFragment = new PackageActivity2();
//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.replace(R.id.flFrameLayout, myFragment, "mywallet");
//                fragmentTransaction.addToBackStack("mywallet");
//                fragmentTransaction.commit();
//
//                Bundle bundle = new Bundle();
//                bundle.putString("image", list1.get(position).getImage_drawable());
//                bundle.putString("title", list1.get(position).getName());
//                bundle.putString("price", list1.get(position).getPrice());
//                bundle.putString("offerprice", list1.get(position).getOfferprice());
//                bundle.putString("Desc", list1.get(position).getDesc());
//                bundle.putString("packid", list1.get(position).getPack_id());
//                bundle.putString("userid", list1.get(position).getUserid());
//                bundle.putString("is_free", list1.get(position).getIs_free());
//                bundle.putString("rating", list1.get(position).getReting_bar());
//                bundle.putString("key", "1");
//                bundle.putString("check",list1.get(position).getCkeck_list());
//                myFragment.setArguments(bundle);
            }
        }, new FilterClickCart() {
            @Override
            public void filterClickCart(int position) {
                pack_id = list1.get(position).getPack_id();
                price = list1.get(position).getPrice();
                offer_price = list1.get(position).getOfferprice();

                if(list1.get(position).getFrom()=="1"){
                    new AddtoCartAs2().execute();
                }else{
                    new AddtoCartAs1().execute();
                }

            }
        });
        RecyclerView.LayoutManager mLayoutManager2 = new GridLayoutManager(mContext, 2);
        activityGrammarPackagesBinding.rvGrammarPackages.setLayoutManager(mLayoutManager2);
        activityGrammarPackagesBinding.rvGrammarPackages.setItemAnimator(new DefaultItemAnimator());
        activityGrammarPackagesBinding.rvGrammarPackages.setAdapter(adapter1New);

        return activityGrammarPackagesBinding.getRoot();
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class HomeGrAs extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            try {
                list1.clear();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
            String response = request.makeWebServiceCall(AllUrl.vocabulary_view_newApi+"?vocabulary_id="+2+"&key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ versionName,2);

            System.out.print("vocabulary_id 2 request >>>>>>>>>>>>>>>>>"+AllUrl.vocabulary_view_newApi+"?vocabulary_id="+2+"&key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ versionName);
            System.out.print("vocabulary_id 2 responce >>>>>>>>>>>>>>>>>  "+response);

            try {

                JSONObject jsonObject = new JSONObject(response);

                status = jsonObject.getString("status");

                if (status.equals("success")) {
                    JSONArray contacts = jsonObject.getJSONArray("data");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        s_cat_id = c.getString("cat_id");
                        s_alpha_id = c.getString("alpha_id");
                        s_price = c.getString("price");
                        s_offer_price = c.getString("offer_price");
                        s_title = c.getString("pack_name");
                        s_image = c.getString("pack_image");
                        s_description = c.getString("description");
                        s_pack_id = c.getString("pack_id");
                        if (c.has("rateStar")){
                            s_rating = c.getString("rateStar");
                        }else{
                            s_rating="1.0";
                        }
                        is_free = c.getString("is_free");

                        ClassModel1 fruitModel1 = new ClassModel1();

                        fruitModel1.setPack_id(s_pack_id);
                        fruitModel1.setReting_bar(s_rating);
                        fruitModel1.setImage_drawable(s_image);
                        fruitModel1.setName(s_title);
                        fruitModel1.setOfferprice(s_offer_price);
                        fruitModel1.setDesc(s_description);
                        fruitModel1.setPrice(s_price);
                        fruitModel1.setIs_free(is_free);
                        fruitModel1.setCkeck_list("0");
                        fruitModel1.setFrom("0");
                        list1.add(fruitModel1);

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
            try {
                adapter1New.notifyDataSetChanged();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class HomeVoAs1 extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("android_version",Build.VERSION.RELEASE);
            hashMap.put("app_version",versionName);

            String response = request.makeWebServiceCall(AllUrl.packages_view_packagesApi,2,hashMap);

            System.out.print("view_packages params>>>>>>>>>>>>>>>>>"+hashMap);
            System.out.print("view_packages >>>>>>>>>>>>>>>>>"+response);


            try {
                JSONObject jsonObject = new JSONObject(response);

                status = jsonObject.getString("status");

                System.out.println("status >>>>>>>>>> "+status);

                if(status.equals("true")){
                    JSONArray contacts = jsonObject.getJSONArray("body");

                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        s_cat_id = c.getString("pack_new_id");
                        s_alpha_id = c.getString("pack_new_id");
                        s_price = c.getString("price");
                        s_offer_price = c.getString("offer_price");
                        s_title = c.getString("name");
                        s_image = c.getString("image");
                        s_description = c.getString("description");
                        s_pack_id = c.getString("pack_new_id");
                        if (c.has("rateStar")){
                            s_rating = c.getString("rateStar");
                        }else{
                            s_rating="1.0";
                        }

                        is_free = c.getString("is_free");

                        ClassModel1 fruitModel1 = new ClassModel1();

                        fruitModel1.setPack_id(s_pack_id);
                        fruitModel1.setReting_bar(s_rating);
                        fruitModel1.setImage_drawable(s_image);
                        fruitModel1.setName(s_title);
                        fruitModel1.setOfferprice(s_offer_price);
                        fruitModel1.setDesc(s_description);
                        fruitModel1.setPrice(s_price);
                        fruitModel1.setIs_free(is_free);
                        fruitModel1.setCkeck_list("1");
                        fruitModel1.setFrom("1");
                        list1.add(fruitModel1);
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
            try {
                adapter1New.notifyDataSetChanged();

            }catch (Exception exception){
                exception.printStackTrace();
            }
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

    class AddtoCartAs2 extends AsyncTask<String,String,String> {

        String message;

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
            hashMap.put("pack_type","1");
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