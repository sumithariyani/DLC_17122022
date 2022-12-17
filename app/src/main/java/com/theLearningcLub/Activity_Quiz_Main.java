package com.theLearningcLub;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.ViewPager2;

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

public class Activity_Quiz_Main extends BaseActivity {

    List<Calendermodelclass> calendermodelclassList = new ArrayList<>();

    ArrayList<Testseriesmodel> movieList;
    RecyclerView recyclerView;
    Testseriesadapter mAdapter;

    ArrayList<String> queidarr = new ArrayList<>();
    String response, s_status_ans, selectedqueid, answer_corrcte, s_data, status,
            s_question_id, s_answer_id, s_pack_id, s_pack_name, s_update_id;
    String video_id, video_image, answer_id, answer_correct = "0", buttonoption;

    AppCompatImageView submitbtn, btn_back, ivBack;
    TextView tvSkip;

    ViewPager2 viewpager;
    TextView textview_quiz_title;
    int a1 = 0;
    String selected = "1", user_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
//        getSupportActionBar().hide();
        setContentView(R.layout.activity_quiz_main);

        viewpager = findViewById(R.id.viewpager);
        tvSkip = findViewById(R.id.tvSkip);
        textview_quiz_title = findViewById(R.id.textview_quiz_title);
        ivBack = findViewById(R.id.ivBack);

        s_pack_id = getIntent().getStringExtra("pack_id");
        s_pack_id = sessionManager.getSavedPackId2();
        System.out.println("Set Pack ID>>>>> " + s_pack_id);
        s_pack_name = getIntent().getStringExtra("pack_name");

        user_status = "5";

        new UserStatusTask().execute();

        if (sessionManager.getKEY_moretest().contains("1")) {
            textview_quiz_title.setText(sessionManager.getPackname2());
        } else {
            textview_quiz_title.setText(sessionManager.getPackname());
        }

        new GetChapterslectedvideoTask().execute();
        sessionManager.setSavedNotAnswer("2");
        submitbtn = findViewById(R.id.submitbtn);
        btn_back = findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (viewpager.getCurrentItem() != 0) {
                    viewpager.setCurrentItem(viewpager.getCurrentItem() - 1, false);
                    sessionManager.setSavedNotAnswer("1");
                } else {
                    System.out.println("else--vp----back---    ");
                    System.out.println("vp page value  " + viewpager.getCurrentItem());
                    finish();
                }
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                quizCloseBox();
            }
        });

        tvSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sessionManager.setSavedSTATUS("2");
                int current = getItem(+1);
                if (current == queidarr.size() - 1) {

                }
                if (current == queidarr.size()) {
                    Intent in = new Intent(Activity_Quiz_Main.this, QuizResultActivity.class);
                    startActivity(in);
                    finish();
                }
                if (current < queidarr.size()) {

                }
                new AddToAnsTask().execute();

            }
        });

        System.out.println("selected >>>>>>>>>>>>>    " + selected);
        System.out.println("Not Answer >>>>>>>>>>>>>     " + sessionManager.getSavedNotAnswer());

        submitbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (selected.equals(sessionManager.getSavedNotAnswer())) {
                    sessionManager.setSavedNotAnswer("2");
                    int current = getItem(+1);
                    if (current == queidarr.size() - 1) {

                    } else {

                    }

                    if (current == queidarr.size()) {
                        Intent in = new Intent(Activity_Quiz_Main.this, QuizResultActivity.class);
                        startActivity(in);
                        finish();
                    }
                    if (current < queidarr.size()) {

                    }
                    new AddToAnsTask().execute();
                } else {
                    CommonFunction.showToastSingle(Activity_Quiz_Main.this, "Please Select any one option.");
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        quizCloseBox();
//        if (viewpager.getCurrentItem() != 0) {
//            viewpager.setCurrentItem(viewpager.getCurrentItem() - 1,false);
//            sessionManager.setSavedNotAnswer("1");
//        }else{
//            System.out.println("else--vp----back---    ");
//            System.out.println("vp page value  "+viewpager.getCurrentItem());
//            finish();
//        }
    }

    private int getItem(int i) {
        return viewpager.getCurrentItem() + i;
    }

    //---------------------------------------------------------------------------------------------
    //Viewpager Adapter

//    public class ImagePagerAdapter extends PagerAdapter {
//
//        Context context;
//        LayoutInflater layoutInflater;
//        List<Calendermodelclass> arrayList;
//
//        public ImagePagerAdapter(Context context, List<Calendermodelclass> arrayList) {
//            this.context = context;
//            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//            this.arrayList = arrayList;
//        }
//
//        @Override
//        public int getCount() {
//            if (arrayList != null) {
//                return arrayList.size();
//            }
//            return 0;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == (object);
//        }
//
//        @Override
//        public Object instantiateItem(ViewGroup container, int position) {
//            View itemView = layoutInflater.inflate(R.layout.quize_question_layout, container, false);
//
//            TextView tvQusLayout = itemView.findViewById(R.id.tvQusLayout);
//            recyclerView = itemView.findViewById(R.id.Viewlist_recycler);
//
//            try {
//                int pos = position + 1;
//                ArrayList<Testseriesmodel> data = calendermodelclassList.get(position).getsList();
//                mAdapter = new Testseriesadapter(context, data);
//                tvQusLayout.setText("Q."+pos+" - " + calendermodelclassList.get(position).getTitle());
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            @SuppressLint("WrongConstant") RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
//            recyclerView.setLayoutManager(mLayoutManager);
//            recyclerView.setItemAnimator(new DefaultItemAnimator());
//            recyclerView.setAdapter(mAdapter);
//
//            container.addView(itemView);
//
//            return itemView;
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((LinearLayout) object);
//        }
//
//    }


    class ImagePagerAdapter extends RecyclerView.Adapter<ImagePagerAdapter.ViewHolder> {

        Context context;
        LayoutInflater layoutInflater;
        List<Calendermodelclass> arrayList;

        public ImagePagerAdapter(Context context, List<Calendermodelclass> arrayList) {
            this.context = context;
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            this.arrayList = arrayList;
        }

        // This method returns our layout

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.quize_question_layout, parent, false);
            return new ViewHolder(view);
        }

        // This method binds the screen with the view
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            try {
                int pos = position + 1;
                ArrayList<Testseriesmodel> data = calendermodelclassList.get(position).getsList();
                mAdapter = new Testseriesadapter(context, data);
                holder.tvQusLayout.setText("Q." + pos + " - " + calendermodelclassList.get(position).getTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }

            @SuppressLint("WrongConstant") RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            holder.recyclerView.setLayoutManager(mLayoutManager);
            holder.recyclerView.setItemAnimator(new DefaultItemAnimator());
            holder.recyclerView.setAdapter(mAdapter);
        }

        // This Method returns the size of the Array
        @Override
        public int getItemCount() {
            if (arrayList != null) {
                return arrayList.size();
            }
            return 0;
        }

        // The ViewHolder class holds the view
        public class ViewHolder extends RecyclerView.ViewHolder {
            RecyclerView recyclerView;
            TextView tvQusLayout;

            public ViewHolder(View itemView) {
                super(itemView);
                tvQusLayout = itemView.findViewById(R.id.tvQusLayout);
                recyclerView = itemView.findViewById(R.id.Viewlist_recycler);
            }
        }
    }

    private class GetChapterslectedvideoTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("packs_id", sessionManager.getSavedPackId2());
            parameter.put("key", sessionManager.getLoginkey());
            parameter.put("user_id", sessionManager.getSavedUserId());

            response = webRequest.makeWebServiceCall(AllUrl.open_quiz_questionsApi, 2, parameter);

            System.out.println("Quiz Pack Response >>>>>>>>>>>>>>>>>>>>>>> " + response);
            System.out.println("Quiz Pack Parameter >>>>>>>>>>>>>>>>>>>>>>> " + parameter);

            if (response != null) {
                try {
                    JSONObject jsonObj = new JSONObject(response);
                    status = jsonObj.getString("status");

                    // Getting JSON Array node
                    JSONArray contacts = jsonObj.getJSONArray("data");

                    // looping through All Contacts
                    for (int i = 0; i < contacts.length(); i++) {
                        JSONObject c = contacts.getJSONObject(i);

                        video_id = c.getString("id");
                        video_image = c.getString("question");
                        queidarr.add(video_id);

                        JSONArray jsonArray = c.getJSONArray("answer");
                        movieList = new ArrayList<>();
                        for (int j = 0; j < jsonArray.length(); j++) {

                            JSONObject jsonObject = jsonArray.getJSONObject(j);

                            buttonoption = jsonObject.getString("answer");
                            answer_correct = jsonObject.getString("answer_correct");
                            answer_id = jsonObject.getString("answer_id");


                            Testseriesmodel movie = new Testseriesmodel(buttonoption, answer_correct, answer_id, video_id);
                            movie.setGenre(answer_correct);
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
            try {

                viewpager.setOnTouchListener(null);

                viewpager.setOnTouchListener(new View.OnTouchListener() {
                    @Override
                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                viewpager.setUserInputEnabled(false);
                viewpager.setAdapter(new ImagePagerAdapter(getApplicationContext(), calendermodelclassList));
                viewpager.getAdapter().notifyDataSetChanged();
                viewpager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                        super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                    }

                    @Override
                    public void onPageSelected(int position) {
                        if (position == 0) {
                            try {
                                System.out.println("position>>>>>>>>>>  " + position);

                                selectedqueid = queidarr.get(position);
//                                    new AddTestTask().execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            try {
                                System.out.println("position>>>>>>>>>>" + position);
                                System.out.println("click>>>>*** >>>>>> " + position);
                                selectedqueid = queidarr.get(position);
//                                    new AddTestTask().execute();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        super.onPageSelected(position);
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                        super.onPageScrollStateChanged(state);
                    }
                });

//                final ViewPager.OnPageChangeListener pageChangeListener = new ViewPager.OnPageChangeListener() {
//
//                    @Override
//                    public void onPageScrollStateChanged(int arg0) { }
//
//                    @Override
//                    public void onPageScrolled(int arg0, float arg1, int arg2) { }
//
//                    @Override
//                    public void onPageSelected(int position) {
//
//                        if (position == 0) {
//                            try {
//                                System.out.println("position>>>>>>>>>>  " + position);
//
//                                selectedqueid = queidarr.get(position);
////                                    new AddTestTask().execute();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        } else {
//                            try {
//                                System.out.println("position>>>>>>>>>>" + position);
//                                System.out.println("click>>>>*** >>>>>> " + position);
//                                selectedqueid = queidarr.get(position);
////                                    new AddTestTask().execute();
//                            } catch (Exception e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    }
//                };
//
//                viewpager.setOnPageChangeListener(pageChangeListener);
//                // do this in a runnable to make sure the viewPager's views are already instantiated before triggering the onPageSelected call
//                viewpager.post(new Runnable() {
//                    @Override
//                    public void run() {
//                        pageChangeListener .onPageSelected(viewpager.getCurrentItem());
//                    }
//                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class AddToAnsTask extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            s_data = sessionManager.getSavedDATA();

            Testseriesmodel testseriesmodel = new Testseriesmodel();
            String s1 = testseriesmodel.getGenre();
            if (sessionManager.getSavedANSWER_ID().equals(sessionManager.getSavedCORRECT_ID())) {
                s_status_ans = "1";
            } else {
                s_status_ans = "0";
            }
            s_answer_id = answer_id;
            s_question_id = video_id;
            s_data = "1";

            answer_corrcte = answer_correct;

            showProgressDialog();

        }

        @Override
        protected String doInBackground(String... strings) {
            System.out.println("queidarr >>>>>>>>>>>>>>>>>" + queidarr.size());
            System.out.println("selectedqueid >>>>>>>>>>>>>>>>>" + selectedqueid);
            System.out.println("a1 >>>>>>>>>>>>>>>>>" + a1);

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> parameter = new HashMap<>();
            parameter.put("user_id", sessionManager.getSavedUserId());
            parameter.put("question_id", selectedqueid);
            System.out.println("Add to Ans Task a1 >> >>   " + selectedqueid);
            parameter.put("answer_id", sessionManager.getSavedANSWER_ID());
            parameter.put("correct_id", sessionManager.getSavedCORRECT_ID());
            parameter.put("pack_id", s_pack_id);
            parameter.put("status", sessionManager.getSavedSTATUS());
            parameter.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.open_quiz_given_answernewApi, 2, parameter);

            System.out.println("Add to Ans Task response >>>>>>>>>>>>>>>>>>>>>>> activityquizmain  " + response);
            System.out.println("Add to Ans Task parameter >>>>>>>>>>>>>>>>>>>>>>>activityquizmain  " + parameter);

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
            viewpager.setCurrentItem(viewpager.getCurrentItem() + 1, true);
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
            // hashMap.put("contact",allcontact);
            response = webRequest.makeWebServiceCall(AllUrl.update_user_statusApi, 2, hashMap);

            System.out.println("user status Response >>>>>>>>>>>>>>" + response);
            System.out.println("user status parameter >>>>>>>>>>>>>>" + hashMap);

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }

    public void quizCloseBox() {
        //will create a view of our custom dialog layout
        View alertCustomdialog = LayoutInflater.from(mContext).inflate(R.layout.dialog_box, null);
        //initialize alert builder.
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);
        AppCompatButton btnYes = alertCustomdialog.findViewById(R.id.btnYes);
        AppCompatButton btnNo = alertCustomdialog.findViewById(R.id.btnNo);
        TextView tvMsg = alertCustomdialog.findViewById(R.id.tvMsg);
        tvMsg.setText(mContext.getResources().getString(R.string.quiz_massage));

        final android.app.AlertDialog dialog = alert.create();
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                dialog.dismiss();
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }
}
