package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Follow;

/**
 * Created by ManhNV on 7/20/2016.
 */
public class FollowEventResponseModel extends Response {
    @SerializedName("Data")
    private Follow follow;

    public Follow getFollow() {
        return follow;
    }

    public void setFollow(Follow follow) {
        this.follow = follow;
    }
}
