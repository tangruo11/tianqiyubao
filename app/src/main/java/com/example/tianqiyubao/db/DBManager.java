package com.example.tianqiyubao.db;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 帮助类，实现对数据库的管理
 */
public class DBManager {
    public static SQLiteDatabase database;
    /*初始化数据库信息*/
    public static void initDB(Context context){
        DBHelper dbHelper = new DBHelper(context);
        database=dbHelper.getWritableDatabase();//创建数据库

    }
    /**
     * 查找数据库当中的城市列表
     */
    public static List<String> qureyAllCityName(){
        List<String> cityList=new ArrayList<>();
        String sql="select city from info ";
        Cursor cursor = database.rawQuery(sql, null);
        while (cursor.moveToNext()) {
            @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
            cityList.add(city);
        }
        cursor.close();

        return cityList;
    }
    /**
     * 根据城市名称替换信息内容
     */
    public static int updateInfoCity(String city,String content){
        ContentValues values=new ContentValues();
        values.put("content",content);
       return database.update("info",values,"city=?",new String[]{city});
    }
    /**
     * 新增一条城市记录
     */
    public static void insertCity(String city,String content){
        String sql="insert into info (city,content) values(?,?)";
        database.execSQL(sql,new String[]{city,content});

    }
    /**
     * 根据城市名，查询数据库当中的内容
     */
    @SuppressLint("Range")
    public static String queryInfoByCity(String city){
        String result=null;
        String sql="select content from info where city=?";
        Cursor cursor = database.rawQuery(sql, new String[]{city});
        if (cursor.getCount()>0) {
            cursor.moveToFirst();
            result=cursor.getString(cursor.getColumnIndex("content"));
        }
        return result;
        }
/**
 * 存储城市信息，最多存储5个，再多就不能存储，首先得获取目前已经存储的数量
 */
    public static int getCityCount(){
        Cursor cursor = database.query("info", null, null, null, null, null, null);
        return cursor.getCount();
    }
/**
 * 查询数据库当中的全部信息
 */
public static List<DataBaseBean> queryAllfromInfo(){
    List<DataBaseBean> list=new ArrayList<>();
    Cursor cursor = database.query("info", null, null, null, null, null, null);
    while (cursor.moveToNext()) {
        @SuppressLint("Range") int id = cursor.getInt(cursor.getColumnIndex("id"));
        @SuppressLint("Range") String city = cursor.getString(cursor.getColumnIndex("city"));
        @SuppressLint("Range") String content = cursor.getString(cursor.getColumnIndex("content"));
        DataBaseBean bean=new DataBaseBean(id,city,content);
        list.add(bean);
    }
        cursor.close();

    return list;

}
/**
 *根据城市名，删除一条记录
 */
public static void deleteCityFromInfo(String city){
    String sql="delete from info where city = ?";
    database.execSQL(sql,new String[]{city});

}
/**
 * 删除表中所有数据的操作
 */
public static void deleteAllDataFromInfo(){
    String sql="delete from info";
    database.execSQL(sql);
}


}
