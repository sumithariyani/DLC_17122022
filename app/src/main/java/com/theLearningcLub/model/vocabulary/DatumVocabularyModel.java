
package com.theLearningcLub.model.vocabulary;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@SuppressWarnings("unused")
public class DatumVocabularyModel implements Serializable {

    @SerializedName("alpha_id")
    private String mAlphaId;
    @SerializedName("cat_id")
    private String mCatId;
    @SerializedName("description")
    private String mDescription;
    @SerializedName("grammar_id")
    private String mGrammarId;
    @SerializedName("is_free")
    private String mIsFree;
    @SerializedName("offer_price")
    private String mOfferPrice;
    @SerializedName("pack_id")
    private String mPackId;
    @SerializedName("pack_image")
    private String mPackImage;
    @SerializedName("pack_name")
    private String mPackName;
    @SerializedName("price")
    private String mPrice;
    @SerializedName("rateStar")
    private String mRateStar;
    @SerializedName("time_perioud")
    private String mTimePerioud;

    public String getAlphaId() {
        return mAlphaId;
    }

    public void setAlphaId(String alphaId) {
        mAlphaId = alphaId;
    }

    public String getCatId() {
        return mCatId;
    }

    public void setCatId(String catId) {
        mCatId = catId;
    }

    public String getDescription() {
        return mDescription;
    }

    public void setDescription(String description) {
        mDescription = description;
    }

    public String getGrammarId() {
        return mGrammarId;
    }

    public void setGrammarId(String grammarId) {
        mGrammarId = grammarId;
    }

    public String getIsFree() {
        return mIsFree;
    }

    public void setIsFree(String isFree) {
        mIsFree = isFree;
    }

    public String getOfferPrice() {
        return mOfferPrice;
    }

    public void setOfferPrice(String offerPrice) {
        mOfferPrice = offerPrice;
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

    public String getPrice() {
        return mPrice;
    }

    public void setPrice(String price) {
        mPrice = price;
    }

    public String getRateStar() {
        return mRateStar;
    }

    public void setRateStar(String rateStar) {
        mRateStar = rateStar;
    }

    public String getTimePerioud() {
        return mTimePerioud;
    }

    public void setTimePerioud(String timePerioud) {
        mTimePerioud = timePerioud;
    }

}
