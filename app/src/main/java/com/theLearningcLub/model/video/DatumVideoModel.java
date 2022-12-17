
package com.theLearningcLub.model.video;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DatumVideoModel {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("id")
    private String mId;
    @SerializedName("Title")
    private String mTitle;
    @SerializedName("videncd")
    private String mVidencd;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getVidencd() {
        return mVidencd;
    }

    public void setVidencd(String videncd) {
        mVidencd = videncd;
    }

}
