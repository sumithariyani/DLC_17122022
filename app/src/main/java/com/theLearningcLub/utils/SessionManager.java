package com.theLearningcLub.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.theLearningcLub.model.otp.OtpModel;

public class SessionManager {

    // Context
    private final Context _context;

    // Shared Preferences reference
    private static SharedPreferences preferences;

    // Editor reference for Shared preferences
    private final SharedPreferences.Editor editor;

    // Shared pref  file name
    private static final String PREF_NAME = "DlcPreferences";
    private static final String IS_LOGIN = "IsLoggedIn";

    private static final String KEY_USERID = "id";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_IS_INTRO = "isIntro";
    private static final String KEY_PACKID = "packid";
    private static final String KEY_PACKID2 = "packid2";
    private static final String KEY_CART_NO = "cartno";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NAME = "name";
    private static final String KEY_UPDATE_ID = "updateid";
    private static final String KEY_DATA = "data";
    private static final String KEY_STATUS = "status";
    private static final String KEY_NASWER_ID = "answer_id";
    private static final String KEY_CORRECT_ID = "correct_id";
    private static final String KEY_RELOAD = "reload";
    private static final String KEY_NOT_ANSWER = "notanswer";
    private static final String KEY_FB_ID = "fbid";
    private static final String KEY_NO_KEY = "nokey";
    private static final String KEU_NO_TITLE = "notitle";
    private static final String KEY_NO_MSG = "nomsg";
    private static final String KEY_NO_TIME = "notime";
    private static final String KEY_TOKEN = "token";
    private static final String KEY_PACK_NAME = "packname";
    private static final String KEY_PACK_NAME2 = "packname2";
    private static final String KEY_CONTACT_SYNC = "contactsyanc";
    private static final String KEY_ISCHECK = "ischeck";
    private static final String KEY_LOGINKEY = "loginkey";
    private static final String KEY_ISCLICK = "isclick";
    private static final String KEY_FRAGMENTCLICK = "fragmentclick";
    private static final String KEY_SELECTEDFRAG = "selectedfrag";
    private static final String KEY_VERIFIEDSTATUS = "verify";
    private static final String KEY_moretest = "mt";
    private static final String KEY_ransactioncancel = "trscan";
    private static final String KEY_QID = "QID";
    private static final String KEY_CONTACT = "contact";
    private static final String KEY_OTP = "otp";
    private static final String KEY_REFER_CODE = "refercode";
    private static final String KEY_ISAGENT = "isagent";
    private static final String KEY_CONTACT_C  = "cont";

    int PRIVATE_MODE = 0;

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";

    private static final String IS_FIRST_TIME_ = "IsFirstTime";

    // Constructor
    @SuppressLint("CommitPrefEdits")
    public SessionManager(Context context) {
        this._context = context;
        int PRIVATE_MODE = 0;
        preferences = context.getApplicationContext().getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public OtpModel getLoginModel() {
        Gson gson = new Gson();
        String json = preferences.getString("DataUserModel", "");
        return gson.fromJson(json, OtpModel.class);
    }

    public void setLoginModel(OtpModel userModel) {
        Gson gson = new Gson();
        String json = gson.toJson(userModel);
        editor.putString("DataUserModel", json);
        editor.apply();
    }

    // Check for login
    public boolean islogin() {
        return preferences.getBoolean(IS_LOGIN, false);
    }

    public void setLoginSession() {
        editor.putBoolean(IS_LOGIN, true);
        editor.commit();
    }

//    public void CheckLogin() {
//        if (this.islogin()) {
//            Intent in = new Intent(_context, DashBoardActivity.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            _context.startActivity(in);
//        } else {
//            Intent in = new Intent(_context, LoginActivity.class);
//            in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                    | Intent.FLAG_ACTIVITY_NEW_TASK);
//            _context.startActivity(in);
//        }
//    }

//    public void logoutUser() {
//        editor.clear();
//        editor.commit();
//        Intent intent = new Intent(_context, LoginActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP
//                | Intent.FLAG_ACTIVITY_NEW_TASK);
//        _context.startActivity(intent);
//    }

    public String getIsAgent() {
        return preferences.getString(KEY_ISAGENT, "");
    }


    public void setIsAgent(String isAgent) {
        editor.putString(KEY_ISAGENT, isAgent);
        editor.commit();
    }



    public String getOtp() {
        return preferences.getString(KEY_OTP, "");
    }


    public void setOtp(String otp) {
        editor.putString(KEY_OTP, otp);
        editor.commit();
    }




    public String getReferCode() {
        return preferences.getString(KEY_REFER_CODE, "");
    }


    public void setReferCode(String referCode) {
        editor.putString(KEY_REFER_CODE, referCode);
        editor.commit();
    }


    public String getselectedfrag() {
        return preferences.getString(KEY_SELECTEDFRAG, "");
    }


    public void setselectedfrag(String selectedfrag) {
        editor.putString(KEY_SELECTEDFRAG, selectedfrag);
        editor.commit();
    }


    public String getContact() {
        return preferences.getString(KEY_CONTACT, "");
    }


    public void setContact(String contact) {
        editor.putString(KEY_CONTACT, contact);
        editor.commit();
    }



    public String getfragmentclick() {
        return preferences.getString(KEY_FRAGMENTCLICK, "");
    }


    public void setfragmentclick(String fragmentclick) {
        editor.putString(KEY_FRAGMENTCLICK, fragmentclick);
        editor.commit();
    }

    public String getclick() {
        return preferences.getString(KEY_ISCLICK, "");
    }


    public void setclick(String cart_no) {
        editor.putString(KEY_ISCLICK, cart_no);
        editor.commit();
    }

    public String getVerifystatus() {
        return preferences.getString(KEY_VERIFIEDSTATUS, "");
    }


    public void setVerifystatus(String cart_no) {
        editor.putString(KEY_VERIFIEDSTATUS, cart_no);
        editor.commit();
    }
    public String getQID() {
        return preferences.getString(KEY_QID, "");
    }


    public void setQID(String Qno) {
        editor.putString(KEY_QID, Qno);
        editor.commit();
    }

    public String getKEY_moretest() {
        return preferences.getString(KEY_moretest, "");
    }

    public void setKEY_moretest(String cart_no) {
        editor.putString(KEY_moretest, cart_no);
        editor.commit();
    }

//====================

    public String getKEY_transcan() {
        return preferences.getString(KEY_ransactioncancel, "");
    }

    public void setKEY_transcan(String trs) {
        editor.putString(KEY_ransactioncancel, trs);
        editor.commit();
    }

    public void setContactSyanc(boolean isLogin) {
        editor.putBoolean(KEY_CONTACT_SYNC, isLogin);
        editor.commit();
    }

    public boolean getContactSyanc() {
        return preferences.getBoolean(KEY_CONTACT_SYNC, true);
    }

    public String getPackname() {
        return preferences.getString(KEY_PACK_NAME, "");
    }

    public void setPackname(String cart_no) {
        editor.putString(KEY_PACK_NAME, cart_no);
        editor.commit();
    }

    public String getPackname2() {
        return preferences.getString(KEY_PACK_NAME2, "");
    }

    public void setPackname2(String cart_no) {
        editor.putString(KEY_PACK_NAME2, cart_no);
        editor.commit();
    }

    public String getLoginkey() {
        return preferences.getString(KEY_LOGINKEY, "");
    }

    public void setLoginkey(String cart_no) {
        editor.putString(KEY_LOGINKEY, cart_no);
        editor.commit();
    }

    //------------------------------------------------------------------------------------------

    public String getIscheck() {
        return preferences.getString(KEY_ISCHECK, "");
    }

    public void setIscheck(String cart_no) {
        editor.putString(KEY_ISCHECK, cart_no);
        editor.commit();
    }

    //------------------------------------------------------------------------------------------

    public String getKeyToken() {
        return preferences.getString(KEY_TOKEN, "");
    }

    public void setKeyToken(String cart_no) {
        editor.putString(KEY_TOKEN, cart_no);
        editor.commit();
    }

    public String getnokey() {
        return preferences.getString(KEY_NO_KEY, "");
    }

    public void setnokey(String cart_no) {
        editor.putString(KEY_NO_KEY, cart_no);
        editor.commit();
    }

    public String getnotitle() {
        return preferences.getString(KEU_NO_TITLE, "");
    }

    public void setnotitle(String cart_no) {
        editor.putString(KEU_NO_TITLE, cart_no);
        editor.commit();
    }

    public String getnotime() {
        return preferences.getString(KEY_NO_TIME, "");
    }

    public void setnotime(String cart_no) {
        editor.putString(KEY_NO_TIME, cart_no);
        editor.commit();
    }

    public String getnomsg() {
        return preferences.getString(KEY_NO_MSG, "");
    }

    public void setnomsg(String cart_no) {
        editor.putString(KEY_NO_MSG, cart_no);
        editor.commit();
    }

    public String getFBID() {
        return preferences.getString(KEY_FB_ID, "");
    }

    public void setFBID(String cart_no) {
        editor.putString(KEY_FB_ID, cart_no);
        editor.commit();
    }

    public void setSavedNotAnswer(String packId) {
        editor.putString(KEY_NOT_ANSWER, packId);
        editor.commit();
    }

    public String getSavedNotAnswer() {
        return preferences.getString(KEY_NOT_ANSWER, "");
    }

    public void setSavedPackId(String packId) {
        editor.putString(KEY_PACKID, packId);
        editor.commit();
    }

    public String getSavedreload() {
        return preferences.getString(KEY_RELOAD, "");
    }

    public void setSavedreload(String cart_no) {
        editor.putString(KEY_RELOAD, cart_no);
        editor.commit();
    }

    public String getSavedCART_NO() {
        return preferences.getString(KEY_CART_NO, "");
    }

    public void setSavedCART_NO(String cart_no) {
        editor.putString(KEY_CART_NO, cart_no);
        editor.commit();
    }

    public String getSavedCORRECT_ID() {
        return preferences.getString(KEY_CORRECT_ID, "");
    }

    public void setSavedCORRECT_ID(String cart_no) {
        editor.putString(KEY_CORRECT_ID, cart_no);
        editor.commit();
    }

    public String getSavedSTATUS() {
        return preferences.getString(KEY_STATUS, "");
    }

    public void setSavedSTATUS(String cart_no) {
        editor.putString(KEY_STATUS, cart_no);
        editor.commit();
    }

    public String getSavedANSWER_ID() {
        return preferences.getString(KEY_NASWER_ID, "");
    }

    public void setSavedANSWER_ID(String cart_no) {
        editor.putString(KEY_NASWER_ID, cart_no);
        editor.commit();
    }

    public String getSavedDATA() {
        return preferences.getString(KEY_DATA, "");
    }

    public void setSavedDATA(String data) {
        editor.putString(KEY_DATA, data);
        editor.commit();
    }

    public String getSavedUPDATE_ID() {
        return preferences.getString(KEY_UPDATE_ID, "");
    }

    public void setSavedUPDATE_ID(String update_id) {
        editor.putString(KEY_UPDATE_ID, update_id);
        editor.commit();
    }

    public String getSavedNAME() {
        return preferences.getString(KEY_NAME, "");
    }

    public void setSavedNAME(String name) {
        editor.putString(KEY_NAME, name);
        editor.commit();
    }

    public String getSavedEMAIL() {
        return preferences.getString(KEY_EMAIL, "");
    }

    public void setSavedEMAIL(String email) {
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public String getSavedPackId() {
        return preferences.getString(KEY_PACKID, "");
    }

    public void setSavedPackId2(String packId) {
        editor.putString(KEY_PACKID2, packId);
        editor.commit();
    }

    public String getSavedPackId2() {
        return preferences.getString(KEY_PACKID2, "");
    }

    public void setSavedUserId(String userid) {
        editor.putString(KEY_USERID, userid);
        editor.commit();
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public void setUserLoggedIn(boolean isLogin) {
        editor.putBoolean(KEY_IS_LOGIN, isLogin);
        editor.commit();
    }

    public boolean isUserLogin() {
        return preferences.getBoolean(KEY_IS_LOGIN, false);
    }

    public String getSavedUserId() {
        return preferences.getString(KEY_USERID, "");
    }

    // shared preferences mode

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTimeLaunch() {
        return preferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public void setIntro(boolean value) {
        editor.putBoolean(KEY_IS_INTRO, value);
        editor.commit();
    }

    public boolean getIntro() {
        return preferences.getBoolean(KEY_IS_INTRO, true);
    }

    public void setFirstTime(boolean isFirstTime) {
        editor.putBoolean(IS_FIRST_TIME_, isFirstTime);
        editor.commit();
    }

    public boolean isFirstTime() {
        return preferences.getBoolean(IS_FIRST_TIME_, true);
    }

    public void setcontact(String packId) {
        editor.putString(KEY_CONTACT_C, packId);
        editor.commit();
    }
    public String getcontact() {
        return preferences.getString(KEY_CONTACT_C, "");
    }

}