
package com.theLearningcLub.model.agentdetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AgentDetailModel {

    @SerializedName("body")
    private List<BodyAgentDetailModel> mBodyAgentDetailModel;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodyAgentDetailModel> getBody() {
        return mBodyAgentDetailModel;
    }

    public void setBody(List<BodyAgentDetailModel> bodyAgentDetailModel) {
        mBodyAgentDetailModel = bodyAgentDetailModel;
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
