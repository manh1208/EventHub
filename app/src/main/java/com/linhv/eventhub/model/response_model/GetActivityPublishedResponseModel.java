package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Activity;

import java.util.List;

/**
 * Created by ManhNV on 8/3/16.
 */
public class GetActivityPublishedResponseModel extends Response{
    @SerializedName("Data")
    private List<Activity> activities;

    public List<Activity> getActivities() {
        return activities;
    }

    public void setActivities(List<Activity> activities) {
        this.activities = activities;
    }
}
