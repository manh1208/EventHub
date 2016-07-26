package com.linhv.eventhub.model.response_model;

import android.support.design.widget.Snackbar;

import com.google.android.gms.games.event.Event;
import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.UserParticipation;

/**
 * Created by ManhNV on 7/26/16.
 */
public class JoinEventFreeResponseModel extends Response {
    @SerializedName("Data")
    private UserParticipation userParticipation;

    public UserParticipation getUserParticipation() {
        return userParticipation;
    }

    public void setUserParticipation(UserParticipation userParticipation) {
        this.userParticipation = userParticipation;
    }
}
