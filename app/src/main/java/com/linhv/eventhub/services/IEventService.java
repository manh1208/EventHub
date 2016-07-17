package com.linhv.eventhub.services;

import com.linhv.eventhub.model.request_model.RateEventRequestMode;
import com.linhv.eventhub.model.response_model.GetComponentItemsResponseModel;
import com.linhv.eventhub.model.response_model.GetEventComponentResponseModel;
import com.linhv.eventhub.model.response_model.GetEventDetailResponseModel;
import com.linhv.eventhub.model.response_model.GetEventsResponseModel;
import com.linhv.eventhub.model.response_model.GetOrganizerResponseModel;
import com.linhv.eventhub.model.response_model.RateEventResponseModel;
import com.linhv.eventhub.model.response_model.Response;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by ManhNV on 7/14/2016.
 */
public interface IEventService {

    @GET("/api/event/getevents")
    void getEvents(@Query("take") int take,
                   @Query("skip") int skip,
                   Callback<GetEventsResponseModel> callback);

    @GET("/api/event/getdetail")
    void getEvent(@Query("eventId") int eventId,
                  @Query("UserId") String userId,
                  Callback<GetEventDetailResponseModel> callback);

    @GET("/api/event/getcomponents")
    void getComponent(@Query("eventId") int eventId,
                      Callback<GetEventComponentResponseModel> callback);

    @GET("/api/event/GetItems")
    void getComponentItem(@Query("typeId") int typeId,
                          Callback<GetComponentItemsResponseModel> callback);

    @GET("/api/event/SearchByName")
    void searchByName(@Query("Name") String name,
                      @Query("Skip") int skip,
                      @Query("Take") int take,
                      Callback<GetEventsResponseModel> callback);

    @POST("/api/event/rate")
    void rateEvent(@Body RateEventRequestMode requestMode,
                   Callback<RateEventResponseModel> callback);

    @GET("/api/event/getorganizer")
    void getOrganizer(@Query("eventId") int eventId,
                      Callback<GetOrganizerResponseModel> callback);
}
