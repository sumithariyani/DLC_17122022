
package com.theLearningcLub.model.videolist;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class VidDatumVideoListModel {

    @SerializedName("description")
    private String mDescription;
    @SerializedName("image")
    private String mImage;
    @SerializedName("is_free")
    private String mIsFree;
    @SerializedName("mar_id")
    private String mMarId;
    @SerializedName("Title")
    private String mTitle;
    @SerializedName("vid_id")
    private String mVidId;
    @SerializedName("videncd")
    private String mVidencd;
    @SerializedName("videos_date")
    private String mVideosDate;

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String image) {
        mImage = image;
    }

    public String getIsFree() {
        return mIsFree;
    }

    public void setIsFree(String isFree) {
        mIsFree = isFree;
    }

    public String getMarId() {
        return mMarId;
    }

    public void setMarId(String marId) {
        mMarId = marId;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getVidId() {
        return mVidId;
    }

    public void setVidId(String vidId) {
        mVidId = vidId;
    }

    public String getVidencd() {
        return mVidencd;
    }

    public void setVidencd(String videncd) {
        mVidencd = videncd;
    }

    public String getVideosDate() {
        return mVideosDate;
    }

    public void setVideosDate(String videosDate) {
        mVideosDate = videosDate;
    }

}
