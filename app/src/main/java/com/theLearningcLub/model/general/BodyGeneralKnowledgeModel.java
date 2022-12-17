
package com.theLearningcLub.model.general;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodyGeneralKnowledgeModel implements Serializable {

    @SerializedName("cat_name")
    private String mCatName;
    @SerializedName("data")
    private List<DatumGeneralKnowledgeModel> mData;

    public String getCatName() {
        return mCatName;
    }

    public void setCatName(String catName) {
        mCatName = catName;
    }

    public List<DatumGeneralKnowledgeModel> getData() {
        return mData;
    }

    public void setData(List<DatumGeneralKnowledgeModel> data) {
        mData = data;
    }

}
