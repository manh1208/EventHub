package com.linhv.eventhub.enumeration;

/**
 * Created by ManhNV on 8/5/16.
 */
public enum SurveyEnum {

    FREETEXT(3) ,SINGLECHOICE(1),MULTIPLECHOICE(2),SCORE(4),LABEL(5),PAGEBREAK(6);
    private int value;

    SurveyEnum(int value){
        this.value=value;
    }

    public int getValue(){
        return this.value;
    }
}
