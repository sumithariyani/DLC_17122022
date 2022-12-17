
package com.theLearningcLub.model.search;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class BodySearchViewModel {

    @SerializedName("cat_name")
    private String mCatName;
    @SerializedName("data")
    private List<DatumSearchViewModel> mData;

    public String getCatName() {
        return mCatName;
    }

    public void setCatName(String catName) {
        mCatName = catName;
    }

    public List<DatumSearchViewModel> getData() {
        return mData;
    }

    public void setData(List<DatumSearchViewModel> data) {
        mData = data;
    }

}
