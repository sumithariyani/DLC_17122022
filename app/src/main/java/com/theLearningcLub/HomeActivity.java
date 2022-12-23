package com.theLearningcLub;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import static com.theLearningcLub.utils.BaseFragment.sessionManager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.iid.FirebaseInstanceId;
import com.theLearningcLub.Dialog.BottomSheetDialog;
import com.theLearningcLub.Model_Class.Contact_Sync_Model;
import com.theLearningcLub.Model_Class.Purachase_package_free_video_Model;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityHomeBinding;
import com.theLearningcLub.fragment.HomeFragment;
import com.theLearningcLub.fragment.MoreFragment;
import com.theLearningcLub.fragment.MyPackagesActivity;
import com.theLearningcLub.fragment.NotificationActivity;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends BaseActivity implements View.OnClickListener {

    @SuppressLint("StaticFieldLeak")
    public static ActivityHomeBinding activityHomeBinding;

    public static BottomNavigationView bottomSheetDashboard;
    public static AppBarLayout appBarLayout;

    public static int frag_count = 0;
    private static final int PERMISSION_REQUEST_CODE = 200;

    FragmentManager fragmentManager = null;

    public static AppCompatTextView tvStudentName, tvStudentNumber;
    ActionBarDrawerToggle actionBarDrawerToggle;
    LinearLayout ll_profile, ll_my_package, ll_quizzes, ll_agent_report, ll_share, ll_privacy, ll_help,
            ll_logout;
    ImageView rl_close;
    public static CircleImageView civUserImage;
    public static AppCompatImageView iv_cart, ivBack;
    public static CircleImageView iv_menu_main;
    public static TextView badgeCart, tvUserHello;
    public static RelativeLayout rlCart;
    public static AppCompatImageView ivWallet;
    TextView tvWalletAmount;

    String DEVICEID;

    String /*key = "0",*/response, status, message, allcontact = "", s_cartcount = "";
    String s, s_user_id, cartkey;
    boolean s_contactsyanc;
    boolean doubleBackToExitPressedOnce = false;

    Fragment fragment;

    ArrayList<Contact_Sync_Model> NameList = new ArrayList<>();

    public static String s_image, s_first, s_last, user_status, s_mobile;

    //-----------------fragment active -------------------
    Fragment fragment1 = new HomeFragment();
    Fragment fragment2 = new MyPackagesActivity();
    Fragment fragment3 = new NotificationActivity();
    Fragment fragment4 = new MoreFragment();
    FragmentManager fm = getSupportFragmentManager();
    Fragment active = fragment1;

    int pageselect=0;
    int currentViewId = 0;
    private Handler mHandler;
    TextView versionName;
   public static int position=0;
    public static ArrayList<Purachase_package_free_video_Model> purachase_package_video_modelslist = new ArrayList<>();

    public static String s_pack_id;
    public static String s_pack_type;
    public static String video_name,video_date,videoimage,s_free_status,stvideo_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityHomeBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_home);

//        mHandler = new Handler();


        ll_profile = findViewById(R.id.ll_profile);
        ll_my_package = findViewById(R.id.ll_my_package);
        ll_agent_report = findViewById(R.id.ll_agent_report);
        ll_quizzes = findViewById(R.id.ll_quizzes);
        ll_share = findViewById(R.id.ll_share);
        ll_privacy = findViewById(R.id.ll_privacy);
        ll_help = findViewById(R.id.ll_help);
        ll_logout = findViewById(R.id.ll_logout);
        rl_close = findViewById(R.id.rl_close);
        tvStudentNumber = findViewById(R.id.tvStudentNumber);
        tvStudentName = findViewById(R.id.tvStudentName);
        civUserImage = findViewById(R.id.civUserImage);
        bottomSheetDashboard = findViewById(R.id.bottomSheetDashboard);
        badgeCart = findViewById(R.id.badgeCart);
        tvUserHello = findViewById(R.id.tvUserHello);
        iv_menu_main = findViewById(R.id.iv_menu_main);
        iv_cart = findViewById(R.id.iv_cart);
        ivBack = findViewById(R.id.ivBack);
        rlCart = findViewById(R.id.rlCart);
        appBarLayout = findViewById(R.id.appBarLayout);
        tvWalletAmount = findViewById(R.id.tvWalletAmount);
        ivWallet = findViewById(R.id.ivWallet);
        versionName = findViewById(R.id.versionName);
        versionName.setText("Version : " + BuildConfig.VERSION_NAME);

        getrefercode();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, activityHomeBinding.drawerHome,
                R.string.nav_open, R.string.nav_close);
        activityHomeBinding.drawerHome.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        tvStudentName.setText(sessionManager.getSavedNAME());
        tvStudentNumber.setText(sessionManager.getcontact());



        iv_menu_main.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.GONE);
        rlCart.setVisibility(View.VISIBLE);
        ivWallet.setVisibility(View.VISIBLE);

        iv_menu_main.setOnClickListener(this);
        iv_cart.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        ll_my_package.setOnClickListener(this);
        ll_agent_report.setOnClickListener(this);
        ll_quizzes.setOnClickListener(this);
        ll_share.setOnClickListener(this);
        ll_privacy.setOnClickListener(this);
        ll_help.setOnClickListener(this);
        ll_logout.setOnClickListener(this);
        rl_close.setOnClickListener(this);
        ivBack.setOnClickListener(this);
        ivWallet.setOnClickListener(this);

        BottomNavigationViewHelper.removeShiftMode(bottomSheetDashboard);
        bottomSheetDashboard.setItemIconTintList(null);
        fragmentManager = getSupportFragmentManager();

        s_user_id = sessionManager.getSavedUserId();
        s = sessionManager.getSavedCART_NO();

        DEVICEID = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        System.out.println("android version name>>>>>>>>>>>>>" + Build.VERSION.RELEASE);

        s_contactsyanc = sessionManager.getContactSyanc();
        sessionManager.setclick("false");
        sessionManager.setfragmentclick("false");

        if (!checkPermission()) {
            ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE},
                    PERMISSION_REQUEST_CODE);
        }

        new CheckLoginStatusTask().execute();
        viewProfileApi();
        cartkey = "1";
        CartCountApi();
        new UserStatusTask().execute();
        new AddTokenTask().execute();
        new NotificationAs().execute();
        new AgentInfoAsyncTask().execute();

//        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//        fragmentTransaction.replace(R.id.flFrameLayout,new HomeFragment()).commit();
//        fragmentTransaction.isAddToBackStackAllowed();
////        fragmentTransaction.commitAllowingStateLoss();
//
//        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);

        fm.beginTransaction().add(R.id.flFrameLayout, fragment3, "3").hide(fragment3).commit();
        fm.beginTransaction().add(R.id.flFrameLayout, fragment4, "4").hide(fragment4).commit();
        fm.beginTransaction().add(R.id.flFrameLayout, fragment2, "2").hide(fragment2).commit();
        fm.beginTransaction().add(R.id.flFrameLayout, fragment1, "1").show(fragment1).commit(); //default fragment is show not hide.

        badgeCart.setText(s_cartcount);

        bottomSheetDashboard.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment = null;
                int id = item.getItemId();


                switch (item.getItemId()) {
                    case R.id.navHome:
//                        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//                        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//                        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//                        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//                        displaySelectedScreen(Constant.Home, null);

                        frag_count = 0;
                        sessionManager.setselectedfrag("0");
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        fm.beginTransaction().hide(active).show(fragment1).commit();
                        active = fragment1;
                        bottomSheetDashboard.getMenu().getItem(0).setChecked(true);
                        tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
                        iv_menu_main.setVisibility(View.VISIBLE);
                        ivBack.setVisibility(View.GONE);
                        rlCart.setVisibility(View.VISIBLE);
                        break;

                    case R.id.navMyPackages:
//                        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//                        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//                        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//                        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//                        displaySelectedScreen(Constant.MyPackages, null);


                        frag_count = 1;
                        sessionManager.setselectedfrag("1");
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        fm.beginTransaction().hide(active).show(fragment2).commit();
                        active = fragment2;
                        bottomSheetDashboard.getMenu().getItem(1).setChecked(true);
                        tvUserHello.setText(getResources().getString(R.string.my_packages));
                        iv_menu_main.setVisibility(View.VISIBLE);
                        ivBack.setVisibility(View.GONE);
                        rlCart.setVisibility(View.VISIBLE);
                        break;

                    case R.id.navNotification:
//                        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//                        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//                        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//                        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//                        displaySelectedScreen(Constant.Notification, null);


                        frag_count = 2;
                        sessionManager.setselectedfrag("2");
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        fm.beginTransaction().hide(active).show(fragment3).commit();
                        active = fragment3;
                        bottomSheetDashboard.getMenu().getItem(2).setChecked(true);
                        tvUserHello.setText(getResources().getString(R.string.notification));
                        iv_menu_main.setVisibility(View.VISIBLE);
                        ivBack.setVisibility(View.GONE);
                        rlCart.setVisibility(View.VISIBLE);
                        break;

                    case R.id.navMore:
//                        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//                        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//                        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//                        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//                        displaySelectedScreen(Constant.More, null);

                        frag_count = 3;
                        sessionManager.setselectedfrag("3");
                        for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                            fm.popBackStack();
                        }
                        fm.beginTransaction().hide(active).show(fragment4).commit();
                        active = fragment4;
                        bottomSheetDashboard.getMenu().getItem(3).setChecked(true);
                        tvUserHello.setText(getResources().getString(R.string.more));
                        iv_menu_main.setVisibility(View.VISIBLE);
                        ivBack.setVisibility(View.GONE);
                        rlCart.setVisibility(View.VISIBLE);
                        break;
                }

                return true;
            }
        });

        switch (sessionManager.getselectedfrag()) {
            case "0":
                fm.beginTransaction().hide(active).show(fragment1).commit();
                active = fragment1;
                bottomSheetDashboard.getMenu().getItem(0).setChecked(true);
                tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
                iv_menu_main.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.GONE);
                rlCart.setVisibility(View.VISIBLE);
                break;
            case "1":
                fm.beginTransaction().hide(active).show(fragment2).commit();
                active = fragment2;
                bottomSheetDashboard.getMenu().getItem(1).setChecked(true);
                tvUserHello.setText(getResources().getString(R.string.my_packages));
                iv_menu_main.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.GONE);
                rlCart.setVisibility(View.VISIBLE);
                break;
            case "2":
                fm.beginTransaction().hide(active).show(fragment3).commit();
                active = fragment3;
                bottomSheetDashboard.getMenu().getItem(2).setChecked(true);
                tvUserHello.setText(getResources().getString(R.string.notification));
                iv_menu_main.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.GONE);
                rlCart.setVisibility(View.VISIBLE);
                break;
            case "3":
                fm.beginTransaction().hide(active).show(fragment4).commit();
                active = fragment4;
                bottomSheetDashboard.getMenu().getItem(3).setChecked(true);
                tvUserHello.setText(getResources().getString(R.string.more));
                iv_menu_main.setVisibility(View.VISIBLE);
                ivBack.setVisibility(View.GONE);
                rlCart.setVisibility(View.VISIBLE);
                break;
        }
        new view_download_list_asy().execute();
    }

//    public void displaySelectedScreen(int state, Bundle bundle) {
//        try {
//            currentViewId = state;
//            Runnable mPendingRunnable = () -> {
//                Fragment fragment;
//                switch (state) {
//                    default:
//                    case Constant.Home:
//                        fragment = new HomeFragment();
//                        break;
//                    case Constant.MyPackages:
//                        fragment = new MyPackagesActivity();
//                        break;
//                    case Constant.Notification:
//                        fragment = new NotificationActivity();
//                        break;
//                    case Constant.More:
//                        fragment = new MoreFragment();
//                        break;
//                }
//                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.flFrameLayout, fragment, "" + state).detach(fragment).attach(fragment).commit();
////                fragmentTransaction.addToBackStack("maindash");
////                fragmentTransaction.isAddToBackStackAllowed();
//
////                fragmentTransaction.commitAllowingStateLoss();
//            };
//            mHandler.post(mPendingRunnable);
//            invalidateOptionsMenu();
//
//
//
//            // this.fragment = fragment;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    class view_download_list_asy extends AsyncTask<String, String, String> {
        String status,s_id,s_desc,s_video_date,s_title,s_video,s_image,s_v_id,is_free="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {





            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response = webRequest.makeWebServiceCall(AllUrl.last_video_view,2,hashMap);

            System.out.println("last_video_view video response >>>>>>>>>>>>>>>  " +response);
            System.out.println("last_video_view video parameter >>>>>>>>>>>>>>>  "+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status=jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("body");

                    JSONObject c = jsonArray.getJSONObject(0);
                    stvideo_id=c.getString("videos_id");
                    video_name=c.getString("video_name");
                    video_date=c.getString("videos_date");
                    videoimage = c.getString("image");
                    s_pack_id=c.getString("pack_id");
                    s_free_status=c.getString("pack_status");
                    s_pack_type=c.getString("pack_type");
                    JSONArray playlist= c.getJSONArray("play_list");
                    if(s_pack_type.equals("0")){
                        for (int i = 0; i < playlist.length(); i++) {
                            JSONObject js=playlist.getJSONObject(i);
                            JSONArray jsonArray1 = js.getJSONArray("vid_data");
                            for (int p = 0; p < jsonArray1.length(); p++) {

                                JSONObject jsonObject1 = jsonArray1.getJSONObject(p);
//                                if(c.getString("videos_id").equals(jsonObject1.getString("vid_id"))){
//                                    position=p;
//                                }

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
                                video_model.setVideo_id( s_v_id);
                                video_model.setVideo_title( s_title);
                                video_model.setVideo( s_video);
                                video_model.setVideo_desc( s_desc);
                                video_model.setVideo_date( s_video_date);
                                video_model.setVideo_image( s_image);
                                video_model.setIs_free( is_free);
                                video_model.setVideoview_Time(videoviewtime);
                                video_model.setVideototal_duration(videotime);
                                purachase_package_video_modelslist.add(video_model);

                            }
                        }
                    }else{
                        for (int p = 0; p < playlist.length(); p++) {

                            JSONObject jsonObject1 = playlist.getJSONObject(p);
//                            if(c.getString("videos_id").equals(jsonObject1.getString("videos_id"))){
//                                position=p;
//                            }

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
                            String videoviewtime=jsonObject1.getString("view_status");
                            String videotime=jsonObject1.getString("v_duration");

                            Purachase_package_free_video_Model video_model = new Purachase_package_free_video_Model();
                            video_model.setVideo_id( s_v_id);
                            video_model.setVideo_title( s_title);
                            video_model.setVideo( s_video);
                            video_model.setVideo_desc( s_desc);
                            video_model.setVideo_date( s_video_date);
                            video_model.setVideo_image( s_image);
                            video_model.setIs_free( is_free);
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
//            Handler handler=new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
            try {
                if (purachase_package_video_modelslist.size()>0){

                BottomSheetDialog bottomSheet = new BottomSheetDialog();
                bottomSheet.show(getSupportFragmentManager(),
                        "ModalBottomSheet");

                }

            }catch (Exception e){

            }
//                }
//            },1000);
        }
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_cart:
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flFrameLayout, new MyCartActivity(), "myCart");
                fragmentTransaction.addToBackStack("myCart");
                fragmentTransaction.commit();

                frag_count = 1;

//                fragment = new MyCartActivity();
//                loadFragment(fragment);
//                sessionManager.setfragmentclick("true");
                break;
            case R.id.ivBack:
                onBackPressed();
                break;
            case R.id.iv_menu_main:
                activityHomeBinding.drawerHome.openDrawer(GravityCompat.START);
                break;
            case R.id.rl_close:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                break;
            case R.id.ll_profile:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction1 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction1.replace(R.id.flFrameLayout, new ProfileActivity(), "profile");
                fragmentTransaction1.addToBackStack("profile");
                fragmentTransaction1.commit();

                frag_count = 1;

//                fragment = new ProfileActivity();
//                loadFragment(fragment);
//                sessionManager.setfragmentclick("true");
                break;
            case R.id.ll_my_package:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction2 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction2.replace(R.id.flFrameLayout, new MyPackagesActivity(), "packages");
                fragmentTransaction2.addToBackStack("packages");
                fragmentTransaction2.commit();

                frag_count = 1;

//                fragment = new MyPackagesActivity();
//                loadFragment(fragment);
                break;
            case R.id.ll_quizzes:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction3 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction3.replace(R.id.flFrameLayout, new QuizActivity(), "quiz");
                fragmentTransaction3.addToBackStack("quiz");
                fragmentTransaction3.commit();

                frag_count = 1;

//                fragment = new QuizActivity();
//                loadFragment(fragment);
                break;
            case R.id.ll_agent_report:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction4 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction4.replace(R.id.flFrameLayout, new AgentReportActivity(), "agent");
                fragmentTransaction4.addToBackStack("agent");
                fragmentTransaction4.commit();

                frag_count = 1;

//                fragment = new AgentReportActivity();
//                loadFragment(fragment);
                break;
            case R.id.ll_share:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction5 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction5.replace(R.id.flFrameLayout, new ShareActivity(), "agent");
                fragmentTransaction5.addToBackStack("agent");
                fragmentTransaction5.commit();

                frag_count = 1;

//                fragment = new ShareActivity();
//                loadFragment(fragment);
                break;
            case R.id.ll_privacy:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction6 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction6.replace(R.id.flFrameLayout, new Fragment_Privacy(), "privacy");
                fragmentTransaction6.addToBackStack("privacy");
                fragmentTransaction6.commit();

                frag_count = 1;

//                fragment = new Fragment_Privacy();
//                loadFragment(fragment);
                tvUserHello.setText(getResources().getString(R.string.privacy_policy));
                iv_menu_main.setVisibility(View.GONE);
                ivBack.setVisibility(View.VISIBLE);
                rlCart.setVisibility(View.GONE);
                break;
            case R.id.ll_help:
                activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
                FragmentTransaction fragmentTransaction8 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction8.replace(R.id.flFrameLayout, new HelpAndSupportActivity(), "help");
                fragmentTransaction8.addToBackStack("help");
                fragmentTransaction8.commit();

                frag_count = 1;

//                fragment = new HelpAndSupportActivity();
//                loadFragment(fragment);
                break;
            case R.id.ll_logout:
                logoutAlertBox();
                break;
            case R.id.ivWallet:
                FragmentTransaction fragmentTransaction7 = getSupportFragmentManager().beginTransaction();
                fragmentTransaction7.replace(R.id.flFrameLayout, new AgentReportActivity(), "agentReport");
                fragmentTransaction7.addToBackStack("agentReport");
                fragmentTransaction7.commit();

                frag_count = 1;

                break;
        }
    }

    private void logoutAlertBox() {
        //will create a view of our custom dialog layout
        View alertCustomdialog = LayoutInflater.from(mContext).inflate(R.layout.dialog_box, null);
        //initialize alert builder.
        AlertDialog.Builder alert = new AlertDialog.Builder(mContext);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);
        AppCompatButton btnYes = alertCustomdialog.findViewById(R.id.btnYes);
        AppCompatButton btnNo = alertCustomdialog.findViewById(R.id.btnNo);
        TextView tvMsg = alertCustomdialog.findViewById(R.id.tvMsg);
        tvMsg.setText(mContext.getResources().getString(R.string.logout_massage));

        final AlertDialog dialog = alert.create();
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.clearSession();
                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class NotificationAs extends AsyncTask<String, String, String> {

        String status;
        JSONArray contacts;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", s_user_id);
            hashMap.put("key", sessionManager.getLoginkey());

            String response = request.makeWebServiceCall(AllUrl.view_notApi, 2, hashMap);

            //  System.out.print("Notification responce >>>>>>>>>>>>>>>>>" + response);
            try {
                JSONObject jsonObject = new JSONObject(response);

                status = jsonObject.getString("status");

                contacts = jsonObject.getJSONArray("data");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (status.equals("success")) {
//                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                    fragmentTransaction.replace(R.id.flFrameLayout,new NotificationActivity(),"notification");
//                    fragmentTransaction.addToBackStack("notification");
//                    fragmentTransaction.commit();
//                    fragment = new NotificationActivity();
//                    loadFragment(fragment);
//                    fm.beginTransaction().add(R.id.flFrameLayout, fragment3, "3").hide(fragment3).commit();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

//    private boolean loadFragment(Fragment fragment) {
//        //switching fragment
//        if (fragment != null) {
//            getSupportFragmentManager()
//                    .beginTransaction()
//                    .add(R.id.flFrameLayout, fragment)
//                    .addToBackStack("gghh")
//                    .commit();
//            return true;
//        }
//        return false;
//    }

    private void viewProfileApi() {
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.ViewProfileApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("viewProfileApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");

                            JSONArray contacts = jsonObject.getJSONArray("data");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);

                                s_first = c.getString("user_fname");
                                s_last = c.getString("user_lname");
                                s_mobile = c.getString("user_contact");
                                s_image = c.getString("user_image");
                            }

                            if (status.equals("true")) {
                                tvStudentName.setText(s_first + "\t" + s_last);
                                tvStudentNumber.setText(s_mobile);

                                if (s_image != null && !s_image.equalsIgnoreCase("")) {
                                    s_image = s_image.replaceAll("\\\\", "");

                                    System.out.println("imageUrlimageUrlimageUrl" + s_image);

                                    Glide.with(mContext).load(s_image.replace("http://", "https://"))
                                            .placeholder(R.drawable.ic_drawer_home).into(HomeActivity.civUserImage);

                                    Glide.with(mContext).load(s_image.replace("http://", "https://"))
                                            .placeholder(R.drawable.ic_drawer_home).into(HomeActivity.iv_menu_main);
                                }

                            }
                            tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
                            hideProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                Intent intentError = new Intent(HomeActivity.this, ServerProblemActivity.class);
                startActivity(intentError);
                finish();
                System.out.println("viewProfileApi error>>>>>>>>>>>>>." + error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", s_user_id);
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("viewProfileApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        } else {
            Intent intent = new Intent(HomeActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext, getResources().getString(R.string.net_connection));
        }
    }

    private void CartCountApi() {
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
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
                                    badgeCart.setVisibility(View.GONE);
                                } else if (s_cartcount.equals("0")) {
                                    badgeCart.setVisibility(View.GONE);
                                } else {
                                    badgeCart.setVisibility(View.VISIBLE);
                                    badgeCart.setText(s_cartcount);
                                }
                            }
                            hideProgressDialog();
                        } catch (JSONException e) {
                            hideProgressDialog();
                            e.printStackTrace();
                        }

                    }, error -> {
                System.out.println("CartCountApi error>>>>>>>>>>>>>." + error);
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
        } else {
            Intent intent = new Intent(HomeActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext, getResources().getString(R.string.net_connection));
        }
    }

    class UserStatusTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("user_status", user_status);
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.update_user_statusApi, 2, hashMap);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    class AddTokenTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("token", FirebaseInstanceId.getInstance().getToken());
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.add_tokenApi, 2, hashMap);
            System.out.println("Add Token parameter  >>>>>>>>>>>>>>>>>>>>" + hashMap);
            System.out.println("Add Token Response  >>>>>>>>>>>>>>>>>>>>" + response);


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    //----------------------------------------------

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /*-----------------------------------AsyncTask -------------------------------- */

    class checksynkTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.check_sinksApi, 2, hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                status = jsonObject1.getString("status");
                message = jsonObject1.getString("msg");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (message.equals("Not Sink")) {
                    sessionManager.setContactSyanc(false);
                    s_contactsyanc = sessionManager.getContactSyanc();

                    new numbersyncTask().execute();

                } else {
                    sessionManager.setContactSyanc(false);
                    s_contactsyanc = sessionManager.getContactSyanc();
                    CommonFunction.showToastSingle(mContext, message);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


        }

        class numbersyncTask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgressDialog();
                for (int i = 0; i < NameList.size(); i++) {
                    allcontact = allcontact + NameList.get(i).getName() + NameList.get(i).getNumber() + ",";
                }
            }

            @Override
            protected String doInBackground(String... strings) {

                WebRequest webRequest = new WebRequest();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_id", sessionManager.getSavedUserId());
                hashMap.put("contact", allcontact);
                hashMap.put("key", sessionManager.getLoginkey());

                response = webRequest.makeWebServiceCall(AllUrl.contact_sinkApi, 2, hashMap);

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                    message = jsonObject1.getString("msg");
                    status = jsonObject1.getString("status");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                hideProgressDialog();

                CommonFunction.showToastSingle(mContext, message);
            }
        }
    }

    @Override
    public void onBackPressed() {

        if(frag_count!=0){

            frag_count = 0;
            sessionManager.setselectedfrag("0");
            for (int i = 0; i < fm.getBackStackEntryCount(); ++i) {
                fm.popBackStack();
            }
            fm.beginTransaction().hide(active).show(fragment1).commit();
            active = fragment1;
            bottomSheetDashboard.getMenu().getItem(0).setChecked(true);
            tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
            iv_menu_main.setVisibility(View.VISIBLE);
            ivBack.setVisibility(View.GONE);
            rlCart.setVisibility(View.VISIBLE);

        }else{

        if (activityHomeBinding.drawerHome.isDrawerOpen(GravityCompat.START)) {
            activityHomeBinding.drawerHome.closeDrawer(GravityCompat.START);
        } else {
//            super.onBackPressed();
        }

        if (doubleBackToExitPressedOnce) {
//            super.onBackPressed();
            showExitDialog();
            return;
        }

        if(frag_count == 0){
            this.doubleBackToExitPressedOnce = true;
            Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
        }

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);

        int fragments = getSupportFragmentManager().getBackStackEntryCount();


        if (fragments < 2) {
            ivBack.setVisibility(View.GONE);
            iv_menu_main.setVisibility(View.VISIBLE);
            rlCart.setVisibility(View.VISIBLE);

            tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
            HomeFragment.fragmentHomeBinding.wv.onResume();
        }

        if (fragments > 0) {
            super.onBackPressed();
        }



        if (frag_count == 0) {
            if (fragments == 0) {
                sessionManager.setfragmentclick("false");
                tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
                ivBack.setVisibility(View.GONE);
                iv_menu_main.setVisibility(View.VISIBLE);
                rlCart.setVisibility(View.VISIBLE);

                HomeFragment.fragmentHomeBinding.wv.onResume();
                bottomSheetDashboard.getMenu().getItem(0).setChecked(true);
                tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
            }
        } else if (frag_count == 1) {
            if (fragments == 0) {
                sessionManager.setfragmentclick("false");
                tvUserHello.setText(getResources().getString(R.string.my_packages));
                bottomSheetDashboard.getMenu().getItem(1).setChecked(true);
            }
        } else if (frag_count == 2) {
            if (fragments == 0) {
                sessionManager.setfragmentclick("false");
                tvUserHello.setText(getResources().getString(R.string.notification));
                bottomSheetDashboard.getMenu().getItem(2).setChecked(true);
            }
        } else if (frag_count == 3) {
            if (fragments == 0) {
                sessionManager.setfragmentclick("false");
                tvUserHello.setText(getResources().getString(R.string.more));
                bottomSheetDashboard.getMenu().getItem(3).setChecked(true);
                ivBack.setVisibility(View.GONE);
                iv_menu_main.setVisibility(View.VISIBLE);
            }
        }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvUserHello.setText(getResources().getString(R.string.hello) + " " + s_first);
    }

    //------------------------Check Login status---------------------
    class CheckLoginStatusTask extends AsyncTask<String, String, String> {

        String login_status_chk;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("device_id", DEVICEID);
            hashMap.put("otp", sessionManager.getOtp());

            response = webRequest.makeWebServiceCall(AllUrl.defaenclogcheckApi, 2, hashMap);

            System.out.println("Check defaenclogcheck Response >>>>>>>>>>>>>>>>" + response);
            System.out.println("Check defaenclogcheck parameter >>>>>>>>>>>>>>>>" + hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                login_status_chk = jsonObject1.getString("status");

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                if (login_status_chk.equals("false")) {

                    sessionManager.clearSession();
                    Intent in = new Intent(mContext, LoginActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

    void getrefercode() {
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = getIntent().getData();
                            System.out.println("firebase url android>>>>>>>>>>> " + deepLink);
                        }
                        System.out.println("firebase url android.....>>>>>>>>>>> " + deepLink);
                    }
                });
    }

    private boolean checkPermission() {
        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        return result3 == PackageManager.PERMISSION_GRANTED;
    }

    public void showExitDialog() {
        Dialog dialogExit = new Dialog(HomeActivity.this);
        dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogExit.setContentView(R.layout.dialog_logout);
        dialogExit.setCancelable(true);
        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogExit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        final ImageView icon = dialogExit.findViewById(R.id.icon);


        TextView text = dialogExit.findViewById(R.id.content);
        text.setText(getResources().getString(R.string.do_you_want_to_exit));

        (dialogExit.findViewById(R.id.bt_YES)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);

            }
        });

        (dialogExit.findViewById(R.id.bt_NO)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogExit.dismiss();
            }
        });

        dialogExit.show();
        dialogExit.getWindow().setAttributes(lp);
    }

    class AgentInfoAsyncTask extends AsyncTask<String, String, String> {
        String status, user_fname, post_name, amount_earned, amount_outstanding, amount_deducted,
                amount_received;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response = request.makeWebServiceCall(AllUrl.MLM_agent_infoApi, 2, hashMap);

            System.out.println("agent_info response .... " + response);
            System.out.println("agent_info params >>>>>>>>>>>>>>>>   " + hashMap);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            hideProgressDialog();
            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray jsonArray = jsonObject.getJSONArray("body");

                System.out.println("cart data =============         " + jsonArray);

                JSONObject c = jsonArray.getJSONObject(0);
                //     s_card_id = c.getString("card_id");
                user_fname = c.getString("user_fname");
                post_name = c.getString("post_name");
                amount_earned = c.getString("amount_earned");
                amount_outstanding = c.getString("amount_outstanding");
                amount_deducted = c.getString("amount_deducted");
                amount_received = c.getString("amount_received");

                tvWalletAmount.setText(getString(R.string.rs) + " " + amount_earned);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

}