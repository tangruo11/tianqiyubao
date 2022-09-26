package com.example.tianqiyubao.cityManager;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;

import com.example.tianqiyubao.R;
import com.example.tianqiyubao.databinding.ActivityDeleteCityBinding;
import com.example.tianqiyubao.db.DBManager;

import java.util.ArrayList;
import java.util.List;

public class DeleteCityActivity extends AppCompatActivity implements View.OnClickListener {
    private ActivityDeleteCityBinding binding;
    private DeleteCityAdapter adapter;
    List<String>mdatas;
    List<String>deleteCities;//暂存的删除城市

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityDeleteCityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.deleteError.setOnClickListener(this);
        binding.deleteTrue.setOnClickListener(this);
        mdatas=new ArrayList<>();
        deleteCities=new ArrayList<>();
        adapter=new DeleteCityAdapter(mdatas,this,deleteCities);
        binding.deleteLv.setAdapter(adapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        List<String> cityNameList = DBManager.qureyAllCityName();
        mdatas.addAll(cityNameList);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        switch (v.getId()) {
            case R.id.delete_error:
                builder.setTitle("提示信息")
                        .setMessage("确定要取消删除这次的记录吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                finish();

                            }
                        })
                        .setNegativeButton("取消",null);
                builder.show();


                break;
            case R.id.delete_true:
                for (int i = 0; i <deleteCities.size() ; i++) {
                    DBManager.deleteCityFromInfo(deleteCities.get(i));
                }
             finish();
                break;
        }
    }
}