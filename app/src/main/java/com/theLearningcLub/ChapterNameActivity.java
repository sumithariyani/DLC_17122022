package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.theLearningcLub.Model_Class.Purachase_package_free_video_Model;
import com.theLearningcLub.adapter.VideoListAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityChapterNameBinding;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class ChapterNameActivity extends BaseFragment {

    ActivityChapterNameBinding activityChapterNameBinding;
    String s, status, s_id, s_desc, s_video_date, s_title, s_video, s_pack_id, s_image, s_pack_name, s_v_id, s_pack_type, buy_package = "1";
//    ArrayList<Purachase_package_video_Model> purachase_package_video_modelslist = new ArrayList<>();
    ArrayList<Purachase_package_free_video_Model> purachase_package_video_modelslist = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activityChapterNameBinding = ActivityChapterNameBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText("");
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);

        s = sessionManager.getSavedCART_NO();

        assert getArguments() != null;
        s_pack_id = getArguments().getString("pack_id");
        s_pack_name = getArguments().getString("pack_name");
        s_pack_type = getArguments().getString("pack_type");

        activityChapterNameBinding.tvPackagesName.setText(sessionManager.getPackname());

        if (s_pack_type.equals("new_package")){
            new view_download_list_asy1().execute();
        } else if (s_pack_type.equals("old_package")){
            new view_download_list_asy().execute();
        }

        return activityChapterNameBinding.getRoot();
    }

    class view_download_list_asy extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            purachase_package_video_modelslist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("pack_type", s_pack_type);
            hashMap.put("pack_id", s_pack_id);
            hashMap.put("buy_package", buy_package);
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());

            String response = webRequest.makeWebServiceCall(AllUrl.defaenc_dvidnApi, 2, hashMap);

            System.out.println(" View video list response >>>>>>>>>>>>>>>  " + response);
            System.out.println(" View video list parameter >>>>>>>>>>>>>>>  " + hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject c = jsonArray.getJSONObject(i);

                    JSONArray jsonArray1 = c.getJSONArray("vid_data");
                    for (int p = 0; p < jsonArray1.length(); p++) {
                        JSONObject jsonObject1 = jsonArray1.getJSONObject(p);

                        s_id = jsonObject1.getString("mar_id");
                        s_desc = jsonObject1.getString("description");
                        s_video_date = jsonObject1.getString("videos_date");
                        s_title = jsonObject1.getString("Title");
                        s_video = jsonObject1.getString("videncd");
                        s_image = jsonObject1.getString("image");
                        s_v_id = jsonObject1.getString("vid_id");
                        String videoviewtime = jsonObject1.getString("view_status");
                        String videotime = jsonObject1.getString("v_duration");

//                        Purachase_package_video_Model video_model = new Purachase_package_video_Model();
//                        video_model.setVideo_id(s_v_id);
//                        video_model.setVideo_title(s_title);
//                        video_model.setVideo(s_video);
//                        video_model.setVideo_desc(s_desc);
//                        video_model.setVideo_date(s_video_date);
//                        video_model.setVideo_image(s_image);
//                        video_model.setVideoview_Time(videoviewtime);
//                        video_model.setVideototal_duration(videotime);
//                        purachase_package_video_modelslist.add(video_model);


                        purachase_package_video_modelslist.add(new Purachase_package_free_video_Model(s_v_id,
                                s_title,s_desc,s_video,s_video_date,s_image,"1",videoviewtime,videotime));
//                        purachase_package_video_modelslist.add(new Purachase_package_video_Model(s_v_id,
//                                s_title,s_desc,s_video,s_video_date,s_image,videoviewtime,videotime));

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
            activityChapterNameBinding.rvChapterName.setAdapter(new VideoListAdapter(purachase_package_video_modelslist, mContext, new FilterClick() {
                @Override
                public void filterClick(int position) {
                    ArrayList<String> videolist = new ArrayList<>();
                    ArrayList<String> videotitle = new ArrayList<>();
                    for (int i = 0; i < purachase_package_video_modelslist.size(); i++) {
                        videolist.add(purachase_package_video_modelslist.get(i).getVideo());
                        videotitle.add(purachase_package_video_modelslist.get(i).getVideo_title());
                    }
                    int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;

                    Intent in = new Intent(mContext, VideoActivity.class);
                    in.putExtra("URI", purachase_package_video_modelslist.get(position).getVideo());
                    in.putExtra("VIDEO_TITLE", purachase_package_video_modelslist.get(position).getVideo_title());
                    in.putExtra("VIDEO_DESC", purachase_package_video_modelslist.get(position).getVideo_desc());
                    in.putExtra("s_pack_id", s_pack_id);
                    in.putExtra("s_pack_name", s_pack_name);
                    in.putExtra("s_pack_type", s_pack_type);
                    in.putExtra("videoType", "1");
                    in.putExtra("is_free", "1");
                    in.putExtra("change","1");
                    in.putExtra("VIDEO_ID", purachase_package_video_modelslist.get(position).getVideo_id());
                    in.putExtra("position", position);
                    in.putExtra("viewduration", viewvdideo);
                    in.putExtra("from", "0");
                    in.putStringArrayListExtra("videoArrayList", videolist);
                    in.putStringArrayListExtra("titleArrayList", videotitle);
                    Bundle bundle = new Bundle();
                    bundle.putParcelableArrayList("ARRAYLIST", purachase_package_video_modelslist);
                    in.putExtras(bundle);
//                    System.out.println("pack id >>>>>>>>>>>>>>>>>>>>>>>>>>  "+s_pack_id);
//                    System.out.println("vid id >>>>>>>>>>>>>>>>>>>>>>>>>>    "+purachase_package_video_modelslist.get(position).getVideo_id());
                    startActivity(in);
                }
            }));

        }
    }


    class view_download_list_asy1 extends AsyncTask<String, String, String> {

        String status, s_id, s_desc, s_video_date, s_title, s_video, s_image, s_v_id, is_free = "";

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            purachase_package_video_modelslist.clear();
        }

        @Override
        protected String doInBackground(String... strings) {


            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("pack_new_id", s_pack_id);
//            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("user_id", sessionManager.getSavedUserId());
//
            String response = webRequest.makeWebServiceCall(AllUrl.view_package_video_list, 2, hashMap);
//
            System.out.println(" View defaenc_dvidn mypack video response >>>>>>>>>>>>>>>  " + response);
            System.out.println(" View defaenc_dvidn mypack video parameter check >>>>>>>>>>>>>>>  " + hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray jsonArray = jsonObject.getJSONArray("body");

                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);


                    s_id = jsonObject1.getString("videos_id");
                    s_desc = jsonObject1.getString("video_description");
                    s_video_date = jsonObject1.getString("videos_date");
                    s_title = jsonObject1.getString("video_name");
                    s_video = jsonObject1.getString("videncd");
                    s_image = jsonObject1.getString("image");
                    s_v_id = jsonObject1.getString("videos_id");
                    if (jsonObject1.has("free_status")) {
                        is_free = jsonObject1.getString("free_status");
                    }
                    String videoviewtime;
                    String videotime;
                    if (jsonObject1.has("view_status")) {

                        videoviewtime = jsonObject1.getString("view_status");
                        videotime = jsonObject1.getString("v_duration");
                    } else {

                        videoviewtime = "0";
                        videotime = "0";
                    }


//                    Purachase_package_video_Model video_model = new Purachase_package_video_Model();
//                    video_model.setVideo_id(s_v_id);
//                    video_model.setVideo_title(s_title);
//                    video_model.setVideo(s_video);
//                    video_model.setVideo_desc(s_desc);
//                    video_model.setVideo_date(s_video_date);
//                    video_model.setVideo_image(s_image);
//                    video_model.setVideoview_Time(videoviewtime);
//                    video_model.setVideototal_duration(videotime);
//                    purachase_package_video_modelslist.add(video_model);

//                    purachase_package_video_modelslist.add(new Purachase_package_video_Model(s_v_id,
//                            s_title,s_desc,s_video,s_video_date,s_image,videoviewtime,videotime));

                    purachase_package_video_modelslist.add(new Purachase_package_free_video_Model(s_v_id,
                            s_title,s_desc,s_video,s_video_date,s_image,"1",videoviewtime,videotime));

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
            if (status.equals("true")) {

                activityChapterNameBinding.rvChapterName.setAdapter(new
                        VideoListAdapter(purachase_package_video_modelslist, mContext, new FilterClick() {
                    @Override
                    public void filterClick(int position) {
                        ArrayList<String> videolist = new ArrayList<>();
                        ArrayList<String> videotitle = new ArrayList<>();
                        for (int i = 0; i < purachase_package_video_modelslist.size(); i++) {
                            videolist.add(purachase_package_video_modelslist.get(i).getVideo());
                            videotitle.add(purachase_package_video_modelslist.get(i).getVideo_title());
                        }
                        int viewvdideo = Integer.parseInt(purachase_package_video_modelslist.get(position).getVideoview_Time().toString()) * 1000;

                        Intent in = new Intent(mContext, VideoActivity.class);
                        in.putExtra("URI", purachase_package_video_modelslist.get(position).getVideo());
                        in.putExtra("VIDEO_TITLE", purachase_package_video_modelslist.get(position).getVideo_title());
                        in.putExtra("VIDEO_DESC", purachase_package_video_modelslist.get(position).getVideo_desc());
                        in.putExtra("s_pack_id", s_pack_id);
                        in.putExtra("s_pack_name", s_pack_name);
                        in.putExtra("VIDEO_ID", purachase_package_video_modelslist.get(position).getVideo_id());
                        in.putExtra("is_free", "1");
                        in.putExtra("change","1");
                        in.putExtra("videoType", "1");
                        in.putExtra("from", "1");
                        in.putExtra("position", position);
                        in.putExtra("viewduration", viewvdideo);
                        in.putStringArrayListExtra("videoArrayList", videolist);
                        in.putStringArrayListExtra("titleArrayList", videotitle);
                        Bundle bundle = new Bundle();
                        bundle.putParcelableArrayList("ARRAYLIST", purachase_package_video_modelslist);
                        in.putExtras(bundle);

//                                Bundle bundle=new Bundle();
//                                bundle.putParcelableArrayList("videoArrayList",purachase_package_video_modelslist);
//                                in.putExtras(bundle);
//                                System.out.println("pack id >>>>>>>>>>>>>>>>>>>>>>>>>>  "+s_pack_id);
//                                System.out.println("vid id >>>>>>>>>>>>>>>>>>>>>>>>>>    "+purachase_package_video_modelslist.get(position).getVideo());
                        startActivity(in);
                    }


                }));

            } else {

            }
        }
    }


}