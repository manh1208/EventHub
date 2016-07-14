package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.User;

/**
 * Created by ManhNV on 7/14/2016.
 */
public class LoginResponseModel extends Response {
    @SerializedName("Data")
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
