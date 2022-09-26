package com.example.tianqiyubao.base;

import android.app.Application;

import com.example.tianqiyubao.db.DBManager;

import org.xutils.x;

public class UniteApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);//xutils框架声明
        //自定义application需要在清单文件中声明
        DBManager.initDB(this);//在uniteapp中进行声明，项目创建的同时数据库也被创建，使其成为全局变量
    }
}
