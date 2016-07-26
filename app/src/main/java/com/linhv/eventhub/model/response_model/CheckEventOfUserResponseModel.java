package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/26/16.
 */
public class CheckEventOfUserResponseModel extends Response {
    @SerializedName("Data")
    private boolean isBelong;

    public boolean isBelong() {
        return isBelong;
    }

    public void setBelong(boolean belong) {
        isBelong = belong;
    }
}
