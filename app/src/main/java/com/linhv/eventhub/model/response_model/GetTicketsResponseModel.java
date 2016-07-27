package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Ticket;

import java.util.List;

/**
 * Created by ManhNV on 7/27/16.
 */
public class GetTicketsResponseModel extends Response {
    @SerializedName("Data")
    private List<Ticket> tickets;

    public List<Ticket> getTickets() {
        return tickets;
    }

    public void setTickets(List<Ticket> tickets) {
        this.tickets = tickets;
    }
}
