
package com.theLearningcLub.model.historyview;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyHistoryViewModel {

    @SerializedName("conteact")
    private String mConteact;
    @SerializedName("date")
    private String mDate;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("feedback")
    private String mFeedback;
    @SerializedName("feedback_by")
    private String mFeedbackBy;
    @SerializedName("h_id")
    private String mHId;
    @SerializedName("message")
    private String mMessage;
    @SerializedName("name")
    private String mName;
    @SerializedName("user_id")
    private String mUserId;

    public String getConteact() {
        return mConteact;
    }

    public void setConteact(String conteact) {
        mConteact = conteact;
    }

    public String getDate() {
        return mDate;
    }

    public void setDate(String date) {
        mDate = date;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getFeedback() {
        return mFeedback;
    }

    public void setFeedback(String feedback) {
        mFeedback = feedback;
    }

    public String getFeedbackBy() {
        return mFeedbackBy;
    }

    public void setFeedbackBy(String feedbackBy) {
        mFeedbackBy = feedbackBy;
    }

    public String getHId() {
        return mHId;
    }

    public void setHId(String hId) {
        mHId = hId;
    }

    public String getMessage() {
        return mMessage;
    }

    public void setMessage(String message) {
        mMessage = message;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
