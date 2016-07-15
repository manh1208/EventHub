package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Event;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class GetEventDetailResponseModel extends Response {
    @SerializedName("Data")
    private Event event;

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }
}
