
package com.theLearningcLub.model.video;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class VideoModel {

    @SerializedName("data")
    private List<DatumVideoModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumVideoModel> getData() {
        return mData;
    }

    public void setData(List<DatumVideoModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
