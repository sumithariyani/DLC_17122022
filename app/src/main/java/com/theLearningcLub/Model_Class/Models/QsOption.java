
package com.theLearningcLub.Model_Class.Models;

import com.google.gson.annotations.SerializedName;


public class QsOption {

    @SerializedName("quiz_answer")
    private String mQuizAnswer;
    @SerializedName("quiz_answers_id")
    private String mQuizAnswersId;
    @SerializedName("quiz_status")
    private String mQuizStatus;

    public String getQuizAnswer() {
        return mQuizAnswer;
    }

    public void setQuizAnswer(String quizAnswer) {
        mQuizAnswer = quizAnswer;
    }

    public String getQuizAnswersId() {
        return mQuizAnswersId;
    }

    public void setQuizAnswersId(String quizAnswersId) {
        mQuizAnswersId = quizAnswersId;
    }

    public String getQuizStatus() {
        return mQuizStatus;
    }

    public void setQuizStatus(String quizStatus) {
        mQuizStatus = quizStatus;
    }

}
