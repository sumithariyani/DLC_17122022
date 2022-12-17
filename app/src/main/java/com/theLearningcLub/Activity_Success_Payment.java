package com.theLearningcLub;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatImageView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.utils.BaseActivity;

public class Activity_Success_Payment extends BaseActivity {

    AppCompatImageView ivSuccessful;
    TextView RelativeLayoutbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity__success__payment);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        ivSuccessful = findViewById(R.id.ivSuccessful);

        Glide.with(mContext)
                .load(R.drawable.ic_successgif)
                .placeholder(R.drawable.ic_successgif)
                .centerCrop()
                .into(ivSuccessful);


        RelativeLayoutbutton=(TextView)findViewById(R.id.RelativeLayoutbutton);
        RelativeLayoutbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Activity_Success_Payment.this,HomeActivity.class);
                intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent1);
                finish();
            }
        });


    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent2 = new Intent(Activity_Success_Payment.this,HomeActivity.class);
        intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent2);
        finish();
    }
}
