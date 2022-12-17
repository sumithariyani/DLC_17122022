package com.theLearningcLub.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by HP on 5/15/2019.
 */

public class PrefManagerSecond2 {
    SharedPreferences pref2;
    SharedPreferences.Editor editor2;
    Context _context2;

    private static final String KEY_USERID = "id";
    private static final String KEY_IS_LOGIN = "isLogin";
    private static final String KEY_IS_INTRO = "isIntro";
    private static final String KEY_PACKID = "packid";
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
    private static final String KEY_CONTACT_SYNC = "contactsyanc";
    private static final String KEY_CONTACT  = "cont";
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "android-welcome_Second";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch_second";

    private static final String IS_FIRST_TIME_ = "IsFirstTime_second";

    public void setContactSyanc(boolean isLogin) {
        editor2.putBoolean(KEY_CONTACT_SYNC, isLogin);
        editor2.commit();
    }

    public boolean getContactSyanc() {
        return pref2.getBoolean(KEY_CONTACT_SYNC, true);
    }



    public void setcontact(String packId) {
        editor2.putString(KEY_CONTACT, packId);
        editor2.commit();
    }
    public String getcontact() {
        return pref2.getString(KEY_CONTACT, "");
    }

    public void setSavedNotAnswer(String packId) {
        editor2.putString(KEY_NOT_ANSWER, packId);
        editor2.commit();
    }
    public String getSavedNotAnswer() {
        return pref2.getString(KEY_NOT_ANSWER, "");
    }



    public void setSavedPackId(String packId) {
        editor2.putString(KEY_PACKID, packId);
        editor2.commit();
    }
    public String getSavedreload() {
        return pref2.getString(KEY_RELOAD, "");
    }


    public void setSavedreload(String cart_no) {
        editor2.putString(KEY_RELOAD, cart_no);
        editor2.commit();
    }

    public String getSavedCART_NO() {
        return pref2.getString(KEY_CART_NO, "");
    }


    public void setSavedCART_NO(String cart_no) {
        editor2.putString(KEY_CART_NO, cart_no);
        editor2.commit();
    }
    public String getSavedCORRECT_ID() {
        return pref2.getString(KEY_CORRECT_ID, "");
    }


    public void setSavedCORRECT_ID(String cart_no) {
        editor2.putString(KEY_CORRECT_ID, cart_no);
        editor2.commit();
    }

    public String getSavedSTATUS() {
        return pref2.getString(KEY_STATUS, "");
    }


    public void setSavedSTATUS(String cart_no) {
        editor2.putString(KEY_STATUS, cart_no);
        editor2.commit();
    }
    public String getSavedANSWER_ID() {
        return pref2.getString(KEY_NASWER_ID, "");
    }


    public void setSavedANSWER_ID(String cart_no) {
        editor2.putString(KEY_NASWER_ID, cart_no);
        editor2.commit();
    }

    public String getSavedDATA() {
        return pref2.getString(KEY_DATA, "");
    }


    public void setSavedDATA(String data) {
        editor2.putString(KEY_DATA, data);
        editor2.commit();
    }

    public String getSavedUPDATE_ID() {
        return pref2.getString(KEY_UPDATE_ID, "");
    }


    public void setSavedUPDATE_ID(String update_id) {
        editor2.putString(KEY_UPDATE_ID, update_id);
        editor2.commit();
    }


    public String getSavedNAME() {
        return pref2.getString(KEY_NAME, "");
    }


    public void setSavedNAME(String name) {
        editor2.putString(KEY_NAME, name);
        editor2.commit();
    }


    public String getSavedEMAIL() {
        return pref2.getString(KEY_EMAIL, "");
    }


    public void setSavedEMAIL(String email) {
        editor2.putString(KEY_EMAIL, email);
        editor2.commit();
    }





    public String getSavedPackId() {
        return pref2.getString(KEY_PACKID, "");
    }





    public void setSavedUserId(String userid) {
        editor2.putString(KEY_USERID, userid);
        editor2.commit();
    }
    public void clearSession() {
        editor2.clear();
        editor2.commit();
    }
    public void setUserLoggedIn(boolean isLogin) {
        editor2.putBoolean(KEY_IS_LOGIN, isLogin);
        editor2.commit();
    }

    public boolean isUserLogin() {
        return pref2.getBoolean(KEY_IS_LOGIN, false);
    }


    public String getSavedUserId() {
        return pref2.getString(KEY_USERID, "");
    }

    // shared pref mode



    public PrefManagerSecond2(Context context) {
        this._context2 = context;
        pref2 = _context2.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor2 = pref2.edit();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        editor2.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        editor2.commit();
    }

    public boolean isFirstTimeLaunch()
    {
        return pref2.getBoolean(IS_FIRST_TIME_LAUNCH, false);
    }

    public void setIntro(boolean value) {
        editor2.putBoolean(KEY_IS_INTRO, value);
        editor2.commit();
    }

    public boolean getIntro() {
        return pref2.getBoolean(KEY_IS_INTRO, false);
    }

    public void setFirstTime(boolean isFirstTime) {
        editor2.putBoolean(IS_FIRST_TIME_, isFirstTime);
        editor2.commit();
    }

    public boolean isFirstTime()
    {
        return pref2.getBoolean(IS_FIRST_TIME_, true);
    }

}
