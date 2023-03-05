package me.paschalis.capfgservice;

import android.app.Application;
import android.os.Build;
import android.util.Log;
import android.content.Intent;
import android.content.Context;
import android.app.Service;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.IBinder;
import android.os.Bundle;
import android.annotation.TargetApi;

public class CapacitorForegroundService extends Service {

    public static final String CHANNEL_ID = "capacitor.foreground.service.channel";

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if (intent.getAction().equals("start")) {
            startForegroundService(intent.getExtras());
        } else {
            stopService(intent);
            stopSelf();
        }
        return START_STICKY;
    }

    public void endService(Intent intent) {
        stopService(intent);
        stopForeground(true);
        stopSelf();
    }

    public String echo(String value) {
        Log.i("Echo", value);
        return value;
    }

    private void startForegroundService(Bundle extras) {
        Context context = getApplicationContext();
        //if channel already exists deleted
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            manager.deleteNotificationChannel(CHANNEL_ID);
        }


        // Create notification channel
        createNotificationChannel(extras);
        int icon = 0;
        if(extras.getString("icon") != null) {
            icon = getResources().getIdentifier(extras.getString("icon"), "drawable", context.getPackageName());
        }

        Notification notification = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notification = new Notification.Builder(context, CHANNEL_ID)
                    .setContentTitle((CharSequence) extras.get("title"))
                    .setContentText((CharSequence) extras.get("description"))
                    .setOngoing(true)
                    .setSmallIcon(icon == 0 ? 17301514 : icon) // Default is the star icon
                    .build();
        }

        // Get notification ID
        Integer id;
        try {
            id = Integer.parseInt((String) extras.get("id"));
        } catch (NumberFormatException e) {
            id = 0;
            // Put service in foreground and show notification (id of 0 is not allowed)
        }
        startForeground(id != 0 ? id : 197812504, notification);

    }
        private void createNotificationChannel(Bundle extras) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                Integer importance = 1;
                try {
                   importance =  extras.getInt("importance");
                } catch (NumberFormatException e) {
                    importance = 1;
                }

                switch (importance) {
                    case 2:
                        importance = NotificationManager.IMPORTANCE_DEFAULT;
                        break;
                    case 3:
                        importance = NotificationManager.IMPORTANCE_HIGH;
                        break;
                    default:
                        importance = NotificationManager.IMPORTANCE_LOW;
                        // We are not using IMPORTANCE_MIN because we want the notification to be visible
                }
                NotificationChannel serviceChannel = new NotificationChannel(
                    CHANNEL_ID,
                    "Foreground Service Channel",
                    NotificationManager.IMPORTANCE_DEFAULT
                );
                serviceChannel.setDescription("Enables background processing.");
                NotificationManager manager = getSystemService(NotificationManager.class);
                manager.createNotificationChannel(serviceChannel);
            }
        }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}