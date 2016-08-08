package com.linhv.eventhub.services;

import com.linhv.eventhub.model.SurveySubmit;
import com.linhv.eventhub.model.response_model.GetParticipatedUsersResponseModel;
import com.linhv.eventhub.model.response_model.GetSurveyResponseModel;
import com.linhv.eventhub.model.response_model.RequestRemoteMicResponseModel;
import com.linhv.eventhub.model.response_model.Response;
import com.linhv.eventhub.model.response_model.SubmitSurveyResponseModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by ManhNV on 8/5/16.
 */
public interface IActivityService {

    @GET("/api/activity/GetSurveyDetail")
    void getSurvey(@Query("activityId") int activityId,
                   Callback<GetSurveyResponseModel> callback);

    @POST("/api/survey/submit")
    void submitSurvey(@Body SurveySubmit surveySubmit,
                      Callback<SubmitSurveyResponseModel> callback);

    @POST("/api/activity/RequestRemoteMic")
    void requestRemoteMic(@Query("eventId") int eventId,
                          @Query("userId") String userId,
                          @Query("activityId") int activityId,
                          Callback<RequestRemoteMicResponseModel> callback);

    @POST("/api/activity/CancelRemoteMic")
    void cancelRemoteMic(@Query("eventId") int eventId,
                         @Query("userId") String userId,
                         @Query("activityId") int activityId,
                         Callback<RequestRemoteMicResponseModel> callback);

}
