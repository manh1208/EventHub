package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ManhNV on 8/5/16.
 */
public class SurveyQuestionGroup implements Comparable {
    @SerializedName("SurveyQuestion")
    private SurveyQuestion surveyQuestion;
    @SerializedName("SurveyQuestionOptions")
    private List<SurveyQuestionOption> surveyQuestionOptions;

    public SurveyQuestion getSurveyQuestion() {
        return surveyQuestion;
    }

    public void setSurveyQuestion(SurveyQuestion surveyQuestion) {
        this.surveyQuestion = surveyQuestion;
    }

    public List<SurveyQuestionOption> getSurveyQuestionOptions() {
        return surveyQuestionOptions;
    }

    public void setSurveyQuestionOptions(List<SurveyQuestionOption> surveyQuestionOptions) {
        this.surveyQuestionOptions = surveyQuestionOptions;
    }


    @Override
    public int compareTo(Object another) {
        SurveyQuestionGroup surveyQuestionGroup = (SurveyQuestionGroup) another;
        return this.surveyQuestion.getPosition() > surveyQuestionGroup.surveyQuestion.getPosition() ? 1 :
                this.surveyQuestion.getPosition() < surveyQuestionGroup.surveyQuestion.getPosition() ? -1 : 0;
    }
}
