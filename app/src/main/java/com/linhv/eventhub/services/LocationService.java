package com.linhv.eventhub.services;

import android.app.IntentService;
import android.content.Intent;

/**
 * Created by ManhNV on 8/14/16.
 */
public class LocationService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public LocationService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {

    }
}
