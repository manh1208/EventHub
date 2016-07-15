package com.linhv.eventhub.model;

import com.google.gson.annotations.SerializedName;

import java.util.Comparator;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class EventComponent implements Comparator {
    @SerializedName("Id")
    private int id;
    @SerializedName("Name")
    private String name;
    @SerializedName("Position")
    private int position;

    @Override
    public int compare(Object lhs, Object rhs) {
        EventComponent component1 = (EventComponent) lhs;
        EventComponent component2 = (EventComponent) rhs;
        int a = component1.getPosition();
        int b = component2.getPosition();
        return a > b ? 1 : a < b ? -1 : 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }
}
