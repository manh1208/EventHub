package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/11/16.
 */
public class Tickets {
    @SerializedName("EventId")
    private int eventId;
    @SerializedName("Name")
    private String name;
    @SerializedName("ImageUrl")
    private String imageUrl;
    @SerializedName("Code")
    private String code;
    @SerializedName("QRCodeUrl")
    private String qrCodeUrl;

    public int getEventId() {
        return eventId;
    }

    public String getName() {
        return name;
    }

    public String getCode() {
        return code;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}
