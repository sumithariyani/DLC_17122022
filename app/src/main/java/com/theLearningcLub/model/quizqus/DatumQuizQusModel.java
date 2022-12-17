
package com.theLearningcLub.model.quizqus;

import java.util.List;
import com.google.gson.annotations.SerializedName;

@SuppressWarnings("unused")
public class DatumQuizQusModel {

    @SerializedName("answer")
    private List<AnswerQuizQusModel> mAnswerQuizQusModel;
    @SerializedName("id")
    private String mId;
    @SerializedName("question")
    private String mQuestion;

    public List<AnswerQuizQusModel> getAnswer() {
        return mAnswerQuizQusModel;
    }

    public void setAnswer(List<AnswerQuizQusModel> answerQuizQusModel) {
        mAnswerQuizQusModel = answerQuizQusModel;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getQuestion() {
        return mQuestion;
    }

    public void setQuestion(String question) {
        mQuestion = question;
    }

}
