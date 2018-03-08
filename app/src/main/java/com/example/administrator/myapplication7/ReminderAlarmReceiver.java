package com.example.administrator.myapplication7;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.NotificationCompat;

import java.util.Date;


public class ReminderAlarmReceiver extends BroadcastReceiver {
    public static final String REMINDER_TEXT = "REMINDER TEXT";

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent) {

        String reminderText = intent.getStringExtra("aa");
        System.out.println("ddddddddddddddddddcccccccccccccc" + reminderText);
        NotificationCompat.Builder notificationBuilder = (NotificationCompat.Builder) new NotificationCompat.Builder(
                context)
                .setContentTitle("title title title ")
                .setContentText(reminderText)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setLargeIcon(
                        BitmapFactory.decodeResource(context.getResources(),
                                R.mipmap.ic_launcher))
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND )
                .setNumber(4);

        // 2.点击通知栏的跳转
        intent = new Intent(context, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, PendingIntent.FLAG_UPDATE_CURRENT);
        notificationBuilder.setContentIntent(pendingIntent);
        // 3.发出通知
        Notification notification = null;
        notification = notificationBuilder.build();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int id=0;
        if(reminderText.equalsIgnoreCase("a")){
            notificationManager.notify(1, notification);
        }
       else{
        }
        id++;
        if(id>10){
            id=0;
        }
        notificationManager.cancel(0);
        notificationManager.notify(id, notification);


//        Notification.Builder builder= new Notification.Builder(context).setSmallIcon(R.mipmap.ic_launcher).setContentTitle("标题").setContentText(reminderText);
//        Intent resultIntent = new Intent(context, MainActivity.class);
//        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, 0);
//        builder.setContentIntent(resultPendingIntent);
//        NotificationManager mNotifyMgr = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        int mNotificationId = 001;
//        mNotifyMgr.notify(1, builder.build());

    }

}