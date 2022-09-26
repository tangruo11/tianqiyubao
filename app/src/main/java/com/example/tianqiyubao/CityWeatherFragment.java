package com.example.tianqiyubao;

import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.tianqiyubao.base.BaseFragment;
import com.example.tianqiyubao.databinding.FragmentCityWeatherBinding;
import com.example.tianqiyubao.db.DBManager;
import com.example.tianqiyubao.juhe.HttpUtils;
import com.example.tianqiyubao.juhe.JuHeIndexBean;
import com.example.tianqiyubao.juhe.JuHeTempBean;
import com.example.tianqiyubao.juhe.URLUtils;
import com.google.gson.Gson;

import java.net.MalformedURLException;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CityWeatherFragment extends BaseFragment implements View.OnClickListener {
private FragmentCityWeatherBinding binding;
private String city;
    private SharedPreferences pref;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding=FragmentCityWeatherBinding.inflate(inflater,container,false);
        initView();
        //从Activity获取携带的城市信息
        Bundle bundle = getArguments();
        city= bundle.getString("city");
        String Tempurl= URLUtils.getTemp_url(city);
        String IndexUrl=URLUtils.getIndex_url(city);
        loadData(Tempurl);
        loadIndexData(IndexUrl);
        exchangebg();
        return binding.getRoot();


    }
    /*换壁纸的函数*/
    public  void exchangebg(){
        pref = getContext().getSharedPreferences("bg_pref", MODE_PRIVATE);//name为文件名称，要和存储的文件名一致才能读出正确的数据
        int bg = pref.getInt("bg", 1);
        switch (bg) {
            case 0:
                binding.fgmentBg.setBackgroundResource(R.mipmap.bg2);
                break;
            case 1:
                binding.fgmentBg.setBackgroundResource(R.mipmap.bg);
                break;
            case 2:
                binding.fgmentBg.setBackgroundResource(R.mipmap.bg3);
                break;
        }

    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private JuHeIndexBean.ResultBean.LifeBean lifeBean;


    private void loadIndexData(final String indexUrl) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String indexJson = HttpUtils.getIndexJson(indexUrl);
                    Gson gson=new Gson();
                    JuHeIndexBean juHeIndexBean = gson.fromJson(indexJson, JuHeIndexBean.class);
                    if (juHeIndexBean!=null) {
                        lifeBean = juHeIndexBean.getResult().getLife();
                    }


                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }

            }
        }).start();
    }

    @Override
    public void onSuccess(String result) {
       //解析并展示数据
        parseShowData(result);
        int i = DBManager.updateInfoCity(city, result);
        if (i<=0) {//表示没有更新数据，即不存在这条城市信息，则插入数据
            DBManager.insertCity(city,result);
        }


    }
    @Override
    public void onError(Throwable ex, boolean isOnCallback) {
   //请求失败的话，从数据库当中查找上一次的城市信息显示在Fragment当中
        String s = DBManager.queryInfoByCity(city);
        if (!TextUtils.isEmpty(s)) {
            parseShowData(s);
        }
    }
    private void parseShowData(String result) {
        Gson gson=new Gson();
        JuHeTempBean juHeTempBean = gson.fromJson( result, JuHeTempBean.class);
        JuHeTempBean.ResultBean tempBeanResult = juHeTempBean.getResult();
        binding.fragCity.setText(tempBeanResult.getCity());
        binding.fragTime.setText(tempBeanResult.getFuture().get(0).getDate());
        binding.fragFengkuang.setText(tempBeanResult.getFuture().get(0).getDirect());
        binding.fragTemprange.setText(tempBeanResult.getFuture().get(0).getTemperature());
        binding.fragTianqi.setText(tempBeanResult.getRealtime().getInfo()+"℃");
        binding.fragWendu.setText(tempBeanResult.getRealtime().getTemperature());
        List<JuHeTempBean.ResultBean.FutureBean> futureList = tempBeanResult.getFuture();
        futureList.remove(0);
        for (int i = 0; i <futureList.size() ; i++) {
            //将xml文件转换成view对象
            View itemView = LayoutInflater.from(getContext()).inflate(R.layout.item_main_center, null);
            //设置view参数
            itemView.setLayoutParams(new RelativeLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            binding.fragCenterLayout.addView(itemView);
            TextView date=itemView.findViewById(R.id.item_day);
            TextView tianqi=itemView.findViewById(R.id.item_tianqi);
            TextView fengxiang=itemView.findViewById(R.id.item_fengxiang);
            TextView wendu=itemView.findViewById(R.id.item_wendu);
            date.setText(futureList.get(i).getDate());
            tianqi.setText(futureList.get(i).getWeather());
            fengxiang.setText(futureList.get(i).getDirect());
            wendu.setText(futureList.get(i).getTemperature());


        }




    }



    private void initView() {
       binding.cloth.setOnClickListener(this);
        binding.car.setOnClickListener(this);
        binding.cold.setOnClickListener(this);
        binding.sport.setOnClickListener(this);
        binding.rays.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
        String msg="暂无数据";
        switch (v.getId()) {
            case R.id.cloth:
                if (lifeBean!=null) {
                    msg = lifeBean.getChuanyi().getV() + "\n" + lifeBean.getChuanyi().getDes();
                }
                builder.setTitle("穿衣指数")
                        .setMessage(msg)
                        .setPositiveButton("确定",null);
                builder.show();
                break;
            case R.id.car:
                if (lifeBean!=null) {
                    msg = lifeBean.getXiche().getV() + "\n" + lifeBean.getXiche().getDes();
                }
                builder.setTitle("洗车指数")
                        .setMessage(msg)
                        .setPositiveButton("确定",null);
                builder.show();
                break;
            case R.id.cold:
                if (lifeBean!=null) {
                    msg = lifeBean.getGanmao().getV() + "\n" + lifeBean.getGanmao().getDes();
                }
                builder.setTitle("感冒指数")
                        .setMessage(msg)
                        .setPositiveButton("确定",null);
                builder.show();
                break;
            case R.id.sport:
                if (lifeBean!=null) {
                    msg = lifeBean.getYundong().getV() + "\n" + lifeBean.getYundong().getDes();
                }
                builder.setTitle("运动指数")
                        .setMessage(msg)
                        .setPositiveButton("确定",null);
                builder.show();
                break;
            case R.id.rays:
                if (lifeBean!=null) {
                    msg = lifeBean.getZiwaixian().getV() + "\n" + lifeBean.getZiwaixian().getDes();
                }
                builder.setTitle("紫外线指数")
                        .setMessage(msg)
                        .setPositiveButton("确定",null);
                builder.show();
                break;
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding=null;
    }
}