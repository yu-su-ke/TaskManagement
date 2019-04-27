package com.example.yusuke.taskmanagement;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.icu.text.DateFormat;

import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;
import java.util.Date;

public class DetailActivity extends Activity {
    private String name;
    private String time;
    private int year;
    private int month;
    private int day;
    private int hour;
    private int minute;
    private TextView textView;
    private TextView textView2;
    private TextView textView3;
    private TextView textView6;
    private ImageView imageView1;
    private ImageView imageView2;
    Context mContext;
    private static final int bid1 = 1;
    private static final int bid2 = 2;
    private static final int bid3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_detail);

        this.imageView2 = (ImageView)findViewById(R.id.imageView3);
        imageView2.setImageResource(R.drawable.prototype);
        this.imageView1 = (ImageView)findViewById(R.id.imageView4);
        imageView1.setImageResource(R.drawable.tnm2);

        //通知機能の追加
        mContext = this;

        Intent intent = getIntent();
        name = intent.getStringExtra("NAME");
        time = intent.getStringExtra("TIME");
        year = intent.getIntExtra("YEAR", 0);
        month = intent.getIntExtra("MONTH", 0);
        day = intent.getIntExtra("DAY", 0);
        hour = intent.getIntExtra("HOUR", 0);
        minute = intent.getIntExtra("MINUTE", 0);

        textView = (TextView) findViewById(R.id.textView);
        textView.setText(time);
        textView2 = (TextView) findViewById(R.id.textView2);

        //時刻表示するコードを追加
        Calendar cal = Calendar.getInstance();       //カレンダーを取得

        final int iYear = cal.get(Calendar.YEAR);         //年を取得
        final int iMonth = cal.get(Calendar.MONTH);       //月を取得
        final int iDate = cal.get(Calendar.DATE);         //日を取得
        final int iHour = cal.get(Calendar.HOUR_OF_DAY);         //時を取得
        final int iMinute = cal.get(Calendar.MINUTE);    //分を取得
        int iSecond = cal.get(Calendar.SECOND);    //秒を取得

        String nowTime = String.valueOf(iYear) + "年" + String.valueOf(iMonth + 1) + "月" + String.valueOf(iDate) + "日"
                + String.valueOf(iHour) + "時" + String.valueOf(iMinute) +"分";
        textView2.setText(String.valueOf(nowTime));

        textView3 = (TextView) findViewById(R.id.textView3);
        textView6 = (TextView) findViewById(R.id.textView6);

        if(year == iYear){
            if(month == iMonth + 1){
                if(day == iDate){
                    if(hour == iHour){
                        if(minute > iMinute){
                            textView3.setText("予定時刻まであと\n" + (minute - iMinute) + "分だよ");
                            textView6.setText("準備は大丈夫？");
                        }else{
                            textView3.setText("予定時刻を過ぎたよ");
                            textView6.setText("オワッタ…");
                        }
                    }else if(hour > iHour){
                        if(minute < iMinute){
                            textView3.setText("予定時刻まであと\n" + (hour - iHour - 1) + "時間と" + (minute + 60 - iMinute) + "分だよ");
                        }else {
                            textView3.setText("予定時刻まであと\n" + (hour - iHour) + "時間と" + (minute - iMinute) + "分だよ");
                            textView6.setText("あとちょっとで\n時間だよっ！！");
                        }
                    }else{
                        textView3.setText("予定時刻を過ぎたよ");
                        textView6.setText("オワッタ…");
                    }
                }else if(day > iDate){
                    if(hour < iHour){
                        textView3.setText("予定時刻まであと\n" + (day - iDate - 1) + "日と" + (hour + 24 - iHour) + "時間だよ");
                    }else{
                        textView3.setText("予定時刻まであと\n" + (day - iDate) + "日と" + (hour - iHour) + "時間だよ");
                        textView6.setText("そろそろ\nだよ～");                    }
                }else{
                    textView3.setText("予定時刻を過ぎたよ");
                    textView6.setText("オワッタ…");
                }
            }else if(month > iMonth + 1){
                if(day < iDate) {
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        textView3.setText("予定時刻まであと\n" + (month - iMonth - 2) + "ヶ月と" + (day + 30 - iDate) + "日だよ");
                    } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
                        textView3.setText("予定時刻まであと\n" + (month - iMonth - 2) + "ヶ月と" + (day + 31 - iDate) + "日だよ");
                    }
                }else{
                    if (month == 1 || month == 3 || month == 5 || month == 7 || month == 8 || month == 10 || month == 12) {
                        textView3.setText("予定時刻まであと\n" + (month - iMonth - 1) + "ヶ月と" + (day - iDate) + "日だよ");
                    } else if (month == 2 || month == 4 || month == 6 || month == 9 || month == 11) {
                        textView3.setText("予定時刻まであと\n" + (month - iMonth - 1) + "ヶ月と" + (day - iDate) + "日だよ");
                    }
                }
                textView6.setText("まだよゆー");
            }else if(month < iMonth + 1){
                textView3.setText("予定時刻を過ぎたよ");
                textView6.setText("オワッタ…");
            }
        }else if(year > iYear){
            if(month < iMonth + 1) {
                textView3.setText("予定時刻まであと\n" + (year - iYear - 1) + "年と" +(month + 11 - iMonth) + "ヶ月だよ");
            }else{
                textView3.setText("予定時刻まであと\n" + (year - iYear) + "年と" + (month - iMonth -1) + "ヶ月だよ");
            }
            textView6.setText("zzz...");
        }else{
            textView3.setText("予定時刻を過ぎたよ");
            textView6.setText("オワッタ…");
        }

//        // 一か月前アラーム
//        Button button = (Button)this.findViewById(R.id.button);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar = Calendar.getInstance();
//                // 過去の時間は即実行されます
//                calendar.set(Calendar.YEAR, iYear);
//                calendar.set(Calendar.MONTH, iMonth - 1);
//                calendar.set(Calendar.DATE, iDate);
//                calendar.set(Calendar.HOUR_OF_DAY, iHour);
//                calendar.set(Calendar.MINUTE, iMinute);
//
//                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
//                intent.putExtra("intentId", 1);
//                PendingIntent pending = PendingIntent.getBroadcast(getApplicationContext(), bid1, intent, 0);
//
//                // アラームをセットする
//                AlarmManager am = (AlarmManager) DetailActivity.this.getSystemService(ALARM_SERVICE);
//                am.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pending);
//                Toast.makeText(getApplicationContext(), "一か月前に通知します", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        // 一日前アラーム
//        Button button2 = (Button)this.findViewById(R.id.button2);
//        button2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Calendar calendar2 = Calendar.getInstance();
//                // 過去の時間は即実行されます
//                calendar2.set(Calendar.YEAR, iYear);
//                calendar2.set(Calendar.MONTH, iMonth);
//                calendar2.set(Calendar.DATE, iDate - 1);
//                calendar2.set(Calendar.HOUR_OF_DAY, iHour);
//                calendar2.set(Calendar.MINUTE, iMinute);
//
//                Intent intent = new Intent(getApplicationContext(), AlarmBroadcastReceiver.class);
//                intent.putExtra("intentId2", 2);
//                PendingIntent pending2 = PendingIntent.getBroadcast(getApplicationContext(), bid2, intent, 0);
//
//                // アラームをセットする
//                AlarmManager am = (AlarmManager) DetailActivity.this.getSystemService(ALARM_SERVICE);
//                am.set(AlarmManager.RTC_WAKEUP, calendar2.getTimeInMillis(), pending2);
//                Toast.makeText(getApplicationContext(), "一日前に通知します", Toast.LENGTH_SHORT).show();
//            }
//        });
    }

//    private void normalNotification() {
//        NotificationCompat.Builder builder = new NotificationCompat.Builder(mContext);
//        builder.setSmallIcon(R.mipmap.ic_launcher);
//        builder.setContentTitle("タイトル");
//        builder.setContentText("内容");
//        builder.setContentInfo("情報欄");
//        builder.setTicker("通知概要");
//        NotificationManager manager =
//                (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
//        manager.notify(0, builder.build());
//    }
}
