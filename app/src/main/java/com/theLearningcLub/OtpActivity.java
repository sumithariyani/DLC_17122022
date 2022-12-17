package com.theLearningcLub;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityOtpBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class OtpActivity extends BaseActivity implements View.OnClickListener{

    ActivityOtpBinding activityOtpBinding;
    EditText [] otpEt;
    String getOtp;
    String status,message,verifiedstatus,user_id,s_user_check;
    String mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityOtpBinding = DataBindingUtil.setContentView(mContext,R.layout.activity_otp);

        mobileNumber = getIntent().getStringExtra("number");

        activityOtpBinding.tvEditNumber.setOnClickListener(this);
        activityOtpBinding.ivBackOtp.setOnClickListener(this);
        activityOtpBinding.btnVerifyAccount.setOnClickListener(this);
        activityOtpBinding.tvResendOtp.setOnClickListener(this);

        activityOtpBinding.tvUserMobile.setText(getResources().getString(R.string.we_sent_an_sms_with_a_4_digit_code_to_919876543210)+" "+mobileNumber);

        setOtpEditTextHandler();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back){
            onBackPressed();
        }else if (view.getId() == R.id.ivBackOtp) {
            onBackPressed();
        } else if (view.getId() == R.id.btn_verify_account){

            CommonFunction.hideKeyboardFrom(mContext,view);

            getOtp = activityOtpBinding.edtPasscode1.getText().toString().trim()+
                    activityOtpBinding.edtPasscode2.getText().toString().trim()+
                    activityOtpBinding.edtPasscode3.getText().toString().trim()+
                    activityOtpBinding.edtPasscode4.getText().toString().trim();

            if (TextUtils.isEmpty(getOtp)){
                CommonFunction.showToastSingle(mContext,"Please enter otp");
            }else if (getOtp.length()< 4){
                CommonFunction.showToastSingle(mContext,"Please enter 4 digit number");
            }else {
                otpApi();
            }

        }else if (view.getId() == R.id.tvEditNumber){
            onBackPressed();
        }else if (view.getId() == R.id.tvResendOtp){
            resendOtpApi();
        }
    }

    private void setOtpEditTextHandler () { //This is the function to be called
        otpEt = new EditText[4];
        otpEt[0] = findViewById(R.id.edt_passcode1);
        otpEt[1] = findViewById(R.id.edt_passcode2);
        otpEt[2] = findViewById(R.id.edt_passcode3);
        otpEt[3] = findViewById(R.id.edt_passcode4);
        for (int i = 0; i < 4; i++) { //Its designed for 6 digit OTP
            final int iVal = i;

            otpEt[iVal].addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    if (iVal == 3 && !otpEt[iVal].getText().toString().isEmpty()) {
                        otpEt[iVal].clearFocus(); //Clears focus when you have entered the last digit of the OTP.
                    } else if (!otpEt[iVal].getText().toString().isEmpty()) {
                        otpEt[iVal + 1].requestFocus(); //focuses on the next edittext after a digit is entered.
                    }
                }
            });

            otpEt[iVal].setOnKeyListener((v, keyCode, event) -> {
                if (event.getAction() != KeyEvent.ACTION_DOWN) {
                    return false; //Dont get confused by this, it is because onKeyListener is called twice and this condition is to avoid it.
                }
                if (keyCode == KeyEvent.KEYCODE_DEL &&
                        otpEt[iVal].getText().toString().isEmpty() && iVal != 0) {
                    //this condition is to handel the delete input by users.
                    otpEt[iVal - 1].setText("");//Deletes the digit of OTP
                    otpEt[iVal - 1].requestFocus();//and sets the focus on previous digit
                }
                return false;
            });
        }
    }

    private void otpApi(){
        try {
            if (isConnectingToInternet(mContext)) {
                showProgressDialog();
                RequestQueue queue = Volley.newRequestQueue(this);
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.verified_otpApi,
                        response -> {
                            // Display the first 500 characters of the response string.
                            Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                            System.out.println("otpApi response>>>>>>>>>>>>>>>" + response);
                            try {

                                JSONObject jsonObject = new JSONObject(response);

                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                                status = jsonObject1.getString("status");
                                message = jsonObject1.getString("msg");
                                verifiedstatus = jsonObject1.getString("verifiedstatus");

                                if(status.equals("true")){
                                    user_id = jsonObject1.getString("user_id");
                                    sessionManager.setVerifystatus(verifiedstatus);
                                    sessionManager.setSavedUserId(user_id);
                                    sessionManager.setUserLoggedIn(true);
                                    sessionManager.setOtp(getOtp);
                                    CommonFunction.showToastSingle(mContext, message);
                                    UserStatusApi();
                                }else{

                                    CommonFunction.showToastSingle(mContext, message);
                                }


                                hideProgressDialog();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                hideProgressDialog();
                            }

                        }, error -> {
                            System.out.println("otpApi error>>>>>>>>>>>>>."+error);
                            hideProgressDialog();
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id",sessionManager.getSavedUserId());
                        params.put("otp",getOtp);
                        System.out.println("otpApi params>>>>>>>>>>>>>>" + params);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }else {
                Intent intent = new Intent(OtpActivity.this, NoInternetActivity.class);
                startActivity(intent);
//                CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void resendOtpApi(){
        try {
            if (isConnectingToInternet(mContext)) {
                showProgressDialog();
                RequestQueue queue = Volley.newRequestQueue(this);
                // Request a string response from the provided URL.
                StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.resend_otpApi,
                        response -> {
                            // Display the first 500 characters of the response string.
                            Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                            System.out.println("resendOtpApi response>>>>>>>>>>>>>>>" + response);
                            try {
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                                status = jsonObject1.getString("status");
                                message = jsonObject1.getString("msg");

                                if (status.equals("true")){
                                    CommonFunction.showToastSingle(mContext, "We have resend the otp to your registered mobile number"+message);
                                }else{
                                    Toast.makeText(mContext, ""+message, Toast.LENGTH_SHORT).show();
                                }

                                hideProgressDialog();

                            } catch (JSONException e) {
                                e.printStackTrace();
                                hideProgressDialog();
                            }

                        }, error -> {
                            System.out.println("resendOtpApi error>>>>>>>>>>>>>."+error);
                            hideProgressDialog();
                        }) {
                    @Override
                    protected Map<String, String> getParams() {
                        Map<String, String> params = new HashMap<>();
                        params.put("user_id",sessionManager.getSavedUserId());
                        System.out.println("resendOtpApi params>>>>>>>>>>>>>>" + params);
                        return params;
                    }
                };
                queue.add(stringRequest);
            }else {
                Intent intent = new Intent(OtpActivity.this, NoInternetActivity.class);
                startActivity(intent);
//                CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    private void UserStatusApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.get_user_statusApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("UserStatusApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                            s_user_check = jsonObject1.getString("user_status");

                            sessionManager.setUserLoggedIn(true);
                            sessionManager.setnokey("0");
                            Intent in = new Intent(mContext,HomeActivity.class);
                            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(in);
                            finish();

                            hideProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("UserStatusApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("UserStatusApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(OtpActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }
}