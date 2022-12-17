package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.theLearningcLub.adapter.MyCartAdapter;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityMyCartBinding;
import com.theLearningcLub.model.viewcart.ViewCartModel;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.FilterClick;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

public class MyCartActivity extends BaseFragment {

    ActivityMyCartBinding activityMyCartBinding;
    MyCartAdapter myCartAdapter;

    String randomcode,set_promoCode;

    String cartid;
    String status,title,message,txnamount;

    ArrayList<String> packidarr = new ArrayList<>();
    ArrayList<String> pricearr = new ArrayList<>();
    ArrayList<String> type_arr = new ArrayList<>();

    int sum = 0;
    int gtotal = 0;
    int count = 0;
    String totalamount;

    String custid="",orderId="",mid="",txnid="",txtdate="";
    String packid,price,pkg_type,msg;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        activityMyCartBinding = ActivityMyCartBinding.inflate(inflater,container,false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.my_cart));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.VISIBLE);
        HomeActivity.rlCart.setVisibility(View.GONE);

        viewCartApi();
        CartCountApi();

        activityMyCartBinding.tvCheckOutBTN.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
            @Override
            public void onClick(View v) {

                int min = 98000;
                int max = 980000000;

                Random r = new Random();
                int i1 = r.nextInt(max - min + 1) + min;
                randomcode = String.valueOf(i1);
                orderId="DLC"+i1;

                System.out.println("Order id >>>>>>>>>>>>>>>"+orderId);
                custid = sessionManager.getSavedUserId();
                mid = "Dictio75795060424141"; /// your marchant key  MID :


                Intent intent = new Intent(mContext,checksum.class);
                intent.putExtra("pack_id",packid);
                intent.putExtra("price",totalamount);
                intent.putExtra("order_id",orderId);
                intent.putExtra("package_type",pkg_type);
                startActivity(intent);
            }
        });

        activityMyCartBinding.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                set_promoCode = Objects.requireNonNull(activityMyCartBinding.etApplyPromoCode.getText()).toString().trim();

                if (TextUtils.isEmpty(set_promoCode)){
                    activityMyCartBinding.etApplyPromoCode.setError("Please enter promo code");
                }else {
                    new AddPromoCode().execute();
                }
            }
        });

        activityMyCartBinding.ivDeletePromoCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                activityMyCartBinding.llSetPromo.setVisibility(View.VISIBLE);
                activityMyCartBinding.llViewPromoCode.setVisibility(View.GONE);
                activityMyCartBinding.etApplyPromoCode.setText("");
                activityMyCartBinding.tvDiscount.setText("0");
                activityMyCartBinding.tvTotalPrice.setText(""+sum);
            }
        });

        activityMyCartBinding.rvMyCart.setLayoutManager(new LinearLayoutManager(mContext,
                LinearLayoutManager.VERTICAL, false));

        return activityMyCartBinding.getRoot();
    }

    private void viewCartApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.view_cart_newApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("viewCartApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            JSONArray jsonArray = jsonObject.getJSONArray("data");

                            if (jsonArray.isNull(0)) {
                                activityMyCartBinding.llCartView.setVisibility(View.GONE);
                                activityMyCartBinding.llEmptyCart.setVisibility(View.VISIBLE);
                            } else {
                                ViewCartModel viewCartModel = new Gson().fromJson(response, ViewCartModel.class);
                                if (viewCartModel.getStatus().equalsIgnoreCase(AllUrl.status)) {
                                    for (int i = 0; i < viewCartModel.getData().size(); i++) {
                                        myCartAdapter = new MyCartAdapter(viewCartModel.getData(), mContext, new FilterClick() {
                                            @Override
                                            public void filterClick(int position) {
                                                cartid = viewCartModel.getData().get(position).getPackId();
                                                alertdialogbox();
                                            }
                                        });

                                        activityMyCartBinding.rvMyCart.setAdapter(myCartAdapter);
                                        myCartAdapter.notifyDataSetChanged();

                                        sum = sum + Integer.parseInt(viewCartModel.getData().get(i).getPrice());
                                        packidarr.add(viewCartModel.getData().get(i).getPackId());
                                        pricearr.add(viewCartModel.getData().get(i).getPrice());
                                        type_arr.add(viewCartModel.getData().get(i).getPackageType());

                                        count = myCartAdapter.getItemCount();
                                        sessionManager.setSavedCART_NO(String.valueOf(count));

                                        activityMyCartBinding.tvPrice.setText("" + sum);
                                        gtotal = sum;
                                        activityMyCartBinding.tvTotalPrice.setText("" + sum);
                                        totalamount = String.valueOf(sum);
                                        activityMyCartBinding.tvCartCountList.setText(getResources().getString(R.string.my_cart) + " " + "(" + viewCartModel.getData().size() + ")");
                                    }
                                    String[] priceArr = pricearr.toArray(new String[0]);
                                    String[] idArr = packidarr.toArray(new String[0]);
                                    String[] typeArr = type_arr.toArray(new String[0]);
                                    StringBuilder sb = new StringBuilder();
                                    StringBuilder pric = new StringBuilder();
                                    StringBuilder typesb = new StringBuilder();

                                    for (int j = 0; j < typeArr.length; j++) {
                                        if (j < type_arr.size() - 1) {
                                            typesb.append(typeArr[j]).append(",");
                                        } else {
                                            typesb.append(typeArr[j]);
                                        }
                                    }

                                    for (int j = 0; j < idArr.length; j++) {
                                        if (j < packidarr.size() - 1) {
                                            sb.append(idArr[j]).append(",");
                                        } else {
                                            sb.append(idArr[j]);
                                        }
                                    }

                                    for (int j1 = 0; j1 < priceArr.length; j1++) {
                                        if (j1 < pricearr.size() - 1) {
                                            pric.append(priceArr[j1]).append(",");
                                        } else {
                                            pric.append(priceArr[j1]);
                                        }
                                    }
                                    packid = sb.toString();
                                    price = pric.toString();
                                    pkg_type = typesb.toString();

                                    System.out.println("packid   >>>>>>>   " + packid);
                                    System.out.println("pkg_type   >>>>>>>   " + pkg_type);

                                    activityMyCartBinding.llCartView.setVisibility(View.VISIBLE);
                                    activityMyCartBinding.llEmptyCart.setVisibility(View.GONE);
                                }
                            }
                            hideProgressDialog();
                        } catch(Exception e){
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("viewCartApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("viewCartApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(getActivity(), NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }

    class AddPromoCode extends AsyncTask<String,String,String> {

        String discount,id,promo_code,start_date,end_date,date_created;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest request = new WebRequest();

            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("promo_code",set_promoCode);

            String response = request.makeWebServiceCall(
                    AllUrl.check_promo_codeApi,2,hashMap);

            System.out.println("promo code response>>>>>>>>"+response);
            System.out.println("promo code >>>>>>>>>>>>>>>> "+set_promoCode);

            try {
                JSONObject jsonObject = new JSONObject(response);
                status = jsonObject.getString("status");
                msg = jsonObject.getString("msg");
                String body = jsonObject.getString("body");
                JSONObject obj = new JSONObject(body);
                id = obj.getString("id");
                promo_code = obj.getString("promo_code");
                title = obj.getString("title");
                start_date = obj.getString("start_date");
                end_date = obj.getString("end_date");
                discount = obj.getString("discount");
                date_created = obj.getString("date_created");

                System.out.println("promo code >>>>>>"+discount);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();
            try {
                if (status.equals("true")){
                    activityMyCartBinding.llSetPromo.setVisibility(View.GONE);
                    activityMyCartBinding.llViewPromoCode.setVisibility(View.VISIBLE);
                    activityMyCartBinding.tvValidPromoCode.setText(promo_code);
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                    double res = (sum / 100.0f) * Double.parseDouble(discount);
                    System.out.println("discount double >>>>>>"+res);
                    int result = (int) Math.round(res);
                    activityMyCartBinding.tvDiscount.setText(""+"-"+result);

                    int discount_value = sum - result;
                    activityMyCartBinding.tvTotalPrice.setText(""+discount_value);
                    totalamount = String.valueOf(discount_value);
                }else {
                    Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void alertdialogbox() {
        //will create a view of our custom dialog layout
        View alertCustomdialog = LayoutInflater.from(mContext).inflate(R.layout.dialog_box,null);
        //initialize alert builder.
        android.app.AlertDialog.Builder alert = new android.app.AlertDialog.Builder(mContext);

        //set our custom alert dialog to tha alertdialog builder
        alert.setView(alertCustomdialog);
        AppCompatButton btnYes = alertCustomdialog.findViewById(R.id.btnYes);
        AppCompatButton btnNo = alertCustomdialog.findViewById(R.id.btnNo);
        TextView tvMsg = alertCustomdialog.findViewById(R.id.tvMsg);
        tvMsg.setText(mContext.getResources().getString(R.string.delete_massage));

        final android.app.AlertDialog dialog = alert.create();
        //this line removed app bar from dialog and make it transperent and you see the image is like floating outside dialog box.
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        //finally show the dialog box in android all
        dialog.show();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DeleteAs().execute();
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

    class DeleteAs extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("pack_id",cartid);
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            String response = webRequest.makeWebServiceCall(
                    AllUrl.delete_cardApi,2,hashMap);

            System.out.println("Delete Response >>>>>>>>>>>>>>>  "+response);
            System.out.println("Delete parameter >>>>>>>>>>>>>>  "+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status = jsonObject.getString("status");
                message = jsonObject.getString("msg");

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sum = 0;
            try {
                if (status.equals("true")){
                    viewCartApi();
                    CartCountApi();
                } else {
                    myCartAdapter.notifyDataSetChanged();
                }
            }catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void showAlert(String msg){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
    }

    class AddOrderAs extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest = new WebRequest();
            HashMap<String,String> hashMap = new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("pack_id",packid);
            hashMap.put("transaction_id",txnid);
            hashMap.put("price",txnamount);
            hashMap.put("tex","00");
            hashMap.put("total", String.valueOf(gtotal));
            hashMap.put("date",txtdate);
            hashMap.put("key", sessionManager.getLoginkey());


            String response = webRequest.makeWebServiceCall(
                    AllUrl.add_orderApi,2,hashMap);

            System.out.println("Add Order request >>>>>>>>>>>>>>>>>>>>>>      "+hashMap);
            System.out.println("Add Order Response >>>>>>>>>>>>>>>>>>>>>>     "+response);
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONObject jsonObject1=jsonObject.getJSONObject("response");
                status =  jsonObject1.getString("status");
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
            try{
                if(status.equals("true")) {
                    Intent intent = new Intent(mContext,Activity_Success_Payment.class);
                    intent.putExtra("amount",gtotal);
                    startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Order is not add", Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void CartCountApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.count_cartApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);

                        System.out.println("CartCountApi response>>>>>>>>>>>>>>>" + response);

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");
                            String s_cartcount = jsonObject.getString("cart_count");

                            if (status.equals("success")) {
                                if (s_cartcount.isEmpty()) {
                                    HomeActivity.badgeCart.setVisibility(View.GONE);
                                } else if (s_cartcount.equals("0")) {
                                    HomeActivity.badgeCart.setVisibility(View.GONE);
                                } else {
                                    HomeActivity.badgeCart.setVisibility(View.VISIBLE);
                                    HomeActivity.badgeCart.setText(s_cartcount);
                                }
                            }
                            hideProgressDialog();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("CartCountApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id", sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("CartCountApi params>>>>>>>>>>>>>>" + params);
                    return params;
                }
            };
            queue.add(stringRequest);
        }else {
            Intent intent = new Intent(getActivity(), NoInternetActivity.class);
            startActivity(intent);
//            CommonFunction.showToastSingle(mContext,getResources().getString(R.string.net_connection));
        }
    }
}