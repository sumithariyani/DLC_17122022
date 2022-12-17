
package com.theLearningcLub.model.general;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GeneralKnowledgeModel implements Serializable {

    @SerializedName("body")
    private List<BodyGeneralKnowledgeModel> mBodyGeneralKnowledgeModel;
    @SerializedName("messages")
    private String mMessages;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodyGeneralKnowledgeModel> getBody() {
        return mBodyGeneralKnowledgeModel;
    }

    public void setBody(List<BodyGeneralKnowledgeModel> bodyGeneralKnowledgeModel) {
        mBodyGeneralKnowledgeModel = bodyGeneralKnowledgeModel;
    }

    public String getMessages() {
        return mMessages;
    }

    public void setMessages(String messages) {
        mMessages = messages;
    }

    public Boolean getStatus() {
        return mStatus;
    }

    public void setStatus(Boolean status) {
        mStatus = status;
    }

}
