package com.theLearningcLub;

import androidx.appcompat.widget.AppCompatEditText;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class RateActivity extends BaseActivity {

    TextView btnSubmit;
    AppCompatEditText popup_edt_comments;
    RatingBar popup_ratingbar1;
    String s_rating="0",s_comments,status,response,message;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rate);

        popup_edt_comments = findViewById(R.id.popup_edt_comments);
        popup_ratingbar1 = findViewById(R.id.popup_ratingbar1);
        btnSubmit = findViewById(R.id.btnSubmit);
        ivBack = findViewById(R.id.ivBack);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                s_comments = popup_edt_comments.getText().toString();
                s_rating = String.valueOf(popup_ratingbar1.getRating());

                if(s_comments.isEmpty()){
                    popup_edt_comments.setError("Please Enter Comments.");
                } else {
                    new AddRatingTask().execute();
                }
            }
        });
    }

    class AddRatingTask extends AsyncTask<String,String,String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("pack_id",sessionManager.getSavedPackId2());
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("comment",s_comments);
            hashMap.put("rateStar",s_rating);
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.add_ratingApi,2,hashMap);

            System.out.println("Add Rating Reaponse >>>>>>>>>>"+response);
            System.out.println("Add Rating parameter >>>>>>>>>"+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                status = jsonObject1.getString("status");
                message = jsonObject1.getString("msg");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            CommonFunction.showToastSingle(mContext,message+"");
        }
    }
}