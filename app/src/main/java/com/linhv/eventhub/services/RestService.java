package com.linhv.eventhub.services;

import com.linhv.eventhub.utils.DataUtils;

/**
 * Created by ManhNV on 06/22/2016.
 */
public class RestService {
    private static final String URL = DataUtils.URL;
//private static final String URL = "http://10.5.50.25:2315";
//    private static final String URL = "http://192.168.150.70:2315";
    private retrofit.RestAdapter restAdapter;
    private IUserService userService;
    private IEventService eventService;
    private IActivityService activityService;


    public RestService() {

        restAdapter = new retrofit.RestAdapter.Builder()
                .setEndpoint(URL)
                .setLogLevel(retrofit.RestAdapter.LogLevel.FULL)
                .build();

        userService = restAdapter.create(IUserService.class);
        eventService = restAdapter.create(IEventService.class);
        activityService = restAdapter.create(IActivityService.class);
    }

    public IUserService getUserService() {
        return userService;
    }

    public IEventService getEventService() {
        return eventService;
    }

    public IActivityService getActivityService() {
        return activityService;
    }
}
