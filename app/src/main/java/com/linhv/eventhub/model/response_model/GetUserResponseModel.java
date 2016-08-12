package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.User;

/**
 * Created by ManhNV on 8/12/16.
 */
public class GetUserResponseModel extends Response {
    @SerializedName("Data")
    private User user;

    public User getUser() {
        return user;
    }
}
