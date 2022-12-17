
package com.theLearningcLub.model.search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class SearchViewModel {

    @SerializedName("body")
    private List<BodySearchViewModel> mBodySearchViewModel;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodySearchViewModel> getBody() {
        return mBodySearchViewModel;
    }

    public void setBody(List<BodySearchViewModel> bodySearchViewModel) {
        mBodySearchViewModel = bodySearchViewModel;
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
