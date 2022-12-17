
package com.theLearningcLub.model.videolist;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class VideoListModel {

    @SerializedName("data")
    private List<DatumVideoListModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumVideoListModel> getData() {
        return mData;
    }

    public void setData(List<DatumVideoListModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
