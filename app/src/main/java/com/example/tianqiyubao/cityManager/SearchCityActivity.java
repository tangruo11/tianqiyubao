package com.example.tianqiyubao.cityManager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.tianqiyubao.MainActivity;
import com.example.tianqiyubao.R;
import com.example.tianqiyubao.base.BaseActivity;
import com.example.tianqiyubao.base.BaseFragment;
import com.example.tianqiyubao.databinding.ActivitySearchCityBinding;
import com.example.tianqiyubao.juhe.HttpUtils;
import com.example.tianqiyubao.juhe.JuHeTempBean;
import com.example.tianqiyubao.juhe.URLUtils;
import com.google.gson.Gson;

import java.net.MalformedURLException;

public class SearchCityActivity extends BaseActivity implements View.OnClickListener {
    private ActivitySearchCityBinding binding;
    private   String city;
    String[] hotCity={"北京","上海","广州","深圳","珠海","佛山","南京","苏州","厦门","长沙","成都","福州","杭州","武汉","青岛","西安","太原"
            ,"沈阳","重庆","天津","南宁"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivitySearchCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //单一数据创建适配器
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.item_search_gv, hotCity);
        binding.searchGv.setAdapter(adapter);
        binding.searchIvSubmit.setOnClickListener(this);
        setListener();

    }

    /*为GridView设置监听*/
    private void setListener() {
        binding.searchGv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                city = hotCity[position];
                String url = URLUtils.getTemp_url(city);
                loadData(url);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_iv_submit:
             city = binding.searchEt.getText().toString().trim();
                if (!TextUtils.isEmpty(city)) {
                    String temp_url = URLUtils.getTemp_url(city);
                    loadData(temp_url);

                }else {
                    Toast.makeText(this,"输入内容不得为空",Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

    @Override
    public void onSuccess(String result) {
        int error_code = new Gson().fromJson(result, JuHeTempBean.class).getError_code();
        if (error_code==0) {
            //有这个城市,跳转主界面
            Intent intent=new Intent(this, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);//清理原来的栈，开启新的栈
            intent.putExtra("city",city);
            startActivity(intent);


        }else{
            //没有这个城市
            Toast.makeText(this,"不存在该城市，请输入正确的城市",Toast.LENGTH_SHORT).show();
        }
    }


}