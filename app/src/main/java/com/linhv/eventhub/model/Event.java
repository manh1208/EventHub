package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/5/2016.
 */
public class Event {
    @SerializedName("Id")
    private int id;

    @SerializedName("CreatorUserID")
    private String creatorUserID;

    @SerializedName("OrganizerId")
    private String organizerId;

    @SerializedName("Name")
    private String name;

    @SerializedName("ImageUrl")
    private String imageUrl;

    @SerializedName("Description")
    private String description;

    @SerializedName("StartDateString")
    private String startDate;

    @SerializedName("EndDateString")
    private String endDate;

    @SerializedName("CreatedTimeString")
    private String createdTime;

    @SerializedName("Address")
    private String address;

    @SerializedName("Latitude")
    private float latitude;

    @SerializedName("Longitude")
    private float longitude;

    @SerializedName("SeoName")
    private String seoName;

    @SerializedName("IsFree")
    private boolean isFree;

    public Event(int id, String creatorUserID, String organizerId, String name, String imageUrl,
                 String description, String startDate, String endDate, String createdTime,
                 String address, float latitude, float longitude, String seoName, boolean isFree) {
        this.id = id;
        this.creatorUserID = creatorUserID;
        this.organizerId = organizerId;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdTime = createdTime;
        this.address = address;
        this.latitude = latitude;
        this.longitude = longitude;
        this.seoName = seoName;
        this.isFree = isFree;
    }

    public Event() {
    }
}
