package com.example.tianqiyubao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    public DBHelper(@Nullable Context context) {
        super(context, "forecast.db", null, 1);
        //第二参数：当前数据库名称，三：数据库游标工厂，四：版本信息
    }
    /**
     * 数据库第一次被创建时会执行的方法,建库的同时建表
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql="create table info(id integer primary key autoincrement,city varchar(20) unique not null,content text not null)";
        db.execSQL(sql);//执行sql语句

    }
    /**
     * 数据库版本更新时会执行的方法
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {


    }
}
