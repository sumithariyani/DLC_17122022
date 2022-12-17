package com.theLearningcLub;

import static android.content.ContentValues.TAG;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.theLearningcLub.utils.SessionManager;

public class InstallReferrerReceiver extends BroadcastReceiver {
    SessionManager sessionManager;
    @Override
    public void onReceive(Context context, Intent intent) {
        sessionManager=new SessionManager(context);
        if (intent.getAction().equals("com.android.vending.INSTALL_REFERRER")) {
            String referrer = "";
            Bundle extras = intent.getExtras();

            if (extras != null) {
                referrer = extras.getString("referrer");
//                referrer = extras.getString("referrer");
            }
            Log.e(TAG, "Referal Code Is: " + referrer);
            if (referrer.contains("DLC")){
                sessionManager.setReferCode(referrer);
            }else {
                sessionManager.setReferCode("0");
            }

//            Toast.makeText(context, ""+referrer, Toast.LENGTH_SHORT).show();
//            AppMethod.setStringPreference(context, AppConstant.PREF_REF_ID, referrer);
        }
    }
}
