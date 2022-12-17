package com.theLearningcLub.fragment;

import static com.google.firebase.messaging.Constants.TAG;
import static com.theLearningcLub.HomeActivity.bottomSheetDashboard;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.theLearningcLub.ChapterNameActivity;
import com.theLearningcLub.HomeActivity;
import com.theLearningcLub.Model_Class.Purachase_package_Model;
import com.theLearningcLub.R;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityMyPackagesBinding;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MyPackagesActivity extends BaseFragment {

    ActivityMyPackagesBinding activityMyPackagesBinding;
    ArrayList<Purachase_package_Model> purachase_package_modelslist = new ArrayList<>();
    String status,s_pack_date,s_orderid,s_userid,s_price,s_packname,s_image,s_pack_id,
            s_id,s_pack_desc,s_pack_type;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activityMyPackagesBinding = ActivityMyPackagesBinding.inflate(inflater, container, false);

//        bottomSheetDashboard.getMenu().getItem(0).setIcon(R.drawable.ic_home);
//        bottomSheetDashboard.getMenu().getItem(1).setIcon(R.drawable.ic_packageicons);
//        bottomSheetDashboard.getMenu().getItem(2).setIcon(R.drawable.ic_notificationfot);
//        bottomSheetDashboard.getMenu().getItem(3).setIcon(R.drawable.ic_more);
//        bottomSheetDashboard.getMenu().getItem(1).setChecked(true);

        HomeActivity.iv_menu_main.setVisibility(View.VISIBLE);
        HomeActivity.tvUserHello.setText(getResources().getString(R.string.my_packages));
        HomeActivity.ivBack.setVisibility(View.GONE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);
        HomeActivity.ivWallet.setVisibility(View.VISIBLE);

        new Purachase_packages_ASY().execute();

        return activityMyPackagesBinding.getRoot();
    }

    class Purachase_packages_ASY extends AsyncTask<String,String,String> {

        JSONArray array;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
            try {
                purachase_package_modelslist.clear();
            }catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            String response =  webRequest.makeWebServiceCall(AllUrl.packages_my_packgesApi,2,hashMap);

            System.out.println("Purachase packages parameter >>>>>>>>>>>>>>     "+hashMap);
            System.out.println("Purachase packages Response >>>>>>>>>>>>>>     "+response);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                array = jsonObject.getJSONArray("data");

                for(int i=0;i<array.length();i++) {
                    JSONObject c = array.getJSONObject(i);
                    s_orderid = c.getString("order_id");
                    s_userid = c.getString("user_id");
                    s_price =  c.getString("price");
                    s_packname = c.getString("pack_name");
                    s_image = c.getString("pack_image");
                    s_pack_id = c.getString("pack_id");
                    s_pack_desc = c.getString("pack_desc");
                    s_pack_date = c.getString("pack_time");
                    s_pack_type = c.getString("pack_type");

                    Purachase_package_Model package_model=new Purachase_package_Model();
                    if (s_pack_date.equals("0")){}else {
                        package_model.setPack_title(s_packname);
                        package_model.setPack_price(s_price);
                        package_model.setPack_image(s_image);
                        package_model.setPack_id(s_pack_id);
                        package_model.setPack_desc(s_pack_desc);
                        package_model.setPack_date(s_pack_date);
                        package_model.setPack_type(s_pack_type);
                        purachase_package_modelslist.add(package_model);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(TAG, "doInBackground: "+e );
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
//            try {
                if (status.equals("true")){
                    activityMyPackagesBinding.rvMyPackages.setAdapter(new MyArrayAdapter(getActivity()));
                    activityMyPackagesBinding.tvNoMyPackages.setVisibility(View.GONE);
                    activityMyPackagesBinding.rvMyPackages.setVisibility(View.VISIBLE);
                    activityMyPackagesBinding.noDataFoundGif.setVisibility(View.GONE);
//                    Log.e(TAG, "onPostExecute: "+ purachase_package_modelslist.size());
                }else {
                    activityMyPackagesBinding.tvNoMyPackages.setVisibility(View.GONE);
                    activityMyPackagesBinding.rvMyPackages.setVisibility(View.GONE);
                    activityMyPackagesBinding.noDataFoundGif.setVisibility(View.VISIBLE);
                    Glide.with(MyPackagesActivity.this).asGif().load(R.drawable.nodatagif).into(activityMyPackagesBinding.noDataFoundGif);
                }
//            }catch (Exception ex){
//                ex.printStackTrace();
//            }
        }
    }

    private class MyArrayAdapter extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter(FragmentActivity con) {
            // TODO Auto-generated constructor stub
            mInflater = LayoutInflater.from(con);

        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return purachase_package_modelslist.size();
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent holder;
            View v = convertView;
            if (v == null) {
                v = mInflater.inflate(R.layout.my_packages_layout, null);
                holder = new ListContent();

                holder.title = v.findViewById(R.id.tvPackagesTitle);
                holder.imageviewpack = v.findViewById(R.id.ivPackagesImage);
                holder.textview_date_pack = v.findViewById(R.id.tvDatePackages);

                v.setTag(holder);
            } else {

                holder = (ListContent) v.getTag();
            }
//            Log.e(TAG, "getView: "+purachase_package_modelslist.size() );
            try{

                holder.title.setText(Html.fromHtml(purachase_package_modelslist.get(position).getPack_title()));
                Glide.with(mContext).load(purachase_package_modelslist.get(position).getPack_image().replace("http://","https://")).into(holder.imageviewpack);

                System.out.println(" enter null day o daay  "+purachase_package_modelslist.get(position).getPack_date());

                holder.textview_date_pack.setText("Expiring in "+purachase_package_modelslist.get(position).getPack_date()+" Days.");

            }catch (Exception e){
                e.printStackTrace();
            }

            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    s_id = purachase_package_modelslist.get(position).getPack_id();
                    s_packname = purachase_package_modelslist.get(position).getPack_title();
                    s_pack_type = purachase_package_modelslist.get(position).getPack_type();

                    sessionManager.setPackname(s_packname);
                    sessionManager.setSavedPackId(s_id);

                    Fragment myFragment = new ChapterNameActivity();
                    FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flFrameLayout,myFragment,"chapterName");
                    fragmentTransaction.addToBackStack("chapterName");
                    fragmentTransaction.commit();

                    Bundle bundle = new Bundle();
                    bundle.putString("pack_id",s_id);
                    bundle.putString("pack_name",s_packname);
                    bundle.putString("pack_type",s_pack_type);
                    myFragment.setArguments(bundle);
                }
            });

            return v;
        }

    }

    static class ListContent {
        TextView title,textview_date_pack;
        ImageView imageviewpack;
    }
}