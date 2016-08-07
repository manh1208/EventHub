package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ManhNV on 8/7/16.
 */
public class SurveyAnswerWithOptions {
    @SerializedName("SurveyAnswer")
    private SurveyAnswer surveyAnswer;
    @SerializedName("SurveyAnswerOptions")
    private List<SurveyResultItem> surveyResultItems;

    public SurveyAnswer getSurveyAnswer() {
        return surveyAnswer;
    }

    public void setSurveyAnswer(SurveyAnswer surveyAnswer) {
        this.surveyAnswer = surveyAnswer;
    }

    public List<SurveyResultItem> getSurveyResultItems() {
        return surveyResultItems;
    }

    public void setSurveyResultItems(List<SurveyResultItem> surveyResultItems) {
        this.surveyResultItems = surveyResultItems;
    }
}
