package com.example.android_pc.notificationtypes;


import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;

import android.net.Uri;
import android.os.Build;

import android.provider.Settings;

import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import java.util.Calendar;

public class NotificationHelper {
    private Context context;
    private NotificationManager mNotificationManager;
    private NotificationCompat.Builder mBuilder;
    public static final String NOTIFICATION_CHANNEL_ID = "10001";

    public NotificationHelper(Context context) {
        this.context = context;
    }

    /**
     * Create channel for notification
     */
    public void createChannel() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, "NOTIFICATION_CHANNEL_NAME", importance);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
            assert mNotificationManager != null;
            mBuilder.setChannelId(NOTIFICATION_CHANNEL_ID);
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
    }

    /**
     * Create and push the simple notification
     */
    public void createNotification(String title, String body) {


        mBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher);
        mBuilder.setContentTitle(title)
                .setContentText(body)
                .setAutoCancel(false)
                .setSound(Settings.System.DEFAULT_NOTIFICATION_URI);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        createChannel();

        assert mNotificationManager != null;
        mNotificationManager.notify(0, mBuilder.build());
    }


    /**
     * Create and push the Inbox Style Notification
     *
     * @param title
     * @param body
     */
    public void createInboxStyleNotification(String title, String body) {
        NotificationCompat.InboxStyle inboxStyle = new NotificationCompat.InboxStyle();

        inboxStyle.setBigContentTitle("Inbox Notification");
        inboxStyle.addLine("Message 1.");
        inboxStyle.addLine("Message 2.");
        inboxStyle.addLine("Message 3.");
        inboxStyle.addLine("Message 4.");
        inboxStyle.addLine("Message 5.");
        inboxStyle.setSummaryText("+2 more");

        mBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(inboxStyle);

        mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        createChannel();
        mNotificationManager.notify(0, mBuilder.build());


    }

    /**
     * Create and push Big Text Style Notification
     *
     * @param title
     * @param body
     */
    public void createBigTextStyleNotification(String title, String body) {

        int NOTIFICATION_ID = 1;

        PendingIntent launchIntent = getLaunchIntent(NOTIFICATION_ID, context.getApplicationContext());
        Intent buttonIntent = new Intent(context.getApplicationContext(), NotificationReceiver.class);
        buttonIntent.putExtra("notificationId", NOTIFICATION_ID);
        PendingIntent dismissIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, buttonIntent, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();
        bigText.bigText("Lorem Ipsum is simply dummy text of the printing and typesetting industry." +
                " Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. " +
                "It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. " +
                "It was popularised in the 1960s with the release of Learjet sheets containing Lorem Ipsum passages, " +
                "and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.");

        mBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.wallpaper)
                .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), R.drawable.icon))
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(bigText)
                .setAutoCancel(true)
                .setContentIntent(launchIntent)
                .addAction(android.R.drawable.ic_delete, "DISMISS", dismissIntent)
                .addAction(android.R.drawable.ic_menu_send, "OPEN APP", launchIntent);
        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);

        createChannel();
        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }


    /**
     * Create and push Big Picture Notification with Pending Intent
     * @param title
     * @param body
     */
    public void createBigPictureNotification(String title, String body) {

        int NOTIFICATION_ID = 1;
        Bitmap pic = BitmapFactory.decodeResource(context.getResources(), R.drawable.wallpaper);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wallpaperbrowse.com/media/images/303836.jpg"));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, 0);

        mBuilder = new NotificationCompat.Builder(context,NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(body)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(pic))
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        mNotificationManager = (NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE);
        createChannel();

        mNotificationManager.notify(NOTIFICATION_ID, mBuilder.build());
    }

    private PendingIntent getLaunchIntent(int notificationId, Context context) {

        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        intent.putExtra("notificationId", notificationId);
        return PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public void createScheduleNotification(){

       /* AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        Intent notificationIntent = new Intent(context, AlarmReceiver.class);
        PendingIntent broadcast = PendingIntent.getBroadcast(context, 100, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.SECOND, 3);
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), broadcast);*/

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent alarmIntent = new Intent(context, AlarmReceiver.class); // AlarmReceiver = broadcast receiver

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmIntent.setData((Uri.parse("custom://"+System.currentTimeMillis())));
        alarmManager.cancel(pendingIntent);

        Calendar alarmStartTime = Calendar.getInstance();
        Calendar now = Calendar.getInstance();
        alarmStartTime.set(Calendar.HOUR_OF_DAY, 17);
        alarmStartTime.set(Calendar.MINUTE, 00);
        alarmStartTime.set(Calendar.SECOND, 0);
        if (now.after(alarmStartTime)) {
            Log.d("Hey","Added a day");
            alarmStartTime.add(Calendar.DATE, 1);
        }

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, alarmStartTime.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
        Log.d("Alarm","Alarms set for everyday 5 pm.");
    }


}
