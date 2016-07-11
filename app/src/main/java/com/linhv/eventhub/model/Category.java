package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by linh.nguyen on 7/11/16.
 */
public class Category {
    @SerializedName("Id")
    private int id;

    @SerializedName("Name")
    private String name;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
