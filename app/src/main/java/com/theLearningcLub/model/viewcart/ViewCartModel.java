
package com.theLearningcLub.model.viewcart;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ViewCartModel {

    @SerializedName("data")
    private List<DatumViewCartModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumViewCartModel> getData() {
        return mData;
    }

    public void setData(List<DatumViewCartModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
