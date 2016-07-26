/**
 * Copyright 2015 Google Inc. All Rights Reserved.
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.linhv.eventhub.GCM;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.android.gms.gcm.GcmPubSub;
import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.iid.InstanceID;
import com.linhv.eventhub.R;
import com.linhv.eventhub.model.response_model.SendTokenResponseModel;
import com.linhv.eventhub.services.RestService;

import java.io.IOException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class RegistrationIntentService extends IntentService {

    private static final String TAG = "RegIntentService";
    private static final String[] TOPICS = {"global"};
    SharedPreferences sharedPreferences;
    private Context mContext;

    public RegistrationIntentService() {
        super(TAG);
        mContext = this;
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        try {
            InstanceID instanceID = InstanceID.getInstance(this);
            String token = instanceID.getToken(getString(R.string.sender_id),
                    GoogleCloudMessaging.INSTANCE_ID_SCOPE, null);
            Log.i(TAG, "GCM Registration Token: " + token);
            sendRegistrationToServer(token);
//            subscribeTopics(token);
        } catch (Exception e) {
            Log.d(TAG, "Failed to complete token refresh", e);
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
        }

    }

    private void sendRegistrationToServer(String token) {
        String userId = sharedPreferences
                .getString(QuickstartPreferences.USER_ID, "");
        if (userId.trim().length() > 0) {
            RestService restService = new RestService();
            restService.getUserService().registerTokenId(userId, token, new Callback<SendTokenResponseModel>() {
                @Override
                public void success(SendTokenResponseModel responseModel, Response response) {
                    if (responseModel.isSucceed()) {
                        sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, true).apply();
                    } else {
                        sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
                    }
                    Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(registrationComplete);
                }

                @Override
                public void failure(RetrofitError error) {
                    sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
                    Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
                    LocalBroadcastManager.getInstance(mContext).sendBroadcast(registrationComplete);
                }
            });
        } else {
            sharedPreferences.edit().putBoolean(QuickstartPreferences.SENT_TOKEN_TO_SERVER, false).apply();
            Intent registrationComplete = new Intent(QuickstartPreferences.REGISTRATION_COMPLETE);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(registrationComplete);
        }
        // gởi token lên server ở đây
    }


    private void subscribeTopics(String token) throws IOException {
        GcmPubSub pubSub = GcmPubSub.getInstance(this);
        for (String topic : TOPICS) {
            pubSub.subscribe(token, "/topics/" + topic, null);
        }
    }

}
