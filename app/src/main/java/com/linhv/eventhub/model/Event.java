package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

import java.sql.Date;
import java.text.SimpleDateFormat;

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

    @SerializedName("StartDate")
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreatorUserID() {
        return creatorUserID;
    }

    public void setCreatorUserID(String creatorUserID) {
        this.creatorUserID = creatorUserID;
    }

    public String getOrganizerId() {
        return organizerId;
    }

    public void setOrganizerId(String organizerId) {
        this.organizerId = organizerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {

        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public String getSeoName() {
        return seoName;
    }

    public void setSeoName(String seoName) {
        this.seoName = seoName;
    }

    public boolean isFree() {
        return isFree;
    }

    public void setFree(boolean free) {
        isFree = free;
    }
}
