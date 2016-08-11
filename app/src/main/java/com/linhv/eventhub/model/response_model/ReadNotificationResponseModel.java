package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/9/16.
 */
public class ReadNotificationResponseModel extends Response {
    @SerializedName("Data")
    private String data;
}
