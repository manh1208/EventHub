

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
import com.linhv.eventhub.activity.LoginActivity;

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
        String message = data.getString("Message");
//        String title = data.getString("Title");
//        String questionId = data.getString("QuestionId");
//        String avatarUrl = data.getString("UserImg");
//        String notificationId = data.getString("NotificationId");
//        int id = Integer.parseInt(questionId);
//        int notiId = Integer.parseInt(notificationId);
//        Log.i("questionId", id + "");
//        Log.i("questionIdSrt", questionId);
        Intent intent = new Intent(this, LoginActivity.class);
//        intent.putExtra("questionId", id);
//        intent.putExtra("notiId", notiId);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                 PendingIntent.FLAG_ONE_SHOT);
//        int numOfNotice = DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().getInt(DataUtils.NUM_OF_NOTICE, 0);
//        numOfNotice++;
//        DataUtils.getINSTANCE(getApplicationContext()).getmPreferences().edit().putInt(DataUtils.NUM_OF_NOTICE, numOfNotice).commit();
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("EventHub")
                .setContentText(message)
                .setColor(getColor(R.color.colorPrimary))
//              .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.avatar))
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
                .setPriority(android.app.Notification.PRIORITY_HIGH)
                .setContentIntent(pendingIntent);
        NotificationManager notificationManager =
                (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build());
    }
}
