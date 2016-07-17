package com.linhv.eventhub.model.request_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class RateEventRequestMode {
    @SerializedName("UserId")
    private String userId;
    @SerializedName("EventId")
    private int eventId;
    @SerializedName("Point")
    private float point;

    public RateEventRequestMode(String userId, int eventId, float point) {
        this.userId = userId;
        this.eventId = eventId;
        this.point = point;
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

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }
}
