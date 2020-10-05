package com.notmind.easynotes.notification;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.notmind.easynotes.R;
import com.notmind.easynotes.pin.PinCodeActivity;

import java.util.Random;

public class NotificationHelper {
    private static final String REMINDER_CHANNEL_ID = "reminder";
    private static final String TAG = "Notification";

    private static int lastNotificationID = -1;
    private static boolean channelCreated = false;

    static void showReminderNotification(Context context, long noteId, String contentTitle, String contentText) {
        Intent intent = new Intent(context, PinCodeActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);


        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, REMINDER_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_app)
                .setContentTitle(contentTitle)
                .setContentText(contentText)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        showNotificationFromBuilder(builder, context);
    }

    private static void showNotificationFromBuilder(NotificationCompat.Builder builder, Context context) {
        createNotificationChannelIfNotCreated(context);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }

        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        int notificationId = new Random().nextInt(100000);
        lastNotificationID = notificationId;
        notificationManagerCompat.notify(notificationId, builder.build());
    }

    private static void createNotificationChannelIfNotCreated(Context context) {
        if (!channelCreated) {
            createNotificationChannel(context);
            channelCreated = true;
        }
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String name = "ReminderChannel";
            String descriptionText = "Notifies when it's time to remind";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(REMINDER_CHANNEL_ID, name, importance);
            channel.setDescription(descriptionText);
            NotificationManager notificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
