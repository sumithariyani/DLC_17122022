
package com.theLearningcLub.model.quiz;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class QuizModel {

    @SerializedName("data")
    private List<DatumQuizModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumQuizModel> getData() {
        return mData;
    }

    public void setData(List<DatumQuizModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
