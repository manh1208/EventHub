package com.linhv.eventhub.model;

/**
 * Created by ManhNV on 7/12/2016.
 */
public class EventType  {

    private int image;
    private String name;

    public EventType(int image, String name) {
        this.image = image;
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        EventType eventType = (EventType) o;
        return this.name.equals(eventType.getName());
    }
}
