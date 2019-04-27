package com.example.yusuke.taskmanagement;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends Activity {
    private static long WAIT_MILLIS = 2000;
    private TextView textView;
    private String text;
    private ImageView imageView1;
    private ImageView imageView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_splash);

        this.textView = (TextView) findViewById(R.id.textView);
        this.textView.setText(text);
        this.imageView2 = (ImageView)findViewById(R.id.imageView2);
        imageView2.setImageResource(R.drawable.prototype);
        this.imageView1 = (ImageView)findViewById(R.id.imageView);
        imageView1.setImageResource(R.drawable.tnm2);

        //乱数生成
        Random r = new Random();
        int ran = r.nextInt(5);
        scheduleAdvice(ran);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 一定時間後にトップ画面に移行する
        Timer t = new Timer();
        final Window window = this.getWindow();
        t.schedule(new TimerTask() {
            @Override
            public void run() {
                // トップ画面を立ち上げ、速やかに終了する
                Intent intent = new Intent(SplashActivity.this, SelectSheetListView.class);
                startActivity(intent);
                finish();
            }
        }, WAIT_MILLIS);
    }

    public void scheduleAdvice(int a){
        if(a == 0){
            this.textView.setText("予定はこまめに\n\r\r\r\r\r\r確認っ！！");
        }else if(a == 1){
            this.textView.setText("無理のない計画を\n\r\r\r\r\r\r\r\r立てよう");
        }else if(a == 2){
            this.textView.setText("予定の見直しも\n\r\r\r\r\r\r\r\r\r\r大事！");
        }else if(a == 3){
            this.textView.setText("予定通りいかない\n\r\r\r\r\r\r\r\rのも人生…");
        }else if(a == 4){
            this.textView.setText("備えあれば\n患いなしっ");
        }
    }
}
