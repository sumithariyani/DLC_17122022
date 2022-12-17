
package com.theLearningcLub.model.quiz;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DatumQuizModel {

    @SerializedName("order_id")
    private String mOrderId;
    @SerializedName("pack_desc")
    private String mPackDesc;
    @SerializedName("pack_id")
    private String mPackId;
    @SerializedName("pack_image")
    private String mPackImage;
    @SerializedName("pack_name")
    private String mPackName;
    @SerializedName("pack_time")
    private String mPackTime;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("user_id")
    private String mUserId;

    public String getOrderId() {
        return mOrderId;
    }

    public void setOrderId(String orderId) {
        mOrderId = orderId;
    }

    public String getPackDesc() {
        return mPackDesc;
    }

    public void setPackDesc(String packDesc) {
        mPackDesc = packDesc;
    }

    public String getPackId() {
        return mPackId;
    }

    public void setPackId(String packId) {
        mPackId = packId;
    }

    public String getPackImage() {
        return mPackImage;
    }

    public void setPackImage(String packImage) {
        mPackImage = packImage;
    }

    public String getPackName() {
        return mPackName;
    }

    public void setPackName(String packName) {
        mPackName = packName;
    }

    public String getPackTime() {
        return mPackTime;
    }

    public void setPackTime(String packTime) {
        mPackTime = packTime;
    }

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

}
