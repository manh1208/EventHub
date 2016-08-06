package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.ParticipatedUser;
import com.linhv.eventhub.model.User;

import java.util.List;

/**
 * Created by ManhNV on 7/29/16.
 */
public class GetParticipatedUsersResponseModel extends Response{

    @SerializedName("Data")
    private List<ParticipatedUser> participatedUser;

    public List<ParticipatedUser> getParticipatedUser() {
        return participatedUser;
    }

    public void setParticipatedUser(List<ParticipatedUser> participatedUser) {
        this.participatedUser = participatedUser;
    }


}
