
package com.theLearningcLub.model.otp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ResponseOtpModel {

    @SerializedName("contact")
    private String mContact;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("is_agent")
    private String mIsAgent;
    @SerializedName("login_key")
    private String mLoginKey;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("name")
    private String mName;
    @SerializedName("status")
    private String mStatus;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("verified_status")
    private String mVerifiedStatus;

    public String getContact() {
        return mContact;
    }

    public void setContact(String contact) {
        mContact = contact;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getIsAgent() {
        return mIsAgent;
    }

    public void setIsAgent(String isAgent) {
        mIsAgent = isAgent;
    }

    public String getLoginKey() {
        return mLoginKey;
    }

    public void setLoginKey(String loginKey) {
        mLoginKey = loginKey;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getVerifiedStatus() {
        return mVerifiedStatus;
    }

    public void setVerifiedStatus(String verifiedStatus) {
        mVerifiedStatus = verifiedStatus;
    }

}
