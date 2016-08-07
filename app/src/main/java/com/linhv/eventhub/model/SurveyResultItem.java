package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/7/16.
 */
public class SurveyResultItem {
    @SerializedName("Id")
    private int id;
    @SerializedName("SurveyResultId")
    private int surveyResultId;
    @SerializedName("SurveyQuestionId")
    private int surveyQuestionId;
    @SerializedName("SelectedSurveyQuestionOptionId")
    private int selectedSurveyQuestionOptionId;
    @SerializedName("AnswerContent")
    private String answerContent;
    @SerializedName("AnswerContentNumber")
    private int answerContentNumber;
    @SerializedName("Active")
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyResultId() {
        return surveyResultId;
    }

    public void setSurveyResultId(int surveyResultId) {
        this.surveyResultId = surveyResultId;
    }

    public int getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(int surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    public int getSelectedSurveyQuestionOptionId() {
        return selectedSurveyQuestionOptionId;
    }

    public void setSelectedSurveyQuestionOptionId(int selectedSurveyQuestionOptionId) {
        this.selectedSurveyQuestionOptionId = selectedSurveyQuestionOptionId;
    }

    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }

    public int getAnswerContentNumber() {
        return answerContentNumber;
    }

    public void setAnswerContentNumber(int answerContentNumber) {
        this.answerContentNumber = answerContentNumber;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
