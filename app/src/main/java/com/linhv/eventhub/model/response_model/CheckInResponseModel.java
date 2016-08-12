package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.User;

/**
 * Created by ManhNV on 7/29/16.
 */
public class CheckInResponseModel extends Response {
    @SerializedName("Data")
    private User user;

    public User getUser() {
        return user;
    }
}
