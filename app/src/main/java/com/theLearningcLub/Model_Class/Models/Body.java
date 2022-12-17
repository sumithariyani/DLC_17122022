
package com.theLearningcLub.Model_Class.Models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

@SuppressWarnings("unused")
public class Body {

    @SerializedName("is_correct")
    private String mIsCorrect;
    @SerializedName("q_option")
    private String mQOption;
    @SerializedName("q_qus")
    private String mQQus;
    @SerializedName("qs_options")
    private List<QsOption> mQsOptions;
    @SerializedName("quiz_detail_id")
    private String mQuizDetailId;
    @SerializedName("quiz_name")
    private String mQuizName;
    @SerializedName("uq_id")
    private String mUqId;

    public String getIsCorrect() {
        return mIsCorrect;
    }

    public void setIsCorrect(String isCorrect) {
        mIsCorrect = isCorrect;
    }

    public String getQOption() {
        return mQOption;
    }

    public void setQOption(String qOption) {
        mQOption = qOption;
    }

    public String getQQus() {
        return mQQus;
    }

    public void setQQus(String qQus) {
        mQQus = qQus;
    }

    public List<QsOption> getQsOptions() {
        return mQsOptions;
    }

    public void setQsOptions(List<QsOption> qsOptions) {
        mQsOptions = qsOptions;
    }

    public String getQuizDetailId() {
        return mQuizDetailId;
    }

    public void setQuizDetailId(String quizDetailId) {
        mQuizDetailId = quizDetailId;
    }

    public String getQuizName() {
        return mQuizName;
    }

    public void setQuizName(String quizName) {
        mQuizName = quizName;
    }

    public String getUqId() {
        return mUqId;
    }

    public void setUqId(String uqId) {
        mUqId = uqId;
    }

}
