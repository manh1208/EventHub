package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class Rating {
    @SerializedName("Point")
    private float point;
    @SerializedName("RateCount")
    private int numOfRate;
    @SerializedName("UserRate")
    private float userRate;

    public float getPoint() {
        return point;
    }

    public void setPoint(float point) {
        this.point = point;
    }

    public int getNumOfRate() {
        return numOfRate;
    }

    public void setNumOfRate(int numOfRate) {
        this.numOfRate = numOfRate;
    }

    public float getUserRate() {
        return userRate;
    }

    public void setUserRate(float userRate) {
        this.userRate = userRate;
    }
}
