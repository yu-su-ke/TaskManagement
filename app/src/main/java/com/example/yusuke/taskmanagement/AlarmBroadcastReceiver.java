package com.example.yusuke.taskmanagement;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.NotificationCompat;
import android.widget.Toast;
import android.util.Log;

/**
 * Created by yusuke on 2017/09/25.
 */

public class AlarmBroadcastReceiver extends BroadcastReceiver {
    Context context;

    @Override   // データを受信した
    public void onReceive(Context context, Intent intent) {

        this.context = context;

        Log.d("AlarmBroadcastReceiver","onReceive() pid=" + android.os.Process.myPid());

        int bid = intent.getIntExtra("intentId",1);
        Intent intent1 = new Intent(context, DetailActivity.class);
        PendingIntent pendingIntent =
                PendingIntent.getActivity(context, bid, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("時間です")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("予定まであと一か月です")
                .setContentText("時間になりました")
                // 音、バイブレート、LEDで通知
                .setDefaults(Notification.DEFAULT_ALL)
                // 通知をタップした時にMainActivityを立ち上げる
                .setContentIntent(pendingIntent)
                .build();
        // 通知
        notificationManager.notify(R.string.app_name, notification);


        int bid2 = intent.getIntExtra("intentId2",2);
        PendingIntent pendingIntent2 =
                PendingIntent.getActivity(context, bid2, intent1, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationManager notificationManager2 =
                (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification2 = new NotificationCompat.Builder(context)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker("時間です")
                .setWhen(System.currentTimeMillis())
                .setContentTitle("予定まであと一日です")
                .setContentText("時間になりました")
                // 音、バイブレート、LEDで通知
                .setDefaults(Notification.DEFAULT_ALL)
                // 通知をタップした時にMainActivityを立ち上げる
                .setContentIntent(pendingIntent2)
                .build();
        // 通知
        notificationManager2.notify(R.string.app_name, notification2);
    }
}
