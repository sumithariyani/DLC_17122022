package com.theLearningcLub;

import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.theLearningcLub.model.schoollist.SchoolList;
import com.theLearningcLub.model.state.StateModel;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityYourNameBinding;
import com.theLearningcLub.model.city.CityModel;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class YourNameActivity extends BaseActivity implements View.OnClickListener{

    ActivityYourNameBinding activityYourNameBinding;
    String yourName,userEmail,mobileNumber;
    String DEVICEID,status;
    String[] classarr=new String[] {"Select Class","Class 1", "Class 2", "Class 3","Class 4","Class 5","Class 6",
            "Class 7","Class 8","Class 9","Class 10","Class 11","Class 12"}; //inline initialization
String[] classarr_id=new String[] {"0","1", "2", "3","4","5","6",
            "7","8","9","10","11","12"}; //inline initialization

    boolean isload=true;
    String s_state,s_state_id,s_district,s_district_Id,s_school,s_class,s_school_Id;
    ArrayList<String> statearr = new ArrayList<>();
    ArrayList<String> stateIDarr = new ArrayList<>();
    ArrayList<String> districtarr = new ArrayList<>();
    ArrayList<String> districtIDarr = new ArrayList<>();
    ArrayList<String> schoolarr = new ArrayList<>();
    ArrayList<String> schoolIDarr = new ArrayList<>();
    String schoolOthers="school";
    String message,verified_status,userid,s_login_key,is_agent;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        activityYourNameBinding = DataBindingUtil.setContentView(mContext,R.layout.activity_your_name);

        activityYourNameBinding.btnNextName.setOnClickListener(this);
        activityYourNameBinding.btnNextEmail.setOnClickListener(this);
        activityYourNameBinding.btnNextMobile.setOnClickListener(this);
        activityYourNameBinding.btnContinueLocation.setOnClickListener(this);
        activityYourNameBinding.tvLoginName.setOnClickListener(this);
        activityYourNameBinding.tvLoginEmail.setOnClickListener(this);
        activityYourNameBinding.tvLoginMobile.setOnClickListener(this);
        activityYourNameBinding.tvLoginLocation.setOnClickListener(this);
        activityYourNameBinding.ivBackEmail.setOnClickListener(this);
        activityYourNameBinding.ivBackMobile.setOnClickListener(this);
        activityYourNameBinding.ivBackLocation.setOnClickListener(this);


        DEVICEID = Settings.Secure.getString(getContentResolver(),Settings.Secure.ANDROID_ID);

        stateApi();
        schoolApi();

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnNextName){

            CommonFunction.hideKeyboardFrom(mContext,view);

            yourName = Objects.requireNonNull(activityYourNameBinding.etYourName.getText()).toString().trim();

            if (TextUtils.isEmpty(yourName)){
                activityYourNameBinding.etYourName.setError("Please enter your name");
            }else {
                activityYourNameBinding.llYourEmail.setVisibility(View.VISIBLE);
                activityYourNameBinding.llYourName.setVisibility(View.GONE);
            }

        }else if (view.getId() == R.id.tvLoginName){
            startActivity(new Intent(YourNameActivity.this,LoginActivity.class));
        }else if (view.getId() == R.id.tvLoginEmail){
            startActivity(new Intent(YourNameActivity.this,LoginActivity.class));
        }else if (view.getId() == R.id.ivBackEmail){
            activityYourNameBinding.llYourEmail.setVisibility(View.GONE);
            activityYourNameBinding.llYourName.setVisibility(View.VISIBLE);
        }else if (view.getId() == R.id.tvLoginMobile){
            startActivity(new Intent(YourNameActivity.this,LoginActivity.class));
        }else if (view.getId() == R.id.ivBackMobile){
            activityYourNameBinding.llYourEmail.setVisibility(View.VISIBLE);
            activityYourNameBinding.llMobileNumber.setVisibility(View.GONE);
        }else if (view.getId() == R.id.tvLoginLocation){
            startActivity(new Intent(YourNameActivity.this,LoginActivity.class));
        }else if (view.getId() == R.id.ivBackLocation){
            activityYourNameBinding.llMobileNumber.setVisibility(View.VISIBLE);
            activityYourNameBinding.llSelectLocation.setVisibility(View.GONE);
        }else if (view.getId() == R.id.btnNextEmail){
            CommonFunction.hideKeyboardFrom(mContext,view);

            userEmail = Objects.requireNonNull(activityYourNameBinding.etYourEmail.getText()).toString().trim();

            if (TextUtils.isEmpty(userEmail) || CommonFunction.emailValidator(userEmail)){
                activityYourNameBinding.etYourEmail.setError("Please enter valid email");
            }else {
                activityYourNameBinding.llYourEmail.setVisibility(View.GONE);
                activityYourNameBinding.llMobileNumber.setVisibility(View.VISIBLE);
            }

        }else if (view.getId() == R.id.btnNextMobile){
            CommonFunction.hideKeyboardFrom(mContext,view);

            mobileNumber = Objects.requireNonNull(activityYourNameBinding.etMobileNumber.getText()).toString().trim();

            if (TextUtils.isEmpty(mobileNumber)){
                activityYourNameBinding.etMobileNumber.setError("Please enter mobile number");
            }else if (mobileNumber.length() < 10){
                activityYourNameBinding.etMobileNumber.setError("Please enter valid mobile number");
            }else {
                activityYourNameBinding.llMobileNumber.setVisibility(View.GONE);
                activityYourNameBinding.llSelectLocation.setVisibility(View.VISIBLE);
            }

        }else if (view.getId() == R.id.btnContinueLocation){
            CommonFunction.hideKeyboardFrom(mContext,view);

            if (s_state.equals("Select state")) {
                Toast.makeText(mContext, "Please select State", Toast.LENGTH_SHORT).show();
            } else if (s_district.equals("Select City")) {
                Toast.makeText(mContext, "Please select city", Toast.LENGTH_SHORT).show();
            } else{
                if(isload){
                    isload=false;
                    if (schoolOthers.equals("school")) {
                        if(s_school.equals("Select School")){
                            Toast.makeText(mContext, "Please select School", Toast.LENGTH_SHORT).show();
                        }
                        else if(s_class.equals("Select Class")){
                            Toast.makeText(mContext, "Please select Class", Toast.LENGTH_SHORT).show();
                        }
                        else{
                            signUpApi();
                        }
                    }else{
                        signUpApi();
                    }
                }

            }

        }
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id._rbschool:
                if (checked)
                    activityYourNameBinding.schoolLg.setVisibility(View.VISIBLE);
                schoolOthers="school";
                    // Pirates are the best
                    break;
            case R.id._rbother:
                if (checked)
                    schoolOthers="others";
                activityYourNameBinding.schoolLg.setVisibility(View.GONE);
                    // Ninjas rule
                    break;
        }
    }

    private void signUpApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.defaencsignupApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("signUpApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject obj1 = new JSONObject(response);
                            JSONObject object = obj1.getJSONObject("response");
                            status = object.getString("status");
                            String messageError = object.getString("msg");

                            if (status.equals("true")){
                                JSONObject jsonObject = new JSONObject(response);
                                JSONObject jsonObject1 = jsonObject.getJSONObject("response");

                                status = jsonObject1.getString("status");
                                message = jsonObject1.getString("msg");
                                userid = jsonObject1.getString("user_id");
                                verified_status = jsonObject1.getString("verified_status");
                                s_login_key = jsonObject1.getString("login");
                                is_agent = jsonObject1.getString("is_agent");

                                if (status.equals("true")){
                                    sessionManager.setSavedUserId(userid);
                                    sessionManager.setLoginkey(s_login_key);
                                    sessionManager.setVerifystatus(verified_status);
                                    sessionManager.setIsAgent(is_agent);
                                    isload=true;
                                    if (verified_status.contains("0")) {
                                        Intent in = new Intent(mContext,OtpActivity.class);
                                        in.putExtra("number", mobileNumber);
                                        startActivity(in);
                                    }else {
                                        sessionManager.setSavedUserId(userid);
                                        sessionManager.setUserLoggedIn(true);

                                        sessionManager.setnokey("0");
                                        Intent in = new Intent(mContext,HomeActivity.class);
                                        in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                        startActivity(in);
                                    }
                                    finish();
                                    CommonFunction.showToastSingle(mContext,message);
                                }
                            }else {
                                isload=true;
                                CommonFunction.showToastSingle(mContext,messageError);
                                if (messageError.equals("Email already registered!")){
                                    activityYourNameBinding.llSelectLocation.setVisibility(View.GONE);
                                    activityYourNameBinding.llYourEmail.setVisibility(View.VISIBLE);
                                }
                                if (messageError.equals("Contact no already registered!")){
                                    activityYourNameBinding.llSelectLocation.setVisibility(View.GONE);
                                    activityYourNameBinding.llMobileNumber.setVisibility(View.VISIBLE);
                                }

                            }

                            hideProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("signUpApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("username",yourName);
                    params.put("lastname","test");
                    params.put("email",userEmail);
                    params.put("password","12345678");
                    params.put("contactno",mobileNumber);
                    params.put("device_id",DEVICEID);
                    params.put("state",s_state_id);
                    params.put("district",s_district_Id);
                    params.put("ref_by",sessionManager.getReferCode());
                    if(schoolOthers.equals("school")){
                        params.put("school_id",s_school_Id);
                        params.put("class",s_class);
                    }else if(schoolOthers.equals("others")){
                        params.put("school_id","0");
                    }
                    System.out.println("signUpApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(YourNameActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

    private void schoolApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.GET, AllUrl.School_list,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("School response>>>>>>>>>>>>>>>" + response);
                        try {
                            SchoolList schoolList = new Gson().fromJson(response,SchoolList.class);
                            if (schoolList.getStatus()){
                                schoolarr.add("Select School");
                                schoolIDarr.add("");
                                for (int i = 0; i < schoolList.getBody().size(); i++) {
                                    schoolarr.add(schoolList.getBody().get(i).getName());
                                    schoolIDarr.add(schoolList.getBody().get(i).getSchoolId());
                                }
                                schoolIDarr.add("0");
                                schoolarr.add("None");
                            }
                            ArrayAdapter<String> stateArr = new ArrayAdapter<>(mContext,R.layout.register_profile_layout,schoolarr);
                            stateArr.setDropDownViewResource(R.layout.profile_layout);
                            activityYourNameBinding.spSchool.setAdapter(stateArr);

                            activityYourNameBinding.spSchool.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    Toast.makeText(getApplicationContext(), statearr.get(i), Toast.LENGTH_LONG).show();
                                    s_school = schoolarr.get(i);
                                    s_school_Id = schoolIDarr.get(i);
                                    if(!s_school.equals("Select School")){


                                        ArrayAdapter<String> stateArr = new ArrayAdapter<>(mContext,R.layout.register_profile_layout,classarr);
                                        stateArr.setDropDownViewResource(R.layout.profile_layout);
                                        activityYourNameBinding.spClass.setAdapter(stateArr);
                                        activityYourNameBinding.classLg.setVisibility(View.VISIBLE);

                                        activityYourNameBinding.spClass.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                            @Override
                                            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    Toast.makeText(getApplicationContext(), statearr.get(i), Toast.LENGTH_LONG).show();
                                                s_class = classarr_id[i];
                                            }

                                            @Override
                                            public void onNothingSelected(AdapterView<?> adapterView) {

                                            }
                                        });

                                    }else{
                                        activityYourNameBinding.classLg.setVisibility(View.GONE);
                                    }

//                                    if (i == 0){
//                                        schoolarr.add("Select City");
//                                        ArrayAdapter<String> cityArr = new ArrayAdapter<>(mContext,R.layout.register_profile_layout,districtarr);
//                                        cityArr.setDropDownViewResource(R.layout.profile_layout);
//                                        activityYourNameBinding.spCity.setAdapter(cityArr);
//                                    }else {
//                                        cityApi();
//                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            hideProgressDialog();

                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                        System.out.println("stateApi error>>>>>>>>>>>>>."+error);
                        hideProgressDialog();
                    }) {
//                @Override
//                protected Map<String, String> getParams() {
//                    Map<String, String> params = new HashMap<>();
//                    params.put("flag","state");
//                    System.out.println("stateApi params>>>>>>>>>>>>>>" + params);
//                    return params;
//                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(YourNameActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }
    private void stateApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.state_district_view_Api,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("stateApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            StateModel stateModel = new Gson().fromJson(response,StateModel.class);
                            if (stateModel.getStatus()){
                                statearr.add("Select state");
                                stateIDarr.add("");
                                for (int i = 0; i < stateModel.getBody().size(); i++) {
                                    statearr.add(stateModel.getBody().get(i).getStateName());
                                    stateIDarr.add(stateModel.getBody().get(i).getStateId());
                                }
                            }
                            ArrayAdapter<String> stateArr = new ArrayAdapter<>(mContext,R.layout.register_profile_layout,statearr);
                            stateArr.setDropDownViewResource(R.layout.profile_layout);
                            activityYourNameBinding.spState.setAdapter(stateArr);

                            activityYourNameBinding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                                    Toast.makeText(getApplicationContext(), statearr.get(i), Toast.LENGTH_LONG).show();
                                    s_state = statearr.get(i);
                                    s_state_id = stateIDarr.get(i);

                                    if (i == 0){
                                        districtarr.add("Select City");
                                        ArrayAdapter<String> cityArr = new ArrayAdapter<>(mContext,R.layout.register_profile_layout,districtarr);
                                        cityArr.setDropDownViewResource(R.layout.profile_layout);
                                        activityYourNameBinding.spCity.setAdapter(cityArr);
                                    }else {
                                        cityApi();
                                    }
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            hideProgressDialog();

                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                        System.out.println("stateApi error>>>>>>>>>>>>>."+error);
                        hideProgressDialog();
                    }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("flag","state");
                    System.out.println("stateApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(YourNameActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

    private void cityApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            districtarr.clear();
            districtIDarr.clear();
            RequestQueue queue = Volley.newRequestQueue(this);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.state_district_view_Api,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("cityApi response>>>>>>>>>>>>>>>" + response);
                        try {

                            CityModel cityModel = new Gson().fromJson(response,CityModel.class);
                            if (cityModel.getStatus()){
                                districtarr.add("Select city");
                                districtIDarr.add("");
                                for (int i = 0; i < cityModel.getBody().size(); i++) {
                                    districtarr.add(cityModel.getBody().get(i).getDistName());
                                    districtIDarr.add(cityModel.getBody().get(i).getDistId());
                                }
                            }

                            ArrayAdapter<String> cityArr = new ArrayAdapter<>(mContext,R.layout.register_profile_layout,districtarr);
                            cityArr.setDropDownViewResource(R.layout.profile_layout);
                            activityYourNameBinding.spCity.setAdapter(cityArr);

                            activityYourNameBinding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                                    s_district = districtarr.get(i);
                                    s_district_Id = districtIDarr.get(i);

                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> adapterView) {

                                }
                            });

                            hideProgressDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("cityApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("flag","district");
                    params.put("state",s_state_id);
                    System.out.println("cityApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(YourNameActivity.this, NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }
}