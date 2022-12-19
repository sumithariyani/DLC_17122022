package com.theLearningcLub;

import androidx.appcompat.widget.AppCompatEditText;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theLearningcLub.Model_Class.ClassModel;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityLoginBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    ActivityLoginBinding activityLoginBinding;
    String mobile_number;
    String DEVICEID;
    String s_mobile;
    String name,email,otp,sotp,motp,s_user_id,s_login_key,verified_status,snumber,is_agent;
    ArrayList<ClassModel> list = new ArrayList<>();
    String status,message,CONTACT,status1,message1;
    boolean isload=true;
    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityLoginBinding = DataBindingUtil.setContentView(mContext,R.layout.activity_login);

        activityLoginBinding.btnLogin.setOnClickListener(this);
        activityLoginBinding.llRegister.setOnClickListener(this);
        activityLoginBinding.tvClearId.setOnClickListener(this);
        activityLoginBinding.supportCallNumber.setOnClickListener(this);
        DEVICEID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_login:

                CommonFunction.hideKeyboardFrom(mContext,view);

                mobile_number = Objects.requireNonNull(activityLoginBinding.etMobileNumber.getText()).toString().trim();

                if(isload){
                    isload=false;
                    if (TextUtils.isEmpty(mobile_number)){
                        activityLoginBinding.etMobileNumber.setError("Please enter mobile number");
                    }else if (mobile_number.length() < 10){
                        activityLoginBinding.etMobileNumber.setError("Please enter valid mobile number");
                    }else {
                        loginApi();
                    }
                }


                break;

                case R.id.supportCallNumber:
                    Intent callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(Uri.parse("tel:8989780888"));
                    startActivity(callIntent);
                break;
            case R.id.ll_register:
                startActivity(new Intent(LoginActivity.this,YourNameActivity.class));
                break;
            case R.id.tvClear_id:
                clearDialog(mContext);
                break;
        }
    }

    public void clearDialog(final Context context) {
        Dialog dialog_clear = new Dialog(context);

        dialog_clear.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog_clear.setContentView(R.layout.dialog_clear);
        dialog_clear.setCancelable(true);
        dialog_clear.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog_clear.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        AppCompatEditText etMobileClear = dialog_clear.findViewById(R.id.etMobileClear);

        (dialog_clear.findViewById(R.id.bt_clear)).setOnClickListener(v -> {

            s_mobile = Objects.requireNonNull(etMobileClear.getText()).toString().trim();

            if(s_mobile.length()<10){
                etMobileClear.setError("Enter correct mobile no.");
            }else {
                DeviceClearApi();
                dialog_clear.dismiss();
            }
        });

        dialog_clear.show();
        dialog_clear.getWindow().setAttributes(lp);
    }

    private void loginApi(){
        if (isConnectingToInternet(mContext)) {

            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.MainURL+"defaenclog_otp_jk.php"/*AllUrl.defaenclog_otpApi*/,
                    response -> {
                        // Display the first 500 characters of the response string.
//                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("loginApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
//                            System.out.println("loginApi  responce  >>>>>>>>   "+response);
                            JSONObject jsonObject1=jsonObject.getJSONObject("response");
                            status = jsonObject1.getString("status");
                            message = jsonObject1.getString("msg");

                            if (status.equals("true")){
                                isload=true;
                                JSONObject object = new JSONObject(response);
//                                System.out.println("login  responce  ****  >>>>>>>>   "+response);
                                JSONObject object1 = object.getJSONObject("response");
                                status1 = object1.getString("status");
                                message1 = object1.getString("msg");
                                CONTACT = object1.getString("contact");

                                sessionManager.setcontact(CONTACT);
//                                Otpconfirmationdialog();

                                Toast.makeText(LoginActivity.this, message1+"", Toast.LENGTH_LONG).show();

                                Intent intent = new Intent(LoginActivity.this,LoginOtpActivity.class);
                                startActivity(intent);
                            }
//                            Toast.makeText(LoginActivity.this, message+"", Toast.LENGTH_LONG).show();

                            hideProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                Intent intentError = new Intent(LoginActivity.this, ServerProblemActivity.class);
                startActivity(intentError);
                finish();
                System.out.println("loginApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("contact",mobile_number);
                    params.put("device_id",DEVICEID);
                    System.out.println("loginApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            stringRequest.setRetryPolicy(new RetryPolicy() {
                @Override
                public int getCurrentTimeout() {
                    return 50000;
                }

                @Override
                public int getCurrentRetryCount() {
                    return 50000;
                }

                @Override
                public void retry(VolleyError error) throws VolleyError {

                }
            });
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(LoginActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

    class OTPAs2 extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest request = new WebRequest();

            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("contact",sessionManager.getcontact());
            hashMap.put("otp",otp);
            hashMap.put("device_id",DEVICEID);

            String response = request.makeWebServiceCall(AllUrl.defaencmobloginApi,2,hashMap);
//            String response = request.makeWebServiceCall(AllUrl.MainURL+"defaenclog_otp_jk.php",2,hashMap);

            System.out.print("defaencmoblogin >>>>>>>>>>>>>>>>>   "+hashMap);
            System.out.print(" defaencmoblogin responce >>>>>>>>>>>>>>>>>   "+response);
            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1=jsonObject.getJSONObject("response");
                sotp = jsonObject1.getString("status");
                motp = jsonObject1.getString("msg");

                if (sotp.equals("true")){
                    s_login_key = jsonObject1.getString("login_key");
                    email = jsonObject1.getString("email");
                    verified_status = jsonObject1.getString("verified_status");
                    snumber = jsonObject1.getString("contact");
                    s_user_id = jsonObject1.getString("user_id");
                    name = jsonObject1.getString("name");
                    is_agent = jsonObject1.getString("is_agent");

                }
                sessionManager.setSavedUserId(s_user_id);
                sessionManager.setUserLoggedIn(true);

                sessionManager.setLoginkey(s_login_key);
                sessionManager.setVerifystatus(verified_status);
                sessionManager.setContact(snumber);
                sessionManager.setSavedEMAIL(email);
                sessionManager.setSavedNAME(name);
                sessionManager.setOtp(otp);
                sessionManager.setIsAgent(is_agent);
                ClassModel fruitModel = new ClassModel();
                fruitModel.setUserid(s_user_id);

                list.add(fruitModel);

                System.out.print("User ID >>>>>>>>>>>>>>>>>>  "+s_user_id);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                hideProgressDialog();
                if (sotp.equals("true")){
                    sessionManager.setnokey("0");
                    Intent in=new Intent(LoginActivity.this,HomeActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }else {
                    CommonFunction.showToastSingle(LoginActivity.this, motp);
                }
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    class ResendAs extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request=new WebRequest();
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("contact",sessionManager.getcontact());
            hashMap.put("device_id",DEVICEID);

            String response= request.makeWebServiceCall(AllUrl.defaenclog_otpApi,2,hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                System.out.println("login  responce  ****  >>>>>>>>   "+response);
                JSONObject jsonObject1=jsonObject.getJSONObject("response");
                status = jsonObject1.getString("status");
                message = jsonObject1.getString("msg");
                CONTACT = jsonObject1.getString("contact");
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            Toast.makeText(LoginActivity.this, message+" to "+sessionManager.getcontact(), Toast.LENGTH_SHORT).show();
        }
    }

    private void DeviceClearApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.defaenc_clear_device_idApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("DeviceClearApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObj = new JSONObject(response);

                            String update_status = jsonObj.getJSONObject("response").getString("status");
                            String update_msg = jsonObj.getJSONObject("response").getString("msg");

                            Toast.makeText(mContext, update_msg, Toast.LENGTH_SHORT).show();

                            hideProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("DeviceClearApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("mobile_no",s_mobile);
                    System.out.println("DeviceClearApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(LoginActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

    private void Otpconfirmationdialog() {
        Dialog dialog1 = new Dialog(LoginActivity.this);
        dialog1.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialog1.setContentView(R.layout.otpverification_dialog);
        dialog1.setCancelable(true);
        dialog1.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog1.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        ImageView icon = dialog1.findViewById(R.id.icon);

        AppCompatEditText emailedittext = dialog1.findViewById(R.id.etYourOtp);
        TextView resendotptxt = dialog1.findViewById(R.id.resendotptxt);
        resendotptxt.setGravity(Gravity.CENTER);

        resendotptxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new ResendAs().execute();
            }
        });

        (dialog1.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp = Objects.requireNonNull(emailedittext.getText()).toString();

                if (otp.isEmpty()) {
                    emailedittext.setError("Please Enter OTP !");
                } else {
                    new OTPAs2().execute();
                }
            }
        });

        dialog1.show();
        dialog1.getWindow().setAttributes(lp);
    }

}