package com.theLearningcLub;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.theLearningcLub.apiClint.AllUrl;
import com.theLearningcLub.databinding.ActivityProfileBinding;
import com.theLearningcLub.utils.BaseFragment;
import com.theLearningcLub.utils.CommonFunction;
import com.theLearningcLub.utils.MultipartUtility;
import com.theLearningcLub.utils.Utility;
import com.theLearningcLub.utils.WebRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ProfileActivity extends BaseFragment {

    ActivityProfileBinding activityProfileBinding;

    String s_state_id,s_district_Id,s_state,s_district;
    ArrayList<String> statearr = new ArrayList<>();
    ArrayList<String> stateIDarr = new ArrayList<>();
    ArrayList<String> districtarr = new ArrayList<>();
    ArrayList<String> districtIDarr = new ArrayList<>();

    //------------------------file code---------------------------------
    String encodeimg1 = "",cartkey;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    String userChoosenTask;
    File uploadFileI;

    String s,status,s_first,s_last,s_email,s_mobile,s_image,s_parent_mobile,s_address,message,
            responseupdate;
    String s_edit_firstname,s_edit_lastname,s_edit_email,s_edit_mobile,s_edit_parent_no,
            s_edit_address,s_cartcount;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        activityProfileBinding = ActivityProfileBinding.inflate(inflater, container, false);
        Objects.requireNonNull(getActivity()).getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);

        HomeActivity.tvUserHello.setText(getResources().getString(R.string.my_profile));
        HomeActivity.iv_menu_main.setVisibility(View.GONE);
        HomeActivity.ivBack.setVisibility(View.VISIBLE);
//        HomeActivity.ivNotification.setVisibility(View.GONE);
        HomeActivity.rlCart.setVisibility(View.GONE);

        try {
            s = sessionManager.getSavedCART_NO();

            cartkey = "1";
            new CartCountTask().execute();

            setstate_district();

            Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(), R.drawable.ic_profile_image_1);
            Bitmap circularBitmap = ImageConverter.getRoundedCornerBitmap(bitmap, 100);

            activityProfileBinding.civProfileImage.setImageBitmap(circularBitmap);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        viewProfileApi();

        activityProfileBinding.civProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        activityProfileBinding.tvUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                s_edit_email = activityProfileBinding.etEmail.getText().toString();
                s_edit_address = activityProfileBinding.etAddress.getText().toString();
                s_edit_mobile = activityProfileBinding.etMobileNumber.getText().toString();
                s_edit_parent_no = activityProfileBinding.etParentsContact.getText().toString();
                s_edit_firstname = activityProfileBinding.etFirstName.getText().toString();
                s_edit_lastname = activityProfileBinding.etLastName.getText().toString();

                if (s_edit_firstname.isEmpty()) {
                    activityProfileBinding.etFirstName.setError("Please Enter First Name.");
                }
                if (s_edit_lastname.isEmpty()) {
                    activityProfileBinding.etLastName.setError("Please Enter Last Name.");
                }
                if (s_edit_email.isEmpty()) {
                    activityProfileBinding.etEmail.setError("Please Enter Email.");
                }
                if (!s_edit_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[com]+")) {
                    activityProfileBinding.etEmail.setError("Invalid Email Address");
                }
                if (s_edit_address.isEmpty()) {
                    activityProfileBinding.etAddress.setError("Please Enter Address.");
                }
                if (s_edit_mobile.isEmpty()) {
                    activityProfileBinding.etMobileNumber.setError("Please Enter Mobile Number.");
                }
                if (s_edit_mobile.length() < 10) {
                    activityProfileBinding.etMobileNumber.setError("Please Enter Valid Mobile Number.");
                }
                if (s_edit_parent_no.isEmpty()) {
                    activityProfileBinding.etParentsContact.setError("Please Enter Parent Mobile Number.");
                }
                if (s_edit_parent_no.length() > 10) {
                    Toast.makeText(mContext, "parent", Toast.LENGTH_SHORT).show();
                    activityProfileBinding.etParentsContact.setError("Please Enter Valid Parent Mobile Number.");
                }
                if (s_state.equals("Select state")) {
                    Toast.makeText(mContext, "Please select State", Toast.LENGTH_SHORT).show();
                }
                if (s_district.equals("Select District")) {
                    Toast.makeText(mContext, "Please select District", Toast.LENGTH_SHORT).show();
                }

                if (s_edit_firstname.isEmpty()) {
                    activityProfileBinding.etFirstName.setError("Please Enter First Name.");
                } else if (s_edit_lastname.isEmpty()) {
                    activityProfileBinding.etLastName.setError("Please Enter Last Name.");
                } else if (s_edit_email.isEmpty()) {
                    activityProfileBinding.etEmail.setError("Please Enter Email.");
                } else if (!s_edit_email.matches("[a-zA-Z0-9._-]+@[a-z]+.[com]+")) {
                    activityProfileBinding.etEmail.setError("Invalid Email Address");
                } else if (s_edit_address.isEmpty()) {
                    activityProfileBinding.etAddress.setError("Please Enter Address.");
                } else if (s_edit_mobile.isEmpty()) {
                    activityProfileBinding.etMobileNumber.setError("Please Enter Mobile Number.");
                } else if (s_edit_mobile.length() < 10) {
                    activityProfileBinding.etMobileNumber.setError("Please Enter Valid Mobile Number.");
                } else if (s_edit_parent_no.isEmpty()) {
                    activityProfileBinding.etParentsContact.setError("Please Enter Parent Mobile Number.");
                } else if (s_edit_parent_no.length() > 10) {
                    Toast.makeText(mContext, "parent", Toast.LENGTH_SHORT).show();
                    activityProfileBinding.etParentsContact.setError("Please Enter Valid Parent Mobile Number.");
                } else if (s_state.equals("Select state")) {
                    Toast.makeText(mContext, "Please select State", Toast.LENGTH_SHORT).show();
                } else if (s_district.equals("Select District")) {
                    Toast.makeText(mContext, "Please select District", Toast.LENGTH_SHORT).show();
                } else {
                    onClickSignUp();
                }
            }
        });

        return activityProfileBinding.getRoot();
    }

    private void viewProfileApi(){
        if (isConnectingToInternet(mContext)) {
            showProgressDialog();
            RequestQueue queue = Volley.newRequestQueue(mContext);
            // Request a string response from the provided URL.
            StringRequest stringRequest = new StringRequest(Request.Method.POST, AllUrl.ViewProfileApi,
                    response -> {
                        // Display the first 500 characters of the response string.
                        Log.d("INFO", response);
//                                Log.e(TAG, "onResponse:login "+response );
                        System.out.println("viewProfileApi response>>>>>>>>>>>>>>>" + response);
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            status = jsonObject.getString("status");

                            JSONArray contacts = jsonObject.getJSONArray("data");
                            for (int i = 0; i < contacts.length(); i++) {
                                JSONObject c = contacts.getJSONObject(i);

                                s_first = c.getString("user_fname");
                                s_last = c.getString("user_lname");
                                s_email = c.getString("user_email");
                                s_mobile = c.getString("user_contact");
                                s_image = c.getString("user_image");
                                s_parent_mobile = c.getString("parent_cont");
                                s_address = c.getString("address");
                                s_state_id = c.getString("u_state");
                                s_district_Id = c.getString("u_district");
                                s_state = c.getString("state_name");
                                s_district = c.getString("dist_name");
                            }

                            if(status.equals("true")) {
                                activityProfileBinding.etFirstName.setText(s_first);
                                activityProfileBinding.etLastName.setText(s_last);
                                activityProfileBinding.etMobileNumber.setText(s_mobile);
                                activityProfileBinding.etEmail.setText(s_email);

                                activityProfileBinding.etAddress.setText(s_address);
                                activityProfileBinding.etParentsContact.setText(s_parent_mobile);

                                activityProfileBinding.tvUserName.setText(s_first+"\t"+s_last);
                                activityProfileBinding.tvUserEmail.setText(s_email);

                                HomeActivity.tvStudentName.setText(s_first + "\t" + s_last);
                                HomeActivity.tvStudentNumber.setText(s_mobile);

                                if (s_image!=null&&!s_image.equalsIgnoreCase("")) {
                                    s_image = s_image.replaceAll("\\\\", "");

                                    System.out.println("imageUrlimageUrlimageUrl"+s_image);

                                    Glide.with(mContext).load(s_image.replace("http://","https://")).placeholder(R.drawable.ic_profile_image_1).into(activityProfileBinding.civProfileImage);

                                    Glide.with(mContext).load(s_image.replace("http://","https://"))
                                            .placeholder(R.drawable.ic_drawer_home).into(HomeActivity.civUserImage);

                                    Glide.with(mContext).load(s_image.replace("http://","https://"))
                                            .placeholder(R.drawable.ic_drawer_home).into(HomeActivity.iv_menu_main);
                                }

                                for (int i = 0; i < stateIDarr.size(); i++) {
                                    if (stateIDarr.get(i).equals(s_state_id)){
                                        System.out.println("s_state_id>>> "+s_state_id+"   >>>>>  "+stateIDarr.get(i));
                                        activityProfileBinding.spState.setSelection(i);
                                    }
                                }

                                for (int i = 0; i < districtIDarr.size(); i++) {
                                    if (districtIDarr.get(i).equals(s_district_Id)){
                                        System.out.println("s_state_id>>> "+s_district_Id+"   >>>>>  "+districtIDarr.get(i));
                                        activityProfileBinding.spCity.setSelection(i);
                                    }
                                }
                            }

                            hideProgressDialog();

                        } catch (JSONException e) {
                            e.printStackTrace();
                            hideProgressDialog();
                        }

                    }, error -> {
                System.out.println("viewProfileApi error>>>>>>>>>>>>>."+error);
                hideProgressDialog();
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<>();
                    params.put("user_id",sessionManager.getSavedUserId());
                    params.put("key", sessionManager.getLoginkey());
                    System.out.println("viewProfileApi params>>>>>>>>>>>>>>" + params);
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

    class ImageUploadTask extends AsyncTask<Void, Void, String> {

        protected void onPreExecute() {
            showProgressDialog();
        }

        protected String doInBackground(Void... params) {
            try {
                String charset = "UTF-8";
                //String requestURL = "http://padobado.com/custom_service/update_profile.php";
                MultipartUtility multipart = new MultipartUtility(AllUrl.edit_profileApi, charset);
                String sid = null;

                if(uploadFileI==null){

                    multipart.addFormField("user_id",sessionManager.getSavedUserId());
                    multipart.addFormField("first_name",s_edit_firstname);
                    multipart.addFormField("last_name",s_edit_lastname);
                    multipart.addFormField("email",s_edit_email);
                    multipart.addFormField("contact",s_edit_mobile);
                    multipart.addFormField("parent_number",s_edit_parent_no);
                    multipart.addFormField("address",s_edit_address);
                    multipart.addFormField("key", sessionManager.getLoginkey());
                    multipart.addFormField("u_state", s_state_id);
                    multipart.addFormField("u_district", s_district_Id);

                    System.out.println(" uploadFileI>>>>>>>>>>>>> ** "+uploadFileI+" "+sessionManager.getSavedUserId()+"  "+sessionManager.getLoginkey());

                }else{
                    System.out.println("image >>>>>>>>>>>>>>"+encodeimg1);
                    uploadFileI = new File(encodeimg1);
                    multipart.addFormField("user_id",sessionManager.getSavedUserId());
                    multipart.addFormField("first_name",s_edit_firstname);
                    multipart.addFormField("last_name",s_edit_lastname);
                    multipart.addFormField("email",s_edit_email);
                    multipart.addFormField("contact",s_edit_mobile);
                    multipart.addFormField("parent_number",s_edit_parent_no);
                    multipart.addFormField("address",s_edit_address);
                    multipart.addFormField("key", sessionManager.getLoginkey());
                    multipart.addFormField("u_state", s_state_id);
                    multipart.addFormField("u_district", s_district_Id);

                    multipart.addFilePart("image", uploadFileI);

                }
                List<String> response = multipart.finish();

                for (String line : response) {
                    System.out.println("ye hai   " + line);
                    responseupdate=line;
                }
                try{
                    JSONObject jsonObject=new JSONObject(responseupdate);
                    JSONObject jsonObject1=jsonObject.getJSONObject("response");
                    status = jsonObject1.getString("status");
                    message = jsonObject1.getString("msg");

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } catch (IOException ex) {
                System.err.println(ex);
            }
            return null;
        }

        protected void onPostExecute(String result) {
            hideProgressDialog();
            if(status.contains("true")){
                CommonFunction.showToastSingle(mContext, "Profile Updated Successfully");
            }else{
                viewProfileApi();
            }
        }
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Utility.MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (userChoosenTask.equals("Take Photo"))
                    cameraIntent();
                else if (userChoosenTask.equals("Choose from Library"))
                    galleryIntent();
            } else {
                //code for deny
                System.out.println("******code for deny");
            }
        }
    }

    private void selectImage() {
        final CharSequence[] items = {"Take Photo", "Choose from Library",
                "Cancel"};

        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("Upload Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {
                boolean result = Utility.checkPermission(mContext);

                if (items[item].equals("Take Photo")) {
                    userChoosenTask = "Take Photo";
                    if (result)
                        // System.out.println("TAKE PHOTO CLICKED");
                        cameraIntent();

                } else if (items[item].equals("Choose from Library")) {
                    userChoosenTask = "Choose from Library";
                    if (result)
                        // System.out.println("GALLERY OPEN");
                        galleryIntent();

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();

    }

    private void galleryIntent() {
        System.out.println("GALLERY OPEN 22");

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);//
        startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
    }

    private void cameraIntent(){
        try{
            System.out.println("CAMERA OPEN 22");
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            // System.out.println("permission allow >>>>>>>>>>>>>>>>>>");
            if (requestCode == SELECT_FILE) {
                try{
                    onSelectFromGalleryResultProfile(data);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }else if (requestCode == REQUEST_CAMERA) {
                try {
                    onCaptureImageResultProfile(data);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void onCaptureImageResultProfile(Intent data) {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(),
                System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Uri tempUri = getImageUri(mContext,thumbnail);

        encodeimg1= getRealPathFromURI(tempUri);

        //  System.out.println("tempUri" + tempUri);;
        activityProfileBinding.civProfileImage.setImageBitmap(thumbnail);

    }

    private void onSelectFromGalleryResultProfile(Intent data) {

        Bitmap bm=null;
        if (data != null) {
            try {
                bm = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), data.getData());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        BitmapDrawable d= new BitmapDrawable(bm);
//define bounds for your drawable
        int left =0;
        int top = 0;
        int right=40;
        int bottom=40;

        Rect r = new Rect(left,top,right,bottom);
//set the new bounds to your drawable
        d.setBounds(r);
        Uri tempUri = getImageUri(mContext,bm);

        encodeimg1 = getRealPathFromURI(tempUri);

        // System.out.println("ProfilePicPath  " + encodeimg1);
        encodeimg1= getRealPathFromURI(data.getData());
        activityProfileBinding.civProfileImage.setImageDrawable(d);
    }

    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "IMG_" + Calendar.getInstance().getTime(), null);
        return Uri.parse(path);
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().getContentResolver().query(contentURI, projection, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = 0;
            idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }

        return result;

    }
    //    submit btn

    public void onClickSignUp() {
        try{
            BitmapDrawable drawable = (BitmapDrawable) activityProfileBinding.civProfileImage.getDrawable();
            Bitmap bitmap = drawable.getBitmap();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            if (bitmap != null) {
//                    Log.e("tag", "uplading,..");
                bitmap.compress(Bitmap.CompressFormat.JPEG,80, baos);

                Uri tempUri = getImageUri(mContext, bitmap);

                encodeimg1= getRealPathFromURI(tempUri);

                uploadFileI = new File(encodeimg1);
                //  System.out.println("currentimage>>>>>>>>>>>>>>>>   "+encodeimg1);

            } else {
                encodeimg1 = "null";
            }


            new ImageUploadTask().execute();

        }catch (Exception e){
            e.printStackTrace();
            new ImageUploadTask().execute();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.CUPCAKE)
    class CartCountTask extends AsyncTask<String, String, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest=new WebRequest();
            HashMap<String, String> hashMap=new HashMap<>();
            hashMap.put("user_id",sessionManager.getSavedUserId());
            hashMap.put("key", sessionManager.getLoginkey());

            String response = webRequest.makeWebServiceCall(AllUrl.count_cartApi,2,hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status = jsonObject.getString("status");
                s_cartcount = jsonObject.getString("cart_count");
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if(s_cartcount.isEmpty()){
                HomeActivity.badgeCart.setVisibility(View.GONE);
            }else if(s_cartcount.equals("0")){
                HomeActivity.badgeCart.setVisibility(View.GONE);
            }else {
                HomeActivity.badgeCart.setVisibility(View.VISIBLE);
                HomeActivity.badgeCart.setText(s_cartcount);
            }
        }
    }

    private void setstate_district() {

        new StateAsyncTask().execute();

        districtarr.add("Select District");
        districtIDarr.add("");

        ArrayAdapter districtadapter = new ArrayAdapter(mContext,R.layout.register_profile_layout,districtarr);
        districtadapter.setDropDownViewResource(R.layout.profile_layout);
        activityProfileBinding.spCity.setAdapter(districtadapter);

        activityProfileBinding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                s_district=districtarr.get(i);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    //-------------------------------------State Asynctask--------------------------------------

    class StateAsyncTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("flag","state");
            String response = webRequest.makeWebServiceCall(AllUrl.state_district_view_Api,2,hashMap);
            System.out.println("state_district_view Response >>>>>>>>>>>>>>>>>>>>>>>"+response);
            System.out.println("state_district_view parameter >>>>>>>>>>>>>>>>>>>>>>>"+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("body");
                statearr.add("Select state");
                stateIDarr.add("");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    String state_id = c.getString("state_id");
                    String state_name = c.getString("state_name");

                    statearr.add(state_name);
                    stateIDarr.add(state_id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayAdapter stateadapter = new ArrayAdapter(mContext,R.layout.register_profile_layout,statearr);
            stateadapter.setDropDownViewResource(R.layout.profile_layout);
            activityProfileBinding.spState.setAdapter(stateadapter);

            activityProfileBinding.spState.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                    System.out.println("s_state_id>>>>> "+s_state_id);
                    s_state=statearr.get(i);
                    s_state_id=stateIDarr.get(i);
                    if (i==0){

                    }else {
                        new DistrictAsyncTask().execute();
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
            viewProfileApi();
        }
    }

    // ------------------------------------Select District ----------------------------------------

    class DistrictAsyncTask extends AsyncTask<String,String,String>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            districtarr.clear();
            districtIDarr.clear();
        }

        @Override
        protected String doInBackground(String... strings) {
            WebRequest webRequest=new WebRequest();
            HashMap<String,String> hashMap=new HashMap<>();
            hashMap.put("flag","district");
            hashMap.put("state",s_state_id);
            String response = webRequest.makeWebServiceCall(AllUrl.state_district_view_Api,2,hashMap);
            System.out.println("state_district_view Response >>>>>>>>>>>>>>>>>>>>>>>"+response);
            System.out.println("state_district_view parameter >>>>>>>>>>>>>>>>>>>>>>>"+hashMap);

            try {
                JSONObject jsonObject=new JSONObject(response);
                status = jsonObject.getString("status");
                JSONArray jsonArray=jsonObject.getJSONArray("body");
                districtarr.add("Select District");
                districtIDarr.add("");
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject c = jsonArray.getJSONObject(i);

                    String dist_id = c.getString("dist_id");
                    String dist_name = c.getString("dist_name");

                    districtarr.add(dist_name);
                    districtIDarr.add(dist_id);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            ArrayAdapter districtadapter = new ArrayAdapter(mContext,R.layout.register_profile_layout,districtarr);
            districtadapter.setDropDownViewResource(R.layout.profile_layout);
            activityProfileBinding.spCity.setAdapter(districtadapter);

            for (int i = 0; i <districtIDarr.size(); i++) {
                if (districtIDarr.get(i).equals(s_district_Id)){
                    s_district = districtarr.get(i);
                    s_district_Id = districtIDarr.get(i);
                    activityProfileBinding.spCity.setSelection(i);
                }else {
                    activityProfileBinding.spCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            s_district=districtarr.get(i);
                            s_district_Id=districtIDarr.get(i);
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }
        }
    }
}