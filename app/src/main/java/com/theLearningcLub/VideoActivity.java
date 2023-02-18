package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.app.PictureInPictureParams;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Rational;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.BuildConfig;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.PlaybackException;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultAllocator;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.PendingDynamicLinkData;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.theLearningcLub.Model_Class.Purachase_package_free_video_Model;
import com.theLearningcLub.Model_Class.Purachase_package_video_Model;
import com.theLearningcLub.adapter.PackageVideoAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityVideoBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.OnSwipeTouchListener;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VideoActivity extends BaseActivity implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private boolean isShowingTrackSelectionDialog;

    private DefaultTrackSelector trackSelector;
    PackageVideoAdapter packageVideoAdapter;
    /* >>>>>  Akshat <<<<< */
    PlayerView playerView;
    SimpleExoPlayer player;
    ConcatenatingMediaSource concatenatingMediaSource;
    //    ArrayList<Purachase_package_free_video_Model> videoListArrayList = new ArrayList<>();
//    ArrayList<videoList> videoList = new ArrayList<>();
    ArrayList<String> videoList = new ArrayList<>();
    ArrayList<String> videotitleList = new ArrayList<>();
    int position;
    TextView title5;
    ImageView btn_next, btn_prev;
    String url5 = "https://learningclub.co.in/admin/upload/videos/Adjective_Level-_4.mp4";

    private int device_height, device_width, brightness, media_volume;
    boolean start = false;
    boolean left, right;
    private float baseX, baseY;
    boolean swipe_move = false;
    private long diffX, diffY;
    public static final int MINIMUM_DISTANCE = 100;
    boolean success = false;
    TextView vol_text, brt_text;
    ProgressBar vol_progress, brt_progress;
    LinearLayout vol_progress_container, vol_text_container, brt_progress_container, brt_text_container;
    ImageView vol_icon, brt_icon, videoBack, lock, exo_play_speed, exo_forword, exo_rewind, video_more, exo_rotate;
    AudioManager audioManager;
    private ContentResolver contentResolver;
    private Window window;
    boolean singleTap, doubleTap = false;
    int videovduration = 0;


    /* Zoom Feature */
    RelativeLayout zoomLayout, zoomContainer, double_tap_play_pause, rootlayoutvideo;
    TextView zoom_perc;
    ScaleGestureDetector scaleGestureDetector;
    private float scale_factor = 1.0f;
    private ControlsMode controlsMode;

    public enum ControlsMode {
        LOCK, FULLSCREEN;
    }

    /* Speed */
    PlaybackParameters playbackParameters;
    float playbackspeed;

    /* Picture in Picture Mode */
    PictureInPictureParams.Builder pictureInpicture;
    boolean isCrossChacked;


    /* Swipe Forward */
    private float seekdistance = 0;
    private final boolean checkSeek = true;
    private float distanceCovered = 0;



    int lastWindowIndex = 0;

    long videoWatchedTime = 0;
    long currentvideoposition = 0;
    boolean runfrom = false;
    ActivityVideoBinding activityVideoBinding;
    ArrayList<Purachase_package_free_video_Model> purachase_package_video_modelslist = new ArrayList<>();
    String s_pack_id, user_status, response, s_is_free, id, s_pack_name, desc, pack_type, videoType, url, title, s_image, s_video_date;
    String from="2";

    List<Purachase_package_video_Model> purachase_package_video_modelslist2 = new ArrayList<>();
    final Handler handler = new Handler();
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        // Hide status bar and default bottom navigation bar
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        setFullScreen();
        hideBottomBar();

        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        activityVideoBinding = DataBindingUtil.setContentView(mContext, R.layout.activity_video);

        /*>>>>>>>> Akshat <<<<<<<<*/

        btn_next = findViewById(R.id.exo_next);
        btn_prev = findViewById(R.id.exo_prev);
        video_more = findViewById(R.id.video_more);
        exo_rewind = findViewById(R.id.exo_rewind);
        exo_forword = findViewById(R.id.exo_forword);
        title5 = findViewById(R.id.videotitle);
        playerView = findViewById(R.id.exoplayer);
        vol_text = findViewById(R.id.vol_text);
        brt_text = findViewById(R.id.brt_text);
        vol_progress = findViewById(R.id.vol_progress);
        brt_progress = findViewById(R.id.brt_progress);
        vol_progress_container = findViewById(R.id.vol_progress_container);
        brt_progress_container = findViewById(R.id.brt_progress_container);
        vol_text_container = findViewById(R.id.vol_text_container);
        brt_text_container = findViewById(R.id.brt_text_container);
        vol_icon = findViewById(R.id.vol_icon);
        brt_icon = findViewById(R.id.brt_icon);

        videoBack = findViewById(R.id.video_back);
        exo_rotate = findViewById(R.id.exo_rotate);
        lock = findViewById(R.id.exo_lock);
        exo_play_speed = findViewById(R.id.exo_play_speed);
        rootlayoutvideo = findViewById(R.id.exo_root_layout);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        zoomLayout = findViewById(R.id.zoom_layout);
        zoom_perc = findViewById(R.id.zoom_percentage);
        zoomContainer = findViewById(R.id.zoom_container);
        double_tap_play_pause = findViewById(R.id.double_tap_play_pause);
        scaleGestureDetector = new ScaleGestureDetector(this, new ScaleDetector());
        trackSelector = new DefaultTrackSelector(this);
        /* Picture in Picture */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            pictureInpicture = new PictureInPictureParams.Builder();
        }

        btn_next.setOnClickListener(this);
        btn_prev.setOnClickListener(this);
        exo_forword.setOnClickListener(this);
        exo_rewind.setOnClickListener(this);
        videoBack.setOnClickListener(this);
        lock.setOnClickListener(this);
        exo_play_speed.setOnClickListener(this);
        exo_rotate.setOnClickListener(this);


        video_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popupMenu = new PopupMenu(VideoActivity.this, v);
                popupMenu.setOnMenuItemClickListener(VideoActivity.this);
                popupMenu.inflate(R.menu.video_menu);
                popupMenu.show();

            }
        });


        // Swipe to increase Volume and Brightness
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        device_width = displayMetrics.widthPixels;
        device_height = displayMetrics.heightPixels;

        playerView.setOnTouchListener(new OnSwipeTouchListener(this) {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {

                switch (motionEvent.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        /* Swipe Forward */
                        seekdistance = 0;
                        distanceCovered = 0;

                        playerView.showController();
                        start = true;
                        if (motionEvent.getX() < (device_width / 2)) {
                            left = true;
                            right = false;
                        } else if (motionEvent.getX() > (device_width / 2)) {
                            left = false;
                            right = true;
                        }
                        baseX = motionEvent.getX();
                        baseY = motionEvent.getY();
                        break;

                    case MotionEvent.ACTION_MOVE:
/*
                        swipe_move = true;
                        diffX = (long) Math.ceil(motionEvent.getX() - baseX);
                        diffY = (long) Math.ceil(motionEvent.getY() - baseY);
                        double brightnessSpeed = 0.005;

                        *//* Swipe Forward *//*
                        final float x = motionEvent.getX();
                        final float y = motionEvent.getY();
                        distanceCovered = getDistance(x, y, motionEvent);

                        try {
                            if (checkSeek) {
                                changeSeek(motionEvent.getHistoricalX(0, 0),
                                        motionEvent.getHistoricalY(0, 0),
                                        x, y, distanceCovered, "X");
                            }
                        } catch (IllegalArgumentException e) {
                            // Remain it null because it will give error everytime you swipe to forward - Akshat
                        }

                        if (Math.abs(diffY) > MINIMUM_DISTANCE) {
                            start = true;
                            if (Math.abs(diffY) > Math.abs(diffX)) {
                                boolean value;
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    value = android.provider.Settings.System.canWrite(getApplicationContext());
                                    if (value) {
                                        if (left) {

                                            contentResolver = getContentResolver();
                                            window = getWindow();
                                            try {
                                                android.provider.Settings.System.putInt(contentResolver,
                                                        android.provider.Settings.System.SCREEN_BRIGHTNESS_MODE,
                                                        Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                                                brightness = android.provider.Settings.System.getInt(contentResolver, Settings.System.SCREEN_BRIGHTNESS);
                                            } catch (Settings.SettingNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                            int new_brightness = (int) (brightness - (diffY * brightnessSpeed));
                                            if (new_brightness > 250) {
                                                new_brightness = 250;
                                            } else if (new_brightness < 1) {
                                                new_brightness = 1;
                                            }
                                            double brt_percentage = Math.ceil((((double) new_brightness / (double) 250) * (double) 100));
                                            brt_progress_container.setVisibility(View.VISIBLE);
                                            brt_text_container.setVisibility(View.VISIBLE);
                                            brt_progress.setProgress((int) brt_percentage);

                                            if (brt_percentage < 30) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness_low);
                                            } else if (brt_percentage > 30 && brt_percentage < 80) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness_medium);
                                            } else if (brt_percentage > 80) {
                                                brt_icon.setImageResource(R.drawable.ic_brightness);
                                            }

                                            brt_text.setText(" " + (int) brt_percentage + "%");
                                            android.provider.Settings.System.putInt(contentResolver,
                                                    android.provider.Settings.System.SCREEN_BRIGHTNESS,
                                                    (new_brightness));

                                            WindowManager.LayoutParams layoutParams = window.getAttributes();
                                            layoutParams.screenBrightness = brightness / (float) 255;
                                            window.setAttributes(layoutParams);

                                        } else if (right) {

                                            vol_text_container.setVisibility(View.VISIBLE);
                                            media_volume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                            int max_volume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                                            double cal = (double) diffY * ((double) max_volume / ((double) (device_height * 2) - brightnessSpeed));
                                            int newMediaVolume = media_volume - (int) cal;

                                            if (newMediaVolume > max_volume) {
                                                newMediaVolume = max_volume;
                                            } else if (newMediaVolume < 1) {
                                                newMediaVolume = 0;
                                            }

                                            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                                                    newMediaVolume, AudioManager.FLAG_REMOVE_SOUND_AND_VIBRATE);

                                            double volPer = Math.ceil((((double) newMediaVolume / (double) max_volume) * (double) 100));
                                            vol_text.setText(" " + (int) volPer + "%");

                                            if (volPer < 1) {
                                                vol_icon.setImageResource(R.drawable.ic_volume_off);
                                                vol_text.setVisibility(View.VISIBLE);
                                                vol_text.setText("Off");
                                            } else if (volPer >= 1) {
                                                vol_icon.setImageResource(R.drawable.ic_volume_up);
                                                vol_text.setVisibility(View.VISIBLE);
                                            }

                                            vol_progress_container.setVisibility(View.VISIBLE);
                                            vol_progress.setProgress((int) volPer);

                                        }
                                        success = true;
                                    } else {

                                        boolean permission;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            permission = Settings.System.canWrite(VideoActivity.this);
                                        } else {
                                            permission = ContextCompat.checkSelfPermission(VideoActivity.this, Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
                                        }
                                        if (permission) {
//                                            Toast.makeText(VideoActivity.this, "Granted", Toast.LENGTH_SHORT).show();
                                        } else {
                                            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                                                Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS);
                                                intent.setData(Uri.parse("package:" + getPackageName()));
                                                startActivityForResult(intent, 111);
                                            } else {
                                                ActivityCompat.requestPermissions(VideoActivity.this, new String[]{Manifest.permission.WRITE_SETTINGS}, 111);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        */
                        break;

                    case MotionEvent.ACTION_UP:
                        swipe_move = false;
                        start = false;
                        vol_progress_container.setVisibility(View.GONE);
                        brt_progress_container.setVisibility(View.GONE);
                        vol_text_container.setVisibility(View.GONE);
                        brt_text_container.setVisibility(View.GONE);
                        break;
                }
                scaleGestureDetector.onTouchEvent(motionEvent);
                return super.onTouch(view, motionEvent);
            }

            @Override
            public void onSingleTouch() {
                super.onSingleTouch();

                if (singleTap) {
                    playerView.showController();
                    singleTap = false;
                } else {
                    playerView.hideController();
                    singleTap = true;
                }

                if (double_tap_play_pause.getVisibility() == View.VISIBLE) {
                    double_tap_play_pause.setVisibility(View.GONE);
                }

            }

            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDoubleTouch() {
                super.onDoubleTouch();

              /*  if (doubleTap) {
                    player.play();
                    double_tap_play_pause.setVisibility(View.GONE);
                    doubleTap = false;
                } else {
                    player.pause();
                    double_tap_play_pause.setVisibility(View.VISIBLE);
                    doubleTap = true;
                }
*/
            }

        });


        activityVideoBinding.shareVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse("https://www.example.com/"))
                        .setDomainUriPrefix("https://thelearningclub.page.link")
                        .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                        .buildDynamicLink();

                Uri dynamicLinkUri = dynamicLink.getUri();

                String shareLink = "https://thelearningclub.page.link/?apn=com.theLearningcLub&link=https://thelearningclub.page.link/?para="
                        + title + "**" + s_pack_id + "**" + id + "**" + s_is_free + "**" + videoType + "**" + url + "";


                Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                        .setLink(Uri.parse(shareLink))
                        .setDomainUriPrefix("https://thelearningclub.page.link")
                        .buildShortDynamicLink()
                        .addOnCompleteListener(VideoActivity.this, new OnCompleteListener<ShortDynamicLink>() {
                            @Override
                            public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                                if (task.isSuccessful()) {
                                    // Short link created
                                    Uri shortLink = task.getResult().getShortLink();
                                    Uri flowchartLink = task.getResult().getPreviewLink();

                                    // share app dialog
                                    Intent intent = new Intent();
                                    intent.setAction(Intent.ACTION_SEND);
                                    intent.putExtra(Intent.EXTRA_TEXT, shortLink.toString());
                                    intent.setType("text/plain");
                                    startActivity(intent);

                                } else {
                                    Toast.makeText(mContext, "Error : Restart your App", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });


                FirebaseDynamicLinks.getInstance().getDynamicLink(getIntent()).addOnSuccessListener(new OnSuccessListener<PendingDynamicLinkData>() {
                    @Override
                    public void onSuccess(PendingDynamicLinkData pendingDynamicLinkData) {

                        Uri deepLink = null;
                        if (pendingDynamicLinkData != null) {
                            deepLink = pendingDynamicLinkData.getLink();
                        }

                        // trying to get from the intent
                        if (deepLink != null) {

                            deepLink = getIntent().getData();
                            String data = deepLink.getQueryParameter("currPage");
//                            Toast.makeText(VideoActivity.this, "Para is " + data, Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });


        activityVideoBinding.ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        user_status = "4";
        new UserStatusTask().execute();

        videoType = getIntent().getStringExtra("videoType");


//        if(getIntent().getStringExtra("change").equals("0")) {
            purachase_package_video_modelslist = getIntent().getExtras().getParcelableArrayList("ARRAYLIST");
//        }else{
//            purachase_package_video_modelslist2 = getIntent().getExtras().getParcelableArrayList("ARRAYLIST");
//        }

        if (videoType.equals("0")) {
            url = getIntent().getStringExtra("URI");
            title = getIntent().getStringExtra("VIDEO_TITLE");
            desc = getIntent().getStringExtra("VIDEO_DESC");
            s_pack_id = getIntent().getStringExtra("s_pack_id");
            s_pack_name = getIntent().getStringExtra("s_pack_name");
            id = getIntent().getStringExtra("VIDEO_ID");
            s_is_free = getIntent().getStringExtra("is_free");
            position = getIntent().getIntExtra("position", 1);
            videotitleList = getIntent().getStringArrayListExtra("titleArrayList");
            videoList = getIntent().getStringArrayListExtra("videoArrayList");
            videovduration = getIntent().getIntExtra("viewduration", 0);
//            videovduration=Integer.parseInt(viewduration);
        } else if (videoType.equals("1")) {
            url = getIntent().getStringExtra("URI");
            title = getIntent().getStringExtra("VIDEO_TITLE");
            desc = getIntent().getStringExtra("VIDEO_DESC");
            s_pack_id = getIntent().getStringExtra("s_pack_id");
            s_pack_name = getIntent().getStringExtra("s_pack_name");
            id = getIntent().getStringExtra("VIDEO_ID");
            s_is_free = getIntent().getStringExtra("is_free");
            pack_type = getIntent().getStringExtra("s_pack_type");
            position = getIntent().getIntExtra("position", 1);
            videotitleList = getIntent().getStringArrayListExtra("titleArrayList");
            videoList = getIntent().getStringArrayListExtra("videoArrayList");
            videovduration = getIntent().getIntExtra("viewduration", 0);
//            videovduration=Integer.parseInt(viewduration);
        }
//        videoList.add(url);
        packageVideoAdapter = new PackageVideoAdapter(s_is_free, purachase_package_video_modelslist, this, new FilterClick() {
            @Override
            public void filterClick(int positions) {

                int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(positions).getVideoview_Time().toString()) * 1000;

                try{
                    if (s_is_free != null && s_is_free.equals("0")) {
                        if (!purachase_package_video_modelslist.get(positions).getIs_free().equals("0")) {

                            videoWatchedTime = player.getDuration() / 1000;
                            currentvideoposition = player.getCurrentPosition() / 1000;
                            if (videoWatchedTime > 0) {
                                runfrom = true;
                                new Addviewapi().execute();
                            }
                            position = positions;
                            videovduration = viewvdideo;
                            id = purachase_package_video_modelslist.get(positions).getVideo_id();
                            player.stop();
                            playVideo();
                        } else {
                            Toast.makeText(mContext, "To watch this amazing video, please purchase the package.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        videoWatchedTime = player.getDuration() / 1000;
                        currentvideoposition = player.getCurrentPosition() / 1000;
                        if (videoWatchedTime > 0) {
                            runfrom = true;
                            new Addviewapi().execute();
                        }
                        position = positions;
                        videovduration = viewvdideo;
                        id = purachase_package_video_modelslist.get(positions).getVideo_id();
                        player.stop();
                        playVideo();
                    }
                } catch (Exception e) {
                    System.out.println(">>>>Error : " + e.toString());
                }


            }
        });
        activityVideoBinding.rvVideoList.setAdapter(packageVideoAdapter);


        activityVideoBinding.videoTitle.setText(title);

//        try {
//            from=getIntent().getStringExtra("from");
//        }catch (Exception e){
//            from="2";
//        }
//
//        if (from.equals("1")){
//            new view_download_list_asy2().execute();
//        }else{
//            new view_download_list_asy().execute();
//        }

//        new view_download_list_asy().execute();

        playVideo();

        if (videoType.equals("0")) {
            url = url;
//                    +"?defaviddb="+id
//                    +"&android_version"+ Build.VERSION.RELEASE
//                    +"&app_version="+ BuildConfig.VERSION_NAME;
            System.out.println("url      " + url);
        } else if (videoType.equals("1")) {
            url = url;
//                    +"?defaviddb="+id
//                    +"&android_version"+ Build.VERSION.RELEASE
//                    +"&app_version="+ BuildConfig.VERSION_NAME;
            System.out.println("url      " + url);
        }


//        activityVideoBinding.webViewVideo.loadUrl(url);

        activityVideoBinding.btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                changeScreenOrientation();
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoWatchedTime = player.getDuration() / 1000;
                currentvideoposition = player.getCurrentPosition() / 1000;
//                Toast.makeText(mContext, currentvideoposition
//                        +"/"+videoWatchedTime, Toast.LENGTH_SHORT).show();
                if(currentvideoposition==(videoWatchedTime-1)){
                    new Addviewapi().execute();
//                    Toast.makeText(mContext, "currentvideoposition:"+id, Toast.LENGTH_SHORT).show();
                }
                handler.postDelayed(this, 1000); //1000ms frequency of updates.
            }
        }, 1000);
    }




    /* >>>>>>>>> Akshat <<<<<<<<<<<<<*/


    /* >>>>>>>>>>>>   Swipe Forward <<<<<<<<<<<<<< */
    public void changeSeek(float X, float Y, float x, float y, float distance, String type) {

        if (type == "Y" && x == X) {
            distance = distance / 300;
            if (y < Y) {
                seekCommon(distance);
            } else {
                seekCommon(-distance);
            }
        } else if (type == "X" && y == Y) {
            distance = distance / 200;
            if (x > X) {
                seekCommon(distance);
            } else {
                seekCommon(-distance);
            }
        }
    }

    public void seekCommon(float distance) {
        seekdistance += distance * 60000;
        if (player != null) {
            Log.e("after", player.getCurrentPosition() + (int) (distance * 60000) + "");
            Log.e("seek distance", (int) (seekdistance) + "");
            if (player.getCurrentPosition() + (int) (distance * 60000) > 0 && player.getCurrentPosition() + (int) (distance * 60000) < player.getDuration() + 10) {
                player.seekTo(player.getCurrentPosition() + (int) (distance * 60000));
            }
        }
    }

    float getDistance(float startX, float startY, MotionEvent ev) {
        float distanceSum = 0;
        final int historySize = ev.getHistorySize();
        for (int h = 0; h < historySize; h++) {
            float hx = ev.getHistoricalX(0, h);
            float hy = ev.getHistoricalY(0, h);
            float dx = (hx - startX);
            float dy = (hy - startY);
            distanceSum += Math.sqrt(dx * dx + dy * dy);
            startX = hx;
            startY = hy;
        }
        float dx = (ev.getX(0) - startX);
        float dy = (ev.getY(0) - startY);
        distanceSum += Math.sqrt(dx * dx + dy * dy);
        return distanceSum;
    }


    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putLong("int_value", player.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        videoWatchedTime = savedInstanceState.getLong("int_value");
        player.seekTo(videoWatchedTime);
        super.onRestoreInstanceState(savedInstanceState);
    }


    @Override
    public boolean onMenuItemClick(MenuItem item) {
        if (item.getItemId() == R.id.exo_unlock) {

            controlsMode = ControlsMode.LOCK;
            rootlayoutvideo.setVisibility(View.INVISIBLE);
            lock.setVisibility(View.VISIBLE);
            lockDeviceRotation(true);

            return true;
        }
        else if (item.getItemId() == R.id.exo_pip) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Rational aspectRatio = new Rational(16, 9);
                pictureInpicture.setAspectRatio(aspectRatio);
                enterPictureInPictureMode(pictureInpicture.build());
                activityVideoBinding.BottomLayout.setVisibility(View.GONE);
            } else {
                Log.wtf("not Oreo", "yes");
            }
            return true;
        }
        else if (item.getItemId() == R.id.quality) {
            if (!isShowingTrackSelectionDialog
                    && com.theLearningcLub.TrackSelectionDialog.willHaveContent(player)) {
                isShowingTrackSelectionDialog = true;
                com.theLearningcLub.TrackSelectionDialog trackSelectionDialog =
                        com.theLearningcLub.TrackSelectionDialog.createForPlayer(
                                player,
                                /* onDismissListener= */ dismissedDialog -> isShowingTrackSelectionDialog = false);
                trackSelectionDialog.show(getSupportFragmentManager(), /* tag= */ null);
            }
            return true;
        }
        return false;
    }


    public void hideBottomBar() {

        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) {
            View v = this.getWindow().getDecorView();
            v.setSystemUiVisibility(View.GONE);
        } else if (Build.VERSION.SDK_INT >= 19) {
            View decodeView = getWindow().getDecorView();
            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
            decodeView.setSystemUiVisibility(uiOptions);
        }

    }


    private void playVideo() {

        String url = videoList.get(position);
        title5.setText(videotitleList.get(position));

        player = new SimpleExoPlayer.Builder(this)
                .build();
        DataSource.Factory dataSourceFactory = new DefaultHttpDataSource.Factory();
        concatenatingMediaSource = new ConcatenatingMediaSource();
        for (int i = 0; i < videoList.size(); i++) {
            new File(videoList.get(i));

            MediaItem mediaItem = MediaItem.fromUri(videoList.get(i));

//            if (id.equals("515")) {
//                MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
////                        .createMediaSource(mediaItem);
//                    .createMediaSource(MediaItem.fromUri(url5));
//                concatenatingMediaSource.addMediaSource(mediaSource);
//            } else {
                MediaSource mediaSource = new ProgressiveMediaSource.Factory(dataSourceFactory)
                        .createMediaSource(mediaItem);
//                    .createMediaSource(MediaItem.fromUri(url5));
                concatenatingMediaSource.addMediaSource(mediaSource);
//            }

        }

        playerView.setPlayer(player);
        playerView.setKeepScreenOn(true);
        player.prepare(concatenatingMediaSource);
        player.seekTo(position, C.TIME_UNSET);
        playerError();
    }

    private void playerError() {
        player.addListener(new Player.Listener() {
            @Override
            public void onPlayerError(PlaybackException error) {
                Player.Listener.super.onPlayerError(error);
                Toast.makeText(VideoActivity.this, "Video Playing Error" + error, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                int latestWindowIndex = player.getCurrentWindowIndex();
                if (latestWindowIndex != lastWindowIndex) {
                    // item selected in playlist has changed, handle here
                    lastWindowIndex = latestWindowIndex;
                    position=lastWindowIndex;
                    title5.setText(videotitleList.get(lastWindowIndex));
                    int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;
                    id = purachase_package_video_modelslist.get(position).getVideo_id();
                    videovduration = viewvdideo;

                    player.seekTo(videovduration);
//                    Handler videohandle= new Handler();
//                    videohandle.postDelayed(new Runnable() {
//                        @Override
//                        public void run() {
//                            if(player.isPlaying()) {
//                                player.seekTo(videovduration);
//                            }
//                        }
//                    },2000);
                }
                videoWatchedTime = player.getDuration() / 1000;
                currentvideoposition = player.getCurrentPosition() / 1000;
                if (videoWatchedTime > 0) {
                    runfrom = true;
                    new Addviewapi().execute();
                }
            }

            @Override
            public void onMediaItemTransition(
                    MediaItem mediaItem, int reason) {
                int latestWindowIndex = player.getCurrentWindowIndex();
                if (latestWindowIndex != lastWindowIndex) {
                    // item selected in playlist has changed, handle here
                    lastWindowIndex = latestWindowIndex;
                    title5.setText(videotitleList.get(lastWindowIndex));
                    position=lastWindowIndex;
                    int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;
                    id = purachase_package_video_modelslist.get(position).getVideo_id();
                    videovduration = viewvdideo;
                    player.seekTo(videovduration);

                }
                videoWatchedTime = player.getDuration() / 1000;
                currentvideoposition = player.getCurrentPosition() / 1000;
                if (videoWatchedTime > 0) {
                    runfrom = true;
                    new Addviewapi().execute();
                }
            }
        });
        player.setPlayWhenReady(true);
        player.seekTo(videovduration);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onPause() {
        super.onPause();
        player.setPlayWhenReady(false);
        player.getPlaybackState();
        if (isInPictureInPictureMode()) {
            player.setPlayWhenReady(true);
        } else {
            player.setPlayWhenReady(false);
            player.getPlaybackState();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
        activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        player.setPlayWhenReady(true);
        player.getPlaybackState();
    }

    private void setFullScreen() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    public void lockDeviceRotation(boolean value) {
        if (value) {
            int currentOrientation = getResources().getConfiguration().orientation;
            if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
//                activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
            }
        } else {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_USER);
                activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR);
                activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.video_back:
                onBackPressed();
                break;
            case R.id.exo_lock:
                controlsMode = ControlsMode.FULLSCREEN;
                rootlayoutvideo.setVisibility(View.VISIBLE);
                lock.setVisibility(View.INVISIBLE);
//                Toast.makeText(mContext, "working", Toast.LENGTH_SHORT).show();
//                lockDeviceRotation(false);
                break;
            case R.id.exo_play_speed:
                MaterialAlertDialogBuilder alertDialog = new MaterialAlertDialogBuilder(VideoActivity.this, R.style.MyThemeOverlay_MaterialComponents_MaterialAlertDialog);
                alertDialog.setTitle("Select Playback Speed").setPositiveButton("Ok", null).setNegativeButton("Cancel", null);
                String[] items = {"0.25x", "0.5x", "1x Normal Speed", "1.5x", "2x"};

                int checkedItem;

                if (playbackspeed == 0.25f) {
                    checkedItem = 0;
                } else if (playbackspeed == 0.5f) {
                    checkedItem = 1;
                } else if (playbackspeed == 1f) {
                    checkedItem = 2;
                } else if (playbackspeed == 1.5f) {
                    checkedItem = 3;
                } else if (playbackspeed == 2f) {
                    checkedItem = 4;
                } else {
                    checkedItem = 2;
                }

                alertDialog.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which) {
                            case 0:
                                playbackspeed = 0.25f;
                                playbackParameters = new PlaybackParameters(playbackspeed);
                                player.setPlaybackParameters(playbackParameters);
                                exo_play_speed.setImageResource(R.drawable.ic_baseline_speed);
                                break;
                            case 1:
                                playbackspeed = 0.5f;
                                playbackParameters = new PlaybackParameters(playbackspeed);
                                player.setPlaybackParameters(playbackParameters);
                                exo_play_speed.setImageResource(R.drawable.ic_baseline_speed);
                                break;
                            case 2:
                                playbackspeed = 1f;
                                playbackParameters = new PlaybackParameters(playbackspeed);
                                player.setPlaybackParameters(playbackParameters);
                                exo_play_speed.setImageResource(R.drawable.ic_speed1x);
                                break;
                            case 3:
                                playbackspeed = 1.5f;
                                playbackParameters = new PlaybackParameters(playbackspeed);
                                player.setPlaybackParameters(playbackParameters);
                                exo_play_speed.setImageResource(R.drawable.ic_baseline_speed);
                                break;
                            case 4:
                                playbackspeed = 2f;
                                playbackParameters = new PlaybackParameters(playbackspeed);
                                player.setPlaybackParameters(playbackParameters);
                                exo_play_speed.setImageResource(R.drawable.ic_speed2x);
                                break;
                            default:
                                break;
                        }
                    }
                });

                alertDialog.show();
                break;
            case R.id.exo_next:
                try {
                    player.stop();
                    position++;
                    int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;
                    id = purachase_package_video_modelslist.get(position).getVideo_id();
                    videovduration = viewvdideo;
                    playVideo();
                } catch (Exception e) {
                    Toast.makeText(this, "NO next Video", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exo_prev:
                try {
                    player.stop();
                    position--;
                    int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;
                    id = purachase_package_video_modelslist.get(position).getVideo_id();
                    videovduration = viewvdideo;
                    playVideo();
                } catch (Exception e) {
                    Toast.makeText(this, "NO previous Video", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.exo_rewind:
                try {
                    player.seekTo(player.getCurrentPosition() - 10000);
                } catch (Exception e) {
                }
                break;
            case R.id.exo_forword:
                try {
                    player.seekTo(player.getCurrentPosition() + 10000);
                } catch (Exception e) {
                }
                break;
            case R.id.exo_rotate:
                try {
                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
                        activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
                    } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
                        activityVideoBinding.BottomLayout.setVisibility(View.GONE);
                    }
                }catch (Exception e){

                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 111) {
            boolean value;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                value = android.provider.Settings.System.canWrite(getApplicationContext());
                if (value) {
                    success = true;
                } else {
                    Toast.makeText(this, "Not Granted", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private class ScaleDetector extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            scale_factor *= detector.getScaleFactor();
            scale_factor = Math.max(0.5f, Math.min(scale_factor, 6.0f));

            zoomLayout.setScaleX(scale_factor);
            zoomLayout.setScaleY(scale_factor);
            int percentage = (int) (scale_factor * 100);
            zoom_perc.setText(" " + percentage + "%");
            zoomContainer.setVisibility(View.VISIBLE);

            brt_text_container.setVisibility(View.GONE);
            vol_text_container.setVisibility(View.GONE);
            brt_progress_container.setVisibility(View.GONE);
            vol_progress_container.setVisibility(View.GONE);

            return true;
        }

        @Override
        public void onScaleEnd(ScaleGestureDetector detector) {

            zoomContainer.setVisibility(View.GONE);

            super.onScaleEnd(detector);
        }
    }


    @Override
    public void onBackPressed() {

        if (controlsMode == ControlsMode.LOCK) return;

        int orientation = getResources().getConfiguration().orientation;
        if (orientation != Configuration.ORIENTATION_LANDSCAPE) {
            videoWatchedTime = player.getDuration() / 1000;
            currentvideoposition = player.getCurrentPosition() / 1000;
            if (videoWatchedTime > 0) {
                runfrom = true;
                new Addviewapi().execute();
            }
            super.onBackPressed();
        } else {
            if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
                this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            } else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
                return;
            }
        }

        if (player.isPlaying()) {
            player.stop();
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



    /*-----------------------------------AsyncTask---------------------------- */

    class view_download_list_asy2 extends AsyncTask<String, String, String> {

        String status,s_id,s_desc,s_video_date,s_title,s_video,s_image,s_v_id,is_free="";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            purachase_package_video_modelslist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_new_id",s_pack_id);
//            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("app_version", BuildConfig.VERSION_NAME);

            String response = webRequest.makeWebServiceCall(AllUrl.view_package_video_list,2,hashMap);
//
            System.out.println(" View defaenc_dvidn mypack video response >>>>>>>>>>>>>>>?  " +response);
            System.out.println(" View defaenc_dvidn mypack video parameter >>>>>>>>>>>>>>> ? "+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status=jsonObject.getString("status");

                JSONArray jsonArray=jsonObject.getJSONArray("body");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                    s_id=jsonObject1.getString("videos_id");
                    s_desc=jsonObject1.getString("video_description");
                    s_video_date=jsonObject1.getString("videos_date");
                    s_title=jsonObject1.getString("video_name");
                    s_video=jsonObject1.getString("videncd");
                    s_image=jsonObject1.getString("image");
                    if (jsonObject1.has("free_status")){
                        is_free = jsonObject1.getString("free_status");
                    }

                    String videoviewtime;
                    String videotime;
                    if(jsonObject1.has("view_status")){

                        videoviewtime=jsonObject1.getString("view_status");
                        videotime=jsonObject1.getString("v_duration");
                    }else {

                        videoviewtime="0";
                        videotime="0";
                    }



//                    Purachase_package_free_video_Model video_model=new Purachase_package_free_video_Model();
//                    video_model.setVideo_id(s_id);
//                    video_model.setVideo_title(s_title);
//                    video_model.setVideo(s_video);
//                    video_model.setVideo_desc(s_desc);
//                    video_model.setVideo_date(s_video_date);
//                    video_model.setVideo_image(s_image);
//                    video_model.setIs_free(is_free);
//
//                    video_model.setVideoview_Time(videoviewtime);
//                    video_model.setVideototal_duration(videotime);
//                    purachase_package_video_modelslist.add(video_model);


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
            packageVideoAdapter.notifyDataSetChanged();
        }
    }

    /*-----------------------------------AsyncTask---------------------------- */

    class view_download_list_asy extends AsyncTask<String, String, String> {

        String status,s_id,s_desc,s_video_date,s_title,s_video,s_image,is_free;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            purachase_package_video_modelslist.clear();
        }
        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_id",s_pack_id);
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("app_version", BuildConfig.VERSION_NAME);

            String response =  webRequest.makeWebServiceCall(AllUrl.MainURL+"defaenc_dvidn_new.php",2,hashMap);

            System.out.println(" View DwnloadVideo_list__play_Fragment  video response >>>>>>>>>>>>>>>  "+response);
            System.out.println(" View DwnloadVideo_list__play_Fragment video parameter >>>>>>>>>>>>>>>  "+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("data");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    JSONArray jsonArray1 = c.getJSONArray("vid_data");
                    for (int p=0;p<jsonArray1.length();p++)
                    {

                        JSONObject jsonObject1 = jsonArray1.getJSONObject(p);

                        s_id=jsonObject1.getString("vid_id");
                        s_desc=jsonObject1.getString("description");
                        s_video_date=jsonObject1.getString("videos_date");
                        s_title=jsonObject1.getString("Title");
                        s_video=jsonObject1.getString("videncd");
                        s_image=jsonObject1.getString("image");
                        if (jsonObject1.has("is_free")){
                            is_free = jsonObject1.getString("is_free");
                        }

                        String videoviewtime;
                        String videotime;
                        if(jsonObject1.has("view_status")){

                            videoviewtime=jsonObject1.getString("view_status");
                            videotime=jsonObject1.getString("v_duration");
                        }else {

                            videoviewtime="0";
                            videotime="0";
                        }


//                        Purachase_package_free_video_Model video_model=new Purachase_package_free_video_Model();
//                        video_model.setVideo_id(s_id);
//                        video_model.setVideo_title(s_title);
//                        video_model.setVideo(s_video);
//                        video_model.setVideo_desc(s_desc);
//                        video_model.setVideo_date(s_video_date);
//                        video_model.setVideo_image(s_image);
//                        video_model.setIs_free(is_free);
//
//                        video_model.setVideoview_Time(videoviewtime);
//                        video_model.setVideototal_duration(videotime);
//                        purachase_package_video_modelslist.add(video_model);
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
            packageVideoAdapter.notifyDataSetChanged();
        }
    }



    class Addviewapi extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("pack_id", s_pack_id);
            hashMap.put("video_id", id);
            hashMap.put("pack_type", from);
            hashMap.put("free_status", s_is_free);
//            hashMap.put("free_status", "0");
            hashMap.put("user_id", sessionManager.getSavedUserId());
            hashMap.put("view_status", currentvideoposition + "");
            hashMap.put("v_duration", videoWatchedTime + "");

            String response = webRequest.makeWebServiceCall(AllUrl.MainURL + "add_view_video.php", 2, hashMap);

            System.out.println(" View Addviewapi  video response >>>>>>>>>>>>>>>  " + response);
            System.out.println(" View Addviewapi video parameter >>>>>>>>>>>>>>>  " + hashMap);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(purachase_package_video_modelslist.get(position).getVideo_id().equals(id)){
                if (currentvideoposition > 0) {
                    purachase_package_video_modelslist.get(position).setVideoview_Time(currentvideoposition + "");
                    purachase_package_video_modelslist.get(position).setVideototal_duration(videoWatchedTime + "");
//                    Toast.makeText(mContext, "working"+purachase_package_video_modelslist.get(position).getVideoview_Time(), Toast.LENGTH_SHORT).show();
                    packageVideoAdapter.notifyDataSetChanged();
                }
            }

        }
    }


    private void changeScreenOrientation() {
        int orientation = getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            activityVideoBinding.BottomLayout.setVisibility(View.VISIBLE);
            int height = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 250, getResources().getDisplayMetrics());
            activityVideoBinding.videoview.getLayoutParams().height = height;
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            activityVideoBinding.BottomLayout.setVisibility(View.GONE);
            activityVideoBinding.videoview.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
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


    /* Akshat (Picture in Picture) */
    @Override
    public void onPictureInPictureModeChanged(boolean isInPictureInPictureMode, Configuration newConfig) {
        super.onPictureInPictureModeChanged(isInPictureInPictureMode, newConfig);
        isCrossChacked = isInPictureInPictureMode;
        if (isInPictureInPictureMode) {
            playerView.hideController();
        } else {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
            playerView.showController();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isCrossChacked) {
            player.release();
            finish();
        }
    }

}