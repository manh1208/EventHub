package com.linhv.eventhub.enumeration;

/**
 * Created by ManhNV on 8/5/16.
 */
public enum  Notify {
    APPROVEDEVENT (1), MESSAGE(2),ACTIVITY(3),REMOTEMIC(4),NEWACTIVITY(5);
    private int value;
    Notify(int value){
        this.value = value;
    }

    public int getValue(){
        return this.value;
    }
}

