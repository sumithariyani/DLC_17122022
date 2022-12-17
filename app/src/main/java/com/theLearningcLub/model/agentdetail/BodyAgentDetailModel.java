
package com.theLearningcLub.model.agentdetail;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyAgentDetailModel {

    @SerializedName("d1")
    private List<D1AgentDetailModel> mD1AgentDetailModel;

    public List<D1AgentDetailModel> getD1() {
        return mD1AgentDetailModel;
    }

    public void setD1(List<D1AgentDetailModel> d1AgentDetailModel) {
        mD1AgentDetailModel = d1AgentDetailModel;
    }

}
