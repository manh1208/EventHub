package com.linhv.eventhub.model;

import com.facebook.Profile;
import com.google.gson.annotations.SerializedName;

/**
 * Created by linh.nguyen on 7/11/16.
 */
public class User {
    @SerializedName("Id")
    private String id;

    @SerializedName("Email")
    private String email;

    @SerializedName("UserName")
    private String userName;

    @SerializedName("FullName")
    private String fullName;

    @SerializedName("Password")
    private String password;

    @SerializedName("CreatedTimeString")
    private String createdTime;

    @SerializedName("ImageUrl")
    private String ImageUrl;

    public User(){

    }

    public User(String email, String userName, String fullName, String password) {
        this.email = email;
        this.userName = userName;
        this.fullName = fullName;
        this.password = password;
    }

    public User(Profile profile) {
        this.setFullName(profile.getName());
        this.setImageUrl(profile.getProfilePictureUri(500, 500).toString());
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getImageUrl() {
        return ImageUrl;
    }

    public void setImageUrl(String imageUrl) {
        ImageUrl = imageUrl;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
