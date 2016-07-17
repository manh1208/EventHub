package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Organizer;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class GetOrganizerResponseModel extends Response {
    @SerializedName("Data")
    private Organizer organizer;

    public Organizer getOrganizer() {
        return organizer;
    }

    public void setOrganizer(Organizer organizer) {
        this.organizer = organizer;
    }
}
