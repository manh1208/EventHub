package com.linhv.eventhub.services;


import com.linhv.eventhub.model.request_model.ExternalLoginRequestModel;
import com.linhv.eventhub.model.request_model.LoginRequestModel;
import com.linhv.eventhub.model.request_model.RegisterRequestModel;
import com.linhv.eventhub.model.response_model.ExternalLoginResponseModel;
import com.linhv.eventhub.model.response_model.FollowEventResponseModel;
import com.linhv.eventhub.model.response_model.GetEventsResponseModel;
import com.linhv.eventhub.model.response_model.GetNotificationResponseModel;
import com.linhv.eventhub.model.response_model.LoginResponseModel;
import com.linhv.eventhub.model.response_model.RegisterResponseModel;
import com.linhv.eventhub.model.response_model.SendTokenResponseModel;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by ManhNV on 6/24/2016.
 */
public interface IUserService {

    @POST("/api/account/externallogin")
    void externalLogin(@Body ExternalLoginRequestModel request, Callback<ExternalLoginResponseModel> callback);

    @GET("/api/account/getnotifications")
    void getNotification(@Query("userid")String userId,
                         Callback<GetNotificationResponseModel> callback);

    @POST("/api/account/login")
    void login(@Body LoginRequestModel request, Callback<LoginResponseModel> callback);


    @POST("/api/account/Register")
    void register(@Body RegisterRequestModel request, Callback<RegisterResponseModel> callback);

    @GET("/api/account/getuserinfo")
    void getUserInfo(@Query("userId") String userId,
                     Callback<LoginResponseModel> callback);

    @POST("/api/userFollow/Follow")
    void followEvent(@Query("eventId") int eventId,
                     @Query("userId") String userId,
                     Callback<FollowEventResponseModel> callback);

    @GET("/api/userFollow/Get")
    void getFollowEvent(@Query("userId") String userId,
                     @Query("take") int take,
                     @Query("skip") int skip,
                     Callback<GetEventsResponseModel> callback);

    @POST("/api/account/RegisterTokenId?Platform=1")
    void registerTokenId(@Query("UserId") String userId,
                         @Query("Token") String token,
                         Callback<SendTokenResponseModel> callback);
}
