package com.theLearningcLub;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.dynamiclinks.DynamicLink;
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks;
import com.google.firebase.dynamiclinks.ShortDynamicLink;
import com.theLearningcLub.Model_Class.Contact_Sync_Model;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityShareBinding;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.Random;

public class ShareActivity extends BaseFragment implements View.OnClickListener{

    ActivityShareBinding activityShareBinding;
    Cursor cursor;
    ArrayList<Contact_Sync_Model> NameList = new ArrayList<>();
    String name,phone,response,message,status,allcontact = "";

    boolean s_contactsyanc;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityShareBinding = ActivityShareBinding.inflate(inflater,container,false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.share));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);

        activityShareBinding.btnShare.setOnClickListener(this);

        if (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
           // requestPermissions( new String[]{Manifest.permission.READ_CONTACTS }, 100);
        }else{
            //readContacts();
            //new checksynkTask().execute();
        }

        s_contactsyanc = sessionManager.getContactSyanc();
        System.out.println("s_contactsyanc >>>>>>>>>>>>>>        "+s_contactsyanc);

        return activityShareBinding.getRoot();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btnShare){
            createlink();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        readContacts();
        new checksynkTask().execute();
    }

    /*-----------------------------------AsyncTask-------------------------------- */

    public void readContacts(){
        try {
            cursor = Objects.requireNonNull(getActivity()).getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, null);

            while (cursor.moveToNext()) {
                int nameIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);

                int phoneNumberIdx = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                name = cursor.getString(nameIdx);
                phone = cursor.getString(phoneNumberIdx);


                char c = name.charAt(0);
                char clast = 0;

                //........... second Char letter logic.....
                String[] number = name.split(" ");
                for (String s : number) {

                    clast = s.charAt(0);
                    System.out.println("last : " + clast);
                }

                Contact_Sync_Model contact_sync_model = new Contact_Sync_Model();

                contact_sync_model.setName(name);
                contact_sync_model.setNumber(phone);
                contact_sync_model.setFirstletter(c);
                contact_sync_model.setLastletter(clast);

                NameList.add(contact_sync_model);
                System.out.println("Name is :   " + name + " number is : " + phone);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    class checksynkTask extends AsyncTask<String, String, String> {
        String Message;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            showProgressDialog();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest = new WebRequest();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            response = webRequest.makeWebServiceCall(AllUrl.check_sinksApi, 2, hashMap);

            System.out.println("Check sync Response >>>>>>>>>>>>>>>>  " + response);
            System.out.println("Check sync parameter >>>>>>>>>>>>>>>>  " + hashMap);

            try {

                JSONObject jsonObject = new JSONObject(response);
                JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                status = jsonObject1.getString("status");
                Message = jsonObject1.getString("msg");

                System.out.println("Check sync status >>>>>>>>>>>>>>>>" + status);
                System.out.println("Check sync message >>>>>>>>>>>>>>>>" + Message);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            hideProgressDialog();

            if (status.contains("false")) {
                System.out.println("api call$$$$$$$$$$$$$$$$          ");
                new numbersyncTask().execute();
            }
        }

        class numbersyncTask extends AsyncTask<String, String, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showProgressDialog();

                for (int i = 0; i < NameList.size(); i++) {
                    allcontact = allcontact + NameList.get(i).getName() + NameList.get(i).getNumber() + ",";
                }
            }

            @Override
            protected String doInBackground(String... strings) {

                WebRequest webRequest = new WebRequest();
                HashMap<String, String> hashMap = new HashMap<>();
                hashMap.put("user_id", sessionManager.getSavedUserId());
                hashMap.put("contact", allcontact);
                hashMap.put("key", sessionManager.getLoginkey());

                response = webRequest.makeWebServiceCall(AllUrl.contact_sinkApi, 2, hashMap);

                System.out.println("Number Sync Response >>>>>>>>>>>>>>" + response);
                System.out.println("Number Sync parameter >>>>>>>>>>>>>>" + hashMap);
                System.out.println("Loginkey >>>>>>>>>>>>>>   " + sessionManager.getLoginkey());

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONObject jsonObject1 = jsonObject.getJSONObject("response");
                    message = jsonObject1.getString("msg");
                    status = jsonObject1.getString("status");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                hideProgressDialog();
            }
        }
    }


    //------------------------------autorefresh method--------------------------------------

    public String random_generator(){

        int min = 9500;
        int max = 9800;

        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;

        String random_gen = "DLC"+sessionManager.getSavedUserId();

        return random_gen;
    }

    public void createlink(){
        Log.e("main", "create link ");
        DynamicLink dynamicLink = FirebaseDynamicLinks.getInstance().createDynamicLink()
                .setLink(Uri.parse("https://play.google.com/store/apps/details?id=com.theLearningcLub&referrer=utm_source%3Dgoogle%26utm_medium%3Dcpc%26anid%3Dadmob"))
                .setDynamicLinkDomain("thelearningclub.page.link")
                // Open links with this app on Android
                .setAndroidParameters(new DynamicLink.AndroidParameters.Builder().build())
                // Open links with com.example.ios on iOS
                //.setIosParameters(new DynamicLink.IosParameters.Builder("com.example.ios").build())
                .setGoogleAnalyticsParameters(new DynamicLink.GoogleAnalyticsParameters.Builder()
                        .setSource("googleplay")
                        .setMedium(random_generator())
                        .setCampaign("example-promo")
                        .build())
                .buildDynamicLink();
//click -- link -- google play store -- inistalled/ or not  ----
        Uri dynamicLinkUri = dynamicLink.getUri();
        Log.e("main", "  Long refer "+ dynamicLink.getUri());
        //   https://referearnpro.page.link?apn=blueappsoftware.referearnpro&link=https%3A%2F%2Fwww.blueappsoftware.com%2F
        // apn  ibi link
        // manuall link
        String sharelinktext  = "https://thelearningclub.page.link/?"+
                "link=https://play.google.com/store/apps/details?id=com.theLearningcLub&referrer="+random_generator()+
                "&apn="+ "com.theLearningcLub"+
                "&st="+"My Refer Link"+
                "&sd="+"Reward Coins 20"+
                "&si="+"https://play-lh.googleusercontent.com/ATTM2uOigV4LDkEfIhjURYPaaFUwEFNdi1ykoyNDo7dtO0sQtB7ZcLwLlutQGBfsaRk=s180-rw";
        // shorten the link
        Task<ShortDynamicLink> shortLinkTask = FirebaseDynamicLinks.getInstance().createDynamicLink()
                //.setLongLink(dynamicLink.getUri())
                .setLongLink(Uri.parse(sharelinktext))  // manually
                .buildShortDynamicLink()
                .addOnCompleteListener(mContext, new OnCompleteListener<ShortDynamicLink>() {
                    @Override
                    public void onComplete(@NonNull Task<ShortDynamicLink> task) {
                        if (task.isSuccessful()) {
                            // Short link created
                            Uri shortLink = Objects.requireNonNull(task.getResult()).getShortLink();
                            Uri flowchartLink = task.getResult().getPreviewLink();
                            assert shortLink != null;
                            Log.e("main ", "short link "+ shortLink);
                            //--------------------------------------------------
                            String share_link = "https://play.google.com/store/apps/details?id=com.theLearningcLub&referrer="+random_generator();
                            //--------------------------------------------------
                            // share app dialog
                            Intent intent = new Intent();
                            intent.setAction(Intent.ACTION_SEND);
                            intent.putExtra(Intent.EXTRA_TEXT,  share_link);
//                            intent.putExtra(Intent.EXTRA_TEXT,  shortLink.toString());
                            intent.setType("text/plain");
                            startActivity(intent);
                        } else {
                            // Error
                            // ...
                            Log.e("main", " error "+task.getException() );
                        }

                        System.out.println("dynamic links>>> ");
                    }
                });
    }
}