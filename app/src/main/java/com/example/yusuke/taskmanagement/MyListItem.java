package com.example.yusuke.taskmanagement;

/**
 * Created by yusuke on 2017/09/06.
 */

import android.util.Log;

public class MyListItem {
    protected int id;
    protected String name;
    protected int year;
    protected int month;
    protected int day;
    protected int hour;
    protected int minute;
    protected String item;
    protected String memo;

    public MyListItem(int id, String name, int year, int month, int day, int hour, int minute, String item, String memo) {
        this.id = id;
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.hour = hour;
        this.minute = minute;
        this.item = item;
        this.memo = memo;
    }

    public int getId() {
        Log.d("取得したID：", String.valueOf(id));
        return id;
    }

    public String getName() {
        return name;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    public int getHour() {
        return hour;
    }

    public int getMinute() {
        return minute;
    }

    public String getItem() {
        return item;
    }

    public String getMemo() {
        return memo;
    }
}
