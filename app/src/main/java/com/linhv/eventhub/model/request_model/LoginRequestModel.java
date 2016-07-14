package com.linhv.eventhub.model.request_model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by ManhNV on 7/14/2016.
 */
public class LoginRequestModel {
    @SerializedName("UserName")
    private String username;
    @SerializedName("Password")
    private String password;

    public LoginRequestModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
