package com.linhv.eventhub.model.request_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/26/16.
 */
public class JoinEventFreeRequestModel {
    @SerializedName("UserId")
    private String userId;
    @SerializedName("EventId")
    private int eventId;

    public JoinEventFreeRequestModel(String userId, int eventId) {
        this.userId = userId;
        this.eventId = eventId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getEventId() {
        return eventId;
    }

    public void setEventId(int eventId) {
        this.eventId = eventId;
    }
}
