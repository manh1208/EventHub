

package com.linhv.eventhub.GCM;

import android.app.NotificationManager;
import android.app.PendingIntent;
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
import com.linhv.eventhub.enumeration.Notify;
import com.linhv.eventhub.otto.BusStation;
import com.linhv.eventhub.otto.Message;
import com.squareup.otto.Bus;

public class MyGcmListenerService extends GcmListenerService {

    private static final String TAG = "MyGcmListenerService";

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
        if (type == Notify.ACTIVITY.getValue()) {
            BusStation.getBus().post(new Message("Ahihi đồ ngốc"));
        } else if (type == Notify.APPROVEDEVENT.getValue()) {
            String message = data.getString("Message");
            String title = data.getString("Title");
            int eventId = Integer.parseInt(data.getString("EventId"));
            int notificationId = Integer.parseInt(data.getString("NotificationId"));
            Intent intent = new Intent(this, EventDetailActivity.class);
            intent.putExtra("eventId", eventId);
            intent.putExtra("notiId", notificationId);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent = PendingIntent.getActivity(this, eventId /* Request code */, intent,
                    PendingIntent.FLAG_ONE_SHOT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
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
        } else if (type == Notify.MESSAGE.getValue()) {

        }

    }
}
