package com.theLearningcLub;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Paint;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.theLearningcLub.Model_Class.City_Model;
import com.theLearningcLub.Model_Class.SearchMainModelClass;
import com.theLearningcLub.adapter.CustomAdapter2;
import com.theLearningcLub.adapter.CustomListAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivitySearchBinding;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SearchActivity extends BaseFragment {

    ActivitySearchBinding activitySearchBinding;

    MyArrayAdapter2 myArrayAdapter2;
    CustomAdapter2 customAdapter;
    ArrayList<City_Model> eventSubModelClasses ;
    List<SearchMainModelClass> eventMainModelClasses = new ArrayList<>();

    String name,status;
    String cat_name,pack_name,pack_id,all_is_free,cat_id,price,pack_image,offer_price,
            time_perioud,alpha_id,grammar_id,description,rateStar;

    List<City_Model> list = new ArrayList<>(); //Auto increment adapter list
    List<String> dataList = new ArrayList<>(); //API Data insert adapter list
    List<String> dataidList = new ArrayList<>(); //API Data insert adapter list
    List<String> isfreeList = new ArrayList<>(); //API Data insert adapter list
    List<String> parentnameList = new ArrayList<>(); //API Data insert adapter list
    List<String> cartidList = new ArrayList<>(); //API Data insert adapter list
    List<String> alplhaidList = new ArrayList<>(); //API Data insert adapter list
    List<String> grammaridList = new ArrayList<>(); //API Data insert adapter list
    List<String> timeperioudList = new ArrayList<>(); //API Data insert adapter list
    List<String> priceList = new ArrayList<>(); //API Data insert adapter list
    List<String> offerpriceList = new ArrayList<>(); //API Data insert adapter list
    List<String> packimageList = new ArrayList<>(); //API Data insert adapter list
    List<String> descriptionList = new ArrayList<>(); //API Data insert adapter list
    List<String> ratestarList = new ArrayList<>(); //API Data insert adapter list

    Dialog dialogExit;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activitySearchBinding = ActivitySearchBinding.inflate(inflater,container,false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);


        HomeActivity.tvUserHello.setText("");
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.VISIBLE);

        new Search_Task().execute();
        new Search_Task2().execute();

        CustomListAdapter adapter = new CustomListAdapter(mContext,
                R.layout.autocompleteitem, dataList,dataidList);

        activitySearchBinding.autoSearch.setThreshold(1);
        activitySearchBinding.autoSearch.setAdapter(adapter);

        activitySearchBinding.autoSearch.setOnItemClickListener((adapterView, view, itemIndex, id) -> {
//                String queryString = (String)adapterView.getItemAtPosition(itemIndex);

            int index = dataList.indexOf(activitySearchBinding.autoSearch.getText().toString());
            eventMainModelClasses.clear();
            eventSubModelClasses.clear();
            City_Model city_model = new City_Model();
            city_model.setCityName(dataList.get(index));
            city_model.setId(dataidList.get(itemIndex));
            city_model.setIs_free(isfreeList.get(index));
            city_model.setCat_id(cartidList.get(index));
            city_model.setAlpha_id(alplhaidList.get(index));
            city_model.setGrammar_id(grammaridList.get(index));
            city_model.setTime_perioud(timeperioudList.get(index));
            city_model.setPrice(priceList.get(index));
            city_model.setOffer_price(offerpriceList.get(index));
            city_model.setPack_image(packimageList.get(index));
            city_model.setDescription(descriptionList.get(index));
            city_model.setRateStar(ratestarList.get(index));
            eventSubModelClasses.add(city_model);
            SearchMainModelClass eventMainModelClass = new SearchMainModelClass(parentnameList.get(index),eventSubModelClasses);
            eventMainModelClasses.add(eventMainModelClass);
            customAdapter = new CustomAdapter2(mContext,list, position -> {
                Fragment myFragment = new PackageActivity2();
                FragmentTransaction fragmentTransaction = Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.flFrameLayout,myFragment,"packages");
                fragmentTransaction.addToBackStack("packages");
                fragmentTransaction.commit();
                Bundle bundle = new Bundle();
                bundle.putString("name",list.get(position).cityName);
                bundle.putString("title",list.get(position).cityName);
                bundle.putString("packid",list.get(position).id);
                bundle.putString("price",list.get(position).getPrice());
                bundle.putString("offer_price",list.get(position).getOffer_price());
                bundle.putString("description",list.get(position).getDescription());
                bundle.putString("image",list.get(position).getPack_image());
                bundle.putString("rating",list.get(position).getRateStar());
                bundle.putString("key","2");
                bundle.putString("is_free",list.get(position).getIs_free());
                myFragment.setArguments(bundle);
            });
            myArrayAdapter2.notifyDataSetChanged();
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        });

        myArrayAdapter2 = new MyArrayAdapter2(mContext);
        activitySearchBinding.rvTrending.setAdapter(myArrayAdapter2);


        activitySearchBinding.autoSearch.setPaintFlags(Paint.UNDERLINE_TEXT_FLAG);

        activitySearchBinding.autoSearch.setOnClickListener(v -> {
            name = activitySearchBinding.autoSearch.getText().toString();
            InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(activitySearchBinding.autoSearch.getWindowToken(),
                    InputMethodManager.RESULT_UNCHANGED_SHOWN);
        });

        return activitySearchBinding.getRoot();

    }

    class Search_Task extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            list.clear();
            dataList.clear();
            dataidList.clear();
            isfreeList.clear();
            parentnameList.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("name","1");

            String response = webRequest.makeWebServiceCall(AllUrl.dlc_search_api_1Api+"?vocabulary_id="+1+"&key="+sessionManager.getLoginkey()+"&user_id="+sessionManager.getSavedUserId(),2);

            String url = AllUrl.dlc_search_api_1Api+"?vocabulary_id="+"1"+"&key="+sessionManager.getLoginkey()+"&user_id="+sessionManager.getSavedUserId();

            System.out.println("Search Responsed >>>>>>>>>>>>>>>>>>>>>>"+url);
            System.out.println("Search 1 Response >>>>>>>>>>>>>>>>>>>>>>"+response);
            System.out.println("Search 1 parameter >>>>>>>>>>>>>>>>>>>>>>"+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);

                status = jsonObject.getString("status");

                System.out.println("status >>>>>>>>>> "+status);

                if(status.equals("true")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("body");
                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        String cat_name = jsonObject1.getString("cat_name");
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                        eventSubModelClasses = new ArrayList<>();
                        for (int i1=0;i1<jsonArray1.length();i1++){
                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i1);

                            pack_name = jsonObject2.getString("pack_name");
                            pack_id = jsonObject2.getString("pack_id");
                            all_is_free = jsonObject2.getString("is_free");
                            cat_id = jsonObject2.getString("cat_id");
                            alpha_id = jsonObject2.getString("alpha_id");
                            grammar_id = jsonObject2.getString("grammar_id");
                            time_perioud = jsonObject2.getString("time_perioud");
                            price = jsonObject2.getString("price");
                            offer_price = jsonObject2.getString("offer_price");
                            pack_image = jsonObject2.getString("pack_image");
                            description = jsonObject2.getString("description");
                            rateStar = jsonObject2.getString("rateStar");
                            System.out.println("event>>>>>>>>>>>>>>>>>>>>>>>>>"+pack_name);

                            dataidList.add(pack_id);
                            dataList.add(pack_name);
                            isfreeList.add(all_is_free);
                            parentnameList.add(cat_name);
                            cartidList.add(cat_id);
                            alplhaidList.add(alpha_id);
                            grammaridList.add(grammar_id);
                            timeperioudList.add(time_perioud);
                            priceList.add(price);
                            offerpriceList.add(offer_price);
                            packimageList.add(pack_image);
                            descriptionList.add(description);
                            ratestarList.add(rateStar);
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                for (int i = 0; i < dataList.size(); i++) {
                    City_Model city_model = new City_Model();
                    city_model.setCityName(dataList.get(i));
                    city_model.setId(dataidList.get(i));
                    city_model.setCat_id(cartidList.get(i));
                    city_model.setAlpha_id(alplhaidList.get(i));
                    city_model.setGrammar_id(grammaridList.get(i));
                    city_model.setTime_perioud(timeperioudList.get(i));
                    city_model.setPrice(priceList.get(i));
                    city_model.setOffer_price(offerpriceList.get(i));
                    city_model.setPack_image(packimageList.get(i));
                    city_model.setDescription(descriptionList.get(i));
                    city_model.setRateStar(ratestarList.get(i));
                    list.add(city_model);
                }
            }catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    class Search_Task2 extends AsyncTask<String, String, String> {
        JSONArray jsonArray;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            eventMainModelClasses.clear();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest=new WebRequest();
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("name","2");

            String response = webRequest.makeWebServiceCall(AllUrl.dlc_search_api_1Api+"?vocabulary_id="+1+"&key="+sessionManager.getLoginkey()+"&user_id="+sessionManager.getSavedUserId(),2);

            System.out.println("Search 2 Response >>>>>>>>>>>>>>>>>>>>>>"+response);
            System.out.println("Search 2 parameter >>>>>>>>>>>>>>>>>>>>>>"+hashMap);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                System.out.println("status >>>>>>>>>> "+status);
                if(status.equals("true")) {
                    jsonArray = jsonObject.getJSONArray("body");
                    for(int i=0;i<jsonArray.length();i++) {

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        cat_name = jsonObject1.getString("cat_name");
                        JSONArray jsonArray1 = jsonObject1.getJSONArray("data");
                        eventSubModelClasses = new ArrayList<>();
                        for (int i1=0;i1<jsonArray1.length();i1++) {

                            JSONObject jsonObject2 = jsonArray1.getJSONObject(i1);
                            pack_name = jsonObject2.getString("pack_name");
                            pack_id = jsonObject2.getString("pack_id");
                            all_is_free = jsonObject2.getString("is_free");
                            cat_id = jsonObject2.getString("cat_id");
                            alpha_id = jsonObject2.getString("alpha_id");
                            grammar_id = jsonObject2.getString("grammar_id");
                            time_perioud = jsonObject2.getString("time_perioud");
                            price = jsonObject2.getString("price");
                            offer_price = jsonObject2.getString("offer_price");
                            pack_image = jsonObject2.getString("pack_image");
                            description = jsonObject2.getString("description");
                            rateStar = jsonObject2.getString("rateStar");

                            City_Model eventSubModelClass = new City_Model();

                            eventSubModelClass.setId(pack_id);
                            eventSubModelClass.setCityName(pack_name);
                            eventSubModelClass.setIs_free(all_is_free);
                            eventSubModelClass.setCat_id(cat_id);
                            eventSubModelClass.setAlpha_id(alpha_id);
                            eventSubModelClass.setGrammar_id(grammar_id);
                            eventSubModelClass.setTime_perioud(time_perioud);
                            eventSubModelClass.setPrice(price);
                            eventSubModelClass.setOffer_price(offer_price);
                            eventSubModelClass.setPack_image(pack_image);
                            eventSubModelClass.setDescription(description);
                            eventSubModelClass.setRateStar(rateStar);
                            eventSubModelClasses.add(eventSubModelClass);
                        }
                        SearchMainModelClass eventMainModelClass = new SearchMainModelClass(cat_name,eventSubModelClasses);
                        eventMainModelClasses.add(eventMainModelClass);
                    }
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @SuppressLint("WrongConstant")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            myArrayAdapter2.notifyDataSetChanged();
        }
    }

    @Override
    public void onResume() {

        super.onResume();

        Objects.requireNonNull(getView()).setFocusableInTouchMode(true);
        getView().requestFocus();
        getView().setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() == KeyEvent.ACTION_UP && keyCode == KeyEvent.KEYCODE_BACK){
                showExitDialog();
                return true;
            }
            return false;
        });
    }

    public void showExitDialog() {
        dialogExit = new Dialog(mContext);
        dialogExit.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
        dialogExit.setContentView(R.layout.dialog_logout);
        dialogExit.setCancelable(true);
        dialogExit.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialogExit.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

        TextView text = dialogExit.findViewById(R.id.content);
        text.setText(getResources().getString(R.string.do_you_want_to_exit));

        (dialogExit.findViewById(R.id.bt_YES)).setOnClickListener(v -> mContext.finish());

        (dialogExit.findViewById(R.id.bt_NO)).setOnClickListener(v -> dialogExit.dismiss());

        dialogExit.show();
        dialogExit.getWindow().setAttributes(lp);
    }

    //---------------------Array Adapter-------------------------------
    private class MyArrayAdapter2 extends BaseAdapter {

        LayoutInflater mInflater;

        public MyArrayAdapter2(Activity con) {
            mInflater = LayoutInflater.from(con);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return eventMainModelClasses.size();
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


        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            final ListContent2 holder;
            View v = convertView;


            if (v == null) {
                v = mInflater.inflate(R.layout.recent_search_layout, null);
                holder = new ListContent2();
                holder.nonlistview = v.findViewById(R.id.rvSearchView);
                holder.txt_monthname = v.findViewById(R.id.tvSearchTitle);
                v.setTag(holder);
            } else {

                holder = (ListContent2) v.getTag();
            }
            try {
                ArrayList<City_Model> data = eventMainModelClasses.get(position).getsList();
                holder.txt_monthname.setText(eventMainModelClasses.get(position).getMonth_name());
                customAdapter = new CustomAdapter2(mContext, data, position1 -> {
                    Fragment myFragment = new PackageActivity2();
                    FragmentTransaction fragmentTransaction = requireActivity().getSupportFragmentManager().beginTransaction();
                    fragmentTransaction.replace(R.id.flFrameLayout,myFragment,"packages");
                    fragmentTransaction.addToBackStack("packages");
                    fragmentTransaction.commit();
                    Bundle bundle = new Bundle();
                    bundle.putString("name",data.get(position1).cityName);
                    bundle.putString("title",data.get(position1).cityName);
                    bundle.putString("packid",data.get(position1).id);
                    bundle.putString("price",data.get(position1).getPrice());
                    bundle.putString("offer_price",data.get(position1).getOffer_price());
                    bundle.putString("description",data.get(position1).getDescription());
                    bundle.putString("image",data.get(position1).getPack_image());
                    bundle.putString("rating",data.get(position1).getRateStar());
                    bundle.putString("key","2");
                    bundle.putString("is_free",data.get(position1).getIs_free());
                    myFragment.setArguments(bundle);
                });
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
                linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                holder.nonlistview.setHasFixedSize(true);
                holder.nonlistview.setLayoutManager(linearLayoutManager);
                holder.nonlistview.setAdapter(customAdapter);
                customAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return v;
        }
    }

    static class ListContent2 {
        TextView txt_monthname;
        RecyclerView nonlistview;
    }
}