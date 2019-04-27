package com.example.yusuke.taskmanagement;

/**
 * Created by yusuke on 2017/09/06.
 */

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class SelectSheetListView extends Activity {
    private DBAdapter dbAdapter;
    private MyBaseAdapter myBaseAdapter;
    private List<MyListItem> items;
    private ListView mListView03;
    protected MyListItem myListItem;

    // 参照するDBのカラム：ID,品名,産地,個数,単価の全部なのでnullを指定
    private String[] columns = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.select_sheet_listview);

        // DBAdapterのコンストラクタ呼び出し
        dbAdapter = new DBAdapter(this);

        // itemsのArrayList生成
        items = new ArrayList<>();

        // MyBaseAdapterのコンストラクタ呼び出し(myBaseAdapterのオブジェクト生成)
        myBaseAdapter = new MyBaseAdapter(this, items);

        // ListViewの結び付け
        mListView03 = (ListView) findViewById(R.id.listView03);

        loadMyList();   // DBを読み込む＆更新する処理

        // 画面遷移用のIntentを作成
        final Intent intent = new Intent(SelectSheetListView.this, RegistrationActivity.class);
        // Buttonのインスタンスをレイアウトから取得
        Button button = (Button) findViewById(R.id.button);

        // 登録ボタン押下時処理
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);      // 各画面へ遷移
            }
        });


        // 行を長押しした時の処理
        mListView03.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                // アラートダイアログ表示
                AlertDialog.Builder builder = new AlertDialog.Builder(SelectSheetListView.this);
                builder.setTitle("削除");
                builder.setMessage("削除しますか？");

                // OKの時の処理
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // IDを取得する
                        myListItem = items.get(position);
                        int listId = myListItem.getId();

                        dbAdapter.openDB();     // DBの読み込み(読み書きの方)
                        dbAdapter.selectDelete(String.valueOf(listId));     // DBから取得したIDが入っているデータを削除する
                        Log.d("Long click : ", String.valueOf(listId));
                        dbAdapter.closeDB();    // DBを閉じる
                        loadMyList();
                    }
                });

                builder.setNegativeButton("キャンセル", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                // ダイアログの表示
                AlertDialog dialog = builder.create();
                dialog.show();

                return false;
            }
        });

        //行を押したときの処理
        final Intent intent2 = new Intent(SelectSheetListView.this, DetailActivity.class);

        mListView03.setOnItemClickListener(
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        myListItem = items.get(position);

                        //受け渡しデータ
                        int id2 = myListItem.getId();
                        String name = myListItem.getName();
                        int year = myListItem.getYear();
                        int month = myListItem.getMonth() + 1;
                        int day = myListItem.getDay();
                        int hour = myListItem.getHour();
                        int minute = myListItem.getMinute();
                        Log.d("試験 ", name);
                        String time = year + "年" + month + "月" + day + "日" + hour + "時" + minute + "分";

                        intent2.putExtra("NAME", name);
                        intent2.putExtra("TIME", time);
                        intent2.putExtra("YEAR", year);
                        intent2.putExtra("MONTH", month);
                        intent2.putExtra("DAY", day);
                        intent2.putExtra("HOUR", hour);
                        intent2.putExtra("MINUTE", minute);
                        startActivityForResult(intent2, 0);
                    }
                });


    }

    /**
     * DBを読み込む＆更新する処理
     * loadMyList()
     */
    private void loadMyList() {
        //ArrayAdapterに対してListViewのリスト(items)の更新
        items.clear();

        dbAdapter.openDB();     // DBの読み込み(読み書きの方)

        // DBのデータを取得
        Cursor c = dbAdapter.getDB(columns);

        if (c.moveToFirst()) {
            do {
                // MyListItemのコンストラクタ呼び出し(myListItemのオブジェクト生成)
                myListItem = new MyListItem(
                        c.getInt(0),
                        c.getString(1),
                        c.getInt(2),
                        c.getInt(3),
                        c.getInt(4),
                        c.getInt(5),
                        c.getInt(6),
                        c.getString(7),
                        c.getString(8));

                Log.d("取得したCursor(ID):", String.valueOf(c.getInt(0)));
                Log.d("取得したCursor(予定名):", c.getString(1));
                Log.d("取得したCursor(年):", String.valueOf(c.getInt(2)));
                Log.d("取得したCursor(月):", String.valueOf(c.getInt(3)));
                Log.d("取得したCursor(日):", String.valueOf(c.getInt(4)));
                Log.d("取得したCursor(時):", String.valueOf(c.getInt(5)));
                Log.d("取得したCursor(分):", String.valueOf(c.getInt(6)));
                Log.d("取得したCursor(重要度):", c.getString(7));
                Log.d("取得したCursor(メモ):", c.getString(8));


                items.add(myListItem);          // 取得した要素をitemsに追加
            } while (c.moveToNext());
        }
        c.close();
        dbAdapter.closeDB();                    // DBを閉じる
        mListView03.setAdapter(myBaseAdapter);  // ListViewにmyBaseAdapterをセット
        myBaseAdapter.notifyDataSetChanged();   // Viewの更新
    }

    /**
     * BaseAdapterを継承したクラス
     * MyBaseAdapter
     */
    public class MyBaseAdapter extends BaseAdapter {
        private Context context;
        private List<MyListItem> items;

        // 毎回findViewByIdをする事なく、高速化が出来るようするholderクラス
        private class ViewHolder {
            TextView textName;
            TextView textLimit;
            TextView textItem;
            TextView textMemo;
        }

        // コンストラクタの生成
        public MyBaseAdapter(Context context, List<MyListItem> items) {
            this.context = context;
            this.items = items;
        }

        // Listの要素数を返す
        @Override
        public int getCount() {
            return items.size();
        }

        // indexやオブジェクトを返す
        @Override
        public Object getItem(int position) {
            return items.get(position);
        }

        // IDを他のindexに返す
        @Override
        public long getItemId(int position) {
            return position;
        }

        // 新しいデータが表示されるタイミングで呼び出される
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = convertView;
            ViewHolder holder;

            // データを取得
            myListItem = items.get(position);

            if (view == null) {
                LayoutInflater inflater =
                        (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.row_sheet_listview, parent, false);

                TextView textName = (TextView) view.findViewById(R.id.textName);      // 予定名のTextView
                TextView textLimit = (TextView) view.findViewById(R.id.textLimit);        // 時間のTextView
                TextView textItem = (TextView) view.findViewById(R.id.textItem);        // 重要度のTextView
                TextView textMemo = (TextView) view.findViewById(R.id.textMemo);        // メモのTextView

                // holderにviewを持たせておく
                holder = new ViewHolder();
                holder.textName = textName;
                holder.textLimit = textLimit;
                holder.textItem = textItem;
                holder.textMemo = textMemo;
                view.setTag(holder);

            } else {
                // 初めて表示されるときにつけておいたtagを元にviewを取得する
                holder = (ViewHolder) view.getTag();
            }

            // 取得した各データを各TextViewにセット
            holder.textName.setText(myListItem.getName());
            holder.textLimit.setText(String.valueOf(myListItem.getYear()) + "年" + String.valueOf(myListItem.getMonth() + 1) + "月" + String.valueOf(myListItem.getDay()) + "日"
                    + String.valueOf(myListItem.getHour()) + "時" + String.valueOf(myListItem.getMinute()) + "分");
            holder.textItem.setText(myListItem.getItem());
            holder.textMemo.setText(myListItem.getMemo());

            return view;
        }
    }
}
