package com.linhv.eventhub.enumeration;

/**
 * Created by ManhNV on 8/5/16.
 */
public enum ActivityEnum {
    None(0),Survey(1),Voting(2),RemoteMic(3);
    private int value;

    ActivityEnum(int value){
        this.value=value;
    }

    public int getValue(){
        return this.value;
    }
}
