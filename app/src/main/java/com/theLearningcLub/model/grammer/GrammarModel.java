
package com.theLearningcLub.model.grammer;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class GrammarModel {

    @SerializedName("data")
    private List<DatumGrammarModel> mData;
    @SerializedName("status")
    private String mStatus;

    public List<DatumGrammarModel> getData() {
        return mData;
    }

    public void setData(List<DatumGrammarModel> data) {
        mData = data;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }

}
