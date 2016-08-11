package com.linhv.eventhub.model.response_model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by ManhNV on 6/23/2016.
 */
public class Response{
    @SerializedName("Succeed")
    private boolean succeed;
    @SerializedName("Message")
    private String message;
    @SerializedName("Errors")
    private List<String> errors;

    public boolean isSucceed() {
        return succeed;
    }

    public void setSucceed(boolean succeed) {
        this.succeed = succeed;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getErrors() {
        return errors;
    }

    public String getErrorsString() {
        String errorsString = this.message+"\n";
        if (getErrors()!=null && getErrors().size()>0) {
            for (String item : getErrors()
                    ) {
                errorsString += item + "\n";
            }
        }
        return errorsString;
    }

    public void setErrors(List<String> errors) {
        this.errors = errors;
    }
}
