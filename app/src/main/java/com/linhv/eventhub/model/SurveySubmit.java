package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/7/16.
 */
public class SurveySubmit {
    @SerializedName("ActivityId")
    private int activityId;
    @SerializedName("Answers")
    private SurveyAnswerWithOptions surveyAnswerWithOptions;

    @SerializedName("UserId")
    private String userId;

    public int getActivityId() {
        return activityId;
    }

    public void setActivityId(int activityId) {
        this.activityId = activityId;
    }

    public SurveyAnswerWithOptions getSurveyAnswerWithOptions() {
        return surveyAnswerWithOptions;
    }

    public void setSurveyAnswerWithOptions(SurveyAnswerWithOptions surveyAnswerWithOptions) {
        this.surveyAnswerWithOptions = surveyAnswerWithOptions;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
