

package com.linhv.eventhub.GCM;

import android.app.ActivityManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.gcm.GcmListenerService;
import com.linhv.eventhub.R;
import com.linhv.eventhub.activity.EventDetailActivity;
import com.linhv.eventhub.activity.LoginActivity;
import com.linhv.eventhub.activity.MainActivity;
import com.linhv.eventhub.activity.StreamActivity;
import com.linhv.eventhub.enumeration.Notify;
import com.linhv.eventhub.model.response_model.GetEventDetailResponseModel;
import com.linhv.eventhub.otto.BusStation;
import com.linhv.eventhub.otto.Message;
import com.linhv.eventhub.otto.NewActivity;
import com.linhv.eventhub.services.RestService;
import com.linhv.eventhub.utils.DataUtils;
import com.linhv.eventhub.utils.QuickSharePreferences;
import com.squareup.otto.Bus;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";
    private String userId;
    private RestService restService;

    @Override
    public void onMessageReceived(String from, Bundle data) {
        String message = data.getString("message");
        Log.d(TAG, "From: " + from);
        Log.d(TAG, "Message: " + message);

        if (from.startsWith("/topics/")) {

        } else {
            // normal downstream message.
        }

        sendNotification(data);
    }

    private void sendNotification(Bundle data) {
        int type = Integer.parseInt(data.getString("Type"));
        Log.i("Otto","Type = "+ type);
        restService = new RestService();
        userId = DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().getString(QuickSharePreferences.SHARE_USERID,"");
        if (type == Notify.REMOTEMIC.getValue()) {

            String message = data.getString("Message");
            String title = data.getString("Title");
            int eventId = Integer.parseInt(data.getString("EventId"));
            int notificationId = Integer.parseInt(data.getString("NotificationId"));
            Log.i("Otto","Gởi message");
            String ipAddress = data.getString("IpAddress");
            BusStation.getBus().post(new Message(ipAddress));
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(getColor(R.color.colorPrimary))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(android.app.Notification.PRIORITY_HIGH);
            if (!checkApp()) {
                Intent intent = new Intent(this, StreamActivity.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("start",true);
//                intent.putExtra("notiId", notificationId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, eventId /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                notificationBuilder.setContentIntent(pendingIntent);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

        }if (type==Notify.NEWACTIVITY.getValue()){
            String message = data.getString("Message");
            String title = data.getString("Title");
            int eventId = Integer.parseInt(data.getString("EventId"));
            int notificationId = Integer.parseInt(data.getString("NotificationId"));
            Log.i("Otto","Gởi message");
            String ipAddress = data.getString("IpAddress");
            BusStation.getBus().post(new NewActivity());
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentTitle(title)
                    .setContentText(message)
                    .setColor(getColor(R.color.colorPrimary))
                    .setAutoCancel(true)
                    .setSound(defaultSoundUri)
                    .setPriority(android.app.Notification.PRIORITY_HIGH);
            if (!checkApp()) {
                Intent intent = new Intent(this, StreamActivity.class);
                intent.putExtra("eventId", eventId);
                intent.putExtra("start",true);
//                intent.putExtra("notiId", notificationId);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                PendingIntent pendingIntent = PendingIntent.getActivity(this, eventId /* Request code */, intent,
                        PendingIntent.FLAG_ONE_SHOT);
                notificationBuilder.setContentIntent(pendingIntent);
            }
            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());

        }
        else {
            final String message = data.getString("Message");
            final String title = data.getString("Title");
            final int eventId = Integer.parseInt(data.getString("EventId"));
            final int notificationId = Integer.parseInt(data.getString("NotificationId"));
            restService.getEventService().getEvent(eventId, userId, new Callback<GetEventDetailResponseModel>() {
                @Override
                public void success(GetEventDetailResponseModel responseModel, Response response) {
                    if (responseModel.isSucceed()){
                        Intent intent = new Intent(getApplicationContext(), EventDetailActivity.class);
                        intent.putExtra("eventId", responseModel.getEvent().getId());
                        intent.putExtra("eventName", responseModel.getEvent().getName());
                        intent.putExtra("notiId", notificationId);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), eventId /* Request code */, intent,
                                PendingIntent.FLAG_ONE_SHOT);
                        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext())
                                .setSmallIcon(R.mipmap.ic_launcher)
                                .setContentTitle(title)
                                .setContentText(message)
                                .setColor(getColor(R.color.colorPrimary))
                                .setAutoCancel(true)
                                .setSound(defaultSoundUri)
                                .setPriority(android.app.Notification.PRIORITY_HIGH)
                                .setContentIntent(pendingIntent);
                        NotificationManager notificationManager =
                                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                        notificationManager.notify(notificationId /* ID of notification */, notificationBuilder.build());
                    }
                }

                @Override
                public void failure(RetrofitError error) {

                }
            });

        }

    }

    public boolean checkApp() {
        ActivityManager am = (ActivityManager) this
                .getSystemService(ACTIVITY_SERVICE);

        // get the info from the currently running task
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);

        ComponentName componentInfo = taskInfo.get(0).topActivity;
        if (componentInfo.getPackageName().equalsIgnoreCase("com.linhv.eventhub")) {
            return true;
        } else {
            return false;
        }
    }

}
