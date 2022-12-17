
package com.theLearningcLub.model.review;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ReviewModel {

    @SerializedName("data")
    private List<DatumReviewModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumReviewModel> getData() {
        return mData;
    }

    public void setData(List<DatumReviewModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
