
package com.theLearningcLub.model.imageslider;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class ImageSliderModel {

    @SerializedName("datatwo")
    private List<DatatwoImageSliderModel> mDatatwoImageSliderModel;
    @SerializedName("status")
    private String mStatus;

    public List<DatatwoImageSliderModel> getDatatwo() {
        return mDatatwoImageSliderModel;
    }

    public void setDatatwo(List<DatatwoImageSliderModel> datatwoImageSliderModel) {
        mDatatwoImageSliderModel = datatwoImageSliderModel;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
