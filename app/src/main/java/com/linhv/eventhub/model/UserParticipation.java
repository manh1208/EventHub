package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/26/16.
 */
public class UserParticipation {
    @SerializedName("Id")
    private int id;
    @SerializedName("UserId")
    private String userId;
    @SerializedName("EventId")
    private int eventId;
    @SerializedName("TicketId")
    private int ticketId;
    @SerializedName("ParticipationTime")
    private String participationTime;
    @SerializedName("Token")
    private String token;
    @SerializedName("ParticipateCode")
    private int participateCode;
    @SerializedName("QRCodeUrl")
    private String qrCodeUrl;
    @SerializedName("Active")
    private boolean active;
}
