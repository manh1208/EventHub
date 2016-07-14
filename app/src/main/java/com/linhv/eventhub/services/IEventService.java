package com.linhv.eventhub.services;

import com.linhv.eventhub.model.response_model.GetEventsResponseModel;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Query;

/**
 * Created by ManhNV on 7/14/2016.
 */
public interface IEventService {

    @GET("/api/event/getevents/")
    void getEvents(@Query("take")int take,
                   @Query("skip")int skip,
            Callback<GetEventsResponseModel> callback);
}
