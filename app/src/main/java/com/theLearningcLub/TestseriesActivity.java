package com.theLearningcLub;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.theLearningcLub.Model_Class.Calendermodelclass;
import com.theLearningcLub.Model_Class.Testseriesmodel;
import com.theLearningcLub.adapter.Testseriesadapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestseriesActivity extends BaseActivity {
    List<Calendermodelclass> calendermodelclassList = new ArrayList<>();

    ArrayList<Testseriesmodel> movieList;
    RecyclerView recyclerView;
    Testseriesadapter mAdapter;

    ArrayList<String> queidarr = new ArrayList<>();
    String selectedqueid,answer_corrcte,s_data,status,s_user_id,s_question_id,s_answer_id,s_update_id;

    int totlano = 0;
    Button submitbtn,btn_back;
    ViewPager viewpager;
    String selected="1",user_status,response;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_testseries);

        s_user_id = sessionManager.getSavedUserId();
        viewpager = findViewById(R.id.viewpager);

        user_status="2";

        new UserStatusTask().execute();

        new GetChapterslectedvideoTask().execute();

        submitbtn = findViewById(R.id.submitbtn);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                if(selected.equals(sessionManager.getSavedNotAnswer())) {
                    System.out.println("*************************    "+selected);
                    sessionManager.setSavedNotAnswer("2");
                    int current = getItem(+1);

                    if (current == queidarr.size()-1) {
                        // last page. make button text to GOT IT
                        submitbtn.setText(getString(R.string.submit));
                    } else {
                        // still pages are left
                        submitbtn.setText(getResources().getString(R.string.next));
                    }

                    if(current==queidarr.size()) {

                        Intent in = new Intent(TestseriesActivity.this, ResultActivity.class);
                        startActivity(in);
                        finish();

                    }
                    if (current < queidarr.size()) {
                        // move to next screen
                        viewpager.setCurrentItem(current);
                    }
                    new AddToAnsTask().execute();
                }
                else {
                    CommonFunction.showToastSingle(TestseriesActivity.this, "Please Select any one option.");
                }
            }
        });


    }
    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_MAIN);
                        intent.addCategory(Intent.CATEGORY_HOME);
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                        finish();

                    }
                }).setNegativeButton("No", null).show();
    }
    private int getItem(int i) {
        return viewpager.getCurrentItem() + i;
    }
    //---------------------------------------------------------------------------------------------
    //Viewpager Adapter

    public class ImagePagerAdapter extends PagerAdapter {

        Context context;
        LayoutInflater layoutInflater;

        List<Calendermodelclass> arrayList;

        public ImagePagerAdapter(Context context, List<Calendermodelclass> arrayList) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == (object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = layoutInflater.inflate(R.layout.quize_question_layout, container, false);

            TextView tvQusLayout = itemView.findViewById(R.id.tvQusLayout);
            recyclerView = itemView.findViewById(R.id.Viewlist_recycler);

            try {

                int pos = position + 1;
                ArrayList<Testseriesmodel> data = calendermodelclassList.get(position).getsList();
                mAdapter = new Testseriesadapter(context, data);
                tvQusLayout.setText("Q - " + calendermodelclassList.get(position).getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }

            @SuppressLint("WrongConstant") RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(mLayoutManager);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setAdapter(mAdapter);
            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }

    }


    private class GetChapterslectedvideoTask extends AsyncTask<String, String, String> {
        String video_id, video_image, video_title,answer_id,answer_correct;


        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response = webRequest.makeWebServiceCall(AllUrl.introduction_quizApi, 2,hashMap);

            System.out.println("introduction_quiz  response  >>>>>>>>>>>>>>>>>>>>>>>   " + response);
            System.out.println("introduction_quiz   parameter>>>>>>>>>>>>>>>>>>>>>>>   " + hashMap);


            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    status = jsonObj.getString("status");

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");

                    totlano = contacts.length() - 1;
                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        video_id = c.getString("id");
                        video_image = c.getString("question");
                        // video_title = c.getString("title");
                        queidarr.add(video_id);



                        JSONArray jsonArray = c.getJSONArray("answer");
                        movieList = new ArrayList<>();
                        for (int j = 0; j < jsonArray.length(); j++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(j);

                            String buttonoption = jsonObject.getString("answer");
                            String answer_correct = jsonObject.getString("answer_correct");
                            String answer_id = jsonObject.getString("answer_id");
                            System.out.println("answer_correct >>>>>>>>>>>>>>>>"+answer_correct);

                            System.out.println("jsonbjectresponse>>>>>>>>>>>>>>>>>>>>>>> " + buttonoption);


                            Testseriesmodel movie = new Testseriesmodel(buttonoption, answer_correct, answer_id,video_id);
                            movieList.add(movie);

                        }

                        Calendermodelclass calendermodelclass = new Calendermodelclass(video_image, "", movieList);

                        calendermodelclassList.add(calendermodelclass);

                    }
                } catch (final JSONException e) {
                    e.printStackTrace();

                }
            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            hideProgressDialog();
            if(status.equals("success")) {
                s_answer_id = answer_id;
                s_question_id=video_id;
                s_data="1";
                answer_corrcte = answer_correct;
            }
            try {


                viewpager.setAdapter(new ImagePagerAdapter(getApplicationContext(), calendermodelclassList));
                // mAdapter.notifyDataSetChanged();
                viewpager.getAdapter().notifyDataSetChanged();

                viewpager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                    }

                    @Override
                    public void onPageSelected(int position) {


                        if (position == 0) {
                            try {
                                selectedqueid = queidarr.get(position);
                                System.out.println("position>>case 0>>>>>>>>      " + position);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                selectedqueid = queidarr.get(position);
                                System.out.println("selectedqueid pos>>>>>>>>>>      " + queidarr.get(position));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }


                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private class AddToAnsTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            s_data = sessionManager.getSavedDATA();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {

                System.out.println("selectedquei*************          "+selectedqueid);

            }catch (Exception e){e.printStackTrace();}

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("user_id", sessionManager.getSavedUserId());
            parameter.put("question_id", sessionManager.getQID());
            parameter.put("answer_id", sessionManager.getSavedANSWER_ID());
            parameter.put("correct_id",sessionManager.getSavedCORRECT_ID());
            parameter.put("status", sessionManager.getSavedSTATUS());
            parameter.put("key", sessionManager.getLoginkey());

            String response = webRequest.makeWebServiceCall(AllUrl.introduction_answergivenApi, 2, parameter);

            System.out.println("Add to Ans Task response >>>>>>>>>>>>TestseriesActivity>>>>>>>>>>> " + response);
            System.out.println("Add to Ans Task parameter >>>>>>>>>>TestseriesActivity>>>>>>>>>>>>> " + parameter);


            if (response != null) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    s_update_id = jsonObject.getString("update_id");

                    sessionManager.setSavedUPDATE_ID(s_update_id);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }


            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            hideProgressDialog();

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
}
