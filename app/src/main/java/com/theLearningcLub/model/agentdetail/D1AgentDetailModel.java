
package com.theLearningcLub.model.agentdetail;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class D1AgentDetailModel {

    @SerializedName("user_fname")
    private String mUserFname;
    @SerializedName("user_id")
    private String mUserId;

    public String getUserFname() {
        return mUserFname;
    }

    public void setUserFname(String userFname) {
        mUserFname = userFname;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
