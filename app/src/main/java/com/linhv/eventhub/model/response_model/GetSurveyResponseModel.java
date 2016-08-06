package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Activity;
import com.linhv.eventhub.model.Survey;
import com.linhv.eventhub.model.SurveyCategory;
import com.linhv.eventhub.model.SurveyQuestion;
import com.linhv.eventhub.model.SurveyQuestionGroup;
import com.linhv.eventhub.model.SurveyQuestionOption;

import java.util.List;

/**
 * Created by ManhNV on 8/5/16.
 */
public class GetSurveyResponseModel extends Response {
    @SerializedName("Data")
    private SurveyResponse surveyResponse;

    public SurveyResponse getSurveyResponse() {
        return surveyResponse;
    }

    public class SurveyResponse{
        @SerializedName("Survey")
        private Survey survey;
        @SerializedName("Activity")
        private Activity activity;
        @SerializedName("RunningCampaignSeoName")
        private String runningCampaignSeoName;
        @SerializedName("SurveyCategories")
        private List<SurveyCategory> surveyCategories;
        @SerializedName("SurveyQuestions")
        private List<SurveyQuestionGroup> surveyQuestionGroups;
        @SerializedName("IsPaging")
        private boolean paging;
        @SerializedName("ParticipantCount")
        private int participantCount;

        public Survey getSurvey() {
            return survey;
        }

        public void setSurvey(Survey survey) {
            this.survey = survey;
        }

        public Activity getActivity() {
            return activity;
        }

        public void setActivity(Activity activity) {
            this.activity = activity;
        }

        public String getRunningCampaignSeoName() {
            return runningCampaignSeoName;
        }

        public void setRunningCampaignSeoName(String runningCampaignSeoName) {
            this.runningCampaignSeoName = runningCampaignSeoName;
        }

        public List<SurveyCategory> getSurveyCategories() {
            return surveyCategories;
        }

        public void setSurveyCategories(List<SurveyCategory> surveyCategories) {
            this.surveyCategories = surveyCategories;
        }

        public List<SurveyQuestionGroup> getSurveyQuestionGroups() {
            return surveyQuestionGroups;
        }

        public void setSurveyQuestionGroups(List<SurveyQuestionGroup> surveyQuestionGroups) {
            this.surveyQuestionGroups = surveyQuestionGroups;
        }

        public boolean isPaging() {
            return paging;
        }

        public void setPaging(boolean paging) {
            this.paging = paging;
        }

        public int getParticipantCount() {
            return participantCount;
        }

        public void setParticipantCount(int participantCount) {
            this.participantCount = participantCount;
        }
    }

}
