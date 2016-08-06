package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/5/16.
 */
public class SurveyQuestionOption {
    @SerializedName("Id")
    private int id;
    @SerializedName("SurveyQuestionId")
    private int surveyQuestionId;
    @SerializedName("Content")
    private String content;
    @SerializedName("AllowUserInput")
    private boolean allowUserInput;
    @SerializedName("Position")
    private int position;
    @SerializedName("Active")
    private boolean activive;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyQuestionId() {
        return surveyQuestionId;
    }

    public void setSurveyQuestionId(int surveyQuestionId) {
        this.surveyQuestionId = surveyQuestionId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isAllowUserInput() {
        return allowUserInput;
    }

    public void setAllowUserInput(boolean allowUserInput) {
        this.allowUserInput = allowUserInput;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isActivive() {
        return activive;
    }

    public void setActivive(boolean activive) {
        this.activive = activive;
    }
}
