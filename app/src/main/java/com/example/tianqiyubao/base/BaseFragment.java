package com.example.tianqiyubao.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.x;

public class BaseFragment extends Fragment implements Callback.CommonCallback <String>{
    /**
     *xutils加载网络数据的步骤
     * 1.声明整体模块 ，一般在application当中声明
     * 2.执行网络请求操作
     */
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void loadData(String path){
        RequestParams params = new RequestParams(path);
        x.http().get(params,this);//当前这个类实现了这个接口，那么当前这个类的对象就是这个接口对象


    }




    @Override
    public void onSuccess(String result) {

    }

    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
        //获取数据失败时会回调的方法
    }

    @Override
    public void onCancelled(CancelledException cex) {
        //取消请求时会调用的方法
    }

    @Override
    public void onFinished() {
        //完成请求时会调用的方法
    }
}
