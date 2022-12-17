
package com.theLearningcLub.model.imageslider;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DatatwoImageSliderModel {

    @SerializedName("advertise_image")
    private String mAdvertiseImage;
    @SerializedName("advertise_name")
    private String mAdvertiseName;

    public String getAdvertiseImage() {
        return mAdvertiseImage;
    }

    public void setAdvertiseImage(String advertiseImage) {
        mAdvertiseImage = advertiseImage;
    }

    public String getAdvertiseName() {
        return mAdvertiseName;
    }

    public void setAdvertiseName(String advertiseName) {
        mAdvertiseName = advertiseName;
    }

}
