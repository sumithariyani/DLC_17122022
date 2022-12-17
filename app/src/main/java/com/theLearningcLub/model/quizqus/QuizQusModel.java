
package com.theLearningcLub.model.quizqus;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class QuizQusModel {

    @SerializedName("data")
    private List<DatumQuizQusModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumQuizQusModel> getData() {
        return mData;
    }

    public void setData(List<DatumQuizQusModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
