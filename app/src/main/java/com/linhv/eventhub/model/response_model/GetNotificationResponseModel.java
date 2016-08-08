package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.Notification;

import java.util.List;

/**
 * Created by ManhNV on 8/8/16.
 */
public class GetNotificationResponseModel extends Response {
    @SerializedName("Data")
    private List<Notification> notifications;

    public List<Notification> getNotifications() {
        return notifications;
    }
}
