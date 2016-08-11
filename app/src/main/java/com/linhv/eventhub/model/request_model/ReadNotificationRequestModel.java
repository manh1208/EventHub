package com.linhv.eventhub.model.request_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 8/9/16.
 */
public class ReadNotificationRequestModel {
    @SerializedName("notiId")
    private int notiId;

    public ReadNotificationRequestModel(int notiId){
        this.notiId = notiId;
    }
}
