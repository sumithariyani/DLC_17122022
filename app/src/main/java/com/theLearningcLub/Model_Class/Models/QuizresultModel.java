
package com.theLearningcLub.Model_Class.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class QuizresultModel {

    @SerializedName("body")
    private List<Body> mBody;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<Body> getBody() {
        return mBody;
    }

    public void setBody(List<Body> body) {
        mBody = body;
    }

    public String getMsg() {
        return mMsg;
    }

    public void setMsg(String msg) {
        mMsg = msg;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
