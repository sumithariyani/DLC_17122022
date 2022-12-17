
package com.theLearningcLub.model.agentreport;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AgentReportModel {

    @SerializedName("body")
    private List<BodyAgentReportModel> mBodyAgentReportModel;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodyAgentReportModel> getBody() {
        return mBodyAgentReportModel;
    }

    public void setBody(List<BodyAgentReportModel> bodyAgentReportModel) {
        mBodyAgentReportModel = bodyAgentReportModel;
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
