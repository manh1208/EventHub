package com.linhv.eventhub.model.request_model;

import com.linhv.eventhub.model.User;

/**
 * Created by ManhNV on 7/14/2016.
 */
public class RegisterRequestModel extends User{

    public RegisterRequestModel(String email, String userName, String fullName, String password) {
        super(email, userName, fullName, password);
    }
}
