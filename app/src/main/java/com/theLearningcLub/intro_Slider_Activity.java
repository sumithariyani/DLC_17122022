package com.theLearningcLub;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.INTERNET;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission_group.PHONE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.bumptech.glide.Glide;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.PrefManagerSecond2;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class intro_Slider_Activity extends BaseActivity {

    private static final int PERMISSION_REQUEST_CODE = 200;

    private ViewPager viewPagerSecond;
    MyViewPagerAdapter myViewPagerAdapter;
    private Button btnNextSecond;
    private PrefManagerSecond2 prefManagerSecond2;
    String response,s_user_check;
    ImageView ivBack;
    TextView tvSkip;

    Integer[] imageSlider = {R.drawable.slider_gif_1,
            R.drawable.slider_gif_2,
            R.drawable.slider_gif_3,
            R.drawable.slider_gif_4};

    Integer[] sliderText = {R.drawable.splash_screen_1,
            R.drawable.splash_screen_2,
            R.drawable.splash_screen_3,
            R.drawable.splash_screen_4};

    Integer[] circle = {R.drawable.circle_1,
            R.drawable.circle_2,
            R.drawable.circle_3,
            R.drawable.circle_4};


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        prefManagerSecond2 = new PrefManagerSecond2(this);

        boolean smsg = prefManagerSecond2.isFirstTimeLaunch();

        if (prefManagerSecond2.isFirstTimeLaunch()) {
            launchHomeScreen();
            finish();
        } else if(prefManagerSecond2.getIntro()){
            launchHomeScreen();
            finish();
        }
        setContentView(R.layout.activity_intro__slider_);

        if (!checkPermission()) {

        } else {

        }

        viewPagerSecond = findViewById(R.id.view_pager_second);
        tvSkip = findViewById(R.id.tvSkip);
        ivBack = findViewById(R.id.ivBack);
        btnNextSecond = findViewById(R.id.btn_next_second);

        addBottomDots(0);
        changeStatusBarColor();

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = getItem(-1);
                if (current < imageSlider.length) {
                    // move to next screen
                    viewPagerSecond.setCurrentItem(current);
                }
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                prefManagerSecond2.setIntro(true);
                launchHomeScreen();
            }
        });

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPagerSecond.setAdapter(myViewPagerAdapter);
        viewPagerSecond.addOnPageChangeListener(viewPagerPageChangeListener);

        btnNextSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < imageSlider.length) {
                    // move to next screen
                    viewPagerSecond.setCurrentItem(current);
                } else {
                    prefManagerSecond2.setIntro(true);
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        if(currentPage == 0)
            ivBack.setVisibility(View.GONE);
        else {
            ivBack.setVisibility(View.VISIBLE);
        }
    }

    private int getItem(int i) {
        return viewPagerSecond.getCurrentItem() + i;
    }

    private void launchHomeScreen() {

        startActivity(new Intent(intro_Slider_Activity.this,LoginActivity.class));
        finish();

    }
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {
//        @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            // changing the next button text 'NEXT' / 'GOT IT'
            if (position == imageSlider.length - 1) {
                // last page. make button text to GOT IT
                btnNextSecond.setText(getString(R.string.start));
            } else {
                // still pages are left
                btnNextSecond.setText(getString(R.string.next));
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {

        }

        @Override
        public void onPageScrollStateChanged(int arg0) {

        }
    };
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }
    public class MyViewPagerAdapter extends PagerAdapter {
        LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {

        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(R.layout.welcome_side_second_1, container, false);
            ImageView ivSlider1 = (ImageView) view.findViewById(R.id.ivSlider1);
            ImageView ivSliderText = (ImageView) view.findViewById(R.id.ivSliderText);
            ImageView ivCircle = (ImageView) view.findViewById(R.id.ivCircle);

            Glide.with(mContext).asGif().load(imageSlider[position]).into(ivSlider1);
            Glide.with(mContext).load(sliderText[position]).into(ivSliderText);
            Glide.with(mContext).load(circle[position]).into(ivCircle);

            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return imageSlider.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }

    //----------------------------------------Check permission -------------------------------------------

    private boolean checkPermission() {

        int result3 = ContextCompat.checkSelfPermission(getApplicationContext(), WRITE_EXTERNAL_STORAGE);
        int result4 = ContextCompat.checkSelfPermission(getApplicationContext(), READ_EXTERNAL_STORAGE);
        int result5 = ContextCompat.checkSelfPermission(getApplicationContext(), ACCESS_FINE_LOCATION);
        int result6 = ContextCompat.checkSelfPermission(getApplicationContext(), INTERNET);
        int result7 = ContextCompat.checkSelfPermission(getApplicationContext(), CAMERA);
        int result8 = ContextCompat.checkSelfPermission(getApplicationContext(), PHONE);

        return result3 == PackageManager.PERMISSION_GRANTED && result4 == PackageManager.PERMISSION_GRANTED && result5 == PackageManager.PERMISSION_GRANTED && result6 == PackageManager.PERMISSION_GRANTED && result7 == PackageManager.PERMISSION_GRANTED && result8 == PackageManager.PERMISSION_GRANTED;

    }

    private void requestPermission() {

        ActivityCompat.requestPermissions(this, new String[]{WRITE_EXTERNAL_STORAGE, READ_EXTERNAL_STORAGE, ACCESS_FINE_LOCATION, INTERNET, CAMERA, PHONE}, PERMISSION_REQUEST_CODE);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                boolean storageAccepted = grantResults[2] == PackageManager.PERMISSION_GRANTED;
                boolean accesslocationAccepted = grantResults[3] == PackageManager.PERMISSION_GRANTED;
                boolean internetAccepted = grantResults[4] == PackageManager.PERMISSION_GRANTED;
                boolean phoneAccepted = grantResults[5] == PackageManager.PERMISSION_GRANTED;
            }
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.app.AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    //---------------------------------------------------------------------------------------------------
    /*---------------------------------AsyncTask------------------------------------------------- */
    class UserStatusCheckTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());


            response = webRequest.makeWebServiceCall(AllUrl.get_user_statusApi, 2, hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONObject jsonObject1=jsonObject.getJSONObject("response");
                s_user_check = jsonObject1.getString("user_status");
                // status = jsonObject1.getString("status");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            try{
                switch (s_user_check) {
                    case "1": {
                        Intent in = new Intent(intro_Slider_Activity.this, Video_play.class);
                        startActivity(in);
                        finish();
                        break;
                    }
                    case "2": {
                        Intent in = new Intent(intro_Slider_Activity.this, TestseriesActivity.class);
                        startActivity(in);
                        finish();
                        break;
                    }
                    case "3": {
                        Intent in = new Intent(intro_Slider_Activity.this, ResultActivity.class);
                        startActivity(in);
                        finish();
                        break;
                    }
                    case "4": {
                        Intent in = new Intent(intro_Slider_Activity.this, HomeActivity.class);
                        startActivity(in);
                        finish();
                        break;
                    }
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
