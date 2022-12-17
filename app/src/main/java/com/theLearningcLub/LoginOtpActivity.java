package com.theLearningcLub;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import androidx.databinding.DataBindingUtil;

import com.theLearningcLub.Model_Class.ClassModel;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityOtpBinding;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class LoginOtpActivity extends BaseActivity implements View.OnClickListener{

    ActivityOtpBinding activityOtpBinding;
    EditText[] otpEt;
    String getOtp;
    String DEVICEID;
    String name,email,sotp,motp,s_user_id,s_login_key,verified_status,snumber,is_agent;
    ArrayList<ClassModel> list = new ArrayList<>();
    String status,message,CONTACT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityOtpBinding = DataBindingUtil.setContentView(mContext,R.layout.activity_otp);

        activityOtpBinding.tvEditNumber.setOnClickListener(this);
        activityOtpBinding.ivBackOtp.setOnClickListener(this);
        activityOtpBinding.btnVerifyAccount.setOnClickListener(this);
        activityOtpBinding.tvResendOtp.setOnClickListener(this);

        DEVICEID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        activityOtpBinding.tvUserMobile.setText(getResources().getString(R.string.we_sent_an_sms_with_a_4_digit_code_to_919876543210)+" "+sessionManager.getcontact());

        setOtpEditTextHandler();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_back){
            onBackPressed();
        }else if (view.getId() == R.id.ivBackOtp) {
            onBackPressed();
        }else if (view.getId() == R.id.btn_verify_account){

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
                new OTPAs2().execute();
            }

        }else if (view.getId() == R.id.tvEditNumber){
            onBackPressed();
        }else if (view.getId() == R.id.tvResendOtp){
            new ResendAs().execute();
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
            hashMap.put("otp",getOtp);
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
                sessionManager.setOtp(getOtp);
                sessionManager.setIsAgent(is_agent);
                ClassModel fruitModel = new ClassModel();
                fruitModel.setUserid(s_user_id);

                list.add(fruitModel);

//                System.out.print("User ID >>>>>>>>>>>>>>>>>>  "+s_user_id);

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
                    Intent in = new Intent(LoginOtpActivity.this,HomeActivity.class);
                    in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(in);
                    finish();
                }else {
                    CommonFunction.showToastSingle(LoginOtpActivity.this, motp);
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
            HashMap<String, String> hashMap = new HashMap<>();
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
            Toast.makeText(LoginOtpActivity.this, message+" to "+sessionManager.getcontact(), Toast.LENGTH_SHORT).show();
        }
    }
}
