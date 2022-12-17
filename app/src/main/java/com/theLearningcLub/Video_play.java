package com.theLearningcLub;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

public class Video_play extends BaseActivity {
    Button RelativeLayoutbutton;

    String status, video, DemoID, DemoTitle, DemoDesc;
    TextView textViewTitle, VideoDesc, seeMore_textview;

    String user_status,response;
    View mBottomLayout;
    WebView wv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_video_play);

        sessionManager.setSavedreload("true");
        user_status="1";

        new UserStatusTask().execute();
        new AddTokenTask().execute();
        new VideoAs().execute();


        mBottomLayout = findViewById(R.id.mBottomLayout);
        RelativeLayoutbutton = findViewById(R.id.RelativeLayoutbutton);
        textViewTitle = findViewById(R.id.textView8);
        VideoDesc = findViewById(R.id.VideoDesc);
        seeMore_textview = findViewById(R.id.seeMore_textview);

        RelativeLayoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent in = new Intent(Video_play.this, TestseriesActivity.class);
                in.putExtra("DemoID", DemoID);
                startActivity(in);
                finish();

            }
        });
        seeMore_textview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertdialogbox();
            }
        });

        doTheAutoRefresh();

        wv =findViewById(R.id.wv);
        ImageView btn =findViewById(R.id.btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changeScreenOrientation();
            }
        });


    }

    private void alertdialogbox() {
        AlertDialog.Builder builder = new AlertDialog.Builder(Video_play.this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.desc_custom_dialog, null);
        builder.setCancelable(false);
        builder.setView(dialogView);

        Button btn_positive = dialogView.findViewById(R.id.btn_ok_desc);
        final TextView desc = dialogView.findViewById(R.id.desc_textview);
        desc.setText(DemoDesc);
        final Dialog dialog = builder.create();

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        btn_positive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.cancel();
            }
        });
        dialog.show();
    }

       @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {


                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();


                    }
                }).setNegativeButton("No", null).show();


    }

    /*-----------------------------------AsyncTask -------------------------------- */

    class VideoAs extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();
             HashMap<String,String> hashMap=new HashMap<>();
             hashMap.put("type","Introduction");
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id",sessionManager.getSavedUserId());

            String response = request.makeWebServiceCall(AllUrl.defembedecdem_vidApi, 2,hashMap);


            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");

                JSONArray contacts = jsonObject.getJSONArray("data");

                for (int i = 0; i < contacts.length(); i++) {
                    JSONObject c = contacts.getJSONObject(i);

                    DemoID = c.getString("id");
                    DemoTitle = c.getString("Title");
                    video = c.getString("videncd");
                    DemoDesc = c.getString("description");

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            showProgressDialog();
            try {
                if (status.equals("success")) {
                    textViewTitle.setText(DemoTitle);
                    VideoDesc.setText(DemoDesc);
                    video = video+"?defaviddb="+DemoID;
                    wv.loadUrl(video);
                }

            }catch (Exception e){
                e.printStackTrace();
            }

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

    class AddTokenTask extends AsyncTask<String,String,String>
    {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("token",sessionManager.getKeyToken());
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.add_tokenApi,2,hashMap);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        try {
            Class.forName("android.webkit.WebView")
                    .getMethod("onPause", (Class[]) null)
                    .invoke(wv, (Object[]) null);

        } catch(ClassNotFoundException cnfe) {

        } catch(NoSuchMethodException nsme) {

        } catch(InvocationTargetException ite) {

        } catch (IllegalAccessException iae) {

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void doTheAutoRefresh() {
        Thread t = new Thread() {

            @Override
            public void run() {
                try {
                    while (!isInterrupted()) {
                        Thread.sleep(100);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    mBottomLayout.setVisibility(View.VISIBLE);
                                }
                            });

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };

        t.start();
    }



    private void changeScreenOrientation() {
        int orientation = Video_play.this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mBottomLayout.setVisibility(View.VISIBLE);
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            mBottomLayout.setVisibility(View.GONE);
        }
        if (Settings.System.getInt(getContentResolver(),
                Settings.System.ACCELEROMETER_ROTATION, 0) == 1) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
                }
            }, 4000);
        }
    }
}