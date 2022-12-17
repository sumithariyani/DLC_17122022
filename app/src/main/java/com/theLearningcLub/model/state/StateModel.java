
package com.theLearningcLub.model.state;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class StateModel {

    @SerializedName("body")
    private List<BodyStateModel> mBodyStateModel;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodyStateModel> getBody() {
        return mBodyStateModel;
    }

    public void setBody(List<BodyStateModel> bodyStateModel) {
        mBodyStateModel = bodyStateModel;
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
