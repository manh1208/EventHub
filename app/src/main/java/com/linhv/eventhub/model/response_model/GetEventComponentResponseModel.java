package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.EventComponent;

import java.util.List;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class GetEventComponentResponseModel extends Response {
    @SerializedName("Data")
    private List<EventComponent> components;

    public List<EventComponent> getComponents() {
        return components;
    }

    public void setComponents(List<EventComponent> components) {
        this.components = components;
    }
}
