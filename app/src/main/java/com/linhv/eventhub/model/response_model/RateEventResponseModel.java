package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Rating;

/**
 * Created by ManhNV on 7/17/2016.
 */
public class RateEventResponseModel extends Response {
    @SerializedName("Data")
    private Rating rate;

    public Rating getRate() {
        return rate;
    }

    public void setRate(Rating rate) {
        this.rate = rate;
    }


}
