
package com.theLearningcLub.model.quizqus;

import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class AnswerQuizQusModel {

    @SerializedName("answer")
    private String mAnswer;
    @SerializedName("answer_correct")
    private String mAnswerCorrect;
    @SerializedName("answer_id")
    private String mAnswerId;
    @SerializedName("s_no")
    private Long mSNo;

    public String getAnswer() {
        return mAnswer;
    }

    public void setAnswer(String answer) {
        mAnswer = answer;
    }

    public String getAnswerCorrect() {
        return mAnswerCorrect;
    }

    public void setAnswerCorrect(String answerCorrect) {
        mAnswerCorrect = answerCorrect;
    }

    public String getAnswerId() {
        return mAnswerId;
    }

    public void setAnswerId(String answerId) {
        mAnswerId = answerId;
    }

    public Long getSNo() {
        return mSNo;
    }

    public void setSNo(Long sNo) {
        mSNo = sNo;
    }

}
