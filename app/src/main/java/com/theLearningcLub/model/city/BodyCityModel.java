
package com.theLearningcLub.model.city;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyCityModel {

    @SerializedName("dist_id")
    private String mDistId;
    @SerializedName("dist_name")
    private String mDistName;
    @SerializedName("state_id")
    private String mStateId;

    public String getDistId() {
        return mDistId;
    }

    public void setDistId(String distId) {
        mDistId = distId;
    }

    public String getDistName() {
        return mDistName;
    }

    public void setDistName(String distName) {
        mDistName = distName;
    }

    public String getStateId() {
        return mStateId;
    }

    public void setStateId(String stateId) {
        mStateId = stateId;
    }

}
