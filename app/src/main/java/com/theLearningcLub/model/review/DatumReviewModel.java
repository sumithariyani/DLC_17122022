
package com.theLearningcLub.model.review;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DatumReviewModel {

    @SerializedName("comments")
    private String mComments;
    @SerializedName("pack_id")
    private String mPackId;
    @SerializedName("rating_date")
    private String mRatingDate;
    @SerializedName("rating_id")
    private String mRatingId;
    @SerializedName("review")
    private String mReview;
    @SerializedName("total_rating")
    private String mTotalRating;
    @SerializedName("user")
    private String mUser;
    @SerializedName("user_id")
    private String mUserId;
    @SerializedName("user_rating")
    private String mUserRating;
    @SerializedName("userimage")
    private String mUserimage;

    public String getComments() {
        return mComments;
    }

    public void setComments(String comments) {
        mComments = comments;
    }

    public String getPackId() {
        return mPackId;
    }

    public void setPackId(String packId) {
        mPackId = packId;
    }

    public String getRatingDate() {
        return mRatingDate;
    }

    public void setRatingDate(String ratingDate) {
        mRatingDate = ratingDate;
    }

    public String getRatingId() {
        return mRatingId;
    }

    public void setRatingId(String ratingId) {
        mRatingId = ratingId;
    }

    public String getReview() {
        return mReview;
    }

    public void setReview(String review) {
        mReview = review;
    }

    public String getTotalRating() {
        return mTotalRating;
    }

    public void setTotalRating(String totalRating) {
        mTotalRating = totalRating;
    }

    public String getUser() {
        return mUser;
    }

    public void setUser(String user) {
        mUser = user;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }

    public String getUserRating() {
        return mUserRating;
    }

    public void setUserRating(String userRating) {
        mUserRating = userRating;
    }

    public String getUserimage() {
        return mUserimage;
    }

    public void setUserimage(String userimage) {
        mUserimage = userimage;
    }

}
