package com.theLearningcLub;

import static com.theLearningcLub.utils.BaseFragment.sessionManager;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;
import com.theLearningcLub.databinding.ActivityServerProblemBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.VideoEnabledWebChromeClient;

public class ServerProblemActivity extends BaseActivity {

    ActivityServerProblemBinding activityServerProblemBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_server_problem);

//
//        TextView tvUserHey = findViewById(R.id.tvUserHey);
//        tvUserHey.setText(getResources().getString(R.string.hey) + " " + sessionManager.getSavedNAME() + "!");



        ImageView noDataFoundGif = findViewById(R.id.noDataFoundGif);
        Glide.with(ServerProblemActivity.this).asGif().load(R.drawable.nodatagif).into(noDataFoundGif);


/*
        VideoView view = (VideoView)findViewById(R.id.videoView);
        String path = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
        view.setVideoURI(Uri.parse(path));
        view.start();

        ImageView playBtn = findViewById(R.id.playBtn);
        ImageView pauseBtn = findViewById(R.id.pauseBtn);
        ImageView stopBtn = findViewById(R.id.stopBtn);

        playBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VideoView view1 = (VideoView)findViewById(R.id.videoView);
                String path = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
                view1.setVideoURI(Uri.parse(path));
                view1.start();

            }
        });

        pauseBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VideoView view2 = (VideoView)findViewById(R.id.videoView);
                String path = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
                view2.setVideoURI(Uri.parse(path));
                view2.pause();

            }
        });

        stopBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                VideoView view3 = (VideoView)findViewById(R.id.videoView);
                String path = "android.resource://" + getPackageName() + "/" + R.raw.samplevideo;
                view3.setVideoURI(Uri.parse(path));
                view3.stopPlayback();

            }
        });*/

        MaterialButton closeBtn = (MaterialButton) findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "App Closed", Toast.LENGTH_SHORT).show();
                finishAffinity();
            }
        });

        ImageView ivExit = (ImageView) findViewById(R.id.ivExit);
        ivExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "App Closed", Toast.LENGTH_SHORT).show();
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


}