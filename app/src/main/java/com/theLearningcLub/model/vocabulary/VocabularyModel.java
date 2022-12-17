
package com.theLearningcLub.model.vocabulary;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class VocabularyModel {

    @SerializedName("data")
    private List<DatumVocabularyModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumVocabularyModel> getData() {
        return mData;
    }

    public void setData(List<DatumVocabularyModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
