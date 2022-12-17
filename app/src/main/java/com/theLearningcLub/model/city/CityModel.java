
package com.theLearningcLub.model.city;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class CityModel {

    @SerializedName("body")
    private List<BodyCityModel> mBodyCityModel;
    @SerializedName("msg")
    private String mMsg;
    @SerializedName("status")
    private Boolean mStatus;

    public List<BodyCityModel> getBody() {
        return mBodyCityModel;
    }

    public void setBody(List<BodyCityModel> bodyCityModel) {
        mBodyCityModel = bodyCityModel;
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
