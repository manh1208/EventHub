package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/5/16.
 */
public class SurveyQuestion {
    @SerializedName("Id")
    private int id;
    @SerializedName("SurveyId")
    private int surveyId;
    @SerializedName("SurveyQuestionTypeId")
    private int surveyQuestionTypeId;
    @SerializedName("Content")
    private String content;
    @SerializedName("Subtitle")
    private String subtitle;
    @SerializedName("Note")
    private String note;
    @SerializedName("Position")
    private int position;
    @SerializedName("Required")
    private boolean required;
    @SerializedName("Settings")
    private String settings;
    @SerializedName("Active")
    private boolean active;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(int surveyId) {
        this.surveyId = surveyId;
    }

    public int getSurveyQuestionTypeId() {
        return surveyQuestionTypeId;
    }

    public void setSurveyQuestionTypeId(int surveyQuestionTypeId) {
        this.surveyQuestionTypeId = surveyQuestionTypeId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public boolean isRequired() {
        return required;
    }

    public void setRequired(boolean required) {
        this.required = required;
    }

    public String getSettings() {
        return settings;
    }

    public void setSettings(String settings) {
        this.settings = settings;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
