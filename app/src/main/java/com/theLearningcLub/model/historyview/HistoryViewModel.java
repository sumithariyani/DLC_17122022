
package com.theLearningcLub.model.historyview;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class HistoryViewModel {

    @SerializedName("body")
    private List<BodyHistoryViewModel> mBodyHistoryViewModel;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodyHistoryViewModel> getBody() {
        return mBodyHistoryViewModel;
    }

    public void setBody(List<BodyHistoryViewModel> bodyHistoryViewModel) {
        mBodyHistoryViewModel = bodyHistoryViewModel;
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
