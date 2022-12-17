package com.theLearningcLub;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.BaseFragment;

import java.util.Objects;

public class Fragment_Aboutus extends BaseFragment {

    WebView webview_privacy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment__privacy, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.about_us));

        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        getActivity().getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        webview_privacy = view.findViewById(R.id.webview_privacy);

        webview_privacy.loadUrl("file:///android_asset/DLCAboutus.html"); // Add webview privacy url

        return view;
    }
}
