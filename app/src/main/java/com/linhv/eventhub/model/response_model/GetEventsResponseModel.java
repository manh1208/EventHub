package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Event;

import java.util.List;

/**
 * Created by ManhNV on 7/14/2016.
 */
public class GetEventsResponseModel extends Response {
    @SerializedName("Data")
    private List<Event> events;

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
}
