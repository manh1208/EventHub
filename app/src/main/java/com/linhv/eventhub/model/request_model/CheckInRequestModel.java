package com.linhv.eventhub.model.request_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/29/16.
 */
public class CheckInRequestModel {
    @SerializedName("UserId")
    private String userId;
    @SerializedName("EventId")
    private int eventId;
    @SerializedName("Code")
    private int code;

    public CheckInRequestModel(String userId, int eventId, int code) {
        this.userId = userId;
        this.eventId = eventId;
        this.code = code;
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

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
