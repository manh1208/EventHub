package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;
import com.linhv.eventhub.model.ComponentItem;

import java.util.List;

/**
 * Created by ManhNV on 7/15/2016.
 */
public class GetComponentItemsResponseModel extends Response {
    @SerializedName("Data")
    private List<ComponentItem> componentItems;

    public List<ComponentItem> getComponentItems() {
        return componentItems;
    }

    public void setComponentItems(List<ComponentItem> componentItems) {
        this.componentItems = componentItems;
    }
}
