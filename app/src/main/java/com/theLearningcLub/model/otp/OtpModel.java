
package com.theLearningcLub.model.otp;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class OtpModel {

    @SerializedName("response")
    private ResponseOtpModel mResponseOtpModel;

    public ResponseOtpModel getResponse() {
        return mResponseOtpModel;
    }

    public void setResponse(ResponseOtpModel responseOtpModel) {
        mResponseOtpModel = responseOtpModel;
    }

}
