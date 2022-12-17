
package com.theLearningcLub.model.state;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyStateModel {

    @SerializedName("state_id")
    private String mStateId;
    @SerializedName("state_name")
    private String mStateName;

    public String getStateId() {
        return mStateId;
    }

    public void setStateId(String stateId) {
        mStateId = stateId;
    }

    public String getStateName() {
        return mStateName;
    }

    public void setStateName(String stateName) {
        mStateName = stateName;
    }

}
