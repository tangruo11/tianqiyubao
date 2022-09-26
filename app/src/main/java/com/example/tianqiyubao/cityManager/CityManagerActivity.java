package com.example.tianqiyubao.cityManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.tianqiyubao.R;
import com.example.tianqiyubao.databinding.ActivityCityManagerBinding;
import com.example.tianqiyubao.db.DBManager;
import com.example.tianqiyubao.db.DataBaseBean;

import java.util.ArrayList;
import java.util.List;

public class CityManagerActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityCityManagerBinding binding;
    private List<DataBaseBean>mdatas;//数据源
    private  CityManagerAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityCityManagerBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        mdatas=new ArrayList<>();
        initView();
        //设置适配器
        adapter= new CityManagerAdapter(mdatas,this);
        binding.cityLv.setAdapter(adapter);


    }

    /**
     * 重新获取焦点时进行数据更新,并提示适配器更新
     */
    @Override
    protected void onResume() {
        super.onResume();
        List<DataBaseBean> list = DBManager.queryAllfromInfo();
        mdatas.clear();//先清空数据
        mdatas.addAll(list);
        adapter.notifyDataSetChanged();
    }

    private void initView() {
        binding.cityBtomBtn.setOnClickListener(this);
        binding.cityIvDelete.setOnClickListener(this);
        binding.cityIvBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent=new Intent();
        switch (v.getId()) {
            case R.id.city_iv_back:
                finish();
                break;

            case R.id.city_iv_delete:
                intent.setClass(this, DeleteCityActivity.class);
                startActivity(intent);
                break;

            case R.id.city_btom_btn:

                int cityCount = DBManager.getCityCount();
                if (cityCount<=5) {
                    intent.setClass(this, SearchCityActivity.class);
                    startActivity(intent);
                }else {
                    Toast.makeText(this,"存储城市数量已达上限，请删除后再增加",Toast.LENGTH_SHORT).show();
                }
                break;
        }


    }
}