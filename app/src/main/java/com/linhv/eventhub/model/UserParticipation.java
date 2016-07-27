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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getTicketId() {
        return ticketId;
    }

    public void setTicketId(int ticketId) {
        this.ticketId = ticketId;
    }

    public String getParticipationTime() {
        return participationTime;
    }

    public void setParticipationTime(String participationTime) {
        this.participationTime = participationTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getParticipateCode() {
        return participateCode;
    }

    public void setParticipateCode(int participateCode) {
        this.participateCode = participateCode;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
