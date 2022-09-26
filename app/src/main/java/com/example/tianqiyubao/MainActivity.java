package com.example.tianqiyubao;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.tianqiyubao.cityManager.CityManagerActivity;
import com.example.tianqiyubao.databinding.ActivityMainBinding;
import com.example.tianqiyubao.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityMainBinding binding;
    private List<Fragment> mdatas;
    //表示需要显示的城市集合
    List<String>cityList;
    //表示圆点的集合
    List<ImageView>imgList;
    private FragPagerAdapter adapter;
    private SharedPreferences pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        exchangebg();
        binding.mainIvAdd.setOnClickListener(this);
        binding.mainIvMore.setOnClickListener(this);
        mdatas=new ArrayList<>();
        cityList= DBManager.qureyAllCityName();
        imgList=new ArrayList<>();
        if (cityList.size()==0) {
            cityList.add("天津");
            cityList.add("上海");
            cityList.add("南京");
        }
        //获取其他地方的传值,实现增加城市的时候Fragment也增加
        Intent intent = getIntent();
        String cityIntent = intent.getStringExtra("city");
        if (!cityList.contains(cityIntent)&& !TextUtils.isEmpty(cityIntent)) {//需判断是否为空，否则会报错
            cityList.add(cityIntent);
        }

        initPager();
        adapter = new FragPagerAdapter(getSupportFragmentManager(), this, mdatas);
        binding.mainVp.setAdapter(adapter);
        //创建小圆点
        initCircle();
        //默认显示最后一个城市信息
        binding.mainVp.setCurrentItem(mdatas.size()-1);
        //设置ViewPager页面监听
        setPagerListener();
    }

    private void setPagerListener() {
        binding.mainVp.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i <imgList.size() ; i++) {
                    imgList.get(i).setSelected(false);
                }
                imgList.get(position).setSelected(true);

            }
        });
    }

    private void initCircle() {
        for (int i = 0; i <mdatas.size() ; i++) {
            ImageView point=new ImageView(this);
            point.setImageResource(R.drawable.pointbg);
            point.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) point.getLayoutParams();
            lp.setMargins(0,0,20,0);
            imgList.add(point);
            binding.mainLayoutPoint.addView(point);

        }
        imgList.get(imgList.size()-1).setSelected(true);
    }

    /*换壁纸的函数*/
    public  void exchangebg(){
        pref = getSharedPreferences("bg_pref", MODE_PRIVATE);
        int bg = pref.getInt("bg", 1);
        switch (bg) {
            case 0:
                binding.totalBg.setBackgroundResource(R.mipmap.bg2);
                break;
            case 1:
                binding.totalBg.setBackgroundResource(R.mipmap.bg);
                break;
            case 2:
                binding.totalBg.setBackgroundResource(R.mipmap.bg3);
                break;
        }

    }

    private void initPager() {
        //创建Fragment对象，添加到ViewPager当中
        for (int i = 0; i <cityList.size() ; i++) {
            CityWeatherFragment ctFrag=new CityWeatherFragment();
            Bundle bundle=new Bundle();
            bundle.putString("city",cityList.get(i));
            ctFrag.setArguments(bundle);
            mdatas.add(ctFrag);
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.main_iv_add:
                intent.setClass(this, CityManagerActivity.class);
                break;
            case R.id.main_iv_more:
                intent.setClass(this,MoreActivity.class);
                break;
        }
        startActivity(intent);
    }

    /**
     * 当页面重新加载时会调用的函数，在获取焦点之前调用，此处完成viewpager的更新
     * 因为数据源是fragment不能直接获取，所以先获取城市列表，再制作fragment和point
     */
    @Override
    protected void onRestart() {
        super.onRestart();
        List<String> list = DBManager.qureyAllCityName();
        if (list.size()==0) {
            list.add("北京");
        }
        cityList.clear();
        cityList.addAll(list);
        mdatas.clear();
        initPager();
        adapter.notifyDataSetChanged();
        //页面数量发生变化，指示器数量也会发生变化，重写设置添加指示器
        imgList.clear();
        binding.mainLayoutPoint.removeAllViews();//布局当中所有点去除
        initCircle();
        binding.mainVp.setCurrentItem(mdatas.size()-1);
    }
}