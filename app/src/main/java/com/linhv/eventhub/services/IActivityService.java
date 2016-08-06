package com.linhv.eventhub.services;

import com.linhv.eventhub.model.response_model.GetParticipatedUsersResponseModel;
import com.linhv.eventhub.model.response_model.GetSurveyResponseModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ManhNV on 8/5/16.
 */
public interface IActivityService {

    @GET("/api/activity/GetSurveyDetail")
    void getSurvey(@Query("activityId")int activityId,
                              Callback<GetSurveyResponseModel> callback);
}
