package com.theLearningcLub;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.IntentCompat;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

public class NoInternetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_no_internet);


        ImageView noInternetGif = findViewById(R.id.noInternetGif);
        noInternetGif.setVisibility(View.VISIBLE);
        Glide.with(NoInternetActivity.this).asGif().load(R.drawable.error404gif).into(noInternetGif);


        MaterialButton retryBtn = (MaterialButton) findViewById(R.id.retryBtn);
        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                haveNetworkConnection();

            }
        });


        ImageView ivExit = (ImageView) findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(NoInternetActivity.this, "App Closed", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });

        ImageView ivBack = (ImageView) findViewById(R.id.ivBack);
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


    }

    @Override
    public void onBackPressed() {

    }

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {

            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected()) {
                    sendtohome();
                    haveConnectedWifi = true;
                }

            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected()) {
                    sendtohome();
                    haveConnectedMobile = true;
                }else {
                    Toast.makeText(this, "Please Check your Internet", Toast.LENGTH_SHORT).show();
                }

        }
        return haveConnectedWifi || haveConnectedMobile;
    }


    private void sendtohome() {

        Intent intent = new Intent(NoInternetActivity.this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        System.exit(0);

    }


}