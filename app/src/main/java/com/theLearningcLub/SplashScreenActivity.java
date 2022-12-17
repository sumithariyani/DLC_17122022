package com.theLearningcLub;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.RemoteException;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.installreferrer.api.InstallReferrerClient;
import com.android.installreferrer.api.InstallReferrerStateListener;
import com.android.installreferrer.api.ReferrerDetails;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class SplashScreenActivity extends BaseActivity {


//    private static final String TAG = "MyFirebaseIIDService";
    String response,s_user_check;
    InstallReferrerClient referrerClient;

    private final Executor backgroundExecutor = Executors.newSingleThreadExecutor();

    private final BroadcastReceiver mUpdateReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            updateData();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_splash_screen);





        sessionManager.setnokey("0");
        sessionManager.setfragmentclick("false");
        sessionManager.setselectedfrag("0");
        sessionManager.setfragmentclick("false");
        checkInstallReferrer();
        updateData();
        getrefercode();

        referrerClient = InstallReferrerClient.newBuilder(this).build();

        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        // Connection established.
                        ReferrerDetails response;
                        try {
                            response = referrerClient.getInstallReferrer();
                            String referrerUrl = response.getInstallReferrer();
                            System.out.println("install referer success  >>>>>>>>>>"+referrerUrl);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }

                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        System.out.println("install referer not support  >>>>>>>>>>");
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        System.out.println("install referer unavailable  >>>>>>>>>>");
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {
                // Try to restart the connection on the next request to
                // Google Play by calling the startConnection() method.
            }
        });


        FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
            @Override
            public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {

                Uri deepLink = null;
                if(pendingDynamicLinkData != null){
                    deepLink = pendingDynamicLinkData.getLink();
                }

                try {

                    // trying to get from the intent
                    if(deepLink != null){

                        deepLink = getIntent().getData();

                        String data = deepLink.getQueryParameter("para");

                        StringTokenizer tokens = new StringTokenizer(data, "**");
                        String titleV = tokens.nextToken();
                        String s_pack_idV = tokens.nextToken();
                        String idV = tokens.nextToken();
                        String s_is_freeV = tokens.nextToken();
                        String videoTypeV = tokens.nextToken();
                        String urlV = tokens.nextToken();

                        if(sessionManager.isUserLogin()) {

                            if (!titleV.isEmpty() && !s_pack_idV.isEmpty() &&
                                    !idV.isEmpty() && !s_is_freeV.isEmpty() &&
                                    !videoTypeV.isEmpty() && !urlV.isEmpty()) {

                                Intent in = new Intent(mContext, VideoActivity.class);
                                in.putExtra("URI", urlV);
                                in.putExtra("VIDEO_TITLE", titleV);
                                in.putExtra("s_pack_id", s_pack_idV);
                                in.putExtra("VIDEO_ID", idV);
                                in.putExtra("is_free", s_is_freeV);
                                in.putExtra("videoType", videoTypeV);

                                startActivity(in);
                                finish();

                            } else {
                                Intent in = new Intent(SplashScreenActivity.this, HomeActivity.class);
                                in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(in);
                                finish();
                            }


                        }else if(sessionManager.getVerifystatus().contains("0")){
                            Intent in=new Intent(SplashScreenActivity.this,LoginActivity.class);
                            startActivity(in);
                            finish();
                        }else {
                            Thread background = new Thread() {
                                public void run() {
                                    try {
                                        // Thread will sleep for 5 seconds
                                        sleep(3*1000);
                                        Intent in=new Intent(SplashScreenActivity.this,intro_Slider_Activity.class);
                                        startActivity(in);
                                        finish();
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            background.start();
                        }


                    } else {
                        if(sessionManager.isUserLogin()) {

                            Intent in = new Intent(SplashScreenActivity.this, HomeActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();

                        }else if(sessionManager.getVerifystatus().contains("0")){
                            Intent in=new Intent(SplashScreenActivity.this,LoginActivity.class);
                            startActivity(in);
                            finish();
                        }else {
                            Thread background = new Thread() {
                                public void run() {
                                    try {
                                        // Thread will sleep for 5 seconds
                                        sleep(3*1000);
                                        Intent in=new Intent(SplashScreenActivity.this,intro_Slider_Activity.class);
                                        startActivity(in);
                                        finish();
                                    }catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            };
                            background.start();
                        }

                    }


                } catch (Exception e) {
                    Toast.makeText(mContext, "Error" + e, Toast.LENGTH_SHORT).show();

                    if(sessionManager.isUserLogin()) {

                        Intent in = new Intent(SplashScreenActivity.this, HomeActivity.class);
                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(in);
                        finish();

                    }else if(sessionManager.getVerifystatus().contains("0")){
                        Intent in=new Intent(SplashScreenActivity.this,LoginActivity.class);
                        startActivity(in);
                        finish();
                    }else {
                        Thread background = new Thread() {
                            public void run() {
                                try {
                                    // Thread will sleep for 5 seconds
                                    sleep(3*1000);
                                    Intent in=new Intent(SplashScreenActivity.this,intro_Slider_Activity.class);
                                    startActivity(in);
                                    finish();
                                }catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        };
                        background.start();
                    }

                }

            }
        });
    }

    /*---------------------------------AsyncTask------------------------------------------------- */
    class UserStatusCheckTask extends AsyncTask<String, String, String> {
        String status;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //  Customprogress2.showPopupProgressSpinner(splash_Activity.this,true);
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.get_user_statusApi, 2, hashMap);

            System.out.println("user check Response >>>>>>>>>>>>>>   " + response);
            System.out.println("user check parameter >>>>>>>>>>>>>   " + hashMap);

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");

                status = jsonObject1.getString("status");
                if (status.equals("true"))
                {
                    s_user_check = jsonObject1.getString("user_status");
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //  Customprogress2.showPopupProgressSpinner(splash_Activity.this,false);
            try {

                if (status.equals("true")) {
                    Thread background = new Thread() {
                        public void run() {
                            try {
                                // Thread will sleep for 5 seconds
                                sleep(3 * 1000);


                                switch (s_user_check) {
                                    case "1": {
                                        Intent in = new Intent(SplashScreenActivity.this, Video_play.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();

                                        break;
                                    }
                                    case "2": {
                                        Intent in = new Intent(SplashScreenActivity.this, TestseriesActivity.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();

                                        break;
                                    }
                                    case "3": {

                                        Intent in = new Intent(SplashScreenActivity.this, ResultActivity.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();

                                        break;
                                    }
                                    case "4": {

                                        Intent in = new Intent(SplashScreenActivity.this, HomeActivity.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();

                                        break;
                                    }
                                    case "5": {

                                        Intent in = new Intent(SplashScreenActivity.this, Activity_Quiz_Main.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();

                                        break;
                                    }
                                    default: {
                                        sessionManager.clearSession();
                                        Intent in = new Intent(SplashScreenActivity.this, LoginActivity.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                        finish();
                                        break;
                                    }
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    background.start();

                }
                else
                {
                    sessionManager.clearSession();
                    Intent in = new Intent(SplashScreenActivity.this, LoginActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    void  getrefercode(){
        FirebaseDynamicLinks.getInstance()
                .getDynamicLink(getIntent())
                .addOnSuccessListener(this, new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {
                        // Get deep link from result (may be null if no link is found)
                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = getIntent().getData();
                            System.out.println("firebase url android>>>>>>>>>>> "+deepLink);
                            System.out.println("firebase link android>>>>>>>>>>> "+pendingDynamicLinkData.getLink());
                        }
                        System.out.println("firebase url android.....>>>>>>>>>>> "+deepLink);

                    }
                });

    }
    private void updateData() {
        boolean isReferrerDetected = Application.isReferrerDetected(getApplicationContext());
        String firstLaunch = Application.getFirstLaunch(getApplicationContext());
        String referrerDate = Application.getReferrerDate(getApplicationContext());
        String referrerDataRaw = Application.getReferrerDataRaw(getApplicationContext());
        String referrerDataDecoded = Application.getReferrerDataDecoded(getApplicationContext());

        StringBuilder sb = new StringBuilder();
        sb.append("<b>First launch:</b>")
                .append("<br/>")
                .append(firstLaunch)
                .append("<br/><br/>")
                .append("<b>Referrer detection:</b>")
                .append("<br/>")
                .append(referrerDate);
        if (isReferrerDetected) {
            sb.append("<br/><br/>")
                    .append("<b>Raw referrer:</b>")
                    .append("<br/>")
                    .append(referrerDataRaw);

            if (referrerDataDecoded != null) {
                sb.append("<br/><br/>")
                        .append("<b>Decoded referrer:</b>")
                        .append("<br/>")
                        .append(referrerDataDecoded);
            }
        }

        System.out.println("link data>>>>>>>>>>  "+ Html.fromHtml(sb.toString()));
        System.out.println("movement link >>>>>>>>> "+new LinkMovementMethod());
//        CustomAlertdialog.createDialog(getApplicationContext(), String.valueOf(Html.fromHtml(sb.toString())));
    }

    @Override
    protected void onPause() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mUpdateReceiver);
        super.onPause();
    }

    @Override
    protected void onResume() {
        LocalBroadcastManager.getInstance(this).registerReceiver(mUpdateReceiver, new IntentFilter(ReferrerReceiver.ACTION_UPDATE_DATA));
        super.onResume();
    }

    // TODO: Change this to use whatever preferences are appropriate. The install referrer should
    // only be sent to the receiver once.
    private final String prefKey = "checkedInstallReferrer";

    void checkInstallReferrer() {
        if (getPreferences(MODE_PRIVATE).getBoolean(prefKey, false)) {
            return;
        }

        InstallReferrerClient referrerClient = InstallReferrerClient.newBuilder(this).build();
        backgroundExecutor.execute(() -> getInstallReferrerFromClient(referrerClient));
    }

    void getInstallReferrerFromClient(InstallReferrerClient referrerClient) {

        referrerClient.startConnection(new InstallReferrerStateListener() {
            @Override
            public void onInstallReferrerSetupFinished(int responseCode) {
                switch (responseCode) {
                    case InstallReferrerClient.InstallReferrerResponse.OK:
                        ReferrerDetails response;
                        try {
                            response = referrerClient.getInstallReferrer();
                        } catch (RemoteException e) {
                            e.printStackTrace();
                            return;
                        }
                        final String referrerUrl = response.getInstallReferrer();


                        // TODO: If you're using GTM, call trackInstallReferrerforGTM instead.
                        trackInstallReferrer(referrerUrl);


                        // Only check this once.
                        getPreferences(MODE_PRIVATE).edit().putBoolean(prefKey, true).apply();

                        // End the connection
                        referrerClient.endConnection();

                        break;
                    case InstallReferrerClient.InstallReferrerResponse.FEATURE_NOT_SUPPORTED:
                        // API not available on the current Play Store app.
                        break;
                    case InstallReferrerClient.InstallReferrerResponse.SERVICE_UNAVAILABLE:
                        // Connection couldn't be established.
                        break;
                }
            }

            @Override
            public void onInstallReferrerServiceDisconnected() {

            }
        });
    }

    // Tracker for Classic GA (call this if you are using Classic GA only)
    private void trackInstallReferrer(final String referrerUrl) {
        new Handler(getMainLooper()).post(() -> {
            InstallReferrerReceiver receiver = new InstallReferrerReceiver();
            Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
            intent.putExtra("referrer", referrerUrl);
            receiver.onReceive(getApplicationContext(), intent);
        });
    }

    // Tracker for GTM + Classic GA (call this if you are using GTM + Classic GA only)
    private void trackInstallReferrerforGTM(final String referrerUrl) {
        new Handler(getMainLooper()).post(() -> {
            InstallReferrerReceiver receiver = new InstallReferrerReceiver();
            Intent intent = new Intent("com.android.vending.INSTALL_REFERRER");
            intent.putExtra("referrer", referrerUrl);
            receiver.onReceive(getApplicationContext(), intent);
        });
    }
}