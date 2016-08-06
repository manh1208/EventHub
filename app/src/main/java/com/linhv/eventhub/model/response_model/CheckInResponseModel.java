package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/29/16.
 */
public class CheckInResponseModel extends Response {
    @SerializedName("Data")
    private boolean successfull;

    public boolean isSuccessfull() {
        return successfull;
    }

    public void setSuccessfull(boolean successfull) {
        this.successfull = successfull;
    }
}
