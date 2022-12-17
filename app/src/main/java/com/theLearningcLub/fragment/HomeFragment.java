package com.theLearningcLub.fragment;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.BuildConfig;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.theLearningcLub.GeneralPackagesActivity;
import com.theLearningcLub.GrammarPackagesActivity;
import com.theLearningcLub.HomeActivity;
import com.theLearningcLub.LoginActivity;
import com.theLearningcLub.Model_Class.AdvertiseModel;
import com.theLearningcLub.Model_Class.ClassModel1;
import com.theLearningcLub.Model_Class.Contact_Sync_Model;
import com.theLearningcLub.NoInternetActivity;
import com.theLearningcLub.PackageActivity;
import com.theLearningcLub.PackageActivity2;
import com.theLearningcLub.QuizActivity;
import com.theLearningcLub.R;
import com.theLearningcLub.SearchActivity;
import com.theLearningcLub.VocabularyPackageActivity;
import com.theLearningcLub.adapter.AdvertiseAdapter;
import com.theLearningcLub.adapter.ChaptervideoAdapter;
import com.theLearningcLub.adapter.ChaptervideoAdapter1;
import com.theLearningcLub.adapter.GeneralAdapter;
import com.theLearningcLub.adapter.VocabularyAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.FragmentHomeBinding;
import com.theLearningcLub.model.general.BodyGeneralKnowledgeModel;
import com.theLearningcLub.model.general.GeneralKnowledgeModel;
import com.theLearningcLub.model.vocabulary.VocabularyModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.FilterClickCart;
import com.theLearningcLub.utils.VideoEnabledWebChromeClient;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class HomeFragment extends BaseFragment implements View.OnClickListener{

    public static FragmentHomeBinding fragmentHomeBinding;

    ArrayList<ClassModel1> list1 = new ArrayList<>();
    String s_price,s_title,s_cat_id,s_alpha_id,s_offer_price,s_description;
    String s_image,s_pack_id;
    String s_rating,is_free;
    private ChaptervideoAdapter1 adapter1;
    private ChaptervideoAdapter adapter;
    ArrayList<AdvertiseModel> advertiseModelArrayList = new ArrayList<>();
    private AdvertiseAdapter advertiseAdapter;
    private int dotscount;
    private ImageView[] dots;
    String user_status,id,video,status,s_cartcount,allcontact = "",DEVICEID;
    boolean s_contactsyanc;
    ArrayList<Contact_Sync_Model> NameList = new ArrayList<>();
    Fragment fragment;
    String pack_id,offer_price,price;
    String versionName;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentHomeBinding = FragmentHomeBinding.inflate(inflater, container, false);
        PackageManager pm = getActivity().getPackageManager();
        String pkgName = getActivity().getPackageName();
        PackageInfo pkgInfo = null;
        try {
            pkgInfo = pm.getPackageInfo(pkgName, 0);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        versionName = pkgInfo.versionName;
        fragmentHomeBinding.tvPayNow.setOnClickListener(this);
        fragmentHomeBinding.etSearch.setOnClickListener(this);
        fragmentHomeBinding.tvMore.setOnClickListener(this);
        fragmentHomeBinding.tvGrammarMore.setOnClickListener(this);
        fragmentHomeBinding.tvGeneralMore.setOnClickListener(this);
        fragmentHomeBinding.btn.setOnClickListener(this);

        HomeActivity.ivWallet.setVisibility(View.VISIBLE);


        fragmentHomeBinding.supportCallNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:8989780888"));
                startActivity(callIntent);
            }
        });

        fragmentHomeBinding.supportCallNumber2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent callIntent = new Intent(Intent.ACTION_DIAL);
                callIntent.setData(Uri.parse("tel:8989780888"));
                startActivity(callIntent);
            }
        });

        DEVICEID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("android version name>>>>>>>>>>>>>" +Build.VERSION.RELEASE);

//        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//        bottomSheetDashboard.getMenu().getItem(0).setChecked(true);

        HomeActivity.iv_menu_main.setVisibility(View.VISIBLE);
        HomeActivity.tvUserHello.setText(getResources().getString(R.string.hello) + " " + HomeActivity.s_first);
//        HomeActivity.tvUserHello.setText(getResources().getString(R.string.hello)+" "+sessionManager.getSavedNAME());
        HomeActivity.ivBack.setVisibility(View.GONE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);

        // Initialize the VideoEnabledWebChromeClient and set event handlers
        //noinspection all
        View loadingView = getLayoutInflater().inflate(R.layout.view_loading_video, null); // Your own view, read class comments
        // See all available constructors...
        // Subscribe to standard events, such as onProgressChanged()...
        // Your code...
        VideoEnabledWebChromeClient webChromeClient = new VideoEnabledWebChromeClient(
                fragmentHomeBinding.nonVideoLayout, fragmentHomeBinding.videoLayout,
                loadingView, fragmentHomeBinding.wv) // See all available constructors...
        {
            // Subscribe to standard events, such as onProgressChanged()...
            @Override
            public void onProgressChanged(WebView view, int progress) {
                // Your code...
            }
        };
        webChromeClient.setOnToggledFullscreen(new VideoEnabledWebChromeClient.ToggledFullscreenCallback() {
            @Override
            public void toggledFullscreen(boolean fullscreen) {
                // Your code to handle the full-screen change, for example showing and hiding the title bar. Example:
                WindowManager.LayoutParams attrs = mContext.getWindow().getAttributes();
                if (fullscreen) {
                    attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    mContext.getWindow().setAttributes(attrs);
                    //noinspection all
                    mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE);
                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                } else {
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_FULLSCREEN;
                    attrs.flags &= ~WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    mContext.getWindow().setAttributes(attrs);
                    //noinspection all
                    mContext.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                }
            }
        });
        fragmentHomeBinding.wv.setWebChromeClient(webChromeClient);
        // Call private class InsideWebViewClient
        fragmentHomeBinding.wv.setWebViewClient(new InsideWebViewClient());

//        myArrayAdapter2 = new MyArrayAdapter2(mContext);
//        fragmentHomeBinding.rvGeneralKnowledge.setAdapter(myArrayAdapter2);

        RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
        fragmentHomeBinding.rvVocabulary.setLayoutManager(mLayoutManager1);
        fragmentHomeBinding.rvVocabulary.setItemAnimator(new DefaultItemAnimator());

        adapter1 = new ChaptervideoAdapter1(mContext, list1, new FilterClick() {
            @Override
            public void filterClick(int position) {
                if(list1.get(position).getFrom()=="1"){
                    fragment = new PackageActivity();
                    loadFragment(fragment);
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
                    fragment.setArguments(bundle);
                }else{
                    fragment = new PackageActivity2();
                    loadFragment(fragment);
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
                    fragment.setArguments(bundle);
                }

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
        RecyclerView.LayoutManager mLayoutManager2 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
        fragmentHomeBinding.rvGrammar.setLayoutManager(mLayoutManager2);
        fragmentHomeBinding.rvGrammar.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.rvGrammar.setAdapter(adapter1);

        advertiseAdapter = new AdvertiseAdapter(mContext,advertiseModelArrayList);
        RecyclerView.LayoutManager mLayoutManager3 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
        fragmentHomeBinding.rvImageSlider.setLayoutManager(mLayoutManager3);
        fragmentHomeBinding.rvImageSlider.setItemAnimator(new DefaultItemAnimator());
        fragmentHomeBinding.rvImageSlider.setAdapter(advertiseAdapter);

        try {
            user_status = "4";
            HomeVoApi();
            new HomeGrAs().execute();
//            new ViewAllCategoryAs().execute();
            HomeGeneralApi();
            UserStatusApi();
            CheckLoginStatusApi();
            CartCountApi();
            CheckTypeApi();
        }catch (Exception ex){
            ex.printStackTrace();
        }

        return fragmentHomeBinding.getRoot();
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.flFrameLayout, fragment)
                    .addToBackStack("gghh")
                    .commit();
            return true;
        }
        return false;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvPayNow:
//                fragment = new QuizActivity();
//                loadFragment(fragment);

                FragmentTransaction fragmentTransaction1 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.flFrameLayout, new QuizActivity(), "quiz");
                fragmentTransaction1.addToBackStack("quiz");
                fragmentTransaction1.commit();

                HomeActivity.frag_count = 1;

                break;
            case R.id.etSearch:
                fragment = new SearchActivity();
                loadFragment(fragment);
//                HomeActivity.tvUserHello.setText("");
//                HomeActivity.iv_menu_main.setVisibility(View.GONE);
//                HomeActivity.ivBack.setVisibility(View.VISIBLE);
//                HomeActivity.ivNotification.setVisibility(View.VISIBLE);
//                HomeActivity.rlCart.setVisibility(View.VISIBLE);
                break;
            case R.id.tvMore:
//                fragment = new VocabularyPackageActivity();
//                loadFragment(fragment);

                FragmentTransaction fragmentTransaction2 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.flFrameLayout, new VocabularyPackageActivity(), "quiz");
                fragmentTransaction2.addToBackStack("quiz");
                fragmentTransaction2.commit();

                HomeActivity.frag_count = 1;

                break;
            case R.id.tvGeneralMore:
//                fragment = new GeneralPackagesActivity();
//                loadFragment(fragment);

                FragmentTransaction fragmentTransaction3 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.flFrameLayout, new GeneralPackagesActivity(), "quiz");
                fragmentTransaction3.addToBackStack("quiz");
                fragmentTransaction3.commit();

                HomeActivity.frag_count = 1;

                break;
            case R.id.tvGrammarMore:
//                fragment = new GrammarPackagesActivity();
//                loadFragment(fragment);

                FragmentTransaction fragmentTransaction4 = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.flFrameLayout, new GrammarPackagesActivity(), "quiz");
                fragmentTransaction4.addToBackStack("quiz");
                fragmentTransaction4.commit();

                HomeActivity.frag_count = 1;

                break;
            case R.id.btn:
                changeScreenOrientation();
                break;
        }
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
                        System.out.println("HomeVoApi url>>>>>>>>>>>>>>>" + AllUrl.vocabulary_view_newApi+"?vocabulary_id="+1+"&key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+versionName);

                        try {
                            VocabularyModel vocabularyModel = new Gson().fromJson(response,VocabularyModel.class);
                            if (vocabularyModel.getStatus().equalsIgnoreCase(AllUrl.status)){
                                fragmentHomeBinding.rvVocabulary.setAdapter(new VocabularyAdapter(vocabularyModel.getData(), mContext, new FilterClick() {
                                    @Override
                                    public void filterClick(int position) {
                                        fragment = new PackageActivity2();
                                        loadFragment(fragment);
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
                                        bundle.putString("key", "1");
                                        fragment.setArguments(bundle);
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
                            }else {
                                JSONObject jsonObject = new JSONObject(response);
                                String status = jsonObject.getString("status");
                                String messages = jsonObject.getString("messages");
                                if (status.equals("false")){
                                    Otpexitdialog(messages);
                                }
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
            Intent intent = new Intent(requireActivity(), NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
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

                        if (s_rating != null) {
                            s_rating = "1.0";
                        } else {
                            s_rating = c.getString("rateStar");
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
                adapter1.notifyDataSetChanged();
                new AdvertiseAsynctask().execute();
            }catch (Exception exception){
                exception.printStackTrace();
            }
        }
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
                        s_rating = c.getString("rateStar");
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
                adapter1.notifyDataSetChanged();
                new HomeVoAs1().execute();
            }catch (Exception ex){
                ex.printStackTrace();
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

    class VideoAs extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("type","Home");
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response = request.makeWebServiceCall(AllUrl.defembedecdem_vidApi, 2,hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");

                JSONArray contacts = jsonObject.getJSONArray("data");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);
                    video = c.getString("videncd");
                    id = c.getString("id");
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
                    video=video+"?defaviddb="+id;
                    fragmentHomeBinding.wv.loadUrl(video);

                    fragmentHomeBinding.wv.getSettings().setJavaScriptEnabled(true);
                    fragmentHomeBinding.wv.getSettings().setDomStorageEnabled(true);
                    fragmentHomeBinding.wv.getSettings().setAppCacheEnabled(true);
                    fragmentHomeBinding.wv.getSettings().setAppCachePath(mContext.getFilesDir().getAbsolutePath() + "/cache");
                    fragmentHomeBinding.wv.getSettings().setDatabaseEnabled(true);
                    fragmentHomeBinding.wv.getSettings().setDatabasePath(mContext.getFilesDir().getAbsolutePath() + "/databases");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    class AdvertiseAsynctask extends AsyncTask<String, String, String> {

        String advertise_image;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                advertiseModelArrayList.clear();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
//            hashMap.put("type","Home");
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response = request.makeWebServiceCall(AllUrl.get_adsApi, 2,hashMap);

            try {

                System.out.print("get_ads >>>>>>>>>>>>>>>>>" + response);
                System.out.print("get_ads parameter >>>>>>>>>>>>>>>>>" + hashMap);

                JSONObject jsonObject = new JSONObject(response);
                //JSONObject jsonObject1=jsonObject.getJSONObject("response");
                status = jsonObject.getString("status");

                JSONArray contacts = jsonObject.getJSONArray("datatwo");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    advertise_image = c.getString("advertise_image");

                    AdvertiseModel advertiseModel = new AdvertiseModel();
                    advertiseModel.setAdvertise_image(advertise_image);
                    advertiseModelArrayList.add(advertiseModel);
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
                advertiseAdapter.notifyDataSetChanged();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }
    }

    private void HomeGeneralApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.get_category_viewApi+"?key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ versionName,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("HomeGeneralApi response>>>>>>>>>>>>>>>" + response);
                        System.out.println("HomeGeneralApi url>>>>>>>>>>>>>>>" + AllUrl.get_category_viewApi+"?key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ versionName);

                        try {
                            GeneralKnowledgeModel generalKnowledgeModel = new Gson().fromJson(response,GeneralKnowledgeModel.class);
                            if (generalKnowledgeModel.getStatus()){
                                for (int i = 0; i < generalKnowledgeModel.getBody().size(); i++) {
                                    BodyGeneralKnowledgeModel bodyGeneralKnowledgeModel = generalKnowledgeModel.getBody().get(i);
                                    for (int j = 0; j < bodyGeneralKnowledgeModel.getData().size(); j++) {
                                        fragmentHomeBinding.rvGeneralKnowledge.setAdapter(new GeneralAdapter(bodyGeneralKnowledgeModel.getData(),mContext,new FilterClick() {
                                            @Override
                                            public void filterClick(int position) {
                                                fragment = new PackageActivity2();
                                                loadFragment(fragment);
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
                                        },new FilterClickCart() {
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
                System.out.println("HomeGeneralApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("HomeGeneralApi params>>>>>>>>>>>>>>" + params);
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

//    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
//    class ViewAllCategoryAs extends AsyncTask<String, String, String> {
//
//        JSONArray jsonArray;
//        String cat_name;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//        }
//
//        @Override
//        protected String doInBackground(String... strings){
//
//            WebRequest request=new WebRequest();
//
//            String allcatresponse = request.makeWebServiceCall(AllUrl.get_category_viewApi+"?key="+ sessionManager.getLoginkey()+"&device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ BuildConfig.VERSION_NAME,2);
//
//
//            System.out.print("get_category_view >>>>>>>>>>>>>>>>>"+allcatresponse);
//            System.out.print("key=+ prefManagerSecond.getLoginkey()"+"device_id="+ DEVICEID+"&user_id="+ sessionManager.getSavedUserId()+"&android_version="+Build.VERSION.RELEASE+"&app_version="+ BuildConfig.VERSION_NAME);
//
//            try {
//                JSONObject jsonObject = new JSONObject(allcatresponse);
//
//                status = jsonObject.getString("status");
//
//                System.out.println("status >>>>>>>>>> "+status);
//
//                if(status.equals("true")) {
//                    jsonArray = jsonObject.getJSONArray("body");
//                    for(int i=0;i<jsonArray.length();i++) {
//                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
//                        cat_name = jsonObject1.getString("cat_name");
//                        JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
//                        eventSubModelClasses = new ArrayList<>();
//                        for (int i1=0;i1<jsonArray1.length();i1++) {
//                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i1);
//                            all_s_cat_id = jsonObject2.getString("cat_id");
//                            all_s_alpha_id = jsonObject2.getString("alpha_id");
//                            all_s_price = jsonObject2.getString("price");
//                            all_s_offer_price = jsonObject2.getString("offer_price");
//                            all_s_title = jsonObject2.getString("pack_name");
//                            all_s_image = jsonObject2.getString("pack_image");
//                            all_s_description = jsonObject2.getString("description");
//                            all_s_pack_id = jsonObject2.getString("pack_id");
//                            all_s_rating = jsonObject2.getString("rateStar");
//                            all_is_free = jsonObject2.getString("is_free");
//
//                            // System.out.println("event>>>>>>>>>>>>>>>>>>>>>>>>>"+event_name);
//                            EventSubModelClass eventSubModelClass = new EventSubModelClass();
//
//                            eventSubModelClass.setPack_id(all_s_pack_id);
//                            eventSubModelClass.setReting_bar(all_s_rating);
//                            eventSubModelClass.setImage_drawable(all_s_image);
//                            eventSubModelClass.setName(all_s_title);
//                            eventSubModelClass.setOfferprice(all_s_offer_price);
//                            eventSubModelClass.setDesc(all_s_description);
//                            eventSubModelClass.setPrice(all_s_price);
//                            eventSubModelClass.setIs_free(all_is_free);
//                            eventSubModelClasses.add(eventSubModelClass);
//                        }
//                        EventMainModelClass eventMainModelClass = new EventMainModelClass(cat_name,eventSubModelClasses);
//                        eventMainModelClasses.add(eventMainModelClass);
//                    }
//                }
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            return null;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//            try {
////                myArrayAdapter2.notifyDataSetChanged();
//                if(status.equals("true")) {
//
//                    if (jsonArray.isNull(0)){
//
//                    }else {
//
//                    }
//                }else {
//
//                }
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
//        }
//    }

//    //---------------------Array Adapter-------------------------------
//    private class MyArrayAdapter2 extends BaseAdapter {
//
//        LayoutInflater mInflater;
//
//
//        public MyArrayAdapter2(Activity con) {
//            mInflater = LayoutInflater.from(con);
//        }
//
//        @Override
//        public int getCount() {
//            // TODO Auto-generated method stub
//            return eventMainModelClasses.size();
//        }
//
//        @Override
//        public Object getItem(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }
//
//        @Override
//        public long getItemId(int position) {
//            // TODO Auto-generated method stub
//            return position;
//        }
//
//
//        @Override
//        public View getView(final int position, View convertView, ViewGroup parent) {
//            // TODO Auto-generated method stub
//            final ListContent2 holder;
//            View v = convertView;
//
//            if (v == null) {
//                v = mInflater.inflate(R.layout.row_category_search, null);
//                holder = new ListContent2();
//                holder.nonlistview = v.findViewById(R.id.recycler);
//                holder.txt_monthname = v.findViewById(R.id.txtvocab);
//                holder.tvMore = v.findViewById(R.id.tvMore);
//                v.setTag(holder);
//            } else {
//                holder = (ListContent2) v.getTag();
//            }
//            try {
//                holder.tvMore.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        fragment = new GeneralPackagesActivity();
//                        loadFragment(fragment);
//                    }
//                });
//                ArrayList<EventSubModelClass> data = eventMainModelClasses.get(position).getsList();
//                myArrayAdapter = new ChaptervideoAdapter2(mContext, data, new FilterClick() {
//                    @Override
//                    public void filterClick(int position) {
//                        fragment = new PackageActivity();
//                        loadFragment(fragment);
//                        Bundle bundle = new Bundle();
//                        bundle.putString("image",data.get(position).getImage_drawable());
//                        bundle.putString("title",data.get(position).getName());
//                        bundle.putString("price",data.get(position).getPrice());
//                        bundle.putString("offerprice",data.get(position).getOfferprice());
//                        bundle.putString("Desc",data.get(position).getDesc());
//                        bundle.putString("packid",data.get(position).getPack_id());
//                        bundle.putString("userid",data.get(position).getUserid());
//                        bundle.putString("is_free",data.get(position).getIs_free());
//                        bundle.putString("rating",data.get(position).getReting_bar());
//                        bundle.putString("key","1");
//                        fragment.setArguments(bundle);
//                    }
//                });
//                RecyclerView.LayoutManager mLayoutManager1 = new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL,false);
//                holder.nonlistview.setLayoutManager(mLayoutManager1);
//                holder.nonlistview.setItemAnimator(new DefaultItemAnimator());
//                holder.nonlistview.setAdapter(myArrayAdapter);
//                holder.txt_monthname.setText(eventMainModelClasses.get(position).getMonth_name());
//                myArrayAdapter.notifyDataSetChanged();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//            return v;
//        }
//
//    }
//
//    static class ListContent2 {
//        TextView txt_monthname,tvMore;
//        RecyclerView nonlistview;
//    }

    private void CheckTypeApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.dlc_get_slide_statusApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("CheckTypeApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String s_status = jsonObject.getString("status");

                            JSONArray contacts = jsonObject.getJSONArray("body");

                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);

                                String chk_img = c.getString("image");
                                String chk_video = c.getString("video");

                                System.out.println("chk_img condition >>>>>>>>>>>>>>>>>"+chk_img);

                                if (chk_img.equals("true")){
                                    new GetImageSliderTask().execute();
                                    fragmentHomeBinding.nonVideoLayout.setVisibility(View.GONE);
                                }else {
                                    new VideoAs().execute();
                                    fragmentHomeBinding.nonVideoLayout.setVisibility(View.VISIBLE);
                                }
                            }

                            hideProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("CheckTypeApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("CheckTypeApi params>>>>>>>>>>>>>>" + params);
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

    private void changeScreenOrientation() {

        int orientation = getResources().getConfiguration().orientation;

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            fragmentHomeBinding.bottomHome.setVisibility(View.VISIBLE);
        } else {

            mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            fragmentHomeBinding.bottomHome.setVisibility(View.GONE);
        }
        if (Settings.System.getInt(mContext.getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    mContext.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }, 4000);
        }
    }

//logout otp

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

    private class InsideWebViewClient extends WebViewClient {
        @Override
        // Force links to be opened inside WebView and not in Default Browser
        // Thanks http://stackoverflow.com/a/33681975/1815624
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
//            System.out.println("current page url>>>>>  "+view.getUrl());
        }

        @Override
        public void onLoadResource(WebView view, String url) {
            super.onLoadResource(view, url);

        }


        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
        }


        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            super.onReceivedError(view, errorCode, description, failingUrl);

            view.loadUrl("file:///android_asset/error.html");

        }
    }

    private class GetImageSliderTask extends AsyncTask<String,String,String> {

        String slider_img,pkgstatus;
        ArrayList<String> mainpkgimg = new ArrayList<>();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            try {
                mainpkgimg.clear();
            }catch (Exception ex){
                ex.printStackTrace();
            }

        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap = new HashMap<>();
            String response = webRequest.makeWebServiceCall(AllUrl.get_slide_image_listApi,2);
            System.out.println("get_slide_image_list response>>>>>>>>>>>>>>>>>>>>>>> "+response);

            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);

                    pkgstatus = jsonObj.getString("status");
                    if (pkgstatus.equals("true")){
                        JSONArray dataarr = jsonObj.getJSONArray("body");
                        // looping through All Contacts
                        for (int i = 0; i < dataarr.length(); i++) {
                            JSONObject c = dataarr.getJSONObject(i);
                            slider_img = c.getString("image");
                            mainpkgimg.add(slider_img);

                            System.out.println("image 1>>>>>>>>>>>>>"+slider_img);
                        }
                    }
                } catch (final JSONException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            try {
                fragmentHomeBinding.viewpager.setAdapter(new ImagePagerAdapter(mContext,mainpkgimg));

                dotscount = Objects.requireNonNull(fragmentHomeBinding.viewpager.getAdapter()).getCount();
                dots = new ImageView[dotscount];

                for(int i = 0; i < dotscount; i++){

                    dots[i] = new ImageView(mContext);
                    dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.nonactive_dot));

                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                            LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                    params.setMargins(8, 0, 8, 0);

                    fragmentHomeBinding.layoutDots.addView(dots[i], params);

                }

                dots[0].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

                fragmentHomeBinding.viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {

                        for(int i = 0; i< dotscount; i++){
                            dots[i].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.nonactive_dot));
                        }

                        dots[position].setImageDrawable(ContextCompat.getDrawable(mContext, R.drawable.active_dot));

                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public class ImagePagerAdapter extends PagerAdapter {

        Context context;
        LayoutInflater layoutInflater;
        ArrayList<String> arrayList;


        public ImagePagerAdapter(Context context, ArrayList<String> arrayList) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            if(arrayList != null){
                return arrayList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            View itemView = layoutInflater.inflate(R.layout.row_img_slide, container, false);

            ImageView productimg = itemView.findViewById(R.id.imgcover);

            System.out.println("image 333>>>>"+arrayList.get(position));

            Glide.with(mContext).load(arrayList.get(position).replace("http://","https://")).placeholder(R.color.white).into(productimg);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }

    private void UserStatusApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.update_user_statusApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
                        hideProgressDialog();

                        System.out.println("UserStatusApi response>>>>>>>>>>>>>>>" + response);

                    }, error -> {
                System.out.println("UserStatusApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("user_status", user_status);
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("UserStatusApi params>>>>>>>>>>>>>>" + params);
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

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(mContext.getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /*-----------------------------------AsyncTask -------------------------------- */
    private void checksynkApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.check_sinksApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("checksynkApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                            status = jsonObject1.getString("status");
                            String message = jsonObject1.getString("msg");

                            if (message.equals("Not Sink")) {
                                sessionManager.setContactSyanc(false);
                                s_contactsyanc = sessionManager.getContactSyanc();
                                numbersyncApi();
                            } else {
                                sessionManager.setContactSyanc(false);
                                s_contactsyanc = sessionManager.getContactSyanc();
                                CommonFunction.showToastSingle(mContext, message);
                            }

                            hideProgressDialog();

                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }

                    }, error -> {
                System.out.println("checksynkApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("checksynkApi params>>>>>>>>>>>>>>" + params);
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

    private void numbersyncApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            for (int i = 0; i < NameList.size(); i++) {
                allcontact = allcontact + NameList.get(i).getName() + NameList.get(i).getNumber() + ",";
            }
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.contact_sinkApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("CheckTypeApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                            String message = jsonObject1.getString("msg");
                            status = jsonObject1.getString("status");
                            CommonFunction.showToastSingle(mContext, message);

                            hideProgressDialog();
                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }

                    }, error -> {
                System.out.println("CheckTypeApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("contact", allcontact);
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("CheckTypeApi params>>>>>>>>>>>>>>" + params);
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

    //------------------------Check Login status---------------------
    private void CheckLoginStatusApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            for (int i = 0; i < NameList.size(); i++) {
                allcontact = allcontact + NameList.get(i).getName() + NameList.get(i).getNumber() + ",";
            }
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.defaenclogcheckApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("CheckTypeApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                            String login_status_chk = jsonObject1.getString("status");

                            if (login_status_chk.equals("false")){
                                sessionManager.clearSession();
                                Intent in = new Intent(mContext, LoginActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                mContext.finish();
                            }

                            hideProgressDialog();
                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }

                    }, error -> {
                System.out.println("CheckTypeApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("device_id", DEVICEID);
                    params.put("otp", sessionManager.getOtp());
                    System.out.println("CheckTypeApi params>>>>>>>>>>>>>>" + params);
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

    private boolean checkPermission() {
        int result3 = ContextCompat.checkSelfPermission(mContext.getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result3 == PackageManager.PERMISSION_GRANTED;
    }
}