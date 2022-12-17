package com.theLearningcLub;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import com.paytm.pgsdk.PaytmOrder;
import com.paytm.pgsdk.PaytmPGService;
import com.paytm.pgsdk.PaytmPaymentTransactionCallback;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.utils.BaseActivity;
import com.theLearningcLub.utils.JSONParser;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kamal_bunkar on 02-02-2018.
 */

public class checksum extends BaseActivity implements PaytmPaymentTransactionCallback {

    String s_user_id,price,txnAmountString,orderIdString,packid,add_order_status,message,agent_status,
            package_type;
    String RESPMSG,mid,TXNDATE,banktxnid,txndate,txnamount,paytm_transaction_status,orderId,txnid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showProgressDialog();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        s_user_id = sessionManager.getSavedUserId();

        Intent in = getIntent();
        packid = in.getStringExtra("pack_id");
        price = in.getStringExtra("price");
        orderIdString= in.getStringExtra("order_id");
        package_type= in.getStringExtra("package_type");
        System.out.println("package_type>> "+package_type);
        txnAmountString = price;
        sendUserDetailTOServerdd dl = new sendUserDetailTOServerdd();
        dl.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

        hideProgressDialog();

    }

    public class sendUserDetailTOServerdd extends AsyncTask<ArrayList<String>, Void, String> {

        private ProgressDialog dialog = new ProgressDialog(checksum.this);

        private String   mid, custid, amt;
        String url ="https://learningclub.co.in/paytm/generateChecksum.php";
        String varifyurl =  "https://securegw.paytm.in/theia/paytmCallback?ORDER_ID="+orderIdString;
        //
//        "https://pguat.paytm.com/paytmchecksum/paytmCallback.jsp";//
        String CHECKSUMHASH ="";

        @Override
        protected void onPreExecute() {
            this.dialog.setMessage("Please wait");
            this.dialog.show();

            // initorderIdString();
            mid = "Dictio75795060424141";  // CREATI42545355156573
            custid = s_user_id;

        }

        protected String doInBackground(ArrayList<String>... alldata) {

            // String  url ="http://xxx.co.in/generateChecksum.php";

            JSONParser jsonParser = new JSONParser(checksum.this);
            String param=
                    "MID="+mid+
                            "&ORDER_ID=" + orderIdString+
                            "&CUST_ID="+custid+
                            "&CHANNEL_ID=WAP&TXN_AMOUNT="+txnAmountString+"&WEBSITE=DEFAULT"+"&CALLBACK_URL="+ varifyurl+"&INDUSTRY_TYPE_ID=Retail";

            Log.e("checksum"," param string "+param);

            JSONObject jsonObject = jsonParser.makeHttpRequest(url,"POST",param);
            // yaha per checksum ke saht order id or status receive hoga..
            Log.e("CheckSum result >>",jsonObject.toString());
            if(jsonObject != null){
                Log.e("CheckSum result >>    ",jsonObject.toString());
                try {
                    CHECKSUMHASH = jsonObject.has("CHECKSUMHASH") ? jsonObject.getString("CHECKSUMHASH") : "";
                    Log.e("CheckSum result >>   ",CHECKSUMHASH);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            return CHECKSUMHASH;
        }

        @Override
        protected void onPostExecute(String result) {
            // jab run kroge to yaha checksum dekhega
            ///ab service ko call krna hai
            Log.e(" setup acc ","  signup result  " + result);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

//            PaytmPGService Service =PaytmPGService.getStagingService();
            // when app is ready to publish use production service
            PaytmPGService Service = PaytmPGService.getProductionService();

            // now call paytm service here
            //below parameter map is required to construct PaytmOrder object, Merchant should replace below map values with his own values
            Map<String, String> paramMap = new HashMap<String, String>();
            //these are mandatory parameters
            // ye sari valeu same hon achaiye

            //MID provided by paytm
            paramMap.put("MID", mid);
            paramMap.put("ORDER_ID", orderIdString);
            paramMap.put("CUST_ID", custid);
            paramMap.put("CHANNEL_ID", "WAP");
            paramMap.put("TXN_AMOUNT", txnAmountString);
            paramMap.put("WEBSITE", "DEFAULT");
            paramMap.put("CALLBACK_URL" ,varifyurl);
            //paramMap.put( "EMAIL" , "abc@gmail.com");   // no need
            // paramMap.put( "MOBILE_NO" , "9144040888");  // no need
            paramMap.put("CHECKSUMHASH" ,CHECKSUMHASH);
            //paramMap.put("PAYMENT_TYPE_ID" ,"CC");    // no need
            paramMap.put("INDUSTRY_TYPE_ID", "Retail");

            PaytmOrder Order = new PaytmOrder(paramMap);

            Log.e("checksum ", paramMap.toString());


            Service.initialize(Order,null);
            // start payment service call here
            Service.startPaymentTransaction(checksum.this, true, true, checksum.this  );
        }
    }

    @Override
    public void onTransactionResponse(Bundle bundle) {
        try {
            RESPMSG = bundle.getString("RESPMSG");
            mid = bundle.getString("MID");
            orderId = bundle.getString("ORDERID");
            paytm_transaction_status = bundle.getString("STATUS");
            txnamount = bundle.getString("TXNAMOUNT");
            txndate = bundle.getString("TXNDATE");
            txnid = bundle.getString("TXNID");
            banktxnid = bundle.getString("BANKTXNID");
            TXNDATE = bundle.getString("TXNDATE");


            //        Toast.makeText(this, bundle.toString(), Toast.LENGTH_LONG).show();
            //        Toast.makeText(this, paytm_transaction_status, Toast.LENGTH_LONG).show();

            if (paytm_transaction_status.contains("TXN_SUCCESS"))
            {
                System.out.println(" ==============             "+paytm_transaction_status);

                new AddOrderAs().execute();
            }else {
                finish();
            }
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public void networkNotAvailable() {

    }

    @Override
    public void clientAuthenticationFailed(String s) {

    }

    @Override
    public void someUIErrorOccurred(String s) {
        Log.e("checksum ", " ui fail respon  "+ s );
    }

    @Override
    public void onErrorLoadingWebPage(int i, String s, String s1) {
        Log.e("checksum ", " error loading pagerespon true "+ s + "  s1 " + s1);
    }

    @Override
    public void onBackPressedCancelTransaction() {
        Log.e("checksum ", " cancel call back respon  " );

        Intent intent = new Intent(checksum.this,Activity_Fail_Payment.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onTransactionCancel(String s, Bundle bundle) {
        Log.e("checksum ", "  transaction cancel " );

        Intent intent = new Intent(checksum.this,Activity_Fail_Payment.class);
        startActivity(intent);
        finish();

    }

    class AddOrderAs extends AsyncTask<String,String,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {

            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("pack_id",packid);
            hashMap.put("transaction_id",txnid);
            hashMap.put("price",txnamount);
            hashMap.put("tex","00");
            hashMap.put("total", price);
            hashMap.put("date",txndate);
            hashMap.put("key", sessionManager.getLoginkey());
            hashMap.put("package_type", package_type);


            String response = webRequest.makeWebServiceCall(AllUrl.packages_add_order_newApi,2,hashMap);

            System.out.println("Add Order request >>>>>>>>>>>>>>>>>>>>>>      "+hashMap);
            System.out.println("Add Order Response >>>>>>>>>>>>>>>>>>>>>>     "+response);
            try {
                JSONObject jsonObject=new JSONObject(response);
                JSONObject jsonObject1=jsonObject.getJSONObject("response");
                add_order_status=  jsonObject1.getString("status");
                message = jsonObject1.getString("msg");
                agent_status = jsonObject1.getString("agent_status");

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

                if(add_order_status.equals("true")) {
                    Intent intent = new Intent(checksum.this,Activity_Success_Payment.class);
                    intent.putExtra("amount",price);
                    startActivity(intent);
                    finish();

                    if (agent_status.equals("2")){
                        sessionManager.setIsAgent("true");
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
