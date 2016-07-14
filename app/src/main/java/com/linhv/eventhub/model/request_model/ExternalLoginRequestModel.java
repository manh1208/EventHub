package com.linhv.eventhub.model.request_model;

import com.linhv.eventhub.model.User;

/**
 * Created by ManhNV on 7/14/2016.
 */
public class ExternalLoginRequestModel  extends User {
    public ExternalLoginRequestModel(User user) {
        this.setEmail(user.getEmail());
        this.setImageUrl( user.getImageUrl());
        this.setFullName(user.getFullName());
    }
}
