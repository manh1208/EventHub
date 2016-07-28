package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.User;

import java.util.List;

/**
 * Created by ManhNV on 7/28/16.
 */
public class GetParticipatedUsesResponseModel extends Response {
    @SerializedName("Data")
    private List<User> users;

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
