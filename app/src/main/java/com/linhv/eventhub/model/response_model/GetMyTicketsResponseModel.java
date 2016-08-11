package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Tickets;

import java.util.List;

/**
 * Created by ManhNV on 8/11/16.
 */
public class GetMyTicketsResponseModel extends Response {
    @SerializedName("Data")
    private List<Tickets> tickets;

    public List<Tickets> getTickets() {
        return tickets;
    }
}
