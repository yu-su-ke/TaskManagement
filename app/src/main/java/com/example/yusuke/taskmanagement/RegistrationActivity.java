package com.example.yusuke.taskmanagement;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    private EditText scheduleName;
    private DatePicker datePicker;
    private TimePicker timePicker;
    private Spinner spinner;
    private EditText memo;

    private TextView Text01Caution01;             // 予定名の※印
    private TextView Text01Caution02;             // メモの※印

    private Button Button01Registration;             // 登録ボタン

    int hour;
    int minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        findViews();        // 各部品の結びつけ処理

        init();             //初期値設定

        // 画面遷移用のIntentを作成
        final Intent intent = new Intent(RegistrationActivity.this, SelectSheetListView.class);

        // 登録ボタン押下時処理
        Button01Registration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // キーボードを非表示
                InputMethodManager inputMethodManager =
                        (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(v.getWindowToken(), 0);

                // DBに登録
                saveList();

                Toast.makeText(RegistrationActivity.this, "予定を登録しました。", Toast.LENGTH_SHORT).show();
                startActivity(intent);      // 各画面へ遷移
            }
        });
    }


    /**
     * 各部品の結びつけ処理
     * findViews()
     */
    private void findViews() {
        scheduleName = (EditText) findViewById(R.id.editText);
        datePicker = (DatePicker) findViewById(R.id.datePicker);
        timePicker = (TimePicker) findViewById(R.id.timePicker);
        spinner = (Spinner) findViewById(R.id.spinner);
        memo = (EditText) findViewById(R.id.editText2);

        Text01Caution01 = (TextView) findViewById(R.id.text01caution01);             // 予定名の※印
        Text01Caution02 = (TextView) findViewById(R.id.text01caution02);             // メモの※印

        Button01Registration = (Button) findViewById(R.id.button01Registration);           // 登録ボタン
    }

    /**
     * 初期値設定 (EditTextの入力欄は空白、※印は消す)
     * init()
     */
    private void init() {
        scheduleName.setText("");
        memo.setText("");

        Text01Caution01.setText("");
        Text01Caution02.setText("");
    }

    /**
     * EditTextに入力したテキストをDBに登録
     * saveDB()
     */
    private void saveList() {
        // 各EditTextで入力されたテキストを取得
        String text1 = scheduleName.getText().toString();
        int year = datePicker.getYear();//年を取得
        int month = datePicker.getMonth();//月を取得
        int day = datePicker.getDayOfMonth();//日を取得
        int currentApiVersion = Build.VERSION.SDK_INT;

        if (currentApiVersion > Build.VERSION_CODES.LOLLIPOP_MR1) {
            hour = timePicker.getHour();
            minute = timePicker.getMinute();
        } else {
            hour = timePicker.getCurrentHour();
            minute = timePicker.getCurrentMinute();
        }
        // 選択されているアイテムのIndexを取得
        int index = spinner.getSelectedItemPosition();
        // 選択されているアイテムを取得
        String item = (String) spinner.getSelectedItem();
        String text2 = memo.getText().toString();

        // EditTextが空白の場合
        if (text1.equals("") || text2.equals("")) {
            Toast.makeText(RegistrationActivity.this, "必要箇所を入力して下さい。", Toast.LENGTH_SHORT).show();
        } else {        // EditTextが全て入力されている場合
            // DBへの登録処理
            DBAdapter dbAdapter = new DBAdapter(this);
            dbAdapter.openDB();                                         // DBの読み書き
            dbAdapter.saveDB(text1, year, month, day, hour, minute, item, text2);   // DBに登録
            dbAdapter.closeDB();                                        // DBを閉じる

            init();     // 初期値設定
        }
    }

//    // メニュー未使用
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
//        return true;
//    }
//
//    // メニュー未使用
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
